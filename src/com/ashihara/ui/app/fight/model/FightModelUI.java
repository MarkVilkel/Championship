/**
 * The file FightModelUI.java was created on 2011.16.10 at 08:56:20
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.model;

import java.util.List;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
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
	
	private FightResultForPlan frfp;
	private FightResult fightResult;
	private final FightSettings fightSettings;
	
	private final UIStatePerformer<FightResultForPlan> nextRoundCallbacker;
	private final UIStatePerformer<FightResultForPlan> nextFightCallbacker;
	
	private boolean nextRoundWasClicked = false;
	private boolean nextFightWasClicked = false;
	
	private final RulesManager rulesManager;
	
	private final boolean advancedNextFights;
	
	public FightModelUI(
			FightResultForPlan frfp,
			FightSettings fightSettings,
			boolean isNextRound,
			UIStatePerformer<FightResultForPlan> nextRoundCallbacker,
			boolean advancedNextFights,
			List<FightResultForPlan> nextFights,
			UIStatePerformer<FightResultForPlan> nextFightCallbacker
	) {
		this.frfp = frfp;
		this.fightResult = frfp.getFightResult();
		this.nextRoundCallbacker = nextRoundCallbacker;
		this.fightSettings = fightSettings;
		this.advancedNextFights = advancedNextFights;
		this.nextFightCallbacker = nextFightCallbacker;
		
		if (!advancedNextFights) {
			if (NextFightManager.getInstance().getNextFightResult() != null && !isNextRound) {
				this.fightResult = NextFightManager.getInstance().getNextFightResult();
				
				if (
						this.fightResult.getBlueFighter().getId().equals(fightResult.getBlueFighter().getId()) &&
						this.fightResult.getRedFighter().getId().equals(fightResult.getRedFighter().getId())
				) {
					NextFightManager.getInstance().setNextFightResult(null);
					NextFightManager.getInstance().setNextUiStatePerformer(null);
				} else {
					NextFightManager.getInstance().setNextFightResult(fightResult);
				}
			}
		}
		
		GroupChampionshipFighter gcf = fightResult.getFirstFighter() != null ? fightResult.getFirstFighter() : fightResult.getSecondFighter();
		this.rulesManager = RulesManagerFactory.getRulesManager(gcf.getFightingGroup().getChampionship().getRules(), uic);
		
		this.viewUI = new FightPanel(
				this.frfp,
				advancedNextFights,
				NextFightManager.getInstance().getNextFightResult(),
				nextFights,
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
		
		if (!nextRoundWasClicked && !advancedNextFights) {
			UIStatePerformer<FightResultForPlan> performer = NextFightManager.getInstance().getNextUiStatePerformer();
			if (performer != null) {
				NextFightManager.getInstance().setNextUiStatePerformer(nextRoundCallbacker);
				performer.performUIState(null);
			}
			else {
				nextRoundCallbacker.performUIState(null);
			}
		} else {
			if (nextFightCallbacker != null) {
				nextFightCallbacker.performUIState(nextFightWasClicked ? frfp : null);
			}
		}
	}

	@Override
	public void saveFightResult() {
		try {
			copyFromUI();
			fightResult = getFightResultService().performFightResultOnFightAction(fightResult, rulesManager);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private void copyOnUI() {
		Long firstFighterPoints = extractValue(fightResult.getFirstFighterPoints());
		Long firstFighterFirstCategory = extractValue(fightResult.getFirstFighterFirstCategoryWarnings());
		Long firstFighterSecondCategory = extractValue(fightResult.getFirstFighterSecondCategoryWarnings());
		boolean firstFighterWinByJudgeDecision = extractValue(fightResult.getFirstFighterWinByJudgeDecision());
		boolean firstFighterWinByTKO = extractValue(fightResult.getFirstFighterWinByTKO());

		Long secondFighterPoints = extractValue(fightResult.getSecondFighterPoints());
		Long secondFighterFirstCategory = extractValue(fightResult.getSecondFighterFirstCategoryWarnings());
		Long secondFighterSecondCategory = extractValue(fightResult.getSecondFighterSecondCategoryWarnings());
		boolean secondFighterWinByJudgeDecision = extractValue(fightResult.getSecondFighterWinByJudgeDecision());
		boolean secondFighterWinByTKO = extractValue(fightResult.getSecondFighterWinByTKO());
		
		if (fightResult.getRedFighter().getId().equals(fightResult.getFirstFighter().getId())) {
			getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().setCount(firstFighterPoints);
			getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().setCount(firstFighterFirstCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().setCount(firstFighterSecondCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(firstFighterWinByJudgeDecision);
			getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByTKO().setSelected(firstFighterWinByTKO);
			
			getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().setCount(secondFighterPoints);
			getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().setCount(secondFighterFirstCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().setCount(secondFighterSecondCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(secondFighterWinByJudgeDecision);
			getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByTKO().setSelected(secondFighterWinByTKO);
		} else {
			getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().setCount(secondFighterPoints);
			getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().setCount(secondFighterFirstCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().setCount(secondFighterSecondCategory);
			getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(secondFighterWinByJudgeDecision);
			getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByTKO().setSelected(secondFighterWinByTKO);
			
			getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().setCount(firstFighterPoints);
			getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().setCount(firstFighterFirstCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().setCount(firstFighterSecondCategory);
			getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(firstFighterWinByJudgeDecision);
			getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByTKO().setSelected(firstFighterWinByTKO);
		}
		
		getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().showCount();
		getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().showCount();
		getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().showCount();
		
		getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().showCount();
		getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().showCount();
		getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().showCount();
		
		getViewUI().performButtonNextRoundEnability();
	}
	
	private boolean extractValue(Boolean val) {
		return val == null ? false : val.booleanValue();
	}

	private long extractValue(Long value) {
		long result = value == null ? 0 : value.longValue();
		return result;
	}

	private void copyFromUI() {
		long firstFighterPoints = getViewUI().getFirstFighterBattleInfoPanel().getPointsPanel().getCount();
		long firstFighterFirstCategory = getViewUI().getFirstFighterBattleInfoPanel().getFirstCategoryPanel().getCount();
		long firstFighterSecondCategory = getViewUI().getFirstFighterBattleInfoPanel().getSecondCategoryPanel().getCount();
		boolean firstFighterWinByJudgeDecision = getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected();
		boolean firstFighterWinByTKO = getViewUI().getFirstFighterBattleInfoPanel().getCheckWinByTKO().isSelected();
		
		long secondFighterPoints = getViewUI().getSecondFighterBattleInfoPanel().getPointsPanel().getCount();
		long secondFighterFirstCategory = getViewUI().getSecondFighterBattleInfoPanel().getFirstCategoryPanel().getCount();
		long secondFighterSecondCategory = getViewUI().getSecondFighterBattleInfoPanel().getSecondCategoryPanel().getCount();
		boolean secondFighterWinByJudgeDecision = getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected();
		boolean secondFighterWinByTKO = getViewUI().getSecondFighterBattleInfoPanel().getCheckWinByTKO().isSelected();

		if (fightResult.getRedFighter().getId().equals(fightResult.getFirstFighter().getId())) {
			fightResult.setFirstFighterPoints(firstFighterPoints);
			fightResult.setFirstFighterFirstCategoryWarnings(firstFighterFirstCategory);
			fightResult.setFirstFighterSecondCategoryWarnings(firstFighterSecondCategory);
			fightResult.setFirstFighterWinByJudgeDecision(firstFighterWinByJudgeDecision);
			fightResult.setFirstFighterWinByTKO(firstFighterWinByTKO);
			
			fightResult.setSecondFighterPoints(secondFighterPoints);
			fightResult.setSecondFighterFirstCategoryWarnings(secondFighterFirstCategory);
			fightResult.setSecondFighterSecondCategoryWarnings(secondFighterSecondCategory);
			fightResult.setSecondFighterWinByJudgeDecision(secondFighterWinByJudgeDecision);
			fightResult.setSecondFighterWinByTKO(secondFighterWinByTKO);
		}
		else {
			fightResult.setFirstFighterPoints(secondFighterPoints);
			fightResult.setFirstFighterFirstCategoryWarnings(secondFighterFirstCategory);
			fightResult.setFirstFighterSecondCategoryWarnings(secondFighterSecondCategory);
			fightResult.setFirstFighterWinByJudgeDecision(secondFighterWinByJudgeDecision);
			fightResult.setFirstFighterWinByTKO(secondFighterWinByTKO);
			
			fightResult.setSecondFighterPoints(firstFighterPoints);
			fightResult.setSecondFighterFirstCategoryWarnings(firstFighterFirstCategory);
			fightResult.setSecondFighterSecondCategoryWarnings(firstFighterSecondCategory);
			fightResult.setSecondFighterWinByJudgeDecision(firstFighterWinByJudgeDecision);
			fightResult.setSecondFighterWinByTKO(firstFighterWinByTKO);
		}
		
		
		fightResult.setFirstFighterPointsForWin(getFightResultService().getFirstFighterPointsForWin(fightSettings, fightResult, rulesManager));
		fightResult.setSecondFighterPointsForWin(getFightResultService().getSecondFighterPointsForWin(fightSettings, fightResult, rulesManager));
		
	}

	@Override
	public void nextRound() {
		nextRoundWasClicked = true;
		
		saveFightResult();
		
		getViewUI().disposeParent();
		nextRoundCallbacker.performUIState(frfp);
	}

	@Override
	public void switchFighters() {
		
	}

	@Override
	public void nextFight() {
		nextFightWasClicked = true;
		
		saveFightResult();
		
		getViewUI().disposeParent();
	}
	
}
