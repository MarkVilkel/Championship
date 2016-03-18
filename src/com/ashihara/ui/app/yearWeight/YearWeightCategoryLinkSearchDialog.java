/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearWeight;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import com.ashihara.ui.app.yearWeight.model.YearWeightCategoryLinkSearchResultModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class YearWeightCategoryLinkSearchDialog extends KASDialog {

	private static final long serialVersionUID = 1L;

	public static final String SELECTED_CATEGORIES = "SELECTED_CATEGORIES";

	private YearWeightCategoryLinkSearchResultModelUI yearWeightCategoryLinkSearchResultModelUI;

	public YearWeightCategoryLinkSearchDialog(DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().SEARCH_WEIGHT_CATEGORIES(), dialogCallBackListener);
		
		getMainPanel().add(getYearWeightCategoryLinkSearchResultModelUI().getViewUI(), BorderLayout.CENTER);
		
		UIFactory.sizeCenterVisible(this, new Dimension(800, 600));

	}

	public YearWeightCategoryLinkSearchResultModelUI getYearWeightCategoryLinkSearchResultModelUI() {
		if (yearWeightCategoryLinkSearchResultModelUI == null) {
			yearWeightCategoryLinkSearchResultModelUI = new YearWeightCategoryLinkSearchResultModelUI();
		}
		return yearWeightCategoryLinkSearchResultModelUI;
	}

	protected Map<String, Object> getDialogCallBackListenerParams() {
		Map<String, Object> paramsMap = super.getDialogCallBackListenerParams();
		paramsMap.put(SELECTED_CATEGORIES, getYearWeightCategoryLinkSearchResultModelUI().getSelectedYearWeightCategoryLinks());
		return paramsMap;
	}

}
