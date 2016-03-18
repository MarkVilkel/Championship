/**
 * The file FightersSearchFrame.java was created on 2010.21.3 at 21:06:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter;

import java.awt.BorderLayout;

import com.ashihara.ui.app.fighter.model.FighterSearchResultModelUI;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class FightersSearchFrame extends AKModalDockable {

	private static final long serialVersionUID = 1L;
	
	private FighterSearchResultModelUI fighterSearchResultModelUI;

	public FightersSearchFrame() {
		super(ApplicationManager.getInstance().getUic().FIGHTERS());
		
		getMainPanel().add(getFighterSearchResultModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public FighterSearchResultModelUI getFighterSearchResultModelUI() {
		if (fighterSearchResultModelUI == null) {
			fighterSearchResultModelUI = new FighterSearchResultModelUI();
		}
		return fighterSearchResultModelUI;
	}

}
