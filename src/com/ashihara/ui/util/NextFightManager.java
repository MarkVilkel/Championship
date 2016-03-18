package com.ashihara.ui.util;

import java.util.List;
import java.util.Vector;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.enums.SC;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;

public class NextFightManager {
	
	private static NextFightManager instance;
	
	private List<NextFightChangeListener> listeners = new Vector<NextFightChangeListener>();
	private FightResult nextFightResult;
	private UIStatePerformer<FightResult> nextUiStatePerformer;
	
	
	private NextFightManager() {
		
	}

	public static NextFightManager getInstance() {
		if (instance == null) {
			instance = new NextFightManager();
		}
		return instance;
	}
	
	public void addNextFightChangeListener(NextFightChangeListener l) {
		listeners.add(l);
	}

	public void removeNextFightChangeListener(NextFightChangeListener l) {
		listeners.remove(l);
	}
	
	private void fireNextFightChanged(FightResult fr) {
		for (NextFightChangeListener l : listeners) {
			l.nextFightChanged(fr);
		}
	}

	public synchronized FightResult getNextFightResult() {
		return nextFightResult;
	}

	public synchronized void setNextFightResult(FightResult nextFightResult) {
		
		if (
				nextFightResult != null &&
				SC.GROUP_TYPE.OLYMPIC.equals(nextFightResult.getFirstFighter().getFightingGroup().getType()) &&
				nextFightResult.getFirstFighterPointsForWin() != null &&
				nextFightResult.getSecondFighterPointsForWin() != null &&
				nextFightResult.getFirstFighterPointsForWin().longValue() != nextFightResult.getSecondFighterPointsForWin().longValue()
		) {
			MessageHelper.showErrorMessage(null, ApplicationManager.getInstance().getUic().CAM_NOT_SET_NEXT_FIGHT_FOR() + " " + nextFightResult);
			nextFightResult = null;
			this.nextUiStatePerformer = null;
		}
		
		this.nextFightResult = nextFightResult;
		fireNextFightChanged(nextFightResult);
	}

	public synchronized UIStatePerformer<FightResult> getNextUiStatePerformer() {
		return nextUiStatePerformer;
	}

	public synchronized void setNextUiStatePerformer(UIStatePerformer<FightResult> nextUiStatePerformer) {
		this.nextUiStatePerformer = nextUiStatePerformer;
	}
	
}
