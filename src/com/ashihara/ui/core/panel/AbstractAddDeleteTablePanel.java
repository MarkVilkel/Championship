/**
 * The file AbstractAddDeleteTablePanel.java was created on 2009.4.3 at 11:41:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

public abstract class AbstractAddDeleteTablePanel<T> extends AddDeleteButtonsTablePanel<T>{

	public AbstractAddDeleteTablePanel(Class<T> rowClass, String addDeletePanelPosition){
		super(rowClass, addDeletePanelPosition);
		getButtonsPanel().remove(getButtonsPanel().getTxtCountToAdd());
	}
	
	public AbstractAddDeleteTablePanel(Class<T> rowClass) {
		this(rowClass, AddDeleteRowPanel.BOTTOM);
	}

	public abstract void add();
	public abstract void delete();
	
	public void onAdd(Integer countToAdd) {
		super.onAdd(countToAdd);
		add();
	}

	public void onDelete() {
		super.onDelete();
		delete();
	}
}
