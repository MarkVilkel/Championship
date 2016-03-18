/**
 * The file AlwaysSelectedDemoCheckBoxTableRenderer.java was created on 2009.6.3 at 12:11:47
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JTable;

public class AlwaysSelectedDemoCheckBoxTableRenderer extends CheckBoxTableRenderer {
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		getCheckBox().setSelected(true);
		return c;
	}

}
