/**
 * The file ChampionshipResultFrame.java was created on 2011.17.10 at 22:23:55
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.ui.app.championship.model.ChampionshipEachFightReportModelUI;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;

public class ChampionshipEachFightReportFrame extends AKIdentifiedDockable<Long> {

	private static final long serialVersionUID = 1L;
	
	private final Championship championship;
	
	private ChampionshipEachFightReportModelUI championshipEachFightReportModelUI;

	public ChampionshipEachFightReportFrame(Championship championship) {
		super(
				uic.CHAMPIONSHIP() + " '" + championship.getName() + "' " + uic.FORMAT_DATE(championship.getBeginningDate()) + " " + uic.EACH_FIGHT_REPORT().toLowerCase(),
				championship.getId()
		);
		
		this.championship = championship;
		
		getMainPanel().add(getChampionshipEachFightReportModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public ChampionshipEachFightReportModelUI getChampionshipEachFightReportModelUI() {
		if (championshipEachFightReportModelUI == null) {
			championshipEachFightReportModelUI = new ChampionshipEachFightReportModelUI(championship);
		}
		return championshipEachFightReportModelUI;
	}

}
