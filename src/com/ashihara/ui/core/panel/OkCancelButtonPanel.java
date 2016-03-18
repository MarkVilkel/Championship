/**
 * The file OkCancelButtonPanel.java was created on 2007.29.8 at 13:57:19
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ashihara.ui.tools.UIFactory;

public class OkCancelButtonPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton btnOk, btnCancel;
	private final ActionListener okBtnActionListener, cancelBtnActionListener;
	
	public OkCancelButtonPanel(ActionListener okBtnActionListener, ActionListener cancelBtnActionListener){
		super();
		this.okBtnActionListener = okBtnActionListener;
		this.cancelBtnActionListener = cancelBtnActionListener;
		init();
	}
	
	private void init(){
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(getBtnOk());
		add(getBtnCancel());
	}
	
	public JButton getBtnOk() {
		if (btnOk == null){
			btnOk = UIFactory.createSmallOkButton();
			btnOk.addActionListener(okBtnActionListener);
		}
		return btnOk;
	}
	
	public JButton getBtnCancel() {
		if (btnCancel == null){
			btnCancel = UIFactory.createSmallCancelButton();
			btnCancel.addActionListener(cancelBtnActionListener);
		}
		return btnCancel;
	}
}
