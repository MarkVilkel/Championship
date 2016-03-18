/**
 * The file AKSplashScreen.java was created on 2007.13.12 at 00:35:12
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.main.view;

import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.UIFactory;

public class AKSplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;

	JLabel splashLabel = new JLabel();
	private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

	public AKSplashScreen() {
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