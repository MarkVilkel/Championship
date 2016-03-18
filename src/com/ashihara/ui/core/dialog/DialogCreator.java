/**
 * The file DialogCreator.java was created on 2009.27.1 at 15:42:34
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.dialog;

import com.ashihara.ui.tools.UIFactory;

public class DialogCreator<T extends KASDialog> extends Thread{
	private final Class<T> clazz;
	private final Object[] constructorParams;
	public DialogCreator(Class<T> clazz, Object ... constructorParams){
		super();
		this.clazz = clazz;
		this.constructorParams = constructorParams;
	}
	public void run(){
		try {
			T dialog = UIFactory.createObject(clazz, constructorParams);
			if (dialog == null){
				throw new NullPointerException("ERROR!!! Dialog '"+clazz+"' was not creared!");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}