/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.ui.app.yearCategory.model.YearCategoryDetailsModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class YearCategoryDetailsDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private YearCategory yearCategory;
	private YearCategoryDetailsModelUI yearCategoryDetailsModelUI;

	public YearCategoryDetailsDialog(YearCategory yearCategory, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().YEAR_CATEGORY(), dialogCallBackListener);
		
		this.yearCategory = yearCategory;
		
		getMainPanel().add(getYearCategoryDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		UIFactory.sizeCenterVisible(this, new Dimension(500, 400));

	}

	public YearCategoryDetailsModelUI getYearCategoryDetailsModelUI() {
		if (yearCategoryDetailsModelUI == null) {
			yearCategoryDetailsModelUI = new YearCategoryDetailsModelUI(yearCategory);
		}
		return yearCategoryDetailsModelUI;
	}

}
