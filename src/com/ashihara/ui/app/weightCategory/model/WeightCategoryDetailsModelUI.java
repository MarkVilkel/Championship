/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory.model;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.ui.app.weightCategory.view.WeightCategoryDetailsPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class WeightCategoryDetailsModelUI extends AKAbstractModelUI<WeightCategoryDetailsPanelViewUI> implements IWeightCategoryDetailsModelUI<WeightCategoryDetailsPanelViewUI>{
	private WeightCategory weightCategory;
	private WeightCategoryDetailsPanelViewUI viewUI;
	
	public WeightCategoryDetailsModelUI(WeightCategory weightCategory) {
		super();
		setWeightCategory(weightCategory);
		viewUI = new WeightCategoryDetailsPanelViewUI(AKUIEventSender.newInstance(this));
		viewUI.getModelUI().reset();
	}
	
	public WeightCategoryDetailsPanelViewUI getViewUI() {
		return viewUI;
	}
	
	protected WeightCategory getWeightCategory() {
		return weightCategory;
	}

	protected void setWeightCategory(WeightCategory weightCategory) {
		this.weightCategory = weightCategory;
	}


	protected void copyDataFromUI() {
		getWeightCategory().setFromWeight(Double.valueOf(getViewUI().getTxtFrom().getText()));
		getWeightCategory().setToWeight(Double.valueOf(getViewUI().getTxtTo().getText()));
	}

	protected void copyDataOnUI() {
		getViewUI().getTxtFrom().setText(getWeightCategory().getFromWeight() == null ? "" : getWeightCategory().getFromWeight().toString());
		getViewUI().getTxtTo().setText(getWeightCategory().getToWeight() == null ? "" : getWeightCategory().getToWeight().toString());
	}

	protected void validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtFrom(), uic.FROM_WEIGHT());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtFrom(), uic.FROM_WEIGHT());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtTo(), uic.TO_WEIGHT());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtTo(), uic.TO_WEIGHT());
		
		if (new Double(getViewUI().getTxtFrom().getText()) >= new Double(getViewUI().getTxtTo().getText())) {
			throw new AKValidationException(uic.FROM_WIEGHT_MUST_BE_LESS_THAN_TO_WEGHT());
		}
		
		if (new Double(getViewUI().getTxtFrom().getText()) <= 0) {
			throw new AKValidationException(uic.FROM_WIEGHT_MUST_BE_POSITIVE());
		}
		
		if (new Double(getViewUI().getTxtTo().getText()) <= 0) {
			throw new AKValidationException(uic.TO_WIEGHT_MUST_BE_POSITIVE());
		}
	}

	public void setViewUI(WeightCategoryDetailsPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			if (getWeightCategory().getId() == null){
				setWeightCategory(new WeightCategory());
			}
			else{
				setWeightCategory(getWeightCategoryService().reload(getWeightCategory()));
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
			setWeightCategory(getWeightCategoryService().saveWeightCategory(getWeightCategory()));
			cancel();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
}
