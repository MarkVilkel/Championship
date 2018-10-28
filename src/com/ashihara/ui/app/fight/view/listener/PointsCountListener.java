/**
 * The file PointsCountListener.java was created on 2011.7.12 at 22:42:08
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.fight.view.CountPanel;

public class PointsCountListener implements CountListener {

	private final CountPanel pointsCountPanel;
	private final RulesManager rulesManager;
	
	public PointsCountListener(
			CountPanel countPanel,
			RulesManager rulesManager
	) {
		this.pointsCountPanel = countPanel;
		this.rulesManager = rulesManager;
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
		Long pointsDifferenceForWin = rulesManager.getPointsDifferenceForWin();
		
		if (pointsDifferenceForWin != null) {
			if (countPanel.getCount() - pointsCountPanel.getCount() >= pointsDifferenceForWin) {
				countPanel.setMaxCount(countPanel.getCount());
			} else if (pointsCountPanel.getCount() - countPanel.getCount() >= pointsDifferenceForWin) {
				pointsCountPanel.setMaxCount(pointsCountPanel.getCount());
			} else {
				countPanel.setMaxCount(rulesManager.getMaxPointsCount());
				pointsCountPanel.setMaxCount(rulesManager.getMaxPointsCount());
			}
		}
		
		countPanel.showCount();
		pointsCountPanel.showCount();
		
	}

}
