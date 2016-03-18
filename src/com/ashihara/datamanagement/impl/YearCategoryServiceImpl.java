/**
 * The file YearCategoryServiceImpl.java was created on 2010.4.4 at 15:43:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.List;

import com.ashihara.datamanagement.interfaces.YearCategoryService;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class YearCategoryServiceImpl extends AbstractAKServiceImpl implements YearCategoryService {

	@Override
	public void deleteCategories(List<YearCategory> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public YearCategory reload(YearCategory yearCategory) throws PersistenceException {
		return loadChildCollections(getHelper().reload(yearCategory));
	}

	@Override
	public YearCategory saveYearCategory(YearCategory yearCategory) throws PersistenceException {
		if (yearCategory == null) {
			return null;
		}
		
		yearCategory = getHelper().save(yearCategory);
		
		if (yearCategory.getYearWeightCategories() != null && !yearCategory.getYearWeightCategories().isEmpty()) {
			for (YearWeightCategoryLink yearWeightCategoryLink : yearCategory.getYearWeightCategories()) {
				yearWeightCategoryLink.setYearCategory(yearCategory);
				yearWeightCategoryLink = getHelper().save(yearWeightCategoryLink);
			}
		}
		
		return yearCategory;
	}

	@Override
	public List<YearCategory> searchByPattern(YearCategory yearCategory, List<YearCategory> exceptYearCategories) throws PersistenceException {
		HqlQuery<YearCategory> hql = getHelper().createHqlQuery(YearCategory.class, getCmYearCategory());
		
		if (yearCategory.getFromYear() != null) {
			hql.addExpression(ExpressionHelper.ge(getCmYearCategory().getFromYear(), yearCategory.getFromYear()));
		}
		
		if (yearCategory.getToYear() != null) {
			hql.addExpression(ExpressionHelper.le(getCmYearCategory().getToYear(), yearCategory.getToYear()));
		}
		
		if (exceptYearCategories != null && !exceptYearCategories.isEmpty()) {
			hql.addExpression(ExpressionHelper.not(ExpressionHelper.in(getCmYearCategory(), exceptYearCategories)));
		}
		
		return loadChildCollections(getHelper().loadByHqlQuery(hql));
	}
	
	private List<YearCategory> loadChildCollections(List<YearCategory> yearCategories) throws PersistenceException {
		if (yearCategories == null || yearCategories.isEmpty()) {
			return yearCategories;
		}
		
		for (YearCategory yearCategory : yearCategories) {
			yearCategory = loadChildCollections(yearCategory);	
		}
		
		return yearCategories;
	}
	
	private YearCategory loadChildCollections(YearCategory yearCategory) throws PersistenceException {
		if (yearCategory == null || yearCategory.getId() == null) {
			return null;
		}
		
		HqlQuery<YearWeightCategoryLink> hql = getHelper().createHqlQuery(YearWeightCategoryLink.class, getCmYearWeightCategoryLink());
		hql.addExpression(ExpressionHelper.eq(getCmYearWeightCategoryLink().getYearCategory(), yearCategory));
		
		List<YearWeightCategoryLink> list = getHelper().loadByHqlQuery(hql);
		yearCategory.setYearWeightCategories(list);
		
		return yearCategory;
		
	}

	@Override
	public void deleteYearWeightCategoryLinks(List<YearWeightCategoryLink> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public List<YearCategory> loadAll() throws PersistenceException {
		HqlQuery<YearCategory> hql = getHelper().createHqlQuery(YearCategory.class, getCmYearCategory());
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public YearWeightCategoryLink loadOrCreateYearWeightCategoryLink(YearCategory yearCategory, WeightCategory weightCategory) throws PersistenceException {
		HqlQuery<YearWeightCategoryLink> hql = getHelper().createHqlQuery(YearWeightCategoryLink.class, getCmYearWeightCategoryLink());
		
		hql.addExpression(ExpressionHelper.eq(getCmYearWeightCategoryLink().getYearCategory(), yearCategory));
		hql.addExpression(ExpressionHelper.eq(getCmYearWeightCategoryLink().getWeightCategory(), weightCategory));
		
		List<YearWeightCategoryLink> list = getHelper().loadByHqlQuery(hql);
		
		if (list == null || list.isEmpty()) {
			YearWeightCategoryLink link = new YearWeightCategoryLink();
			link.setYearCategory(yearCategory);
			link.setWeightCategory(weightCategory);
			link = getHelper().save(link);
			return link;
		}
		
		return list.get(0);
	}

	@Override
	public YearCategory loadOrCreateYearCategory(Long fromYear, Long toYear) throws PersistenceException {
		HqlQuery<YearCategory> hql = getHelper().createHqlQuery(YearCategory.class, getCmYearCategory());
		
		hql.addExpression(ExpressionHelper.eq(getCmYearCategory().getFromYear(), fromYear));
		hql.addExpression(ExpressionHelper.eq(getCmYearCategory().getToYear(), toYear));

		List<YearCategory> list = getHelper().loadByHqlQuery(hql);
		
		if (list == null || list.isEmpty()) {
			YearCategory yearCategory = new YearCategory();
			yearCategory.setFromYear(fromYear);
			yearCategory.setToYear(toYear);
			yearCategory = saveYearCategory(yearCategory);
			return yearCategory;
		}
		
		return list.get(0);
	}

}
