/**
 * The file RadioButtonTableRenderer.java was created on 2009.9.3 at 14:56:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JRadioButton;
import javax.swing.JTable;

public class RadioButtonTableRenderer extends KASDefaultRenderer {
	private static final long serialVersionUID = 1L;
	private JRadioButton radioButton;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof Boolean){
			getRadioButton().setSelected((Boolean)value);
		}
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		getRadioButton().setBackground(c.getBackground());
		return getRadioButton();
	}
	
	public JRadioButton getRadioButton() {
		if (radioButton == null) {
			radioButton = new JRadioButton();
			radioButton.setHorizontalAlignment(JRadioButton.CENTER);
		}
		return radioButton;
	}
}
