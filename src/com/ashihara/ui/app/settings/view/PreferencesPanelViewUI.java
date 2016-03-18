/**
 * The file PreferencesPanelViewUI.java was created on 2008.22.9 at 10:04:22
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.settings.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.ashihara.ui.app.settings.model.IPreferencesModelUI;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.OkCancelResetButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PreferencesPanelViewUI extends KASPanel implements UIView<IPreferencesModelUI>{
	private static final long serialVersionUID = 1L;
	
	private KASComboBox cmbLookAndFeel, cmbTheme;
	
	protected KASPanel detailsPanel, testPanel;
	private PanelBuilder detailsPanelBuilder;
	private CellConstraints detailsPanelCC;
	
	private OkCancelResetButtonPanel okCancelResetButtonPanel;
	
	private IPreferencesModelUI modelUI;
	
	
	public PreferencesPanelViewUI(IPreferencesModelUI modelUI){
		super();
		setModelUI(modelUI);
		init();
	}

	protected void init() {
		detailsPanel = new KASPanel();
		
		CellConstraints cc = getDetailsPanelCC();
		PanelBuilder builder = getDetailsPanelBuilder();
		
		builder.addSeparator("  " + uic.PREFERENCES(), cc.xyw(1, 1, 7));
		
		builder.addLabel(uic.LOOK_AND_FEEL()+": ", cc.xy(2, 3));
		builder.add(getCmbLookAndFeel(), cc.xy(4, 3));
		
		builder.addLabel(uic.THEME()+": ", cc.xy(2, 5));
		builder.add(getCmbTheme(), cc.xy(4, 5));
		
		add(getDetailsPanel(), BorderLayout.NORTH);
		add(getOkCancelResetButtonPanel(), BorderLayout.SOUTH);
	}

	public KASComboBox getCmbLookAndFeel() {
		if (cmbLookAndFeel == null){
			cmbLookAndFeel = new KASComboBox();
			
			cmbLookAndFeel.addKeyListener(new EnterEscapeKeyAdapter(getOkCancelResetButtonPanel().getBtnSave(), getOkCancelResetButtonPanel().getBtnCancel()));
			
			cmbLookAndFeel.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if (ItemEvent.SELECTED == e.getStateChange()) {
						getModelUI().lookAndFeelSelected();
					}
				}
			});
		}
		return cmbLookAndFeel;
	}

	public KASComboBox getCmbTheme() {
		if (cmbTheme == null){
			cmbTheme = new KASComboBox();

			cmbLookAndFeel.addKeyListener(new EnterEscapeKeyAdapter(getOkCancelResetButtonPanel().getBtnSave(), getOkCancelResetButtonPanel().getBtnCancel()));
		}
		return cmbTheme;
	}

	public OkCancelResetButtonPanel getOkCancelResetButtonPanel() {
		if (okCancelResetButtonPanel == null){
			ActionListener okAl = new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getModelUI().ok();
				}
			};
			ActionListener cancelAl = new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getModelUI().cancel();
				}
			};
			ActionListener resetAl = new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					getModelUI().reset();
				}
			};
			okCancelResetButtonPanel = new OkCancelResetButtonPanel(okAl, cancelAl, resetAl);
		}
		return okCancelResetButtonPanel;
	}

	public KASPanel getDetailsPanel() {
		return detailsPanel;
	}

	public KASPanel getTestPanel() {
		if(testPanel == null){
			testPanel = new KASPanel();
			testPanel.add(UIFactory.createAddAllButton());
			testPanel.add(UIFactory.createAddButton());
			testPanel.add(UIFactory.createRemButton());
			testPanel.add(UIFactory.createRemAllButton());
			testPanel.add(UIFactory.createSmallClearButton());
			testPanel.add(UIFactory.createSmallClearButtonNoLabel());
			testPanel.add(UIFactory.createArrowDownPaperButton());
			testPanel.add(UIFactory.createBinocularButton());
			testPanel.add(UIFactory.createCancelButton());
			testPanel.add(UIFactory.createSmallCancelButton());
			testPanel.add(UIFactory.createSmallCancelButtonNoLabel());
			testPanel.add(UIFactory.createSmallBackButton());
			testPanel.add(UIFactory.createExcelButton());
			testPanel.add(UIFactory.createNoBinocularButton());
			testPanel.add(UIFactory.createOkButton());
			testPanel.add(UIFactory.createSmallOkButtonNoLabel());
			testPanel.add(UIFactory.createSmallResetButton());
			testPanel.add(UIFactory.createSmallSaveButton());
			testPanel.add(UIFactory.createSmallSearchButton());
			testPanel.add(UIFactory.createSmallUploadButton());
			testPanel.add(UIFactory.createWordButton());
		}
		return testPanel;
	}

	public IPreferencesModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IPreferencesModelUI modelUI) {
		this.modelUI = modelUI;
	}

	public PanelBuilder getDetailsPanelBuilder() {
		if (detailsPanelBuilder == null) {
			FormLayout fl = new FormLayout(
					"10dlu, right:pref,4dlu, pref, 20dlu, pref, 4dlu,pref, right:20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
			
			detailsPanelBuilder = new PanelBuilder(fl, getDetailsPanel());
		}
		return detailsPanelBuilder;
	}

	public CellConstraints getDetailsPanelCC() {
		if (detailsPanelCC == null) {
			detailsPanelCC = new CellConstraints();
		}
		return detailsPanelCC;
	}
}