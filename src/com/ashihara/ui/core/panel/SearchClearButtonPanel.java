/**
 * The file SearchClearButtonPanel.java was created on 2007.29.8 at 16:39:26
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.UIFactory;

public class SearchClearButtonPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton btnClear, btnSearch, btnShowHide;
	private JPanel buttonsPanel;
	private SearchClearKeyListener keyListener;
	private final ActionListener clearBtnActionListener, searchBtnActionListener;
	private boolean showing = true;
	private Dimension size;
	private JComponent detailsComponent;
	
	public SearchClearButtonPanel(ActionListener clearBtnActionListener, ActionListener searchBtnActionListener){
		super();
		this.clearBtnActionListener = clearBtnActionListener;
		this.searchBtnActionListener = searchBtnActionListener;
		init();
	}
	
	private void init(){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		add(getButtonsPanel(), BorderLayout.SOUTH);
		
		addContainerListener(new ContainerAdapter(){
			public void componentAdded(ContainerEvent e) {
				setSearchClearKeyListenerForComponentTree(e.getChild());
			}
		});
	}
	
	public void setDetailsPanel(KASPanel panel, boolean scrollPaneNeeded){
		
		JPanel btnHideShowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		btnHideShowPanel.add(getBtnShowHide());
		add(btnHideShowPanel, BorderLayout.NORTH);
		
		if (scrollPaneNeeded) {
			detailsComponent = UIFactory.createScrollPane(panel);
		}
		else {
			detailsComponent = panel;
		}
		
		add(detailsComponent, BorderLayout.CENTER);
	}
	
	public void setDetailsPanel(KASPanel panel){
		setDetailsPanel(panel, true);
	}
	
	public JButton getBtnClear() {
		if (btnClear == null){
			btnClear = UIFactory.createSmallClearButton();
			btnClear.addActionListener(clearBtnActionListener);
		}
		return btnClear;
	}
	
	public JButton getBtnSearch() {
		if (btnSearch == null){
			btnSearch = UIFactory.createSmallSearchButton();
			btnSearch.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Container container = SearchClearButtonPanel.this;
					try {
						Validator.validateInputData(container);
						searchBtnActionListener.actionPerformed(e);
					} catch (AKValidationException e1) {
						MessageHelper.handleValidationException(null, e1);
					}
				}
			});
		}
		return btnSearch;
	}

	public JPanel getButtonsPanel() {
		if (buttonsPanel == null){
			buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			buttonsPanel.add(getBtnSearch());
			buttonsPanel.add(getBtnClear());
		}
		return buttonsPanel;
	}
	
	private class SearchClearKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){  
				getBtnSearch().doClick();
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){  
				getBtnClear().doClick();
			} 
		}

		public void keyReleased(KeyEvent e) {
			
		}

		public void keyTyped(KeyEvent e) {
			
		}
	}

	private void setSearchClearKeyListenerForComponentTree(Component c) {
		if (c instanceof JTextField){
			c.removeKeyListener(getKeyListener());
			c.addKeyListener(getKeyListener());
		}
		else if (c instanceof JComboBox) {
			c.removeKeyListener(getKeyListener());
			c.addKeyListener(getKeyListener());
			setSearchClearKeyListenerForComponentTree(((JComboBox)c).getEditor().getEditorComponent());
		}
		else if (c instanceof Container){
			for (Component com : ((Container)c).getComponents()) {
				setSearchClearKeyListenerForComponentTree(com);
			}
		}
	}
	
	public SearchClearKeyListener getKeyListener() {
		if (keyListener == null){
			keyListener = new SearchClearKeyListener();
		}
		return keyListener;
	}
	
	public boolean isSearchCriteriaFilled(){
		return (isSearchCriteriaFilled(this));
	}
	
	private boolean isSearchCriteriaFilled(Component c){
		if (c instanceof JTextField){
			JTextField txt = (JTextField)c;
			if(!txt.getText().trim().isEmpty())
				return true;
		}
		else if (c instanceof JComboBox){
			JComboBox cmb = (JComboBox)c;
			if(cmb.getSelectedItem() != null)
				return true;
		}
		else if (c instanceof Container){
			for (Component com : ((Container)c).getComponents()){
				boolean result = isSearchCriteriaFilled(com);
				if (result)
					return true;
			}
		}
		return false;
	}

	private JButton getBtnShowHide() {
		if (btnShowHide == null) {
			btnShowHide = new JButton("+");
			btnShowHide.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showHide();
				}
			});
			setSize(btnShowHide);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					btnShowHide.setText("-");
				}
			});
		}
		return btnShowHide;
	}

	protected void showHide() {
		if (showing) {
			getBtnShowHide().setText("+");
			showing = false;
			
			size = getSize();
			
			remove(getButtonsPanel());
			remove(detailsComponent);
			
			setPreferredSize(new Dimension(size.width, getBtnShowHide().getSize().height + getInsets().bottom + getInsets().top));
		}
		else {
			getBtnShowHide().setText("-");
			showing = true;

			add(getButtonsPanel(), BorderLayout.SOUTH);
			add(detailsComponent, BorderLayout.CENTER);
			
			setPreferredSize(size);
		}
		
		setSize(getBtnShowHide());
	}
	
	private void setSize(JButton btn) {
		int btnSize = Math.max(btn.getPreferredSize().width, btn.getPreferredSize().height);
		btn.setPreferredSize(new Dimension(btnSize, btnSize));
	}

}