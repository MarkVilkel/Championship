/**
 * The file ChampionshipServiceImpl.java was created on 2010.6.4 at 20:43:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ashihara.datamanagement.interfaces.ChampionshipService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class ChampionshipServiceImpl extends AbstractAKServiceImpl implements ChampionshipService {

	@Override
	public Championship reloadChampionship(Championship championship) throws PersistenceException {
		return getHelper().reload(championship);
	}

	@Override
	public Championship saveChampionship(Championship championship) throws PersistenceException {
		return getHelper().save(championship);
	}

	@Override
	public List<ChampionshipFighter> loadChampionshipFighters(Championship championship) throws PersistenceException {
		if (championship == null || championship.getId() == null) {
			return new ArrayList<ChampionshipFighter>();
		}
		
		HqlQuery<ChampionshipFighter> hql = getHelper().createHqlQuery(ChampionshipFighter.class, getCmChampionshipFighter());
		
		hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getChampionship(), championship));
		
		List<ChampionshipFighter> result = getHelper().loadByHqlQuery(hql);
		
		return result;
	}

	@Override
	public List<FightingGroup> loadGroups(Championship championship) throws PersistenceException {
		if (championship == null || championship.getId() == null) {
			return new ArrayList<FightingGroup>();
		}
		
		HqlQuery<FightingGroup> hql = getHelper().createHqlQuery(FightingGroup.class, getCmFightingGroup());
		
		hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getChampionship(), championship));
		
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public List<Fighter> getFightersFromList(List<ChampionshipFighter> championshipFighters) {
		List<Fighter> result = new ArrayList<Fighter>();
		
		if (championshipFighters == null || championshipFighters.isEmpty()) {
			return result; 
		}
		
		for (ChampionshipFighter cf : championshipFighters) {
			result.add(cf.getFighter());
		}

		return result;
	}

	@Override
	public void createChampionshipFighters(Championship championship, List<Fighter> fighters) throws PersistenceException {
		if (championship == null || championship.getId() == null || fighters == null || fighters.isEmpty()) {
			return;
		}
		
		List<ChampionshipFighter> saveList = new ArrayList<ChampionshipFighter>();
		
		Long lastNumber = getLastChampionshipFighterNumber(championship);
		
		for (Fighter fighter : fighters) {
			
			ChampionshipFighter championshipFighter = new ChampionshipFighter();
			championshipFighter.setFighter(fighter);
			championshipFighter.setChampionship(championship);
			championshipFighter.setNumber(++lastNumber);
			
			saveList.add(championshipFighter);
		}
		
		getHelper().saveAll(saveList);
	}
	
	private Long getLastChampionshipFighterNumber(Championship championship) throws PersistenceException {
		List<ChampionshipFighter> list = loadChampionshipFighters(championship);
		Long last = 0l;
		
		if (list != null) {
			for (ChampionshipFighter cf : list) {
				if (last < cf.getNumber()) {
					last = cf.getNumber();
				}
			}
		}
		
		return last;
	}

	@Override
	public void delete(List<ChampionshipFighter> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public void deleteChampionships(List<Championship> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public List<Championship> searchByPattern(Championship pattern) throws PersistenceException {
		HqlQuery<Championship> hql = getHelper().createHqlQuery(Championship.class, getCmChampionship());
		
		if (pattern.getName() != null && !pattern.getName().isEmpty()) {
			hql.addExpression(ExpressionHelper.ilike(getCmChampionship().getName(), pattern.getName()));
		}
		
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public void organizeChampionshipFighterNumbers(Championship championship) throws PersistenceException {
		List<ChampionshipFighter> championshipFighters = loadChampionshipFighters(championship);
		
		if (championshipFighters == null) {
			return;
		}
		long number = 0;
		for (ChampionshipFighter championshipFighter : championshipFighters) {
			++number;
			championshipFighter.setNumber(number);
		}
		
		List<Fighter> fighters = getFightersFromList(championshipFighters);
		getFighterService().saveFighters(fighters);
	}

	@Override
	public List<ChampionshipFighter> loadChampionshipFighters(Championship championship, Fighter pattern) throws PersistenceException {
		if (championship == null || championship.getId() == null) {
			return new ArrayList<ChampionshipFighter>();
		}
		
		HqlQuery<ChampionshipFighter> hql = getHelper().createHqlQuery(ChampionshipFighter.class, getCmChampionshipFighter());
		
		hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getChampionship(), championship));
		
		if (pattern != null) {
			if (pattern.getName() != null && !pattern.getName().isEmpty()) {
				hql.addExpression(ExpressionHelper.ilike(getCmChampionshipFighter().getFighter().getName(), pattern.getName()));
			}
			if (pattern.getSurname() != null && !pattern.getSurname().isEmpty()) {
				hql.addExpression(ExpressionHelper.ilike(getCmChampionshipFighter().getFighter().getSurname(), pattern.getSurname()));
			}
			if (pattern.getCountry() != null && pattern.getCountry().getId() != null) {
				hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getFighter().getCountry(), pattern.getCountry()));
			}
			if (pattern.getGender() != null) {
				hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getFighter().getGender(), pattern.getGender()));
			}
		}
		List<ChampionshipFighter> result = getHelper().loadByHqlQuery(hql);
		getFighterService().fillParticipanceInChampionshipsCount(result.stream().map(cf -> cf.getFighter()).collect(Collectors.toList()));
		
		return result;
	}

	@Override
	public List<ChampionshipFighter> saveChampionshipFighters(
			List<ChampionshipFighter> championshipFighters
	) throws PersistenceException {
		return getHelper().saveAll(championshipFighters);
	}

	@Override
	public List<ChampionshipFighter> loadAllFightersNotInAnyGroup(Championship championship) throws PersistenceException {
		List<ChampionshipFighter> fighters = loadChampionshipFighters(championship);
		List<FightingGroup> groups = getFightingGroupService().loadGroups(championship);
		for (FightingGroup group : groups) {
			List<GroupChampionshipFighter> groupFighters = getFightingGroupService().loadGroupChampionshipFighters(group);
			for (GroupChampionshipFighter groupFighter : groupFighters) {
				fighters.remove(groupFighter.getChampionshipFighter());
			}
		}
		return fighters;
	}

	@Override
	public Championship loadChampionshipById(Long id) throws PersistenceException {
		
		HqlQuery<Championship> hql = getHelper().createHqlQuery(Championship.class, getCmChampionship());
		hql.addExpression(ExpressionHelper.eq(getCmChampionship().getId(), id));
		
		List<Championship> result = getHelper().loadByHqlQuery(hql);
		if (result == null || result.isEmpty()) {
			return null;
		}
		else {
			return result.get(0);
		}
		
	}

	@Override
	public List<Championship> loadChampionshipByName(String name) throws PersistenceException {
		HqlQuery<Championship> hql = getHelper().createHqlQuery(Championship.class, getCmChampionship());
		hql.addExpression(ExpressionHelper.eq(getCmChampionship().getName(), name));
		
		List<Championship> result = getHelper().loadByHqlQuery(hql);
		return result;
	}

	@Override
	public ChampionshipFighter loadOrCreateChampionshipFighter(
			Championship championship,
			Fighter fighter
	) throws PersistenceException {

		HqlQuery<ChampionshipFighter> hql = getHelper().createHqlQuery(ChampionshipFighter.class, getCmChampionshipFighter());
		hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getChampionship(), championship));
		hql.addExpression(ExpressionHelper.eq(getCmChampionshipFighter().getFighter(), fighter));
		
		List<ChampionshipFighter> list = getHelper().loadByHqlQuery(hql);

		if (list == null || list.isEmpty()) {
			ChampionshipFighter championshipFighter = new ChampionshipFighter();
			championshipFighter.setChampionship(championship);
			championshipFighter.setFighter(fighter);
			championshipFighter = getHelper().save(championshipFighter);
			return championshipFighter;
		}
		else {
			return list.get(0);
		}
	}
	
}
