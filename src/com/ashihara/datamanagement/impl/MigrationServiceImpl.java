/**
 * The file MigrationServiceImpl.java was created on 2012.21.4 at 00:45:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.interfaces.MigrationService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.datamanagement.pojo.wraper.DBWrapper;
import com.ashihara.enums.CM;
import com.ashihara.ui.tools.KASDebuger;
import com.ashihara.utils.DataManagementUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.persistence.core.Attribute;
import com.rtu.persistence.core.Do;

public class MigrationServiceImpl extends AbstractAKServiceImpl implements MigrationService {

	@Override
	public DBWrapper exportDataBase() throws PersistenceException {
		Map allPersistentClassesMetadata = getKasServerSessionManager().getDefaultServerSession().getHibernateSessionFactory().getAllClassMetadata();
		DBWrapper dbWrapper = new DBWrapper();
		dbWrapper.setDbObjectsMap(new LinkedHashMap<Class<Do>, List<Do>>());
		
		
		for (Object key : allPersistentClassesMetadata.keySet()) {
			try {
				Class<Do> clazz = (Class<Do>)Class.forName(key.toString());
				List<Do> allObjects = loadAll(clazz);
				dbWrapper.getDbObjectsMap().put(clazz, allObjects);
			} catch (ClassNotFoundException e) {
				throw new PersistenceException(e);
			}
		}
		
		return dbWrapper;
	}
	
	private <D extends Do> List<D> loadAll(Class<D> clazz) throws PersistenceException {
		Attribute<D> attribute;
		try {
			attribute = createAttribute(clazz);
		} catch (Throwable e) {
			throw new PersistenceException(e);
		}
		
		HqlQuery<D> hql = getHelper().createHqlQuery(clazz, attribute);
		return getHelper().loadByHqlQuery(hql);
	}
	
	private <D extends Do> void saveAll(List<D> objects) throws PersistenceException {
		getHelper().saveAll(objects);
	}

	
	private <D extends Do> Attribute<D> createAttribute(Class<D> clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String className = CM.class.getName() + "$" + clazz.getSimpleName();
		Class<Attribute<D>> attributeClazz = (Class<Attribute<D>>) Class.forName(className);
		return attributeClazz.newInstance();
	}
	
	@Override
	public void saveFightingGroups(DBWrapper dbWrapper) throws PersistenceException, AKBusinessException {
		Map<Class<Do>, List<Do>> objectMap = dbWrapper.getDbObjectsMap();
		
		Championship championship = loadOrCreateChampionship(dbWrapper.getChampionship());
		
		for (FightingGroup otherGroup : dbWrapper.getFightingGroups()) {
			Map<Long, FightResult> otherFightResults = getFightResults(otherGroup, objectMap);
					
			YearWeightCategoryLink yearWeightCategoryLink = loadOrCreateYearWeightCategoryLink(otherGroup.getYearWeightCategoryLink());
			
			FightingGroup group = cloneGroup(otherGroup, championship, yearWeightCategoryLink);
			
			Map<Long, GroupChampionshipFighter> otherGroupChampionshipFighters = getGroupChampionshipFighters(otherGroup, objectMap);
			Map<Long, GroupChampionshipFighter> groupChampionshipFighters = cloneGroupChampionshipFighters(group, otherGroupChampionshipFighters); 
			
			Map<Long, FightResult> newFightResults = new LinkedHashMap<Long, FightResult>();
			
			for (Long id : otherFightResults.keySet()) {
				FightResult otherFightResult = otherFightResults.get(id);
				FightResult fightResult = cloneFightResult(otherFightResult, championship, groupChampionshipFighters);
				
				newFightResults.put(id, fightResult);
			}
			
			setupAndSavePreviousNextFightResults(otherFightResults, newFightResults);
		}
	}
	
	private void setupAndSavePreviousNextFightResults(
			Map<Long, FightResult> otherFightResults,
			Map<Long, FightResult> newFightResults
	) throws PersistenceException {
		
		for (Long id : otherFightResults.keySet()) {
			FightResult otherFightResult = otherFightResults.get(id);
			FightResult newFightResult = newFightResults.get(id);
			
			boolean save = false;
			
			if (otherFightResult.getPreviousRoundFightResult() != null) {
				FightResult newPreviousFightResult = newFightResults.get(otherFightResult.getPreviousRoundFightResult().getId());
				newFightResult.setPreviousRoundFightResult(newPreviousFightResult);
				save = true;
			}
			
			if (otherFightResult.getNextRoundFightResult() != null) {
				FightResult newNextFightResult = newFightResults.get(otherFightResult.getNextRoundFightResult().getId());
				newFightResult.setNextRoundFightResult(newNextFightResult);
				
				save = true;
			}
			
			if (save) {
				getFightResultService().saveFightResult(newFightResult);
			}
		}
	}

	private Map<Long, GroupChampionshipFighter> cloneGroupChampionshipFighters(
			FightingGroup group,
			Map<Long, GroupChampionshipFighter> otherGroupChampionshipFighters
	) throws PersistenceException, AKBusinessException {
		Map<Long, GroupChampionshipFighter> result = new LinkedHashMap<Long, GroupChampionshipFighter>();
		for (Long id : otherGroupChampionshipFighters.keySet()) {
			GroupChampionshipFighter otherGCF = otherGroupChampionshipFighters.get(id);
			GroupChampionshipFighter gcf = cloneGroupChampionshipFighter(group, otherGCF);
			result.put(id, gcf);
		}
		return result;
	}

	private FightResult cloneFightResult(
			FightResult originalFightResult,
			Championship championship,
			Map<Long, GroupChampionshipFighter> groupChampionshipFighters
	) throws PersistenceException, AKBusinessException {
		
		GroupChampionshipFighter firstFighter = groupChampionshipFighters.get(originalFightResult.getFirstFighter().getId());
		GroupChampionshipFighter secondFighter = groupChampionshipFighters.get(originalFightResult.getSecondFighter().getId());
		
		FightResult clone = DataManagementUtils.clone(originalFightResult);
		clone.setFirstFighter(firstFighter);
		clone.setSecondFighter(secondFighter);
		clone.setNextRoundFightResult(null);
		clone.setPreviousRoundFightResult(null);
		
		clone = getFightResultService().saveFightResult(clone);

		return clone;
	}

	private GroupChampionshipFighter cloneGroupChampionshipFighter(
			FightingGroup group,
			GroupChampionshipFighter otherFighter
	) throws PersistenceException, AKBusinessException {
		
		Fighter fighter = getFighterService().loadOrCreateFighter(otherFighter.getChampionshipFighter().getFighter());
		ChampionshipFighter championshipFighter = getChampionshipService().loadOrCreateChampionshipFighter(group.getChampionship(), fighter);
		
		GroupChampionshipFighter groupChampionshipFighter = new GroupChampionshipFighter();
		
		groupChampionshipFighter.setChampionshipFighter(championshipFighter);
		groupChampionshipFighter.setFightingGroup(group);
		groupChampionshipFighter.setOlympicLevel(otherFighter.getOlympicLevel());
		
		groupChampionshipFighter = getFightingGroupService().saveGroupChampionshipFighter(groupChampionshipFighter);
		
		return groupChampionshipFighter;
	}

	private <T extends Do> Map<Long, GroupChampionshipFighter> getGroupChampionshipFighters(FightingGroup group, Map<Class<T>, List<T>> map) {
		List<GroupChampionshipFighter> all = (List<GroupChampionshipFighter>)map.get(GroupChampionshipFighter.class);
		
		Map<Long, GroupChampionshipFighter> result = new LinkedHashMap<Long, GroupChampionshipFighter>();
		for (GroupChampionshipFighter gcf : all) {
			if (gcf.getFightingGroup().getId().equals(group.getId())) {
				result.put(gcf.getId(), gcf);
			}
		}
		
		return result;
	}
	
	private <T extends Do> Map<Long, FightResult> getFightResults(FightingGroup group, Map<Class<T>, List<T>> map) {
		List<FightResult> allFightResults = (List<FightResult>)map.get(FightResult.class);
		
		Map<Long, FightResult> result = new LinkedHashMap<Long, FightResult>();
		for (FightResult fr : allFightResults) {
			if (fr.getFirstFighter().getFightingGroup().getId().equals(group.getId())) {
				result.put(fr.getId(), fr);
			}
		}
		
		return result;
	}

	private YearWeightCategoryLink loadOrCreateYearWeightCategoryLink(YearWeightCategoryLink otherDBYearWeightCategoryLink) throws PersistenceException {
		YearCategory yearCategory = getYearCategoryService().loadOrCreateYearCategory(
				otherDBYearWeightCategoryLink.getYearCategory().getFromYear(),
				otherDBYearWeightCategoryLink.getYearCategory().getToYear()
		);
		
		WeightCategory weightCategory = getWeightCategoryService().loadOrCreateWeightCategory(
				otherDBYearWeightCategoryLink.getWeightCategory().getFromWeight(),
				otherDBYearWeightCategoryLink.getWeightCategory().getToWeight()
		);
		
		YearWeightCategoryLink link = getYearCategoryService().loadOrCreateYearWeightCategoryLink(
				yearCategory,
				weightCategory
		);
		
		return link;
	}
	
	private FightingGroup cloneGroup(
			FightingGroup group,
			Championship championship,
			YearWeightCategoryLink yearWeightCategoryLink
	) throws PersistenceException, AKBusinessException {
		
		FightingGroup clone = DataManagementUtils.clone(group);
		clone.setChampionship(championship);
		clone.setYearWeightCategoryLink(yearWeightCategoryLink);
		
		clone = getFightingGroupService().save(clone);
		return clone;
	}
	
	private Championship loadOrCreateChampionship(Championship otherDBChampionship) throws PersistenceException, AKBusinessException {
		Championship result = loadChampionship(otherDBChampionship);
		if (result == null) {
			KASDebuger.println("No such championship " + otherDBChampionship + " " + otherDBChampionship.getId() + " creating new one");
			Championship clone = DataManagementUtils.clone(otherDBChampionship);
			result = getChampionshipService().saveChampionship(clone);
		}
		return result;
	}
	
	private Championship loadChampionship(Championship otherDBChampionship) throws PersistenceException {
		List<Championship> championships = getChampionshipService().loadChampionshipByName(otherDBChampionship.getName());
		if (championships == null || championships.isEmpty()) {
			return null;
		}
		else {
			return championships.get(0);
		}
	}
	
}
