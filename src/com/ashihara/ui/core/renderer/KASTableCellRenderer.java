/**
 * The file KASTableCellRenderer.java was created on 2009.5.2 at 13:32:13
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class KASTableCellRenderer extends DefaultTableCellRenderer {
	private Object lastSetValue;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		lastSetValue = value;
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);	
		return comp;
	}
	
	public Object getLastSetValue() {
		return lastSetValue;
	}
}
