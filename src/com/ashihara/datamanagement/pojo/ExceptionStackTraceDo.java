/**
 * The file ExceptionStackTraceDo was created on 2008.28.5 at 10:40:34
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import com.rtu.persistence.core.BaseDo;

public class ExceptionStackTraceDo extends BaseDo {
	private static final long serialVersionUID = 1L;

	private ExceptionHeaderDo exceptionHeaderDo;
	private String oneTraceLine;
	
	public ExceptionHeaderDo getExceptionHeaderDo() {
		return exceptionHeaderDo;
	}
	public void setExceptionHeaderDo(ExceptionHeaderDo exceptionHeaderDo) {
		this.exceptionHeaderDo = exceptionHeaderDo;
	}
	public String getOneTraceLine() {
		return oneTraceLine;
	}
	public void setOneTraceLine(String oneTraceLine) {
		this.oneTraceLine = oneTraceLine;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((exceptionHeaderDo == null) ? 0 : exceptionHeaderDo
						.hashCode());
		result = prime * result
				+ ((oneTraceLine == null) ? 0 : oneTraceLine.hashCode());
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
		ExceptionStackTraceDo other = (ExceptionStackTraceDo) obj;
		if (exceptionHeaderDo == null) {
			if (other.exceptionHeaderDo != null)
				return false;
		} else if (!exceptionHeaderDo.equals(other.exceptionHeaderDo))
			return false;
		if (oneTraceLine == null) {
			if (other.oneTraceLine != null)
				return false;
		} else if (!oneTraceLine.equals(other.oneTraceLine))
			return false;
		return true;
	}
}
