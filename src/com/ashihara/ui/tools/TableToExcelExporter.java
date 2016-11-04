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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.enums.UIC;
import com.ashihara.ui.core.file.ExcelFileFilter;
import com.ashihara.ui.core.renderer.KASTableCellRenderer;
import com.ashihara.utils.FileUtils;

public class TableToExcelExporter {
	
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
		
        HSSFCellStyle tableHeaderStyle = createStyle(book, 12, true, HSSFCellStyle.ALIGN_CENTER); 
        HSSFCellStyle rowStyle = createStyle(book, 10, false, HSSFCellStyle.ALIGN_LEFT);

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
	
    private static HSSFCellStyle createStyle(HSSFWorkbook wb, int fontSize, boolean fontBold, short alignment){ 
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setBoldweight(fontBold ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
        HSSFCellStyle style = wb.createCellStyle();
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

	public static void drawWholeTreeToExcel(List<FightResult> fightResults, List<GroupChampionshipFighter> fighters, String initialFileName, UIC uic) throws IOException {
		if (initialFileName.length() > 0) {
			initialFileName += " ";
		}
		
		initialFileName += uic.FORMAT_DATE(new Date());
		
		
		File file = FileUtils.getSavePath(new ExcelFileFilter(), initialFileName);
		
		if (file != null) {
			drawWholeTreeToExcel(fightResults, fighters, file);
		}
	}

	private static void drawWholeTreeToExcel(List<FightResult> fightResults, List<GroupChampionshipFighter> fighters, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		sheet.setDefaultColumnWidth((short)30);
		
		drawOlympicNet(fightResults, book, sheet);
		
		
		for (FightResult fr : fightResults) {
			HSSFCellStyle redStyle = createCellStyle(book, new HSSFColor.RED());
			HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE());
			
			int x = fr.getOlympicLevel().intValue();
			int y = fr.getOlympicPositionOnLevel().intValue();
			
			int theFirstOffset = Math.max(0, (int)(Math.pow(2, x)) - 1);
			int distance = (int)(Math.pow(2, x + 1));
			
			HSSFRow redRow = sheet.createRow((y * 2 * distance) + theFirstOffset);
			redRow.setHeightInPoints(25);

			HSSFRow whiteRow = sheet.createRow((y * 2 * distance) + distance + theFirstOffset);
			whiteRow.setHeightInPoints(25);

			if (fr.getRedFighter() != null) {
				HSSFCellUtil.createCell(redRow, x, toString(fr.getRedFighter()), redStyle);
			}
			
			if (fr.getBlueFighter() != null) {
				HSSFCellUtil.createCell(whiteRow, x, toString(fr.getBlueFighter()), whiteStyle);
			}
		}
		
        book.write(fileOut);
        fileOut.close();
        openFileWithSystemEditor(file);
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
				if (fr.getFirstFighter() != null) {
					secondLevelFightersCount ++;
				}
				if (fr.getSecondFighter() != null) {
					secondLevelFightersCount ++;
				}
			}
		}
		
		int barierCount = 2;
		for (; !(barierCount > firstLevelFightersCount && barierCount > secondLevelFightersCount); barierCount *= 2) {}
		
		int x = 0;
		for (int maxY = barierCount; maxY > 0; maxY /= 2) {
			int fightersCount = 0;
			for (int y = 0; y < maxY; y ++) {
				if (x == 0 && fightersCount >= firstLevelFightersCount) {
					break;
				}
				
				int theFirstOffset = Math.max(0, (int)(Math.pow(2, x)) - 1);
				int distance = (int)(Math.pow(2, x + 1));
				
				HSSFCellStyle redStyle = createCellStyle(book, new HSSFColor.WHITE());
				HSSFRow redRow = sheet.createRow((y * 2 * distance) + theFirstOffset);
				redRow.setHeightInPoints(25);
				HSSFCellUtil.createCell(redRow, x, "", redStyle);
				fightersCount ++;
				
				if (x == 0 && fightersCount >= firstLevelFightersCount) {
					break;
				}
				
				HSSFCellStyle whiteStyle = createCellStyle(book, new HSSFColor.WHITE());
				HSSFRow whiteRow = sheet.createRow((y * 2 * distance) + distance + theFirstOffset);
				whiteRow.setHeightInPoints(25);
				HSSFCellUtil.createCell(whiteRow, x, "", whiteStyle);
				fightersCount ++;
				
				
			}
			x++;
		}
	}

	private static String toString(GroupChampionshipFighter f) {
		if (f == null || f.getChampionshipFighter() == null) {
			return "";
		}
		return f.getChampionshipFighter().toString();
	}
	
	private static HSSFCellStyle createCellStyle(HSSFWorkbook book, HSSFColor color) {
		HSSFCellStyle rowStyle = createStyle(book, 8, true, HSSFCellStyle.ALIGN_CENTER, color.getIndex());
		rowStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		rowStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		return rowStyle;
	}
	
    private static HSSFCellStyle createStyle(
    		HSSFWorkbook wb,
    		int fontSize,
    		boolean fontBold,
    		short alignment,
    		short backgroung
    ) { 
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setBoldweight(fontBold ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontName(HSSFFont.FONT_ARIAL);
        
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(backgroung);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        style.setFont(font);
        style.setAlignment(alignment);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

}
