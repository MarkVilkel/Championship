/**
 * The file MillisTableRenderer.java was created on 2008.8.5 at 17:17:40
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JTable;

import com.ashihara.utils.DateTimeUtils;

public class MillisTableRenderer extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof Long){
			Long time = (Long)value;
			value = DateTimeUtils.convertMillis(time, uic);
		}
		
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		return comp;
	}
}
