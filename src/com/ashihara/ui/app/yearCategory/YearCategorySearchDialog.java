/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.ui.app.yearCategory.model.YearCategorySearchResultModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class YearCategorySearchDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	public static final String SELECTED_YEAR_CATEGORIES = "SELECTED_YEAR_CATEGORIES";
	
	private YearCategorySearchResultModelUI yearCategorySearchResultModelUI;
	private final List<YearCategory> exceptYearCategories;

	public YearCategorySearchDialog(List<YearCategory> exceptYearCategories, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().SEARCH_YEAR_CATEGORIES(), dialogCallBackListener);
		
		this.exceptYearCategories = exceptYearCategories;
		getMainPanel().add(getYearCategorySearchResultModelUI().getViewUI(), BorderLayout.CENTER);
		
		pack();
		UIFactory.centerVisible(this);

	}

	public YearCategorySearchResultModelUI getYearCategorySearchResultModelUI() {
		if (yearCategorySearchResultModelUI == null) {
			yearCategorySearchResultModelUI = new YearCategorySearchResultModelUI(exceptYearCategories);
		}
		return yearCategorySearchResultModelUI;
	}

	protected Map<String, Object> getDialogCallBackListenerParams() {
		Map<String, Object> paramsMap = super.getDialogCallBackListenerParams();
		paramsMap.put(SELECTED_YEAR_CATEGORIES, getYearCategorySearchResultModelUI().getSelectedYearCategories());
		return paramsMap;
	}
}
