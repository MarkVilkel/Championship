/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.ui.app.weightCategory.model.WeightCategoryDetailsModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class WeightCategoryDetailsDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private WeightCategory weightCategory;
	private WeightCategoryDetailsModelUI weightCategoryDetailsModelUI;

	public WeightCategoryDetailsDialog(WeightCategory weightCategory, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().WEIGHT_CATEGORY(), dialogCallBackListener);
		
		this.weightCategory = weightCategory;
		
		getMainPanel().add(getWeightCategoryDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		UIFactory.sizeCenterVisible(this, new Dimension(400, 200));

	}

	public WeightCategoryDetailsModelUI getWeightCategoryDetailsModelUI() {
		if (weightCategoryDetailsModelUI == null) {
			weightCategoryDetailsModelUI = new WeightCategoryDetailsModelUI(weightCategory);
		}
		return weightCategoryDetailsModelUI;
	}

}
