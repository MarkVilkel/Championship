/**
 * The file GroupEditModel.java was created on 2010.11.4 at 12:51:24
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.championship.data.RulesManagerFactory;
import com.ashihara.ui.app.group.view.GroupEditPanelView;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.component.combo.ComboBoxItem;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class GroupEditModel extends AKAbstractModelUI<GroupEditPanelView> implements IGroupEditModel<GroupEditPanelView> {
	
	private FightingGroup fightingGroup;
	private GroupEditPanelView viewUI;
	private AbstractFightSystemModel fightSystemModel;
	private FightSettings fightSettings;
	private final UIStatePerformer<FightingGroup> groupsManager;
	private final RulesManager rulesManager;
	
	public GroupEditModel(FightingGroup fightingGroup, UIStatePerformer<FightingGroup> groupsManager) {
		this.fightingGroup = fightingGroup;
		this.groupsManager = groupsManager;
		this.rulesManager = RulesManagerFactory.getRulesManager(fightingGroup.getChampionship().getRules(), uic);
		this.viewUI = new GroupEditPanelView(AKUIEventSender.newInstance(this), rulesManager);
		
		this.viewUI.getModelUI().reset();
	}

	@Override
	public GroupEditPanelView getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(GroupEditPanelView viewUI) {
		this.viewUI = viewUI;
	}

	public void setFightingGroup(FightingGroup fightingGroup) {
		this.fightingGroup = fightingGroup;
	}

	public FightingGroup getFightingGroup() {
		return fightingGroup;
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void reset() {
		try {
			doReset();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	private void doReset() throws PersistenceException {
		reloadCombos();
		setFightingGroup(getGroupService().reload(getFightingGroup()));
		
		
		if (fightSystemModel == null) {
			if (SC.GROUP_TYPE.OLYMPIC.equals(getFightingGroup().getType())) {
				fightSystemModel = new OlympicSystemModel(getFightingGroup(), rulesManager);
			}
			else if (SC.GROUP_TYPE.ROUND_ROBIN.equals(getFightingGroup().getType())) {
				fightSystemModel = new TableRoundRobinSystemModel(getFightingGroup(), rulesManager);
			}
			else {
				fightSystemModel = null;
			}
		}
		
		fightSettings = getFightSettingsService().load();
		
		
		if (getAbstractFightSystemModel() != null) {
			getAbstractFightSystemModel().setGroup(getFightingGroup());
			getAbstractFightSystemModel().reset();
		}
		
		copyOnUI();
		
		if (groupsManager != null) {
			groupsManager.performUIState(getFightingGroup());
		}
	}

	private void reloadCombos() {
		ComboUIHelper.reloadGroupTypeCombo(getViewUI().getCmbType(), appManager.getUic(), true);
		ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getCmbGender(), new SC.GENDER(), true, uic);
	}

	private void copyOnUI() {
		boolean isTypeEnabled = getFightingGroup().getType() == null;
		
		if (!isTypeEnabled && getAbstractFightSystemModel() != null) {
			getViewUI().getTournamentActionCenterPanel().removeAll();
			getViewUI().getTournamentActionCenterPanel().add((Component)getAbstractFightSystemModel().getViewUI(), BorderLayout.CENTER);
		}
		
		if (SC.GROUP_STATUS.INITIAL.equals(getFightingGroup().getStatus())) {
			getViewUI().getCmbType().setEnabled(isTypeEnabled);
			getViewUI().getCmbGender().setEnabled(isTypeEnabled);
			getViewUI().getBtnStart().setVisible(!isTypeEnabled);
		}
		else if (SC.GROUP_STATUS.STARTED.equals(getFightingGroup().getStatus())) {
			
			getViewUI().getBtnStart().setEnabled(false);
			getViewUI().getCmbType().setEnabled(false);
			getViewUI().getCmbGender().setEnabled(false);
			getViewUI().getTxtName().setEnabled(false);
			getAbstractFightSystemModel().setVisible(false);
			
			if (SC.GROUP_TYPE.OLYMPIC.equals(getFightingGroup().getType())) {
				getViewUI().getSaveCancelResetButtonPanel().getBtnSave().setEnabled(false);
			}
		}
		
		getViewUI().getTxtName().setText(getFightingGroup().getName());
		getViewUI().getLblTatami().setText(fightSettings.getTatami());
		
		if (getFightingGroup().getType() != null) {
			getViewUI().getCmbType().tryToSelectIfInEntrySet(getFightingGroup().getType());
		}
		else {
			getViewUI().getCmbType().tryToSelectIfInEntrySet(SC.GROUP_TYPE.OLYMPIC);
		}
		
		if (getFightingGroup().getGender() != null) {
			getViewUI().getCmbGender().tryToSelectIfInEntrySet(getFightingGroup().getGender());
		}
		else {
			getViewUI().getCmbGender().tryToSelectIfInEntrySet(SC.GENDER.MALE);
		}
		
		getViewUI().getTxtWeightCategory().setText(getFightingGroup().getYearWeightCategoryLink().getWeightCategory().toString());
		getViewUI().getTxtYearCategory().setText(getFightingGroup().getYearWeightCategoryLink().getYearCategory().toString());
	}

	@Override
	public void save() {
		try {
			doSave();
			doReset();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private void validateData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtName(), uic.GROUP_NAME());
		Validator.validateMandatoryComponent(getViewUI().getCmbType(), uic.TYPE());
		Validator.validateMandatoryComponent(getViewUI().getCmbGender(), uic.GENDER());
	}

	private void copyFromUI() {
		getFightingGroup().setName(getViewUI().getTxtName().getText());
		Object selectedType = null;
		if (getViewUI().getCmbType().getSelectedItem() instanceof ComboBoxItem) {
			selectedType = getViewUI().getCmbType().getSelectedItem();
		}
		getFightingGroup().setType(((ComboBoxItem<String>) selectedType).getId());
		
		Object selectedGender = null;
		if (getViewUI().getCmbGender().getSelectedItem() instanceof ComboBoxItem) {
			selectedGender = getViewUI().getCmbGender().getSelectedItem();
		}
		getFightingGroup().setGender(((ComboBoxItem<String>) selectedGender).getId());
		
		getFightingGroup().setTatami(fightSettings.getTatami());
	}

	public AbstractFightSystemModel getAbstractFightSystemModel() {
		return fightSystemModel;
	}

	@Override
	public void start() {
		try {
			doSave();
			getFightingGroup().setStatus(SC.GROUP_STATUS.STARTED);
			doSave();
			if (getAbstractFightSystemModel() != null) {
				getAbstractFightSystemModel().startGroup();
			}
			doReset();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private void doSave() throws PersistenceException, AKValidationException {
		validateData();
		copyFromUI();
		setFightingGroup(getGroupService().save(getFightingGroup()));
		if (getAbstractFightSystemModel() != null) {
			getAbstractFightSystemModel().save();
		}
	}

	@Override
	public void reloadTournamentResultTable() {
		try {
			final List<FighterPlace> fighterPlaces;
			if (getAbstractFightSystemModel() != null) {
				fighterPlaces = getAbstractFightSystemModel().loadGroupTournamentResults(getFightingGroup());
			}
			else {
				fighterPlaces = new ArrayList<FighterPlace>();
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getViewUI().getFightResultsTable().getTable().getKASModel().setDataRows(fighterPlaces);
				}
			});
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

}
