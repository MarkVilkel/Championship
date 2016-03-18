/**
 * The file YearCategory.java was created on 2010.4.4 at 13:51:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import java.util.ArrayList;
import java.util.List;

import com.rtu.persistence.core.BaseDo;

public class YearCategory extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private Long fromYear;
	private Long toYear;
	
//	Calculated
	private List<YearWeightCategoryLink> yearWeightCategories;
	
	public Long getFromYear() {
		return fromYear;
	}
	public void setFromYear(Long fromYear) {
		this.fromYear = fromYear;
	}
	public Long getToYear() {
		return toYear;
	}
	public void setToYear(Long toYear) {
		this.toYear = toYear;
	}
	
	public String toString() {
		return getFromYear() + " - " + getToYear();
	}
	
	public List<YearWeightCategoryLink> getYearWeightCategories() {
		return yearWeightCategories;
	}
	public void setYearWeightCategories(List<YearWeightCategoryLink> yearWeightCategories) {
		this.yearWeightCategories = yearWeightCategories;
	}

	public List<WeightCategory> getWeightCategories() {
		List<WeightCategory> weights = new ArrayList<WeightCategory>();
		for (YearWeightCategoryLink lnk : getYearWeightCategories()) {
			weights.add(lnk.getWeightCategory());
		}
		return weights;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fromYear == null) ? 0 : fromYear.hashCode());
		result = prime * result + ((toYear == null) ? 0 : toYear.hashCode());
		result = prime
				* result
				+ ((yearWeightCategories == null) ? 0 : yearWeightCategories
						.hashCode());
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
		YearCategory other = (YearCategory) obj;
		if (fromYear == null) {
			if (other.fromYear != null)
				return false;
		} else if (!fromYear.equals(other.fromYear))
			return false;
		if (toYear == null) {
			if (other.toYear != null)
				return false;
		} else if (!toYear.equals(other.toYear))
			return false;
		if (yearWeightCategories == null) {
			if (other.yearWeightCategories != null)
				return false;
		} else if (!yearWeightCategories.equals(other.yearWeightCategories))
			return false;
		return true;
	}
}
