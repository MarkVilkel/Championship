/**
 * The file IChampionshipEditModelUI.java was created on 2010.5.4 at 22:18:11
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IChampionshipEditModelUI<T extends UIView> extends UIModel<T> {

	void addFighters();
	void deleteSelectedFighters();
	void save();
	void cancel();
	void fullReload();
	void addGroupsByYearCategory();
	void deleteSelectedGroups();
	void linkClickedOnChampionshipFighter(ChampionshipFighter value, String columnId);
	void addGroupsByWeightCategory();
	void searchGroupsByYearWeight();
	void clearYearWeightCriteria();
	void linkClickedOnGroup(FightingGroup value);
	void filterFighters();
	void clearFightersFilter();
	void showGroupPlaceReport();
	void showEachFighterReport();
	void checkFighters();
	void exportGroups();
	void importGroups();
	void exportFinishedGroups();
	void exportAllGroupsToExcel();
	void openPlan();

}
