/**
 * The file AuthorizationModelUI.java was created on 2009.10.1 at 16:35:50
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.auth.model;

import com.ashihara.datamanagement.core.persistence.exception.AKException;
import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.SecurityService;
import com.ashihara.datamanagement.pojo.User;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.ashihara.ui.app.auth.view.AuthorizationPanelViewUI;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.utils.PswHasher;
import com.rtu.exception.PersistenceException;

public class AuthorizationModelUI extends AKAbstractModelUI<AuthorizationPanelViewUI> implements IAuthorizationModelUI<AuthorizationPanelViewUI> {
	
	private AuthorizationPanelViewUI viewUI;
//	private final UIStatePerformer<UIC> uiStatePerformer;
	
	public AuthorizationModelUI(UIStatePerformer<UIC> uiStatePerformer){
		super();
		this.viewUI = new AuthorizationPanelViewUI(AKUIEventSender.newInstance(this));
//		this.uiStatePerformer = uiStatePerformer;
		
//		String locale = getUtilsClientService().getLocaleFromRegister();
//		reloadCombo(uic);
//		String language = SC.UI_PREFERENCES.UI_LANGUAGE.getLanguage(locale);
//		getViewUI().getCmbLanguage().setSelectedItemAsId(language);
	}
	
//	private void reloadCombo(UIC uic) {
//		Utils.fillUpCaptionableWithCMItems(getViewUI().getCmbLanguage(), new SC.UI_PREFERENCES.UI_LANGUAGE(), false, uic);
//	}

	public AuthorizationPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void login(String name, Long pwd) {
		User user = new User();
		user.setName(name);
		user.setPassword(pwd);
		
		try {
			AKClientSession kasClientSession = AKServerSessionManagerImpl.getInstance().getDefaultServerSession().getServerSideServiceFactory().getService(SecurityService.class).login(user);
			if (kasClientSession == null || kasClientSession.getUser() == null)
				getViewUI().getErrorLabel().setVisible(true);
			else {
				user = kasClientSession.getUser(); 
				user.setUiLanguage(SC.UI_PREFERENCES.UI_LANGUAGE.ENGLISH);
				
				ApplicationManager.getInstance().setClientSession(kasClientSession);
				user = getUserService().save(user);
				
				getViewUI().disposeParent();
			}
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	public void okPressed(){
		if (new String(getViewUI().getTxtPassword().getPassword()).isEmpty() || getViewUI().getTxtUserName().getText().isEmpty()){
			getViewUI().getErrorLabel().setVisible(true);	
		}
		else {
			login(getViewUI().getTxtUserName().getText(), PswHasher.hash(getViewUI().getTxtPassword().getPassword()));
		}
	}

	public void cancelPressed() {
		ApplicationManager.getInstance().setClientSession(null);
		getViewUI().disposeParent();
	}

	public void setViewUI(AuthorizationPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

//	public void switchLanguage() {
//		String lang = getViewUI().getCmbLanguage().getSelectedItem().toString();
//		UIC uic = new UICEnglish().ENGLISH().equals(lang) ? new UICEnglish() : new UICLatvian();
//		performTranslation(uic);
//	}
	
//	private void performTranslation(UIC uic) {
//		ApplicationManager.getInstance().setUic(uic);
//		String locale = uic.GET_LOCALE().getLanguage();
//		getUtilsClientService().putLocaleToRegister(locale);
//		changeCaptions(uic);
//		uiStatePerformer.performUIState(uic);
//		
//		ApplicationManager.getInstance().getMainFrameModelUI().changeCaptions(uic);
//	}

//	private void changeCaptions(UIC uic) {
//		getViewUI().getErrorLabel().setText(uic.USER_NAME_AND_OR_PASSWORD_IS_INCORRECT());
////		getViewUI().getLblLanguage().setText(uic.LANGUAGE());
//		getViewUI().getLblUserName().setText(uic.USER_NAME());
//		getViewUI().getLblPassword().setText(uic.PASSWORD());
//		
//		getViewUI().getOkCancelButtonPanel().getBtnCancel().setText(uic.CANCEL());
//		getViewUI().getOkCancelButtonPanel().getBtnCancel().setToolTipText(uic.CANCEL());
//		
//		getViewUI().getOkCancelButtonPanel().getBtnOk().setText(uic.OK());
//		getViewUI().getOkCancelButtonPanel().getBtnOk().setToolTipText(uic.OK());
//		
//		getViewUI().repaint();
//	}
}