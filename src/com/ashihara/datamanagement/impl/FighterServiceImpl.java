/**
 * The file FighterServiceImpl.java was created on 2010.31.1 at 14:37:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.interfaces.FighterService;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.enums.SC;
import com.ashihara.ui.tools.KASDebuger;
import com.ashihara.utils.DataManagementUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class FighterServiceImpl extends AbstractAKServiceImpl implements FighterService {
	
	@Override
	public void deleteFighter(Fighter fighter) throws PersistenceException {
		getHelper().delete(fighter);
	}

	@Override
	public void deleteFighters(List<Fighter> fighters) throws PersistenceException {
		getHelper().deleteAll(fighters);
	}

	@Override
	public Fighter reloadFighter(Fighter fighter) throws PersistenceException {
		return getHelper().reload(fighter);
	}

	@Override
	public Fighter saveFighter(Fighter fighter) throws PersistenceException {
		return getHelper().save(fighter);
	}

	@Override
	public List<Fighter> saveFighters(List<Fighter> fighters) throws PersistenceException {
		return getHelper().saveAll(fighters);
	}

	@Override
	public List<Fighter> searchByPattern(Fighter fighter, List<Fighter> exceptFighters) throws PersistenceException {
		HqlQuery<Fighter> hql = getHelper().createHqlQuery(Fighter.class, getCmFighter());
		
		if (fighter.getName() != null && !fighter.getName().isEmpty()) {
			hql.addExpression(ExpressionHelper.ilike(getCmFighter().getName(), fighter.getName()));
		}
		if (fighter.getSurname() != null && !fighter.getSurname().isEmpty()) {
			hql.addExpression(ExpressionHelper.ilike(getCmFighter().getSurname(), fighter.getSurname()));
		}
		if (fighter.getCountry() != null && fighter.getCountry().getId() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFighter().getCountry(), fighter.getCountry()));
		}
		
		if (fighter.getGender() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFighter().getGender(), fighter.getGender()));
		}
		
		if (exceptFighters != null && !exceptFighters.isEmpty()) {
			hql.addExpression(ExpressionHelper.not(ExpressionHelper.in(getCmFighter(), exceptFighters)));
		}
		
		List<Fighter> result = getHelper().loadByHqlQuery(hql);
		fillParticipanceInChampionshipsCount(result);
		
		return result;
	}

	@Override
	public Fighter reload(Fighter fighter) throws PersistenceException {
		return getHelper().reload(fighter);
	}

	@Override
	public Long getNewFighterNumber() throws PersistenceException {
		List<Fighter> fighters = searchByPattern(new Fighter(), null);
		Long max = 0l;
		for (Fighter f : fighters) {
			if (max < f.getId()) {
				max = f.getId();
			}
		}
		max ++;
		return max;
	}

	@Override
	public List<ChampionshipFighter> loadFightersSuitableForGroup(FightingGroup group) throws PersistenceException {
		List<ChampionshipFighter> championshipFighters = getChampionshipService().loadChampionshipFighters(group.getChampionship());
		
		List<ChampionshipFighter> fighters = new ArrayList<ChampionshipFighter>();
		for (ChampionshipFighter championshipFighter : championshipFighters) {
			Fighter fighter = championshipFighter.getFighter();
			if (
					fighter.getFullYearsOld() >= group.getYearWeightCategoryLink().getYearCategory().getFromYear() &&
					fighter.getFullYearsOld() <= group.getYearWeightCategoryLink().getYearCategory().getToYear() &&
					fighter.getWeight() > group.getYearWeightCategoryLink().getWeightCategory().getFromWeight() &&
					fighter.getWeight() <= group.getYearWeightCategoryLink().getWeightCategory().getToWeight() &&
					(SC.GENDER.MIXED.equals(group.getGender()) || fighter.getGender().equals(group.getGender()))
			) {
				fighters.add(championshipFighter);
			}
		}
		
		return fighters;
	}

	@Override
	public void importFighters(File file) throws PersistenceException {
		List<String> lines = new ArrayList<String>();
		List<Fighter> fighters = new ArrayList<Fighter>();
		
		String problemStr = "";
		
		try {
			Reader in = new InputStreamReader(new FileInputStream(file), "UTF-16");
//			FileInputStream fstream = new FileInputStream(file);
//			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(in);
			String strLine;
			while ((strLine = br.readLine()) != null) {
				lines.add(strLine);
			}
			in.close();
			
			List<Country> countries = getCountryService().loadAll();
			
			for (String str : lines) {
				problemStr = str;
				
				String[] f = str.split("\t");
				Fighter fighter = new Fighter();
//				fighter.setNumber(Long.valueOf(f[0].trim()));
				
				String nameSurname = f[1].trim();
				String name = nameSurname.split(" ")[0];
				String surname = nameSurname.split(" ")[1];
				fighter.setName(name);
				
//				String surname = f[2].trim();
				fighter.setSurname(surname);
				
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				cal.set(Calendar.YEAR, (int) (cal.get(Calendar.YEAR) - Long.valueOf(f[2].trim())));
				fighter.setBirthday(new Date(cal.getTimeInMillis()));
				
				String weight = f[3].trim();
				weight = weight.replaceAll(",", ".");
				
				fighter.setWeight(Double.valueOf(weight));

				String countryStr = f[4].trim();
				Country country = getCountryService().getByName(countries, countryStr);
				if (country == null) {
					KASDebuger.println(countryStr);
				}
				fighter.setCountry(country);
				
				fighter.setKyu(10l);
				
				fighter.setGender(SC.GENDER.MALE);
				if (f.length > 5) {
					fighter.setGender(SC.GENDER.FEMALE);
				}
				fighters.add(fighter);
			}
		} catch (Exception e){//Catch exception if any
			throw new PersistenceException(problemStr + " " + e.getMessage());
		}
		
		saveFighters(fighters);
	}

	@Override
	public List<ChampionshipFighter> loadFightersByGroup(FightingGroup group) throws PersistenceException {
		List<GroupChampionshipFighter> gcFighters = getFightingGroupService().loadGroupChampionshipFighters(group);
		List<ChampionshipFighter> result = new ArrayList<ChampionshipFighter>();
		for (GroupChampionshipFighter gcf : gcFighters) {
			result.add(gcf.getChampionshipFighter());
		}
		return result;
	}

	@Override
	public Fighter loadOrCreateFighter(Fighter fighter) throws PersistenceException, AKBusinessException {
		HqlQuery<Fighter> hql = getHelper().createHqlQuery(Fighter.class, getCmFighter());
		hql.addExpression(ExpressionHelper.eq(getCmFighter().getName(), fighter.getName()));		
		hql.addExpression(ExpressionHelper.eq(getCmFighter().getGender(), fighter.getGender()));
		hql.addExpression(ExpressionHelper.eq(getCmFighter().getSurname(), fighter.getSurname()));
		hql.addExpression(ExpressionHelper.eq(getCmFighter().getCountry(), fighter.getCountry()));
		hql.addExpression(ExpressionHelper.eq(getCmFighter().getWeight(), fighter.getWeight()));
		
		List<Fighter> result = getHelper().loadByHqlQuery(hql);
		
		if (result == null || result.isEmpty()) {
			
			Country country = getCountryService().loadOrCreateCounty(fighter.getCountry().getCode(), fighter.getCountry().getName());
			
			Fighter newFighter = DataManagementUtils.clone(fighter);
			newFighter.setCountry(country);
			
			newFighter = saveFighter(newFighter);
			return newFighter;
		}
		
		return result.get(0);
	}
	
	@Override
	public void fillParticipanceInChampionshipsCount(List<Fighter> fighters) throws PersistenceException {
		if (fighters == null || fighters.isEmpty()) {
			return;
		}
		
		HqlQuery<ChampionshipFighter> hql = getHelper().createHqlQuery(ChampionshipFighter.class, getCmChampionshipFighter());
		hql.addExpression(ExpressionHelper.in(getCmChampionshipFighter().getFighter(), fighters));
		List<ChampionshipFighter> result = getHelper().loadByHqlQuery(hql);
		Map<Long, AtomicInteger> map = new HashMap<>();
		
		for (ChampionshipFighter cf : result) {
			map.computeIfAbsent(cf.getFighter().getId(), (c) -> new AtomicInteger()).incrementAndGet();
		}
		
		for (Fighter fighter : fighters) {
			AtomicInteger count = map.get(fighter.getId());
			int participance = count != null ? count.get() : 0;
			fighter.setParticipanceInChampionshipsCount(participance);
		}
	}

}
