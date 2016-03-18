/**
 * The file MainFrameModelUI.java was created on 2009.22.1 at 12:52:29
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.main.model;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.ashihara.ui.app.auth.AuthorizationDialog;
import com.ashihara.ui.app.main.view.MainFrame;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.KASDebuger;

public class MainFrameModelUI extends AKAbstractModelUI<MainFrame> implements IMainFrameModelUI<MainFrame> {
	
	private MainFrame viewUI;
	private boolean wasMainWindowCloseInvoked = false;
	
	public MainFrameModelUI(){
		super();
		
		viewUI = new MainFrame(AKUIEventSender.newInstance(this));
		
		new AuthorizationDialog(null);
	}
	
	public MainFrame getViewUI() {
		return viewUI;
	}
	
	public void disposeApplication(){
		disposeApplication(true);
	}
	
	private void disposeApplication(Boolean closeSession){
		disposeAllComponents();
		
		exit();
	}
	
	private void disposeAllComponents(){
		try{
			appManager.disposeAllComponents();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				KASDebuger.println("Exit");
				System.exit(0);
			}
		});
	}
	
	public void configureMenu() {
		getViewUI().getJMenuBar().setVisible(true);
		getViewUI().setVisible(true);
	}

	private void enableUI(boolean flag) {
		getViewUI().getDesktop().setVisible(flag);
		if (getViewUI().getJMenuBar() != null) {
			getViewUI().getJMenuBar().setVisible(flag);
		}
		
		for (Window window : getViewUI().getOwnedWindows() ) {
			if (window instanceof JDialog) {
				((JDialog)window).getGlassPane().setVisible(!flag);
			}
		}
	}
	
	public void onMainWindowClose() {
		onMainWindowClose(true);
	}

	public void onMainWindowClose(Boolean closeSession) {
		if (!wasMainWindowCloseInvoked){
			wasMainWindowCloseInvoked = true;
			disposeApplication(closeSession);
		}
	}

	public void setViewUI(MainFrame viewUI) {
		this.viewUI = viewUI;		
	}
}