/**
 * The file CountryServiceImpl.java was created on 2010.2.4 at 21:06:15
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.List;

import com.ashihara.datamanagement.interfaces.CountryService;
import com.ashihara.datamanagement.pojo.Country;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class CountryServiceImpl extends AbstractAKServiceImpl implements CountryService {

	@Override
	public List<Country> loadAll() throws PersistenceException {
		HqlQuery<Country> hql = getHelper().createHqlQuery(Country.class, getCmCountry());
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public void deleteCountries(List<Country> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public Country reload(Country country) throws PersistenceException {
		return getHelper().reload(country);
	}

	@Override
	public Country saveCountry(Country country) throws PersistenceException {
		return getHelper().save(country);
	}

	@Override
	public List<Country> searchByPattern(Country country) throws PersistenceException {
		HqlQuery<Country> hql = getHelper().createHqlQuery(Country.class, getCmCountry());
		
		if (country.getCode() != null && !country.getCode().isEmpty()) {
			hql.addExpression(ExpressionHelper.ilike(getCmCountry().getCode(), country.getCode()));
		}
		
		if (country.getName() != null && !country.getName().isEmpty()) {
			hql.addExpression(ExpressionHelper.ilike(getCmCountry().getName(), country.getName()));
		}
		
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public Country getByName(List<Country> countries, String name) {
		if (name.toLowerCase().equals("dania")) {
			name = "Denmark";
		}
		for (Country c : countries) {
			if (c.getName().toLowerCase().equals(name.toLowerCase())) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Country loadOrCreateCounty(String code, String name) throws PersistenceException {
		HqlQuery<Country> hql = getHelper().createHqlQuery(Country.class, getCmCountry());
		
		hql.addExpression(ExpressionHelper.eq(getCmCountry().getCode(), code));
		hql.addExpression(ExpressionHelper.eq(getCmCountry().getName(), name));
		
		List<Country> result = getHelper().loadByHqlQuery(hql);
		if (result == null || result.isEmpty()) {
			Country country = new Country();
			country.setCode(code);
			country.setName(name);
			return saveCountry(country);
		}
		else {
			return result.get(0);
		}
	}

}
