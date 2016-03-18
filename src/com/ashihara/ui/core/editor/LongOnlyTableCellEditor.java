/**
 * The file LongOnlyTableCellEditor.java was created on 2009.2.11 at 23:41:44
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import com.ashihara.ui.core.component.textField.LongOnlyTextField;

public class LongOnlyTableCellEditor extends NumberOnlyAbstractTableCellEditor<LongOnlyTextField> {
	private static final long serialVersionUID = 1L;

	public LongOnlyTableCellEditor() {
		super(new LongOnlyTextField());
	}
}