/**
 * The file MVDialog.java was created on 2007.28.8 at 23:06:08
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.ashihara.enums.UIC;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.KASDebuger;
import com.ashihara.ui.tools.UIFactory;

public class KASDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	protected static UIC uic = ApplicationManager.getInstance().getUic();
//	private boolean wasUIBlocked = false;
	private final DialogCallBackListener dialogCallBackListener;
	private boolean wasDialogClosed = false;
	
	public KASDialog(String title){
		this(title, null);
	}
	
	public KASDialog(String title, DialogCallBackListener dialogCallBackListener){
		super(ApplicationManager.getInstance().getMainFrame());
		this.dialogCallBackListener = dialogCallBackListener;
		try{
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setGlassPane(new EmptyGrayPanel());
			setTitle(title);
			ApplicationManager.getInstance().registerComponent(this);
			
			init();
			
			pack();
			repaint();
		} finally{
//			wasUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		}
	}
	
	private void init() {
		setModal(true);
		setResizable(false);
		
		setContentPane(getMainPanel());
	}
	
	public JPanel getMainPanel() {
		if (mainPanel == null){
			mainPanel = new JPanel();
			mainPanel.setBorder(BorderFactory.createEtchedBorder());
			mainPanel.setLayout(new BorderLayout());
		}
		return mainPanel;
	}

	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (isWindowClosing(e)) {
			ApplicationManager.getInstance().unregisterComponent(this);
		}
	}
	
	protected boolean isWindowClosing(WindowEvent e){
		if (e.getID() == WindowEvent.WINDOW_CLOSED)	{
			if (!wasDialogClosed){
				wasDialogClosed = true;
				onDialogClosing();
			}
			return true;
		}
			
		return false;
	}
	
	protected void onDialogClosing() {
		if (dialogCallBackListener != null) {
//			AKUIEventSender.startAndCheckUIVisibleProgress(wasUIBlocked);
			dialogCallBackListener.dialogClosed(this.getClass(), getDialogCallBackListenerParams());
//			wasUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		}
	}

	protected Map<String, Object> getDialogCallBackListenerParams() {
		return new HashMap<String, Object>();
	}

	public void dispose(){
		super.dispose();
		System.out.println("KASDialog dispose");
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

	
	public void processKeyEvent(KeyEvent ke){
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
			dispose();
		else
			super.processKeyEvent(ke);
	}
	
	public void setVisible(boolean isVisible){
		System.gc();
		if (ApplicationManager.getInstance().isRegistered(this)){ // this is needed for session expired functionality
			super.setVisible(isVisible);
		}
		else{
			try{
				KASDebuger.println("Dialog '"+this.getClass().getSimpleName()+"' is not registered, can not perform visibilty for flag value - "+isVisible);
			}catch(Throwable e){
			}
		}
	}
	
	public Dimension getMainFrameSize(){
		return ApplicationManager.getInstance().getMainFrameSizeForDialog();
	}
}