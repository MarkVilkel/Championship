/**
 * The file KASSmallHeaderRenderer.java was created on 14:26:44 at 2007.19.9
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import sun.swing.table.DefaultTableCellHeaderRenderer;

public class KASSmallHeaderRenderer extends DefaultTableCellHeaderRenderer{
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		comp.setPreferredSize(new Dimension(300,20));
		return comp;
	}
}