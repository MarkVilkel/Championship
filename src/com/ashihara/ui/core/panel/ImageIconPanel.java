/**
 * The file ImagePanel.java was created on 2010.3.4 at 13:01:31
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageIconPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon imageIcon;
	private Image scaledImage;
	
	public ImageIconPanel (ImageIcon imageIcon) { 
		setImageIcon(imageIcon);
		
		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				if (getImageIcon() != null) {
					setScaledImage(getImageIcon().getImage());
				}
			}
		});
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent (g);
		
		if (getScaledImage() != null) {
			g.drawImage (getScaledImage(), getInsets().left, getInsets().top, this);
		}
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		if (imageIcon != null) {
			setScaledImage(imageIcon.getImage());
		}
	}

	public Image getScaledImage() {
		return scaledImage;
	}

	private void setScaledImage(Image image) {
		int width = getSize ().width;
		int height = getSize ().height;
		
		if (image != null && width > 0 && height > 0) { 
			scaledImage = image.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
		}
		else { 
			scaledImage = null;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}
}
