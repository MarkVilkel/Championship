/**
 * The file AKClientSession.java was created on 2009.4.10 at 18:04:01
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.session;

import com.ashihara.datamanagement.pojo.User;
import com.rtu.session.interfaces.ClientSession;

public interface AKClientSession extends ClientSession{
	public User getUser();
	public void setUser(User user);
}
