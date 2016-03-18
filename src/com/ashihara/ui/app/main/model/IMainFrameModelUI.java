/**
 * The file IMainFrameModelUI.java was created on 2009.22.1 at 12:52:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.main.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IMainFrameModelUI<T extends UIView> extends UIModel<T> {
	void exit();
	void configureMenu();
	void onMainWindowClose();
	void onMainWindowClose(Boolean closeSession);
	void disposeApplication();
}