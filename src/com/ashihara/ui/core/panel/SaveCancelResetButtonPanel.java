/**
 * The file OkCancelButtonPanel.java was created on 2007.29.8 at 13:57:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ashihara.ui.tools.UIFactory;

public class SaveCancelResetButtonPanel extends KASPanel{
	private static final long serialVersionUID = 1L;
	
	protected JButton btnSave, btnCancel, btnReset;
	protected final ActionListener saveBtnActionListener, cancelBtnActionListener, resetBtnActionListener;
	
	public SaveCancelResetButtonPanel(ActionListener saveBtnActionListener, ActionListener cancelBtnActionListener, ActionListener resetBtnActionListener){
		this(saveBtnActionListener, cancelBtnActionListener, resetBtnActionListener, (Component[])null);
	}
	
	public SaveCancelResetButtonPanel(ActionListener saveBtnActionListener,
			ActionListener cancelBtnActionListener,
			ActionListener resetBtnActionListener, Component... components) {
		
		super();
		this.saveBtnActionListener = saveBtnActionListener;
		this.cancelBtnActionListener = cancelBtnActionListener;
		this.resetBtnActionListener = resetBtnActionListener;
		init(components);
	}
	
	protected void init(Component ... components) {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		if (components != null && components.length > 0) { 
			for (Component c : components) {
				add(c);
			}
		}
		
		add(getBtnSave());
		add(getBtnReset());
		add(getBtnCancel());
	}
	
	public JButton getBtnSave() {
		if (btnSave == null){
			btnSave = UIFactory.createSmallSaveButton();
			if (saveBtnActionListener != null) {
				btnSave.addActionListener(saveBtnActionListener);
			}
		}
		return btnSave;
	}
	
	public JButton getBtnCancel() {
		if (btnCancel == null){
			btnCancel = UIFactory.createSmallCancelButton();
			if (cancelBtnActionListener != null) {
				btnCancel.addActionListener(cancelBtnActionListener);
			}
		}
		return btnCancel;
	}

	public JButton getBtnReset() {
		if (btnReset == null){
			btnReset = UIFactory.createSmallResetButton();
			if (resetBtnActionListener != null) {
				btnReset.addActionListener(resetBtnActionListener);
			}
		}
		return btnReset;
	}
}
