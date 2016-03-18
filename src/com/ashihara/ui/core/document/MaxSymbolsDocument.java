/**
 * The file MaxSymbolsDocument.java was created on 2009.2.11 at 22:33:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.MessageHelper;

public class MaxSymbolsDocument extends KasPlainDocument {
	private static final long serialVersionUID = 1L;
	
	private int maxSymbolsCount = -1;
	private final UIC uic;
	
	public MaxSymbolsDocument(UIC uic) {
		super();
		
		this.uic = uic;
	}
	
	public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
		int length = getLength();
		if (str != null) {
			length += str.length();
		}
		
		String s = "";
		if (length > getMaxSymbolsCount() && getMaxSymbolsCount() >= 0) {
			MessageHelper.showErrorMessage(null, uic.TEXT_LENGTH_EXCEEDED_MAXIMAL_SYMBOLS_COUNT()+" = "+getMaxSymbolsCount()+"!");
		}
		else {
			s = str;
		}
		
		super.insertString(offs, s, a);
	}

	public int getMaxSymbolsCount() {
		return maxSymbolsCount;
	}

	public void setMaxSymbolsCount(int maxSymbolsCount) {
		this.maxSymbolsCount = maxSymbolsCount;
	}
	
	public UIC getUic() {
		return uic;
	}
}
