/**
 * The file MultiSelectionPanel.java was created on 2008.2.2 at 18:51:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import com.ashihara.ui.core.KASList;
import com.ashihara.ui.core.layout.OrientableFlowLayout;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class MultiSelectionPanel<T extends Object> extends KASPanel{
	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnAddAll, btnRem, btnRemAll;
	private KASList<T> initialList, selectedList;
	private final String initialListCaption, selectedListCaption;
	private KASPanel btnPanel;
	
	public MultiSelectionPanel(String initialListCaption, String selectedListCaption){
		super();
		this.initialListCaption = initialListCaption;
		this.selectedListCaption = selectedListCaption;
		
		init();
	}
	
	private void init(){
		FormLayout fl = new FormLayout(
				"pref, 15dlu, pref, 20dlu, pref, 4dlu,pref, right:20dlu, pref, 4dlu, pref",
				"pref, pref, pref, pref, pref, 4dlu, pref, 4dlu, pref");

		CellConstraints cc = new CellConstraints();

		PanelBuilder builder = new PanelBuilder(fl, this);
		
		builder.addSeparator("  " + initialListCaption, cc.xyw(1, 1, 1));
		
		builder.add(UIFactory.createScrollPane(getInitialList(), new Dimension(250,350)), cc.xy(1, 3));
		builder.add(getBtnPanel(), cc.xy(3, 3));
		
		builder.addSeparator(selectedListCaption, cc.xyw(5, 1, 1));
		builder.add(UIFactory.createScrollPane(getSelectedList(), new Dimension(250,350)), cc.xy(5, 3));
	}
	
	public JButton getBtnAdd() {
		if (btnAdd == null){
			btnAdd = UIFactory.createAddButton();
			btnAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Object[] selected = getInitialList().getSelectedValues();
					List<T> list = getInitialList().getKasModel().removeElements(selected);
					getSelectedList().getKasModel().addAll(list);
				}
			});
		}
		return btnAdd;
	}

	public JButton getBtnAddAll() {
		if (btnAddAll == null){
			btnAddAll = UIFactory.createAddAllButton();
			btnAddAll.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getSelectedList().getKasModel().addAll(getInitialList().getKasModel().getAllElements());
					getInitialList().getKasModel().removeAllElements();
				}
			});
		}
		return btnAddAll;
	}

	public JButton getBtnRem() {
		if (btnRem == null){
			btnRem = UIFactory.createRemButton();
			btnRem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Object[] selected = getSelectedList().getSelectedValues();
					List<T> list = getSelectedList().getKasModel().removeElements(selected);
					getInitialList().getKasModel().addAll(list);
				}
			});
}
		return btnRem;
	}

	public JButton getBtnRemAll() {
		if (btnRemAll == null){
			btnRemAll = UIFactory.createRemAllButton();
			btnRemAll.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getInitialList().getKasModel().addAll(getSelectedList().getKasModel().getAllElements());
					getSelectedList().getKasModel().removeAllElements();
				}
			});
		}
		return btnRemAll;
	}
	
	public KASList<T> getInitialList() {
		if (initialList == null){
			initialList = new KASList<T>();
		}
		return initialList;
	}

	public KASList<T> getSelectedList() {
		if (selectedList == null){
			selectedList = new KASList<T>();
		}
		return selectedList;
	}

	public KASPanel getBtnPanel() {
		if (btnPanel == null){
			btnPanel = new KASPanel(new OrientableFlowLayout(OrientableFlowLayout.VERTICAL));
			btnPanel.add(getBtnAdd());
			btnPanel.add(getBtnAddAll());
			btnPanel.add(getBtnRem());
			btnPanel.add(getBtnRemAll());

		}
		return btnPanel;
	}
}