/**
 * The file KASJTextArea.java was created on 2008.8.9 at 12:28:01
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;

public class KASJTextArea extends JTextArea{
	private static final long serialVersionUID = 1L;

	private int maxSymbolsCount = -1;
	protected static UIC uic = ApplicationManager.getInstance().getUic();

	
	public KASJTextArea(int i, int j) {
		super(i, j);
		
		init();
	}
	
	public KASJTextArea() {
		super();
		
		init();
	}

	private void init() {
		addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e) {
//				System.out.println("KASJTextArea.this.getParent().dispatchEvent(e);");
//				getParent().getParent().dispatchEvent(e);
			}
		});
	}

	public void setText(String text) {
		super.setText(text);
		setCaretPosition(0);
	}
	
	protected Document createDefaultModel() {
        return new MaxSymbolsDocument();
    }

	private class MaxSymbolsDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
			int length = getLength();
			if (str != null)
				length += str.length();
			
			String s = "";
			if (length > getMaxSymbolsCount() && getMaxSymbolsCount() >= 0)
				MessageHelper.showErrorMessage(null, uic.TEXT_LENGTH_EXCEEDED_MAXIMAL_SYMBOLS_COUNT()+" = "+getMaxSymbolsCount()+"!");
			else
				s = str;
			
			super.insertString(offs, s, a);
		}
	}
	
	public int getMaxSymbolsCount() {
		return maxSymbolsCount;
	}

	public void setMaxSymbolsCount(int maxSymbolsCount) {
		this.maxSymbolsCount = maxSymbolsCount;
	}
	
	public void setBold() {
		Font font = getFont();
		setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
	}
}