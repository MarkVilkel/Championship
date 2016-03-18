/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.ui.app.yearCategory.YearCategoryDetailsDialog;
import com.ashihara.ui.app.yearCategory.view.YearCategorySearchResultPanelViewUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class YearCategorySearchResultModelUI extends AKAbstractModelUI<YearCategorySearchResultPanelViewUI> implements IYearCategorySearchResultModelUI<YearCategorySearchResultPanelViewUI>, DialogCallBackListener {
	
	private YearCategorySearchResultPanelViewUI viewUI;
	private YearCategory yearCategory = new YearCategory();
	
	private List<YearCategory> selectedYearCategories = new ArrayList<YearCategory>();
	
	private final List<YearCategory> exceptYearCategories;

	public YearCategorySearchResultModelUI(){
		this(null);
	}
	
	public YearCategorySearchResultModelUI(List<YearCategory> exceptYearCategories){
		super();
		
		this.exceptYearCategories = exceptYearCategories;
		
		if (exceptYearCategories != null) {
			viewUI = new YearCategorySearchResultPanelViewUI(AKUIEventSender.newInstance(this), YearCategorySearchResultPanelViewUI.SELECT_MODE);
		}
		else {
			viewUI = new YearCategorySearchResultPanelViewUI(AKUIEventSender.newInstance(this));
		}
		
		clear();
		viewUI.getModelUI().search();
	}
	
	public YearCategorySearchResultPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void setViewUI(YearCategorySearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public void search() {
		try {
			copyFromUI();
			
			getViewUI().getYearCategoryTable().getTable().getKASModel().setDataRows(
					getYearCategoryService().searchByPattern(getYearCategory(), exceptYearCategories));
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getYearCategoryTable().resetHeaderCenterResultCaption(getViewUI().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setYearCategory(new YearCategory());
		copyOnUI();
		getViewUI().getYearCategoryTable().getTable().getKASModel().clear();
		getViewUI().getYearCategoryTable().resetHeaderCenterResultCaption(null);
		
	}
	
	
	public void deleteSelectedTableRows() {
		List<YearCategory> list = getViewUI().getYearCategoryTable().getTable().getKASModel().getDataDeleted();
		try {
			getYearCategoryService().deleteCategories(list);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
			search();
		}
	}

	private void copyOnUI(){
		getViewUI().getTxtFrom().setText(getYearCategory().getFromYear() == null ? "" : getYearCategory().getFromYear().toString());
		getViewUI().getTxtTo().setText(getYearCategory().getToYear() == null ? "" : getYearCategory().getToYear().toString());
	}
	
	private void copyFromUI(){
		Long from = null;
		try {
			from = Long.valueOf(getViewUI().getTxtFrom().getText());
		}
		catch (Exception e) {
			getViewUI().getTxtFrom().setText("");
		}
		
		Long to = null;
		try {
			to = Long.valueOf(getViewUI().getTxtTo().getText());
		}
		catch (Exception e) {
			getViewUI().getTxtTo().setText("");
		}
		
		getYearCategory().setFromYear(from);
		getYearCategory().setToYear(to);
	}

	public void onAddTableRows(Integer countToAdd) {
		appManager.openDialog(YearCategoryDetailsDialog.class, new YearCategory(), this);
	}

	public void onLinkClicked(YearCategory value, String columnId) {
		if (uic.FROM_YEAR().equals(columnId)){
			appManager.openDialog(YearCategoryDetailsDialog.class, value, this);
		}
	}

	public void dialogClosed(Class<?> dialog, Map<String, Object> map) {
		if (dialog == YearCategoryDetailsDialog.class){
			getViewUI().getModelUI().search();
		}
	}

	public YearCategory getYearCategory() {
		return yearCategory;
	}

	public void setYearCategory(YearCategory yearCategory) {
		this.yearCategory = yearCategory;
	}

	@Override
	public void selectYearCategories() {
		selectedYearCategories = getViewUI().getYearCategoryTable().getTable().getSelectedObjects();
		getViewUI().disposeParent();
	}

	public List<YearCategory> getSelectedYearCategories() {
		return selectedYearCategories;
	}

}