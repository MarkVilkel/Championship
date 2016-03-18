/**
 * The file StudentDetailsDialog.java was created on 2008.30.1 at 23:57:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.ui.app.fight.view.GradientPanel;
import com.ashihara.ui.app.fighter.model.IFighterDetailsModelUI;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.component.date.AKDateChooser;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.ImageIconPanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FighterDetailsPanelViewUI extends GradientPanel implements UIView<IFighterDetailsModelUI>{
	private static final long serialVersionUID = 1L;
	
	private KASTextField txtName, txtSurname, txtWeight, txtKyu, txtDan, txtFullYears;
	private AutoCompleteComboBox<Country> cmbCountry;
	private AutoCompleteComboBox<String> cmbGender; 
	private AKDateChooser dateBirthday;
	private GradientPanel fighterDetailsPanel, photoPanel;
	private IFighterDetailsModelUI modelUI;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	private KASPanel imageButtonPanel;
	private JButton btnSelectFile, btnDeleteFile;
	
	private ImageIconPanel imagePanel;
	
	public FighterDetailsPanelViewUI(IFighterDetailsModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		UIManager.put("Label.disabledForeground", Color.BLACK);
		
		add(getPhotoPanel(), BorderLayout.WEST);
		add(getDetailsUIPanel(), BorderLayout.CENTER);
		add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
	}

	public void setGradienColor(Color gradienColor) {
		super.setGradienColor(gradienColor);
		
		fighterDetailsPanel.setGradienColor(gradienColor);
		photoPanel.setGradienColor(gradienColor);
	}
	
	public KASPanel getDetailsUIPanel() {
		if (fighterDetailsPanel == null){
			fighterDetailsPanel = new GradientPanel();
			FormLayout fl = new FormLayout(
					"right:90dlu,4dlu, pref, 20dlu, pref, 4dlu, pref, right:20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, fighterDetailsPanel);
			
			builder.addSeparator("  " + uic.FIGHTER_INFO(), cc.xyw(1, 1, 7));
			
			builder.addLabel(uic.NAME()+": ", cc.xy(1, 3));
			builder.add(getTxtName(), cc.xy(3, 3));
			
			builder.addLabel(uic.SURNAME()+": ", cc.xy(1, 5));
			builder.add(getTxtSurname(), cc.xy(3, 5));

			builder.addLabel(uic.WEIGHT()+": ", cc.xy(1, 7));
			builder.add(getTxtWeight(), cc.xy(3, 7));
			
			builder.addLabel(uic.GENDER()+": ", cc.xy(1, 9));
			builder.add(getCmbGender(), cc.xy(3, 9));
			
			builder.addLabel(uic.COUNTRY()+": ", cc.xy(1, 11));
			builder.add(getCmbCountry(), cc.xy(3, 11));
			
			builder.addLabel(uic.BIRTHDAY()+": ", cc.xy(1, 13));
			builder.add(getDateBirthday(), cc.xy(3, 13));
			
			builder.addLabel(uic.AGE()+": ", cc.xy(1, 15));
			builder.add(getTxtFullYears(), cc.xy(3, 15));
			
			builder.addLabel(uic.KYU()+": ", cc.xy(1, 17));
			builder.add(getTxtKyu(), cc.xy(3, 17));
			
			builder.addLabel(uic.OR(), cc.xy(1, 19));
			
			builder.addLabel(uic.DAN()+": ", cc.xy(1, 21));
			builder.add(getTxtDan(), cc.xy(3, 21));
		}
		return fighterDetailsPanel;
	}

	public KASTextField getTxtName() {
		if (txtName == null){
			txtName = UIFactory.createKASTextField();
			txtName.setDisabledTextColor(Color.BLACK);
			txtName.setMaxSymbolsCount(255);
			txtName.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getTxtName().requestFocus();
				}
			});

		}
		return txtName;
	}

	public KASTextField getTxtSurname() {
		if (txtSurname == null){
			txtSurname = UIFactory.createKASTextField();
			txtSurname.setDisabledTextColor(Color.BLACK);
			txtSurname.setMaxSymbolsCount(255);
			txtSurname.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtSurname;
	}

	public IFighterDetailsModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IFighterDetailsModelUI modelUI) {
		this.modelUI = modelUI;		
	}

	public KASTextField getTxtWeight() {
		if (txtWeight == null) {
			txtWeight = UIFactory.createKASTextField();
			txtWeight.setDisabledTextColor(Color.BLACK);
			txtWeight.setMaxSymbolsCount(255);
			txtWeight.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtWeight;
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

	public AutoCompleteComboBox<Country> getCmbCountry() {
		if (cmbCountry == null) {
			cmbCountry = UIFactory.createAutoCompleteComboBox20(Country.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbCountry;
	}

	public AKDateChooser getDateBirthday() {
		if (dateBirthday == null) {
			dateBirthday = new AKDateChooser();
		}
		return dateBirthday;
	}

	public KASPanel getPhotoPanel() {
		if (photoPanel == null) {
			photoPanel =  new GradientPanel();
			photoPanel.setPreferredSize(new Dimension(200, 100));
			
			photoPanel.add(getImagePanel(), BorderLayout.CENTER);
			photoPanel.add(getImageButtonsPanel(), BorderLayout.SOUTH);
		}
		return photoPanel;
	}

	public ImageIconPanel getImagePanel() {
		if (imagePanel == null) {
			imagePanel = new ImageIconPanel(null);
		}
		return imagePanel;
	}
	
	public KASPanel getImageButtonsPanel() {
		if (imageButtonPanel == null){
			imageButtonPanel = UIFactory.createJPanelFLL();
			imageButtonPanel.add(getBtnSelectFile());
			imageButtonPanel.add(getBtnDeleteFile());
		}
		return imageButtonPanel;
	}
	public JButton getBtnSelectFile() {
		if (btnSelectFile == null){
			btnSelectFile = UIFactory.createBinocularButton();
			btnSelectFile.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getModelUI().loadPhoto();
				}
			});
			btnSelectFile.setToolTipText(uic.LOAD_PHOTO());
		}
		return btnSelectFile;
	}

	public JButton getBtnDeleteFile() {
		if (btnDeleteFile == null){
			btnDeleteFile = UIFactory.createSmallCancelButtonNoLabel();
			btnDeleteFile.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getModelUI().deletePhoto();
				}
			});
			btnDeleteFile.setToolTipText(uic.DELETE_PHOTO());
		}
		return btnDeleteFile;
	}

	public KASTextField getTxtKyu() {
		if (txtKyu == null) {
			txtKyu = UIFactory.createKASTextField();
			txtKyu.setDisabledTextColor(Color.BLACK);
			txtKyu.setMaxSymbolsCount(2);
			txtKyu.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtKyu;
	}

	public KASTextField getTxtDan() {
		if (txtDan == null) {
			txtDan = UIFactory.createKASTextField();
			txtDan.setDisabledTextColor(Color.BLACK);
			txtDan.setMaxSymbolsCount(2);
			txtDan.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtDan;
	}

	public KASTextField getTxtFullYears() {
		if (txtFullYears == null) {
			txtFullYears = UIFactory.createKASTextField();
			txtFullYears.setDisabledTextColor(Color.BLACK);
			txtFullYears.setEditable(false);
		}
		return txtFullYears;
	}

	public AutoCompleteComboBox<String> getCmbGender() {
		if (cmbGender == null) {
			cmbGender = UIFactory.createAutoCompleteComboBox20(String.class, DEFAULT_CONTROLS_LENGTH);
		}
		return cmbGender;
	}

}