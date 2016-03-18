/**
 * The file WarningsCountListener.java was created on 2011.16.10 at 10:53:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.fight.view.CountPanel;
import com.ashihara.ui.app.fight.view.CountPanel.CountListener;

public class WarningsCountListener implements CountListener {
	private final CountPanel pointsCountPanel;
	
	public WarningsCountListener(CountPanel countPanel) {
		this.pointsCountPanel = countPanel;
	}
	
	@Override
	public void countIncreased(CountPanel countPanel) {
		if (countPanel.getCount() == 2) {
			pointsCountPanel.increaseCount(1);
		}
		else if (countPanel.getCount() == 3) {
			pointsCountPanel.increaseCount(2);
		}
	}
	@Override
	public void countDecreased(CountPanel countPanel) {
		if (countPanel.getCount() == 2) {
			pointsCountPanel.decreaseCount(2);
		}
		else if (countPanel.getCount() == 1) {
			pointsCountPanel.decreaseCount(1);
		}
	}
}
