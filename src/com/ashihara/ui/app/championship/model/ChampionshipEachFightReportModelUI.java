/**
 * The file ChampionshipResultModelUI.java was created on 2011.17.10 at 22:26:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.ui.app.championship.view.ChampionshipEachFightReportViewUI;
import com.ashihara.ui.app.group.GroupDetailsFrame;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class ChampionshipEachFightReportModelUI extends AKAbstractModelUI<ChampionshipEachFightReportViewUI> implements IChampionshipEachFightReportModelUI<ChampionshipEachFightReportViewUI> {
	
	private ChampionshipEachFightReportViewUI viewUI;
	private final Championship championship;
	
	public ChampionshipEachFightReportModelUI(Championship championship) {
		this.championship = championship;
		viewUI = new ChampionshipEachFightReportViewUI(AKUIEventSender.newInstance(this));
		
		clear();
		viewUI.getModelUI().filter();
	}

	@Override
	public ChampionshipEachFightReportViewUI getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(ChampionshipEachFightReportViewUI viewUI) {
		this.viewUI = viewUI;
	}

	@Override
	public void filter() {
		FightingGroup group = null;
		if (getViewUI().getCmbGroups().getSelectedItem() instanceof FightingGroup) {
			group = (FightingGroup)getViewUI().getCmbGroups().getSelectedItem();
		}
		
		ChampionshipFighter firstFighter = null;
		if (getViewUI().getCmbFirstFighter().getSelectedItem() instanceof ChampionshipFighter) {
			firstFighter = (ChampionshipFighter)getViewUI().getCmbFirstFighter().getSelectedItem();
		}
		
		ChampionshipFighter secondFighter = null;
		if (getViewUI().getCmbSecondFighter().getSelectedItem() instanceof ChampionshipFighter) {
			secondFighter = (ChampionshipFighter)getViewUI().getCmbSecondFighter().getSelectedItem();
		}
		
		try {
			List<FightResultReport> result = getFightResultService().generateEachFightReport(championship, group, firstFighter, secondFighter);
			
			FightingGroup fightingGroup = null;
			List<FightResultReport> theResult = new ArrayList<FightResultReport>();
			for (FightResultReport frr : result) {
				if (
						frr.getLastRound() != null &&
						frr.getLastRound().getFirstFighter() != null &&
						frr.getLastRound().getFirstFighter().getFightingGroup() != null
				) {
					if (!frr.getLastRound().getFirstFighter().getFightingGroup().equals(fightingGroup)) {
						if (fightingGroup != null) {
							theResult.add(new FightResultReport());
						}
						fightingGroup = frr.getLastRound().getFirstFighter().getFightingGroup();
					}
				}
				theResult.add(frr);
			}
			
			getViewUI().getFightResultPanel().getTable().getKASModel().clear();
			getViewUI().getFightResultPanel().getTable().getKASModel().setDataRows(theResult);
		} catch (PersistenceException e) {
			MessageHelper.handleException(null, e);
		}
	}

	@Override
	public void clear() {
		try {
			ComboUIHelper.reloadChampionshipGroups(getViewUI().getCmbGroups(), championship);
			ComboUIHelper.reloadChampionshipFighters(getViewUI().getCmbFirstFighter(), championship, true);
			ComboUIHelper.reloadChampionshipFighters(getViewUI().getCmbSecondFighter(), championship, true);
			
			getViewUI().getFightResultPanel().getTable().getKASModel().clear();
		} catch (PersistenceException e) {
			MessageHelper.handleException(null, e);
		}
	}

	@Override
	public void groupSelected() {
		try {
			if (getViewUI().getCmbGroups().getSelectedItem() instanceof FightingGroup) {
				FightingGroup group = (FightingGroup)getViewUI().getCmbGroups().getSelectedItem();
				ComboUIHelper.reloadFightersByGroup(getViewUI().getCmbFirstFighter(), group, true);
				ComboUIHelper.reloadFightersByGroup(getViewUI().getCmbSecondFighter(), group, true);
			}
			else {
				ComboUIHelper.reloadChampionshipFighters(getViewUI().getCmbFirstFighter(), championship, true);
				ComboUIHelper.reloadChampionshipFighters(getViewUI().getCmbSecondFighter(), championship, true);
				
			}
		} catch (PersistenceException e) {
			MessageHelper.handleException(null, e);
		}
	}

	@Override
	public void linkClicked(FightResultReport value, String columnId) {
		if (uic.GROUP().equals(columnId)) {
			if (value.getLastRound() != null) {
				FightingGroup group = value.getLastRound().getFirstFighter().getFightingGroup();
				appManager.openIdentifiedFrame(GroupDetailsFrame.class, group.getId(), group);
			}
		}
	}

}
