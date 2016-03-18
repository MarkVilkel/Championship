/**
 * The file AKSplashScreen.java was created on 2010.26.1 at 23:03:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.ashihara.ui.resources.ResourceHelper;

public class SplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;

	JLabel splashLabel = new JLabel();
	private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

	public SplashScreen() {
		super();
		ImageIcon splashIcon = ResourceHelper.getImageIcon(ResourceHelper.LATVIA_ASHIHARA_KARATE_LOGO);
		
		splashLabel = new JLabel(splashIcon);
		getContentPane().add(splashLabel);
		pack();
		
		UIFactory.componentToCenter(this);
		
		setCursor(waitCursor);
	}
	
	public void setVisible(boolean b){
		super.setVisible(b);
		if (b == true){
		    this.toFront();
		}
	}
}