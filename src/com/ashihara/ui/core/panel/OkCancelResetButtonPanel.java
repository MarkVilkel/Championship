/**
 * The file OkCancelResetButtonPanel was created on 2008.23.7 at 14:26:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ashihara.ui.tools.UIFactory;

public class OkCancelResetButtonPanel extends SaveCancelResetButtonPanel{
	private static final long serialVersionUID = 1L;

	public OkCancelResetButtonPanel(ActionListener saveBtnActionListener, ActionListener cancelBtnActionListener, ActionListener resetBtnActionListener) {
		super(saveBtnActionListener, cancelBtnActionListener, resetBtnActionListener);
	}

	public JButton getBtnSave() {
		if (btnSave == null){
			btnSave = UIFactory.createSmallOkButton();
			btnSave.addActionListener(saveBtnActionListener);
		}
		return btnSave;
	}
}
