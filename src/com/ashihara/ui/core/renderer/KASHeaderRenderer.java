/**
 * The file KASHeaderRenderer.java was created on 2007.8.9 at 19:49:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;

import sun.swing.table.DefaultTableCellHeaderRenderer;

public class KASHeaderRenderer extends DefaultTableCellHeaderRenderer{
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		comp.setPreferredSize(new Dimension(300,30));
		
		comp.setFont(new Font("Serif", Font.BOLD, 15));
		return comp;
	}

}
