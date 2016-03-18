/**
 * The file FighterDetailsModelUI.java was created on 2009.8.1 at 17:06:53
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.points.model;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.ui.app.points.view.PointsDetailsPanelViewUI;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class PointsDetailsModelUI extends AKAbstractModelUI<PointsDetailsPanelViewUI> implements IPointsDetailsModelUI<PointsDetailsPanelViewUI>{
	private FightSettings fightSettings;
	private PointsDetailsPanelViewUI viewUI;
	
	public PointsDetailsModelUI() {
		super();
		
		viewUI = new PointsDetailsPanelViewUI(AKUIEventSender.newInstance(this));
		viewUI.getModelUI().reset();
	}
	
	public PointsDetailsPanelViewUI getViewUI() {
		return viewUI;
	}
	
	protected void copyDataFromUI() {
		getFightSettings().setForWinning(Long.valueOf(getViewUI().getTxtForWinning().getText()));
		getFightSettings().setForDraw(Long.valueOf(getViewUI().getTxtForDraw().getText()));
		getFightSettings().setForLoosing(Long.valueOf(getViewUI().getTxtForLoosing().getText()));
		
		getFightSettings().setFirstRoundTime(Long.valueOf(getViewUI().getTxtFirstRoundTime().getText()));
		getFightSettings().setSecondRoundTime(Long.valueOf(getViewUI().getTxtSecondRoundTime().getText()));
		getFightSettings().setOtherRoundTime(Long.valueOf(getViewUI().getTxtOtherRoundTime().getText()));
		
		getFightSettings().setTatami(getViewUI().getTxtTatami().getText());
	}

	protected void copyDataOnUI() {
		getViewUI().getTxtForWinning().setText(getFightSettings().getForWinning().toString());
		getViewUI().getTxtForDraw().setText(getFightSettings().getForDraw().toString());
		getViewUI().getTxtForLoosing().setText(getFightSettings().getForLoosing().toString());
		
		getViewUI().getTxtFirstRoundTime().setText(getFightSettings().getFirstRoundTime().toString());
		getViewUI().getTxtSecondRoundTime().setText(getFightSettings().getSecondRoundTime().toString());
		getViewUI().getTxtOtherRoundTime().setText(getFightSettings().getOtherRoundTime().toString());
		
		getViewUI().getTxtTatami().setText(getFightSettings().getTatami());
	}

	protected void  validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtForWinning(), uic.FOR_WINNING());
		Validator.validate255LengthComponent(getViewUI().getTxtForWinning(), uic.FOR_WINNING());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtForDraw(), uic.FOR_DRAW());
		Validator.validate255LengthComponent(getViewUI().getTxtForDraw(), uic.FOR_DRAW());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtForLoosing(), uic.FOR_LOOSING());
		Validator.validate255LengthComponent(getViewUI().getTxtForLoosing(), uic.FOR_LOOSING());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtFirstRoundTime(), uic.FIRST_ROUND_TIME_IN_SECONDS());
		Validator.validate255LengthComponent(getViewUI().getTxtFirstRoundTime(), uic.FIRST_ROUND_TIME_IN_SECONDS());

		Validator.validateMandatoryComponent(getViewUI().getTxtSecondRoundTime(), uic.SECOND_ROUND_TIME_IN_SECONDS());
		Validator.validate255LengthComponent(getViewUI().getTxtSecondRoundTime(), uic.SECOND_ROUND_TIME_IN_SECONDS());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtOtherRoundTime(), uic.OTHER_ROUND_TIME_IN_SECONDS());
		Validator.validate255LengthComponent(getViewUI().getTxtOtherRoundTime(), uic.OTHER_ROUND_TIME_IN_SECONDS());
		
		Validator.validateMandatoryComponent(getViewUI().getTxtTatami(), uic.TATAMI());
		Validator.validate255LengthComponent(getViewUI().getTxtTatami(), uic.TATAMI());
		

		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtForWinning(), uic.FOR_WINNING());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtForWinning(), uic.FOR_DRAW());
		Validator.validateDataDoubleTypeComponent(getViewUI().getTxtForLoosing(), uic.FOR_LOOSING());
		
		Validator.validateDataIntegerTypeComponent(getViewUI().getTxtFirstRoundTime(), uic.FIRST_ROUND_TIME_IN_SECONDS());
		Validator.validateDataIntegerTypeComponent(getViewUI().getTxtSecondRoundTime(), uic.SECOND_ROUND_TIME_IN_SECONDS());
		Validator.validateDataIntegerTypeComponent(getViewUI().getTxtOtherRoundTime(), uic.OTHER_ROUND_TIME_IN_SECONDS());
	}

	public void setViewUI(PointsDetailsPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			setFightSettings(getFightSettingsService().load());

			copyDataOnUI();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void save() {
		try {
			validateData();
			copyDataFromUI();
			getFightSettingsService().save(getFightSettings());
			cancel();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	public FightSettings getFightSettings() {
		return fightSettings;
	}

	public void setFightSettings(FightSettings fightSettings) {
		this.fightSettings = fightSettings;
	}
}
