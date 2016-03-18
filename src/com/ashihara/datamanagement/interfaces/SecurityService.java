package com.ashihara.datamanagement.interfaces;

import com.ashihara.datamanagement.core.persistence.exception.AKException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.pojo.User;
import com.rtu.exception.PersistenceException;
import com.rtu.session.interfaces.ClientSession;

public interface SecurityService extends AKService {
	public static final String ADMIN_USER_NAME = "Admin";
	public static final String ADMIN_USER_PASSWORD = "Admin";
	
	public AKClientSession login(User user) throws PersistenceException, AKException;
	public AKClientSession defaultLogin() throws PersistenceException, AKException;
	public void logout(AKClientSession context);
	public void logout(ClientSession ClientSession);
}
