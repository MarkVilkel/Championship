/**
 * The file GroupChampionshipFighter.java was created on 2010.6.4 at 20:24:10
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class GroupChampionshipFighter extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private FightingGroup fightingGroup;
	private ChampionshipFighter championshipFighter;
	private Long olympicLevel; // used for olympic system, for initial fighters set
	
	public GroupChampionshipFighter() {
		olympicLevel = Long.valueOf(0);// by default every fighter is on zero level
	}
	
	public FightingGroup getFightingGroup() {
		return fightingGroup;
	}
	public void setFightingGroup(FightingGroup fightingGroup) {
		this.fightingGroup = fightingGroup;
	}
	public ChampionshipFighter getChampionshipFighter() {
		return championshipFighter;
	}
	public void setChampionshipFighter(ChampionshipFighter championshipFighter) {
		this.championshipFighter = championshipFighter;
	}
	
	@Override
	public String toString() {
		return String.valueOf(fightingGroup) + " " + String.valueOf(championshipFighter);
	}
	public Long getOlympicLevel() {
		return olympicLevel;
	}
	public void setOlympicLevel(Long olympicLevel) {
		this.olympicLevel = olympicLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((championshipFighter == null) ? 0 : championshipFighter
						.hashCode());
		result = prime * result
				+ ((fightingGroup == null) ? 0 : fightingGroup.hashCode());
		result = prime * result
				+ ((olympicLevel == null) ? 0 : olympicLevel.hashCode());
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
		GroupChampionshipFighter other = (GroupChampionshipFighter) obj;
		if (championshipFighter == null) {
			if (other.championshipFighter != null)
				return false;
		} else if (!championshipFighter.equals(other.championshipFighter))
			return false;
		if (fightingGroup == null) {
			if (other.fightingGroup != null)
				return false;
		} else if (!fightingGroup.equals(other.fightingGroup))
			return false;
		if (olympicLevel == null) {
			if (other.olympicLevel != null)
				return false;
		} else if (!olympicLevel.equals(other.olympicLevel))
			return false;
		return true;
	}

}
