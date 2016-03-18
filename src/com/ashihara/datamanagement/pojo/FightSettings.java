/**
 * The file Points.java was created on 2010.13.4 at 22:59:07
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class FightSettings extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Long forWinning;
	private Long forDraw;
	private Long forLoosing;
	
	private Long firstRoundTime; // in seconds
	private Long secondRoundTime; // in seconds
	private Long otherRoundTime; // in seconds
	
	private String tatami;
	
	
	public Long getForWinning() {
		return forWinning;
	}
	public void setForWinning(Long forWinning) {
		this.forWinning = forWinning;
	}
	public Long getForDraw() {
		return forDraw;
	}
	public void setForDraw(Long forDraw) {
		this.forDraw = forDraw;
	}
	public Long getForLoosing() {
		return forLoosing;
	}
	public void setForLoosing(Long forLoosing) {
		this.forLoosing = forLoosing;
	}
	public String getTatami() {
		return tatami;
	}
	public void setTatami(String tatami) {
		this.tatami = tatami;
	}
	public Long getFirstRoundTime() {
		return firstRoundTime;
	}
	public void setFirstRoundTime(Long firstRoundTime) {
		this.firstRoundTime = firstRoundTime;
	}
	public Long getSecondRoundTime() {
		return secondRoundTime;
	}
	public void setSecondRoundTime(Long secondRoundTime) {
		this.secondRoundTime = secondRoundTime;
	}
	public Long getOtherRoundTime() {
		return otherRoundTime;
	}
	public void setOtherRoundTime(Long otherRoundTime) {
		this.otherRoundTime = otherRoundTime;
	}
	public long getTimeForRound(long roundNumber) {
		if (roundNumber <= 1) {
			return getFirstRoundTime() * 1000;
		}
		else if (roundNumber <= 2) {
			return getSecondRoundTime() * 1000;
		}
		else {
			return getOtherRoundTime() * 1000;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((firstRoundTime == null) ? 0 : firstRoundTime.hashCode());
		result = prime * result + ((forDraw == null) ? 0 : forDraw.hashCode());
		result = prime * result
				+ ((forLoosing == null) ? 0 : forLoosing.hashCode());
		result = prime * result
				+ ((forWinning == null) ? 0 : forWinning.hashCode());
		result = prime * result
				+ ((otherRoundTime == null) ? 0 : otherRoundTime.hashCode());
		result = prime * result
				+ ((secondRoundTime == null) ? 0 : secondRoundTime.hashCode());
		result = prime * result + ((tatami == null) ? 0 : tatami.hashCode());
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
		FightSettings other = (FightSettings) obj;
		if (firstRoundTime == null) {
			if (other.firstRoundTime != null)
				return false;
		} else if (!firstRoundTime.equals(other.firstRoundTime))
			return false;
		if (forDraw == null) {
			if (other.forDraw != null)
				return false;
		} else if (!forDraw.equals(other.forDraw))
			return false;
		if (forLoosing == null) {
			if (other.forLoosing != null)
				return false;
		} else if (!forLoosing.equals(other.forLoosing))
			return false;
		if (forWinning == null) {
			if (other.forWinning != null)
				return false;
		} else if (!forWinning.equals(other.forWinning))
			return false;
		if (otherRoundTime == null) {
			if (other.otherRoundTime != null)
				return false;
		} else if (!otherRoundTime.equals(other.otherRoundTime))
			return false;
		if (secondRoundTime == null) {
			if (other.secondRoundTime != null)
				return false;
		} else if (!secondRoundTime.equals(other.secondRoundTime))
			return false;
		if (tatami == null) {
			if (other.tatami != null)
				return false;
		} else if (!tatami.equals(other.tatami))
			return false;
		return true;
	}

}
