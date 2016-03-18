/**
 * The file KASLinkLabel was created on 2008.13.5 at 17:18:32
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class KASLinkLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private List<ActionListener> actionListeners;
	
	private Color inactiveColor = Color.black;
	private Color activeColor = Color.red;
	private Cursor initialCursor;
	
	public KASLinkLabel(String caption){
		super("<html><u>"+caption+"</u></html>");
		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				
			}
			
			public void mouseEntered(MouseEvent arg0) {
				KASLinkLabel.this.setForeground(getActiveColor());
				setInitialCursor(KASLinkLabel.this.getCursor());
				KASLinkLabel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent arg0) {
				KASLinkLabel.this.setForeground(getInactiveColor());
				KASLinkLabel.this.setCursor(getInitialCursor());
			}
			
			public void mousePressed(MouseEvent arg0) {
				KASLinkLabel.this.setForeground(getInactiveColor());
				fireActionPerformed();
			}
			
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
	}
	
	public void setText(String text){
		super.setText(formatText(text));
	}

	private String formatText(String text) {
		return "<html><u>"+text+"</u></html>";
	}

	public void addActionListener(ActionListener listener){
		getActionListeners().add(listener);
	}
	
	public void removeActionListener(ActionListener listener){
		getActionListeners().remove(listener);
	}
	
	public void fireActionPerformed(){
		for (ActionListener al : getActionListeners())
			al.actionPerformed(null);
	}
	
	public List<ActionListener> getActionListeners() {
		if (actionListeners == null){
			actionListeners = new ArrayList<ActionListener>();
		}
		return actionListeners;
	}

	public Color getActiveColor() {
		return activeColor;
	}

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}

	public Color getInactiveColor() {
		return inactiveColor;
	}

	public void setInactiveColor(Color inactiveColor) {
		this.inactiveColor = inactiveColor;
		setForeground(inactiveColor);
	}

	protected Cursor getInitialCursor() {
		return initialCursor;
	}

	protected void setInitialCursor(Cursor initialCursor) {
		this.initialCursor = initialCursor;
	}
	
	public void setBold(){
		Font font = getFont();
		setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
	}
}

