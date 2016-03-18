package com.ashihara.ui.app.group.view.stuff;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;

public class TPanel extends JLabel implements Composite, CompositeContext {
	private static final long serialVersionUID = 1L;
	
	private int w = 300;
	private int h = 200;
	
	private int cornerRadius = 20;
	private int[] roundRect; // first quadrant
	private BufferedImage image;
	
	private boolean initialized = false;
	private int[][] first = new int[cornerRadius][];
	private int[][] second = new int[cornerRadius][];
	private int[][] third = new int[cornerRadius][];
	private int[][] forth = new int[cornerRadius][];
	
	public TPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		setMinimumSize(new Dimension(w, h));
		
		// calculate round rect     
		roundRect = new int[cornerRadius];
		for(int i = 0; i < roundRect.length; i++) {
			roundRect[i] = (int)(Math.cos(Math.asin(1 - ((double)i)/20))*20); // x for y
		}
		
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); // all black
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(g instanceof Graphics2D) {
			
			Graphics2D g2d = (Graphics2D) g;
			
			// draw 1 + 2 rectangles and copy pixels from image. could also be 1 rectangle + 4 edges
			g2d.setComposite(AlphaComposite.Src);
			
			g2d.drawImage(image, cornerRadius, 0, image.getWidth() - cornerRadius - cornerRadius, image.getHeight(), null);
			g2d.drawImage(image, 0, cornerRadius, cornerRadius, image.getHeight() - cornerRadius - cornerRadius, null);
			g2d.drawImage(image, image.getWidth() - cornerRadius, cornerRadius, image.getWidth(), image.getHeight() - cornerRadius, image.getWidth() - cornerRadius, cornerRadius, image.getWidth(), image.getHeight() - cornerRadius, null);
			
			// draw the corners using our own logic
			g2d.setComposite(this);
			
			g2d.drawImage(image, 0, 0, null);
			
		} else {
			super.paintComponent(g);
		}
	}
	
	@Override
	public CompositeContext createContext(ColorModel srcColorModel,
			ColorModel dstColorModel, RenderingHints hints) {
		return this;
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
		// assume only corners need painting
		
		if(!initialized) {
			// copy image pixels
			for(int i = 0; i < cornerRadius; i++) {
				// quadrants
				
				// from top to buttom
				// first
				first[i] = (int[]) src.getDataElements(src.getWidth() - cornerRadius, i, roundRect[i], 1, first[i]);
				
				// second
				second[i] = (int[]) src.getDataElements(cornerRadius - roundRect[i], i, roundRect[i], 1, second[i]);
				
				// from buttom to top
				// third
				third[i] = (int[]) src.getDataElements(cornerRadius - roundRect[i], src.getHeight() - i - 1, roundRect[i], 1, third[i]);
				
				// forth
				forth[i] = (int[]) src.getDataElements(src.getWidth() - cornerRadius, src.getHeight() - i - 1, roundRect[i], 1, forth[i]);
			}
			initialized = true;
		}       
		
		// copy image pixels into corners
		for(int i = 0; i < cornerRadius; i++) {
			// first
			dstOut.setDataElements(src.getWidth() - cornerRadius, i, first[i].length, 1, second[i]);
			
			// second
			dstOut.setDataElements(cornerRadius - roundRect[i], i, second[i].length, 1, second[i]);
			
			// third
			dstOut.setDataElements(cornerRadius - roundRect[i], src.getHeight() - i - 1, third[i].length, 1, third[i]);
			
			// forth
			dstOut.setDataElements(src.getWidth() - cornerRadius, src.getHeight() - i - 1, forth[i].length, 1, forth[i]);
		}
	}
	
}
