/**
 * The file JosuiRulesManager.java was created on 27 february 2018 at 23:33:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import com.ashihara.enums.UIC;

public class JosuiStyleRulesManager implements RulesManager {
	
	private final UIC uic;

	public JosuiStyleRulesManager(UIC uic) {
		this.uic = uic;
	}

	@Override
	public boolean hasSecondPenaltyCategory() {
		return true;
	}

	@Override
	public String getFirstPenaltyCategoryCaption() {
		return uic.FIRST_CATEGORY();
	}

	@Override
	public String getSecondPenaltyCategoryCaption() {
		return uic.SECOND_CATEGORY();
	}

	@Override
	public boolean redFighterFromTheLeft() {
		return true;
	}

	@Override
	public long getMaxRoundsCount() {
		return Long.MAX_VALUE;
	}

}
