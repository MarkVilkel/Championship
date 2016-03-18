/**
 * The file GradientPanel.java was created on 2010.14.4 at 22:33:22
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.ashihara.ui.core.panel.KASPanel;

public class GradientPanel extends KASPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Color gradienColor;

	public GradientPanel() {
		this(null);
	}
	
	public GradientPanel(Color color) {
		this.gradienColor = color;
	}
	
	/* the below protected method solves the need, it applies color with gradient effect.
	the 2 statements setOpaque() before and after super.paintComponent(g) is must !! */
	
	protected void paintComponent(Graphics g)
	{
		if (gradienColor != null) {
			Graphics2D g2d = (Graphics2D)g;
			Color color1 = Color.LIGHT_GRAY;
			Color color2 = gradienColor;
			int w = getWidth( );
			int h = getHeight( );
			GradientPaint gp = new GradientPaint(0, 0, color1,0, h, color2 );
			g2d.setPaint( gp );
			g2d.fillRect( 0, 0, w, h );
			setOpaque(false);
			super.paintComponent(g);
			setOpaque(true);
		}
		else {
			super.paintComponent(g);
		}
		
	}

	public Color getGradienColor() {
		return gradienColor;
	}

	public void setGradienColor(Color gradienColor) {
		this.gradienColor = gradienColor;
	}
}
