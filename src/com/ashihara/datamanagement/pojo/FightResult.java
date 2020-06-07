/**
 * The file FightResult.java was created on 2010.13.4 at 23:21:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import java.util.ArrayList;
import java.util.List;

import com.rtu.persistence.core.BaseDo;

public class FightResult extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private GroupChampionshipFighter firstFighter;
	private GroupChampionshipFighter secondFighter;
	
	private Long firstFighterPointsForWin;
	private Long firstFighterPoints;
	private Long firstFighterFirstCategoryWarnings;
	private Long firstFighterSecondCategoryWarnings;
	
	private Long secondFighterPointsForWin;
	private Long secondFighterPoints;
	private Long secondFighterFirstCategoryWarnings;
	private Long secondFighterSecondCategoryWarnings;

	
	private FightResult previousRoundFightResult;
	private FightResult nextRoundFightResult;
	
	private Long roundNumber;
	
	private Long olympicLevel; // x coordinate
	private Long olympicPositionOnLevel; // y coordinate

	private Boolean firstFighterWinByJudgeDecision;
	private Boolean secondFighterWinByJudgeDecision;

	private Boolean firstFighterWinByTKO;
	private Boolean secondFighterWinByTKO;

	private GroupChampionshipFighter redFighter; // calculated
	private GroupChampionshipFighter blueFighter; // calculated
	
	private FightResult firstFighterParent; // calculated
	private FightResult secondFighterParent; // calculated
	private List<FightResult> children = new ArrayList<FightResult>(); // calculated
	
	
	public GroupChampionshipFighter getFirstFighter() {
		return firstFighter;
	}
	public void setFirstFighter(GroupChampionshipFighter firstFighter) {
		this.firstFighter = firstFighter;
	}
	public GroupChampionshipFighter getSecondFighter() {
		return secondFighter;
	}
	public void setSecondFighter(GroupChampionshipFighter secondFighter) {
		this.secondFighter = secondFighter;
	}
	public Long getFirstFighterPoints() {
		return firstFighterPoints;
	}
	public void setFirstFighterPoints(Long firstFighterPoints) {
		this.firstFighterPoints = firstFighterPoints;
	}
	public Long getSecondFighterPoints() {
		return secondFighterPoints;
	}
	public void setSecondFighterPoints(Long secondFighterPoints) {
		this.secondFighterPoints = secondFighterPoints;
	}
	public GroupChampionshipFighter getRedFighter() {
		return redFighter;
	}
	public void setRedFighter(GroupChampionshipFighter redFighter) {
		this.redFighter = redFighter;
	}
	public GroupChampionshipFighter getBlueFighter() {
		return blueFighter;
	}
	public void setBlueFighter(GroupChampionshipFighter blueFighter) {
		this.blueFighter = blueFighter;
	}
	public FightResult getPreviousRoundFightResult() {
		return previousRoundFightResult;
	}
	public void setPreviousRoundFightResult(FightResult previousRoundFightResult) {
		this.previousRoundFightResult = previousRoundFightResult;
	}
	public Long getFirstFighterFirstCategoryWarnings() {
		return firstFighterFirstCategoryWarnings;
	}
	public void setFirstFighterFirstCategoryWarnings(
			Long firstFighterFirstCategoryWarnings) {
		this.firstFighterFirstCategoryWarnings = firstFighterFirstCategoryWarnings;
	}
	public Long getFirstFighterSecondCategoryWarnings() {
		return firstFighterSecondCategoryWarnings;
	}
	public void setFirstFighterSecondCategoryWarnings(
			Long firstFighterSecondCategoryWarnings) {
		this.firstFighterSecondCategoryWarnings = firstFighterSecondCategoryWarnings;
	}
	public Long getSecondFighterFirstCategoryWarnings() {
		return secondFighterFirstCategoryWarnings;
	}
	public void setSecondFighterFirstCategoryWarnings(
			Long secondFighterFirstCategoryWarnings) {
		this.secondFighterFirstCategoryWarnings = secondFighterFirstCategoryWarnings;
	}
	public Long getSecondFighterSecondCategoryWarnings() {
		return secondFighterSecondCategoryWarnings;
	}
	public void setSecondFighterSecondCategoryWarnings(
			Long secondFighterSecondCategoryWarnings) {
		this.secondFighterSecondCategoryWarnings = secondFighterSecondCategoryWarnings;
	}
	public FightResult getNextRoundFightResult() {
		return nextRoundFightResult;
	}
	public void setNextRoundFightResult(FightResult nextRoundFightResult) {
		this.nextRoundFightResult = nextRoundFightResult;
	}
	public Long getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(Long roundNumber) {
		this.roundNumber = roundNumber;
	}
	public Long getFirstFighterPointsForWin() {
		return firstFighterPointsForWin;
	}
	public void setFirstFighterPointsForWin(Long firstFighterPointsForWin) {
		this.firstFighterPointsForWin = firstFighterPointsForWin;
	}
	public Long getSecondFighterPointsForWin() {
		return secondFighterPointsForWin;
	}
	public void setSecondFighterPointsForWin(Long secondFighterPointsForWin) {
		this.secondFighterPointsForWin = secondFighterPointsForWin;
	}
	public Long getOlympicLevel() {
		return olympicLevel;
	}
	public void setOlympicLevel(Long olympicLevel) {
		this.olympicLevel = olympicLevel;
	}
	public Long getOlympicPositionOnLevel() {
		return olympicPositionOnLevel;
	}
	public void setOlympicPositionOnLevel(Long olympicPositionOnLevel) {
		this.olympicPositionOnLevel = olympicPositionOnLevel;
	}

	@Override
	public String toString() {
		if (firstFighter == null && secondFighter == null) {
			return super.toString();
		}
		
		String result = 
			(firstFighter != null ? firstFighter.getChampionshipFighter().getFighter().toString() : "") +
			" VS " +
			(secondFighter != null ? secondFighter.getChampionshipFighter().getFighter().toString() : "");
		return result;
	}
	
	public FightResult getFirstFighterParent() {
		return firstFighterParent;
	}
	public void setFirstFighterParent(FightResult firstFighterParent) {
		this.firstFighterParent = firstFighterParent;
	}
	public FightResult getSecondFighterParent() {
		return secondFighterParent;
	}
	public void setSecondFighterParent(FightResult secondFighterParent) {
		this.secondFighterParent = secondFighterParent;
	}
	public List<FightResult> getChildren() {
		return children;
	}
	public void setChildren(List<FightResult> children) {
		this.children = children;
	}
	public Boolean getFirstFighterWinByJudgeDecision() {
		return firstFighterWinByJudgeDecision;
	}
	public void setFirstFighterWinByJudgeDecision(Boolean firstFighterWinByJudgeDecision) {
		this.firstFighterWinByJudgeDecision = firstFighterWinByJudgeDecision;
	}
	public Boolean getSecondFighterWinByJudgeDecision() {
		return secondFighterWinByJudgeDecision;
	}
	public void setSecondFighterWinByJudgeDecision(Boolean secondFighterWinByJudgeDecision) {
		this.secondFighterWinByJudgeDecision = secondFighterWinByJudgeDecision;
	}
	public Boolean getFirstFighterWinByTKO() {
		return firstFighterWinByTKO;
	}
	public void setFirstFighterWinByTKO(Boolean firstFighterWinByTKO) {
		this.firstFighterWinByTKO = firstFighterWinByTKO;
	}
	public Boolean getSecondFighterWinByTKO() {
		return secondFighterWinByTKO;
	}
	public void setSecondFighterWinByTKO(Boolean secondFighterWinByTKO) {
		this.secondFighterWinByTKO = secondFighterWinByTKO;
	}

	public boolean isFirstFighterWon(long criticalWarningCount) {
		boolean result =
						getFirstFighterFirstCategoryWarnings() != null && getFirstFighterFirstCategoryWarnings() >= criticalWarningCount ||
						getFirstFighterSecondCategoryWarnings() != null && getFirstFighterSecondCategoryWarnings() >= criticalWarningCount ||
						Boolean.TRUE.equals(getFirstFighterWinByTKO()) ||
						Boolean.TRUE.equals(getFirstFighterWinByJudgeDecision()) ||
						(getFirstFighterPoints() != null && getSecondFighterPoints() != null && getFirstFighterPoints().longValue() > getSecondFighterPoints().longValue())
						;
		return result;
	}

	public boolean isSecondFighterWon(long criticalWarningCount) {
		boolean result =
				getSecondFighterFirstCategoryWarnings() != null && getSecondFighterFirstCategoryWarnings() >= criticalWarningCount ||
				getSecondFighterFirstCategoryWarnings() != null && getSecondFighterSecondCategoryWarnings() >= criticalWarningCount ||
				Boolean.TRUE.equals(getSecondFighterWinByTKO()) ||
			    Boolean.TRUE.equals(getSecondFighterWinByJudgeDecision()) ||
			    (getFirstFighterPoints() != null && getSecondFighterPoints() != null && getFirstFighterPoints().longValue() < getSecondFighterPoints().longValue())
			    ;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((blueFighter == null) ? 0 : blueFighter.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((firstFighter == null) ? 0 : firstFighter.hashCode());
		result = prime * result
				+ ((firstFighterFirstCategoryWarnings == null) ? 0 : firstFighterFirstCategoryWarnings.hashCode());
		result = prime * result + ((firstFighterParent == null) ? 0 : firstFighterParent.hashCode());
		result = prime * result + ((firstFighterPoints == null) ? 0 : firstFighterPoints.hashCode());
		result = prime * result + ((firstFighterPointsForWin == null) ? 0 : firstFighterPointsForWin.hashCode());
		result = prime * result
				+ ((firstFighterSecondCategoryWarnings == null) ? 0 : firstFighterSecondCategoryWarnings.hashCode());
		result = prime * result
				+ ((firstFighterWinByJudgeDecision == null) ? 0 : firstFighterWinByJudgeDecision.hashCode());
		result = prime * result + ((firstFighterWinByTKO == null) ? 0 : firstFighterWinByTKO.hashCode());
		result = prime * result + ((nextRoundFightResult == null) ? 0 : nextRoundFightResult.hashCode());
		result = prime * result + ((olympicLevel == null) ? 0 : olympicLevel.hashCode());
		result = prime * result + ((olympicPositionOnLevel == null) ? 0 : olympicPositionOnLevel.hashCode());
		result = prime * result + ((previousRoundFightResult == null) ? 0 : previousRoundFightResult.hashCode());
		result = prime * result + ((redFighter == null) ? 0 : redFighter.hashCode());
		result = prime * result + ((roundNumber == null) ? 0 : roundNumber.hashCode());
		result = prime * result + ((secondFighter == null) ? 0 : secondFighter.hashCode());
		result = prime * result
				+ ((secondFighterFirstCategoryWarnings == null) ? 0 : secondFighterFirstCategoryWarnings.hashCode());
		result = prime * result + ((secondFighterParent == null) ? 0 : secondFighterParent.hashCode());
		result = prime * result + ((secondFighterPoints == null) ? 0 : secondFighterPoints.hashCode());
		result = prime * result + ((secondFighterPointsForWin == null) ? 0 : secondFighterPointsForWin.hashCode());
		result = prime * result
				+ ((secondFighterSecondCategoryWarnings == null) ? 0 : secondFighterSecondCategoryWarnings.hashCode());
		result = prime * result
				+ ((secondFighterWinByJudgeDecision == null) ? 0 : secondFighterWinByJudgeDecision.hashCode());
		result = prime * result + ((secondFighterWinByTKO == null) ? 0 : secondFighterWinByTKO.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FightResult other = (FightResult) obj;
		if (blueFighter == null) {
			if (other.blueFighter != null)
				return false;
		} else if (!blueFighter.equals(other.blueFighter))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (firstFighter == null) {
			if (other.firstFighter != null)
				return false;
		} else if (!firstFighter.equals(other.firstFighter))
			return false;
		if (firstFighterFirstCategoryWarnings == null) {
			if (other.firstFighterFirstCategoryWarnings != null)
				return false;
		} else if (!firstFighterFirstCategoryWarnings.equals(other.firstFighterFirstCategoryWarnings))
			return false;
		if (firstFighterParent == null) {
			if (other.firstFighterParent != null)
				return false;
		} else if (!firstFighterParent.equals(other.firstFighterParent))
			return false;
		if (firstFighterPoints == null) {
			if (other.firstFighterPoints != null)
				return false;
		} else if (!firstFighterPoints.equals(other.firstFighterPoints))
			return false;
		if (firstFighterPointsForWin == null) {
			if (other.firstFighterPointsForWin != null)
				return false;
		} else if (!firstFighterPointsForWin.equals(other.firstFighterPointsForWin))
			return false;
		if (firstFighterSecondCategoryWarnings == null) {
			if (other.firstFighterSecondCategoryWarnings != null)
				return false;
		} else if (!firstFighterSecondCategoryWarnings.equals(other.firstFighterSecondCategoryWarnings))
			return false;
		if (firstFighterWinByJudgeDecision == null) {
			if (other.firstFighterWinByJudgeDecision != null)
				return false;
		} else if (!firstFighterWinByJudgeDecision.equals(other.firstFighterWinByJudgeDecision))
			return false;
		if (firstFighterWinByTKO == null) {
			if (other.firstFighterWinByTKO != null)
				return false;
		} else if (!firstFighterWinByTKO.equals(other.firstFighterWinByTKO))
			return false;
		if (nextRoundFightResult == null) {
			if (other.nextRoundFightResult != null)
				return false;
		} else if (!nextRoundFightResult.equals(other.nextRoundFightResult))
			return false;
		if (olympicLevel == null) {
			if (other.olympicLevel != null)
				return false;
		} else if (!olympicLevel.equals(other.olympicLevel))
			return false;
		if (olympicPositionOnLevel == null) {
			if (other.olympicPositionOnLevel != null)
				return false;
		} else if (!olympicPositionOnLevel.equals(other.olympicPositionOnLevel))
			return false;
		if (previousRoundFightResult == null) {
			if (other.previousRoundFightResult != null)
				return false;
		} else if (!previousRoundFightResult.equals(other.previousRoundFightResult))
			return false;
		if (redFighter == null) {
			if (other.redFighter != null)
				return false;
		} else if (!redFighter.equals(other.redFighter))
			return false;
		if (roundNumber == null) {
			if (other.roundNumber != null)
				return false;
		} else if (!roundNumber.equals(other.roundNumber))
			return false;
		if (secondFighter == null) {
			if (other.secondFighter != null)
				return false;
		} else if (!secondFighter.equals(other.secondFighter))
			return false;
		if (secondFighterFirstCategoryWarnings == null) {
			if (other.secondFighterFirstCategoryWarnings != null)
				return false;
		} else if (!secondFighterFirstCategoryWarnings.equals(other.secondFighterFirstCategoryWarnings))
			return false;
		if (secondFighterParent == null) {
			if (other.secondFighterParent != null)
				return false;
		} else if (!secondFighterParent.equals(other.secondFighterParent))
			return false;
		if (secondFighterPoints == null) {
			if (other.secondFighterPoints != null)
				return false;
		} else if (!secondFighterPoints.equals(other.secondFighterPoints))
			return false;
		if (secondFighterPointsForWin == null) {
			if (other.secondFighterPointsForWin != null)
				return false;
		} else if (!secondFighterPointsForWin.equals(other.secondFighterPointsForWin))
			return false;
		if (secondFighterSecondCategoryWarnings == null) {
			if (other.secondFighterSecondCategoryWarnings != null)
				return false;
		} else if (!secondFighterSecondCategoryWarnings.equals(other.secondFighterSecondCategoryWarnings))
			return false;
		if (secondFighterWinByJudgeDecision == null) {
			if (other.secondFighterWinByJudgeDecision != null)
				return false;
		} else if (!secondFighterWinByJudgeDecision.equals(other.secondFighterWinByJudgeDecision))
			return false;
		if (secondFighterWinByTKO == null) {
			if (other.secondFighterWinByTKO != null)
				return false;
		} else if (!secondFighterWinByTKO.equals(other.secondFighterWinByTKO))
			return false;
		return true;
	}
	
}
