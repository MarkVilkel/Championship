/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.ui.app.fighter.model.FighterDetailsModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class FighterDetailsDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private Fighter fighter;
	private FighterDetailsModelUI fighterDetailsModelUI;

	public FighterDetailsDialog(Fighter fighter, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().FIGHTER(), dialogCallBackListener);
		
		this.fighter = fighter;
		
		getMainPanel().add(getFighterDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		pack();
		UIFactory.centerVisible(this);

	}

	public FighterDetailsModelUI getFighterDetailsModelUI() {
		if (fighterDetailsModelUI == null) {
			fighterDetailsModelUI = new FighterDetailsModelUI(fighter);
		}
		return fighterDetailsModelUI;
	}

}
