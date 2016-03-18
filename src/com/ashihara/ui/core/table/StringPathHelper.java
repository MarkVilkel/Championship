/**
 * The file StringPathHelper.java was created on 2009.12.5 at 13:53:08
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringPathHelper {
	private static final String SPLITTER_PATTERN = "\\.";
	private static final String GET_METHOD_PREFIX = "get";
	private static final String SET_METHOD_PREFIX = "set";
	private static final int START = 1;
	
	public static Class<?> getClassByPath(String path, Class<?> rootClass) {
		try {
			String[] splittedPath = path.split(SPLITTER_PATTERN);
			
			Class<?> pathItemClass = rootClass;
			
			for (int i = START; i < splittedPath.length; i++) { // Starting from 1, because the first split is the path of the rootClass
				String pathItem = splittedPath[i];
				pathItem = firstCharToUpperCase(pathItem);
				
				String getMethodName = GET_METHOD_PREFIX + pathItem;
				Method getMethod = pathItemClass.getMethod(getMethodName, (Class<?>[])null);
				
				pathItemClass = getMethod.getReturnType();
			}
			
			return pathItemClass;
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Object invokeGetMethodForPath(String path, Object rootObject) {
		try {
			String[] splittedPath = path.split(SPLITTER_PATTERN);
			
			Object pathItemObject = rootObject;
			Class<?> pathItemClass = pathItemObject.getClass();
			
			for (int i = START; i < splittedPath.length; i++) { // Starting from 1, because the first split is the path of the rootClass
				String pathItem = splittedPath[i];
				pathItem = firstCharToUpperCase(pathItem);
				
				String getMethodName = GET_METHOD_PREFIX + pathItem;
				Method getMethod = pathItemClass.getMethod(getMethodName, (Class<?>[])null);
				
				pathItemObject = getMethod.invoke(pathItemObject, (Object[]) null);
				
				if (pathItemObject == null) {
					break;
				}
				
				pathItemClass = pathItemObject.getClass();
			}
			
			return pathItemObject;
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void invokeSetMethodForPath(String path, Object rootObject, Object valueForSetting) {
		try {
			String[] splittedPath = path.split(SPLITTER_PATTERN);
			
			Object pathItemObject = rootObject;
			Class<?> pathItemClass = pathItemObject.getClass();
			
			for (int i = START; i < splittedPath.length; i++) { // Starting from 1, because the first split is the path of the rootClass
				String pathItem = splittedPath[i];
				pathItem = firstCharToUpperCase(pathItem);
				
				if (i == splittedPath.length - 1) { // call set method
					String setMethodName = SET_METHOD_PREFIX + pathItem;
					Method setMethod = findSetMethod(pathItemClass, setMethodName, valueForSetting);
					setMethod.invoke(pathItemObject, valueForSetting);
				}
				else { // call get method
					String getMethodName = GET_METHOD_PREFIX + pathItem;
					Method getMethod = pathItemClass.getMethod(getMethodName, (Class<?>[])null);
					
					pathItemObject = getMethod.invoke(pathItemObject, (Object[]) null);
					
					if (pathItemObject == null) {
						break;
					}
					
					pathItemClass = pathItemObject.getClass();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	private static Method findSetMethod(
			Class<?> pathItemClass,
			String setMethodName,
			Object valueForSetting
	) throws SecurityException, NoSuchMethodException {
		if (valueForSetting != null) {
			return pathItemClass.getMethod(setMethodName, valueForSetting.getClass());
		}
		else {
			Method[] methods = pathItemClass.getMethods();
			for (Method method : methods) {
				if (
						method.getName().equals(setMethodName) &&
						method.getParameterTypes() != null &&
						method.getParameterTypes().length == 1
				) { 
					return method;
				}
			}
		}
		throw new NoSuchMethodException("Mehtod " + setMethodName + " is not found for class " + pathItemClass);
	}

	private static String firstCharToUpperCase(String s) {
		return String.valueOf(s.charAt(0)).toUpperCase().concat(s.substring(1));
	}
}
