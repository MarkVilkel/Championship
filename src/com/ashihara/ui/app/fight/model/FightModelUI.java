/**
 * The file FightModelUI.java was created on 2011.16.10 at 08:56:20
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.model;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.championship.data.RulesManagerFactory;
import com.ashihara.ui.app.fight.view.FightPanel;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.util.NextFightManager;
import com.rtu.exception.PersistenceException;

public class FightModelUI extends AKAbstractModelUI<FightPanel> implements IFightModelUI<FightPanel> {
	
	private FightPanel viewUI;
	
	private FightResult fightResult;
	private final FightSettings fightSettings;
	
	private final UIStatePerformer<FightResult> callbacker;
	
	private boolean nextRoundWasClicked = false;
	
	private final RulesManager rulesManager;
	
	public FightModelUI(
			FightResult fightResult,
			FightSettings fightSettings,
			boolean isNextRound,
			UIStatePerformer<FightResult> callbacker
	) {
		this.fightResult = fightResult;
		this.callbacker = callbacker;
		this.fightSettings = fightSettings;
		
		if (NextFightManager.getInstance().getNextFightResult() != null && !isNextRound) {
			this.fightResult = NextFightManager.getInstance().getNextFightResult();
			
			if (
					this.fightResult.getBlueFighter().getId().equals(fightResult.getBlueFighter().getId()) &&
					this.fightResult.getRedFighter().getId().equals(fightResult.getRedFighter().getId())
			) {
				NextFightManager.getInstance().setNextFightResult(null);
				NextFightManager.getInstance().setNextUiStatePerformer(null);
			}
			else {
				NextFightManager.getInstance().setNextFightResult(fightResult);
			}
		}
		
		GroupChampionshipFighter gcf = fightResult.getFirstFighter() != null ? fightResult.getFirstFighter() : fightResult.getSecondFighter();
		this.rulesManager = RulesManagerFactory.getRulesManager(gcf.getFightingGroup().getChampionship().getRules(), uic);
		
		this.viewUI = new FightPanel(
				this.fightResult,
				NextFightManager.getInstance().getNextFightResult(),
				fightSettings,
				AKUIEventSender.newInstance(this),
				rulesManager
		);
		
		reset();
	}
	
	private void reset() {
		copyOnUI();
	}

	@Override
	public FightPanel getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(FightPanel viewUI) {
		this.viewUI = viewUI;
	}

	public void dispose() {
		viewUI.dispose();
		saveFightResult();
		
		if (!nextRoundWasClicked) {
			UIStatePerformer<FightResult> performer = NextFightManager.getInstance().getNextUiStatePerformer();
			if (performer != null) {
				NextFightManager.getInstance().setNextUiStatePerformer(callbacker);
				performer.performUIState(null);
			}
			else {
				callbacker.performUIState(null);
			}
		}
	}

	@Override
	public void saveFightResult() {
		try {
			copyFromUI();
			fightResult = getFightResultService().performFightResultOnFightAction(fightResult);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private void copyOnUI() {
		Long firstFighterPoints = extractValue(fightResult.getFirstFighterPoints());
		Long firstFighterFirstCategory = extractValue(fightResult.getFirstFighterFirstCategoryWarnings());
		Long firstFighterSecondCategory = extractValue(fightResult.getFirstFighterSecondCategoryWarnings());

		Long secondFighterPoints = extractValue(fightResult.getSecondFighterPoints());
		Long secondFighterFirstCategory = extractValue(fightResult.getSecondFighterFirstCategoryWarnings());
		Long secondFighterSecondCategory = extractValue(fightResult.getSecondFighterSecondCategoryWarnings());
		
		if (fightResult.getRedFighter().getId().equals(fightResult.getFirstFighter().getId())) {
			getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().increaseCount(firstFighterPoints);
			getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().increaseCount(firstFighterFirstCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().increaseCount(firstFighterSecondCategory);
			
			getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().increaseCount(secondFighterPoints);
			getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().increaseCount(secondFighterFirstCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().increaseCount(secondFighterSecondCategory);
		}
		else {
			getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().increaseCount(secondFighterPoints);
			getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().increaseCount(secondFighterFirstCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().increaseCount(secondFighterSecondCategory);
			
			getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().increaseCount(firstFighterPoints);
			getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().increaseCount(firstFighterFirstCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().increaseCount(firstFighterSecondCategory);
		}
	}
	
	private long extractValue(Long value) {
		long result = value == null ? 0 : value.longValue();
		return result;
	}

	private void copyFromUI() {
		long firstFighterPoints = getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().getCount();
		long firstFighterFirstCategory = getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().getCount();
		long firstFighterSecondCategory = getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().getCount();
		
		long secondFighterPoints = getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().getCount();
		long secondFighterFirstCategory = getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().getCount();
		long secondFighterSecondCategory = getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().getCount();

		if (fightResult.getRedFighter().getId().equals(fightResult.getFirstFighter().getId())) {
			fightResult.setFirstFighterPoints(firstFighterPoints);
			fightResult.setFirstFighterFirstCategoryWarnings(firstFighterFirstCategory);
			fightResult.setFirstFighterSecondCategoryWarnings(firstFighterSecondCategory);
			
			fightResult.setSecondFighterPoints(secondFighterPoints);
			fightResult.setSecondFighterFirstCategoryWarnings(secondFighterFirstCategory);
			fightResult.setSecondFighterSecondCategoryWarnings(secondFighterSecondCategory);
		}
		else {
			fightResult.setFirstFighterPoints(secondFighterPoints);
			fightResult.setFirstFighterFirstCategoryWarnings(secondFighterFirstCategory);
			fightResult.setFirstFighterSecondCategoryWarnings(secondFighterSecondCategory);
			
			fightResult.setSecondFighterPoints(firstFighterPoints);
			fightResult.setSecondFighterFirstCategoryWarnings(firstFighterFirstCategory);
			fightResult.setSecondFighterSecondCategoryWarnings(firstFighterSecondCategory);
		}
		
		
		fightResult.setFirstFighterPointsForWin(getFightResultService().getFirstFighterPointsForWin(fightSettings, fightResult));
		fightResult.setSecondFighterPointsForWin(getFightResultService().getSecondFighterPointsForWin(fightSettings, fightResult));
		
	}

	@Override
	public void nextRound() {
		nextRoundWasClicked = true;
		
		saveFightResult();
		
		getViewUI().disposeParent();
		callbacker.performUIState(fightResult);
	}

	@Override
	public void switchFighters() {
		
	}

}
