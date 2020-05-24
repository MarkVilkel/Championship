/**
 * The file FightPlanItem.java was created on 26 февр. 2020 г. at 20:43:03
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo.wraper;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.ui.core.table.FakeRow;

public class FightResultForPlan implements FakeRow {

	private final FightResult fightResult;
	private final FightingGroup fightingGroup;
	private String numberInPlan;
	private final boolean fake;
	
	public FightResultForPlan() {
		this(null, null, false);
	}
	
	public FightResultForPlan(
			FightResult fightResult,
			FightingGroup fightingGroup,
			boolean fake
	) {
		this.fightResult = fightResult;
		this.fightingGroup = fightingGroup;
		this.fake = fake;
	}

	public FightResult getFightResult() {
		return fightResult;
	}
	
	
	public GroupChampionshipFighter getFirstFighter() {
		return fightResult == null ? null : fightResult.getFirstFighter();
	}
	
	public GroupChampionshipFighter getSecondFighter() {
		return fightResult == null ? null : fightResult.getSecondFighter();
	}
	
	public Long getFirstFighterPointsForWin() {
		return fightResult == null ? null : fightResult.getFirstFighterPointsForWin();
	}
	
	public Long getFirstFighterPoints() {
		return fightResult == null ? null : fightResult.getFirstFighterPoints();
	}
	
	public Long getSecondFighterPointsForWin() {
		return fightResult == null ? null : fightResult.getSecondFighterPointsForWin();
	}
	
	public Long getSecondFighterPoints() {
		return fightResult == null ? null : fightResult.getSecondFighterPoints();
	}

	public FightingGroup getFightingGroup() {
		return fightingGroup;
	}

	public String getNumberInPlan() {
		return numberInPlan;
	}

	public void setNumberInPlan(String numberInPlan) {
		this.numberInPlan = numberInPlan;
	}

	@Override
	public String toString() {
		return "FightResultForPlan [fightResult=" + fightResult + ", fightingGroup=" + fightingGroup + ", numberInPlan="
				+ numberInPlan + "]";
	}

	@Override
	public boolean isFake() {
		return fake;
	}

}
