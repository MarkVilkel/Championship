/**
 * The file YearWeightCategoryLink.java was created on 2010.4.4 at 13:53:45
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class YearWeightCategoryLink extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private YearCategory yearCategory;
	private WeightCategory weightCategory;
	
	public YearCategory getYearCategory() {
		return yearCategory;
	}
	public void setYearCategory(YearCategory yearCategory) {
		this.yearCategory = yearCategory;
	}
	public WeightCategory getWeightCategory() {
		return weightCategory;
	}
	public void setWeightCategory(WeightCategory weightCategory) {
		this.weightCategory = weightCategory;
	}
	
	public String toString() {
		if (getWeightCategory() != null && getYearCategory() != null) {
			return getYearCategory().toString() +  ", " + getWeightCategory().toString();
		}
		return super.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((weightCategory == null) ? 0 : weightCategory.hashCode());
		result = prime * result
				+ ((yearCategory == null) ? 0 : yearCategory.hashCode());
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
		YearWeightCategoryLink other = (YearWeightCategoryLink) obj;
		if (weightCategory == null) {
			if (other.weightCategory != null)
				return false;
		} else if (!weightCategory.equals(other.weightCategory))
			return false;
		if (yearCategory == null) {
			if (other.yearCategory != null)
				return false;
		} else if (!yearCategory.equals(other.yearCategory))
			return false;
		return true;
	}
	
}
