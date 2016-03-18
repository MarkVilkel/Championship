/**
 * The file ConnectorPanel.java was created on 2012.14.4 at 14:20:46
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.ashihara.ui.core.component.KASLinkLabel;
import com.ashihara.ui.core.panel.KASPanel;

public class ConnectorPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private final boolean isUpper;
	private final boolean canFight;
	private final ActionListener fightActionListener;
	private final ActionListener nextFightActionListener;
	
	private KASLinkLabel lnkFight;
	private KASLinkLabel lnkNext;
	
	
	public ConnectorPanel(
			boolean isUpper,
			boolean canFight,
			ActionListener fightActionListener,
			ActionListener nextFightActionListener
	) {
		this.isUpper = isUpper;
		this.canFight = canFight;
		this.fightActionListener = fightActionListener;
		this.nextFightActionListener = nextFightActionListener;
		
		setPreferredSize(new Dimension(50, 25));
		
		if (isUpper && canFight) {
			setLayout(new BorderLayout());
			add(getLnkFight(), BorderLayout.CENTER);
		}
		else if (canFight) {
			add(getLnkNext(), BorderLayout.NORTH);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (canFight) {
			Dimension size = getSize();
			
			float width = size.width;
			float height = size.height;
			
			float y = isUpper ? height - 1 : 0;
			
			Graphics2D g2d = (Graphics2D)g;
			
			Color prevColor = g2d.getColor();
			
			g2d.setColor(Color.BLACK);
			
			drawLine(g2d, 0, y, width, y);
			
			g2d.setColor(prevColor);
		}
		
	}
	
	private void drawLine(Graphics2D g2d, float x1, float y1, float x2, float y2) {
		g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}

	private KASLinkLabel getLnkFight() {
		if (lnkFight == null) {
			lnkFight = new KASLinkLabel(uic.FIGHT());
			lnkFight.setHorizontalAlignment(SwingUtilities.CENTER);
			lnkFight.addActionListener(fightActionListener);
		}
		return lnkFight;
	}

	private KASLinkLabel getLnkNext() {
		if (lnkNext == null) {
			lnkNext = new KASLinkLabel(uic.NEXT());
			lnkNext.setHorizontalAlignment(SwingUtilities.CENTER);
			lnkNext.addActionListener(nextFightActionListener);
		}
		return lnkNext;
	}

}
