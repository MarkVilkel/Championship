/**
 * The file AddDeleteRowPanel.java was created on 2007.18.8 at 18:32:48
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.ashihara.ui.core.interfaces.AddDeleteInterface;
import com.ashihara.ui.core.layout.OrientableFlowLayout;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.UIFactory;

public class AddDeleteRowPanel extends KASPanel{
	private static final long serialVersionUID = 1L;
	private JButton btnAdd, btnDelete;
	private JTextField txtCountToAdd;
	private final AddDeleteInterface target;
	
	public final static String LEFT = "Left"; 
	public final static String RIGHT = "Right";
	public final static String BOTTOM = "Bottom";
	
	public AddDeleteRowPanel(AddDeleteInterface target, String orientation){
		super();
		this.target = target;
		
		if (BOTTOM.equals(orientation))
			setLayout(new FlowLayout(OrientableFlowLayout.LEFT));
		else if (LEFT.equals(orientation) || RIGHT.equals(orientation)){
			setLayout(new FlowLayout(OrientableFlowLayout.TOP));
			setPreferredSize(new Dimension(55,60));
		}		
		
		add(getBtnDelete());
		add(getBtnAdd());
		add(getTxtCountToAdd());
	}
	
	public JButton getBtnAdd() {
		if (btnAdd == null){
			btnAdd = UIFactory.createPlusButton();
			btnAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try{
						Integer countToAdd = new Integer(1);
						if (getTxtCountToAdd().isVisible())
							countToAdd = Integer.valueOf(getTxtCountToAdd().getText());
						if (countToAdd<0){
							MessageHelper.showErrorMessage(AddDeleteRowPanel.this,uic.THE_NUMBER_MUST_BE_POSITIVE());
							getTxtCountToAdd().setText("1");
						}else
							target.onAdd(countToAdd);
					} catch(Exception e ){
						e.printStackTrace();
						MessageHelper.showErrorMessage(AddDeleteRowPanel.this,uic.MUST_BE_THE_NUMBER());
						getTxtCountToAdd().setText("1");
					}
				}
			});
			btnAdd.setToolTipText(uic.ADD_NEW_ITEMS());
		}
		return btnAdd;
	}

	public JButton getBtnDelete() {
		if (btnDelete == null){
			btnDelete = UIFactory.createMinusButton();
			btnDelete.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (target.showDeleteConfirmation()){
						if (target.doesSelectionExist()){
							int result = MessageHelper.showConfirmationMessage(null, uic.ARE_YOU_SURE_YOU_WANT_TO_DELETE_SELECTED_ROWS());
							if (result == JOptionPane.YES_OPTION)
								target.onDelete();
						}
					}
					else
						target.onDelete();
				}
			});
			btnDelete.setToolTipText(uic.DELETE_SELECTED_ROWS());
			btnDelete.setEnabled(false);
		}
		return btnDelete;
	}

	public JTextField getTxtCountToAdd() {
		if (txtCountToAdd == null){
			txtCountToAdd = UIFactory.createJTextField(20);
			txtCountToAdd.setText("1");
			txtCountToAdd.setForeground(Color.RED);
			txtCountToAdd.setPreferredSize(new Dimension(20,22));
			txtCountToAdd.setToolTipText(uic.SPECIFY_THE_COUNT_OF_ITEMS_TO_BE_ADDED());
		}
		return txtCountToAdd;
	}
}