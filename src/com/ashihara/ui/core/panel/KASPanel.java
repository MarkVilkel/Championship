/**
 * The file KASPanel.java was created on 2008.5.1 at 16:51:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;

public class KASPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	protected final int DEFAULT_CONTROLS_LENGTH = 150;
	protected final int MEDIUM_CONTROLS_LENGTH = 200;
	protected final int LONG_CONTROLS_LENGTH = 250;
	
	public KASPanel(){
		this(new BorderLayout());
	}
	
	public KASPanel(LayoutManager layout) {
		super(layout);
	}

	public KASPanel(LayoutManager layout, Component c) {
		super(layout);
		add(c);
	}

	private void setEnabled(boolean b, Component component){
		if (component instanceof Container){
			if (component != this) {
				component.setEnabled(b);
			}
			if (component instanceof KASPanel && component != this) {
				component.setEnabled(b);
			}
			else {
				for (Component c : ((Container)component).getComponents()) {
					setEnabled(b,c);
				}
			}
		}
		else if (component != null){
			component.setEnabled(b);
		}
	}
	
	@Override
	public void setEnabled(boolean b){
		setEnabled(b, this);
		super.setEnabled(b);
	}
	
	public void disposeParent(){
		ApplicationManager.getInstance().disposeParent(this);
	}
	
	public Component getParentWindowOrFrame(){
		return ApplicationManager.getParentWindowOrFrame(this);
	}
	
	protected ActionListener getDisposeParentAl(){
		ActionListener AL = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				disposeParent();
			}
		};
		return AL;
	}
	
	protected class EnterEscapeKeyAdapter extends KeyAdapter{
		private final JButton btnOk, btnCancel;
		private final ActionListener alOk, alCancel;
		public EnterEscapeKeyAdapter(JButton btnOk, JButton btnCancel){
			super();
			this.btnCancel = btnCancel;
			this.btnOk = btnOk;
			this.alCancel = null;
			this.alOk = null;
		}
		public EnterEscapeKeyAdapter(ActionListener alOk, ActionListener alCancel){
			super();
			this.btnCancel = null;
			this.btnOk = null;
			this.alCancel = alCancel;
			this.alOk = alOk;
		}
		
		@Override
		public void keyPressed(KeyEvent key) {
			if (key.getKeyCode() ==  KeyEvent.VK_ENTER) {
				if (btnOk != null) {
					btnOk.doClick();
				}
				else {
					alOk.actionPerformed(null);
				}
			}
			else if (key.getKeyCode() ==  KeyEvent.VK_ESCAPE)
				if (btnCancel != null) {
					btnCancel.doClick();
				}
				else {
					alCancel.actionPerformed(null);
				}
		}
	}
}
