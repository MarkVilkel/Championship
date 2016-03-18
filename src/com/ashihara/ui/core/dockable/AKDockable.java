/**
 * The file KASModalDockable.java was created on 2008.19.9 at 17:45:05
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.dockable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;
import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockableState;

public abstract class AKDockable extends JPanel implements Dockable{
	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private DockKey dockKey;
	
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	public AKDockable(String title){
		super();
		
		dockKey = new DockKey(title);
		dockKey.setName(title);
		dockKey.setTabName(title);
		dockKey.setDockableState(DockableState.STATE_DOCKED);
		dockKey.setCloseEnabled(true);
		dockKey.setMaximizeEnabled(true);
		dockKey.setFloatEnabled(true);
		dockKey.setNotification(true);
		
		ApplicationManager.getInstance().registerComponent(this);
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		add(getMainPanel(), BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		if (mainPanel == null){
			mainPanel = new JPanel(new BorderLayout());
		}
		return mainPanel;
	}

	public Component getComponent() {
		return this;
	}

	public DockKey getDockKey() {
		return dockKey;
	}
	
	public String getTabTitle(){
		return getDockKey() == null ? "" : getDockKey().getTabName();
	}

	public void dispose() {
		System.out.println("KASDockable dispose");
		unlinkAnyExistedModelsAndViews(getMainPanel());
	}

	/**
	 * Due to modelUI and viewUI have crossreferences on each other, so they must be unlink, to remove memory leaks.
	 *
	 */
	private void unlinkAnyExistedModelsAndViews(final Component c) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				UIFactory.unlinkAnyExistedModelsAndViews(c);
				if (c instanceof Container) {
					((Container)c).removeAll();
				}
				removeAll();
			}
		});
	}
	
	public void changeTitle(String title) {
		getDockKey().setName(title);
		getDockKey().setTabName(title);
		getDockKey().setKey(title);
	}
}