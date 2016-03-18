/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.yearWeight.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;

import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.yearWeight.model.IYearWeightCategoryLinkSearchResultModelUI;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class YearWeightCategoryLinkSearchResultPanelViewUI extends KASPanel implements UIView<IYearWeightCategoryLinkSearchResultModelUI> {
	
	private static final long serialVersionUID = 1L;
	
	private YearWeightCategoryLinkTable yearWeightCategoryLinkTable;
	private YearWeightSearchPanel yearWeightSearchPanel; 
	private KASPanel searchPanelDetails;
	private IYearWeightCategoryLinkSearchResultModelUI modelUI;
	private JButton btnSelect;

	public YearWeightCategoryLinkSearchResultPanelViewUI(IYearWeightCategoryLinkSearchResultModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getSearchPanel(), BorderLayout.NORTH);
		add(getYearWeightCategoryLinkTable(), BorderLayout.CENTER);
	}
	
	public class YearWeightCategoryLinkTable extends AddDeleteButtonsTablePanel<YearWeightCategoryLink>{
		private static final long serialVersionUID = 1L;

		public YearWeightCategoryLinkTable() {
			super(YearWeightCategoryLink.class);
			
			getButtonsPanel().add(getBtnSelect());
			
			getButtonsPanel().getBtnAdd().setVisible(false);
			getButtonsPanel().getBtnDelete().setVisible(false);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
		}
		
		public void selectedValueChanged(ListSelectionEvent event) {
			super.selectedValueChanged(event);
			getBtnSelect().setEnabled(getTable().getSelectedRowCount() != 0);
		}
	}

	public YearWeightCategoryLinkTable getYearWeightCategoryLinkTable() {
		if (yearWeightCategoryLinkTable == null){
			yearWeightCategoryLinkTable = new YearWeightCategoryLinkTable();
			
			CM.YearWeightCategoryLink cmYearWeightCategoryLink = new CM.YearWeightCategoryLink(); 
			
			yearWeightCategoryLinkTable.getTable().getKASModel().addColumn(new KASColumn(uic.YEAR_CATEGORY(), cmYearWeightCategoryLink.getYearCategory()));
			yearWeightCategoryLinkTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT_CATEGORY(), cmYearWeightCategoryLink.getWeightCategory()));
			
			yearWeightCategoryLinkTable.showRowCount(uic.WEIGHT_CATEGORIES_COUNT());
		}
		return yearWeightCategoryLinkTable;
	}

	public YearWeightSearchPanel getSearchPanel() {
		if (yearWeightSearchPanel == null){
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
			yearWeightSearchPanel = new YearWeightSearchPanel(uic.SEARCH_BY_CRITERIA(), searchAl, clearAl);
		}
		return yearWeightSearchPanel;
	}


	public IYearWeightCategoryLinkSearchResultModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IYearWeightCategoryLinkSearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public JButton getBtnSelect() {
		if (btnSelect == null) {
			
			btnSelect = new JButton(uic.SELECT_WEIGHT_CATEGORIES());
			btnSelect.setToolTipText(uic.SELECT_WEIGHT_CATEGORIES());
			btnSelect.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));

			btnSelect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().select();
				}
			});
			
			btnSelect.setEnabled(false);
		}
		return btnSelect;
	}
}