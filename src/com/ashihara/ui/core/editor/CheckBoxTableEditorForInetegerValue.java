/**
 * The file CheckBoxTableEditorForInetegerValue.java was created on 2007.27.8 at 17:53:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckBoxTableEditorForInetegerValue extends DefaultCellEditor implements KASTableCellEditor{
	private static final long serialVersionUID = 1L;
	
	private final Integer forTrue, forFalse;
	
	public CheckBoxTableEditorForInetegerValue(Integer forTrue, Integer forFalse){
		super(new JCheckBox());
		((JCheckBox)getComponent()).setHorizontalAlignment(JCheckBox.CENTER);
		this.forTrue = forTrue;
		this.forFalse = forFalse;
	}
	public Object getCellEditorValue(){
		Boolean value = (Boolean)super.getCellEditorValue();
		if (value) 
			return new Integer(forTrue);
		else
			return new Integer(forFalse);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean bool, int row, int column){
		Component comp = super.getTableCellEditorComponent(table, value, bool, row, column);
		if (value.equals(forTrue)){
			((JCheckBox)comp).setSelected(true);
		}
		else {
			((JCheckBox)comp).setSelected(false);
		}
		return comp;
	}
}
