/**
 * The file IAuthorizationModelUI.java was created on 2009.10.1 at 16:35:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.auth.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IAuthorizationModelUI<T extends UIView<?>> extends UIModel<T> {
	void okPressed();
	void cancelPressed();
	void login(String name, Long pwd);
//	void switchLanguage();
}
