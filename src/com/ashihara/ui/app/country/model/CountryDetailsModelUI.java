/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country.model;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.ui.app.country.view.CountryDetailsPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class CountryDetailsModelUI extends AKAbstractModelUI<CountryDetailsPanelViewUI> implements ICountryDetailsModelUI<CountryDetailsPanelViewUI>{
	private Country country;
	private CountryDetailsPanelViewUI viewUI;
	
	public CountryDetailsModelUI(Country country) {
		super();
		setCountry(country);
		viewUI = new CountryDetailsPanelViewUI(AKUIEventSender.newInstance(this));
		viewUI.getModelUI().reset();
	}
	
	public CountryDetailsPanelViewUI getViewUI() {
		return viewUI;
	}
	
	protected Country getCountry() {
		return country;
	}

	protected void setCountry(Country country) {
		this.country = country;
	}


	protected void copyDataFromUI() {
		getCountry().setCode(getViewUI().getTxtCode().getText().trim());
		getCountry().setName(getViewUI().getTxtName().getText().trim());
	}

	protected void copyDataOnUI() {
		getViewUI().getTxtCode().setText(getCountry().getCode());
		getViewUI().getTxtName().setText(getCountry().getName());
	}

	protected void  validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtCode(), uic.NAME());
		Validator.validate255LengthComponent(getViewUI().getTxtCode(), uic.NAME());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtName(), uic.NAME());
		Validator.validate255LengthComponent(getViewUI().getTxtName(), uic.NAME());
	}

	public void setViewUI(CountryDetailsPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			if (getCountry().getId() == null){
				setCountry(new Country());
			}
			else{
				setCountry(getCountryService().reload(getCountry()));
			}

			copyDataOnUI();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void save() {
		try {
			validateData();
			copyDataFromUI();
			getCountryService().saveCountry(getCountry());
			cancel();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
}
