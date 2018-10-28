/**
 * The file WarningsCountListener.java was created on 2011.16.10 at 10:53:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.fight.view.CountPanel;

public class WarningsCountListener implements CountListener {

	private final CountPanel pointsCountPanel;
	private final RulesManager rulesManager;
	private final WarningCountProvider warningCountProvider;
	private final boolean isFirstCategoryListener;
	
	public WarningsCountListener(
			CountPanel countPanel,
			RulesManager rulesManager,
			WarningCountProvider warningCountProvider,
			boolean isFirstCategoryListener
	) {
		this.pointsCountPanel = countPanel;
		this.rulesManager = rulesManager;
		this.warningCountProvider = warningCountProvider;
		this.isFirstCategoryListener = isFirstCategoryListener;
	}
	
	@Override
	public void countIncreased(CountPanel countPanel) {
		if (rulesManager.sumFirstAndSecondPenaltyCategory()) {
			long firstCategoryWarningCount = warningCountProvider.getFirstCategoryWarningCount();
			long secondCategoryWarningCount = warningCountProvider.getSecondCategoryWarningCount();
			Long increase = rulesManager.getWarningIncreaseCount(firstCategoryWarningCount, secondCategoryWarningCount, isFirstCategoryListener);
			if (increase != null) {
				pointsCountPanel.increaseCount(increase);
			}
		} else {
			long warningCount = countPanel.getCount();
			Long increase = rulesManager.getWarningIncreaseCount(warningCount);
			if (increase != null) {
				pointsCountPanel.increaseCount(increase);
			}
		}
	}
	
	@Override
	public void countDecreased(CountPanel countPanel) {
		if (rulesManager.sumFirstAndSecondPenaltyCategory()) {
			long firstCategoryWarningCount = warningCountProvider.getFirstCategoryWarningCount();
			long secondCategoryWarningCount = warningCountProvider.getSecondCategoryWarningCount();
			Long decrease = rulesManager.getWarningDecreaseCount(firstCategoryWarningCount, secondCategoryWarningCount, isFirstCategoryListener);
			if (decrease != null) {
				pointsCountPanel.decreaseCount(decrease);
			}
		} else {
			long warningCount = countPanel.getCount();
			Long decrease = rulesManager.getWarningDecreaseCount(warningCount);
			if (decrease != null) {
				pointsCountPanel.decreaseCount(decrease);
			}
		}
	}
	
}
