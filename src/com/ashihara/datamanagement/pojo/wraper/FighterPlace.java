/**
 * The file FighterPlace.java was created on 2010.4.11 at 14:38:58
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo.wraper;

import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;


public class FighterPlace {
	
	private GroupChampionshipFighter gcFighter;
	private Integer place;
	
	private Long pointsForWin;
	private Long points;
	private Long firstCategoryWarnings;
	private Long secondCategoryWarnings;
	private Long wonByJudgeDecisionCount;
	private Long wonByTKOCount;

	public FighterPlace() {
	}
	
	public FighterPlace(GroupChampionshipFighter gcFighter) {
		this.gcFighter = gcFighter;
		this.place = 0;
		this.points = 0l;
		this.pointsForWin = 0l;
		this.secondCategoryWarnings = 0l;
		this.firstCategoryWarnings = 0l;
		this.wonByJudgeDecisionCount = 0l;
		this.wonByTKOCount = 0l;
	}
	
	public GroupChampionshipFighter getGCFighter() {
		return gcFighter;
	}
	public void setGCFighter(GroupChampionshipFighter gcFighter) {
		this.gcFighter = gcFighter;
	}
	public Integer getPlace() {
		return place;
	}
	public void setPlace(Integer place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return String.valueOf(gcFighter.getChampionshipFighter().toString()) + " " + String.valueOf(pointsForWin);
	}

	public Long getPointsForWin() {
		return pointsForWin;
	}

	public void setPointsForWin(Long pointsForWin) {
		this.pointsForWin = pointsForWin;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public Long getFirstCategoryWarnings() {
		return firstCategoryWarnings;
	}

	public void setFirstCategoryWarnings(Long firstCategoryWarnings) {
		this.firstCategoryWarnings = firstCategoryWarnings;
	}

	public Long getSecondCategoryWarnings() {
		return secondCategoryWarnings;
	}

	public void setSecondCategoryWarnings(Long secondCategoryWarnings) {
		this.secondCategoryWarnings = secondCategoryWarnings;
	}

    public Long getWonByJudgeDecisionCount() {
        return wonByJudgeDecisionCount;
    }

    public void setWonByJudgeDecisionCount(Long wonByJudgeDecisionCount) {
        this.wonByJudgeDecisionCount = wonByJudgeDecisionCount;
    }

	public Long getWonByTKOCount() {
		return wonByTKOCount;
	}

	public void setWonByTKOCount(Long wonByTKOCount) {
		this.wonByTKOCount = wonByTKOCount;
	}

}
