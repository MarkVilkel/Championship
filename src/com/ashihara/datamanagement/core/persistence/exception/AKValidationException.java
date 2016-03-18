/**
 * The file AKValidationException.java was created on 2007.29.8 at 14:15:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.persistence.exception;

public class AKValidationException extends AKException {
	private static final long serialVersionUID = 1L;
	
	private Object userObject;
	
	public AKValidationException(Exception e){
		super(e);
	}
	
	public AKValidationException(String msg){
		super(msg);
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
}
