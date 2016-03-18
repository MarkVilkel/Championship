/**
 * The file KASDefaultRenderer.java was created on 2007.20.8 at 22:31:59
 * by
 * @author Marks Vilkelis.
 */

package com.ashihara.ui.core.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;

public class KASDefaultRenderer extends KASTableCellRenderer{
	private static final long serialVersionUID = 1L;
	protected UIC uic = ApplicationManager.getInstance().getUic();
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (isSelected) return comp;
		if (!table.isCellEditable(row, column)){
			if (row%2 == 0)
				comp.setBackground(new Color(230,230,230));//Color.lightGray);
			else
				comp.setBackground(new Color(210,210,210));//Color.lightGray);
		}
			
		else  
			comp.setBackground(Color.white);
		
		return comp;
	}
}