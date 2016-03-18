/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country.model;

import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.ui.app.country.CountryDetailsDialog;
import com.ashihara.ui.app.country.view.CountrySearchResultPanelViewUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class CountrySearchResultModelUI extends AKAbstractModelUI<CountrySearchResultPanelViewUI> implements ICountrySearchResultModelUI<CountrySearchResultPanelViewUI>, DialogCallBackListener {
	private CountrySearchResultPanelViewUI viewUI;
	private Country country = new Country();

	public CountrySearchResultModelUI(){
		super();
		viewUI = new CountrySearchResultPanelViewUI(AKUIEventSender.newInstance(this));
		
		viewUI.init();
		clear();
		viewUI.getModelUI().search();
	}
	
	public CountrySearchResultPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void search() {
		try {
			copyFromUI();
			
			getViewUI().getCountryTable().getTable().getKASModel().setDataRows(
					getCountryService().searchByPattern(getCountry()));
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getCountryTable().resetHeaderCenterResultCaption(getViewUI().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setCountry(new Country());
		copyOnUI();
		getViewUI().getCountryTable().getTable().getKASModel().clear();
		getViewUI().getCountryTable().resetHeaderCenterResultCaption(null);
		
	}
	
	
	public void deleteSelectedTableRows() {
		List<Country> list = getViewUI().getCountryTable().getTable().getKASModel().getDataDeleted();
		try {
			getCountryService().deleteCountries(list);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
			search();
		}
	}

	private void copyOnUI(){
		getViewUI().getTxtCode().setText(getCountry().getCode());
		getViewUI().getTxtName().setText(getCountry().getName());
	}
	
	private void copyFromUI(){
		getCountry().setCode(getViewUI().getTxtCode().getText());
		getCountry().setName(getViewUI().getTxtName().getText());
	}

	public void onAddTableRows(Integer countToAdd) {
		appManager.openDialog(CountryDetailsDialog.class, new Country(), this);
	}

	public void onLinkClicked(Country value, String columnId) {
		if (uic.CODE().equals(columnId)){
			appManager.openDialog(CountryDetailsDialog.class, value, this);
		}
	}

	public void dialogClosed(Class<?> dialog, Map<String, Object> map) {
		if (dialog == CountryDetailsDialog.class){
			getViewUI().getModelUI().search();
		}
	}

	public void setViewUI(CountrySearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}