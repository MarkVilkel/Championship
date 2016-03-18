/**
 * The file SimpleTablePanel.java was created on 2009.10.3 at 12:55:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

public class SimpleTablePanel<T> extends AbstractAddDeleteTablePanel<T> {
	private static final long serialVersionUID = 1L;

	public SimpleTablePanel(Class<T> rowClass, String addDeletePanelPosition){
		super(rowClass, addDeletePanelPosition);
	}
	
	public SimpleTablePanel(Class<T> rowClass) {
		super(rowClass);
		getCommonButtonsPanel().setVisible(false);
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
}
