/**
 * The file UIEventListener.java was created on 2009.9.1 at 17:19:21
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.core;

import java.lang.reflect.Method;

import com.ashihara.ui.core.mvc.model.UIModel;

public interface UIEventListener {
	void finish(UIModel clazz, Method method, Object[] args, Object result);
}
