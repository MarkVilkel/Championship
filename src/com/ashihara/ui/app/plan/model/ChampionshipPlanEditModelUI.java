/**
 * The file ChampionshipEditModelUI.java was created on 2010.5.4 at 22:19:18
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan.model;

import static com.ashihara.datamanagement.impl.util.ObjectUtils.areTheSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.championship.data.RulesManagerFactory;
import com.ashihara.ui.app.fight.FightJFrame;
import com.ashihara.ui.app.group.GroupDetailsFrame;
import com.ashihara.ui.app.plan.FightingGroupSearchDialog;
import com.ashihara.ui.app.plan.view.ChampionshipPlanEditPanelViewUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.table.KASGenericTableModel;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class ChampionshipPlanEditModelUI extends AKAbstractModelUI<ChampionshipPlanEditPanelViewUI>
									implements IChampionshipPlanEditModelUI<ChampionshipPlanEditPanelViewUI>, DialogCallBackListener, UIStatePerformer<FightResult> {
	
	private ChampionshipPlanEditPanelViewUI viewUI;

	private FightSettings fightSettings;
	private ChampionshipPlan plan;
	private final RulesManager rulesManager;
	
	public ChampionshipPlanEditModelUI(ChampionshipPlan plan) {
		this.plan = plan;
		this.viewUI = new ChampionshipPlanEditPanelViewUI(AKUIEventSender.newInstance(this), RulesManagerFactory.getRulesManager(plan.getChampionship().getRules(), uic));
		this.rulesManager = RulesManagerFactory.getRulesManager(plan.getChampionship().getRules(), uic);
		this.viewUI.getModelUI().reloadFights();
	}

	@Override
	public ChampionshipPlanEditPanelViewUI getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(ChampionshipPlanEditPanelViewUI viewUI) {
		this.viewUI = viewUI;
	}

	@Override
	public void dialogClosed(Class<?> dialog, Map<String, Object> dialogParams) {
		reloadFights();
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();		
	}

	@Override
	public void reloadFights() {
		try {
			boolean finalsAtTheEnd = plan.getFinalsAtTheEnd() != null ? plan.getFinalsAtTheEnd() : false;
			getViewUI().getFinalsAtTheEnd().setSelected(finalsAtTheEnd);
			
			fightSettings = getFightSettingsService().load();
			List<FightingGroup> groups = getChampionshipPlanService().loadGroups(plan);
			List<FightResultForPlan> fightResults = getFightResultService().loadOrCreateFightResults(groups, finalsAtTheEnd);
			
			getViewUI().getFightsTable().getTable().getKASModel().setDataRows(fightResults);
			int selectedIndex = findFirstFightIndex(fightResults);
			if (selectedIndex >= 0) {
				getViewUI().getFightsTable().getTable().setRowSelectionInterval(selectedIndex, selectedIndex);
			}
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private int findFirstFightIndex(List<FightResultForPlan> fightResults) {
		for (int i = 0; i < fightResults.size(); i++) {
			FightResultForPlan item = fightResults.get(i);
			if (canFight(item)) {
				return i;
			}
		}
		return -1;
	}

	private boolean canFight(FightResultForPlan item) {
		if (item == null) {
			return false;
		}
		FightResult fr = item.getFightResult();
		if (fr == null) {
			return false;
		}
		
		if (
				fr != null &&
				fr.getFirstFighter() != null &&
				fr.getSecondFighter() != null &&
				!fr.isFirstFighterWon() &&
				!fr.isSecondFighterWon()
		) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onAddGroups() {
		appManager.openDialog(FightingGroupSearchDialog.class, plan, this);		
	}

	@Override
	public void deleteSelectedGroups() {
		try {
			List<FightResultForPlan> items = getViewUI().getFightsTable().getTable().getSelectedObjects();
			List<FightingGroup> groups = items.stream().map(i -> i.getFightingGroup()).collect(Collectors.toList());
			getChampionshipPlanService().removeFromPlan(groups);
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		reloadFights();
	}

	@Override
	public void linkClicked(FightResultForPlan value, String columnId) {
		if (uic.GROUP().equals(columnId)) {
			openGroup(value.getFightingGroup());
		}
	}

	@Override
	public void reloadTournamentResults() {
		try {
			List<FightingGroup> groups = getChampionshipPlanService().loadGroups(plan);
			List<FighterPlace> result = new ArrayList<>();
			for (FightingGroup group : groups) {
				List<FighterPlace> places = getFightResultService().loadGroupTournamentResults(group);
				result.add(new FighterPlace(group));
				result.addAll(places);
			}
			getViewUI().getFightResultsTable().getTable().getKASModel().setDataRows(result);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void linkClicked(FighterPlace value, String columnId) {
		if (uic.GROUP().equals(columnId)) {
			openGroup(value.getFightingGroup());
		}
	}
	
	private void openGroup(FightingGroup group) {
		appManager.openIdentifiedFrame(GroupDetailsFrame.class, group.getId(), group);
	}

	@Override
	public void startFight() {
		startFight(getViewUI().getShowNext().isSelected());
	}
	
	private void startFight(boolean withNext) {
		FightResultForPlan frfp = getViewUI().getFightsTable().getTable().getSingleSelectedRow();
		doStartFight(frfp, withNext);
	}
	
	private void doStartFight(FightResultForPlan frfp, boolean withNext) {
		final UIStatePerformer<FightResult> nextRoundPerformer = new UIStatePerformer<FightResult>() {
			@Override
			public void performUIState(FightResult param) {
				if (param == null) {
					reloadFights();
				} else {
					final List<FightResult> nextFights = withNext ? getNextFights(param) : null;
					showFightFrame(this, param, true, nextFights);
				}
			}
		};
		
		if (frfp != null) {
			final List<FightResult> nextFights = withNext ? getNextFights(frfp.getFightResult()) : null;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					FightResult value = frfp.getFightResult();
					showFightFrame(nextRoundPerformer, value, false, nextFights);
				}
			});
		}
	}
	
	private List<FightResult> getNextFights(FightResult currentFightResult) {
		int currentIndex = getIndex(currentFightResult);
		List<FightResult> result = new ArrayList<>();
		if (currentIndex < 0) {
			return result;
		}
		
		KASGenericTableModel<FightResultForPlan> model = getViewUI().getFightsTable().getTable().getKASModel();
		List<FightResultForPlan> all = model.getDataRows();
		int count = Math.min(rulesManager.getAdvancedNextFightsCount(), model.getRowCount() - 1);
		int index = currentIndex;
		for (int i = 0; i < all.size(); i++) {
			
			if (result.size() == count) {
				break;
			}
			
			index ++;
			if (index >= all.size()) {
				index = 0;
			}
			if (index == currentIndex) {
				break;
			}
			FightResultForPlan frfp = all.get(index);
			FightResult fr = frfp.getFightResult();
			if (fr == null) {
				//surrogate group
				continue;
			}
			if (
					fr != null &&
					fr.getFirstFighter() != null &&
					fr.getSecondFighter() != null
			) {
				if (fr.isFirstFighterWon() || fr.isSecondFighterWon()) {
					//skip finished fight
					continue;
				} else {
					result.add(fr);
				}
			} else {
				break;
			}
		}
		
		return result;
	}
	
	private int getIndex(FightResult currentFR) {
		KASGenericTableModel<FightResultForPlan> model = getViewUI().getFightsTable().getTable().getKASModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			FightResultForPlan frfp = model.getDataRow(i);
			FightResult fr = frfp.getFightResult();
			
			if (
					fr != null &&
					areTheSame(fr.getFirstFighter(), currentFR.getFirstFighter()) &&
					areTheSame(fr.getSecondFighter(), currentFR.getSecondFighter())
			) {
				return i;
			}
		}
		return -1;
	}

	private void showFightFrame(
			UIStatePerformer<FightResult> nextRoundPerformer,
			FightResult fr,
			boolean createNextRound,
			List<FightResult> nextFights
	) {
		try {
			if (ApplicationManager.getInstance().isRegistered(FightJFrame.class)) {
				MessageHelper.showInformtionMessage(null, uic.FIGHT_WINDOW_IS_ALREADY_OPENED_CLOSE_IT_FIRST());
			} else {
				final FightResult nextRoundFightResult;
				if (createNextRound) {
					nextRoundFightResult = createNextRoundFightResult(fr);
					nextRoundFightResult.setBlueFighter(fr.getBlueFighter());
					nextRoundFightResult.setRedFighter(fr.getRedFighter());
				} else {
					nextRoundFightResult = fr;
				}
				
				new FightJFrame(nextRoundFightResult, fightSettings, true, nextRoundPerformer, true, nextFights, this);
			}
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	public FightResult createNextRoundFightResult(FightResult previousRoundFightResult) throws PersistenceException {
		FightResult nextFightResult = getFightResultService().createNextFightResult(
				previousRoundFightResult,
				rulesManager.copyPointsAndWarningsToTheNextRound()
		);
		previousRoundFightResult.setNextRoundFightResult(nextFightResult);
		getFightResultService().saveFightResult(previousRoundFightResult);
		return nextFightResult;
	}

	@Override
	public void performUIState(FightResult param) {
		reloadFights();
		if (param != null) {
			List<FightResult> nextFights = getNextFights(param);
			if (nextFights != null && !nextFights.isEmpty()) {
				FightResultForPlan frfp = find(nextFights.get(0));
				doStartFight(frfp, getViewUI().getShowNext().isSelected());
			}
		}
	}

	private FightResultForPlan find(FightResult fightResult) {
		KASGenericTableModel<FightResultForPlan> model = getViewUI().getFightsTable().getTable().getKASModel();
		List<FightResultForPlan> all = model.getDataRows();
		for (FightResultForPlan f : all) {
			if (f.getFightResult() != null && f.getFightResult().getId().equals(fightResult.getId())) {
				return f;
			}
		}
		return null;
	}

	@Override
	public void save() {
		try {
 			plan.setFinalsAtTheEnd(getViewUI().getFinalsAtTheEnd().isSelected());
			getChampionshipPlanService().save(plan);
			reloadFights();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		
	}

}
