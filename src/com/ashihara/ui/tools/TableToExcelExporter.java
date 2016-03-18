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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;

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
}
