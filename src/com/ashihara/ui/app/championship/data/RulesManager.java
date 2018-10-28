/**
 * The file RulesManager.java was created on 27 feb. 2018 at 23:32:57
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

public interface RulesManager {

	boolean hasSecondPenaltyCategory();
	String getFirstPenaltyCategoryCaption();
	String getSecondPenaltyCategoryCaption();

	boolean redFighterFromTheLeft();
	long getMaxRoundsCount();
	Long getMaxPointsDifferenceForTheNextRound();
	
	
	Long getWarningIncreaseCount(long warningCount);
	Long getWarningDecreaseCount(long warningCount);
	long getMaxPenaltyCount();
	
	boolean sumFirstAndSecondPenaltyCategory();
	Long getWarningIncreaseCount(long firstCategoryWarningCount, long secondCategoryWarningCount, boolean isFirstCategoryCountIncreased);
	Long getWarningDecreaseCount(long firstCategoryWarningCount, long secondCategoryWarningCount, boolean isFirstCategoryCountDecreased);
	Long getMaxSumPenaltyCount();
	
	long getMaxPointsCount();
	Long getPointsDifferenceForWin();
	
	boolean copyPointsAndWarningsToTheNextRound();
	boolean canWinByJudgeDecision();

}
