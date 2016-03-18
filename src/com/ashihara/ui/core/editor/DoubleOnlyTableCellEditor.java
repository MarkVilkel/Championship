/**
 * The file DoubleOnlyTableCellEditor.java was created on 2009.1.11 at 17:07:26
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import com.ashihara.ui.core.component.textField.DoubleOnlyTextField;

public class DoubleOnlyTableCellEditor extends NumberOnlyAbstractTableCellEditor<DoubleOnlyTextField> {
	private static final long serialVersionUID = 1L;

	public DoubleOnlyTableCellEditor() {
		super(new DoubleOnlyTextField());
	}
}