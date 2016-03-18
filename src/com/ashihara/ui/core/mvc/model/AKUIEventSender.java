/**
 * The file AKUIEventSender.java was created on 2009.8.1 at 15:55:33
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.model;

import com.ashihara.ui.core.mvc.core.UIEventSender;
import com.ashihara.ui.core.mvc.core.UIEventSenderDelegate;

public class AKUIEventSender extends UIEventSender {
	private static UIEventSenderDelegate uiEventSenderDelegate = new AKUIEventSenderDelegate(); 
	
	public static <T extends UIModel> T newInstance(T model) {
		return UIEventSender.newInstance(model, uiEventSenderDelegate);
	}
	
	protected AKUIEventSender(UIModel model){
		super(model);
	}
}