/**
 * The file GroupEditPanelView.java was created on 2010.11.4 at 12:50:02
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.group.model.IGroupEditModel;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class GroupEditPanelView extends KASPanel implements UIView<IGroupEditModel> {

	private static final long serialVersionUID = 1L;
	
	private IGroupEditModel modelUI;
	
	private KASTextField txtName;
	private JLabel lblTatami;
	private JLabel txtYearCategory, txtWeightCategory;
	private AutoCompleteComboBox<String> cmbType;
	private AutoCompleteComboBox<String> cmbGender;
	private KASPanel detailsPanel;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	private JButton btnStart;
	private JCheckBox checkInPlan;
	
	private KASPanel tournamentActionPanel;
	private KASPanel tournamentActionCenterPanel;
	
	private KASPanel tournamentTablePresentationPanel;
	private KASPanel tournamentTablePresentationCenterPanel;
	
	private KASPanel tournamentResultPanel;
	
	private JSplitPane mainSplitPane;
	
	
	private JTabbedPane mainTabbedPane;
	private SimpleTablePanel<FighterPlace> fightResultsTable;
	private final RulesManager rulesManager;
	
	public GroupEditPanelView(IGroupEditModel modelUI, RulesManager rulesManager) {
		this.rulesManager = rulesManager;
		setModelUI(modelUI);
		
		init();
	}
	
	private void init() {
//		add(getDetailsPanel(), BorderLayout.WEST);
		add(getMainSplitPane(), BorderLayout.CENTER);
	}

	@Override
	public IGroupEditModel getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IGroupEditModel modelUI) {
		this.modelUI = modelUI;
	}
	
	public KASPanel getDetailsPanel() {
		if (detailsPanel == null){
			detailsPanel = new KASPanel();
			FormLayout fl = new FormLayout(
					"right:70dlu, 4dlu, pref, 10dlu",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 10dlu");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, detailsPanel);
			
			builder.addSeparator("  " + uic.GROUP_INFO(), cc.xyw(1, 1, 4));
			
			builder.addLabel(uic.GROUP_NAME()+": ", cc.xy(1, 3));
			builder.add(getTxtName(), cc.xy(3, 3));
			
			builder.addLabel(uic.TYPE()+": ", cc.xy(1, 5));
			builder.add(getCmbType(), cc.xy(3, 5));
			
			builder.addLabel(uic.GENDER()+": ", cc.xy(1, 7));
			builder.add(getCmbGender(), cc.xy(3, 7));
			
			builder.addLabel(uic.TATAMI()+": ", cc.xy(1, 9));
			builder.add(getLblTatami(), cc.xy(3, 9));

			builder.addLabel(uic.YEAR_CATEGORY()+": ", cc.xy(1, 11));
			builder.add(getTxtYearCategory(), cc.xy(3, 11));

			builder.addLabel(uic.WEIGHT_CATEGORY()+": ", cc.xy(1, 13));
			builder.add(getTxtWeightCategory(), cc.xy(3, 13));
			
			builder.addLabel(uic.IN_PLAN()+": ", cc.xy(1, 15));
			builder.add(getCheckInPlan(), cc.xy(3, 15));
		}
		
		return detailsPanel;
	}


	public KASTextField getTxtName() {
		if (txtName == null) {
			txtName = UIFactory.createKASTextField();
		}
		return txtName;
	}

	public AutoCompleteComboBox<String> getCmbType() {
		if (cmbType == null) {
			cmbType = UIFactory.createAutoCompleteComboBox20(String.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbType;
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
					getModelUI().reset();
				}
			};
			ActionListener cancelAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			
			saveCancelResetButtonPanel = new SaveCancelResetButtonPanel(saveAl, cancelAl, resetAl, getBtnStart());
		}
		return saveCancelResetButtonPanel;
	}

	public JLabel getTxtYearCategory() {
		if (txtYearCategory == null) {
			txtYearCategory = new JLabel();
		}
		return txtYearCategory;
	}

	public JLabel getTxtWeightCategory() {
		if (txtWeightCategory == null) {
			txtWeightCategory = new JLabel();
		}
		return txtWeightCategory;
	}

	public JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = UIFactory.createSmallButon(130);
			btnStart.setText(uic.START_TOURNAMENT());
			
			btnStart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().start();
				}
			});
		}
		return btnStart;
	}

	private KASPanel getTournamentActionPanel() {
		if (tournamentActionPanel == null) {
			tournamentActionPanel = new KASPanel();
			
			tournamentActionPanel.add(getTournamentActionCenterPanel(), BorderLayout.CENTER);
			tournamentActionPanel.add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
		}
		return tournamentActionPanel;
	}

	private JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			
//			mainTabbedPane.addTab(uic.FIGHTS_TABLE(), getTournamentTablePresentationPanel());
			mainTabbedPane.addTab(uic.FIGHTS(), getTournamentActionPanel());
			mainTabbedPane.addTab(uic.RESULT_TABLE(), getTournamentResultPanel());
			
			mainTabbedPane.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					if (mainTabbedPane.getSelectedComponent() == getTournamentResultPanel()) {
						getModelUI().reloadTournamentResultTable();
					}
					else if (mainTabbedPane.getSelectedComponent() == getTournamentActionPanel()) {
						getModelUI().reset();
					}
				}
			});
		}
		return mainTabbedPane;
	}

	private KASPanel getTournamentResultPanel() {
		if (tournamentResultPanel == null) {
			tournamentResultPanel = new KASPanel();
			
			tournamentResultPanel.add(getFightResultsTable(), BorderLayout.CENTER);
		}
		return tournamentResultPanel;
	}

	public KASPanel getTournamentActionCenterPanel() {
		if (tournamentActionCenterPanel == null) {
			tournamentActionCenterPanel = new KASPanel();
		}
		return tournamentActionCenterPanel;
	}

	public SimpleTablePanel<FighterPlace> getFightResultsTable() {
		if (fightResultsTable == null) {
			fightResultsTable = new SimpleTablePanel<>(FighterPlace.class);
			
			
			CM.FighterPlace cmFighterPlace = new CM.FighterPlace(); 
			
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

		}
		return fightResultsTable;
	}

	public JLabel getLblTatami() {
		if (lblTatami == null) {
			lblTatami = new JLabel();
		}
		return lblTatami;
	}

	public AutoCompleteComboBox<String> getCmbGender() {
		if (cmbGender == null) {
			cmbGender = UIFactory.createAutoCompleteComboBox20(String.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbGender;
	}

	private KASPanel getTournamentTablePresentationPanel() {
		if (tournamentTablePresentationPanel == null) {
			tournamentTablePresentationPanel = new KASPanel();
			
			tournamentTablePresentationPanel.add(getTournamentTablePresentationCenterPanel(), BorderLayout.CENTER);
		}
		return tournamentTablePresentationPanel;
	}

	public KASPanel getTournamentTablePresentationCenterPanel() {
		if (tournamentTablePresentationCenterPanel == null) {
			tournamentTablePresentationCenterPanel = new KASPanel();
		}
		return tournamentTablePresentationCenterPanel;
	}

	private JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			
			mainSplitPane.setLeftComponent(getDetailsPanel());
			mainSplitPane.setRightComponent(getMainTabbedPane());
			
			mainSplitPane.setOneTouchExpandable(true);
		}
		return mainSplitPane;
	}

	public JCheckBox getCheckInPlan() {
		if (checkInPlan == null) {
			checkInPlan = new JCheckBox();
			checkInPlan.setEnabled(false);
		}
		return checkInPlan;
	}

}
