/**
 * The file JosuiRulesManager.java was created on 27 february 2018 at 23:33:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import com.ashihara.enums.UIC;

public class NikoStyleRulesManager implements RulesManager {
	
	private final UIC uic;

	public NikoStyleRulesManager(UIC uic) {
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
		return 3;
	}

	@Override
	public Long getWarningIncreaseCount(long warningCount) {
		if (warningCount == 2) {
			return 1L;
		} else if (warningCount == 3) {
			return 2L;
		} else if (warningCount == 4) {
			return 3L;
		} else {
			return null;
		}
	}

	@Override
	public Long getWarningDecreaseCount(long warningCount) {
		if (warningCount == 3) {
			return 3L;
		} else if (warningCount == 2) {
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
		return 7;
	}

	@Override
	public Long getPointsDifferenceForWin() {
		return Long.MAX_VALUE;
	}

	@Override
	public boolean copyPointsAndWarningsToTheNextRound() {
		return true;
	}

	@Override
	public boolean canWinByJudgeDecision() {
		return false;
	}

	@Override
	public Long getMaxPointsDifferenceForTheNextRound() {
		return null;
	}

	@Override
	public boolean sumFirstAndSecondPenaltyCategory() {
		return true;
	}

	@Override
	public Long getWarningIncreaseCount(
			long firstCategoryWarningCount,
			long secondCategoryWarningCount,
			boolean isFirstCategoryCountIncreased
	) {
		if (firstCategoryWarningCount <= 1 && secondCategoryWarningCount <= 1) {
			return null;
		} else if (firstCategoryWarningCount < 1) {
			return getWarningIncreaseCount(secondCategoryWarningCount);
		} else if (secondCategoryWarningCount < 1) {
			return getWarningIncreaseCount(firstCategoryWarningCount);
		} else {
			if (
					(firstCategoryWarningCount == 1 && isFirstCategoryCountIncreased) ||
					(secondCategoryWarningCount == 1 && !isFirstCategoryCountIncreased)
			) {
				return null;
			} else {
				long sum = firstCategoryWarningCount + secondCategoryWarningCount - 1;
				return getWarningIncreaseCount(sum);
			}
		}
	}

	@Override
	public Long getWarningDecreaseCount(
			long firstCategoryWarningCount,
			long secondCategoryWarningCount,
			boolean isFirstCategoryCountDecreased
	) {
		if (firstCategoryWarningCount <= 0 && secondCategoryWarningCount <= 0) {
			return null;
		} else if (firstCategoryWarningCount <= 0 && !isFirstCategoryCountDecreased) {
			return getWarningDecreaseCount(secondCategoryWarningCount);
		} else if (secondCategoryWarningCount <= 0 && isFirstCategoryCountDecreased) {
			return getWarningDecreaseCount(firstCategoryWarningCount);
		} else {
			if (
					(firstCategoryWarningCount == 0 && isFirstCategoryCountDecreased) ||
					(secondCategoryWarningCount == 0 && !isFirstCategoryCountDecreased)
			) {
				return null;
			} else {
				long sum = firstCategoryWarningCount + secondCategoryWarningCount - 1;
				return getWarningDecreaseCount(sum);
			}
		}
	}

	@Override
	public Long getMaxSumPenaltyCount() {
		return 5L;
	}

}
