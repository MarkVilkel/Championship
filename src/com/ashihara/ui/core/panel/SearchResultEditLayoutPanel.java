/**
 * The file SearchResultEditLayoutPanel.java was created on 2007.29.8 at 16:27:15
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.ashihara.ui.core.interfaces.EditPanelInterface;
import com.ashihara.ui.core.interfaces.SearchEditPanelSwitcher;
import com.ashihara.ui.core.interfaces.SearchPanelInterface;

public class SearchResultEditLayoutPanel<T> extends JPanel implements SearchEditPanelSwitcher<T>{
	private static final long serialVersionUID = 1L;
	
	private final String SEARCH_RESULT = "SEARCH_RESULT"; 
	private final String EDIT = "EDIT";
	private final String BEFORE_EDIT = "BEFORE_EDIT";
	
	private JPanel searchResultPanel, editPanel, beforeEditPanel;
	
	private CardLayout mainCardLayout;
	
	public SearchResultEditLayoutPanel(){
		super();
	}
	
	public void setSearchResultEditPanels(JPanel searchResultPanel, JPanel editPanel){
		setSearchResultEditPanels(searchResultPanel, editPanel, new JPanel());
	}
	
	public void setSearchResultEditPanels(JPanel searchResultPanel, JPanel editPanel, JPanel beforeEditPanel){
		this.editPanel = editPanel;
		this.searchResultPanel = searchResultPanel;
		this.beforeEditPanel = beforeEditPanel;
		
		init();	
	}

	private void init() {
		setLayout(getMainCardLayout());
		add(searchResultPanel, SEARCH_RESULT);
		add(editPanel, EDIT);
		add(beforeEditPanel, BEFORE_EDIT);
		switchOnSearch(null);
	}

	public CardLayout getMainCardLayout() {
		if (mainCardLayout == null){
			mainCardLayout = new CardLayout();
		}
		return mainCardLayout;
	}

	public void switchOnEdit(T object, String param) {
		if (editPanel instanceof EditPanelInterface) {
			((EditPanelInterface<T>) editPanel).setEditingObject(object, param);
		}
		getMainCardLayout().show(this, EDIT);
	}

	public void switchOnSearch(Object param) {
		if (searchResultPanel instanceof SearchPanelInterface){
			((SearchPanelInterface)searchResultPanel).backToSearch(param);
		}
		getMainCardLayout().show(this, SEARCH_RESULT);	
	}

	public void switchOnBeforeEdit(T object, String param) {
		if (beforeEditPanel instanceof EditPanelInterface) {
			((EditPanelInterface<T>) beforeEditPanel).setEditingObject(object, param);
		}
		getMainCardLayout().show(this, BEFORE_EDIT);
	}
}
