/**
 * The file OlympicFightersPanel.java was created on 2012.20.2 at 21:47:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.ashihara.ui.core.panel.KASPanel;

public class OlympicFightersPanel extends KASPanel {

	private static final long serialVersionUID = 1L;

	private OlympicFighterTreeCellPanel redFighterPanel;
	private OlympicFighterTreeCellPanel blueFighterPanel;
	
	private JButton btnFight;
	
	public OlympicFightersPanel() {
		
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		
		add(getBtnFight(), BorderLayout.EAST);
	}

	private JButton getBtnFight() {
		if (btnFight == null) {
			btnFight = new JButton(uic.FIGHT());
		}
		return btnFight;
	}

	public OlympicFighterTreeCellPanel getRedFighterPanel() {
		return redFighterPanel;
	}

	public void setRedFighterPanel(OlympicFighterTreeCellPanel redFighterPanel) {
		if (this.redFighterPanel != null) {
			throw new IllegalArgumentException("Red fighter panel is already added");
		}
		add(redFighterPanel, BorderLayout.NORTH);

		this.redFighterPanel = redFighterPanel;
	}

	public OlympicFighterTreeCellPanel getBlueFighterPanel() {
		return blueFighterPanel;
	}

	public void setBlueFighterPanel(OlympicFighterTreeCellPanel blueFighterPanel) {
		if (this.blueFighterPanel != null) {
			throw new IllegalArgumentException("Blue fighter panel is already added");
		}
		add(blueFighterPanel, BorderLayout.SOUTH);
		this.blueFighterPanel = blueFighterPanel;
	}
	
}
