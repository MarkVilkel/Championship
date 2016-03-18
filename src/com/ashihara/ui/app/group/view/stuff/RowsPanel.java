/**
 * The file RowsPanel.java was created on 2012.14.4 at 10:47:09
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import com.ashihara.ui.core.panel.KASPanel;

public class RowsPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	
	private final int maxRowsCount;
	private final int rowHeight;
	private final int rowsGap;
	
	private List<KASPanel> rowPanels;
	private KASPanel detailsPanel;
	
	
	public RowsPanel(
			int maxRowsCount,
			int rowsHeight,
			int rowsGap
	) {
		this.maxRowsCount = maxRowsCount;
		this.rowHeight = rowsHeight;
		this.rowsGap = rowsGap;
		
		if (maxRowsCount <= 0) {
			throw new IllegalArgumentException("maxRowsCount must be positive");
		}
		
		if (rowsHeight < 0) {
			throw new IllegalArgumentException("rowsHeight must not be negative");
		}
		
		init();
	}

	private void init() {
		add(getDetailsPanel(), BorderLayout.CENTER);
	}

	public List<KASPanel> getRowPanels() {
		if (rowPanels == null) {
			rowPanels = new ArrayList<KASPanel>();
			for (int i = 0; i < maxRowsCount; i++) {
				KASPanel panel = new KASPanel(new GridBagLayout());
				
				rowPanels.add(panel);
			}
			
			for (int i = 0; i < maxRowsCount; i++) {
				clearRow(i);
			}

		}
		return rowPanels;
	}
	
	private void setComponent(KASPanel rowPanel, Component comp) {
		rowPanel.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
	    c.weightx = 1.0;
	    c.gridx = 0;
	    c.gridy = 0;
	    
		rowPanel.add(comp, c);
	}
	
	private void setEmptyComponent(KASPanel rowPanel) {
		setComponent(rowPanel, Box.createVerticalStrut(rowHeight));
	}

	private KASPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new KASPanel(new GridLayout(getRowPanels().size(), 1, 0, rowsGap));
			for (KASPanel row : getRowPanels()) {
				detailsPanel.add(row);
			}
		}
		return detailsPanel;
	}
	
	private void validateRow(int row) {
		if (maxRowsCount <= row) {
			throw new IllegalArgumentException("row must be less than max rows number - row<" + row + "> max<" + maxRowsCount + ">");
		}
	}
	
	public void clearRow(int row) {
		validateRow(row);
		setEmptyComponent(getRowPanels().get(row));
	}
	
	public void setRowComponent(Component comp, int row) {
		validateRow(row);
		setComponent(getRowPanels().get(row), comp);
	}
	
	public void clearAllRows() {
		for (int i = 0; i < getRowPanels().size(); i ++) {
			clearRow(i);
		}
	}

	public void removeRowComponent(Component component) {
		for (KASPanel panel : getRowPanels()) {
			panel.remove(component);
		}
	}

	@Override
	public void repaint() {
		super.repaint();
		if (rowPanels != null) {
			for (KASPanel panel : getRowPanels()) {
				panel.repaint();
			}
		}
	}

}
