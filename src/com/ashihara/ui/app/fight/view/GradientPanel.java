/**
 * The file GradientPanel.java was created on 2010.14.4 at 22:33:22
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.ashihara.ui.core.panel.KASPanel;


public class GradientPanel extends KASPanel {
	private static final long serialVersionUID = 1L;
	
	private Color gradienColor;
	private final Image photo;

	public GradientPanel() {
		this(null, null);
	}
	
	public GradientPanel(Color color) {
		this(color, null);
	}
	
	public GradientPanel(Color color, ImageIcon photo) {
		this.gradienColor = color;
		if (photo != null) {
			this.photo = photo.getImage();
		} else {
			this.photo = null;
		}
	}
	
	/* the below protected method solves the need, it applies color with gradient effect.
	the 2 statements setOpaque() before and after super.paintComponent(g) is must !! */
	
	protected void paintComponent(Graphics g) {
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
			
			if (photo != null) {
				drawScaledImage(photo, this, g);
			}

			
			super.paintComponent(g);
			setOpaque(true);
		}
		else {
			super.paintComponent(g);
		}
	}
	
	public static void drawScaledImage(Image image, Component canvas, Graphics g) {
        int imgWidth = image.getWidth(null);
        int imgHeight = image.getHeight(null);
         
        double imgAspect = (double) imgHeight / imgWidth;
 
        int canvasWidth = (int)(canvas.getWidth() / 3d);
        int canvasHeight = (int)(canvas.getHeight() / 3d);
         
        double canvasAspect = (double) canvasHeight / canvasWidth;
 
        int x1 = 5; // top left X position
        int y1 = (int) (80 * canvasAspect); // top left Y position
        int x2 = 0; // bottom right X position
        int y2 = 0; // bottom right Y position
         
        if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
            // the image is smaller than the canvas
//            x1 = (canvasWidth - imgWidth)  / 2;
//            y1 = (canvasHeight - imgHeight) / 2;
            x2 = imgWidth + x1;
            y2 = imgHeight + y1;
             
        } else {
            if (canvasAspect > imgAspect) {
//                y1 = canvasHeight;
                // keep image aspect ratio
                canvasHeight = (int) (canvasWidth * imgAspect);
//                y1 = (y1 - canvasHeight) / 2;
            } else {
//                x1 = canvasWidth;
                // keep image aspect ratio
                canvasWidth = (int) (canvasHeight / imgAspect);
//                x1 = (x1 - canvasWidth) / 2;
            }
            x2 = canvasWidth + x1;
            y2 = canvasHeight + y1;
        }
 
        g.drawImage(image, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
    }

	public Color getGradienColor() {
		return gradienColor;
	}

	public void setGradienColor(Color gradienColor) {
		this.gradienColor = gradienColor;
	}
}
