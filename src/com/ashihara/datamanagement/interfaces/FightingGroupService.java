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

	List<FightingGroup> loadGroups(Championship championship) throws PersistenceException;
	List<YearCategory> getYearCategoriesFromList(List<FightingGroup> groups);
	void deleteGroups(List<FightingGroup> groups) throws PersistenceException;
	void createGroupsYear(Championship championship, List<YearCategory> selectedYearCategories) throws PersistenceException, AKBusinessException;
	List<FightingGroup> saveGroups(List<FightingGroup> groups) throws PersistenceException;
	FightingGroup saveGroup(FightingGroup group) throws PersistenceException;
	void createGroupsByYearWeight(Championship championship, List<YearWeightCategoryLink> list) throws PersistenceException;
	List<FightingGroup> filterGroups(Championship championship, YearWeightCategoryLink pattern) throws PersistenceException;
	
	FightingGroup reload(FightingGroup fightingGroup) throws PersistenceException;
	FightingGroup save(FightingGroup fightingGroup) throws PersistenceException;
	void performGroupChampionshipFighters(List<GroupChampionshipFighter> gcfListToSave, List<GroupChampionshipFighter> gcfListToDelete) throws PersistenceException;
	List<GroupChampionshipFighter> loadGroupChampionshipFighters(FightingGroup fightingGroup) throws PersistenceException;
	
	void setupLevelsForOlympicGroupChampionshipFighters(List<GroupChampionshipFighter> fighters) throws PersistenceException;
	GroupChampionshipFighter loadOrCreateGroupChampionshipFighter(FightingGroup group, ChampionshipFighter championshipFighter) throws PersistenceException;
	GroupChampionshipFighter saveGroupChampionshipFighter(GroupChampionshipFighter groupChampionshipFighter) throws PersistenceException;
	List<FightingGroup> loadStartedWithRealFightResultsGroups(Championship championship) throws PersistenceException;
	List<FightingGroup> loadStartedGroups(Championship championship) throws PersistenceException;
	void sortFightingGroups(List<FightingGroup> groups);
}
