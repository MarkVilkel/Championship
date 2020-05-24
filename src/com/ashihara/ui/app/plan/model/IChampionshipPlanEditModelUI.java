/**
 * The file IChampionshipEditModelUI.java was created on 2010.5.4 at 22:18:11
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan.model;

import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IChampionshipPlanEditModelUI<T extends UIView> extends UIModel<T> {

	void cancel();
	void reloadFights();
	
	void onAddGroups();
	void deleteSelectedGroups();
	void reloadTournamentResults();
	
	void linkClicked(FightResultForPlan value, String columnId);
	void linkClicked(FighterPlace value, String columnId);
	void startFight();
	void save();

}
