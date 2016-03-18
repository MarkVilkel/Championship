/**
 * The file AKServerServiceInvocationHandler.java was created on 2009.4.10 at 12:39:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

import com.ashihara.datamanagement.core.persistence.exception.AKException;
import com.ashihara.datamanagement.core.session.AKServerSessionManager;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.impl.ExceptionServiceImpl;
import com.ashihara.datamanagement.interfaces.ExceptionService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.pojo.User;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.rtu.exception.PersistenceException;
import com.rtu.persistence.TransactionType;
import com.rtu.service.core.AbstractServerServiceInvocationHandler;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.session.ServerSession;
import com.rtu.session.SessionManager;
import com.rtu.session.interfaces.ClientSession;
import com.rtu.session.interfaces.SessionHandler;

public class AKServerServiceInvocationHandler<T extends DMIdentifiedService> extends
		AbstractServerServiceInvocationHandler<T> {
	
	private AKServerSessionManager aKServerSessionManager;
	private Map<String, String> constrainMessagesMap;

	public AKServerServiceInvocationHandler(T service, TransactionType transactionType, ClientSession clientSession) {
		super(service, transactionType, clientSession);
	}

	@Override
	protected SessionManager getSessionManager() {
		if (aKServerSessionManager == null) {
			aKServerSessionManager = AKServerSessionManagerImpl.getInstance();
		}
		return aKServerSessionManager;
	}

	@Override
	protected Throwable convertConstraintViolationException(ConstraintViolationException e) {
		String msg = getConstraintName(e);
		for (String key : getConstrainMessagesMap().keySet()){
			if (msg.contains(key)){
				String message = getConstrainMessagesMap().get(key);
				return new PersistenceException(message);
			}
		}
		return super.convertConstraintViolationException(e);
	}
	
	@Override
	protected Throwable convertInvocationTargetException(InvocationTargetException ite) {
		if (ite.getTargetException() instanceof AKException) {
			return ite.getTargetException();
		}
		return super.convertInvocationTargetException(ite);
	}

	@Override
	protected void handleRawServerServiceInvocationException(Throwable exception, ServerSession serverSession) {
		if (exception != null && getService().getClass() != ExceptionServiceImpl.class) {
			try{
				User user = getUser(serverSession.getSessionHandler());
				getSessionManager().getDefaultServerSession().getServerSideServiceFactory().getService(ExceptionService.class, TransactionType.COMMIT_TRANSACTION).saveException(exception, user);
			} catch(Throwable e ){
				e.printStackTrace();
			}
		}
	}

	public Map<String, String> getConstrainMessagesMap() {
		if (constrainMessagesMap == null) {
			constrainMessagesMap = new HashMap<String, String>();
			
			User user = null;
			try {
				user = getUser(getService().getClientSession().getSessionHandler());
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
			UIC uic = SC.UI_PREFERENCES.UI_LANGUAGE.getUIC(user.getUiLanguage());
			
			// FOR H2 DB
			constrainMessagesMap.put("FK9D0C8BD673D2F00A",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.WEIGHT_CATEGORY() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			constrainMessagesMap.put("FKF7907C9D8406307D",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.COUNTRY() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			constrainMessagesMap.put("FK14B9883AB6162CB",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.FIGHTER() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			
			constrainMessagesMap.put("CONSTRAINT_INDEX_6",uic.COUNTRY_NAME_AND_CODE_MUST_BE_UNIQUE() + "!");
			
			
			
			
			// For PostgreSQL DB
			constrainMessagesMap.put("fk9d0c8bd6fb1a03a2",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.WEIGHT_CATEGORY() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			constrainMessagesMap.put("fkf7907c9d8406307d",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.COUNTRY() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			constrainMessagesMap.put("fk14b9883ab6162cb",uic.COULD_NOT_DELETE_OBJECT() + " '" + uic.FIGHTER() + "'!\n" + uic.OTHER_ITEMS_IN_THE_SYSTEM_REFERENCES_ON_IT_DELETE_THEM_FIRST() + "!");
			
			constrainMessagesMap.put("country_name_key",uic.COUNTRY_NAME_MUST_BE_UNIQUE() + "!");
			constrainMessagesMap.put("country_code_key",uic.COUNTRY_CODE_MUST_BE_UNIQUE() + "!");
			
		}
		return constrainMessagesMap;
	}
	
	private User getUser(SessionHandler handler) throws PersistenceException {
		User user = getSessionManager().getDefaultServerSession().getServerSideServiceFactory().getService(UserService.class, TransactionType.NO_TRANSACTION).loadByName(handler.getUserId());
		return user;
	}

}
