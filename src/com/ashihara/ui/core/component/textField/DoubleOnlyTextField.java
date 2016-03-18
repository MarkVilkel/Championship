/**
 * The file DoubleOnlyTextField.java was created on 2009.2.11 at 22:32:29
 * by
 * @author Marks Vilkelis.
 */

package com.ashihara.ui.core.component.textField;

import com.ashihara.ui.core.document.AbstractNumberDocument;
import com.ashihara.ui.core.document.DoubleTextDocument;

public class DoubleOnlyTextField extends NumberOnlyAbstractTextField<Double> {
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractNumberDocument createNumberDocument() {
		return new DoubleTextDocument();
	}

	@Override
	protected Double getNewValue(Double value) {
		return new Double(value);
	}

	@Override
	protected Double getNewValueFromInt(int value) {
		return new Double(value);
	}

	@Override
	protected Double getOldValue(Double value) {
		return new Double(value);
	}

	@Override
	protected Double parseToNumberFromText() {
		try {
			return parse(getText());
		} catch (NumberFormatException exception) {
			return 0d;
		}
	}

	@Override
	public Double parse(Object value) {
		return Double.parseDouble(String.valueOf(value));
	}
}