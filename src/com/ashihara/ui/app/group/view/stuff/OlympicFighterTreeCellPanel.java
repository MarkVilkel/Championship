/**
 * The file FigtherEditPanel.java was created on 2010.19.4 at 22:27:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.SwingUtilities;

import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.component.KASLabel;
import com.ashihara.ui.core.panel.KASPanel;

public class OlympicFighterTreeCellPanel extends KASPanel {
	
	private static final long serialVersionUID = 1L;
	
	private KASLabel lblFighter;
	
	private boolean isRed;
	private final boolean canFight;
	private final boolean addLeftConnector;
	private final boolean drawLeftConnector;
	private final int height;
	private final int leftConnectorHeight;
	
	private final ActionListener fightActionListener;
	private final ActionListener nextFightActionListener;
	
	public OlympicFighterTreeCellPanel(
			boolean isRed,
			boolean canFight,
			boolean addLeftConnector,
			boolean drawLeftConnector,
			int height,
			int leftConnectorHeight,
			ActionListener fightActionListener,
			ActionListener nextFightActionListener
	) {
		this.isRed = isRed;
		this.canFight = canFight;
		this.addLeftConnector = addLeftConnector;
		this.drawLeftConnector = drawLeftConnector;
		this.height = height;
		this.fightActionListener = fightActionListener;
		this.nextFightActionListener = nextFightActionListener;
		this.leftConnectorHeight = leftConnectorHeight;
		
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		
		if (addLeftConnector) {
			add(new LeftConnectorPanel(leftConnectorHeight, drawLeftConnector), BorderLayout.WEST);
		}

		int fontHeight = getLblFighter().getFontMetrics(getLblFighter().getFont()).getHeight();
		
		KASPanel detailsPanel = new KASPanel();
		detailsPanel.setOpaque(false);
		detailsPanel.add(Box.createVerticalStrut((height - fontHeight) / 2), BorderLayout.NORTH);
		detailsPanel.add(getLblFighter(), BorderLayout.CENTER);
		detailsPanel.add(Box.createVerticalStrut((height - fontHeight) / 2), BorderLayout.SOUTH);
		
		
		add(detailsPanel, BorderLayout.CENTER);
		add(new ConnectorPanel(isRed, canFight, fightActionListener, nextFightActionListener), BorderLayout.EAST);
		
		setBackground(isRed);
	}
	
	private void setBackground(boolean isRed) {
		Color background = isRed ? UIUtils.RED : UIUtils.BLUE;
		setBackground(background);
	}

	public KASLabel getLblFighter() {
		if (lblFighter == null) {
			lblFighter = new KASLabel();
			lblFighter.setBold();
			lblFighter.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblFighter;
	}

	public boolean isRed() {
		return isRed;
	}

	public void setIsRed(boolean isRed) {
		this.isRed = isRed;
		
		setBackground(isRed);
	}

}
