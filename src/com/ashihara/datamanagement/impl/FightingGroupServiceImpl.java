/**
 * The file GroupServiceImpl.java was created on 2010.7.4 at 22:36:15
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.interfaces.FightingGroupService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.enums.SC;
import com.ashihara.utils.MathUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class FightingGroupServiceImpl extends AbstractAKServiceImpl implements FightingGroupService {

	@Override
	public void createGroupsYear(Championship championship, List<YearCategory> selectedYearCategories) throws PersistenceException, AKBusinessException {
		if (championship == null || championship.getId() == null || selectedYearCategories == null) {
			return;
		}
		
		for (YearCategory yc : selectedYearCategories) {
			if (yc.getYearWeightCategories() == null || yc.getYearWeightCategories().isEmpty()) {
				throw new AKBusinessException(getUIC().CAN_NOT_CREATE_GROUP_FOR_YEAR_CATEGORY() + " '" + yc + "' " + getUIC().BECAUSE_IT_DOES_NOT_CONTAIN_ANY_WEIGHT_CATEGORIES());
			}
			
			createGroupsByYearWeight(championship, yc.getYearWeightCategories());
		}
	}
	
	@Override
	public void createGroupsByYearWeight(Championship championship, List<YearWeightCategoryLink> list) throws PersistenceException {
		if (list != null && championship != null && championship.getId() != null) {
			
			List<FightingGroup> groups = new ArrayList<FightingGroup>();
			
			for (YearWeightCategoryLink ywc : list) {
				FightingGroup group = new FightingGroup();
				group.setChampionship(championship);
				group.setYearWeightCategoryLink(ywc);
				group.setName(ywc.getYearCategory().toString() + ", " + ywc.getWeightCategory().toString());
				group.setStatus(SC.GROUP_STATUS.INITIAL);
				groups.add(group);
			}
			saveGroups(groups);
		}
	}
	
	@Override
	public List<FightingGroup> saveGroups(List<FightingGroup> groups) throws PersistenceException {
		return getHelper().saveAll(groups);
	}

	@Override
	public void deleteGroups(List<FightingGroup> groups) throws PersistenceException {
		getHelper().deleteAll(groups);
	}

	@Override
	public List<YearCategory> getYearCategoriesFromList(List<FightingGroup> groups) {
		List<YearCategory> result = new ArrayList<YearCategory>();
		
		if (groups == null || groups.isEmpty()) { 
			return result;
		}
		
		for (FightingGroup group : groups) {
			result.add(group.getYearWeightCategoryLink().getYearCategory());
		}
		
		return result;
	}

	@Override
	public List<FightingGroup> loadGroups(Championship championship) throws PersistenceException {
		if (championship == null || championship.getId() == null) {
			return new ArrayList<FightingGroup>();
		}
		
		HqlQuery<FightingGroup> hql = getHelper().createHqlQuery(FightingGroup.class, getCmFightingGroup());
		
		hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getChampionship(), championship));
		
		List<FightingGroup> result = getHelper().loadByHqlQuery(hql); 
		sortFightingGroups(result);
		
		return result;
	}

	@Override
	public FightingGroup saveGroup(FightingGroup group) throws PersistenceException {
		return getHelper().save(group);
	}

	@Override
	public List<FightingGroup> filterGroups(Championship championship, YearWeightCategoryLink pattern) throws PersistenceException {
		if (championship == null || championship.getId() == null) {
			return new ArrayList<FightingGroup>();
		}
		
		HqlQuery<FightingGroup> hql = getHelper().createHqlQuery(FightingGroup.class, getCmFightingGroup());
		hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getChampionship(), championship));
		
		if (pattern.getWeightCategory() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getYearWeightCategoryLink().getWeightCategory(), pattern.getWeightCategory()));
		}
		
		if (pattern.getYearCategory() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getYearWeightCategoryLink().getYearCategory(), pattern.getYearCategory()));
		}
		
		List<FightingGroup> result = getHelper().loadByHqlQuery(hql); 
		sortFightingGroups(result);
		
		return result;
	}
	
	private void sortFightingGroups(List<FightingGroup> groups) {
		Collections.sort(groups, new Comparator<FightingGroup>() {
			@Override
			public int compare(FightingGroup o1, FightingGroup o2) {
				long year1 = o1.getYearWeightCategoryLink().getYearCategory().getFromYear();
				long year2 = o2.getYearWeightCategoryLink().getYearCategory().getFromYear();
				if (year1 == year2) {
					double weight1 = o1.getYearWeightCategoryLink().getWeightCategory().getFromWeight();
					double weight2 = o2.getYearWeightCategoryLink().getWeightCategory().getFromWeight();
					if (weight1 == weight2) {
						if (SC.GENDER.MALE.equals(o1.getGender()) && SC.GENDER.MALE.equals(o2.getGender())) {
							if (o1.getId() > o2.getId()) {
								return 1;
							}
							else {
								return -1;
							}
						}
						else if (SC.GENDER.MALE.equals(o1.getGender())) {
							return -1;
						}
						else {
							return 1;
						}
					}
					else if (weight1 > weight2) {
						return 1;
					}
					else {
						return -1;
					}
				}
				else if (year1 > year2) {
					return 1;
				}
				else {
					return -1;
				}
			}
		});
	}

	@Override
	public FightingGroup reload(FightingGroup fightingGroup) throws PersistenceException {
		return getHelper().reload(fightingGroup);
	}

	@Override
	public FightingGroup save(FightingGroup fightingGroup) throws PersistenceException {
		return getHelper().save(fightingGroup);
	}

	@Override
	public void performGroupChampionshipFighters(List<GroupChampionshipFighter> gcfListToSave, List<GroupChampionshipFighter> gcfListToDelete) throws PersistenceException {
		getHelper().deleteAll(gcfListToDelete);
		getHelper().saveAll(gcfListToSave);
	}

	@Override
	public List<GroupChampionshipFighter> loadGroupChampionshipFighters(FightingGroup fightingGroup) throws PersistenceException {
		if (fightingGroup == null || fightingGroup.getId() == null) {
			return new ArrayList<GroupChampionshipFighter>();
		}
		
		HqlQuery<GroupChampionshipFighter> hql = getHelper().createHqlQuery(GroupChampionshipFighter.class, getCmGroupChampionshipFighter());
		hql.addExpression(ExpressionHelper.eq(getCmGroupChampionshipFighter().getFightingGroup(), fightingGroup));

		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public void setupLevelsForOlympicGroupChampionshipFighters(List<GroupChampionshipFighter> fighters) throws PersistenceException {
		int idealFightersNumber = MathUtils.calculateIdealFightersNumberForOlympic(fighters.size());
		
		int fightersCountToGoToTheSecondLevel = idealFightersNumber - fighters.size();
		if (fightersCountToGoToTheSecondLevel == 0) {
			for (GroupChampionshipFighter gcf : fighters) {
				gcf.setOlympicLevel(Long.valueOf(0));
			}
		}
		else {
			for (int i = 0; i < fighters.size() - fightersCountToGoToTheSecondLevel; i++) {
				GroupChampionshipFighter gcf = fighters.get(i);
				gcf.setOlympicLevel(Long.valueOf(0));
			}
			for (int i = 0; i < fighters.size() - fightersCountToGoToTheSecondLevel; i++) {
				GroupChampionshipFighter gcf = fighters.get(i);
				gcf.setOlympicLevel(Long.valueOf(0));
			}
			for (int i = fighters.size() - fightersCountToGoToTheSecondLevel; i < fighters.size(); i++) {
				GroupChampionshipFighter gcf = fighters.get(i);
				gcf.setOlympicLevel(Long.valueOf(1));
			}
		}
		
		performGroupChampionshipFighters(fighters, null);
		
	}

	@Override
	public GroupChampionshipFighter saveGroupChampionshipFighter(GroupChampionshipFighter groupChampionshipFighter) throws PersistenceException {
		return getHelper().save(groupChampionshipFighter);
	}

	@Override
	public GroupChampionshipFighter loadOrCreateGroupChampionshipFighter(
			FightingGroup group,
			ChampionshipFighter championshipFighter
	) throws PersistenceException {
		
		HqlQuery<GroupChampionshipFighter> hql = getHelper().createHqlQuery(GroupChampionshipFighter.class, getCmGroupChampionshipFighter());
		
		hql.addExpression(ExpressionHelper.eq(getCmGroupChampionshipFighter().getFightingGroup(), group));
		hql.addExpression(ExpressionHelper.eq(getCmGroupChampionshipFighter().getChampionshipFighter(), championshipFighter));
		
		List<GroupChampionshipFighter> list = getHelper().loadByHqlQuery(hql);
		
		if (list == null || list.isEmpty()) {
			GroupChampionshipFighter groupChampionshipFighter = new GroupChampionshipFighter();
			groupChampionshipFighter.setChampionshipFighter(championshipFighter);
			groupChampionshipFighter.setFightingGroup(group);
			groupChampionshipFighter = saveGroupChampionshipFighter(groupChampionshipFighter);
			return groupChampionshipFighter;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	public List<FightingGroup> loadStartedWithRealFightResultsGroups(Championship championship) throws PersistenceException {
		List<FightingGroup> allGroups = loadGroups(championship);
		List<FightingGroup> result = new ArrayList<FightingGroup>();
		
		for (FightingGroup group : allGroups) {
			if (SC.GROUP_STATUS.STARTED.equals(group.getStatus())) {
				List<FightResult> fightResults = null;
				if (SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
					fightResults = getFightResultService().loadOrCreateOlympicFightResults(group);
				}
				else if (SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
					fightResults = getFightResultService().loadOrCreateRoundRobinLastFightResults(group);
				}
				boolean isAnyRealResult = isAnyRealResult(fightResults);
				if (isAnyRealResult) {
					result.add(group);
				}
			}
		}
		
		return result;
	}

	private boolean isAnyRealResult(List<FightResult> fightResults) {
		if (fightResults == null) {
			return false;
		}
		
		for (FightResult fr : fightResults) {
			if (fr.getId() != null) {
				return true;
			}
		}
		
		return false;
	}

}
