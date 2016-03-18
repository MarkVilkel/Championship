/**
 * The file KASLabel.java was created on 2009.9.3 at 16:25:32
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.Font;
import javax.swing.JLabel;

public class KASLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private boolean underline = false;
	private String txt;

	public KASLabel() {
		this("");
	}
	
	public KASLabel(String text) {
		super(text);
	}
	
	public KASLabel(String text, boolean bold) {
		this(text);
		if (bold) {
			setBold();
		}
	}
	
	public void setBold() {
		Font font = getFont();
		setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
	}
	
	public void setUnderline(boolean flag) {
		underline = flag;
		setText(getText());
	}
	
	public boolean getUnderline() {
		return underline;
	}
	
	public String getNotFormattedText(){
		if (underline) {
			return txt;
		}
		else {
			return getText();
		}
	}
	
	public void setText(String text){
		txt = text;
		if (underline) {
			super.setText("<html><u>"+text+"</u></html>");
		} 
		else {
			super.setText(text);
		}
	}

	public void setItalic() {
		Font font = getFont();
		setFont(new Font(font.getName(), Font.ITALIC, font.getSize()));
	}
}