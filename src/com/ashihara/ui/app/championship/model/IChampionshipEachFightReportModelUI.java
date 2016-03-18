/**
 * The file IChampionshipResultModelUI.java was created on 2011.17.10 at 22:24:52
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.ui.core.mvc.model.UIModel;
import com.ashihara.ui.core.mvc.view.UIView;

public interface IChampionshipEachFightReportModelUI<T extends UIView<?>> extends UIModel<T>{

	void filter();

	void clear();

	void groupSelected();

	void linkClicked(FightResultReport value, String columnId);

}
