/**
 * The file Fighter.java was created on 2010.31.1 at 14:00:07
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ashihara.utils.MathUtils;
import com.rtu.persistence.core.BaseDo;

public class Fighter extends BaseDo {
	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private Long number;
	private Date birthday;
	private Double weight;
	private Long kyu;
	private Long dan;
	private String gender;
	
	private Country country;
	
//  -------- Calculated --------	
	public Long getFullYearsOld() {
		if (getBirthday() == null) {
			return null;
		}
		long yearsOld = System.currentTimeMillis() - getBirthday().getTime();
		return yearsOld / (1000l * 60l * 60l * 24l * 365l);
	
	}
	
	private Integer participanceInChampionshipsCount;
//	----------------------------
	

	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName()).append(" ").append(getSurname());
		
		if (getCountry() != null && getWeight() != null) {
			builder.append(" (").append(getCountry().getCode());
			builder.append(", ").append(MathUtils.removeTrailingZeros(getWeight())).append(")");
		} else {
			if (getWeight() != null) {
				builder.append(" (").append(MathUtils.removeTrailingZeros(getWeight())).append("kg)");
			}
			if (getCountry() != null) {
				builder.append(" (").append(" ").append(getCountry().getCode()).append(")");
			}
		}
		
		
		return builder.toString();
	}
	
	public String toShortString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName()).append(" ").append(getSurname());
		return builder.toString();
	}

	public String toLongString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName()).append(" ").append(getSurname());
		
		List<String> items = new ArrayList<>();
		if (getCountry() != null) {
			items.add(getCountry().getCode());
		}
		if (getWeight() != null) {
			items.add(MathUtils.removeTrailingZeros(getWeight()) + "kg");
		}
		if (getDan() != null) {
			items.add(getDan() + "dan");
		}
		if (getKyu() != null) {
			items.add(getKyu() + "kyu");
		}
		
		if (!items.isEmpty()) {
			builder.append(" (");
			for (int i = 0; i < items.size(); i++) {
				builder.append(items.get(i));
				if (i < items.size() - 1) {
					builder.append(", ");
				}
			}
			builder.append(")");
		}
		
		return builder.toString();
	}

	public Long getKyu() {
		return kyu;
	}
	public void setKyu(Long kyu) {
		this.kyu = kyu;
	}
	public Long getDan() {
		return dan;
	}
	public void setDan(Long dan) {
		this.dan = dan;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getParticipanceInChampionshipsCount() {
		return participanceInChampionshipsCount;
	}
	public void setParticipanceInChampionshipsCount(Integer participanceInChampionshipsCount) {
		this.participanceInChampionshipsCount = participanceInChampionshipsCount;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((dan == null) ? 0 : dan.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((kyu == null) ? 0 : kyu.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Fighter other = (Fighter) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (dan == null) {
			if (other.dan != null)
				return false;
		} else if (!dan.equals(other.dan))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (kyu == null) {
			if (other.kyu != null)
				return false;
		} else if (!kyu.equals(other.kyu))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
}
