/**
 * The file Group.java was created on 2010.21.3 at 14:01:23
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class FightingGroup extends BaseDo {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String type;
	private String status;
	private String gender;
	private String tatami;
	
	private Championship championship;
	private YearWeightCategoryLink yearWeightCategoryLink;
	private ChampionshipPlan plan;
	private Integer orderInPlan;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	@Override
	public String toString() {
		if (getName() != null) {
			return getName();
		}
		return super.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public YearWeightCategoryLink getYearWeightCategoryLink() {
		return yearWeightCategoryLink;
	}

	public void setYearWeightCategoryLink(
			YearWeightCategoryLink yearWeightCategoryLink) {
		this.yearWeightCategoryLink = yearWeightCategoryLink;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTatami() {
		return tatami;
	}

	public void setTatami(String tatami) {
		this.tatami = tatami;
	}

	public ChampionshipPlan getPlan() {
		return plan;
	}

	public void setPlan(ChampionshipPlan plan) {
		this.plan = plan;
	}
	
	public Integer getOrderInPlan() {
		return orderInPlan;
	}

	public void setOrderInPlan(Integer orderInPlan) {
		this.orderInPlan = orderInPlan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((championship == null) ? 0 : championship.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderInPlan == null) ? 0 : orderInPlan.hashCode());
		result = prime * result + ((plan == null) ? 0 : plan.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tatami == null) ? 0 : tatami.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((yearWeightCategoryLink == null) ? 0 : yearWeightCategoryLink.hashCode());
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
		FightingGroup other = (FightingGroup) obj;
		if (championship == null) {
			if (other.championship != null)
				return false;
		} else if (!championship.equals(other.championship))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderInPlan == null) {
			if (other.orderInPlan != null)
				return false;
		} else if (!orderInPlan.equals(other.orderInPlan))
			return false;
		if (plan == null) {
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tatami == null) {
			if (other.tatami != null)
				return false;
		} else if (!tatami.equals(other.tatami))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (yearWeightCategoryLink == null) {
			if (other.yearWeightCategoryLink != null)
				return false;
		} else if (!yearWeightCategoryLink.equals(other.yearWeightCategoryLink))
			return false;
		return true;
	}

}
