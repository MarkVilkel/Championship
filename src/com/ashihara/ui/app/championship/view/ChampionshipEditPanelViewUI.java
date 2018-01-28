/**
 * The file ChampionshipEditPanelViewUI.java was created on 2010.5.4 at 22:17:40
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.enums.CM;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.championship.model.IChampionshipEditModelUI;
import com.ashihara.ui.app.fighter.view.FighterSearchPanel;
import com.ashihara.ui.app.fighter.view.GenderRenderer;
import com.ashihara.ui.app.yearWeight.view.YearWeightSearchPanel;
import com.ashihara.ui.core.component.KASLinkLabel;
import com.ashihara.ui.core.component.date.AKDateChooser;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.core.renderer.KASDefaultRenderer;
import com.ashihara.ui.core.renderer.KASSmallHeaderRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ChampionshipEditPanelViewUI extends KASPanel implements UIView<IChampionshipEditModelUI> {

	private static final long serialVersionUID = 1L;
	
	private ChampionshipFighterTable championshipFighterTable;
	private GroupTable groupTable;
	
	private KASPanel groupTablePanel;
	private KASPanel championshipFighterTablePanel;
	
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	
	private KASTextField txtName;
	private AKDateChooser dateChooser;
	private KASPanel champioshipDetailsPanel;
	private JSplitPane fightersGroupsSplitPane;
	
	private YearWeightSearchPanel yearWeightSearchPanel;
	private FighterSearchPanel fighterSearchPanel;
	
	private IChampionshipEditModelUI modelUI;
	
	private KASLinkLabel btnGroupPlaceReport, btnEachFighterReport;
	
	private JButton btnCheckFighters;
	
	private JButton btnExportCompletedGroups;
	private JButton btnExportGroups;
	private JButton btnImportGroups;
	private JButton btnExportEachGroupToExcel;
	

	public ChampionshipEditPanelViewUI(IChampionshipEditModelUI modelUI) {
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
		add(getFightersGroupsSplitPane(), BorderLayout.CENTER);		
		add(getChampioshipDetailsPanel(), BorderLayout.NORTH);
	}

	@Override
	public IChampionshipEditModelUI getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IChampionshipEditModelUI modelUI) {
		this.modelUI = modelUI;
	}
	
	
	
	
	
	public class ChampionshipFighterTable extends AddDeleteButtonsTablePanel<ChampionshipFighter> implements LinkClickingListener<ChampionshipFighter> {
		private static final long serialVersionUID = 1L;

		public ChampionshipFighterTable() {
			super(ChampionshipFighter.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());
			getTable().addLinkClickingListener(this);
			
			getHeaderRightPanel().add(getBtnCheckFighters(), 0);
		}
		
		@Override
		public void linkClicked(ChampionshipFighter value, String columnId) {
			getModelUI().linkClickedOnChampionshipFighter(value, columnId);
		}
		
		public void onAdd(Integer countToAdd) {
			getModelUI().addFighters();
		}
		
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedFighters();
		}
	}

	public class GroupTable extends AddDeleteButtonsTablePanel<FightingGroup> implements LinkClickingListener<FightingGroup>{
		private static final long serialVersionUID = 1L;
		
		private JButton btnAddWeightCategory;

		public GroupTable() {
			super(FightingGroup.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());
			
			getButtonsPanel().getBtnAdd().setText(uic.ADD_YEAR_CATEGORY());
			getButtonsPanel().getBtnAdd().setToolTipText(uic.ADD_YEAR_CATEGORY());
			
			getButtonsPanel().add(getBtnAddWeightCategory());
			
			
			getButtonsPanel().getBtnAdd().setPreferredSize(new Dimension(110, getButtonsPanel().getBtnAdd().getPreferredSize().height));
			getBtnAddWeightCategory().setPreferredSize(new Dimension(110, getBtnAddWeightCategory().getPreferredSize().height));
			
			
			getTable().addLinkClickingListener(this);

		}
		
		public void onAdd(Integer countToAdd) {
			getModelUI().addGroupsByYearCategory();
		}
		
		public void onDelete() {
			super.onDelete();
			getModelUI().deleteSelectedGroups();
		}

		public JButton getBtnAddWeightCategory() {
			if (btnAddWeightCategory == null) {
				btnAddWeightCategory = UIFactory.createPlusButton();
				btnAddWeightCategory.setText(uic.ADD_GROUP_BY_WEIGHT_CATEGORY());
				btnAddWeightCategory.setToolTipText(uic.ADD_GROUP_BY_WEIGHT_CATEGORY());
				
				btnAddWeightCategory.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						getModelUI().addGroupsByWeightCategory();
					}
				});
			}
			return btnAddWeightCategory;
		}

		@Override
		public void linkClicked(FightingGroup value, String columnId) {
			getModelUI().linkClickedOnGroup(value);
		}
		
		public void setEnabled(boolean flag) {
			super.setEnabled(flag);
			updateExportBtn();
		}

	}



	public ChampionshipFighterTable getChampionshipFighterTable() {
		if (championshipFighterTable == null) {
			championshipFighterTable = new ChampionshipFighterTable();
			
			CM.ChampionshipFighter cmChampionshipFighter = new CM.ChampionshipFighter();

			championshipFighterTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.NAME(), cmChampionshipFighter.getFighter().getName()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.SURNAME(), cmChampionshipFighter.getFighter().getSurname()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.GENDER(), cmChampionshipFighter.getFighter().getGender(), new GenderRenderer()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.COUNTRY(), cmChampionshipFighter.getFighter().getCountry()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.AGE(), cmChampionshipFighter.getFighter().getFullYearsOld()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT(), cmChampionshipFighter.getFighter().getWeight()));
			championshipFighterTable.getTable().getKASModel().addColumn(new KASColumn(uic.NUMBER(), cmChampionshipFighter.getNumber(), true));
			
			championshipFighterTable.showRowCount(uic.FIGHTERS_COUNT());
			
		}
		
		return championshipFighterTable;
	}

	public GroupTable getGroupTable() {
		if (groupTable == null) {
			groupTable = new GroupTable();

			CM.FightingGroup cmGroup = new CM.FightingGroup();
			
			groupTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.TITLE(), cmGroup.getName()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.TYPE(), cmGroup.getType(), new GroupTypeRenderer()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.GENDER(), cmGroup.getGender(), new GenderRenderer()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.YEAR_CATEGORY(), cmGroup.getYearWeightCategoryLink().getYearCategory()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT_CATEGORY(), cmGroup.getYearWeightCategoryLink().getWeightCategory()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.TATAMI(), cmGroup.getTatami()));
			
			groupTable.showRowCount(uic.GROUPS_COUNT());
			
			groupTable.getHeaderRightPanel().add(getBtnExportEachGroupToExcel());
			groupTable.getHeaderRightPanel().add(Box.createHorizontalStrut(20), 0);
			groupTable.getHeaderRightPanel().add(getBtnExportCompletedGroups(), 0);
			groupTable.getHeaderRightPanel().add(getBtnExportGroups(), 0);
			groupTable.getHeaderRightPanel().add(getBtnImportGroups(), 0);
			
			groupTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					updateExportBtn();
				}
			});
		}
		
		return groupTable;
	}
	
	private void updateExportBtn() {
		boolean isEnabled = groupTable.getTable().getSelectedRowCount() != 0;
		getBtnExportGroups().setEnabled(isEnabled);
	}
	
	public class GroupTypeRenderer extends KASDefaultRenderer {
		private static final long serialVersionUID = 1L;
		
		private SC.GROUP_TYPE groupType = new SC.GROUP_TYPE(); 
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (value != null) {
				value = groupType.getUICaption(value.toString(), uic);
			}
			
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			return comp;
		}
	}
	

	public SaveCancelResetButtonPanel getSaveCancelResetButtonPanel() {
		if (saveCancelResetButtonPanel == null) {
			ActionListener saveAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().save();
				}
			};

			ActionListener resetAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().fullReload();
				}
			};
			
			ActionListener cancelAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			
			saveCancelResetButtonPanel = new SaveCancelResetButtonPanel(saveAl, cancelAl, resetAl);
		}
		
		return saveCancelResetButtonPanel;
	}
	
	
	public KASPanel getChampioshipDetailsPanel() {
		if (champioshipDetailsPanel == null){
			champioshipDetailsPanel = new KASPanel();
			FormLayout fl = new FormLayout(
					"right:90dlu,4dlu, pref, 20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, champioshipDetailsPanel);
			
			builder.addSeparator("  " + uic.CHAMPIONSHIP_INFO(), cc.xyw(1, 1, 7));
			
			builder.addLabel(uic.TITLE()+": ", cc.xy(1, 3));
			builder.add(getTxtName(), cc.xy(3, 3));
			
			builder.add(getBtnGroupPlaceReport(), cc.xy(5, 3));
			builder.add(getBtnEachFighterReport(), cc.xy(5, 5));
			
			builder.addLabel(uic.BEGINNING_DATE()+": ", cc.xy(1, 5));
			builder.add(getDateChooser(), cc.xy(3, 5));
		}
		
		return champioshipDetailsPanel;
	}

	public KASTextField getTxtName() {
		if (txtName == null) {
			txtName = UIFactory.createKASTextField();
			txtName.setMaxSymbolsCount(255);
			txtName.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtName;
	}

	public AKDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new AKDateChooser();
		}
		return dateChooser;
	}

	public JSplitPane getFightersGroupsSplitPane() {
		if (fightersGroupsSplitPane == null) {
			fightersGroupsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			fightersGroupsSplitPane.setOneTouchExpandable(true);
			fightersGroupsSplitPane.setTopComponent(getChampionshipFighterTablePanel());
			fightersGroupsSplitPane.setBottomComponent(getGroupTablePanel());
			fightersGroupsSplitPane.setDividerLocation(ApplicationManager.getInstance().getMainFrame().getSize().width / 2);
		}
		return fightersGroupsSplitPane;
	}

	public YearWeightSearchPanel getYearWeightSearchPanel() {
		if (yearWeightSearchPanel == null){
			ActionListener searchAl =  new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().searchGroupsByYearWeight();
				}
			};
			ActionListener clearAl =  new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().clearYearWeightCriteria();
				}
			};
			yearWeightSearchPanel = new YearWeightSearchPanel(uic.FILTER_GROUPS_BY_CRITERIA(), searchAl, clearAl);
		}
		return yearWeightSearchPanel;
	}

	public KASPanel getGroupTablePanel() {
		if (groupTablePanel == null) {
			groupTablePanel = new KASPanel();
			
			groupTablePanel.add(getYearWeightSearchPanel(), BorderLayout.NORTH);
			groupTablePanel.add(getGroupTable(), BorderLayout.CENTER);
		}
		return groupTablePanel;
	}

	public KASPanel getChampionshipFighterTablePanel() {
		if (championshipFighterTablePanel == null) {
			championshipFighterTablePanel = new KASPanel();
			championshipFighterTablePanel.add(getFighterSearchPanel(), BorderLayout.NORTH);
			championshipFighterTablePanel.add(getChampionshipFighterTable(), BorderLayout.CENTER);
		}
		return championshipFighterTablePanel;
	}

	public FighterSearchPanel getFighterSearchPanel() {
		if (fighterSearchPanel == null) {
			fighterSearchPanel = new FighterSearchPanel(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().filterFighters();
				}
			}, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().clearFightersFilter();
				}
			});
		}
		return fighterSearchPanel;
	}

	public KASLinkLabel getBtnGroupPlaceReport() {
		if (btnGroupPlaceReport == null) {
			btnGroupPlaceReport = new KASLinkLabel(uic.GROUP_PLACE_REPORT());
			
			btnGroupPlaceReport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().showGroupPlaceReport();
				}
			});
		}
		return btnGroupPlaceReport;
	}

	public KASLinkLabel getBtnEachFighterReport() {
		if (btnEachFighterReport == null) {
			btnEachFighterReport = new KASLinkLabel(uic.EACH_FIGHT_REPORT());
			
			btnEachFighterReport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().showEachFighterReport();
				}
			});
		}
		return btnEachFighterReport;
	}

	private JButton getBtnCheckFighters() {
		if (btnCheckFighters == null) {
			btnCheckFighters = new JButton(uic.CHECK_FIGHTERS());
			
			btnCheckFighters.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().checkFighters();
				}
			});
		}
		return btnCheckFighters;
	}

	private JButton getBtnExportGroups() {
		if (btnExportGroups == null) {
			btnExportGroups = new JButton(uic.EXPORT());
			
			btnExportGroups.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().exportGroups();
				}
			});
			
			btnExportGroups.setEnabled(false);
		}
		return btnExportGroups;
	}

	private JButton getBtnImportGroups() {
		if (btnImportGroups == null) {
			btnImportGroups = new JButton(uic.IMPORT());
			
			btnImportGroups.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().importGroups();
				}
			});
		}
		return btnImportGroups;
	}

	private JButton getBtnExportCompletedGroups() {
		if (btnExportCompletedGroups == null) {
			btnExportCompletedGroups = new JButton(uic.EXPORT_FINISHED_GROUPS());
			
			btnExportCompletedGroups.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().exportFinishedGroups();
				}
			});
		}
		return btnExportCompletedGroups;
	}

	private JButton getBtnExportEachGroupToExcel() {
		if (btnExportEachGroupToExcel == null) {
			btnExportEachGroupToExcel = UIFactory.createExportButton();
			btnExportEachGroupToExcel.addActionListener((e) -> getModelUI().exportAllGroupsToExcel());
		}
		return btnExportEachGroupToExcel;
	}

}
