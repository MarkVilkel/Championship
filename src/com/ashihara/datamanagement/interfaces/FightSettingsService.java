/**
 * The file PointsService.java was created on 2010.13.4 at 23:02:32
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.rtu.exception.PersistenceException;

public interface FightSettingsService extends AKService {
	public FightSettings load() throws PersistenceException;
	public FightSettings save(FightSettings fightSettings) throws PersistenceException;	
}
