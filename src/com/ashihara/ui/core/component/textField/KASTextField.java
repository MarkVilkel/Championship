/**
 * The file KASTextField.java was created on 2008.1.10 at 16:59:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.textField;

import javax.swing.JTextField;
import javax.swing.text.Document;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.document.MaxSymbolsDocument;
import com.ashihara.ui.tools.ApplicationManager;

public class KASTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private MaxSymbolsDocument maxSymbolsDocument;
	
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	public KASTextField(){
		super();
	}

	public KASTextField(String text, int columns){
		super(text, columns);
	}
	
    protected Document createDefaultModel() {
        return getMaxSymbolsDocument();
    }

	public int getMaxSymbolsCount() {
		return getMaxSymbolsDocument().getMaxSymbolsCount();
	}

	public void setMaxSymbolsCount(int maxSymbolsCount) {
		getMaxSymbolsDocument().setMaxSymbolsCount(maxSymbolsCount);
	}

	public MaxSymbolsDocument getMaxSymbolsDocument() {
		if (maxSymbolsDocument == null) {
			maxSymbolsDocument = new MaxSymbolsDocument(uic);
		}
		return maxSymbolsDocument;
	}
}
