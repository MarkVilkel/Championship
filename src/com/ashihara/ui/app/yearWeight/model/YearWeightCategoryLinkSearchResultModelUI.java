/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearWeight.model;

import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.app.yearWeight.view.YearWeightCategoryLinkSearchResultPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class YearWeightCategoryLinkSearchResultModelUI extends AKAbstractModelUI<YearWeightCategoryLinkSearchResultPanelViewUI> implements IYearWeightCategoryLinkSearchResultModelUI<YearWeightCategoryLinkSearchResultPanelViewUI>{
	
	private YearWeightCategoryLinkSearchResultPanelViewUI viewUI;
	private YearWeightCategoryLink pattern;
	private List<YearWeightCategoryLink> selectedYearWeightCategoryLinks = new ArrayList<YearWeightCategoryLink>();

	public YearWeightCategoryLinkSearchResultModelUI(){
		super();
		viewUI = new YearWeightCategoryLinkSearchResultPanelViewUI(AKUIEventSender.newInstance(this));
		
		viewUI.init();
		clear();
		viewUI.getModelUI().search();
	}
	
	public YearWeightCategoryLinkSearchResultPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void search() {
		try {
			copyFromUI();
			
			getViewUI().getYearWeightCategoryLinkTable().getTable().getKASModel().setDataRows(
					getWeightCategoryService().searchByPattern(getPattern()));
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getYearWeightCategoryLinkTable().resetHeaderCenterResultCaption(getViewUI().getSearchPanel().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setPattern(new YearWeightCategoryLink());
		getViewUI().getYearWeightCategoryLinkTable().getTable().getKASModel().clear();
		getViewUI().getYearWeightCategoryLinkTable().resetHeaderCenterResultCaption(null);
		try {
			reloadCombos();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	private void reloadCombos() throws PersistenceException {
		ComboUIHelper.reloadWeightCategoryCombo(getViewUI().getSearchPanel().getCmbWeightCategory(), true);
		ComboUIHelper.reloadYearCategoryCombo(getViewUI().getSearchPanel().getCmbYearCategory(), true);
	}

	private void copyFromUI(){
		Object weight = getViewUI().getSearchPanel().getCmbWeightCategory().getSelectedItem();
		if (weight instanceof WeightCategory) {
			getPattern().setWeightCategory((WeightCategory)weight);
		}
		Object year = getViewUI().getSearchPanel().getCmbYearCategory().getSelectedItem();
		if (year instanceof YearCategory) {
			getPattern().setYearCategory((YearCategory)year);
		}
	}

	public void setViewUI(YearWeightCategoryLinkSearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public YearWeightCategoryLink getPattern() {
		return pattern;
	}

	public void setPattern(YearWeightCategoryLink pattern) {
		this.pattern = pattern;
	}
	
	public List<YearWeightCategoryLink> getSelectedYearWeightCategoryLinks() {
		return selectedYearWeightCategoryLinks;
	}

	@Override
	public void select() {
		selectedYearWeightCategoryLinks = getViewUI().getYearWeightCategoryLinkTable().getTable().getSelectedObjects(); 
		getViewUI().disposeParent();
	}

}