/**
 * The file StudentDetailsDialog.java was created on 2008.30.1 at 23:57:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.points.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ashihara.ui.app.points.model.IPointsDetailsModelUI;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SaveCancelResetButtonPanel;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PointsDetailsPanelViewUI extends KASPanel implements UIView<IPointsDetailsModelUI>{
	private static final long serialVersionUID = 1L;
	
	private KASTextField txtForWinning, txtForDraw, txtForLoosing, txtFirstRoundTime, txtSecondRoundTime, txtOtherRoundTime, txtTatami;
	private KASPanel detailsPanel;
	private IPointsDetailsModelUI modelUI;
	private SaveCancelResetButtonPanel saveCancelResetButtonPanel;
	
	public PointsDetailsPanelViewUI(IPointsDetailsModelUI modelUI){
		super();
		
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getDetailsUIPanel(), BorderLayout.CENTER);
		add(getSaveCancelResetButtonPanel(), BorderLayout.SOUTH);
	}

	public KASPanel getDetailsUIPanel() {
		if (detailsPanel == null){
			detailsPanel = new KASPanel();
			FormLayout fl = new FormLayout(
					"right:130dlu,4dlu, pref, 20dlu, pref, 4dlu, pref, right:20dlu, pref, 4dlu, pref",
					"pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 10dlu, pref");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, detailsPanel);
			
			builder.addSeparator("  " + uic.FIGHT_SETTINGS(), cc.xyw(1, 1, 7));
			
			builder.addLabel(uic.POINTS_FOR_WINNING()+": ", cc.xy(1, 3));
			builder.add(getTxtForWinning(), cc.xy(3, 3));
			
			builder.addLabel(uic.POINTS_FOR_DRAW()+": ", cc.xy(1, 5));
			builder.add(getTxtForDraw(), cc.xy(3, 5));
			
			builder.addLabel(uic.POINTS_FOR_LOOSING()+": ", cc.xy(1, 7));
			builder.add(getTxtForLoosing(), cc.xy(3, 7));
			
			builder.addLabel(uic.FIRST_ROUND_TIME_IN_SECONDS()+": ", cc.xy(1, 9));
			builder.add(getTxtFirstRoundTime(), cc.xy(3, 9));
			
			builder.addLabel(uic.SECOND_ROUND_TIME_IN_SECONDS()+": ", cc.xy(1, 11));
			builder.add(getTxtSecondRoundTime(), cc.xy(3, 11));

			builder.addLabel(uic.OTHER_ROUND_TIME_IN_SECONDS()+": ", cc.xy(1, 13));
			builder.add(getTxtOtherRoundTime(), cc.xy(3, 13));

			builder.addLabel(uic.TATAMI()+": ", cc.xy(1, 15));
			builder.add(getTxtTatami(), cc.xy(3, 15));
		}
		return detailsPanel;
	}

	public KASTextField getTxtForWinning() {
		if (txtForWinning == null){
			txtForWinning = UIFactory.createKASTextField();
			txtForWinning.setMaxSymbolsCount(255);
			txtForWinning.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtForWinning;
	}

	public KASTextField getTxtForDraw() {
		if (txtForDraw == null){
			txtForDraw = UIFactory.createKASTextField();
			txtForDraw.setMaxSymbolsCount(255);
			txtForDraw.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtForDraw;
	}

	public IPointsDetailsModelUI getModelUI() {
		return modelUI;
	}

	public void setModelUI(IPointsDetailsModelUI modelUI) {
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

	public KASTextField getTxtForLoosing() {
		if (txtForLoosing == null) {
			txtForLoosing = UIFactory.createKASTextField();
			txtForLoosing.setMaxSymbolsCount(255);
			txtForLoosing.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtForLoosing;
	}

	public KASTextField getTxtFirstRoundTime() {
		if (txtFirstRoundTime == null) {
			txtFirstRoundTime = UIFactory.createKASTextField();
			txtFirstRoundTime.setMaxSymbolsCount(255);
			txtFirstRoundTime.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtFirstRoundTime;
	}

	public KASTextField getTxtSecondRoundTime() {
		if (txtSecondRoundTime == null) {
			txtSecondRoundTime = UIFactory.createKASTextField();
			txtSecondRoundTime.setMaxSymbolsCount(255);
			txtSecondRoundTime.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtSecondRoundTime;
	}

	public KASTextField getTxtOtherRoundTime() {
		if (txtOtherRoundTime == null) {
			txtOtherRoundTime = UIFactory.createKASTextField();
			txtOtherRoundTime.setMaxSymbolsCount(255);
			txtOtherRoundTime.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtOtherRoundTime;
	}

	public KASTextField getTxtTatami() {
		if (txtTatami == null) {
			txtTatami = UIFactory.createKASTextField();
			txtTatami.setMaxSymbolsCount(255);
			txtTatami.addKeyListener(new EnterEscapeKeyAdapter(getSaveCancelResetButtonPanel().getBtnSave(), getSaveCancelResetButtonPanel().getBtnCancel()));
		}
		return txtTatami;
	}
}