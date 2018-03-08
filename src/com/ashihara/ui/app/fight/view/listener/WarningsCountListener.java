/**
 * The file WarningsCountListener.java was created on 2011.16.10 at 10:53:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.fight.view.CountPanel;
import com.ashihara.ui.app.fight.view.CountPanel.CountListener;

public class WarningsCountListener implements CountListener {

	private final CountPanel pointsCountPanel;
	private final RulesManager rulesManager;
	
	public WarningsCountListener(
			CountPanel countPanel,
			RulesManager rulesManager
	) {
		this.pointsCountPanel = countPanel;
		this.rulesManager = rulesManager;
	}
	
	@Override
	public void countIncreased(CountPanel countPanel) {
		long warningCount = countPanel.getCount();
		Long increase = rulesManager.getWarningIncreaseCount(warningCount);
		if (increase != null) {
			pointsCountPanel.increaseCount(increase);
		}
	}
	
	@Override
	public void countDecreased(CountPanel countPanel) {
		long warningCount = countPanel.getCount();
		Long increase = rulesManager.getWarningDecreaseCount(warningCount);
		if (increase != null) {
			pointsCountPanel.decreaseCount(increase);
		}
	}
	
}
