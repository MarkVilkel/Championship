/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory.model;

import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.app.yearCategory.view.YearCategoryDetailsPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class YearCategoryDetailsModelUI extends AKAbstractModelUI<YearCategoryDetailsPanelViewUI> implements IYearCategoryDetailsModelUI<YearCategoryDetailsPanelViewUI>{
	private YearCategory yearCategory;
	private YearCategoryDetailsPanelViewUI viewUI;
	
	public YearCategoryDetailsModelUI(YearCategory yearCategory) {
		super();
		setYearCategory(yearCategory);
		viewUI = new YearCategoryDetailsPanelViewUI(AKUIEventSender.newInstance(this));
		viewUI.getModelUI().reset();
	}
	
	public YearCategoryDetailsPanelViewUI getViewUI() {
		return viewUI;
	}
	
	protected YearCategory getYearCategory() {
		return yearCategory;
	}

	protected void setYearCategory(YearCategory yearCategory) {
		this.yearCategory = yearCategory;
	}


	protected void copyDataFromUI() {
		getYearCategory().setFromYear(Long.valueOf(getViewUI().getTxtFrom().getText()));
		getYearCategory().setToYear(Long.valueOf(getViewUI().getTxtTo().getText()));
		
		getYearCategory().setYearWeightCategories(getViewUI().getYearWeightCategoryTable().getTable().getKASModel().getDataRows());
	}

	protected void copyDataOnUI() {
		getViewUI().getTxtFrom().setText(getYearCategory().getFromYear() == null ? "" : getYearCategory().getFromYear().toString());
		getViewUI().getTxtTo().setText(getYearCategory().getToYear() == null ? "" : getYearCategory().getToYear().toString());
		
		if (getYearCategory().getYearWeightCategories() != null) {
			getViewUI().getYearWeightCategoryTable().getTable().getKASModel().setDataRows(getYearCategory().getYearWeightCategories());
		}
		
	}

	protected void validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtFrom(), uic.FROM_YEAR());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtFrom(), uic.FROM_YEAR());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtTo(), uic.TO_YEAR());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtTo(), uic.TO_YEAR());
		
		if (new Double(getViewUI().getTxtFrom().getText()) > new Double(getViewUI().getTxtTo().getText())) {
			throw new AKValidationException(uic.FROM_YEAR_MUST_BE_LESS_THAN_TO_YEAR());
		}
		
		if (new Double(getViewUI().getTxtFrom().getText()) <= 0) {
			throw new AKValidationException(uic.FROM_YEAR_MUST_BE_POSITIVE());
		}
		
		if (new Double(getViewUI().getTxtTo().getText()) <= 0) {
			throw new AKValidationException(uic.TO_YEAR_MUST_BE_POSITIVE());
		}
		
		List<YearWeightCategoryLink> list = getViewUI().getYearWeightCategoryTable().getTable().getKASModel().getDataRows();
		
		if (list == null || list.isEmpty()) {
			throw new AKValidationException(uic.AT_LEAST_ONE_WEIGHT_CATEGORY_HAS_TO_BE_ADDED());
		}
		
		Validator.validateMandatoryList(list, getCmYearWeightCategoryLink().getWeightCategory(), uic.WEIGHT_CATEGORY());
		
		Validator.validateUniqeList(list, getCmYearWeightCategoryLink().getWeightCategory(), uic.WEIGHT_CATEGORY());
		
	}

	public void setViewUI(YearCategoryDetailsPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			getViewUI().getYearWeightCategoryTable().getTable().getKASModel().clear();
			
			if (getYearCategory().getId() == null){
				setYearCategory(new YearCategory());
			}
			else{
				setYearCategory(getYearCategoryService().reload(getYearCategory()));
			}
			
			ComboUIHelper.reloadWeightCategoryCombo(getViewUI().getCmbWeightCategory(), true);

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
			getYearCategoryService().deleteYearWeightCategoryLinks(getViewUI().getYearWeightCategoryTable().getTable().getKASModel().getDataCleanDeleted());
			setYearCategory(getYearCategoryService().saveYearCategory(getYearCategory()));
			cancel();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
}
