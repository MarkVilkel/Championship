/**
 * The file IPreferencesModelUI.java was created on 2009.23.3 at 15:29:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.settings.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IPreferencesModelUI<T extends UIView> extends UIModel<T> {
	void ok();
	void cancel();
	void reset();
	void lookAndFeelSelected();
}
