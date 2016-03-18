package com.ashihara.ui.tools;

public interface IExcelable {
	
	int getColumnCount();
	int getRowCount();

	String getHeader(int column);
	String getCell(int row, int column);

	
}
