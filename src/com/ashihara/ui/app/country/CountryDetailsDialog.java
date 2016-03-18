/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.ui.app.country.model.CountryDetailsModelUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class CountryDetailsDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	private Country country;
	private CountryDetailsModelUI countryDetailsModelUI;

	public CountryDetailsDialog(Country country, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().COUNTRY(), dialogCallBackListener);
		
		this.country = country;
		
		getMainPanel().add(getCountryDetailsModelUI().getViewUI(), BorderLayout.CENTER);
		
		pack();
		UIFactory.centerVisible(this);

	}

	public CountryDetailsModelUI getCountryDetailsModelUI() {
		if (countryDetailsModelUI == null) {
			countryDetailsModelUI = new CountryDetailsModelUI(country);
		}
		return countryDetailsModelUI;
	}

}
