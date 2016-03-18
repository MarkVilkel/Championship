/**
 * The file IFighterDetailsModelUI.java was created on 2009.8.1 at 17:21:44
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IFighterDetailsModelUI<T extends UIView> extends UIModel<T>{

	void save();

	void reset();

	void cancel();

	void loadPhoto();

	void deletePhoto();

}
