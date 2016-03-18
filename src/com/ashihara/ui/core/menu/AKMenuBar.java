/**
 * The file AKMenuBar.java was created on 2008.5.1 at 16:58:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.dockable.AKDockable;
import com.ashihara.ui.tools.ApplicationManager;

public abstract class AKMenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	protected static ApplicationManager appManager = ApplicationManager.getInstance(); 
	protected static UIC uic = appManager.getUic();
	
	private JMenu menuWindow;
	
//	private	JMenu menuSystem;
//	private KASMenuItem menuLogout;
	private KASMenuItem menuExit;
//	
//	private JMenu menuUser;
//	private KASMenuItem menuItemChangePwd, menuItemChangeName;
	
	public AKMenuBar(){
		super();
		beforeInit();
		init();
		afterInit();
	}
	
	protected void init(){
//		getMenuUser().add(getMenuItemChangeName());
//		getMenuUser().add(getMenuItemChangePwd());
//		
//		getMenuSystem().add(getMenuLogout());
//		getMenuSystem().add(getMenuExit());
//		
//		add(getMenuUser());
//		add(getMenuSystem());
		add(getMenuWindow());
	}
	
	public abstract void beforeInit();
	public abstract void afterInit();
	
	public JMenu getMenuWindow() {
		if (menuWindow == null){
			menuWindow = new JMenu(uic.WINDOWS());
			menuWindow.addMenuListener(new MenuListener(){
				public void menuCanceled(MenuEvent arg0) {
				}
				public void menuDeselected(MenuEvent arg0) {
				}
				public void menuSelected(MenuEvent arg0) {
					menuWindow.removeAll();

					List<AKDockable> list = appManager.findAllDockable();
					for (AKDockable frame : list) {
						KASMenuActionListener al = new KASMenuActionListener(frame);
						KASMenuItem kasMenuItem = new KASMenuItem(al, frame.getTabTitle());
						getMenuWindow().add(kasMenuItem);
					}
					getMenuWindow().addSeparator();
					if (!list.isEmpty()) {
						JMenuItem kasMenuItem = KASMenuItem.createCloseAllFramesMenuItem();
						getMenuWindow().add(kasMenuItem);
					}
				}
			});
			menuWindow.setEnabled(false);
		}
		return menuWindow;
	}

//	public KASMenuItem getMenuLogout() {
//		if (menuLogout == null){
//			menuLogout = new KASMenuItem(uic.LOGOFF());
//			menuLogout.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					appManager.getMainFrameModelUI().logout();
//				}
//			});
//		}
//		return menuLogout;
//	}

	public KASMenuItem getMenuExit() {
		if (menuExit == null){
			menuExit = new KASMenuItem(uic.EXIT());
			menuExit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					appManager.getMainFrameModelUI().onMainWindowClose();
				}
			});
		}
		return menuExit;
	}
	
//	public JMenu getMenuUser() {
//		if (menuUser == null){
//			menuUser = new JMenu(uic.USER());
//		}
//		return menuUser;
//	}

//	public JMenu getMenuSystem() {
//		if (menuSystem == null){
//			menuSystem = new JMenu(uic.SYSTEM());
//		}
//		return menuSystem;
//	}

//	public KASMenuItem getMenuItemChangeName() {
//		if (menuItemChangeName == null){
//			menuItemChangeName = new KASMenuItem(uic.CHANGE_USER_INFO());
//			menuItemChangeName.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					appManager.openDialog(NewUserDialog.class, ServiceProxy.getLoggedInUser());
//				}
//			});
//		}
//		return menuItemChangeName;
//	}

//	public KASMenuItem getMenuItemChangePwd() {
//		if (menuItemChangePwd == null){
//			menuItemChangePwd = new KASMenuItem(uic.CHANGE_PASSWORD());
//			menuItemChangePwd.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					appManager.openDialog(ChangePasswordDialog.class, ServiceProxy.getLoggedInUser());
//				}
//			});
//		}
//		return menuItemChangePwd;
//	}
}