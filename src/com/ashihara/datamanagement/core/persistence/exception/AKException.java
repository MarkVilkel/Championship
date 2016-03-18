/**
 * The file PersistenceException.java was created on 2010.31.1 at 14:11:44
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.persistence.exception;

public class AKException extends Exception {
	private static final long serialVersionUID = 1L;

	public AKException(Throwable e) {
		super(e);
	}

	public AKException(String msg) {
		super(msg);
	}
}

