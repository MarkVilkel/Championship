/**
 * The file JosuiRulesManager.java was created on 27 february 2018 at 23:33:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import com.ashihara.enums.UIC;

public class JosuiStyleRulesManager implements RulesManager {
	
	private final UIC uic;

	public JosuiStyleRulesManager(UIC uic) {
		this.uic = uic;
	}

	@Override
	public boolean hasSecondPenaltyCategory() {
		return true;
	}

	@Override
	public String getFirstPenaltyCategoryCaption() {
		return uic.FIRST_CATEGORY();
	}

	@Override
	public String getSecondPenaltyCategoryCaption() {
		return uic.SECOND_CATEGORY();
	}

	@Override
	public boolean redFighterFromTheLeft() {
		return true;
	}

	@Override
	public long getMaxRoundsCount() {
		return Long.MAX_VALUE;
	}

	@Override
	public Long getWarningIncreaseCount(long warningCount) {
		if (warningCount == 2) {
			return 1L;
		} else if (warningCount == 3) {
			return 2L;
		} else {
			return null;
		}
	}

	@Override
	public Long getWarningDecreaseCount(long warningCount) {
		if (warningCount == 2) {
			return 2L;
		} else if (warningCount == 1) {
			return 1L;
		} else {
			return null;
		}
	}

	@Override
	public long getMaxPenaltyCount() {
		return 4;
	}

	@Override
	public long getMaxPointsCount() {
		return 100;
	}

	@Override
	public Long getPointsDifferenceForWin() {
		return 8L;
	}

	@Override
	public boolean copyPointsAndWarningsToTheNextRound() {
		return false;
	}

	@Override
	public boolean canWinByJudgeDecision() {
		return false;
	}

	@Override
	public Long getMaxPointsDifferenceForTheNextRound() {
		return 0L;
	}

	@Override
	public boolean sumFirstAndSecondPenaltyCategory() {
		return false;
	}

	@Override
	public Long getWarningIncreaseCount(long firstCategoryWarningCount, long secondCategoryWarningCount) {
		return null;
	}

	@Override
	public Long getWarningDecreaseCount(long firstCategoryWarningCount, long secondCategoryWarningCount) {
		return null;
	}

	@Override
	public Long getMaxSumPenaltyCount() {
		return null;
	}

}
