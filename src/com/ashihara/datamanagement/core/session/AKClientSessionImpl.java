/**
 * The file AKClientSessionImpl.java was created on 2008.2.4 at 12:17:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.session;

import com.ashihara.datamanagement.pojo.User;
import com.rtu.session.ClientSessionImpl;
import com.rtu.session.interfaces.SessionHandler;

public class AKClientSessionImpl extends ClientSessionImpl implements AKClientSession {
	private static final long serialVersionUID = 1L;

	private User user;
	
	public AKClientSessionImpl() {
		this(null, null);
	}
	
	public AKClientSessionImpl(User user, SessionHandler sessionHandler) {
		super();
		setUser(user);
		setSessionHandler(sessionHandler);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
