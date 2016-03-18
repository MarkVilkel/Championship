/**
 * The file ApplicationManager.java was created on 2007.28.8 at 22:46:45
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.enums.UIC;
import com.ashihara.enums.UICEnglish;
import com.ashihara.ui.app.main.model.IMainFrameModelUI;
import com.ashihara.ui.app.main.view.MainFrame;
import com.ashihara.ui.core.dialog.DialogCreator;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.core.dockable.AKDockable;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.core.interfaces.IdentifiedComponent;
import com.ashihara.ui.core.menu.AKMenuBar;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingDesktop;

public class ApplicationManager {
	private static ApplicationManager manager = null;
	
	private List<Component> components;
	
	private UIC uic;
	
	private AKClientSession clientSession;
	
	private ApplicationManager(){
	}
	
	public static ApplicationManager getInstance(){
		if (manager == null)
			manager = new ApplicationManager();
		return manager;
	}
	
	public void registerComponent(Component component){
		KASDebuger.println("--- Register component - "+component.getClass().getSimpleName());
		if (component instanceof AKDockable){
			getMainFrame().addNewDockable((AKDockable)component);
		}
		synchronized (this) {
			getComponents().add(component);
		}
	}
	
	
	public boolean isRegistered(Class<? extends Component> clazz){
		synchronized (this) {
			for (Component c : getComponents()) {
				if (c.getClass() == clazz) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean isRegistered(Component c){
		synchronized (this) {
			return getComponents().contains(c);
		}
	}
	
	public boolean unregisterComponent(Component c){
		synchronized (this) {
			if (isRegistered(c)){
				KASDebuger.println("--- Unregister component - "+c.getClass().getSimpleName());
				getComponents().remove(c);
				return true;
			}
			return false;
		}
	}
	
	public static Component getParentWindowOrFrame(Component c){
		if (c instanceof Window){
			return c;
		}
		else if (c instanceof JInternalFrame){
			return c;
		}
		else if (c instanceof AKDockable){
			return c;
		}
		else if (c != null){
			return getParentWindowOrFrame(c.getParent());
		}
		return null;
	}
	

	public void disposeParent(Component c) {
		Component parent = getParentWindowOrFrame(c);
		unregisterAndDisposeComponent(parent);
	}

	public void unregisterAndDisposeComponent(Component c){
		if (unregisterComponent(c)){
			KASDebuger.println("--- Dispose component - "+c.getClass().getSimpleName());
			
			if (c instanceof AKDockable){
				
				DockingDesktop desktop = getMainFrame().getDesktop();
				desktop.close((Dockable)c);
				desktop.removeFromTabbedGroup((Dockable)c);
				desktop.remove((Dockable)c);
		      	desktop.unregisterDockable((Dockable) c);
		      	
		      	((AKDockable)c).dispose();
		      	
				List<AKDockable> frames = ApplicationManager.getInstance().findAllDockable();
				((AKMenuBar)ApplicationManager.getInstance().getMainFrame().getJMenuBar()).getMenuWindow().setEnabled(!frames.isEmpty());
			}
			else if (c instanceof KASDialog){
				KASDialog dialog = (KASDialog)c;
				dialog.dispose();
			}
			else if (c instanceof JFrame) {
				((JFrame)c).dispose();
			}
		}
		else {
			if (c instanceof JFrame) {
				((JFrame)c).dispose();
			}
		}
		
		Runtime.getRuntime().gc();
		
		if (getMainFrame() != null) {
			getMainFrame().repaint();
		}
	}
	
	public  <K extends Object, T extends AKIdentifiedDockable<K>> T findIdentifiedComponent(Class<T> clazz, Object id){
		synchronized (this) {
			for (Component com : getComponents()){
				if (com instanceof IdentifiedComponent && com.getClass() == clazz){
					if(id == null && ((IdentifiedComponent)com).getId() == null){
						return (T)com;
					}
					if (((IdentifiedComponent)com).getId()!= null && ((IdentifiedComponent)com).getId().equals(id)) {
						return (T)com;
					}
				}
			}
			return null;
		}
	}
	
	public Component findComponent(Class clazz){
		synchronized (this) {
			for (Component com : getComponents()){
				if (com.getClass() == clazz) {
					return com;
				}
			}
			return null;
		}
	}
	
	public List<AKDockable> findAllDockable(){
		synchronized (this) {
			List<AKDockable> list = new ArrayList<AKDockable>();
			for (Component com : getComponents())
				if (com instanceof AKDockable)
					list.add((AKDockable)com);
			return list;
		}
	}

	public MainFrame getMainFrame(){
		synchronized (this) {
			for (Component com : getComponents())
				if (com.getClass() == MainFrame.class)
					return (MainFrame)com;
			return null;
		}
	}
	
	public IMainFrameModelUI getMainFrameModelUI(){
		MainFrame mainFrame = getMainFrame();
		if (mainFrame == null) {
			return null;
		}
		return mainFrame.getModelUI();
	}
	
	public Dimension getMainFrameSizeForDialog(){
		Dimension size = getMainFrame().getSize();
		return new Dimension(size.width-7, size.height-7);
	}
	
	public void disposeAllComponents() {
		disposeAllComponentsExceptMainFrame();
		unregisterAndDisposeComponent(getMainFrame());
	}
	
	public void disposeAllComponentsExceptMainFrame() {
		List<Component> list = new ArrayList<Component>();
		synchronized (this) {
			for (Component c : getComponents()) {
				if (c.getClass() != MainFrame.class) {
					list.add(c);
				}
			}
			
			for (Component c : list) {
				unregisterAndDisposeComponent(c);
			}
		}
	}
	
	public synchronized void setCursorForAll(final Cursor cursor){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				synchronized (this) {
					List<Component> components = new ArrayList<Component>();
					components.addAll(getComponents()); 
					if (components != null){
						MainFrame mainFrame = getMainFrame();
						if (mainFrame != null) {
							mainFrame.setCursor(cursor);
							mainFrame.getContentPane().setCursor(cursor);
							for (Component c : components){
								if (c instanceof Window) {
									((Window)c).setCursor(cursor);
								}
							}
						}
					}
				}
			}
		});
				
	}

	public synchronized void setCursorForAll(int cursor){
		setCursorForAll(Cursor.getPredefinedCursor(cursor));
	}
	
	public Cursor getCurrentCursor(){
		MainFrame mainFrame = getMainFrame();
		if (mainFrame != null)
			return mainFrame.getCursor();
		return null;
	}
	
//	private void setCursorForTree(Component component, Cursor cursor){
//		if (component.getCursor().getType() == Cursor.WAIT_CURSOR || component.getCursor().getType() == Cursor.DEFAULT_CURSOR){
//			component.setCursor(cursor);
//		}
//		
//		if (component instanceof Container){
//			for (Component c : ((Container)component).getComponents()){
//				setCursorForTree(c, cursor);
//			}
//		}
//	}
	
	public void switchLanguage(){
		uic = new UICEnglish();
	}
	
	public UIC getUic() {
		if (uic == null){
			switchLanguage();
		}
		return uic;
	}

	public synchronized List<Component> getComponents() {
		if (components == null){
			components = new ArrayList<Component>();
		}
		return components;
	}
	
	public <T extends AKIdentifiedDockable> void bringToFront(Class<T> clazz, Object id){
		T frame = (T)ApplicationManager.getInstance().findIdentifiedComponent(clazz, id);
		bringToFront(frame);
	}

	public <T extends AKModalDockable> void bringToFront(Class<T> clazz){
		T frame = (T)ApplicationManager.getInstance().findComponent(clazz);
		bringToFront(frame);
	}
	
	public <T extends AKDockable> void bringToFront(T dockable){
		if (ApplicationManager.getInstance().getMainFrame() != null){
			ApplicationManager.getInstance().getMainFrame().setSelectedDockable(dockable);
		}
	}
	
	public void bringParentToFront(Component c){
		Component parent = ApplicationManager.getParentWindowOrFrame(c);
		synchronized (this) {
			if (getComponents().contains(parent)){
				if (parent instanceof AKDockable){
					bringToFront((AKDockable) parent);
				}
				else{
					KASDebuger.println(this+" can not bring to front component - "+c);
				}
			}
		}
	}
	
	public <K extends Object, T extends AKIdentifiedDockable<K>> T openIdentifiedFrame(Class<T> clazz, K id, Object ... constructorParams){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		
		T frame = (T)ApplicationManager.getInstance().findIdentifiedComponent(clazz, id);
		try {
			if (frame == null){
				frame = UIFactory.createObject(clazz, constructorParams);
				if (frame == null){
					throw new NullPointerException("ERROR!!! Frame '"+clazz+"' was not creared!");
				}
			}
			else{
				bringToFront(frame);
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
		}
		return frame;
	}
	
	public <T extends AKModalDockable> T openModalFrame(Class<T> clazz){
		return openModalFrame(clazz, (Object[])null);
	}

	public <T extends AKModalDockable> T openModalFrame(Class<T> clazz, Object... constructorParams) {
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		
		T frame = (T)ApplicationManager.getInstance().findComponent(clazz);
		try {
			if (frame == null){
				frame = UIFactory.createObject(clazz, constructorParams);
				if (frame == null){
					throw new NullPointerException("ERROR!!! Frame '"+clazz+"' was not creared!");
				}
			}
			else{
				bringToFront(frame);
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
		}
		return frame;
	}
	
	public <T extends KASDialog> void openDialog(Class<T> clazz, Object ... constructorParams) {
		SwingUtilities.invokeLater(new DialogCreator<T>(clazz, constructorParams));
	}
	
	public <T extends KASDialog> void openDialog(Class<T> clazz) {
		openDialog(clazz, (Object[])null);
	}

	public AKClientSession getClientSession() {
		return clientSession;
	}

	public void setClientSession(AKClientSession clientSession) {
		this.clientSession = clientSession;
	}
}