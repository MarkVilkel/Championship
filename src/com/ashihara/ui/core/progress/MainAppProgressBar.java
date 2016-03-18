/**
 * The file MainAppProgressBar.java was created on 2009.8.1 at 15:00:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.progress;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.panel.AngledLinesWindowsCornerIcon;
import com.ashihara.ui.core.panel.KASStatusBar;
import com.ashihara.ui.tools.ApplicationManager;

public class MainAppProgressBar extends JProgressBar {
    private static final long serialVersionUID = 1L;
    private final UIC uic = ApplicationManager.getInstance().getUic();

    public MainAppProgressBar(){
    	super();
    	
    	setBorder(BorderFactory.createEmptyBorder());
    	
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(new JLabel(new AngledLinesWindowsCornerIcon()), BorderLayout.SOUTH);
		rightPanel.setOpaque(false);
		
		setLayout(new BorderLayout());
		add(rightPanel, BorderLayout.EAST);
    }
    
    public void start() {
    	SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				setIndeterminate(true);
				setStringPainted(true);
				setString(uic.WAIT()+"...");
			}
    	});
    }

    public void stop() {
    	SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				setIndeterminate(false);
				setStringPainted(false);
				setString("");
			}
    	});
    }

    public boolean isRunning() {
    	return isIndeterminate();
    }
    
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		KASStatusBar.paintLines(g, getWidth(), getHeight());
	}
}