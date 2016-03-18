/**
 * The file AutoCompleteComboBox.java was created on 2009.3.11 at 11:46:16
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.combo;

import com.ashihara.ui.core.component.JComboBoxAutocompleteWorkingBackspace;

public class AutoCompleteComboBox<T extends Object> extends KASComboBox {
	private static final long serialVersionUID = 1L;
	
	private final Class<T> returnType;

	public AutoCompleteComboBox(Class<T> returnType) {
		super();
		
		this.returnType = returnType;
		
		new JComboBoxAutocompleteWorkingBackspace(this);
		setEditable(true);
	}

	public Class<T> getReturnType() {
		return returnType;
	}
	
	public Object validateSelectedValue() {
		Object selectedItem = getSelectedItem();
		
		if (selectedItem instanceof ComboBoxItem) {
			return null;
		}
		else if (returnType == ComboBoxItem.class && selectedItem != null) {
			for (Object o : getAllItems()) {
				ComboBoxItem cmi = (ComboBoxItem) o;
				
				if (cmi != null && selectedItem.equals(cmi.getUiCaption())) {
					return null;
				}
			}
		}
		else if (selectedItem != null && selectedItem.getClass() == returnType) {
			return null;
		}
		else if (selectedItem == null) {
			return null;
		}
		
		return selectedItem;
	}
}
