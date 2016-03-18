package com.ashihara.ui.app.group.view.stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.ashihara.ui.core.panel.KASPanel;

public class LeftConnectorPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private final int connectorHeight;
	private final boolean drawConnector;
	
	public LeftConnectorPanel(int connectorHeight, boolean drawConnector) {
		this.connectorHeight = connectorHeight;
		this.drawConnector = drawConnector;
		
		setPreferredSize(new Dimension(50, 25));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (drawConnector) {
			Dimension size = getSize();
			
			float width = size.width;
			float height = size.height;
			
			float halfOfHeight = height / 2;
			float halfOfConnectorHeight = connectorHeight / 2;
			
			Graphics2D g2d = (Graphics2D)g;
			
			Color prevColor = g2d.getColor();
			
			g2d.setColor(Color.BLACK);
			
			int x = 0;
			
			drawLine(g2d, x, halfOfHeight, width, halfOfHeight); // right horizontal line
			drawLine(g2d, x, halfOfHeight - halfOfConnectorHeight -1, x, halfOfHeight + halfOfConnectorHeight); // vertical line
			
			g2d.setColor(prevColor);
			
		}
		
	}
	
	private void drawLine(Graphics2D g2d, float x1, float y1, float x2, float y2) {
		g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}

}
