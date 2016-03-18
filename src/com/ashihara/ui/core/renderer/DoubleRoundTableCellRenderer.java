/**
 * The file DoubleRoundTableCellRenderer.java was created on 2007.4.11 at 14:36:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;

import javax.swing.JTable;

import com.ashihara.utils.MathUtils;

public class DoubleRoundTableCellRenderer extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;
	private final int decimalPlace;
	
	public DoubleRoundTableCellRenderer(int decimalPlace){
		this.decimalPlace = decimalPlace;
	}
	public DoubleRoundTableCellRenderer(){
		this(2);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof Double){
			Double doubl  = (Double)value;
			value = MathUtils.round(doubl, decimalPlace);
		}
		
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		return comp;
	}
}