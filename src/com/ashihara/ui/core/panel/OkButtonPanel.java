/**
 * The file OkButtonPanel.java was created on 2008.15.8 at 12:48:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.ashihara.ui.tools.UIFactory;

public class OkButtonPanel extends KASPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton btnOk;
	private final ActionListener okBtnActionListener;
	
	public OkButtonPanel(ActionListener okBtnActionListener){
		super();
		this.okBtnActionListener = okBtnActionListener;
		
		init();
	}
	
	private void init(){
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(getBtnOk());
	}
	
	public JButton getBtnOk() {
		if (btnOk == null){
			btnOk = UIFactory.createSmallOkButton();
			btnOk.addActionListener(okBtnActionListener);
		}
		return btnOk;
	}
}
