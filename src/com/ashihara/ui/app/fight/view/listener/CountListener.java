/**
 * The file CountListener.java was created on 28 окт. 2018 г. at 8:59:52
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view.listener;

import com.ashihara.ui.app.fight.view.CountPanel;

public interface CountListener {
	void countIncreased(CountPanel countPanel);
	void countDecreased(CountPanel countPanel);
}
