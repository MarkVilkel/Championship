/**
 * The file UIEventSender.java was created on 2009.7.11 at 00:44:42
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.core;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ashihara.ui.core.mvc.model.UIModel;

public class UIEventSender implements InvocationHandler, UIEventListener{
	private final UIModel model;
	
	private static KasAWTEventListener kasAWTEventListener = new KasAWTEventListener();
	private static boolean isUIBlocked = false;
	private static int totalObjectBlockedUI = 0;
	private static UIEventSenderDelegate uiEventSenderDelegate;
	
	private static ExecutorService executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	public static <T extends UIModel> T newInstance(T model, UIEventSenderDelegate uiEventSenderDelegate) {
		Object object = Proxy.newProxyInstance(model.getClass().getClassLoader(),
				model.getClass().getInterfaces(),
				new UIEventSender(model));
		
		UIEventSender.uiEventSenderDelegate = uiEventSenderDelegate;
		return (T)object;
	}
	
	protected UIEventSender(UIModel model){
		super();
		
		this.model = model;
	}
	
	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
//		KASDebuger.println("Start UI method '" + model.getClass().getSimpleName()+"."+method.getName()+"' invocation.");
		
		startUIVisibleProgress();
		
		submit(new UIEventExecutor(model, method, args, UIEventSender.this));
		
		return null;
	}
	
	@Override
	public synchronized void finish(UIModel clazz, Method method, Object[] args, Object result) {
//		KASDebuger.println("Finish UI method '" + clazz.getClass().getSimpleName()+"." + method.getName()+"' invocation.");
		
		clazz.finishAsynchronicCall(model, method, args, result);

		stopUIVisibleProgress();
	}
	
	public static synchronized void stopUIVisibleProgress() {
		totalObjectBlockedUI--;
//		KASDebuger.println("--totalObjectBlockedUI = "+totalObjectBlockedUI);
		
		if (totalObjectBlockedUI > 0){
			return;
		}
		
		unblockUI();
		doAfterUnblockUI();
	}
	
	protected static synchronized void doAfterUnblockUI() {
		if (uiEventSenderDelegate != null) {
			uiEventSenderDelegate.afterUnblockUI();
		}
	}

	public static synchronized void startUIVisibleProgress() {
		totalObjectBlockedUI++;
//		KASDebuger.println("++totalObjectBlockedUI = "+totalObjectBlockedUI);
		if (totalObjectBlockedUI > 0 && !isUIBlocked()){
			doBeforeBlockUI();
			blockUI();
		}
	}

	protected static synchronized void doBeforeBlockUI() {
		if (uiEventSenderDelegate != null) {
			uiEventSenderDelegate.beforeBlockUI();
		}
	}
	private static synchronized void blockUI(){
		setUIBlocked(true);
		
	    Toolkit.getDefaultToolkit().addAWTEventListener(kasAWTEventListener,
	    	AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}
	
	private static class KasAWTEventListener implements AWTEventListener{
		@Override
		public void eventDispatched(AWTEvent event) {
			if (event instanceof InputEvent) {
				((InputEvent) event).consume();
			}
		}
	}
	
	public static synchronized void unblockUI(){
		setUIBlocked(false);
		
		Toolkit.getDefaultToolkit().removeAWTEventListener(kasAWTEventListener);
	}

	public static synchronized boolean isUIBlocked() {
		return isUIBlocked;
	}

	private static synchronized void setUIBlocked(boolean isUIBlocked) {
//		KASDebuger.println("setUIBlocked = " + isUIBlocked);
		UIEventSender.isUIBlocked = isUIBlocked;
	}
	
	public static void startAndCheckUIVisibleProgress(boolean wasUIBlocked){
		if (wasUIBlocked) {
			UIEventSender.startUIVisibleProgress();
		}
	}
	
	public static boolean stopAndCheckUIVisibleProgress(){
		boolean wasUIBlocked = UIEventSender.isUIBlocked();
		
		if (wasUIBlocked){
			UIEventSender.stopUIVisibleProgress();
		}
		
		return wasUIBlocked;
	}

	public static int getTotalObjectBlockedUI() {
		return totalObjectBlockedUI;
	}
	
	public static Future<?> submit(Runnable task) {
		return executor.submit(task);
	}
}