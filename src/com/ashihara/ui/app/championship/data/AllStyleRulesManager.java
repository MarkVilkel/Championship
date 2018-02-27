/**
 * The file JosuiRulesManager.java was created on 27 feb. 2018 at 23:33:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

import com.ashihara.enums.UIC;

public class AllStyleRulesManager implements RulesManager {

	private final UIC uic;

	public AllStyleRulesManager(UIC uic) {
		this.uic = uic;
	}

	@Override
	public boolean hasSecondPenaltyCategory() {
		return false;
	}

	@Override
	public String getFirstPenaltyCategoryCaption() {
		return uic.PENALTY();
	}

	@Override
	public String getSecondPenaltyCategoryCaption() {
		return uic.PENALTY();
	}

}
