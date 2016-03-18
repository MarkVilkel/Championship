/**
 * The file FightersSearchFrame.java was created on 2010.21.3 at 21:06:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory;

import java.awt.BorderLayout;

import com.ashihara.ui.app.weightCategory.model.WeightCategorySearchResultModelUI;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class WeightCategorySearchFrame extends AKModalDockable {

	private static final long serialVersionUID = 1L;
	
	private WeightCategorySearchResultModelUI weightCategorySearchResultModelUI;

	public WeightCategorySearchFrame() {
		super(ApplicationManager.getInstance().getUic().WEIGHT_CATEGORIES());
		
		getMainPanel().add(getWeightCategorySearchResultModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public WeightCategorySearchResultModelUI getWeightCategorySearchResultModelUI() {
		if (weightCategorySearchResultModelUI == null) {
			weightCategorySearchResultModelUI = new WeightCategorySearchResultModelUI();
		}
		return weightCategorySearchResultModelUI;
	}

}
