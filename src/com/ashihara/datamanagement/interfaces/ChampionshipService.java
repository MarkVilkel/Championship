/**
 * The file ChampionshipService.java was created on 2010.6.4 at 20:43:33
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.rtu.exception.PersistenceException;

public interface ChampionshipService extends AKService {

	Championship reloadChampionship(Championship championship) throws PersistenceException;
	Championship saveChampionship(Championship championship) throws PersistenceException;
	void deleteChampionships(List<Championship> list) throws PersistenceException;
	List<Championship> searchByPattern(Championship pattern) throws PersistenceException;
	void organizeChampionshipFighterNumbers(Championship championship) throws PersistenceException;
	
	List<ChampionshipFighter> loadChampionshipFighters(Championship championship, Fighter pattern) throws PersistenceException;
	List<ChampionshipFighter> loadChampionshipFighters(Championship championship) throws PersistenceException;
	List<ChampionshipFighter> saveChampionshipFighters(List<ChampionshipFighter> championshipFighters) throws PersistenceException;
	
	List<Fighter> getFightersFromList(List<ChampionshipFighter> championshipFighters);
	List<FightingGroup> loadGroups(Championship championship) throws PersistenceException;
	
	void createChampionshipFighters(Championship championship, List<Fighter> fighters) throws PersistenceException;
	void delete(List<ChampionshipFighter> list) throws PersistenceException;
	
	List<ChampionshipFighter> loadAllFightersNotInAnyGroup(Championship championship) throws PersistenceException;
	Championship loadChampionshipById(Long id) throws PersistenceException;
	List<Championship> loadChampionshipByName(String name) throws PersistenceException;
	ChampionshipFighter loadOrCreateChampionshipFighter(Championship championship, Fighter fighter) throws PersistenceException;
	
}
