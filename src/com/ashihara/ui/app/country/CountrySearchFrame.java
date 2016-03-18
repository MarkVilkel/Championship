/**
 * The file FightersSearchFrame.java was created on 2010.21.3 at 21:06:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country;

import java.awt.BorderLayout;

import com.ashihara.ui.app.country.model.CountrySearchResultModelUI;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class CountrySearchFrame extends AKModalDockable {

	private static final long serialVersionUID = 1L;
	
	private CountrySearchResultModelUI countrySearchResultModelUI;

	public CountrySearchFrame() {
		super(ApplicationManager.getInstance().getUic().COUNTRIES());
		
		getMainPanel().add(getCountrySearchResultModelUI().getViewUI(), BorderLayout.CENTER);
	}

	public CountrySearchResultModelUI getCountrySearchResultModelUI() {
		if (countrySearchResultModelUI == null) {
			countrySearchResultModelUI = new CountrySearchResultModelUI();
		}
		return countrySearchResultModelUI;
	}

}
