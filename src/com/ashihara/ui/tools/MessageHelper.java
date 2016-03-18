/**
 * The file ExceptionHandler.java was created on 2007.19.8 at 12:58:41
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.enums.UIC;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;

public class MessageHelper {
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	private static Object[] yesNoOptions = new Object[]{uic.YES(), uic.NO()};
	private static Object[] okOptions = new Object[]{uic.OK()};

	public static void showErrorMessage(Component owner, String caption, String message){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		
		JOptionPane.showOptionDialog(owner,
				message, caption, 
				JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, null,
				okOptions, okOptions[0]);
		
		AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
	}

	public static void showInformtionMessage(Component owner, String message){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		
		JOptionPane.showOptionDialog(owner,
				message, uic.INFORMATION(), 
				JOptionPane.OK_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null,
				okOptions, okOptions[0]);
		
		AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
	}
	
	public static int showConfirmationMessage(Component owner, String message){
		return showConfirmationMessage(owner, uic.CHOICE(), message);
	}
	
	public static int showConfirmationMessage(Component owner, String header, String message){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		
		int n = JOptionPane.showOptionDialog(owner,
				message, header, 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				yesNoOptions, yesNoOptions[0]);

		AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
		
		return n;
	}
	
	public static void showLocalizedMessage(Component owner, Exception e){
		e.printStackTrace();
		showErrorMessage(owner, e.getLocalizedMessage());
	}
	
	public static void showErrorMessage(Component owner, String message){
		showErrorMessage(owner, uic.ERROR(), message);
	}
	
//	public static void handleSessionExpiredException(SessionExpiredException e){
//		if (ServiceProxy.getLoggedInUser() == null){
//			return;
//		}
//		e.printStackTrace();
//		if (SessionExpiredException.THE_USER_IS_ALREADY_CONNECTED.equals(e.getMessage()))
//			showErrorMessage(null, uic.THE_USER_WITH_GIVEN_NAME_ALREADY_CONNECTED());
//		else 
//			showErrorMessage(null, uic.NO_SESSION_OR_YOUR_SESSION_HAS_BEEN_EXPIRED());
//		if (ApplicationManager.getInstance().getMainFrame() != null)
//			ApplicationManager.getInstance().getMainFrameModelUI().logout(false);
//	}
//	
	public static void handleException(Component owner, Throwable e){
		if (e instanceof AKValidationException) {
			handleValidationException(owner, (AKValidationException)e);
		}
		else {
			e.printStackTrace();
			showErrorMessage(owner, e.getMessage());
		}
	}
	
//	public static void handleDBException(Component owner, DMCoreException e){
//		e.printStackTrace();
//		showErrorMessage(owner, e.getMessage());
//	}
//	
//	public static void handleKASBusinessException(Component owner, AKBusinessException e){
//		e.printStackTrace();
//		showErrorMessage(owner, e.getMessage());
//	}
	
	public static void handleValidationException(Component owner, String msg) {
		showErrorMessage(owner, uic.VALIDATION_ERROR(), msg);
	}
	
	public static void handleValidationException(Component owner, AKValidationException e) {
		e.printStackTrace();
		handleValidationException(owner, e.getMessage());
	}
	
//	public static void handleValidationException(Component owner, AKValidationException e, String msg) {
//		e.printStackTrace();
//		handleValidationException(owner, e.getMessage() + "\n"+msg);
//	}
	
//	public static void handleConnectionException(ConnectException e) {
//		e.printStackTrace();
//		showErrorMessage(null, uic.CONNECTION_ERROR(), uic.NO_CONNECTION_WITH_SERVER_PLEASE_CONTACT_SYSTEM_ADMINISTRATOR());
//		if (ApplicationManager.getInstance().getMainFrameModelUI() != null){
//			ApplicationManager.getInstance().getMainFrameModelUI().onMainWindowClose(false);
//		}else{
//			ApplicationManager.getInstance().getMainFrameModelUI().exit();
//		}
//	}

	public static void showErrorMessage(Component c, Throwable e) {
		e.printStackTrace();
		showErrorMessage(c, e.getMessage());
	}
}