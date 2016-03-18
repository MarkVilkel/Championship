/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.points;

import java.awt.BorderLayout;

import com.ashihara.ui.app.points.model.PointsDetailsModelUI;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class PointsDetailsDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private PointsDetailsModelUI pointsDetailsModelUI;

	public PointsDetailsDialog() {
		super(ApplicationManager.getInstance().getUic().FIGHT_SETTINGS());
		
		getMainPanel().add(getPointsDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		pack();
		UIFactory.centerVisible(this);
	}

	public PointsDetailsModelUI getPointsDetailsModelUI() {
		if (pointsDetailsModelUI == null) {
			pointsDetailsModelUI = new PointsDetailsModelUI();
		}
		return pointsDetailsModelUI;
	}

}
