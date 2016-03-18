/**
 * The file IFighterSearchResultModelUI.java was created on 2009.8.1 at 16:19:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory.model;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IWeightCategorySearchResultModelUI<T extends UIView> extends UIModel<T> {
	void search();
	void clear();
	void deleteSelectedTableRows();
	void onAddTableRows(Integer countToAdd);
	void onLinkClicked(WeightCategory value, String columnId);
}
