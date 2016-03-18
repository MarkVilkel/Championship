/**
 * @version 1.20 1999-09-26
 * @author Cay Horstmann
 */
package com.ashihara.ui.core.component.textField;

import com.ashihara.ui.core.document.AbstractNumberDocument;
import com.ashihara.ui.core.document.LongTextDocument;

public class LongOnlyTextField extends NumberOnlyAbstractTextField<Long> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractNumberDocument createNumberDocument() {
		return new LongTextDocument();
	}

	@Override
	protected Long getNewValue(Long value) {
		return new Long(value);
	}

	@Override
	protected Long getNewValueFromInt(int value) {
		return new Long(value);
	}

	@Override
	protected Long getOldValue(Long value) {
		return new Long(value);
	}

	@Override
	protected Long parseToNumberFromText() {
		try {
			return parse(getText());
		} catch (NumberFormatException exception) {
			return 0l;
		}
	}

	@Override
	public Long parse(Object value) {
		return Long.parseLong(String.valueOf(value));
	}
}