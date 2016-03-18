/**
 * The file ComboBoxItem.java was created on 2009.13.10 at 12:03:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.combo;

public class ComboBoxItem<T> {
	private T id;
	private T uiCaption;
	
	public T getId() {
		return id;
	}
	public void setId(T id) {
		this.id = id;
	}
	public T getUiCaption() {
		return uiCaption;
	}
	public void setUiCaption(T uiCaption) {
		this.uiCaption = uiCaption;
	}
	
	public String toString() {
		if (getUiCaption() == null) {
			return "";
		}
		return getUiCaption().toString();
	}
	
	public boolean equals(Object o) {
		if (o instanceof ComboBoxItem) {
			ComboBoxItem cbi = (ComboBoxItem)o;
			if (cbi.getId() == null && cbi.getUiCaption() == null && getId() == null && getUiCaption() == null) {
				return super.equals(o);
			}
			else if (cbi.getId() == null && cbi.getUiCaption() != null && getId() == null && getUiCaption() != null) {
				return cbi.getUiCaption().equals(getUiCaption());
			}
			else if (cbi.getId() != null && cbi.getUiCaption() == null && getId() != null && getUiCaption() == null) {
				return cbi.getId().equals(getId());
			}
			else if (cbi.getId() != null && cbi.getUiCaption() != null && getId() != null && getUiCaption() != null) {
				return cbi.getId().equals(getId()) && cbi.getUiCaption().equals(getUiCaption());
			}
			else {
				return super.equals(o);
			}
		}
		return super.equals(o);
	}
}
