/**
 * The file AKServerSessionManagerImpl.java was created on 2009.2.10 at 16:16:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.session;

import java.util.Date;

import com.ashihara.datamanagement.core.TimeUtils;
import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.core.service.AKServerServiceFactoryImpl;
import com.ashihara.datamanagement.interfaces.SecurityService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.pojo.User;
import com.rtu.exception.PersistenceException;
import com.rtu.persistence.TransactionType;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.service.core.ServerSideServiceFactory;
import com.rtu.session.AbstractSessionManager;
import com.rtu.session.ServerSession;

public class AKServerSessionManagerImpl extends AbstractSessionManager implements AKServerSessionManager {
	private User defaultUser;
	
	private static AKServerSessionManager instance;
	
	private AKServerSessionManagerImpl() {
		
	}
	
	public static AKServerSessionManager getInstance() {
		if (instance == null) {
			instance = new AKServerSessionManagerImpl();
		}
		return instance;
	}

	@Override
	protected String getHibernateCfgXmlPath() {
		return "com/ashihara/datamanagement/xml/hibernate.cfg.xml";
	}

	@Override
	protected Date getCurrentTime() {
		return TimeUtils.getCurrentApplicationServerTime();
	}

	@Override
	protected <T extends DMIdentifiedService, M extends ServerSideServiceFactory<T>> M cretateNewServerSideServiceFactory(ServerSession serverSession) {
		return (M) new AKServerServiceFactoryImpl<AKService>(serverSession);
	}

	@Override
	protected String getDefaultUserID() {
		return getDefaultUser().getName();
	}

	protected User getDefaultUser() {
		if (defaultUser == null) {
			ServerSession serverSession = createSession("Temporary user");
			try {
				String name = SecurityService.ADMIN_USER_NAME;
				String password = SecurityService.ADMIN_USER_PASSWORD;
				String email = "markvilkel@inbox.lv";
				
				defaultUser = serverSession.getServerSideServiceFactory().getService(UserService.class, TransactionType.COMMIT_TRANSACTION).loadOrCreateSystemUser(name, password, email);
			} catch (PersistenceException e) {
				throw new RuntimeException(e);
			} catch (AKValidationException e) {
				throw new RuntimeException(e);
			}
		}
		return defaultUser;
	}
}
