/**
 * The file YearWeightSearchPanel.java was created on 2010.8.4 at 23:13:44
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearWeight.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class YearWeightSearchPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private SearchClearButtonPanel searchClearButtonPanel;
	private KASPanel searchPanelDetails;
	
	private final ActionListener searchAl, clearAl;
	private AutoCompleteComboBox<WeightCategory> cmbWeightCategory;
	private AutoCompleteComboBox<YearCategory> cmbYearCategory;
	
	private final String searchPanelCaption;

	public YearWeightSearchPanel(String searchPanelCaption, ActionListener searchAl, ActionListener clearAl) {
		this.searchAl = searchAl;
		this.clearAl = clearAl;
		this.searchPanelCaption = searchPanelCaption;
		
		init();
	}
	
	private void init() {
		add(getSearchClearButtonPanel(), BorderLayout.CENTER);
	}
	

	public SearchClearButtonPanel getSearchClearButtonPanel() {
		if (searchClearButtonPanel == null){
			searchClearButtonPanel = new SearchClearButtonPanel(clearAl, searchAl);
			searchClearButtonPanel.setDetailsPanel(getSearchPanelDetails());
		}
		return searchClearButtonPanel;
	}

	public KASPanel getSearchPanelDetails() {
		if (searchPanelDetails == null){
			searchPanelDetails = new KASPanel();
			FormLayout fl = new FormLayout(
					"30dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref",
					"pref, 10dlu, pref, 2dlu, pref, 10dlu");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, searchPanelDetails);

			builder.addSeparator("  " + searchPanelCaption, cc.xyw(1, 1, 4));

			builder.addLabel(uic.YEAR_CATEGORY(), cc.xy(2, 3));
			builder.add(getCmbYearCategory(), cc.xy(2, 5));

			builder.addLabel(uic.WEIGHT_CATEGORY(), cc.xy(4, 3));
			builder.add(getCmbWeightCategory(), cc.xy(4, 5));
			
		}
		
		return searchPanelDetails;
	}
	
	public AutoCompleteComboBox<WeightCategory> getCmbWeightCategory() {
		if (cmbWeightCategory == null) {
			cmbWeightCategory = UIFactory.createAutoCompleteComboBox20(WeightCategory.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbWeightCategory;
	}

	public AutoCompleteComboBox<YearCategory> getCmbYearCategory() {
		if (cmbYearCategory == null) {
			cmbYearCategory = UIFactory.createAutoCompleteComboBox20(YearCategory.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbYearCategory;
	}

}
