/**
 * The file UIView.java was created on 2009.8.1 at 15:32:15
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.view;

import com.ashihara.ui.core.mvc.model.UIModel;

public interface UIView<T extends UIModel> {
	T getModelUI();
	void setModelUI(T modelUI);
}
