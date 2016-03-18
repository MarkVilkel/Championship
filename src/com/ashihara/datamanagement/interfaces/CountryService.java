/**
 * The file CountryService.java was created on 2010.2.4 at 21:05:47
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Country;
import com.rtu.exception.PersistenceException;

public interface CountryService  extends AKService {

	public List<Country> loadAll() throws PersistenceException;
	public void deleteCountries(List<Country> list) throws PersistenceException;
	public List<Country> searchByPattern(Country country) throws PersistenceException;
	public Country reload(Country country) throws PersistenceException;
	public Country saveCountry(Country country) throws PersistenceException;
	public Country getByName(List<Country> countries, String name);
	public Country loadOrCreateCounty(String code, String name) throws PersistenceException;

}
