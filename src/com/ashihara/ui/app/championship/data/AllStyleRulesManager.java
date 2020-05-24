/**
 * The file JosuiRulesManager.java was created on 27 feb. 2018 at 23:33:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import com.ashihara.enums.UIC;

public class AllStyleRulesManager implements RulesManager {

	private final UIC uic;

	public AllStyleRulesManager(UIC uic) {
		this.uic = uic;
	}

	@Override
	public boolean hasSecondPenaltyCategory() {
		return false;
	}

	@Override
	public String getFirstPenaltyCategoryCaption() {
		return uic.PENALTY();
	}

	@Override
	public String getSecondPenaltyCategoryCaption() {
		return uic.PENALTY();
	}

	@Override
	public boolean redFighterFromTheLeft() {
		return false;
	}

	@Override
	public long getMaxRoundsCount() {
		return 3;
	}

	@Override
	public Long getWarningIncreaseCount(long warningCount) {
		if (warningCount == 3) {
			return 1L;
		} else if (warningCount == 4) {
			return 2L;
		} else if (warningCount == 5) {
			return 3L;
		} else {
			return null;
		}
	}

	@Override
	public Long getWarningDecreaseCount(long warningCount) {
		if (warningCount == 4) {
			return 3L;
		} else if (warningCount == 3) {
			return 2L;
		} else if (warningCount == 2) {
			return 1L;
		} else {
			return null;
		}
	}

	@Override
	public long getMaxPenaltyCount() {
		return 5;
	}

	@Override
	public long getMaxPointsCount() {
		return 6;
	}

	@Override
	public Long getPointsDifferenceForWin() {
		return null;
	}

	@Override
	public boolean copyPointsAndWarningsToTheNextRound() {
		return true;
	}

	@Override
	public boolean canWinByJudgeDecision() {
		return true;
	}

	@Override
	public long getMaxPointsDifferenceForTheNextRound() {
		return 2;
	}

	@Override
	public boolean canWinByTKO() {
		return false;
	}

	@Override
	public Long getExatraPointsForTKO() {
		return null;
	}

	@Override
	public boolean canExceedMaxPointsCount() {
		return false;
	}

}
