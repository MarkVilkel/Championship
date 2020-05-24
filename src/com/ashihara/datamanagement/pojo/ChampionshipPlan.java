/**
 * The file ChampionshipPlan.java was created on 12 февр. 2020 г. at 21:20:49
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class ChampionshipPlan extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Championship championship;
	private Boolean finalsAtTheEnd;

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	public Boolean getFinalsAtTheEnd() {
		return finalsAtTheEnd;
	}

	public void setFinalsAtTheEnd(Boolean finalsAtTheEnd) {
		this.finalsAtTheEnd = finalsAtTheEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((championship == null) ? 0 : championship.hashCode());
		result = prime * result + ((finalsAtTheEnd == null) ? 0 : finalsAtTheEnd.hashCode());
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
		ChampionshipPlan other = (ChampionshipPlan) obj;
		if (championship == null) {
			if (other.championship != null)
				return false;
		} else if (!championship.equals(other.championship))
			return false;
		if (finalsAtTheEnd == null) {
			if (other.finalsAtTheEnd != null)
				return false;
		} else if (!finalsAtTheEnd.equals(other.finalsAtTheEnd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChampionshipPlan [championship=" + championship + ", finalsAtTheEnd=" + finalsAtTheEnd + "]";
	}

}
