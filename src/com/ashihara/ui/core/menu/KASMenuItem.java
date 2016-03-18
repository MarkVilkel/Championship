/**
 * The file KASMenuItem.java was created on 11:26:31 at 2007.2.11
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenuItem;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.dockable.AKDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class KASMenuItem extends JMenuItem{
	private static final long serialVersionUID = 1L;
	
	private static UIC uic = ApplicationManager.getInstance().getUic();
	
	public KASMenuItem(KASMenuActionListener al, String title){
		super(title == null? al.getFrame().getName() : title);
		addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				List<AKDockable> frames = ApplicationManager.getInstance().findAllDockable();
				((AKMenuBar)ApplicationManager.getInstance().getMainFrame().getJMenuBar()).getMenuWindow().setEnabled(!frames.isEmpty());
			}
		});
		if (al != null)
			addActionListener(al);
	}
	
	public KASMenuItem(KASMenuActionListener al){
		this(al, null);
	}
	
	public KASMenuItem(String title) {
		this(null, title);
	}

	public static KASMenuItem createCloseAllFramesMenuItem(){
		KASMenuItem menu = new KASMenuItem(uic.CLOSE_ALL());
		menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				List<AKDockable> list = ApplicationManager.getInstance().findAllDockable();
				for (AKDockable frame : list)
					ApplicationManager.getInstance().unregisterAndDisposeComponent(frame);
				ApplicationManager.getInstance().getMainFrame().repaint();
			}
		});
		return menu;
	}
}