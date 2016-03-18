/**
 * The file DateCellRenderer.java was created on 2007.23.10 at 23:26:34
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;

public class DateCellRenderer extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;
	private UIC uic = ApplicationManager.getInstance().getUic();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof Date){
			Date date = (Date)value;
			value = uic.FORMAT_DATE(date);
		}
		
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		return comp;
	}
}