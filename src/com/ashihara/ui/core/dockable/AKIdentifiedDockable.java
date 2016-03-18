/**
 * The file KASIdentifiedDockable.java was created on 2009.26.1 at 13:07:51
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.dockable;

import com.ashihara.ui.core.interfaces.IdentifiedComponent;

public class AKIdentifiedDockable<T extends Object> extends AKDockable implements IdentifiedComponent<T>{
	private static final long serialVersionUID = 1L;
	
	private T id;

	public AKIdentifiedDockable(String title, T id) {
		super(title);
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
}