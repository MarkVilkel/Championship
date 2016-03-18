/**
 * The file UIModel.java was created on 2009.8.1 at 15:27:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.model;

import java.lang.reflect.Method;

import com.ashihara.ui.core.mvc.view.UIView;

public interface UIModel<T extends UIView> {
	public void finishAsynchronicCall(UIModel clazz, Method method, Object[] args, Object invocationResult);
	public T getViewUI();
	public void setViewUI(T viewUI);
	public void unlink();
}
