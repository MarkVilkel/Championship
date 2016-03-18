/**
 * The file UIEventExecutor.java was created on 2009.9.1 at 17:05:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ashihara.ui.core.mvc.model.UIModel;

public class UIEventExecutor implements Runnable {
	private final UIModel<?> object;
	private final Method method;
	private final Object[] args;
	private final UIEventListener listener;
	
	public UIEventExecutor(UIModel<?> object, Method method, Object[] args, UIEventListener listener){
		super();
		
		this.object = object;
		this.method = method;
		this.args = args;
		this.listener = listener;
	}
	
	@Override
	public void run(){
		Object result = null;
		try {
			result = method.invoke(object, args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			listener.finish(object, method, args, result);
		}
	}
}
