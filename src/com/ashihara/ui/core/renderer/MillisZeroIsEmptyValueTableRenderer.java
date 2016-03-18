/**
 * The file MillisZeroIsEmptyValueTableRenderer.java was created on 2009.2.11 at 23:57:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import javax.swing.JTable;

public class MillisZeroIsEmptyValueTableRenderer extends MillisTableRenderer {
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		
		if (value instanceof Long){
			Long time = (Long)value;
			if (time == null || time == 0){
				value = "";
			}
		}
		
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		return comp;
	}

}
