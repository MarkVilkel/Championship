package com.ashihara.datamanagement.core.persistence.exception;

public class AKBusinessException extends AKException {
	private static final long serialVersionUID = 1L;
	
	public AKBusinessException(Exception e){
		super(e);
	}
	
	public AKBusinessException(String msg){
		super(msg);
	}
}
