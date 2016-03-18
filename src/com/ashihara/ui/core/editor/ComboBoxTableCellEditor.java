/**
 * The file ComboBoxTableCellEditor.java was created on 14:14:48 at 2007.19.9
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

import com.ashihara.ui.core.component.combo.ComboBoxItem;

public class ComboBoxTableCellEditor extends DefaultCellEditor implements KASTableCellEditor{
	private static final long serialVersionUID = 1L;
	
	public ComboBoxTableCellEditor(JComboBox cmb) {
		super(cmb);
		setClickCountToStart(2);
	}
	
	public ComboBoxTableCellEditor() {
		this(new JComboBox());
	}
	
	public JComboBox getCombo() {
		return (JComboBox)getComponent();
	}
	
	public Object getCellEditorValue() {
		Object value = super.getCellEditorValue();
		
		if (value instanceof ComboBoxItem) {
			ComboBoxItem cbItem = (ComboBoxItem) value;
			return cbItem.getId();
		}
		
		return value;
	}
}
