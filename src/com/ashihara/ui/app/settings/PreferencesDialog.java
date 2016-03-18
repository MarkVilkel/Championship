/**
 * The file PreferencesDialog.java was created on 2008.22.9 at 10:02:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.ashihara.ui.app.settings.model.PreferencesModelUI;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class PreferencesDialog extends KASDialog {
	private static final long serialVersionUID = 1L;
	
	private PreferencesModelUI preferencesModelUI;

	public PreferencesDialog() {
		super(ApplicationManager.getInstance().getUic().LOOK_AND_FEEL());
		
		getMainPanel().add(getPreferencesModelUI().getViewUI(), BorderLayout.CENTER);
		
		UIFactory.sizeCenterVisible(this, new Dimension(300, 200));
	}

	public PreferencesModelUI getPreferencesModelUI() {
		if (preferencesModelUI == null){
			preferencesModelUI = new PreferencesModelUI();
		}
		return preferencesModelUI;
	}
}