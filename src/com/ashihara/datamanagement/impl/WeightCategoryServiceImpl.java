/**
 * The file WeightCategoryServiceImpl.java was created on 2010.4.4 at 14:14:41
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.List;

import com.ashihara.datamanagement.interfaces.WeightCategoryService;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class WeightCategoryServiceImpl extends AbstractAKServiceImpl implements WeightCategoryService {

	@Override
	public void deleteCategories(List<WeightCategory> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	@Override
	public WeightCategory reload(WeightCategory weightCategory) throws PersistenceException {
		return getHelper().reload(weightCategory);
	}

	@Override
	public WeightCategory saveWeightCategory(WeightCategory weightCategory) throws PersistenceException {
		return getHelper().save(weightCategory);
	}

	@Override
	public List<WeightCategory> searchByPattern(WeightCategory weightCategory) throws PersistenceException {
		HqlQuery<WeightCategory> hql = getHelper().createHqlQuery(WeightCategory.class, getCmWeightCategory());
		
		if (weightCategory.getFromWeight() != null) {
			hql.addExpression(ExpressionHelper.ge(getCmWeightCategory().getFromWeight(), weightCategory.getFromWeight()));
		}
		
		if (weightCategory.getToWeight() != null) {
			hql.addExpression(ExpressionHelper.le(getCmWeightCategory().getToWeight(), weightCategory.getToWeight()));
		}
		
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public List<WeightCategory> loadAll() throws PersistenceException {
		return searchByPattern(new WeightCategory());
	}

	@Override
	public List<YearWeightCategoryLink> searchByPattern(YearWeightCategoryLink pattern) throws PersistenceException {
		HqlQuery<YearWeightCategoryLink> hql = getHelper().createHqlQuery(YearWeightCategoryLink.class, getCmYearWeightCategoryLink());
		
		if (pattern.getWeightCategory() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmYearWeightCategoryLink().getWeightCategory(), pattern.getWeightCategory()));
		}
		
		if (pattern.getWeightCategory() != null) {
			hql.addExpression(ExpressionHelper.eq(getCmYearWeightCategoryLink().getWeightCategory(), pattern.getWeightCategory()));
		}
		
		return getHelper().loadByHqlQuery(hql);
	}

	@Override
	public WeightCategory loadOrCreateWeightCategory(Double fromWeight, Double toWeight) throws PersistenceException {
		HqlQuery<WeightCategory> hql = getHelper().createHqlQuery(WeightCategory.class, getCmWeightCategory());
		
		hql.addExpression(ExpressionHelper.eq(getCmWeightCategory().getFromWeight(), fromWeight));
		hql.addExpression(ExpressionHelper.eq(getCmWeightCategory().getToWeight(), toWeight));

		List<WeightCategory> list = getHelper().loadByHqlQuery(hql);
		
		if (list == null || list.isEmpty()) {
			WeightCategory weightCategory = new WeightCategory();
			
			weightCategory.setFromWeight(fromWeight);
			weightCategory.setToWeight(toWeight);
			weightCategory = saveWeightCategory(weightCategory);
			
			return weightCategory;
		}
		
		return list.get(0);
	}

}
