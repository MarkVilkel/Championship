/**
 * The file IntegerOnlyTableCellEditor.java was created on 2009.2.11 at 23:42:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import com.ashihara.ui.core.component.textField.IntegerOnlyTextField;

public class IntegerOnlyTableCellEditor extends NumberOnlyAbstractTableCellEditor<IntegerOnlyTextField> {
	private static final long serialVersionUID = 1L;

	public IntegerOnlyTableCellEditor() {
		super(new IntegerOnlyTextField());
	}
}