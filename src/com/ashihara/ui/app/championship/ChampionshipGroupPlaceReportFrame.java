/**
 * The file ChampionshipResultFrame.java was created on 2011.17.10 at 22:23:55
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.ui.app.championship.model.ChampionshipGroupPlaceReportModelUI;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;

public class ChampionshipGroupPlaceReportFrame extends AKIdentifiedDockable<Long> {

	private static final long serialVersionUID = 1L;
	
	private final Championship championship;
	
	private ChampionshipGroupPlaceReportModelUI championshipGroupPlaceReportModelUI;

	public ChampionshipGroupPlaceReportFrame(Championship championship) {
		super(
				uic.CHAMPIONSHIP() + " '" + championship.getName() + "' " + uic.FORMAT_DATE(championship.getBeginningDate()) + " " + uic.GROUP_PLACE_REPORT().toLowerCase(),
				championship.getId()
		);
		
		this.championship = championship;
		
		getMainPanel().add(getChampionshipGroupPlaceReportModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public ChampionshipGroupPlaceReportModelUI getChampionshipGroupPlaceReportModelUI() {
		if (championshipGroupPlaceReportModelUI == null) {
			championshipGroupPlaceReportModelUI = new ChampionshipGroupPlaceReportModelUI(championship);
		}
		return championshipGroupPlaceReportModelUI;
	}

}
