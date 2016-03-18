/**
 * The file SearchEditPanelSwitcher.java was created on 2007.1.9 at 16:28:11
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.interfaces;

public interface SearchEditPanelSwitcher<T> {
	public void switchOnSearch(Object param);
	public void switchOnEdit(T object, String param);
	public void switchOnBeforeEdit(T object, String param);
}
