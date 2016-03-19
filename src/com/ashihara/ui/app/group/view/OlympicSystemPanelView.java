/**
 * The file OlympicSystemPanelView.java was created on 2010.19.4 at 22:20:52
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.ashihara.ui.app.group.model.IOlympicSystemModel;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterProvider;
import com.ashihara.ui.app.group.view.stuff.ChampionshipFighterTable;
import com.ashihara.ui.app.group.view.stuff.OlympicFighterTreeCellPanel;
import com.ashihara.ui.app.group.view.stuff.RowsPanel;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.tools.UIFactory;

public class OlympicSystemPanelView extends KASPanel implements UIView<IOlympicSystemModel> {
	
	private static final long serialVersionUID = 1L;
	
	private IOlympicSystemModel modelUI;
	private KASPanel detailsPanel, buttonsUpperPanel;
	private List<RowsPanel> columnPanels;
	private JScrollPane detailsPanelScroll;
	private JButton btnExportToExel;
	
	private ChampionshipFighterTable championshipFighterTable;
	private JSplitPane mainSplitPane;
	
	private int maxColumnsNumber;
	private int maxRowsNumber;
	private int rowHeight;
	private int rowsGap;
	
	private final ChampionshipFighterProvider championshipFighterProvider;
	
	public OlympicSystemPanelView(
			IOlympicSystemModel modelUI,
			ChampionshipFighterProvider championshipFighterProvider,
			int maxColumnsNumber,
			int maxRowsNumber,
			int rowHeight,
			int rowsGap
	) {
		this.championshipFighterProvider = championshipFighterProvider;
		this.maxColumnsNumber = maxColumnsNumber;
		this.maxRowsNumber = maxRowsNumber;
		this.rowHeight = rowHeight;
		this.rowsGap = rowsGap;
		
		setModelUI(modelUI);
		
		init();
	}
	
	
	private void init() {
		add(getMainSplitPane(), BorderLayout.CENTER);
	}

	@Override
	public IOlympicSystemModel getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IOlympicSystemModel modelUI) {
		this.modelUI = modelUI;
	}


	public JScrollPane getDetailsPanelScroll() {
		if (detailsPanelScroll == null) {
			detailsPanelScroll = UIFactory.createScrollPane(getDetailsPanel()); 
		}
		return detailsPanelScroll;
	}

	public KASPanel getDetailsPanel() {
		if (detailsPanel == null){
			detailsPanel = new KASPanel(new BorderLayout(0, 0));
			
			KASPanel subPanel = new KASPanel(new GridLayout(1, maxColumnsNumber, 0, 0));
			
			for (RowsPanel panel : getColumnPanels()) {
				subPanel.add(panel);
			}
			
			detailsPanel.add(subPanel, BorderLayout.CENTER);
			detailsPanel.add(getButtonsUpperPanel(), BorderLayout.NORTH);
			
		}
		return detailsPanel;
	}


	public void setComponentToCell(
			Component component,
			int column,
			int row
	) {
		validateCell(column, row);
		
		RowsPanel rowPanel = getColumnPanels().get(column);
		rowPanel.setRowComponent(component, row);
	}
	
	public void removeComponentFromCell(
			Component component,
			int column,
			int row
	) {
		validateCell(column, row);
		
		RowsPanel rowPanel = getColumnPanels().get(column);
		rowPanel.clearRow(row);
	}
	
	private void validateCell(
			int column,
			int row
	) {
		if (maxColumnsNumber <= column) {
			throw new IllegalArgumentException("column must be less than max columns number - column<" + column + "> levels<" + maxColumnsNumber + ">");
		}
		
		if (maxRowsNumber <= row) {
			throw new IllegalArgumentException("row must be less than max rows number - row<" + row + "> max<" + maxRowsNumber + ">");
		}
	}


	public void removeAllFighterPanels() {
		for (RowsPanel panel : getColumnPanels()) {
			panel.clearAllRows();
		}
	}

	public void removeFighterPanel(OlympicFighterTreeCellPanel panel) {
		for (RowsPanel p : getColumnPanels()) {
			p.removeRowComponent(panel);
		}
	}


	private List<RowsPanel> getColumnPanels() {
		if (columnPanels == null) {
			columnPanels = new ArrayList<RowsPanel>();
			
			int rowsNumber = maxRowsNumber;
			int height = rowHeight;
			
			for (int i = 0; i < maxColumnsNumber; i++) {
				RowsPanel panel = new RowsPanel(rowsNumber, height, rowsGap);
				columnPanels.add(panel);
				
				rowsNumber /= 2;
				height *= 2;
			}
		}
		return columnPanels;
	}
	
	@Override
	public void repaint() {
		super.repaint();
		if (columnPanels != null) {
			for (RowsPanel rowsPanel : getColumnPanels()) {
				rowsPanel.repaint();
			}
		}
	}


	public ChampionshipFighterTable getChampionshipFighterTable() {
		if (championshipFighterTable == null) {
			championshipFighterTable = new ChampionshipFighterTable(championshipFighterProvider);
		}
		return championshipFighterTable;
	}
	
	private JSplitPane getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			mainSplitPane.setTopComponent(getChampionshipFighterTable());
			mainSplitPane.setBottomComponent(getDetailsPanelScroll());
			mainSplitPane.setDividerLocation(200);
			mainSplitPane.setOneTouchExpandable(true);
		}
		return mainSplitPane;
	}
	
	public int getMaxColumnsNumber() {
		return maxColumnsNumber;
	}


	public void setMaxColumnsNumber(int maxColumnsNumber) {
		this.maxColumnsNumber = maxColumnsNumber;
	}


	public int getMaxRowsNumber() {
		return maxRowsNumber;
	}


	public void setMaxRowsNumber(int maxRowsNumber) {
		this.maxRowsNumber = maxRowsNumber;
	}


	public int getRowHeight() {
		return rowHeight;
	}


	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}


	public int getRowsGap() {
		return rowsGap;
	}


	public void setRowsGap(int rowsGap) {
		this.rowsGap = rowsGap;
	}


	public KASPanel getButtonsUpperPanel() {
		if (buttonsUpperPanel == null) {
			buttonsUpperPanel = new KASPanel(new FlowLayout(FlowLayout.LEFT));
			buttonsUpperPanel.add(getBtnExportToExel());
		}
		return buttonsUpperPanel;
	}


	public JButton getBtnExportToExel() {
		if (btnExportToExel == null) {
			btnExportToExel = UIFactory.createExcelButton();
			btnExportToExel.addActionListener((e) -> getModelUI().exportToExcel());
		}
		return btnExportToExel;
	}

	
}
