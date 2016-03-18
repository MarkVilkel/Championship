/**
 * The file WeightCategory.java was created on 2010.4.4 at 13:51:05
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class WeightCategory extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Double fromWeight;
	private Double toWeight;
	
	public Double getFromWeight() {
		return fromWeight;
	}
	public void setFromWeight(Double weight) {
		this.fromWeight = weight;
	}
	public Double getToWeight() {
		return toWeight;
	}
	public void setToWeight(Double weight) {
		this.toWeight = weight;
	}

	public String toString() {
		return getFromWeight() + " - " + getToWeight();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fromWeight == null) ? 0 : fromWeight.hashCode());
		result = prime * result
				+ ((toWeight == null) ? 0 : toWeight.hashCode());
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
		WeightCategory other = (WeightCategory) obj;
		if (fromWeight == null) {
			if (other.fromWeight != null)
				return false;
		} else if (!fromWeight.equals(other.fromWeight))
			return false;
		if (toWeight == null) {
			if (other.toWeight != null)
				return false;
		} else if (!toWeight.equals(other.toWeight))
			return false;
		return true;
	}

}
