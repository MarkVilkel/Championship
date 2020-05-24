/**
 * The file FighterService.java was created on 2010.31.1 at 14:37:12
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.io.File;
import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.rtu.exception.PersistenceException;

public interface FighterService extends AKService {
	Fighter saveFighter(Fighter fighter) throws PersistenceException;
	Fighter reloadFighter(Fighter fighter) throws PersistenceException;
	void deleteFighter(Fighter fighter) throws PersistenceException;
	
	List<Fighter> saveFighters(List<Fighter> fighters) throws PersistenceException;
	void deleteFighters(List<Fighter> fighters) throws PersistenceException;
	List<Fighter> searchByPattern(Fighter fighter, List<Fighter> exceptFighters) throws PersistenceException;
	Fighter reload(Fighter fighter) throws PersistenceException;
	Long getNewFighterNumber() throws PersistenceException;
	List<ChampionshipFighter> loadFightersSuitableForGroup(FightingGroup group) throws PersistenceException;
	
	void importFighters(File file) throws PersistenceException;
	List<ChampionshipFighter> loadFightersByGroup(FightingGroup group) throws PersistenceException;
	Fighter loadOrCreateFighter(Fighter fighter) throws PersistenceException, AKBusinessException;
	
	void fillParticipanceInChampionshipsCount(List<Fighter> fighters) throws PersistenceException;
	
}