/**
 * The file PointsCountListener.java was created on 2011.7.12 at 22:42:08
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.fight.view.CountPanel;
import com.ashihara.ui.app.fight.view.CountPanel.CountListener;

public class PointsCountListener implements CountListener {

	private final CountPanel pointsCountPanel;
	
	public PointsCountListener(CountPanel countPanel) {
		this.pointsCountPanel = countPanel;
	}
	
	@Override
	public void countIncreased(CountPanel countPanel) {
		perfrorm(countPanel);
	}
	
	@Override
	public void countDecreased(CountPanel countPanel) {
		perfrorm(countPanel);
	}
	
	private void perfrorm(CountPanel countPanel) {
		if (countPanel.getCount() - pointsCountPanel.getCount() >= 8) {
			countPanel.setMaxCount(countPanel.getCount());
		}
		else if (pointsCountPanel.getCount() - countPanel.getCount() >= 8) {
			pointsCountPanel.setMaxCount(pointsCountPanel.getCount());
		}
		else {
			countPanel.setMaxCount(100);
			pointsCountPanel.setMaxCount(100);
		}
		
		countPanel.showCount();
		pointsCountPanel.showCount();
		
	}

}
