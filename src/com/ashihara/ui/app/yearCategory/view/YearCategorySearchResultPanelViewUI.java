/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearCategory.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.yearCategory.model.IYearCategorySearchResultModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class YearCategorySearchResultPanelViewUI extends KASPanel implements LinkClickingListener<YearCategory>, UIView<IYearCategorySearchResultModelUI>{
	private static final long serialVersionUID = 1L;
	private YearCategoryTable yearCategoryTable;
	private SearchClearButtonPanel searchClearButtonPanel; 
	private KASPanel searchPanelDetails;
	private JTextField txtFrom, txtTo;
	private IYearCategorySearchResultModelUI modelUI;
	private JButton btnSelect;
	
	public static final String SELECT_MODE = "SELECT_MODE";
	public static final String EDIT_MODE = "EDIT_MODE";
	
	public final String mode;

	public YearCategorySearchResultPanelViewUI(IYearCategorySearchResultModelUI modelUI, String mode){
		super();
		this.mode = mode;
		this.modelUI = modelUI;
		
		init();
	}
	
	public YearCategorySearchResultPanelViewUI(IYearCategorySearchResultModelUI modelUI){
		this(modelUI, EDIT_MODE);
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getYearCategoryTable(), BorderLayout.CENTER);
	}
	
	public class YearCategoryTable extends AddDeleteButtonsTablePanel<YearCategory>{
		private static final long serialVersionUID = 1L;

		public YearCategoryTable() {
			super(YearCategory.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			if (SELECT_MODE.equals(getMode())) {
				getButtonsPanel().getBtnAdd().setVisible(false);
				getButtonsPanel().getBtnDelete().setVisible(false);
				getButtonsPanel().add(getBtnSelect());
			}
		}
		
		public void onAdd(Integer countToAdd) {
			getModelUI().onAddTableRows(countToAdd);
		}
		
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedTableRows();
		}
		
		public void selectedValueChanged(ListSelectionEvent event) {
			super.selectedValueChanged(event);
			getBtnSelect().setEnabled(getTable().getSelectedRowCount() != 0);
		}
	}

	public YearCategoryTable getYearCategoryTable() {
		if (yearCategoryTable == null){
			yearCategoryTable = new YearCategoryTable();
			yearCategoryTable.getTable().addLinkClickingListener(this);
			
			CM.YearCategory cmYearCategory = new CM.YearCategory(); 
			
			if (SELECT_MODE.equals(getMode())) {
				yearCategoryTable.getTable().getKASModel().addColumn(new KASColumn(uic.FROM_YEAR(), cmYearCategory.getFromYear()));
			}
			else {
				yearCategoryTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.FROM_YEAR(), cmYearCategory.getFromYear()));
			}
			yearCategoryTable.getTable().getKASModel().addColumn(new KASColumn(uic.TO_YEAR(), cmYearCategory.getToYear()));
			yearCategoryTable.getTable().getKASModel().addColumn(new KASColumn(uic.INCLUDED_WEIGHT_CATEGORIES(), cmYearCategory.getWeightCategories()));
			
			yearCategoryTable.showRowCount(uic.YEAR_CATEGORIES_COUNT());
		}
		return yearCategoryTable;
	}

	public void linkClicked(YearCategory value, String columnId) {
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

			builder.addLabel(uic.FROM_YEAR() + " >", cc.xy(2, 3));
			builder.add(getTxtFrom(), cc.xy(2, 5));

			builder.addLabel(uic.TO_YEAR()+ " <", cc.xy(4, 3));
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
	
	public IYearCategorySearchResultModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IYearCategorySearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public String getMode() {
		return mode;
	}
	
	public JButton getBtnSelect() {
		if (btnSelect == null) {
			
			btnSelect = new JButton(uic.SELECT_YEAR_CATEGORIES());
			btnSelect.setToolTipText(uic.SELECT_YEAR_CATEGORIES());
			btnSelect.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));

			btnSelect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().selectYearCategories();
				}
			});
			
			btnSelect.setEnabled(false);
		}
		return btnSelect;
	}
}