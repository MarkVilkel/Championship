/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan.model;

import java.util.List;

import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.ui.app.plan.view.FightingGroupSearchPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;

public class FightingGroupSearchModelUI extends AKAbstractModelUI<FightingGroupSearchPanelViewUI> implements IFightingGroupSearchModelUI<FightingGroupSearchPanelViewUI>{
	private ChampionshipPlan plan;
	private FightingGroupSearchPanelViewUI viewUI;
	
	public FightingGroupSearchModelUI(ChampionshipPlan plan) {
		this.plan = plan;
		viewUI = new FightingGroupSearchPanelViewUI(AKUIEventSender.newInstance(this));
		getViewUI().getModelUI().reload();
	}
	
	@Override
	public FightingGroupSearchPanelViewUI getViewUI() {
		return viewUI;
	}
	
	@Override
	public void setViewUI(FightingGroupSearchPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void ok() {
		try {
			List<FightingGroup> groups = getViewUI().getGroupTable().getTable().getSelectedObjects();
			getChampionshipPlanService().addToPlan(plan, groups);
			cancel();
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void reload() {
		try {
			List<FightingGroup> groups = getChampionshipPlanService().loadGroupsForAddingToPlan(plan);
			getViewUI().getGroupTable().getTable().getKASModel().setDataRows(groups);
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

}
