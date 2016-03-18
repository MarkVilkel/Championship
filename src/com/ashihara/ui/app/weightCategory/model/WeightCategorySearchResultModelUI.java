/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory.model;

import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.ui.app.weightCategory.WeightCategoryDetailsDialog;
import com.ashihara.ui.app.weightCategory.view.WeightCategorySearchResultPanelViewUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class WeightCategorySearchResultModelUI extends AKAbstractModelUI<WeightCategorySearchResultPanelViewUI> implements IWeightCategorySearchResultModelUI<WeightCategorySearchResultPanelViewUI>, DialogCallBackListener {
	private WeightCategorySearchResultPanelViewUI viewUI;
	private WeightCategory weightCategory = new WeightCategory();

	public WeightCategorySearchResultModelUI(){
		super();
		viewUI = new WeightCategorySearchResultPanelViewUI(AKUIEventSender.newInstance(this));
		
		viewUI.init();
		clear();
		viewUI.getModelUI().search();
	}
	
	public WeightCategorySearchResultPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void search() {
		try {
			copyFromUI();
			
			getViewUI().getCountryTable().getTable().getKASModel().setDataRows(
					getWeightCategoryService().searchByPattern(getWeightCategory()));
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getCountryTable().resetHeaderCenterResultCaption(getViewUI().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setWeightCategory(new WeightCategory());
		copyOnUI();
		getViewUI().getCountryTable().getTable().getKASModel().clear();
		getViewUI().getCountryTable().resetHeaderCenterResultCaption(null);
		
	}
	
	
	public void deleteSelectedTableRows() {
		List<WeightCategory> list = getViewUI().getCountryTable().getTable().getKASModel().getDataDeleted();
		try {
			getWeightCategoryService().deleteCategories(list);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
			search();
		}
	}

	private void copyOnUI(){
		getViewUI().getTxtFrom().setText(getWeightCategory().getFromWeight() == null ? "" : getWeightCategory().getFromWeight().toString());
		getViewUI().getTxtTo().setText(getWeightCategory().getToWeight() == null ? "" : getWeightCategory().getToWeight().toString());
	}
	
	private void copyFromUI(){
		Double from = null;
		try {
			from = Double.valueOf(getViewUI().getTxtFrom().getText());
		}
		catch (Exception e) {
			getViewUI().getTxtFrom().setText("");
		}
		
		Double to = null;
		try {
			to = Double.valueOf(getViewUI().getTxtTo().getText());
		}
		catch (Exception e) {
			getViewUI().getTxtTo().setText("");
		}
		
		getWeightCategory().setFromWeight(from);
		getWeightCategory().setToWeight(to);
	}

	public void onAddTableRows(Integer countToAdd) {
		appManager.openDialog(WeightCategoryDetailsDialog.class, new WeightCategory(), this);
	}

	public void onLinkClicked(WeightCategory value, String columnId) {
		if (uic.FROM_WEIGHT().equals(columnId)){
			appManager.openDialog(WeightCategoryDetailsDialog.class, value, this);
		}
	}

	public void dialogClosed(Class<?> dialog, Map<String, Object> map) {
		if (dialog == WeightCategoryDetailsDialog.class){
			getViewUI().getModelUI().search();
		}
	}

	public void setViewUI(WeightCategorySearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public WeightCategory getWeightCategory() {
		return weightCategory;
	}

	public void setWeightCategory(WeightCategory weightCategory) {
		this.weightCategory = weightCategory;
	}

}