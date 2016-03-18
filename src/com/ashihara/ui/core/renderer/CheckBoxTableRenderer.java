/**
 * The file CheckBoxTableRenderer.java was created on 15:00:20 at 2007.18.10
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckBoxTableRenderer extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;
	
	private JCheckBox checkBox;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof Boolean){
			getCheckBox().setSelected((Boolean)value);
		}
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		getCheckBox().setBackground(c.getBackground());
		return getCheckBox();
	}
	public JCheckBox getCheckBox() {
		if (checkBox == null){
			checkBox = new JCheckBox();
			checkBox.setHorizontalAlignment(JCheckBox.CENTER);
		}
		return checkBox;
	}
}
