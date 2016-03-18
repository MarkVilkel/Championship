/**
 * The file MainFrame.java was created on 2007.12.12 at 22:41:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.main.view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.awt.image.ToolkitImage;

import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.ashihara.ui.app.main.MainMenu;
import com.ashihara.ui.app.main.model.IMainFrameModelUI;
import com.ashihara.ui.core.dockable.AKDockable;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASStatusBar;
import com.ashihara.ui.core.progress.MainAppProgressBar;
import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;
import com.vlsolutions.swing.docking.DockTabbedPane;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockableState;
import com.vlsolutions.swing.docking.DockingDesktop;
import com.vlsolutions.swing.docking.DockingUtilities;
import com.vlsolutions.swing.docking.TabbedDockableContainer;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeEvent;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeListener;

public class MainFrame extends JFrame implements UIView<IMainFrameModelUI>{
	
	private static final long serialVersionUID = 1L;
	
	
	private DockingDesktop desktop;
	private KASStatusBar statusBar;
	private MainAppProgressBar progressBar;
	private MainMenu mainMenu;
	private NextFightStatusBarPanel nextFightStatusBarPanel;
	
	private UIC uic = ApplicationManager.getInstance().getUic();
	
	private IMainFrameModelUI modelUI;
	private BufferedImage backgroundImage;
	private JPanel contentPanel;
	
	public MainFrame(IMainFrameModelUI modelUI){
		super();
		this.modelUI = modelUI;
		ApplicationManager.getInstance().registerComponent(this);
		
		UIFactory.installLAF(SC.UI_PREFERENCES.DEFAULT_LOOK_AND_FEEL, SC.UI_PREFERENCES.DEFAULT_THEME);
		
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("");
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3+5, Toolkit.getDefaultToolkit().getScreenSize().height*2/3 + 50));
		
		setContentPane(getContentPanel());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent arg0) {
				getModelUI().onMainWindowClose();
			}
		});
		setTitle(uic.ASHIHARA_KARATE_CHAMPIONSHIP());
		setJMenuBar(getMainMenu());
		
		setVisible(true);
	}
	
	public DockingDesktop getDesktop() {
		if (desktop == null){
			desktop = new DockingDesktop(){
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) {
					super.paint(g);
//
//					Graphics2D g2d = (Graphics2D)g;
//					
//					BufferedImage img = getBackgroundImage();
//					int w = img.getWidth(null);
//					int h = img.getHeight(null);
//					BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//					g.drawImage(img, 0, 0, null);
//					
//					/* Create a rescale filter op that makes the image 50% opaque */
//					float[] scales = { 1f, 1f, 1f, 0.5f };
//					float[] offsets = new float[4];
//					RescaleOp rop = new RescaleOp(scales, offsets, null);
//					
//					/* Draw the image, applying the filter */
//					g2d.drawImage(bi, rop, 0, 0);
//					   
				}
			};
			desktop.addDockableStateWillChangeListener(new DockableStateWillChangeListener(){
				public void dockableStateWillChange(DockableStateWillChangeEvent e) {
					if (e.getFutureState().isClosed()){ 
						ApplicationManager.getInstance().unregisterAndDisposeComponent((Component)e.getFutureState().getDockable());
					}
				}
			});
		}
		return desktop;
	}

    public void addNewDockable(AKDockable dockable){
        Dockable parent=null;
        double x=0.0f;
        double y=1.0f;
       
        for(DockableState state:getDesktop().getDockables()){
            if(!state.isClosed() && !state.isHidden() &&!state.isFloating() && !state.isMaximized()){
                if(x<state.getPosition().getX()){
                    x=state.getPosition().getX();
                    parent=state.getDockable();
                }
                else if (x==state.getPosition().getX() && y>state.getPosition().getY()){
                    y=state.getPosition().getY();
                    parent=state.getDockable();
                }
            }
        }
        
        DockTabbedPane dtp;
        if(parent!=null){
        	dtp = (DockTabbedPane) DockingUtilities.findTabbedDockableContainer(parent);
            int order=1;
            
            if(dtp != null)
                order=dtp.getTabCount();
            getDesktop().createTab(parent,dockable,order,true);
        } 
        else {
           desktop.addDockable(dockable);
        }
    }
    
    public void setSelectedDockable(AKDockable dockable){
    	TabbedDockableContainer tabs = DockingUtilities.findTabbedDockableContainer(dockable);
    	if (tabs != null) {
    		tabs.setSelectedDockable(dockable);
    	}
    }

	public KASStatusBar getStatusBar() {
		if (statusBar == null){
			statusBar = new KASStatusBar();
			statusBar.add(getNextFightStatusBarPanel(), BorderLayout.WEST);
			statusBar.add(getProgressBar(), BorderLayout.EAST);
		}
		return statusBar;
	}

	public MainAppProgressBar getProgressBar() {
		if (progressBar == null){
			progressBar = new MainAppProgressBar();
		}
		return progressBar;
	}

	public IMainFrameModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IMainFrameModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public MainMenu getMainMenu() {
		if (mainMenu == null) {
			mainMenu = new MainMenu();
		}
		return mainMenu;
	}

	private BufferedImage getBackgroundImage() {
		if (backgroundImage == null) {
			backgroundImage = ((ToolkitImage)ResourceHelper.getImageIcon(ResourceHelper.LATVIA_ASHIHARA_KARATE_LOGO).getImage()).getBufferedImage();
		}
		return backgroundImage;
	}

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel(new BorderLayout()) {
				private static final long serialVersionUID = 1L;
				@Override
				public void paint(Graphics g) {
					super.paint(g);
					if (getDesktop().getDockables().length <= 0) {
						Graphics2D g2d = (Graphics2D)g;
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));  
						BufferedImage img = getBackgroundImage();
						g.drawImage(img, 0, 0, this.getWidth(), this.getHeight() - getStatusBar().getHeight(), null);
					}
				}
			};
			
			
			contentPanel.add(getDesktop(), BorderLayout.CENTER);
			contentPanel.add(getStatusBar(), BorderLayout.SOUTH);
		}
		return contentPanel;
	}

	private NextFightStatusBarPanel getNextFightStatusBarPanel() {
		if (nextFightStatusBarPanel == null) {
			nextFightStatusBarPanel = new NextFightStatusBarPanel();
		}
		return nextFightStatusBarPanel;
	}
}