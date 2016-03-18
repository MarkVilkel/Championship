/**
 * The file CheckBoxTableEditor.java was created on 14:57:22 at 2007.18.10
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckBoxTableEditor extends DefaultCellEditor implements KASTableCellEditor{
	private static final long serialVersionUID = 1L;

	public CheckBoxTableEditor() {
		super(new JCheckBox());
		((JCheckBox)getComponent()).setHorizontalAlignment(JCheckBox.CENTER);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean bool, int row, int column){
		Component comp = super.getTableCellEditorComponent(table, value, bool, row, column);
		((JCheckBox)comp).setSelected((Boolean)value);
		return comp;
	}

}
