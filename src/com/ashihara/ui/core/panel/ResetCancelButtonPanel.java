/**
 * The file ResetCancelButtonPanel.java was created on 2009.5.8 at 13:32:09
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Component;
import java.awt.event.ActionListener;


public class ResetCancelButtonPanel extends SaveCancelResetButtonPanel {
	private static final long serialVersionUID = 1L;
	
	public ResetCancelButtonPanel(ActionListener cancelBtnActionListener, ActionListener resetBtnActionListener) {
		super(null, cancelBtnActionListener, resetBtnActionListener);
	}
	
	protected void init(Component ... components) {
		super.init(components);
		getBtnSave().setVisible(false);
	}

}
