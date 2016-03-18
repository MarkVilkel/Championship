/**
 * The file KASMenuActionListener.java was created on 2008.11.8 at 13:05:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ashihara.ui.core.dockable.AKDockable;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;
import com.ashihara.ui.core.dockable.AKModalDockable;
import com.ashihara.ui.tools.ApplicationManager;

public class KASMenuActionListener implements ActionListener{
	private final AKDockable frame;
	
	public KASMenuActionListener(AKDockable frame){
		this.frame = frame;
	}
	
	public AKDockable getFrame() {
		return frame;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getFrame() instanceof AKModalDockable) {
			ApplicationManager.getInstance().bringToFront(((AKModalDockable)getFrame()).getClass());
		}
		else if (getFrame() instanceof AKIdentifiedDockable) {
			ApplicationManager.getInstance().bringToFront(((AKIdentifiedDockable)getFrame()).getClass(), ((AKIdentifiedDockable)getFrame()).getId());
		}
	}
}