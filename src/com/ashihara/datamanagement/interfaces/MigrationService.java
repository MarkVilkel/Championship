/**
 * The file MigrationService.java was created on 2012.21.4 at 00:45:18
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.wraper.DBWrapper;
import com.rtu.exception.PersistenceException;

public interface MigrationService extends AKService {

	DBWrapper exportDataBase() throws PersistenceException;

	void saveFightingGroups(DBWrapper dbWrapper) throws PersistenceException, AKBusinessException;
	
}
