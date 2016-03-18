/**
 * The file AKUIEventSenderDelegate.java was created on 2009.7.11 at 01:22:08
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.model;

import java.awt.Cursor;

import com.ashihara.ui.core.mvc.core.UIEventSenderDelegate;
import com.ashihara.ui.tools.ApplicationManager;

public class AKUIEventSenderDelegate implements UIEventSenderDelegate {
	private Cursor previousCursor;

	public void afterUnblockUI() {
		if (ApplicationManager.getInstance().getMainFrame() != null){
			ApplicationManager.getInstance().getMainFrame().getProgressBar().stop();
		}
		setDefaultCursor();
	}
	
	public void beforeBlockUI() {
		if (ApplicationManager.getInstance().getMainFrame()!= null) {
			ApplicationManager.getInstance().getMainFrame().getProgressBar().start();
		}
		setWaitCursor();
	}

	private void setWaitCursor(){
		Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
		if (ApplicationManager.getInstance().getCurrentCursor() != null && waitCursor.getType() != ApplicationManager.getInstance().getCurrentCursor().getType()){
			previousCursor = ApplicationManager.getInstance().getCurrentCursor();
			ApplicationManager.getInstance().setCursorForAll(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}
	
	private void setDefaultCursor(){
		ApplicationManager.getInstance().setCursorForAll(previousCursor);
	}
}
