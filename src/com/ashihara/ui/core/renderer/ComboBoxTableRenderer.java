/**
 * The file ComboBoxTableRenderer.java was created on 2009.2.6 at 22:40:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;

public class ComboBoxTableRenderer extends KASDefaultRenderer {
	private static final long serialVersionUID = 1L;
	private JComboBox comboBox;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		getComboBox().setBackground(c.getBackground());
		return getComboBox();
	}

	public JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
		}
		return comboBox;
	}
}
