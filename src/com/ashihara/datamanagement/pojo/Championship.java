/**
 * The file Championship.java was created on 2010.21.3 at 12:50:45
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import java.util.Date;

import com.rtu.persistence.core.BaseDo;

public class Championship extends BaseDo {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date beginningDate;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((beginningDate == null) ? 0 : beginningDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Championship other = (Championship) obj;
		if (beginningDate == null) {
			if (other.beginningDate != null)
				return false;
		} else if (!beginningDate.equals(other.beginningDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
