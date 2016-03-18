/**
 * The file ChampionshipSearchResultFrame.java was created on 2010.7.4 at 20:53:36
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship;

import java.awt.BorderLayout;

import com.ashihara.ui.app.championship.model.ChampionshipSearchResultModelUI;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class ChampionshipSearchResultFrame extends AKModalDockable {

	private static final long serialVersionUID = 1L;

	private ChampionshipSearchResultModelUI championshipSearchResultModelUI;
	
	public ChampionshipSearchResultFrame() {
		super(ApplicationManager.getInstance().getUic().SEARCH_CHAMPIONSHIPS());
		
		getMainPanel().add(getChampionshipSearchResultModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public ChampionshipSearchResultModelUI getChampionshipSearchResultModelUI() {
		if (championshipSearchResultModelUI == null) {
			championshipSearchResultModelUI = new ChampionshipSearchResultModelUI();
		}
		return championshipSearchResultModelUI;
	}

}
