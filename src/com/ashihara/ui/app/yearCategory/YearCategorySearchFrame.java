/**
 * The file FightersSearchFrame.java was created on 2010.21.3 at 21:06:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory;

import java.awt.BorderLayout;

import com.ashihara.ui.app.yearCategory.model.YearCategorySearchResultModelUI;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class YearCategorySearchFrame extends AKModalDockable {

	private static final long serialVersionUID = 1L;
	
	private YearCategorySearchResultModelUI yearCategorySearchResultModelUI;

	public YearCategorySearchFrame() {
		super(ApplicationManager.getInstance().getUic().YEAR_CATEGORIES());
		
		getMainPanel().add(getYearCategorySearchResultModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public YearCategorySearchResultModelUI getYearCategorySearchResultModelUI() {
		if (yearCategorySearchResultModelUI == null) {
			yearCategorySearchResultModelUI = new YearCategorySearchResultModelUI();
		}
		return yearCategorySearchResultModelUI;
	}

}
