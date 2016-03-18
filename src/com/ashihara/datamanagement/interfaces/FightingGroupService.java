/**
 * The file GroupService.java was created on 2010.7.4 at 22:36:03
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.rtu.exception.PersistenceException;

public interface FightingGroupService extends AKService {

	public List<FightingGroup> loadGroups(Championship championship) throws PersistenceException;
	public List<YearCategory> getYearCategoriesFromList(List<FightingGroup> groups);
	public void deleteGroups(List<FightingGroup> groups) throws PersistenceException;
	public void createGroupsYear(Championship championship, List<YearCategory> selectedYearCategories) throws PersistenceException, AKBusinessException;
	public List<FightingGroup> saveGroups(List<FightingGroup> groups) throws PersistenceException;
	public FightingGroup saveGroup(FightingGroup group) throws PersistenceException;
	public void createGroupsByYearWeight(Championship championship, List<YearWeightCategoryLink> list) throws PersistenceException;
	public List<FightingGroup> filterGroups(Championship championship, YearWeightCategoryLink pattern) throws PersistenceException;
	
	public FightingGroup reload(FightingGroup fightingGroup) throws PersistenceException;
	public FightingGroup save(FightingGroup fightingGroup) throws PersistenceException;
	public void performGroupChampionshipFighters(List<GroupChampionshipFighter> gcfListToSave, List<GroupChampionshipFighter> gcfListToDelete) throws PersistenceException;
	public List<GroupChampionshipFighter> loadGroupChampionshipFighters(FightingGroup fightingGroup) throws PersistenceException;
	
	public void setupLevelsForOlympicGroupChampionshipFighters(List<GroupChampionshipFighter> fighters) throws PersistenceException;
	public GroupChampionshipFighter loadOrCreateGroupChampionshipFighter(FightingGroup group, ChampionshipFighter championshipFighter) throws PersistenceException;
	public GroupChampionshipFighter saveGroupChampionshipFighter(GroupChampionshipFighter groupChampionshipFighter) throws PersistenceException;
	public List<FightingGroup> loadStartedWithRealFightResultsGroups(Championship championship) throws PersistenceException;
}
