package com.ashihara.datamanagement.impl;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.persistence.exception.AKException;
import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.core.session.AKClientSessionImpl;
import com.ashihara.datamanagement.interfaces.SecurityService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.pojo.User;
import com.ashihara.utils.PswHasher;
import com.rtu.exception.PersistenceException;
import com.rtu.session.ServerSession;
import com.rtu.session.SessionHandlerImpl;
import com.rtu.session.interfaces.ClientSession;
import com.rtu.session.interfaces.SessionHandler;

public class SecurityServiceImpl extends AbstractAKServiceImpl implements SecurityService {
	
	private void closeSession(String sessionHandler) {
		getKasServerSessionManager().destroySesion(sessionHandler);
	}

	public AKClientSession login(User user) throws PersistenceException, AKException {
		ServerSession defaultServerSession = getKasServerSessionManager().getDefaultServerSession();
		if (Boolean.FALSE.equals(isAnyUserInDb())) {
			createDefaultAdmin();
		}
		
		User existedUser = defaultServerSession.getServerSideServiceFactory().getService(UserService.class).doesUserExist(user.getName(), user.getPassword());
		AKClientSession clientSession = null;
		
		if (existedUser != null && existedUser.getId() != null) {
			ServerSession serverSession = getKasServerSessionManager().createSession(existedUser.getName());
			clientSession = createKasClientSession(serverSession, existedUser);
		}
		return clientSession;
	}
	
	private Boolean isAnyUserInDb() throws PersistenceException {
		return getServerServiceFactory().getService(UserService.class).isAnyUserInDB();
	}
	
	private AKClientSession createKasClientSession(ServerSession serverSession, User user) {
		SessionHandler sessionHandler = new SessionHandlerImpl(serverSession.getSessionHandler().getSessionId(), user.getName());
		AKClientSession clientSession = new AKClientSessionImpl(user, sessionHandler);
		return clientSession;
	}
	
	private User createDefaultAdmin() throws PersistenceException, AKBusinessException {
		ServerSession defaultServerSession = getKasServerSessionManager().getDefaultServerSession();
		UserService userService = defaultServerSession.getServerSideServiceFactory().getService(UserService.class);
		
		User user = userService.createUser(ADMIN_USER_NAME, ADMIN_USER_PASSWORD);
		
		user = userService.save(user);
		return user;
	}

	public void logout(AKClientSession context) {
		logout((ClientSession)context);
	}

	private AKClientSession isLogged(User user) throws PersistenceException {
		ServerSession serverSession = getKasServerSessionManager().getServerSessionByUser(user.getName()); 
		AKClientSession kasClientSession = createKasClientSession(serverSession, user);
		
		return kasClientSession;
	}

	public void logout(ClientSession clientSession) {
		if (clientSession == null || clientSession.getSessionHandler() == null) {
			return;
		}
		closeSession(clientSession.getSessionHandler().getSessionId());
	}

	public AKClientSession defaultLogin() throws PersistenceException, AKException {
		User user = new User();
		user.setName(ADMIN_USER_NAME);
		user.setPassword(PswHasher.hash(ADMIN_USER_PASSWORD));
		return login(user);
	}
}