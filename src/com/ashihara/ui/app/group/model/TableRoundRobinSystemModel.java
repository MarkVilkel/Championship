/*
 * Copyright 2011 Dukascopy Bank SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ashihara.ui.app.group.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.fight.FightJFrame;
import com.ashihara.ui.app.group.view.TableRoundRobinSystemPanelView;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterProvider;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.table.KASRow;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.util.NextFightManager;
import com.rtu.exception.PersistenceException;

/**
 * @author Mark Vilkel
 */
public class TableRoundRobinSystemModel extends AbstractFightSystemModel<TableRoundRobinSystemPanelView> implements ITableRoundRobinSystemModel<TableRoundRobinSystemPanelView> {

	private TableRoundRobinSystemPanelView viewUI;
	private FightingGroup group;
	private FightSettings fightSettings;
	private final ChampionshipFighterProvider championshipFighterProvider = new ChampionshipFighterProvider();
	
	public TableRoundRobinSystemModel(FightingGroup group) {
		this.viewUI = new TableRoundRobinSystemPanelView(AKUIEventSender.newInstance(this), championshipFighterProvider);
		this.group = group;
		
		this.viewUI.getModelUI().reset();
	}
	
	@Override
	public void reset() {
		try {
			doReset();
		} catch (PersistenceException e) {
			MessageHelper.handleException(null, e);
		}
	}
	
	private void doReset() throws PersistenceException {
		fightSettings = getFightSettingsService().load();
		
		reloadCombo();
		
		List<GroupChampionshipFighter> fighters = getGroupService().loadGroupChampionshipFighters(getGroup());
		getViewUI().getChampionshipFighterTable().getTable().getKASModel().setDataRows(fighters);
		
		boolean isTournamentStarted = SC.GROUP_STATUS.STARTED.equals(getGroup().getStatus());
		getViewUI().getChampionshipFighterTable().getTable().getKASModel().setCanEdit(!isTournamentStarted);
		getViewUI().getChampionshipFighterTable().setEnabled(!isTournamentStarted);
		getViewUI().getChampionshipFighterTable().getCommonButtonsPanel().setVisible(!isTournamentStarted);

		
		if (isTournamentStarted) {
			List<FightResult> fightResults = getFightResultService().loadOrCreateRoundRobinLastFightResults(getGroup());
			getViewUI().getFightResultTable().getTable().getKASModel().setDataRows(fightResults);
		}
	}

	private void reloadCombo() throws PersistenceException {
		List<ChampionshipFighter> items = getFighterService().loadFightersSuitableForGroup(getGroup());
		championshipFighterProvider.setItems(items);
	}

	private FightingGroup getGroup() {
		return group;
	}

	@Override
	public void save() throws AKValidationException, PersistenceException {
		List<GroupChampionshipFighter> deletedList = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataCleanDeleted();
		
		List<GroupChampionshipFighter> allData = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataRows();
		List<GroupChampionshipFighter> saveList = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataCleanAdded();
		saveList.addAll(getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataCleanModified());
		
		validateSaveData(allData, saveList);
		for (GroupChampionshipFighter gcf : saveList) {
			gcf.setFightingGroup(getGroup());
		}
		getGroupService().performGroupChampionshipFighters(saveList, deletedList);
	}

	private void validateSaveData(
			List<GroupChampionshipFighter> allData,
			List<GroupChampionshipFighter> saveList
	) throws AKValidationException {
		Validator.validateMandatoryList(saveList, getCmGroupChampionshipFighter().getChampionshipFighter().getFighter(), uic.FIGHTER());
		
		List<Long> uniqueIdues = new ArrayList<Long>();
		for (GroupChampionshipFighter gcf : allData) {
			if (uniqueIdues.contains(gcf.getChampionshipFighter().getFighter().getId())) {
				throw new AKValidationException(uic.FIGHTERS_COULD_NOT_BE_REPEATED() + " '" + gcf.getChampionshipFighter().getFighter() + "'");
			}
			else {
				uniqueIdues.add(gcf.getChampionshipFighter().getFighter().getId());
			}
		}
	}

	@Override
	public void setGroup(FightingGroup fightingGroup) {
		group = fightingGroup;
	}

	@Override
	public void setVisible(final boolean b) {
//		getViewUI().getChampionshipFighterTable().getButtonsPanel().getBtnAdd().setVisible(b);
		if (!b) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					List<KASRow<GroupChampionshipFighter>> rows = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getRows();
					for (KASRow<GroupChampionshipFighter> row : rows) {
						row.setEditable(b);
					}
					getViewUI().getChampionshipFighterTable().repaint();
				}
			});
		}
	}

	@Override
	public void linkClicked(
			final FightResult value,
			String columnId
	) {
		
		if (uic.ACTION().equals(columnId)) {
			final UIStatePerformer<FightResult> nextRoundPerformer = new UIStatePerformer<FightResult>() {
				@Override
				public void performUIState(FightResult param) {
					try {
						if (param == null) {
							reset();
							setVisible(false);
						}
						else {
							if (ApplicationManager.getInstance().isRegistered(FightJFrame.class)) {
								MessageHelper.showInformtionMessage(null, uic.FIGHT_WINDOW_IS_ALREADY_OPENED_CLOSE_IT_FIRST());
							}
							else {
								FightResult nextRoundFightResult = createNextRoundFightResult(param);
								nextRoundFightResult.setBlueFighter(param.getBlueFighter());
								nextRoundFightResult.setRedFighter(param.getRedFighter());
								
								new FightJFrame(nextRoundFightResult, fightSettings, true, this);
							}
						}
						
					} catch (PersistenceException e) {
						MessageHelper.handleException(getViewUI(), e);
					}
				}
			};
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (ApplicationManager.getInstance().isRegistered(FightJFrame.class)) {
						MessageHelper.showInformtionMessage(null, uic.FIGHT_WINDOW_IS_ALREADY_OPENED_CLOSE_IT_FIRST());
					}
					else {
						value.setRedFighter(value.getFirstFighter());
						value.setBlueFighter(value.getSecondFighter());
						
						new FightJFrame(value, fightSettings, false, nextRoundPerformer);
					}
				}
			});
		}
		else if (uic.NEXT_FIGHT().equals(columnId)) {
			value.setRedFighter(value.getFirstFighter());
			value.setBlueFighter(value.getSecondFighter());

			NextFightManager.getInstance().setNextFightResult(value);
			NextFightManager.getInstance().setNextUiStatePerformer(
					new UIStatePerformer<FightResult>() {
						@Override
						public void performUIState(FightResult param) {
							reset();
						}
					}
			);
		}
	}
	
	public FightResult createNextRoundFightResult(FightResult previousRoundFightResult) throws PersistenceException {
		FightResult nextFightResult = getFightResultService().createNextFightResult(previousRoundFightResult);
		previousRoundFightResult.setNextRoundFightResult(nextFightResult);
		getFightResultService().saveFightResult(previousRoundFightResult);
		return nextFightResult;
	}


	@Override
	public TableRoundRobinSystemPanelView getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(TableRoundRobinSystemPanelView viewUI) {
		this.viewUI = viewUI;
	}

	@Override
	public void startGroup() throws PersistenceException {
		
	}

	@Override
	public List<FighterPlace> loadGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException {
		return getFightResultService().loadGroupTournamentResults(fightingGroup);
	}

}

