/**
 * The file ChampionshipPlanServiceImpl.java was created on 12 февр. 2020 г. at 21:33:11
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.ashihara.datamanagement.interfaces.ChampionshipPlanService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class ChampionshipPlanServiceImpl extends AbstractAKServiceImpl implements ChampionshipPlanService {

	@Override
	public ChampionshipPlan reload(ChampionshipPlan plan) throws PersistenceException {
		return getHelper().reload(plan);
	}

	@Override
	public ChampionshipPlan save(ChampionshipPlan plan) throws PersistenceException {
		return getHelper().save(plan);
	}

	@Override
	public ChampionshipPlan loadOrCreate(Championship championship) throws PersistenceException {
		HqlQuery<ChampionshipPlan> hql = getHelper().createHqlQuery(ChampionshipPlan.class, getCmChampionshipPlan());
		hql.addExpression(ExpressionHelper.eq(getCmChampionshipPlan().getChampionship(), championship));
		ChampionshipPlan plan = getHelper().uniqueResult(hql);
		if (plan == null) {
			plan = new ChampionshipPlan();
			plan.setChampionship(championship);
			plan.setFinalsAtTheEnd(false);
			plan = getHelper().save(plan);
		}
		return plan;
	}

	@Override
	public void addToPlan(ChampionshipPlan plan, List<FightingGroup> groups) throws PersistenceException {
		Optional<Integer> maxOpt = loadGroups(plan).stream().map(g -> g.getOrderInPlan()).max((o1, o2) -> o1.compareTo(o2));
		final AtomicInteger order = new AtomicInteger(maxOpt.orElse(0));
		groups.forEach(e -> {e.setPlan(plan); e.setOrderInPlan(order.incrementAndGet());});
		getFightingGroupService().saveGroups(groups);
	}

	@Override
	public void removeFromPlan(List<FightingGroup> groups) throws PersistenceException {
		if (groups == null) {
			return;
		}
		groups = groups.stream().filter(g -> g != null).collect(Collectors.toList());
		if (groups.isEmpty()) {
			return;
		}
		
		groups.forEach(e -> {e.setPlan(null); e.setOrderInPlan(null);});
		getFightingGroupService().saveGroups(groups);
	}

	@Override
	public List<FightingGroup> loadGroupsForAddingToPlan(ChampionshipPlan plan) throws PersistenceException {
		List<FightingGroup> allGroups = getFightingGroupService().loadStartedWithRealFightResultsGroups(plan.getChampionship());
		List<FightingGroup> alreadyAddedGroups = loadGroups(plan);
		allGroups.removeAll(alreadyAddedGroups);
		getFightingGroupService().sortFightingGroups(allGroups);
		return allGroups;
	}

	@Override
	public void sortGroupsForPlan(List<FightingGroup> groups) {
		Collections.sort(groups, (o1, o2) -> o1.getOrderInPlan().compareTo(o2.getOrderInPlan()));
	}
	
	@Override
	public List<FightingGroup> loadGroups(ChampionshipPlan plan) throws PersistenceException {
		HqlQuery<FightingGroup> hql = getHelper().createHqlQuery(FightingGroup.class, getCmFightingGroup());
		hql.addExpression(ExpressionHelper.eq(getCmFightingGroup().getPlan(), plan));
		List<FightingGroup> result = getHelper().loadByHqlQuery(hql);
		sortGroupsForPlan(result);
		return result;
	}


}
