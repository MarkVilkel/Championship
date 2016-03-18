/**
 * The file WeightCategoryService.java was created on 2010.4.4 at 14:14:24
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.rtu.exception.PersistenceException;

public interface WeightCategoryService extends AKService {

	public void deleteCategories(List<WeightCategory> list) throws PersistenceException;
	public List<WeightCategory> searchByPattern(WeightCategory weightCategory) throws PersistenceException;
	public WeightCategory reload(WeightCategory weightCategory) throws PersistenceException;
	public WeightCategory saveWeightCategory(WeightCategory weightCategory) throws PersistenceException;
	public List<WeightCategory> loadAll() throws PersistenceException;
	public List<YearWeightCategoryLink> searchByPattern(YearWeightCategoryLink pattern) throws PersistenceException;
	public WeightCategory loadOrCreateWeightCategory(Double fromWeight, Double toWeight) throws PersistenceException;

}
