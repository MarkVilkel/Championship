/**
 * The file IOlympicSystemModel.java was created on 2010.19.4 at 22:21:13
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IOlympicSystemModel<T extends UIView> extends UIModel<T> {

	void exportToExcel();

}
