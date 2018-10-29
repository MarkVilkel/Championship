/**
 * The file AuthorizationPanelViewUI.java was created on 2009.10.1 at 16:34:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.auth.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ashihara.ui.app.auth.model.IAuthorizationModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.OkCancelButtonPanel;
import com.ashihara.ui.resources.ResourceHelper;
import com.ashihara.ui.tools.UIFactory;
import com.ashihara.utils.PswHasher;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AuthorizationPanelViewUI extends KASPanel implements UIView<IAuthorizationModelUI<?>> {
	private static final long serialVersionUID = 1L;
	
	private OkCancelButtonPanel okCancelButtonPanel;
	private JPanel buttonPanel, detailsPanel, logoPanel;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JLabel errorLabel;
//	private KASComboBox cmbLanguage;
	private JLabel lblUserName, lblPassword, lblLanguage;
	
	private IAuthorizationModelUI<?> modelUI;
	
	public AuthorizationPanelViewUI(IAuthorizationModelUI<?> modelUI) {
		super();
		this.modelUI = modelUI;
		
		init();
	}

	public IAuthorizationModelUI<?> getModelUI() {
		return modelUI;
	}

	
	private void init() {
		setLayout(new BorderLayout());
		add(getLogoPanel(),BorderLayout.NORTH);
		add(getDetailsPanel(),BorderLayout.CENTER);
		add(getButtonPanel(),BorderLayout.SOUTH);
		
		tryAutoLogin();
	}

	private void tryAutoLogin() {
		Object name = System.getProperty("name");
		Object longPassword = System.getProperty("password");
		Object stringPassword = System.getProperty("pwd");
		
		System.clearProperty("name");
		System.clearProperty("password");
		System.clearProperty("pwd");
		
		if (name != null) {
			getTxtUserName().setText(String.valueOf(name));
			try {
				if (longPassword != null) {
					getModelUI().login(String.valueOf(name), Long.valueOf(longPassword.toString()));
				}
				else if (stringPassword != null) {
					getModelUI().login(String.valueOf(name), PswHasher.hash(String.valueOf(stringPassword)));
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public JPanel getButtonPanel() {
		if (buttonPanel == null){
			buttonPanel = UIFactory.createJPanelBL();
			buttonPanel.add(getOkCancelButtonPanel(), BorderLayout.CENTER);
			buttonPanel.add(getErrorLabel(), BorderLayout.NORTH);
		}
		return buttonPanel;
	}

	public JPanel getDetailsPanel() {
		if (detailsPanel == null){
			detailsPanel = new JPanel();
			
			FormLayout fl = new FormLayout(
					"right:100dlu, 4dlu, pref, 20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, detailsPanel);
			
			builder.add(getLblUserName(), cc.xy(1, 3));
			builder.add(getTxtUserName(), cc.xy(3, 3));

			builder.add(getLblPassword(), cc.xy(1, 5));
			builder.add(getTxtPassword(), cc.xy(3, 5));
			
//			builder.add(getLblLanguage(), cc.xy(1, 7));
//			builder.add(getCmbLanguage(), cc.xy(3, 7));
		}
		
		return detailsPanel;
	}

	public JPasswordField getTxtPassword() {
		if (txtPassword == null){
			txtPassword = UIFactory.createJPasswordField(DEFAULT_CONTROLS_LENGTH);
			txtPassword.addKeyListener(new ADKeyAdapter());
		}
		return txtPassword;
	}

	public JTextField getTxtUserName() {
		if (txtUserName == null){
			txtUserName = UIFactory.createJTextField(DEFAULT_CONTROLS_LENGTH);
			txtUserName.addKeyListener(new ADKeyAdapter());
		}
		return txtUserName;
	}

	public JPanel getLogoPanel() {
		if (logoPanel == null){
			logoPanel = UIFactory.createJPanelBL();
			logoPanel.add(new JLabel(ResourceHelper.getImageIcon(ResourceHelper.LATVIA_ASHIHARA_KARATE_LOGO_SMALL)), BorderLayout.CENTER);
		}
		return logoPanel;
	}
	
	public JLabel getErrorLabel() {
		if (errorLabel == null){
			errorLabel = new JLabel(uic.USER_NAME_AND_OR_PASSWORD_IS_INCORRECT());
			errorLabel.setForeground(Color.RED);
			errorLabel.setVisible(false);
			errorLabel.setHorizontalAlignment(JLabel.CENTER);
		}
		return errorLabel;
	}
	
	private class ADKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent key) {
			if (key.getKeyCode() ==  KeyEvent.VK_ENTER)
				getOkCancelButtonPanel().getBtnOk().doClick();
			else if (key.getKeyCode() ==  KeyEvent.VK_ESCAPE)
				getOkCancelButtonPanel().getBtnCancel().doClick();
			else
				getErrorLabel().setVisible(false);
		}
	}

	public OkCancelButtonPanel getOkCancelButtonPanel() {
		if (okCancelButtonPanel == null){
			ActionListener okAL = new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().okPressed();
				}
			};
			ActionListener cancelAL = new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					getModelUI().cancelPressed();
				}
			};
			okCancelButtonPanel = new OkCancelButtonPanel(okAL, cancelAL);
		}
		return okCancelButtonPanel;
	}

	public void setModelUI(IAuthorizationModelUI<?> modelUI) {
		this.modelUI = modelUI;		
	}

//	public KASComboBox getCmbLanguage() {
//		if (cmbLanguage == null) {
//			cmbLanguage = UIFactory.createKasComboBox20(DEFAULT_CONTROLS_LENGTH);
//			cmbLanguage.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					getModelUI().switchLanguage();
//				}
//			});
//		}
//		return cmbLanguage;
//	}

	public JLabel getLblUserName() {
		if (lblUserName == null) {
			lblUserName = new JLabel(uic.USER_NAME());
			lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblUserName;
	}

	public JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel(uic.PASSWORD());
			lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblPassword;
	}

	public JLabel getLblLanguage() {
		if (lblLanguage == null) {
			lblLanguage = new JLabel(uic.LANGUAGE());
			lblLanguage.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblLanguage;
	}
}