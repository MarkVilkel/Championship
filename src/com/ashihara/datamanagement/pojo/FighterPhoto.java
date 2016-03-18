/**
 * The file FighterPhoto.java was created on 2010.4.4 at 13:38:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

public class FighterPhoto extends AbstractBlob {
	private static final long serialVersionUID = 1L;
	
	private Fighter fighter;

	public Fighter getFighter() {
		return fighter;
	}

	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fighter == null) ? 0 : fighter.hashCode());
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
		FighterPhoto other = (FighterPhoto) obj;
		if (fighter == null) {
			if (other.fighter != null)
				return false;
		} else if (!fighter.equals(other.fighter))
			return false;
		return true;
	}
}
