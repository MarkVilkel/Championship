/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.weightCategory.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.weightCategory.model.IWeightCategorySearchResultModelUI;
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

public class WeightCategorySearchResultPanelViewUI extends KASPanel implements LinkClickingListener<WeightCategory>, UIView<IWeightCategorySearchResultModelUI>{
	private static final long serialVersionUID = 1L;
	private WeightCategoryTable weightCategoryTable;
	private SearchClearButtonPanel searchClearButtonPanel; 
	private KASPanel searchPanelDetails;
	private JTextField txtFrom, txtTo;
	private IWeightCategorySearchResultModelUI modelUI;

	public WeightCategorySearchResultPanelViewUI(IWeightCategorySearchResultModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getCountryTable(), BorderLayout.CENTER);
	}
	
	public class WeightCategoryTable extends AddDeleteButtonsTablePanel<WeightCategory>{
		private static final long serialVersionUID = 1L;

		public WeightCategoryTable() {
			super(WeightCategory.class);
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

	public WeightCategoryTable getCountryTable() {
		if (weightCategoryTable == null){
			weightCategoryTable = new WeightCategoryTable();
			weightCategoryTable.getTable().addLinkClickingListener(this);
			
			CM.WeightCategory cmWeightCategory = new CM.WeightCategory(); 
			
			weightCategoryTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.FROM_WEIGHT(), cmWeightCategory.getFromWeight()));
			weightCategoryTable.getTable().getKASModel().addColumn(new KASColumn(uic.TO_WEIGHT(), cmWeightCategory.getToWeight()));
			
			weightCategoryTable.showRowCount(uic.WEIGHT_CATEGORIES_COUNT());
		}
		return weightCategoryTable;
	}

	public void linkClicked(WeightCategory value, String columnId) {
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

			builder.addLabel(uic.FROM_WEIGHT() + " >", cc.xy(2, 3));
			builder.add(getTxtFrom(), cc.xy(2, 5));

			builder.addLabel(uic.TO_WEIGHT()+ " <", cc.xy(4, 3));
			builder.add(getTxtTo(), cc.xy(4, 5));
			
		}
		
		return searchPanelDetails;
	}

	public JTextField getTxtFrom() {
		if (txtFrom == null){
			txtFrom = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtFrom;
	}

	public JTextField getTxtTo() {
		if (txtTo == null){
			txtTo = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtTo;
	}
	
	public IWeightCategorySearchResultModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IWeightCategorySearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}
}