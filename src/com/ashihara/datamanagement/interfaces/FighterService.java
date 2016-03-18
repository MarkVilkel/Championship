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
	public Fighter saveFighter(Fighter fighter) throws PersistenceException;
	public Fighter reloadFighter(Fighter fighter) throws PersistenceException;
	public void deleteFighter(Fighter fighter) throws PersistenceException;
	
	public List<Fighter> saveFighters(List<Fighter> fighters) throws PersistenceException;
	public void deleteFighters(List<Fighter> fighters) throws PersistenceException;
	public List<Fighter> searchByPattern(Fighter fighter, List<Fighter> exceptFighters) throws PersistenceException;
	public Fighter reload(Fighter fighter) throws PersistenceException;
	public Long getNewFighterNumber() throws PersistenceException;
	public List<ChampionshipFighter> loadFightersSuitableForGroup(FightingGroup group) throws PersistenceException;
	
	public void importFighters(File file) throws PersistenceException;
	public List<ChampionshipFighter> loadFightersByGroup(FightingGroup group) throws PersistenceException;
	public Fighter loadOrCreateFighter(Fighter fighter) throws PersistenceException, AKBusinessException;
	
}