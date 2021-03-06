/**
 * The file StudentDetailsDialog.java was created on 2008.30.1 at 23:57:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.country.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ashihara.ui.app.country.model.ICountryDetailsModelUI;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CountryDetailsPanelViewUI extends KASPanel implements UIView<ICountryDetailsModelUI>{
	private static final long serialVersionUID = 1L;
	
	private KASTextField txtCode, txtName;
	private KASPanel studentDetailsPanel;
	private ICountryDetailsModelUI modelUI;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	
	public CountryDetailsPanelViewUI(ICountryDetailsModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getDetailsUIPanel(), BorderLayout.CENTER);
		add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
	}

	public KASPanel getDetailsUIPanel() {
		if (studentDetailsPanel == null){
			studentDetailsPanel = new KASPanel();
			FormLayout fl = new FormLayout(
					"right:90dlu,4dlu, pref, 20dlu, pref, 4dlu,pref, right:20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, studentDetailsPanel);
			
			builder.addSeparator("  " + uic.COUNTRY_INFO(), cc.xyw(1, 1, 7));
			
			builder.addLabel(uic.CODE()+": ", cc.xy(1, 3));
			builder.add(getTxtCode(), cc.xy(3, 3));
			
			builder.addLabel(uic.NAME()+": ", cc.xy(1, 5));
			builder.add(getTxtName(), cc.xy(3, 5));
		}
		return studentDetailsPanel;
	}

	public KASTextField getTxtCode() {
		if (txtCode == null){
			txtCode = UIFactory.createKASTextField();
			txtCode.setMaxSymbolsCount(255);
			txtCode.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtCode;
	}

	public KASTextField getTxtName() {
		if (txtName == null){
			txtName = UIFactory.createKASTextField();
			txtName.setMaxSymbolsCount(255);
			txtName.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtName;
	}

	public ICountryDetailsModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(ICountryDetailsModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public SaveCancelResetButtonPanel getSaveCancelResetButtonPanel() {
		if (saveCancelResetButtonPanel == null) {
			ActionListener saveAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().save();
				}
			};
			ActionListener resetAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().reset();
				}
			};
			ActionListener cancelAl = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			saveCancelResetButtonPanel = new SaveCancelResetButtonPanel(saveAl, cancelAl, resetAl);
		}
		return saveCancelResetButtonPanel;
	}
}