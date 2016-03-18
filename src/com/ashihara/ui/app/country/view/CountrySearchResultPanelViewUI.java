/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.country.model.ICountrySearchResultModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CountrySearchResultPanelViewUI extends KASPanel implements LinkClickingListener<Country>, UIView<ICountrySearchResultModelUI>{
	private static final long serialVersionUID = 1L;
	private CountryTable countryTable;
	private SearchClearButtonPanel searchClearButtonPanel; 
	private KASPanel searchPanelDetails;
	private JTextField txtCode, txtName;
	private ICountrySearchResultModelUI modelUI;

	public CountrySearchResultPanelViewUI(ICountrySearchResultModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getCountryTable(), BorderLayout.CENTER);
	}
	
	public class CountryTable extends AddDeleteButtonsTablePanel<Country>{
		private static final long serialVersionUID = 1L;

		public CountryTable() {
			super(Country.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
		}
		
		public void onAdd(Integer countToAdd) {
			getModelUI().onAddTableRows(countToAdd);
		}
		
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedTableRows();
		}
	}

	public CountryTable getCountryTable() {
		if (countryTable == null){
			countryTable = new CountryTable();
			countryTable.getTable().addLinkClickingListener(this);
			
			CM.Country cmCountry = new CM.Country(); 
			
			countryTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.CODE(), cmCountry.getCode()));
			countryTable.getTable().getKASModel().addColumn(new KASColumn(uic.NAME(), cmCountry.getName()));
			
			countryTable.showRowCount(uic.COUNTRIES_COUNT());
		}
		return countryTable;
	}

	public void linkClicked(Country value, String columnId) {
		getModelUI().onLinkClicked(value, columnId);
	}

	public SearchClearButtonPanel getSearchClearButtonPanel() {
		if (searchClearButtonPanel == null){
			ActionListener searchAl =  new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().search();
				}
			};
			ActionListener clearAl =  new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().clear();
				}
			};
			searchClearButtonPanel = new SearchClearButtonPanel(clearAl, searchAl);
			searchClearButtonPanel.setDetailsPanel(getSearchPanelDetails());
		}
		return searchClearButtonPanel;
	}

	public KASPanel getSearchPanelDetails() {
		if (searchPanelDetails == null){
			searchPanelDetails = new KASPanel();
			FormLayout fl = new FormLayout(
					"30dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref",
					"pref, 10dlu, pref, 2dlu, pref, 10dlu");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, searchPanelDetails);

			builder.addSeparator("  " + uic.SEARCH_BY_CRITERIA(), cc.xyw(1, 1, 4));

			builder.addLabel(uic.CODE(), cc.xy(2, 3));
			builder.add(getTxtCode(), cc.xy(2, 5));

			builder.addLabel(uic.NAME(), cc.xy(4, 3));
			builder.add(getTxtName(), cc.xy(4, 5));
			
		}
		
		return searchPanelDetails;
	}

	public JTextField getTxtCode() {
		if (txtCode == null){
			txtCode = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtCode;
	}

	public JTextField getTxtName() {
		if (txtName == null){
			txtName = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtName;
	}
	
	public ICountrySearchResultModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(ICountrySearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}
}