/**
 * The file NumberOnlyAbstractTableCellEditor.java was created on 2009.2.11 at 23:29:49
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.SwingConstants;

import com.ashihara.ui.core.component.textField.NumberOnlyAbstractTextField;

public class NumberOnlyAbstractTableCellEditor<T extends NumberOnlyAbstractTextField> extends DefaultCellEditor implements KASTableCellEditor {
	private static final long serialVersionUID = 1L;

	public NumberOnlyAbstractTableCellEditor(T textField) {
		super(textField);
		
		getEditorComponent().setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
    public Object getCellEditorValue() {
    	Object value = super.getCellEditorValue();
    	try {
    		return getEditorComponent().parse(value);
    	}
    	catch (Throwable t) {
    		return null;
    	}
    }
    

	public boolean stopCellEditing() {
    	if (getCellEditorValue() == null) {
    		return false;
    	}
    	return super.stopCellEditing();
    }
	
	public T getEditorComponent() {
		return ((T)getComponent());
	}
}