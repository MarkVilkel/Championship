/**
 * The file KASStatusBar.java was created on 2008.2.10 at 14:22:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KASStatusBar extends KASPanel {
	private static final long serialVersionUID = 1L;
	
	public KASStatusBar() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(10, 23));
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(new JLabel(new AngledLinesWindowsCornerIcon()), BorderLayout.SOUTH);
		rightPanel.setOpaque(false);
		rightPanel.setBorder(BorderFactory.createEtchedBorder());
		
		add(rightPanel, BorderLayout.EAST);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintLines(g, getWidth(), getHeight());
	}
	
	public static void paintLines(Graphics g, int width, int height){
		int y = 0;
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, width, y);
		y++;
		g.setColor(new Color(196, 194, 183));
		g.drawLine(0, y, width, y);
		y++;
		g.setColor(new Color(218, 215, 201));
		g.drawLine(0, y, width, y);
		y++;
		g.setColor(new Color(233, 231, 217));
		g.drawLine(0, y, width, y);
		
		y = height - 3;
		g.setColor(new Color(233, 232, 218));
		g.drawLine(0, y, width, y);
		y++;
		g.setColor(new Color(233, 231, 216));
		g.drawLine(0, y, width, y);
		y = height - 1;
		g.setColor(new Color(221, 221, 220));
		g.drawLine(0, y, width, y);
	}
}