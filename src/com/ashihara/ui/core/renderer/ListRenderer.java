/**
 * The file ListRenderer.java was created on 2009.29.11 at 21:51:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;

public class ListRenderer extends KASDefaultRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof List) {
			List list = (List) value;
			if (list.isEmpty()) {
				value = "";
			}
		}
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		return comp;
	}
}
