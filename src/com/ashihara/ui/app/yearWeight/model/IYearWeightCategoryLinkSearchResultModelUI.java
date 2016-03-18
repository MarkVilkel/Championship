/**
 * The file IFighterSearchResultModelUI.java was created on 2009.8.1 at 16:19:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearWeight.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IYearWeightCategoryLinkSearchResultModelUI<T extends UIView> extends UIModel<T> {
	void search();
	void clear();
	void select();
}
