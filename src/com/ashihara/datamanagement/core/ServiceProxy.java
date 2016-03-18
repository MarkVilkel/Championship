/**
 * The file ServiceProxy.java was created on 2007.30.12 at 14:08:57
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.ui.tools.KASDebuger;
import com.ashihara.utils.MathUtils;

public class ServiceProxy implements InvocationHandler {
	
	public static <T extends AKService> T newInstance(T serviceImpl) {
	     T service = (T) Proxy.newProxyInstance(serviceImpl.getClass().getClassLoader(),
                serviceImpl.getClass().getInterfaces(),
                new ServiceProxy());
		return service;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Date before = new Date();
		
		try {
			Object result = method.invoke(proxy, args);
			
			return result;
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Date after = new Date();
			
			KASDebuger.println("Invocation took - " + MathUtils.round(new Double(after.getTime() - before.getTime())/1000, 4));
		}
	}
}