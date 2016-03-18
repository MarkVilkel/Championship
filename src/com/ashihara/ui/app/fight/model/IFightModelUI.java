/**
 * The file IFightModelUI.java was created on 2011.16.10 at 08:55:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IFightModelUI<T extends UIView<?>> extends UIModel<T> {

	void saveFightResult();

	void nextRound();

	void switchFighters();

}
