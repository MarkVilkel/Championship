/**
 * @version 1.20 1999-09-26
 * @author Cay Horstmann
 */
package com.ashihara.ui.core.component.textField;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyVetoException;
import java.io.Serializable;

import javax.swing.text.Document;

import com.ashihara.ui.core.document.AbstractNumberDocument;

public abstract class NumberOnlyAbstractTextField<T extends Number> extends KASTextField implements Serializable {
	private T lastValue;
	
	private static final int XMINSIZE = 50;
	private static final int YMINSIZE = 20;

	public NumberOnlyAbstractTextField() {
		this(0, 10);
	}
	
	
	protected abstract T getOldValue(T value);
	protected abstract T getNewValue(T value);
	protected abstract T getNewValueFromInt(int value);
	protected abstract T parseToNumberFromText();
	protected abstract AbstractNumberDocument createNumberDocument();
	public abstract T parse(Object value);
	

	public NumberOnlyAbstractTextField(int defval, int size) {
		super("" + defval, size);
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
				if (!event.isTemporary()) {
					lastValue = parseToNumberFromText();
				}
			}
			
			public void focusLost(FocusEvent event) {
				if (!event.isTemporary()) {
					editComplete();
				}
			}
		});
	}
	
	public void editComplete() {
		T oldValue = getOldValue(lastValue);
		T newValue = getNewValue(parseToNumberFromText());
		try {
			fireVetoableChange("value", oldValue, newValue);
			// survived, therefore no veto
			firePropertyChange("value", oldValue, newValue);
		} catch (PropertyVetoException e) { // someone didn't like it
//			JOptionPane.showMessageDialog(this, "" + e, "Input Error", JOptionPane.WARNING_MESSAGE);
			setText("" + lastValue);
			requestFocus();
			// doesn't work in all JDK versions--see bug #4128659
		}
	}
	
	public void setValue(int v) throws PropertyVetoException {
		T oldValue = getOldValue(parseToNumberFromText());
		T newValue = getNewValueFromInt(v);
		fireVetoableChange("value", oldValue, newValue);
		// survived, therefore no veto
		setText("" + v);
		firePropertyChange("value", oldValue, newValue);
	}
	
	protected Document createDefaultModel() {
		return createNumberDocument();
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(XMINSIZE, YMINSIZE);
	}	
}