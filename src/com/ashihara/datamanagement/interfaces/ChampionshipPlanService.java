/**
 * The file ChampionshipService.java was created on 2010.6.4 at 20:43:33
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.rtu.exception.PersistenceException;

public interface ChampionshipPlanService extends AKService {

	ChampionshipPlan loadOrCreate(Championship championship) throws PersistenceException;
	
	ChampionshipPlan reload(ChampionshipPlan plan) throws PersistenceException;
	ChampionshipPlan save(ChampionshipPlan plan) throws PersistenceException;

	void addToPlan(ChampionshipPlan plan, List<FightingGroup> groups) throws PersistenceException;
	void removeFromPlan(List<FightingGroup> groups) throws PersistenceException;

	List<FightingGroup> loadGroupsForAddingToPlan(ChampionshipPlan plan) throws PersistenceException;

	void sortGroupsForPlan(List<FightingGroup> groups);
	
	List<FightingGroup> loadGroups(ChampionshipPlan plan) throws PersistenceException;
	
}
