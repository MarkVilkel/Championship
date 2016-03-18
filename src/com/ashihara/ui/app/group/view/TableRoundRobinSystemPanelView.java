/*
 * Copyright 2011 Dukascopy Bank SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ashihara.ui.app.group.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.group.model.ITableRoundRobinSystemModel;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterTable;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterTableCellEditor;
import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.renderer.KASDefaultRenderer;
import com.ashihara.ui.core.renderer.KASSmallHeaderRenderer;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.ApplicationManager;

/**
 * @author Mark Vilkel
 */
public class TableRoundRobinSystemPanelView extends KASPanel implements UIView<ITableRoundRobinSystemModel<?>>, UIStatePerformer<FightResult>, LinkClickingListener<FightResult> {

	private static final long serialVersionUID = 1L;
	
	private ITableRoundRobinSystemModel<?> modelUI;
	
	private ChampionshipFighterTable championshipFighterTable;
	private FightResultTable fightResultTable;
	private KASComboBox cmbFighter;
	private JSplitPane splitPane;
	
	public TableRoundRobinSystemPanelView(ITableRoundRobinSystemModel<?> modelUI) {
		this.modelUI = modelUI;
		
		init();
	}
	

	private void init() {
		add(getSplitPane(), BorderLayout.CENTER);
	}
	
	public class FightResultTable extends SimpleTablePanel<FightResult> {
		private static final long serialVersionUID = 1L;

		public FightResultTable() {
			super(FightResult.class);
		}
	}

	@Override
	public ITableRoundRobinSystemModel<?> getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(ITableRoundRobinSystemModel<?> modelUI) {
		this.modelUI = modelUI;
	}

	@Override
	public void performUIState(FightResult param) {
		// TODO Auto-generated method stub
		
	}


	public ChampionshipFighterTable getChampionshipFighterTable() {
		if (championshipFighterTable == null) {
			championshipFighterTable = new ChampionshipFighterTable(getCmbFighter());
		}
		
		return championshipFighterTable;
	}


	public FightResultTable getFightResultTable() {
		if (fightResultTable == null) {
			fightResultTable = new FightResultTable();
			
			fightResultTable.getTable().addLinkClickingListener(this);
			
			CM.FightResult cmFightResult = new CM.FightResult(); 
			
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_FIGHTER(), cmFightResult.getFirstFighter().getChampionshipFighter(), new ColoredCellRenderer(UIUtils.LIGHT_RED)));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.PREVIOUS_ROUNDS_I(), cmFightResult.getPreviousRoundFightResult(), new PreviousRoundsCellRenderer(true)));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_CATEGORY(), cmFightResult.getFirstFighterFirstCategoryWarnings()));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_CATEGORY(), cmFightResult.getFirstFighterSecondCategoryWarnings()));
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getFirstFighterPoints()));
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getFirstFighterPointsForWin()));
			
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_FIGHTER(), cmFightResult.getSecondFighter().getChampionshipFighter(), new ColoredCellRenderer(UIUtils.LIGHT_BLUE)));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.PREVIOUS_ROUNDS_II(), cmFightResult.getPreviousRoundFightResult(), new PreviousRoundsCellRenderer(false)));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_CATEGORY(), cmFightResult.getSecondFighterFirstCategoryWarnings()));
//			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_CATEGORY(), cmFightResult.getSecondFighterSecondCategoryWarnings()));
			
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFightResult.getSecondFighterPoints()));
			fightResultTable.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFightResult.getSecondFighterPointsForWin()));
			
			fightResultTable.getTable().getKASModel().addColumn(KASColumn.createFakeLinkColumn(uic.NEXT_FIGHT(), uic.NEXT()));
			fightResultTable.getTable().getKASModel().addColumn(KASColumn.createFakeLinkColumn(uic.ACTION(), uic.FIGHT()));
			
			fightResultTable.getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());

			fightResultTable.showRowCount(uic.FIGHTS_COUNT());
			
			
			for (int i = 0; i < fightResultTable.getTable().getColumnCount(); i++) {
				TableColumn c = fightResultTable.getTable().getColumnModel().getColumn(i);
				if (i == 1 || i == 2 || i == 4 || i == 5) {
					c.setMaxWidth(70); 
				}
			}

		}
		return fightResultTable;
	}
	
	private class ColoredCellRenderer extends KASDefaultRenderer {
		private static final long serialVersionUID = 1L;
		
		private final Color color;
		
		public ColoredCellRenderer(Color color) {
			this.color = color;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			JComponent c = (JComponent)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			c.setBackground(color);
			return c;
		}		
	}
	
	private class PreviousRoundsCellRenderer extends KASDefaultRenderer {
		private static final long serialVersionUID = 1L;
		private final boolean first;
		
		public PreviousRoundsCellRenderer(boolean first) {
			this.first = first;
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			String str = "";
			if (value instanceof FightResult) {
				FightResult fr = (FightResult)value;
				
				while (fr.getPreviousRoundFightResult() != null) {
					fr = fr.getPreviousRoundFightResult();
				}
				
				int round = 0;
				while (fr.getNextRoundFightResult() != null) {
					round ++;
					
					str += "Round = " + round;
					
					if (first) {
						str += ", 1 cat = " + fr.getFirstFighterFirstCategoryWarnings();
						str += ", 2 cat = " + fr.getFirstFighterSecondCategoryWarnings();
						str += ", pts = " + fr.getFirstFighterPoints() + ";";
					}
					else {
						str += ", 1 cat = " + fr.getSecondFighterFirstCategoryWarnings();
						str += ", 2 cat = " + fr.getSecondFighterSecondCategoryWarnings();
						str += ", pts = " + fr.getSecondFighterPoints() + "; ";
					}
					
					fr = fr.getNextRoundFightResult();
				}
			}
			return super.getTableCellRendererComponent(table, str, isSelected, hasFocus, row, column);
		}
	}

	@Override
	public void linkClicked(FightResult value, String columnId) {
		getModelUI().linkClicked(value, columnId);
	}


	public KASComboBox getCmbFighter() {
		if (cmbFighter == null) {
			cmbFighter = new KASComboBox();
		}
		return cmbFighter;
	}
	

	public JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			splitPane.setTopComponent(getChampionshipFighterTable());
			splitPane.setBottomComponent(getFightResultTable());
			
			splitPane.setOneTouchExpandable(true);
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					splitPane.setDividerLocation((int)ApplicationManager.getInstance().getMainFrame().getSize().getHeight() / 3);
				}
			});

		}
		return splitPane;
	}

}
