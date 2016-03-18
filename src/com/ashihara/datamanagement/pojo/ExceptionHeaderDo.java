/**
 * The file ExceptionHeaderDo was created on 2008.28.5 at 10:38:06
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class ExceptionHeaderDo extends BaseDo {
	private static final long serialVersionUID = 1L;

	private String headerCause;
	private String description;

	public String getHeaderCause() {
		return headerCause;
	}

	public void setHeaderCause(String headerCause) {
		this.headerCause = headerCause;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((headerCause == null) ? 0 : headerCause.hashCode());
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
		ExceptionHeaderDo other = (ExceptionHeaderDo) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (headerCause == null) {
			if (other.headerCause != null)
				return false;
		} else if (!headerCause.equals(other.headerCause))
			return false;
		return true;
	}
}
