/**
 * The file UserService.java was created on 2010.21.3 at 12:27:24
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.User;
import com.rtu.exception.PersistenceException;

public interface UserService extends AKService {
	public User loadByName(String name) throws PersistenceException;
	public User loadOrCreateSystemUser(String name, String password, String email) throws PersistenceException, AKValidationException;
	public Boolean isAnyUserInDB() throws PersistenceException;
	public User save(User user) throws PersistenceException;
	public User doesUserExist(String name, Long password) throws PersistenceException, AKValidationException;
	public User createUser(String name, String password);
}
