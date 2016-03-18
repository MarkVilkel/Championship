/*
 * Copyright 2011 Dukascopy Bank SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ashihara.datamanagement.pojo.wraper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.pojo.FightResult;

/**
 * @author Mark Vilkel
 */
public class FightResultReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<FightResult> previousRounds = new ArrayList<FightResult>();
	private FightResult lastRound;
	
	
	public List<FightResult> getPreviousRounds() {
		return previousRounds;
	}
	public void setPreviousRounds(List<FightResult> previousRounds) {
		this.previousRounds = previousRounds;
	}
	public FightResult getLastRound() {
		return lastRound;
	}
	public void setLastRound(FightResult lastRound) {
		this.lastRound = lastRound;
	}
	
}
