/**
 * The file YearCategoryService.java was created on 2010.4.4 at 15:42:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.rtu.exception.PersistenceException;

public interface YearCategoryService extends AKService {

	public YearCategory reload(YearCategory yearCategory) throws PersistenceException;
	public YearCategory saveYearCategory(YearCategory yearCategory) throws PersistenceException;
	public List<YearCategory> searchByPattern(YearCategory yearCategory, List<YearCategory> exceptYearCategories) throws PersistenceException;
	public void deleteCategories(List<YearCategory> list) throws PersistenceException;
	public void deleteYearWeightCategoryLinks(List<YearWeightCategoryLink> list) throws PersistenceException;
	public List<YearCategory> loadAll() throws PersistenceException;
	public YearWeightCategoryLink loadOrCreateYearWeightCategoryLink(YearCategory yearCategory, WeightCategory weightCategory) throws PersistenceException;
	public YearCategory loadOrCreateYearCategory(Long fromYear, Long toYear) throws PersistenceException;

}
