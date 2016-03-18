/**
 * The file PointsServiceImpl.java was created on 2010.13.4 at 23:02:55
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import com.ashihara.datamanagement.interfaces.FightSettingsService;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;

public class FightSettingsServiceImpl extends AbstractAKServiceImpl implements FightSettingsService {

	@Override
	public FightSettings load() throws PersistenceException {
		HqlQuery<FightSettings> hql = getHelper().createHqlQuery(FightSettings.class, getCmFightSettings());
		FightSettings fightSettings = getHelper().uniqueResult(hql);
		if (fightSettings == null) {
			fightSettings = new FightSettings();
			fightSettings.setForWinning(3l);
			fightSettings.setForDraw(1l);
			fightSettings.setForLoosing(0l);
			fightSettings.setTatami("A");
			fightSettings.setFirstRoundTime(3 * 60l);
			fightSettings.setSecondRoundTime(1 * 60l);
			fightSettings.setOtherRoundTime(1 * 60l);
			
			fightSettings = getHelper().save(fightSettings);
		}
		return fightSettings;
	}

	@Override
	public FightSettings save(FightSettings fightSettings) throws PersistenceException {
		if (fightSettings == null || fightSettings.getId() == null) {
			return null;
		}
		return getHelper().save(fightSettings);
	}
	

}
