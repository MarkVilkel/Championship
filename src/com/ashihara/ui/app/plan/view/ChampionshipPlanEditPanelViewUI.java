/**
 * The file ChampionshipEditPanelViewUI.java was created on 2010.5.4 at 22:17:40
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;

import com.ashihara.datamanagement.pojo.ChampionshipPlan;
import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.group.view.TableRoundRobinSystemPanelView.ColoredCellRenderer;
import com.ashihara.ui.app.plan.model.IChampionshipPlanEditModelUI;
import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.TableToExcelExporter;
import com.ashihara.ui.tools.UIFactory;

public class ChampionshipPlanEditPanelViewUI extends KASPanel implements UIView<IChampionshipPlanEditModelUI> {

	private static final long serialVersionUID = 1L;
	
	private FightsTable fightsTable;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	private IChampionshipPlanEditModelUI modelUI;
	
	private JTabbedPane mainTabbedPane;
	private SimpleTablePanel<FighterPlace> fightResultsTable;
	private KASPanel tournamentResultPanel, buttonsPanel;
	private final RulesManager rulesManager;
	private JButton btnStartFight;
	private JCheckBox showNext, finalsAtTheEnd;
	private final ChampionshipPlan plan;
	
	public ChampionshipPlanEditPanelViewUI(ChampionshipPlan plan, IChampionshipPlanEditModelUI modelUI, RulesManager rulesManager) {
		this.plan = plan;
		this.modelUI = modelUI;
		this.rulesManager = rulesManager;
		
		init();
	}

	private void init() {
		add(getButtonPanels(), BorderLayout.SOUTH);
		add(getMainTabbedPane(), BorderLayout.CENTER);		
	}

	@Override
	public IChampionshipPlanEditModelUI getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IChampionshipPlanEditModelUI modelUI) {
		this.modelUI = modelUI;
	}
	
	public class FightsTable extends AddDeleteButtonsTablePanel<FightResultForPlan> {
		private static final long serialVersionUID = 1L;
		
		public FightsTable() {
			super(FightResultForPlan.class);
			getButtonsPanel().getTxtCountToAdd().setVisible(false);
			getTable().setSingleSelection();
			getHeaderCenterPanel().add(getFinalsAtTheEnd());
		}
		
		@Override
		public void onAdd(Integer countToAdd) {
			getModelUI().onAddGroups();
		}
		
		@Override
		public void onDelete() {
			getModelUI().deleteSelectedGroups();
		}
		
		@Override
		public void selectedValueChanged(ListSelectionEvent event) {
			List<FightResultForPlan> selectedObjects = getTable().getSelectedObjects();
			boolean selected = !selectedObjects.isEmpty();
			getButtonsPanel().getBtnDelete().setEnabled(selected);
			perfromStartFightEnabled();
		}
		
		@Override
		public JButton getBtnExportToExcel() {
			if (btnExportToExcel == null){
				btnExportToExcel = UIFactory.createExcelButton();
				btnExportToExcel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							String caption = "Plan for " + plan.getChampionship().getName();
							TableToExcelExporter.drawPlanToExcel(getTable().getKASModel().getDataRows(), caption, uic);
						} catch (IOException e) {
							MessageHelper.handleException(null, e);
						}
					}
				});
			}
			return btnExportToExcel;
		}

	}

	private KASPanel getButtonPanels() {
		if (buttonsPanel == null) {
			buttonsPanel = new KASPanel(new BorderLayout(1, 1));
//			KASPanel panel = new KASPanel(new FlowLayout(FlowLayout.LEFT));
//			panel.add(getFinalsAtTheEnd());
//			buttonsPanel.add(panel, BorderLayout.WEST);
			buttonsPanel.add(getSaveCancelResetButtonPanel(), BorderLayout.EAST);
		}
		return buttonsPanel;
	}

	public SaveCancelResetButtonPanel getSaveCancelResetButtonPanel() {
		if (saveCancelResetButtonPanel == null) {
			ActionListener saveAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				}
			};

			ActionListener resetAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reloadSelectedComponent();
				}
			};
			
			ActionListener cancelAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			
			saveCancelResetButtonPanel = new SaveCancelResetButtonPanel(
					saveAl,
					cancelAl,
					resetAl
			);
			saveCancelResetButtonPanel.getBtnSave().setVisible(false);
			saveCancelResetButtonPanel.add(getBtnStartFight(), 0);
			saveCancelResetButtonPanel.add(getShowNext(), 0);
		}
		
		return saveCancelResetButtonPanel;
	}

	public FightsTable getFightsTable() {
		if (fightsTable == null) {
			fightsTable = new FightsTable();
			
			CM.FightResultForPlan cmFightResult = new CM.FightResultForPlan(); 
			
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.NR(), cmFightResult.getNumberInPlan()));
			fightsTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.GROUP(), cmFightResult.getFightingGroup()));
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_FIGHTER(), cmFightResult.getFirstFighter().getChampionshipFighter(), new ColoredCellRenderer(UIUtils.LIGHT_RED)));
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getFirstFighterPoints()));
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getFirstFighterPointsForWin()));
			
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_FIGHTER(), cmFightResult.getSecondFighter().getChampionshipFighter(), new ColoredCellRenderer(UIUtils.LIGHT_BLUE)));
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getSecondFighterPoints()));
			fightsTable.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getSecondFighterPointsForWin()));
			
			fightsTable.showRowCount(uic.FIGHTS_COUNT());
			fightsTable.getTable().addLinkClickingListener((value, columnId) -> getModelUI().linkClicked(value, columnId));
			
			
			for (int i = 0; i < fightsTable.getTable().getColumnCount(); i++) {
				TableColumn c = fightsTable.getTable().getColumnModel().getColumn(i);
				if (i == 0) {
					c.setMaxWidth(90);
					c.setPreferredWidth(90);
				} else if (i == 3 || i == 6) {
					c.setMaxWidth(70); 
				} else if (i == 4 || i == 7) {
					c.setMaxWidth(100);
					c.setPreferredWidth(100);
				}
			}

		}
		return fightsTable;
	}

	public SimpleTablePanel<FighterPlace> getFightResultsTable() {
		if (fightResultsTable == null) {
			fightResultsTable = new SimpleTablePanel<>(FighterPlace.class);
			
			
			CM.FighterPlace cmFighterPlace = new CM.FighterPlace(); 
			
			fightResultsTable.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.GROUP(), cmFighterPlace.getFightingGroup()));
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.FIGHTER(), cmFighterPlace.getGCFighter().getChampionshipFighter()));
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.COUNTRY(), cmFighterPlace.getGCFighter().getChampionshipFighter().getFighter().getCountry()));
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getFirstPenaltyCategoryCaption(), cmFighterPlace.getFirstCategoryWarnings()));
			if (rulesManager.hasSecondPenaltyCategory()) {
				fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getSecondPenaltyCategoryCaption(), cmFighterPlace.getSecondCategoryWarnings()));
			}
			if (rulesManager.canWinByJudgeDecision()) {
			    fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.BY_JUDGE_DECISION(), cmFighterPlace.getWonByJudgeDecisionCount()));
			}
			if (rulesManager.canWinByTKO()) {
			    fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.BY_TKO(), cmFighterPlace.getWonByTKOCount()));
			}
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFighterPlace.getPoints()));
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFighterPlace.getPointsForWin()));
			fightResultsTable.getTable().getKASModel().addColumn(new KASColumn(uic.PLACE(), cmFighterPlace.getPlace()));
			
			fightResultsTable.getTable().addLinkClickingListener((value, columnId) -> getModelUI().linkClicked(value, columnId));

		}
		return fightResultsTable;
	}


	private JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();

			mainTabbedPane.addTab(uic.FIGHTS(), getFightsTable());
			mainTabbedPane.addTab(uic.RESULT_TABLE(), getTournamentResultPanel());
			
			mainTabbedPane.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					reloadSelectedComponent();
				}
			});
		}
		return mainTabbedPane;
	}

	private void reloadSelectedComponent() {
		if (getMainTabbedPane().getSelectedComponent() == getTournamentResultPanel()) {
			getModelUI().reloadTournamentResults();
		}
		else if (getMainTabbedPane().getSelectedComponent() == getFightsTable()) {
			getModelUI().reloadFights();
		}
	}

	private KASPanel getTournamentResultPanel() {
		if (tournamentResultPanel == null) {
			tournamentResultPanel = new KASPanel();
			
			tournamentResultPanel.add(getFightResultsTable(), BorderLayout.CENTER);
		}
		return tournamentResultPanel;
	}

	public JButton getBtnStartFight() {
		if (btnStartFight == null) {
			btnStartFight = UIFactory.createSmallButon(100);
			btnStartFight.addActionListener((e) -> getModelUI().startFight());
			perfromStartFightEnabled();
		}
		return btnStartFight;
	}

	private void perfromStartFightEnabled() {
		FightResultForPlan fr = getFightsTable().getTable().getSingleSelectedRow();
		boolean enabled = fr != null && fr.getFirstFighter() != null && fr.getSecondFighter() != null;
		getBtnStartFight().setEnabled(enabled);
		getShowNext().setEnabled(enabled);
		
		if (fr != null) {
			getBtnStartFight().setText(fr.getNumberInPlan() + ". " + uic.FIGHT());
		} else {
			getBtnStartFight().setText(uic.FIGHT());
		}
	}

	public JCheckBox getShowNext() {
		if (showNext == null) {
			showNext = new JCheckBox(uic.SHOW_NEXT_FIGHTS());
			showNext.setSelected(true);
		}
		return showNext;
	}

	public JCheckBox getFinalsAtTheEnd() {
		if (finalsAtTheEnd == null) {
			finalsAtTheEnd = new JCheckBox(uic.FINALS_AT_THE_END());
			finalsAtTheEnd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().save();
				}
			});
		}
		return finalsAtTheEnd;
	}

}
