/**
 * The file ChampionshipFighter.java was created on 2010.6.4 at 20:21:57
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class ChampionshipFighter extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Championship championship;
	private Fighter fighter;
	private Long number;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Championship getChampionship() {
		return championship;
	}
	public void setChampionship(Championship championship) {
		this.championship = championship;
	}
	public Fighter getFighter() {
		return fighter;
	}
	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}
	
	@Override
	public String toString() {
		if (getFighter() != null) {
			String result = getFighter().toString();
			if (getNumber() != null) {
				result = "<" + getNumber() + "> " + result;
			}
			return result;
		}
		return super.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((championship == null) ? 0 : championship.hashCode());
		result = prime * result + ((fighter == null) ? 0 : fighter.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		ChampionshipFighter other = (ChampionshipFighter) obj;
		if (championship == null) {
			if (other.championship != null)
				return false;
		} else if (!championship.equals(other.championship))
			return false;
		if (fighter == null) {
			if (other.fighter != null)
				return false;
		} else if (!fighter.equals(other.fighter))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
