/**
 * The file AuthorizationDialog.java was created on 2007.28.8 at 23:15:07
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.auth;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.ashihara.enums.UIC;
import com.ashihara.ui.app.auth.model.AuthorizationModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class AuthorizationDialog extends KASDialog implements UIStatePerformer<UIC>{
	private static final long serialVersionUID = 1L;
	
	private AuthorizationModelUI authorizationModelUI;
	
	public AuthorizationDialog(DialogCallBackListener dialogCallBackListener) {
		super("", dialogCallBackListener);
		performUIState(ApplicationManager.getInstance().getUic());
		init();
	}

	private void init() {
		getMainPanel().setLayout(new BorderLayout());
		getMainPanel().add(getAuthorizationModelUI().getViewUI(),BorderLayout.CENTER);
		
//		UIFactory.sizeCenterVisible(this, new Dimension(400,300));
		pack();
		UIFactory.centerVisible(this);
	}

	public AuthorizationModelUI getAuthorizationModelUI() {
		if (authorizationModelUI == null){
			authorizationModelUI = new AuthorizationModelUI(this);
		}
		return authorizationModelUI;
	}

	public void performUIState(UIC uic) {
		setTitle(uic.AUTHENTIFICATION());
	}
}