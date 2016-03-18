/**
 * The file Main.java was created on 2010.26.1 at 23:01:40
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.SecurityService;
import com.ashihara.ui.app.main.model.MainFrameModelUI;
import com.ashihara.ui.app.main.view.AKSplashScreen;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.UIFactory;

public class Main {
	
	private AKSplashScreen splashScreen;
	
	public Main() {
		super();
		
		
		UIManager.put("TabbedPane.selectedForeground", Color.BLUE);
		
		init();
		
		launch();
	}
	
	private void launch() {
		try {
			getSplashScreen().setVisible(true);
			
			try { 
				AKServerSessionManagerImpl.getInstance().getDefaultServerSession().getServerSideServiceFactory().getService(SecurityService.class).defaultLogin();
			} catch (Exception e) {
				e.printStackTrace();
				MessageHelper.showErrorMessage(null, ApplicationManager.getInstance().getUic().CAN_NOT_CONNECT_TO_THE_DATA_BASE());
				System.exit(-1);
			}
//			
//			
//			UIFactory.installLanguageForDockable(ApplicationManager.getInstance().getUic());
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrameModelUI();
					
					AKClientSession session = ApplicationManager.getInstance().getClientSession();
					if (session == null) {
						System.exit(0);
					}
					UIFactory.installLAF(session.getUser().getLookAndFeel(), session.getUser().getTheme());
				}
			});
			
		} finally {
			if (getSplashScreen().isVisible()) {
				getSplashScreen().setVisible(false);
			}
		}
	}

	private void init() {
		
	}

	public AKSplashScreen getSplashScreen() {
		if (splashScreen == null){
			splashScreen = new AKSplashScreen();
		}
		return splashScreen;
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
