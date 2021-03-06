/**
 * The file TableToExcelExporter.java was created on 13:46:44 at 2007.30.10
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.ashihara.ui.core.file.ExcelFileFilter;
import com.ashihara.ui.core.renderer.KASTableCellRenderer;
import com.ashihara.utils.FileUtils;

public class TableToExcelExporter {
	
	private final static float ROW_HEIGHT = 25;
	private final static short ROW_WIDTH = 60;
	private final static short TITLE_Y_POS = 0;
	private final static short TITLE_Y_OFFSET = 2;
	private final static int MAX_TITLE_LENGTH = 25;
	
	public static String toTitle(FightingGroup group, UIC uic) {
		String title = group.getName();
		return title.length() > MAX_TITLE_LENGTH ? title.substring(0, MAX_TITLE_LENGTH) : title;
	}
	
	public static void drawTableToExcel(JTable table, UIC uic) throws IOException{
		drawTableToExcel(table, "", uic);
	}
	
	public static void drawTableToExcel(JTable table, String initialFileName, UIC uic) throws IOException{
		JTable[] tables = {table};
		drawTablesToExcelHorizontal(tables, initialFileName, uic);
	}
	
	public static void drawTablesToExcelHorizontal(JTable[] tables, String initialFileName, UIC uic) throws IOException{
		if (initialFileName.length()>0)
			initialFileName+=" ";
		initialFileName+=uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		drawTablesToExcelHorizontal(tables, file);
	}

	public static void drawToExcel(IExcelable excelable, String initialFileName, UIC uic) throws IOException{
		if (initialFileName.length() > 0) {
			initialFileName += " ";
		}
		
		initialFileName += uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		drawTablesToExcelHorizontal(excelable, file);
	}

	public static void drawTableToExcel(JTable table, File file) throws IOException{
		JTable[] tables = {table};
		drawTablesToExcelHorizontal(tables, file);
	}

	public static void drawTablesToExcelHorizontal(JTable[] tables, File file) throws IOException{
		if (file == null) return;
		FileOutputStream fileOut = new FileOutputStream(file);
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
		int x = 0;
		for (JTable table : tables){
			drawTableToBook(book, sheet, table, x, 0);
			x+=table.getColumnModel().getColumnCount()+1;
		}
		
        book.write(fileOut);
        fileOut.close();
        openFileWithSystemEditor(file);
	}

	public static void drawTablesToExcelHorizontal(IExcelable excelable, File file) throws IOException{
		if (file == null) return;
		FileOutputStream fileOut = new FileOutputStream(file);
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
		drawTableToBook(book, sheet, excelable, 0, 0);
		
        book.write(fileOut);
        fileOut.close();
        openFileWithSystemEditor(file);
	}
	
	private static void drawTableToBook(HSSFWorkbook book, HSSFSheet sheet, JTable table, int colOffset, int rowOffset){
		drawTableToBook(
				book,
				sheet,
				new IExcelable() {
					
					@Override
					public int getRowCount() {
						return table.getRowCount();
					}
					
					@Override
					public String getHeader(int column) {
						TableColumn tableColumn = table.getColumnModel().getColumn(column);
						return tableColumn.getHeaderValue().toString();
					}
					
					@Override
					public int getColumnCount() {
						return table.getColumnModel().getColumnCount();
					}
					
					@Override
					public String getCell(int row, int column) {
		            	Object value = table.getValueAt(row, column);
		            	Object renderedValue = tryToGetRenderedValue(table, row, column, value);
		            	if (renderedValue == null) {
		            		renderedValue = value == null ? "" : value;
		            	}
		            	String val = renderedValue.toString().replaceAll("\\<.*?\\>", "");
		            	return val;
					}
				},
				colOffset,
				rowOffset
		);
	}

	
	private static void drawTableToBook(HSSFWorkbook book, HSSFSheet sheet, IExcelable excelable, int colOffset, int rowOffset){
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 20, true, HSSFCellStyle.ALIGN_CENTER, new HSSFColor.WHITE().getIndex(), true); 
        HSSFCellStyle rowStyle = createStyle(book, 18, false, HSSFCellStyle.ALIGN_LEFT, new HSSFColor.WHITE().getIndex(), true);

        for (int col = 0; col < excelable.getColumnCount(); col++) {
        	int excelColumn = col + colOffset;
        	
        	HSSFCellUtil.createCell(sheet.createRow(0), excelColumn, excelable.getHeader(col), tableHeaderStyle);
        	HSSFCellUtil.createCell(sheet.createRow(1), excelColumn, "", tableHeaderStyle);
            for (int row = 1; row < excelable.getRowCount(); row++){
            	int excelRow = row + rowOffset;
            	String val = excelable.getCell(row - 1, col);
            	HSSFCellUtil.createCell(sheet.createRow(excelRow+1), excelColumn, val, rowStyle);
            }
        }
	}

	
	private static Object tryToGetRenderedValue(JTable table, int row, int col, Object value) {
		Object renderedValue = null;
		TableCellRenderer renderer = table.getCellRenderer(row, col);
		if (renderer instanceof KASTableCellRenderer) {
			renderer.getTableCellRendererComponent(table, value, false, false, row, col);
			renderedValue = ((KASTableCellRenderer)renderer).getLastSetValue();
		}
		return renderedValue;
	}

    private static HSSFCellStyle createStyle(
    		HSSFWorkbook wb,
    		int fontSize,
    		boolean fontBold,
    		short alignment,
    		Short foreground,
    		boolean needBorders
    ) { 
        HSSFFont font = createFont(wb, fontSize, fontBold);
        return createStyle(wb, font, alignment, foreground, needBorders);
    }

    private static HSSFCellStyle createStyle(
    		HSSFWorkbook wb,
    		HSSFFont font,
    		short alignment,
    		Short foreground,
    		boolean needBorders
    ) { 
        HSSFCellStyle style = wb.createCellStyle();
        if (foreground != null) {
        	style.setFillForegroundColor(foreground);
        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        }
        if (needBorders) {
        	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        }
        
        style.setFont(font);
        style.setAlignment(alignment);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    
	public static String getCorrectExcelFileName(String path){
		return getCorrectFileName(path, ExcelFileFilter.EXTENSION);
	}
	
	public static String getCorrectFileName(String path, String extesntion){
		if (path.endsWith("."+extesntion)) return path;
		else return path+"."+extesntion;
	}
	
	public static void openFileWithSystemEditor(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			MessageHelper.showLocalizedMessage(null, e);
		}
	}

	public static void drawWholeTreeToExcel(
			Map<FightingGroup, List<FightResult>> fightResults,
			String initialFileName,
			UIC uic
	) throws IOException {
		if (initialFileName.length() > 0) {
			initialFileName += " ";
		}
		
		initialFileName += uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		if (file != null) {
			try (FileOutputStream fileOut = new FileOutputStream(file)) {
				HSSFWorkbook book = new HSSFWorkbook();
				
				int i = 0;
				for (FightingGroup group : fightResults.keySet()) {
					i++;
					
					HSSFSheet sheet = book.createSheet(i + ") " + toTitle(group, uic));
					
					List<FightResult> fr = fightResults.get(group);
					String title = toTitle(group, uic);
					
					if (SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
						drawOlympicFightResultsToExcel(title, fr, book, sheet);
					} else if (SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
						drawRoundRobinFightResultsToExcel(uic, title, fr, book, sheet);
					} else {
						throw new IllegalArgumentException("Unsupported group type " + group.getType());
					}
				}
				
				book.write(fileOut);
				fileOut.close();
				openFileWithSystemEditor(file);
			}
		}
	}

	
	
	public static void drawPlanToExcel(
			List<FightResultForPlan> fightResults,
			String initialFileName,
			UIC uic
	) throws IOException {
		if (initialFileName.length() > 0) {
			initialFileName += " ";
		}
		
		initialFileName += uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		if (file != null) {
			try (FileOutputStream fileOut = new FileOutputStream(file)) {
				HSSFWorkbook book = new HSSFWorkbook();
				
				HSSFSheet sheet = book.createSheet("plan");
				drawPlanFightResultsToExcel(uic, fightResults, book, sheet);
				
				book.write(fileOut);
				fileOut.close();
				openFileWithSystemEditor(file);
			}
		}
	}
	
	
	private static void drawPlanFightResultsToExcel(
			UIC uic,
			List<FightResultForPlan> items,
			HSSFWorkbook book,
			HSSFSheet sheet
	) {
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 20, true, HSSFCellStyle.ALIGN_CENTER, null, true);
        
        HSSFColor lightRed = getLightRedColor(book);
        
        HSSFCellStyle redStyle = createCellStyle(book, lightRed, true);
        redStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle redStyleLeft = createCellStyle(book, lightRed, true);
        
        HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE(), true);
        whiteStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle whiteStyleLeft = createCellStyle(book, new HSSFColor.WHITE(), true);
        
        HSSFCellUtil.createCell(sheet.createRow(1), 0, uic.NR(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 1, uic.GROUP(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 2, uic.FIGHTER() + " 1", tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 3, uic.POINTS(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 4, uic.RESULT_SCORE(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 5, uic.FIGHTER() + " 2", tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 6, uic.POINTS(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(1), 7, uic.RESULT_SCORE(), tableHeaderStyle);
        
        int row = 3;
        for (FightResultForPlan frfp : items) {
        	HSSFCellUtil.createCell(sheet.createRow(row), 0, frfp.getNumberInPlan(), whiteStyleLeft);
        	HSSFCellUtil.createCell(sheet.createRow(row), 1, toString(frfp.getFightingGroup()), whiteStyleLeft);
        	
        	HSSFCellUtil.createCell(sheet.createRow(row), 2, emptyIfNpe(() -> frfp.getFightResult().getFirstFighter().getChampionshipFighter().toString()), redStyleLeft);
        	HSSFCellUtil.createCell(sheet.createRow(row), 3, emptyIfNpe(() -> toString(frfp.getFightResult().getFirstFighterPoints())), redStyle);
        	HSSFCellUtil.createCell(sheet.createRow(row), 4, emptyIfNpe(() -> toString(frfp.getFightResult().getFirstFighterPointsForWin())), redStyle);
        	
        	HSSFCellUtil.createCell(sheet.createRow(row), 5, emptyIfNpe(() -> frfp.getFightResult().getSecondFighter().getChampionshipFighter().toString()), whiteStyleLeft);
        	HSSFCellUtil.createCell(sheet.createRow(row), 6, emptyIfNpe(() -> toString(frfp.getFightResult().getSecondFighterPoints())), whiteStyle);
        	HSSFCellUtil.createCell(sheet.createRow(row), 7, emptyIfNpe(() -> toString(frfp.getFightResult().getSecondFighterPointsForWin())), whiteStyle);
        		
        	row ++;
        }
	}

	private static String emptyIfNpe(Supplier<String> s) {
		try {
			return s.get();
		} catch (NullPointerException e) {
			return "";
		}
	}

	
	private static void drawRoundRobinFightResultsToExcel(
			UIC uic,
			String title,
			List<FightResult> fightResults,
			HSSFWorkbook book,
			HSSFSheet sheet
	) {
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 20, true, HSSFCellStyle.ALIGN_CENTER, null, true);
        
        HSSFColor lightRed = getLightRedColor(book);
        
        HSSFCellStyle redStyle = createCellStyle(book, lightRed, true);
        redStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle redStyleLeft = createCellStyle(book, lightRed, true);
        
        HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE(), true);
        whiteStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle whiteStyleLeft = createCellStyle(book, new HSSFColor.WHITE(), true);
        
		HSSFCellStyle headerStyle = createHeaderCellStyle(book, new HSSFColor.WHITE(), false);
		HSSFRow headerRow = sheet.createRow(TITLE_Y_POS);
		headerRow.setHeightInPoints(ROW_HEIGHT);
		HSSFCellUtil.createCell(headerRow, 2, title, headerStyle);


        HSSFCellUtil.createCell(sheet.createRow(2), 0, uic.FIGHTER() + " 1", tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(2), 1, uic.POINTS(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(2), 2, uic.RESULT_SCORE(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(2), 3, uic.FIGHTER() + " 2", tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(2), 4, uic.POINTS(), tableHeaderStyle);
        HSSFCellUtil.createCell(sheet.createRow(2), 5, uic.RESULT_SCORE(), tableHeaderStyle);
        
        int row = 3;
        for (FightResult fr : fightResults) {
        	HSSFCellUtil.createCell(sheet.createRow(row), 0, fr.getFirstFighter().getChampionshipFighter().toString(), redStyleLeft);
        	HSSFCellUtil.createCell(sheet.createRow(row), 1, toString(fr.getFirstFighterPoints()), redStyle);
        	HSSFCellUtil.createCell(sheet.createRow(row), 2, toString(fr.getFirstFighterPointsForWin()), redStyle);
        	
        	HSSFCellUtil.createCell(sheet.createRow(row), 3, fr.getSecondFighter().getChampionshipFighter().toString(), whiteStyleLeft);
        	HSSFCellUtil.createCell(sheet.createRow(row), 4, toString(fr.getSecondFighterPoints()), whiteStyle);
        	HSSFCellUtil.createCell(sheet.createRow(row), 5, toString(fr.getSecondFighterPointsForWin()), whiteStyle);
        	row ++;
        }
	}

	private static String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static void drawOlympicFightResultsToExcel(
			String title,
			List<FightResult> fightResults,
			String initialFileName,
			UIC uic
	) throws IOException {
		if (initialFileName.length() > 0) {
			initialFileName += " ";
		}
		
		initialFileName += uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		if (file != null) {
			drawOlympicFightResultsToExcel(title, fightResults, file);
		}
	}

	
	private static void drawOlympicFightResultsToExcel(String title, List<FightResult> fightResults, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		
		drawOlympicFightResultsToExcel(title, fightResults, book, sheet);
		
        book.write(fileOut);
        fileOut.close();
        openFileWithSystemEditor(file);
	}
	
	private static void drawOlympicFightResultsToExcel(
			String title,
			List<FightResult> fightResults,
			HSSFWorkbook book,
			HSSFSheet sheet
	) throws IOException {
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
		
		HSSFCellStyle headerStyle = createHeaderCellStyle(book, new HSSFColor.WHITE(), false);
		HSSFRow headerRow = sheet.createRow(TITLE_Y_POS);
		headerRow.setHeightInPoints(ROW_HEIGHT);
		HSSFCellUtil.createCell(headerRow, 2, title, headerStyle);

		
		drawOlympicNet(fightResults, book, sheet);

		HSSFColor lightRed = getLightRedColor(book);
		
		HSSFCellStyle redStyle = createCellStyle(book, lightRed, true);
		HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE(), true);
		
		for (FightResult fr : fightResults) {
			
			int x = fr.getOlympicLevel().intValue();
			int y = fr.getOlympicPositionOnLevel().intValue();
			
			int theFirstOffset = Math.max(0, (int)(Math.pow(2, x)) - 1);
			int distance = (int)(Math.pow(2, x + 1));
			
			HSSFRow redRow = sheet.createRow(TITLE_Y_OFFSET + (y * 2 * distance) + theFirstOffset);
			redRow.setHeightInPoints(ROW_HEIGHT);

			HSSFRow whiteRow = sheet.createRow(TITLE_Y_OFFSET + (y * 2 * distance) + distance + theFirstOffset);
			whiteRow.setHeightInPoints(ROW_HEIGHT);

			if (fr.getRedFighter() != null) {
				HSSFCellUtil.createCell(redRow, x, toString(fr.getRedFighter()), redStyle);
			}
			
			if (fr.getBlueFighter() != null) {
				HSSFCellUtil.createCell(whiteRow, x, toString(fr.getBlueFighter()), whiteStyle);
			}
		}
	}
	
	private static void drawOlympicNet(List<FightResult> fightResults, HSSFWorkbook book, HSSFSheet sheet) {
		int firstLevelFightersCount = 0;
		int secondLevelFightersCount = 0;
		
		for (FightResult fr : fightResults) {
			if (fr.getOlympicLevel().intValue() == 0) {
				if (fr.getFirstFighter() != null) {
					firstLevelFightersCount ++;
				}
				if (fr.getSecondFighter() != null) {
					firstLevelFightersCount ++;
				}
			} else if (fr.getOlympicLevel().intValue() == 1) {
				if (fr.getFirstFighter() != null && fr.getFirstFighterParent() == null) {
					secondLevelFightersCount ++;
				}
				if (fr.getSecondFighter() != null && fr.getSecondFighterParent() == null) {
					secondLevelFightersCount ++;
				}
			}
		}
		
		secondLevelFightersCount = secondLevelFightersCount + firstLevelFightersCount / 2;
		
		HSSFColor lightRed = getLightRedColor(book);
		
		HSSFCellStyle redStyle = createCellStyle(book, lightRed, true);
		HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE(), true);
		HSSFCellStyle lineStyle = createLineCellStyle(book, new HSSFColor.WHITE(), false);
		
		int x = 0;
		boolean twoSemiFinals = false;
		boolean wasTwoSemiFinals = false;
		for (int maxY = secondLevelFightersCount, i = 0; maxY > 0; maxY /= 2, i++) {
			int fightersCount = 0;
			boolean wasFightForFirstPlace = false;
			for (int y = 0; y < maxY; y ++) {
				if (x == 0) {
					if (fightersCount >= firstLevelFightersCount) {
						break;
					}
				}
				
				int theFirstOffset = Math.max(0, (int)(Math.pow(2, x)) - 1);
				int distance = (int)(Math.pow(2, x + 1));
				
				HSSFRow redRow = sheet.createRow(TITLE_Y_OFFSET + (y * 2 * distance) + theFirstOffset);
				redRow.setHeightInPoints(ROW_HEIGHT);
				HSSFCellUtil.createCell(redRow, x, "", redStyle);
				fightersCount ++;
				
				if (maxY > 1 || !wasTwoSemiFinals) {
					boolean textDrawn = false;
					for (int yLine = (y * 2 * distance) + theFirstOffset + 1; yLine < (y * 2 * distance) + theFirstOffset + distance; yLine ++) {
						HSSFRow lineRow = sheet.createRow(TITLE_Y_OFFSET + yLine);
						lineRow.setHeightInPoints(ROW_HEIGHT);
						String text = "";
						if (!textDrawn) {
							if (maxY == 1) {
								text = "Final";
							} else if (maxY == 2) {
								if (wasTwoSemiFinals) {
									if (wasFightForFirstPlace) {
										text = "Final (for the 3-rd place)";
									} else {
										text = "Final (for the 1-st place)";
										wasFightForFirstPlace = true;
									}
								} else {
									text = "Semi-Final";
								}
							} else {
								text = "1/" + maxY;
							}
						}
						HSSFCellUtil.createCell(lineRow, x, text, lineStyle);
						textDrawn = true;
					}
				}
				
				if (x == 0) {
					if (fightersCount >= firstLevelFightersCount) {
						break;
					}
				}
				
				HSSFRow whiteRow = sheet.createRow(TITLE_Y_OFFSET + (y * 2 * distance) + distance + theFirstOffset);
				whiteRow.setHeightInPoints(ROW_HEIGHT);
				HSSFCellUtil.createCell(whiteRow, x, "", whiteStyle);
				fightersCount ++;
			}
			
			if (fightersCount == 4 && (i > 0 || firstLevelFightersCount > secondLevelFightersCount)) {
				twoSemiFinals = !twoSemiFinals;
				wasTwoSemiFinals = true;
			}
			
			if (twoSemiFinals) {
				maxY *= 2;
			}
			x++;
		}
		
		
		
		if (!wasTwoSemiFinals) {
			int y = 0;
			
			int theFirstOffset = Math.max(0, (int)(Math.pow(2, x)) - 1);
			int distance = (int)(Math.pow(2, x + 1));
			
			HSSFRow redRow = sheet.createRow(TITLE_Y_OFFSET + (y * 2 * distance) + theFirstOffset);
			redRow.setHeightInPoints(ROW_HEIGHT);
			HSSFCellUtil.createCell(redRow, x, "", redStyle);
		}
	}

	private static String toString(GroupChampionshipFighter f) {
		if (f == null || f.getChampionshipFighter() == null) {
			return "";
		}
		return f.getChampionshipFighter().toString();
	}

	private static HSSFCellStyle createLineCellStyle(HSSFWorkbook book, HSSFColor color, boolean needBorders) {
		HSSFCellStyle rowStyle = createStyle(book, 8, true, HSSFCellStyle.ALIGN_CENTER, color.getIndex(), needBorders);
		rowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return rowStyle;
	}

	private static HSSFFont createFont(
    		HSSFWorkbook wb,
    		int fontSize,
    		boolean fontBold
	) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setBoldweight(fontBold ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontName(HSSFFont.FONT_ARIAL);
        return font;
	}
	
	private static HSSFCellStyle createCellStyle(HSSFWorkbook book, HSSFColor color, boolean needBorders) {
		HSSFCellStyle rowStyle = createStyle(book, createFont(book, 18, true), HSSFCellStyle.ALIGN_LEFT, color.getIndex(), needBorders);
		return rowStyle;
	}

	private static HSSFCellStyle createHeaderCellStyle(HSSFWorkbook book, HSSFColor color, boolean needBorders) {
		HSSFCellStyle rowStyle = createStyle(book, 22, true, HSSFCellStyle.ALIGN_LEFT, color.getIndex(), needBorders);
		return rowStyle;
	}
	
	
	public static HSSFColor setColor(HSSFWorkbook workbook, byte r,byte g, byte b) {
	    HSSFPalette palette = workbook.getCustomPalette();
	    HSSFColor hssfColor = palette.findColor(r, g, b);
	    if (hssfColor == null) {
	    	palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
	    	hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
	    }

	    return hssfColor;
	}

	private static HSSFColor getLightRedColor(HSSFWorkbook book) {
		HSSFColor color = setColor(book, (byte)255, (byte)204, (byte)203);
		return color;
	}

}
