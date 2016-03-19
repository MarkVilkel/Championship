/**
 * The file OlympicSystemModel.java was created on 2010.19.4 at 22:21:45
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.ashihara.ui.app.group.view.OlympicSystemPanelView;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterProvider;
import com.ashihara.ui.app.group.view.stuff.OlympicFighterTreeCellPanel;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.IExcelable;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.TableToExcelExporter;
import com.ashihara.ui.util.NextFightManager;
import com.ashihara.utils.MathUtils;
import com.rtu.exception.PersistenceException;

public class OlympicSystemModel extends AbstractFightSystemModel<OlympicSystemPanelView> implements IOlympicSystemModel<OlympicSystemPanelView> {

	private OlympicSystemPanelView viewUI;
	
	private FightingGroup group;
	
	private List<OlympicFighterTreeCellPanel> presentedFighterEditPanels;
	
	private FightSettings fightSettings;
	
	private final int ROW_HEIGHT = 20;
	private final int ROWS_GAP = 10;
	
	private ChampionshipFighterProvider championshipFighterProvider = new ChampionshipFighterProvider();
	
	
	public OlympicSystemModel(FightingGroup group) {
		viewUI = new OlympicSystemPanelView(AKUIEventSender.newInstance(this), championshipFighterProvider, 7, 64, ROW_HEIGHT, ROWS_GAP);
		viewUI.setRowHeight(ROW_HEIGHT);
		viewUI.setRowsGap(ROWS_GAP);
	}

	@Override
	public OlympicSystemPanelView getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(OlympicSystemPanelView viewUI) {
		this.viewUI = viewUI;
	}

	public List<OlympicFighterTreeCellPanel> getPresentedFighterEditPanels() {
		if (presentedFighterEditPanels == null) {
			presentedFighterEditPanels = new ArrayList<OlympicFighterTreeCellPanel>();
		}
		return presentedFighterEditPanels;
	}

	@Override
	public void reset() throws PersistenceException {
		
		fightSettings = getFightSettingsService().load();
		
		reloadCombo();
		
		List<GroupChampionshipFighter> list = getGroupService().loadGroupChampionshipFighters(group);
		getViewUI().getChampionshipFighterTable().getTable().getKASModel().setDataRows(list);

		boolean isTournamentStarted = SC.GROUP_STATUS.STARTED.equals(getGroup().getStatus());
		getViewUI().getChampionshipFighterTable().getTable().getKASModel().setCanEdit(!isTournamentStarted);
		getViewUI().getChampionshipFighterTable().setEnabled(!isTournamentStarted);
		getViewUI().getChampionshipFighterTable().getCommonButtonsPanel().setVisible(!isTournamentStarted);
		
		
		getViewUI().getBtnExportToExel().setEnabled(isTournamentStarted);
		
		if (isTournamentStarted) {
			resetOlympicTree(list);
		}
	}
	
	private void resetOlympicTree(List<GroupChampionshipFighter> list) throws PersistenceException {
		getPresentedFighterEditPanels().clear();
		getViewUI().removeAllFighterPanels();
		
		List<FightResult> fightResults = getFightResultService().loadOrCreateOlympicFightResults(group);
		Map<Long, AtomicInteger> levelsMap = createLevelsMap(fightResults);
		
		for (FightResult fightResult : fightResults) {
			AtomicInteger numberOfLevel = levelsMap.get(fightResult.getOlympicLevel());
			if (numberOfLevel == null) {
				numberOfLevel = new AtomicInteger(0);
				levelsMap.put(fightResult.getOlympicLevel(), numberOfLevel);
			}
			
			if (fightResult.getFirstFighter() != null) {
				addGroupChampionshipFighter(
						fightResult,
						fightResult.getFirstFighterParent(),
						fightResult.getFirstFighter(),
						numberOfLevel.intValue()
				);
			}
			numberOfLevel.incrementAndGet();
			
			if (fightResult.getSecondFighter() != null) {
				addGroupChampionshipFighter(
						fightResult,
						fightResult.getSecondFighterParent(),
						fightResult.getSecondFighter(),
						numberOfLevel.intValue()
				);
			}
			numberOfLevel.incrementAndGet();
		}
		getViewUI().getBtnExportToExel().setEnabled(!fightResults.isEmpty());
		getViewUI().repaint();
	}

	private Map<Long, AtomicInteger> createLevelsMap(List<FightResult> fightResults) {
		Map<Long, AtomicInteger> map = new HashMap<Long, AtomicInteger>();
		for (FightResult fr : fightResults) {
			if (!map.containsKey(fr.getOlympicLevel())) {
				map.put(fr.getOlympicLevel(), new AtomicInteger(0));
			}
		}
		return map;
	}

	private void addGroupChampionshipFighter(
			final FightResult fightResult,
			final FightResult parentFightResult,
			GroupChampionshipFighter fighter,
			int number
	) throws PersistenceException {
		final boolean isRed = isRed(number);
		
		double panelHeight = MathUtils.mul2(ROW_HEIGHT + ROWS_GAP, fightResult.getOlympicLevel().longValue());
		double leftConnectorHeight = 0.17 * panelHeight;
		
		OlympicFighterTreeCellPanel fighterEditPanel = new OlympicFighterTreeCellPanel(
				isRed,
				fightResult.getFirstFighter() != null && fightResult.getSecondFighter() != null,
				fightResult.getOlympicLevel().longValue() > 0,
				parentFightResult != null,
				(int)panelHeight,
				(int)leftConnectorHeight,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (isRed) {
							startFight(fightResult);
						}
					}
				},
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						NextFightManager.getInstance().setNextFightResult(fightResult);
						NextFightManager.getInstance().setNextUiStatePerformer(
								new UIStatePerformer<FightResult>() {
									@Override
									public void performUIState(FightResult param) {
										try {
											reset();
										} catch (PersistenceException e) {
											MessageHelper.handleException(null, e);
										}
									}
								}
						);
					}
				}
		);
		
		getPresentedFighterEditPanels().add(fighterEditPanel);
		getViewUI().setComponentToCell(
				fighterEditPanel,
				fightResult.getOlympicLevel().intValue(),
				2 * fightResult.getOlympicPositionOnLevel().intValue() + (isRed ? 0 : 1)
		);
		
		copyOnUI(fighterEditPanel, fighter);
		getViewUI().repaint();
	}
	
	private boolean isRed(int number) {
		boolean result = number % 2 == 0;
		return result;
	}
	
	private void copyOnUI(OlympicFighterTreeCellPanel fighterEditPanel, GroupChampionshipFighter fighter) throws PersistenceException {
		fighterEditPanel.getLblFighter().setText(fighter.getChampionshipFighter().toString());
	}

	@Override
	public void save() throws PersistenceException, AKValidationException {
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

	public FightingGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(FightingGroup group) {
		this.group = group;
	}

	@Override
	public void setVisible(boolean b) {
		
	}

	@Override
	public void startGroup() throws PersistenceException {
		List<GroupChampionshipFighter> fighters = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataRows();
		getGroupService().setupLevelsForOlympicGroupChampionshipFighters(fighters);
	}

	private void startFight(final FightResult fightResult) {
		final UIStatePerformer<FightResult> nextRoundPerformer = new UIStatePerformer<FightResult>() {
			@Override
			public void performUIState(FightResult param) {
				try {
					if (param == null) {
						reset();
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
					new FightJFrame(fightResult, fightSettings, false, nextRoundPerformer);
				}
			}
		});

	}

	public FightResult createNextRoundFightResult(FightResult previousRoundFightResult) throws PersistenceException {
		FightResult nextFightResult = getFightResultService().createNextFightResult(previousRoundFightResult);
		previousRoundFightResult.setNextRoundFightResult(nextFightResult);
		getFightResultService().saveFightResult(previousRoundFightResult);
		
		return nextFightResult;
	}
	
	private void reloadCombo() throws PersistenceException {
		List<ChampionshipFighter> items = getFighterService().loadFightersSuitableForGroup(getGroup());
		championshipFighterProvider.setItems(items);
	}

	@Override
	public List<FighterPlace> loadGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException {
		return getFightResultService().loadGroupTournamentResults(fightingGroup);
	}

	@Override
	public void exportToExcel() {
		try {
			List<FightResult> fightResults = getFightResultService().loadOrCreateOlympicFightResults(group);
			TableToExcelExporter.drawToExcel(
					new IExcelable() {
						@Override
						public int getRowCount() {
							return fightResults.size();
						}
						
						@Override
						public String getHeader(int column) {
							switch (column) {
								case 0 : return uic.FIGHTER() + " I";
								case 1 : return uic.POINTS();
								case 2 : return uic.RESULT_SCORE();
								case 3 : return uic.FIGHTER() + " II";
								case 4 : return uic.POINTS();
								case 5 : return uic.RESULT_SCORE();
								default : return "";
							}
						}
						
						@Override
						public int getColumnCount() {
							return 6;
						}
						
						@Override
						public String getCell(int row, int column) {
							FightResult fr = fightResults.get(row);
							switch (column) {
							case 0 : return fr.getFirstFighter() != null ? fr.getFirstFighter().getChampionshipFighter().toString() : "";
							case 1 : return fr.getFirstFighterPoints() == null ? "" : fr.getFirstFighterPoints().toString();
							case 2 : return fr.getFirstFighterPointsForWin() == null ? "" : fr.getFirstFighterPointsForWin().toString();
							case 3 : return fr.getSecondFighter() != null ? fr.getSecondFighter().getChampionshipFighter().toString() : "";
							case 4 : return fr.getSecondFighterPoints() == null ? "" : fr.getSecondFighterPoints().toString();
							case 5 : return fr.getSecondFighterPointsForWin() == null ? "" : fr.getSecondFighterPointsForWin().toString();
							default : return "";
						}
						}
					},
					uic.OLYMPIC_FIGH_RESULTS(),
					uic
			);
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

}
