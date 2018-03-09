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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.ashihara.ui.core.file.ExcelFileFilter;
import com.ashihara.ui.core.renderer.KASTableCellRenderer;
import com.ashihara.utils.FileUtils;

public class TableToExcelExporter {
	
	private final static float ROW_HEIGHT = 15;
	private final static short ROW_WIDTH = 25;
	private final static short TITLE_Y_POS = 0;
	private final static short TITLE_Y_OFFSET = 2;
	
	public static String toTitle(FightingGroup group, UIC uic) {
		return group.getName() + ", " + SC.GENDER.getCaption(group.getGender(), uic) + ", " + uic.TATAMI() + " " + group.getTatami();
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
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 12, true, HSSFCellStyle.ALIGN_CENTER, new HSSFColor.WHITE().getIndex()); 
        HSSFCellStyle rowStyle = createStyle(book, 10, false, HSSFCellStyle.ALIGN_LEFT, new HSSFColor.WHITE().getIndex());

        for (int col = 0; col < excelable.getColumnCount(); col++) {
        	int excelColumn = col + colOffset;
        	
            HSSFCellUtil.createCell(sheet.createRow(0), excelColumn, excelable.getHeader(col), tableHeaderStyle);
            for (int row = 0; row < excelable.getRowCount(); row++){
            	int excelRow = row + rowOffset;
            	String val = excelable.getCell(row, col);
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
    		Short foreground
    ) { 
        HSSFFont font = createFont(wb, fontSize, fontBold);
        return createStyle(wb, font, alignment, foreground);
    }

    private static HSSFCellStyle createStyle(
    		HSSFWorkbook wb,
    		HSSFFont font,
    		short alignment,
    		Short foreground
    ) { 
        HSSFCellStyle style = wb.createCellStyle();
        if (foreground != null) {
        	style.setFillForegroundColor(foreground);
        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
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
					
					HSSFSheet sheet = book.createSheet(String.valueOf(i));
					
					List<FightResult> fr = fightResults.get(group);
					String title = toTitle(group, uic);
					
					if (SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
						drawWholeTreeToExcel(title, fr, book, sheet);
					} else if (SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
						drawToExcelAsTable(uic, title, fr, book, sheet);
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

	private static void drawToExcelAsTable(
			UIC uic,
			String title,
			List<FightResult> fightResults,
			HSSFWorkbook book,
			HSSFSheet sheet
	) {
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 10, true, HSSFCellStyle.ALIGN_CENTER, null);
        
        HSSFCellStyle redStyle = createCellStyle(book, new HSSFColor.RED());
        redStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle redStyleLeft = createCellStyle(book, new HSSFColor.RED());
        
        HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE());
        whiteStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle whiteStyleLeft = createCellStyle(book, new HSSFColor.WHITE());
        
		HSSFCellStyle headerStyle = createHeaderCellStyle(book, new HSSFColor.WHITE());
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

	public static void drawWholeTreeToExcel(
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
			drawWholeTreeToExcel(title, fightResults, file);
		}
	}

	
	private static void drawWholeTreeToExcel(String title, List<FightResult> fightResults, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		
		drawWholeTreeToExcel(title, fightResults, book, sheet);
		
        book.write(fileOut);
        fileOut.close();
        openFileWithSystemEditor(file);
	}
	
	private static void drawWholeTreeToExcel(
			String title,
			List<FightResult> fightResults,
			HSSFWorkbook book,
			HSSFSheet sheet
	) throws IOException {
		
		sheet.setDefaultColumnWidth(ROW_WIDTH);
		
		
		HSSFCellStyle headerStyle = createHeaderCellStyle(book, new HSSFColor.WHITE());
		HSSFRow headerRow = sheet.createRow(TITLE_Y_POS);
		headerRow.setHeightInPoints(ROW_HEIGHT);
		HSSFCellUtil.createCell(headerRow, 2, title, headerStyle);

		
		drawOlympicNet(fightResults, book, sheet);
		
		HSSFCellStyle redStyle = createCellStyle(book, new HSSFColor.RED());
		HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE());
		
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
		
		HSSFCellStyle redStyle = createCellStyle(book, new HSSFColor.RED());
		HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE());
		HSSFCellStyle lineStyle = createLineCellStyle(book, new HSSFColor.WHITE());
		
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

	private static HSSFCellStyle createLineCellStyle(HSSFWorkbook book, HSSFColor color) {
		HSSFCellStyle rowStyle = createStyle(book, 8, true, HSSFCellStyle.ALIGN_CENTER, color.getIndex());
		rowStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
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
	
	private static HSSFCellStyle createCellStyle(HSSFWorkbook book, HSSFColor color) {
		HSSFCellStyle rowStyle = createStyle(book, createFont(book, 8, true), HSSFCellStyle.ALIGN_LEFT, color.getIndex());
		rowStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		return rowStyle;
	}

	private static HSSFCellStyle createHeaderCellStyle(HSSFWorkbook book, HSSFColor color) {
		HSSFCellStyle rowStyle = createStyle(book, 12, true, HSSFCellStyle.ALIGN_LEFT, color.getIndex());
		return rowStyle;
	}


}
