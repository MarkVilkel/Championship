/**
 * The file AddDeleteInterface.java was created on 2007.18.8 at 19:59:47
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.interfaces;

public interface AddDeleteInterface {
	public void onAdd(Integer countToAdd);
	public void onDelete();
	public boolean doesSelectionExist();
	public boolean showDeleteConfirmation();
}
