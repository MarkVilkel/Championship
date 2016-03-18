/**
 * The file FightersSearchFrame.java was created on 2010.21.3 at 21:06:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.enums.UIC;
import com.ashihara.ui.app.championship.model.ChampionshipEditModelUI;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;

public class ChampionshipEditFrame extends AKIdentifiedDockable<Long> implements UIStatePerformer<Championship>{

	private static final long serialVersionUID = 1L;
	
	private final Championship championship;
	
	private ChampionshipEditModelUI championshipEditModelUI;

	public ChampionshipEditFrame(Championship championship) {
		super("", championship.getId());
		
		this.championship = championship;
		
		performUIState(championship);
		
		getMainPanel().add(getChampionshipEditModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public ChampionshipEditModelUI getChampionshipEditModelUI() {
		if (championshipEditModelUI == null) {
			championshipEditModelUI = new ChampionshipEditModelUI(championship, this);
		}
		return championshipEditModelUI;
	}

	@Override
	public void performUIState(Championship param) {
		UIC uic = ApplicationManager.getInstance().getUic();
		String title = "";
		
		if (param.getId() != null) {
			title = uic.CHAMPIONSHIP() + " '" + param.getName() + "' " + uic.FORMAT_DATE(param.getBeginningDate());
			setId(param.getId());
		}
		else {
			title = uic.NEW_CHAMPIONSHIP();
		}
		
		changeTitle(title);
	}

}
