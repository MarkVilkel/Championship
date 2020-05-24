/**
 * The file FighterSearchResultPanelViewUI.java was created on 2008.30.1 at 23:22:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;

import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.fighter.model.IFighterSearchResultModelUI;
import com.ashihara.ui.core.component.KASFileChooser;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.renderer.DateCellRenderer;
import com.ashihara.ui.core.renderer.KASSmallHeaderRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.resources.ResourceHelper;

public class FighterSearchResultPanelViewUI extends KASPanel implements LinkClickingListener<Fighter>, UIView<IFighterSearchResultModelUI>{
	private static final long serialVersionUID = 1L;
	
	private FighterTable fighterTable;
	private KASPanel searchPanelDetails;
	private IFighterSearchResultModelUI modelUI;
	private JButton btnSelect; // for selection mode
	private FighterSearchPanel fighterSearchPanel;
	private final String mode;
	private JButton btnImport;
	
	public static final String SELECT_MODE = "SELECT_MODE";
	public static final String EDIT_MODE = "EDIT_MODE";

	public FighterSearchResultPanelViewUI(IFighterSearchResultModelUI modelUI, String mode){
		super();
		
		this.modelUI = modelUI;
		this.mode = mode;
		
		if (!SELECT_MODE.equals(mode) && !EDIT_MODE.equals(mode)) {
			throw new IllegalArgumentException("Illegal mode - " + mode);
		}
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		add(getFighterSearchPanel(), BorderLayout.NORTH);
		add(getFighterTable(), BorderLayout.CENTER);
	}
	
	public class FighterTable extends AddDeleteButtonsTablePanel<Fighter>{
		private static final long serialVersionUID = 1L;

		public FighterTable() {
			super(Fighter.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			
			if (SELECT_MODE.equals(getMode())) {
				getButtonsPanel().getBtnAdd().setVisible(false);
				getButtonsPanel().getBtnDelete().setVisible(false);
				
				getButtonsPanel().add(getBtnSelect());
				
				getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());
			}
		}
		
		@Override
		public void selectedValueChanged(ListSelectionEvent event) {
			super.selectedValueChanged(event);
			getBtnSelect().setEnabled(getTable().getSelectedRowCount() != 0);
		}

		@Override
		public void onAdd(Integer countToAdd) {
			getModelUI().onAddTableRows(countToAdd);
		}
		
		@Override
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedTableRows();
		}
	}

	public FighterTable getFighterTable() {
		if (fighterTable == null){
			fighterTable = new FighterTable();
			fighterTable.getTable().addLinkClickingListener(this);
			
			CM.Fighter cmFighter = new CM.Fighter(); 
			
			if (SELECT_MODE.equals(getMode())) {
				fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.NAME(), cmFighter.getName()));
			}
			else {
				fighterTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.NAME(), cmFighter.getName()));
			}
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.SURNAME(), cmFighter.getSurname()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT(), cmFighter.getWeight()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.GENDER(), cmFighter.getGender(), new GenderRenderer()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.COUNTRY(), cmFighter.getCountry()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.BIRTHDAY(), cmFighter.getBirthday(), new DateCellRenderer()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.AGE(), cmFighter.getFullYearsOld()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.KYU(), cmFighter.getKyu()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.DAN(), cmFighter.getDan()));
			fighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.SKILL(), cmFighter.getParticipanceInChampionshipsCount()));
			
			fighterTable.showRowCount(uic.FIGHTERS_COUNT());
			
			fighterTable.getHeaderRightPanel().add(getBtnImport());
		}
		return fighterTable;
	}
	
	@Override
	public void linkClicked(Fighter value, String columnId) {
		getModelUI().onLinkClicked(value, columnId);
	}


	@Override
	public IFighterSearchResultModelUI getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IFighterSearchResultModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public String getMode() {
		return mode;
	}

	public JButton getBtnSelect() {
		if (btnSelect == null) {
			
			btnSelect = new JButton(uic.SELECT_FIGHTERS());
			btnSelect.setToolTipText(uic.SELECT_FIGHTERS());
			btnSelect.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));

			btnSelect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().selectFighters();
				}
			});
			
			btnSelect.setEnabled(false);
		}
		return btnSelect;
	}

	public FighterSearchPanel getFighterSearchPanel() {
		if (fighterSearchPanel == null) {
			fighterSearchPanel = new FighterSearchPanel(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().search();
				}
			}, 
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().clear();
				}
			});
		}
		return fighterSearchPanel;
	}

	public JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton(uic.IMPORT());
			btnImport.setToolTipText(uic.IMPORT());
			btnImport.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));

			btnImport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					KASFileChooser chooser = KASFileChooser.createKASOpenFileChooser(); 
					int result = chooser.showOpenDialog(FighterSearchResultPanelViewUI.this);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						getModelUI().importFighters(file);
					}
				}
			});
			
			btnImport.setVisible(EDIT_MODE.equals(mode));
		}
		return btnImport;
	}
}