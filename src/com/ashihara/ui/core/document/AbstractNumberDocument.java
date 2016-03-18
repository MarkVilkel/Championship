/**
 * The file AbstractNumberDocument.java was created on 2009.2.11 at 22:28:29
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public abstract class AbstractNumberDocument extends KasPlainDocument {
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null) {
			return;
		}
		
		String oldString = getText(0, getLength());
		String newString = oldString.substring(0, offs) + str + oldString.substring(offs);
		try {
			parseNumber(newString + "0");
			super.insertString(offs, str, a);
		} catch (NumberFormatException e) {
		}
	}
	
	protected abstract void parseNumber(String str);
}
