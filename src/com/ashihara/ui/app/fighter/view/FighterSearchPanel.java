/**
 * The file FighterSearchPanel.java was created on 2010.19.4 at 23:57:25
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FighterSearchPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtName, txtSurname;
	private AutoCompleteComboBox<Country> cmbCountry;
	private AutoCompleteComboBox<String> cmbGender; 

	private KASPanel searchPanelDetails;
	private SearchClearButtonPanel searchClearButtonPanel;
	private final ActionListener searchAl, clearAl;

	public FighterSearchPanel(ActionListener searchAl, ActionListener clearAl) {
		super();
		
		this.searchAl = searchAl;
		this.clearAl = clearAl;
		
		init();
	}

	private void init() {
		add(getSearchClearButtonPanel(), BorderLayout.CENTER);
	}
	
	public KASPanel getSearchPanelDetails() {
		if (searchPanelDetails == null){
			searchPanelDetails = new KASPanel();
			FormLayout fl = new FormLayout(
					"30dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref",
					"pref, 10dlu, pref, 2dlu, pref, 10dlu");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, searchPanelDetails);

			builder.addSeparator("  " + uic.FILTER_FIGHTERS_BY_CRITERIA(), cc.xyw(1, 1, 6));

			builder.addLabel(uic.NAME(), cc.xy(2, 3));
			builder.add(getTxtName(), cc.xy(2, 5));

			builder.addLabel(uic.SURNAME(), cc.xy(4, 3));
			builder.add(getTxtSurname(), cc.xy(4, 5));
			
			builder.addLabel(uic.GENDER(), cc.xy(6, 3));
			builder.add(getCmbGender(), cc.xy(6, 5));
			
			builder.addLabel(uic.COUNTRY(), cc.xy(8, 3));
			builder.add(getCmbCountry(), cc.xy(8, 5));
		}
		
		return searchPanelDetails;
	}

	

	public JTextField getTxtName() {
		if (txtName == null){
			txtName = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getTxtName().requestFocus();
				}
			});
		}
		return txtName;
	}

	public JTextField getTxtSurname() {
		if (txtSurname == null){
			txtSurname = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtSurname;
	}
	
	public AutoCompleteComboBox<Country> getCmbCountry() {
		if (cmbCountry == null) {
			cmbCountry = UIFactory.createAutoCompleteComboBox20(Country.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbCountry;
	}
	
	public SearchClearButtonPanel getSearchClearButtonPanel() {
		if (searchClearButtonPanel == null){
			searchClearButtonPanel = new SearchClearButtonPanel(clearAl, searchAl);
			searchClearButtonPanel.setDetailsPanel(getSearchPanelDetails());
		}
		return searchClearButtonPanel;
	}

	public AutoCompleteComboBox<String> getCmbGender() {
		if (cmbGender == null) {
			cmbGender = UIFactory.createAutoCompleteComboBox20(String.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbGender;
	}

}
