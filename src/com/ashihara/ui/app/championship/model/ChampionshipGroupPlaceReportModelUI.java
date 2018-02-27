/**
 * The file ChampionshipResultModelUI.java was created on 2011.17.10 at 22:26:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.ui.app.championship.data.RulesManagerFactory;
import com.ashihara.ui.app.championship.view.ChampionshipGroupPlaceReportViewUI;
import com.ashihara.ui.app.group.GroupDetailsFrame;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class ChampionshipGroupPlaceReportModelUI extends AKAbstractModelUI<ChampionshipGroupPlaceReportViewUI> implements IChampionshipGroupPlaceReportModelUI<ChampionshipGroupPlaceReportViewUI> {
	
	private ChampionshipGroupPlaceReportViewUI viewUI;
	private final Championship championship;
	
	public ChampionshipGroupPlaceReportModelUI(Championship championship) {
		this.championship = championship;
		viewUI = new ChampionshipGroupPlaceReportViewUI(
				AKUIEventSender.newInstance(this),
				RulesManagerFactory.getRulesManager(championship.getRules(), uic)
		);
		
		clear();
		viewUI.getModelUI().filter();
	}

	@Override
	public ChampionshipGroupPlaceReportViewUI getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(ChampionshipGroupPlaceReportViewUI viewUI) {
		this.viewUI = viewUI;
	}

	@Override
	public void filter() {
		FightingGroup group = null;
		if (getViewUI().getCmbGroups().getSelectedItem() instanceof FightingGroup) {
			group = (FightingGroup)getViewUI().getCmbGroups().getSelectedItem();
		}
		
		try {
			List<FighterPlace> result;
			if (group != null) {
				result = getFightResultService().loadGroupTournamentResults(group);
			}
			else {
				result = getFightResultService().loadChampionshipResults(championship);
			}
			
			FightingGroup fightingGroup = null;
			List<FighterPlace> theResult = new ArrayList<FighterPlace>();
			for (FighterPlace fp : result) {
				if (fp.getGCFighter() != null && fp.getGCFighter().getFightingGroup() != null) {
					if (!fp.getGCFighter().getFightingGroup().equals(fightingGroup)) {
						if (fightingGroup != null) {
							theResult.add(new FighterPlace());
						}
						fightingGroup = fp.getGCFighter().getFightingGroup();
					}
				}
				theResult.add(fp);
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
			getViewUI().getFightResultPanel().getTable().getKASModel().clear();
		} catch (PersistenceException e) {
			MessageHelper.handleException(null, e);
		}
	}

	@Override
	public void linkClicked(FighterPlace value, String columnId) {
		if (uic.GROUP().equals(columnId)) {
			if (value.getGCFighter() != null) {
				appManager.openIdentifiedFrame(GroupDetailsFrame.class, value.getGCFighter().getFightingGroup().getId(), value.getGCFighter().getFightingGroup());
			}
		}
	}

}
