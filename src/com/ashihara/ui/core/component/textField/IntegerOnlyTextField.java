/**
 * The file IntegerOnlyTextField.java was created on 2009.2.11 at 23:26:07
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.textField;

import com.ashihara.ui.core.document.AbstractNumberDocument;
import com.ashihara.ui.core.document.IntegerTextDocument;

public class IntegerOnlyTextField extends NumberOnlyAbstractTextField<Integer> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractNumberDocument createNumberDocument() {
		return new IntegerTextDocument();
	}

	@Override
	protected Integer getNewValue(Integer value) {
		return new Integer(value);
	}

	@Override
	protected Integer getNewValueFromInt(int value) {
		return new Integer(value);
	}

	@Override
	protected Integer getOldValue(Integer value) {
		return new Integer(value);
	}

	@Override
	protected Integer parseToNumberFromText() {
		try {
			return parse(getText());
		} catch (NumberFormatException exception) {
			return 0;
		}
	}

	@Override
	public Integer parse(Object value) {
		return Integer.parseInt(String.valueOf(value));
	}
}
