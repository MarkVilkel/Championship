/**
 * The file RulesManager.java was created on 27 feb. 2018 at 23:32:57
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.data;

public interface RulesManager {

	boolean hasSecondPenaltyCategory();
	String getFirstPenaltyCategoryCaption();
	String getSecondPenaltyCategoryCaption();

	boolean redFighterFromTheLeft();
	long getMaxRoundsCount();

}
