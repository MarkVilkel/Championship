/**
 * The file IGroupEditModel.java was created on 2010.11.4 at 12:50:28
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.model;

import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IGroupEditModel<T extends UIView> extends UIModel<T> {
	void save();
	void reset();
	void cancel();
	void start();
	void reloadTournamentResultTable();

}
