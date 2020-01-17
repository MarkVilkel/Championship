/**
 * The file ChampionshipResultPanelViewUI.java was created on 2011.17.10 at 22:24:29
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JTable;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.championship.model.IChampionshipEachFightReportModelUI;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.renderer.CheckBoxCellRendererForIntegerValue;
import com.ashihara.ui.core.renderer.CheckBoxTableRenderer;
import com.ashihara.ui.core.renderer.KASDefaultRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ChampionshipEachFightReportViewUI extends KASPanel implements UIView<IChampionshipEachFightReportModelUI<?>>, LinkClickingListener<FightResultReport>{

	private static final long serialVersionUID = 1L;
	
	private IChampionshipEachFightReportModelUI<?> modelUI;
	
	private ChampionshipFightResultReportTable fightResultPanel;
	private SearchClearButtonPanel searchClearButtonPanel;
	private KASPanel searchPanelDetails;
	private KASComboBox cmbGroups, cmbFirstFighter, cmbSecondFighter;
	private final RulesManager rulesManager;
	
	public ChampionshipEachFightReportViewUI(
			IChampionshipEachFightReportModelUI<?> modelUI,
			RulesManager rulesManager
	) {
		this.modelUI = modelUI;
		this.rulesManager = rulesManager;
		
		init();
	}

	private void init() {
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getFightResultPanel(), BorderLayout.CENTER);
	}

	@Override
	public IChampionshipEachFightReportModelUI<?> getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IChampionshipEachFightReportModelUI<?> modelUI) {
		this.modelUI = modelUI;
	}
	
	public class ChampionshipFightResultReportTable extends SimpleTablePanel<FightResultReport> {
		private static final long serialVersionUID = 1L;

		public ChampionshipFightResultReportTable() {
			super(FightResultReport.class);
		}
	}

	public ChampionshipFightResultReportTable getFightResultPanel() {
		if (fightResultPanel == null) {
			fightResultPanel = new ChampionshipFightResultReportTable();
			
			
			fightResultPanel.getTable().addLinkClickingListener(this);
			
			CM.FightResultReport cmFightResult = new CM.FightResultReport(); 
			
			fightResultPanel.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.GROUP(), cmFightResult.getLastRound().getFirstFighter().getFightingGroup()));
			
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_FIGHTER(), cmFightResult.getLastRound().getFirstFighter().getChampionshipFighter()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_COUNTRY(), cmFightResult.getLastRound().getFirstFighter().getChampionshipFighter().getFighter().getCountry()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.PREVIOUS_ROUNDS_I(), cmFightResult.getPreviousRounds(), new PreviousRoundsCellRenderer(true, rulesManager)));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getFirstPenaltyCategoryCaption(), cmFightResult.getLastRound().getFirstFighterFirstCategoryWarnings()));
			if (rulesManager.hasSecondPenaltyCategory()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getSecondPenaltyCategoryCaption(), cmFightResult.getLastRound().getFirstFighterSecondCategoryWarnings()));
			}
			if (rulesManager.canWinByJudgeDecision()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.BY_JUDGE_DECISION(), cmFightResult.getLastRound().getFirstFighterWinByJudgeDecision(), new CheckBoxTableRenderer()));
			}
			if (rulesManager.canWinByTKO()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.BY_TKO(), cmFightResult.getLastRound().getFirstFighterWinByTKO(), new CheckBoxTableRenderer()));
			}
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getLastRound().getFirstFighterPoints()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getLastRound().getFirstFighterPointsForWin()));
			
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_FIGHTER(), cmFightResult.getLastRound().getSecondFighter().getChampionshipFighter()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_COUNTRY(), cmFightResult.getLastRound().getSecondFighter().getChampionshipFighter().getFighter().getCountry()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.PREVIOUS_ROUNDS_II(), cmFightResult.getPreviousRounds(), new PreviousRoundsCellRenderer(false, rulesManager)));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getFirstPenaltyCategoryCaption(), cmFightResult.getLastRound().getSecondFighterFirstCategoryWarnings()));
			if (rulesManager.hasSecondPenaltyCategory()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(rulesManager.getSecondPenaltyCategoryCaption(), cmFightResult.getLastRound().getSecondFighterSecondCategoryWarnings()));
			}
			if (rulesManager.canWinByJudgeDecision()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.BY_JUDGE_DECISION(), cmFightResult.getLastRound().getSecondFighterWinByJudgeDecision(), new CheckBoxTableRenderer()));
			}
			if (rulesManager.canWinByJudgeDecision()) {
				fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.BY_TKO(), cmFightResult.getLastRound().getSecondFighterWinByTKO(), new CheckBoxTableRenderer()));
			}
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getLastRound().getSecondFighterPoints()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getLastRound().getSecondFighterPointsForWin()));
			
			fightResultPanel.showRowCount(uic.RESULTS_COUNT());
			
		}
		
		return fightResultPanel;
	}
	
	private class PreviousRoundsCellRenderer extends KASDefaultRenderer {
		private static final long serialVersionUID = 1L;
		private final boolean first;
		private final RulesManager rulesManager;
		
		public PreviousRoundsCellRenderer(boolean first, RulesManager rulesManager) {
			this.first = first;
			this.rulesManager = rulesManager;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			String str = "";
			if (value instanceof List<?>) {
				List<FightResult> results = (List<FightResult>)value;
				int round = 0;
				for (FightResult fr : results) {
					round ++;
					
					str += "Round = " + round;
					
					if (first) {
						if (rulesManager.hasSecondPenaltyCategory()) {
							str += ", 1 cat = " + fr.getFirstFighterFirstCategoryWarnings();
							str += ", 2 cat = " + fr.getFirstFighterSecondCategoryWarnings();
						} else { 
							str += ", penalty = " + fr.getFirstFighterFirstCategoryWarnings();
						}
							
						str += ", pts = " + fr.getFirstFighterPoints() + ";";
					}
					else {
						if (rulesManager.hasSecondPenaltyCategory()) {
							str += ", 1 cat = " + fr.getSecondFighterFirstCategoryWarnings();
							str += ", 2 cat = " + fr.getSecondFighterSecondCategoryWarnings();
						} else {
							str += ", penalty = " + fr.getSecondFighterFirstCategoryWarnings();
						}
						str += ", pts = " + fr.getSecondFighterPoints() + "; ";
					}
				}
			}
			return super.getTableCellRendererComponent(table, str, isSelected, hasFocus, row, column);
		}
	}

	private SearchClearButtonPanel getSearchClearButtonPanel() {
		if (searchClearButtonPanel == null) {
			searchClearButtonPanel = new SearchClearButtonPanel(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getModelUI().clear();
						}
					},
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getModelUI().filter();
						}
					}
			);
			
			searchClearButtonPanel.setDetailsPanel(getSearchPanelDetails(), true);
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

			builder.addLabel(uic.GROUP(), cc.xy(2, 3));
			builder.add(getCmbGroups(), cc.xy(2, 5));
			
			builder.addLabel(uic.FIRST_FIGHTER(), cc.xy(4, 3));
			builder.add(getCmbFirstFighter(), cc.xy(4, 5));
			
			builder.addLabel(uic.SECOND_FIGHTER(), cc.xy(6, 3));
			builder.add(getCmbSecondFighter(), cc.xy(6, 5));
		}
		
		return searchPanelDetails;
	}

	public KASComboBox getCmbGroups() {
		if (cmbGroups == null) {
			cmbGroups = UIFactory.createKasComboBox20(DEFAULT_CONTROLS_LENGTH);
			cmbGroups.addKeyListener(new EnterEscapeKeyAdapter(getSearchClearButtonPanel().getBtnSearch(), getSearchClearButtonPanel().getBtnClear()));
			
			cmbGroups.addSelectionItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					getModelUI().groupSelected();
				}
			});

		}
		return cmbGroups;
	}

	public KASComboBox getCmbFirstFighter() {
		if (cmbFirstFighter == null) {
			cmbFirstFighter = UIFactory.createKasComboBox20(DEFAULT_CONTROLS_LENGTH);
			cmbFirstFighter.addKeyListener(new EnterEscapeKeyAdapter(getSearchClearButtonPanel().getBtnSearch(), getSearchClearButtonPanel().getBtnClear()));
		}
		return cmbFirstFighter;
	}

	public KASComboBox getCmbSecondFighter() {
		if (cmbSecondFighter == null) {
			cmbSecondFighter = UIFactory.createKasComboBox20(DEFAULT_CONTROLS_LENGTH);
			cmbSecondFighter.addKeyListener(new EnterEscapeKeyAdapter(getSearchClearButtonPanel().getBtnSearch(), getSearchClearButtonPanel().getBtnClear()));
		}
		return cmbSecondFighter;
	}

	@Override
	public void linkClicked(FightResultReport value, String columnId) {
		getModelUI().linkClicked(value, columnId);
	}
	
}
