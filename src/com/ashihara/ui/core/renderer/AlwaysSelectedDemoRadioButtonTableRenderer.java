/**
 * The file AlwaysSelectedDemoRadioButtonTableRenderer.java was created on 2009.9.3 at 14:58:23
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import javax.swing.JTable;

public class AlwaysSelectedDemoRadioButtonTableRenderer extends RadioButtonTableRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		getRadioButton().setSelected(true);
		return c;
	}
}
