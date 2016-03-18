/**
 * The file PreferencesModelUI.java was created on 2009.23.3 at 15:30:31
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.settings.model;

import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.pojo.User;
import com.ashihara.ui.app.settings.view.PreferencesPanelViewUI;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.UIFactory;
import com.rtu.exception.PersistenceException;

public class PreferencesModelUI extends AKAbstractModelUI<PreferencesPanelViewUI> implements IPreferencesModelUI<PreferencesPanelViewUI> {
	private PreferencesPanelViewUI viewUI;
	
	public PreferencesModelUI(){
		super();
		createModelUI();
		getViewUI().getModelUI().reset();
	}

	protected void createModelUI() {
		setViewUI(new PreferencesPanelViewUI(AKUIEventSender.newInstance(this)));
	}
	
	public PreferencesPanelViewUI getViewUI() {
		return viewUI;
	}

	public void setViewUI(PreferencesPanelViewUI viewUI) {
		this.viewUI = viewUI;
	}
	
	public void installLAF(final String lafName, final String themeName){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				UIFactory.installLAF(lafName, themeName);
				UIFactory.refreshComponentTreeUI(getViewUI().getParentWindowOrFrame());
			}
		});
	}
	
	public void ok(){
		try {
			boolean needRestart = copyFromUI();
			ApplicationManager.getInstance().getClientSession().setUser(getUserService().save(ApplicationManager.getInstance().getClientSession().getUser()));
			
			User user = ApplicationManager.getInstance().getClientSession().getUser(); 
			installLAF(user.getLookAndFeel(), user.getTheme());
			
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	public void cancel(){
		getViewUI().disposeParent();
	}
	
	protected void copyOnUI() {
		getViewUI().getCmbLookAndFeel().setSelectedItem(ApplicationManager.getInstance().getClientSession().getUser().getLookAndFeel());
		getViewUI().getCmbTheme().setSelectedItem(ApplicationManager.getInstance().getClientSession().getUser().getTheme());
	}
	
	protected boolean copyFromUI(){
		ApplicationManager.getInstance().getClientSession().getUser().setTheme((String)getViewUI().getCmbTheme().getSelectedItem());
		ApplicationManager.getInstance().getClientSession().getUser().setLookAndFeel((String)getViewUI().getCmbLookAndFeel().getSelectedItem());
		
		return true;
	}

	public void reset() {
		ComboUIHelper.reloadLAFCombo(getViewUI().getCmbLookAndFeel(), UIFactory.getLookAndFeelMap().keySet(), false);
		reloadLAFCombo();
		
		copyOnUI();
	}
	
	private void reloadLAFCombo() {
		ComboUIHelper.reloadLAFCombo(getViewUI().getCmbTheme(), UIFactory.getThemeMap().keySet(), false);
	}
	
	public void lookAndFeelSelected() {
		String lafName = (String)getViewUI().getCmbLookAndFeel().getSelectedItem();
		getViewUI().getCmbTheme().setEnabled(UIFactory.canPerformThemes(lafName));
	}
}