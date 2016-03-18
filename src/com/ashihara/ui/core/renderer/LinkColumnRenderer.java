/**
 * The file LinkColumnRenderer.java was created on 2007.1.9 at 18:24:50
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JTable;

public class LinkColumnRenderer extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;
	
	private boolean linkActive = true;
	private Cursor linkCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		value = value == null ? "" : value;
		String val = "";
		if (isLinkActive()) {
			val = "<html><u>"+value+"</html></u>";
		}
		else {
			val = String.valueOf(value);
		}
		Component comp = super.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, column);
		return comp;
	}
	
	public boolean isLinkActive() {
		return linkActive;
	}

	public void setLinkActive(boolean linkActive) {
		this.linkActive = linkActive;
	}

	public Cursor getLinkCursor() {
		return linkCursor;
	}

	public void setLinkCursor(Cursor linkCursor) {
		this.linkCursor = linkCursor;
	}
}
