/**
 * The file FightingGroupSearchDialog.java was created on 13 февр. 2020 г. at 21:54:22
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.ui.app.plan.model.FightingGroupSearchModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class FightingGroupSearchDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private ChampionshipPlan plan;
	private FightingGroupSearchModelUI planModelUI;
	

	public FightingGroupSearchDialog(ChampionshipPlan plan, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().SELECT_GROUPS(), dialogCallBackListener);
		
		this.plan = plan;
		
		getMainPanel().add(getFighterDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		setResizable(true);
		pack();
		UIFactory.centerVisible(this);
	}

	public FightingGroupSearchModelUI getFighterDetailsModelUI() {
		if (planModelUI == null) {
			planModelUI = new FightingGroupSearchModelUI(plan);
		}
		return planModelUI;
	}
	
}
