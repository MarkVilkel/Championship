/**
 * The file GenderRenderer.java was created on 2010.8.12 at 23:04:49
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.view;

import java.awt.Component;

import javax.swing.JTable;

import com.ashihara.enums.SC;
import com.ashihara.ui.core.renderer.KASDefaultRenderer;

public class GenderRenderer extends KASDefaultRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (SC.GENDER.FEMALE.equals(value)) {
			value = new SC.GENDER().getUICaption(value.toString(), uic); 
		}
		else if (SC.GENDER.MALE.equals(value)) {
			value = new SC.GENDER().getUICaption(value.toString(), uic); 
		}
		else if (SC.GENDER.MIXED.equals(value)) {
			value = new SC.GENDER().getUICaption(value.toString(), uic); 
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
