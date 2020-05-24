/**
 * The file FightersSearchFrame.java was created on 2020.21.3 at 21:06:38
 * by
 * @author Mark Vilkel
 */
package com.ashihara.ui.app.plan;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.ui.app.plan.model.ChampionshipPlanEditModelUI;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;

public class ChampionshipPlanEditFrame extends AKIdentifiedDockable<Long> {

	private static final long serialVersionUID = 1L;
	
	private final ChampionshipPlan plan;
	
	private ChampionshipPlanEditModelUI championshipPlanEditModelUI;

	public ChampionshipPlanEditFrame(ChampionshipPlan plan) {
		super(uic.PLAN_FOR() + " '" + plan.getChampionship().getName() + "' " + uic.FORMAT_DATE(plan.getChampionship().getBeginningDate()), plan.getId());
		
		this.plan = plan;
		
		getMainPanel().add(getChampionshipPlanEditModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public ChampionshipPlanEditModelUI getChampionshipPlanEditModelUI() {
		if (championshipPlanEditModelUI == null) {
			championshipPlanEditModelUI = new ChampionshipPlanEditModelUI(plan);
		}
		return championshipPlanEditModelUI;
	}

}
