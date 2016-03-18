/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.model.IChampionshipSearchResultModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.renderer.DateCellRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ChampionshipSearchResultPanelViewUI extends KASPanel implements LinkClickingListener<Championship>, UIView<IChampionshipSearchResultModelUI>{
	private static final long serialVersionUID = 1L;
	private ChampionshipTable championshipTable;
	private SearchClearButtonPanel searchClearButtonPanel; 
	private KASPanel searchPanelDetails;
	private JTextField txtName;
	private IChampionshipSearchResultModelUI modelUI;

	public ChampionshipSearchResultPanelViewUI(IChampionshipSearchResultModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getChampionshipTable(), BorderLayout.CENTER);
	}
	
	public class ChampionshipTable extends AddDeleteButtonsTablePanel<Championship>{
		private static final long serialVersionUID = 1L;

		public ChampionshipTable() {
			super(Championship.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			getButtonsPanel().getBtnAdd().setVisible(false);
		}
		
		public void onAdd(Integer countToAdd) {
		}
		
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedTableRows();
		}
	}

	public ChampionshipTable getChampionshipTable() {
		if (championshipTable == null){
			championshipTable = new ChampionshipTable();
			championshipTable.getTable().addLinkClickingListener(this);
			
			CM.Championship cmChampionship = new CM.Championship(); 
			
			championshipTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.NAME(), cmChampionship.getName()));
			championshipTable.getTable().getKASModel().addColumn(new KASColumn(uic.BEGINNING_DATE(), cmChampionship.getBeginningDate(), new DateCellRenderer()));
			championshipTable.getTable().getKASModel().addColumn(KASColumn.createFakeLinkColumn(uic.GROUP_PLACE_REPORT(), uic.SHOW_REPORT()));
			championshipTable.getTable().getKASModel().addColumn(KASColumn.createFakeLinkColumn(uic.EACH_FIGHT_REPORT(), uic.SHOW_REPORT()));
			
			championshipTable.showRowCount(uic.CHAMPIONSHIPS_COUNT());
		}
		return championshipTable;
	}

	public void linkClicked(Championship value, String columnId) {
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

			builder.addLabel(uic.NAME(), cc.xy(2, 3));
			builder.add(getTxtName(), cc.xy(2, 5));
			
		}
		
		return searchPanelDetails;
	}

	public JTextField getTxtName() {
		if (txtName == null){
			txtName = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
		}
		return txtName;
	}
	
	public IChampionshipSearchResultModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IChampionshipSearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}
}