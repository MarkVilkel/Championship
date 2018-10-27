/**
 * The file ChampionshipEditModelUI.java was created on 2010.5.4 at 22:19:18
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.interfaces.MigrationService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.datamanagement.pojo.wraper.DBWrapper;
import com.ashihara.enums.CM;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.championship.ChampionshipEachFightReportFrame;
import com.ashihara.ui.app.championship.ChampionshipGroupPlaceReportFrame;
import com.ashihara.ui.app.championship.view.ChampionshipEditPanelViewUI;
import com.ashihara.ui.app.fighter.FighterDetailsDialog;
import com.ashihara.ui.app.fighter.FighterSearchDialog;
import com.ashihara.ui.app.group.GroupDetailsFrame;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.app.yearCategory.YearCategorySearchDialog;
import com.ashihara.ui.app.yearWeight.YearWeightCategoryLinkSearchDialog;
import com.ashihara.ui.core.component.combo.ComboBoxItem;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.file.CommonAKFileFilter;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.core.validator.Validator;
import com.ashihara.ui.tools.KASDebuger;
import com.ashihara.ui.tools.MessageHelper;
import com.ashihara.ui.tools.TableToExcelExporter;
import com.ashihara.utils.DataManagementUtils;
import com.ashihara.utils.FileUtils;
import com.rtu.exception.PersistenceException;

public class ChampionshipEditModelUI extends AKAbstractModelUI<ChampionshipEditPanelViewUI>
									implements IChampionshipEditModelUI<ChampionshipEditPanelViewUI>, DialogCallBackListener {
	
	private ChampionshipEditPanelViewUI viewUI;
	final private UIStatePerformer<Championship> parentWindow;
	
	private Championship championship;
	private List<ChampionshipFighter> championshipFighters;
	
	public ChampionshipEditModelUI(Championship championship, UIStatePerformer<Championship> parentWindow) {
		super();
		
		this.championship = championship;
		this.viewUI = new ChampionshipEditPanelViewUI(AKUIEventSender.newInstance(this));
		this.parentWindow = parentWindow;
		
		viewUI.getModelUI().fullReload();
	}

	@Override
	public ChampionshipEditPanelViewUI getViewUI() {
		return viewUI;
	}

	@Override
	public void setViewUI(ChampionshipEditPanelViewUI viewUI) {
		this.viewUI = viewUI;
	}

	public Championship getChampionship() {
		return championship;
	}

	public void setChampionship(Championship championship) {
		this.championship = championship;
	}

	@Override
	public void deleteSelectedFighters() {
		try {
			getChampionshipService().delete(getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataCleanDeleted());
//			getChampionshipService().organizeChampionshipFighterNumbers(getChampionship());
			reloadChampionshipFighters();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void cancel() {
		getViewUI().disposeParent();
	}

	@Override
	public void save() {
		try {
			validateChampionshipData();
			
			List<ChampionshipFighter> championshipFighters = getViewUI().getChampionshipFighterTable().getTable().getKASModel().getDataRows();
			validateFighters(championshipFighters);
			
			copyFromUIChampiohshipOnly();
			setChampionship(getChampionshipService().saveChampionship(getChampionship()));
			
			championshipFighters = getChampionshipService().saveChampionshipFighters(championshipFighters);
			
			fullReload();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKValidationException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	private void validateFighters(List<ChampionshipFighter> fighters) throws AKValidationException {
		Validator.validateMandatoryList(fighters, new CM.ChampionshipFighter().getNumber(), uic.NUMBER());
		Validator.validateLongPositiveList(fighters, new CM.ChampionshipFighter().getNumber(), uic.NUMBER());
		
		if (fighters.size() > 0) {
			for (int i = 0; i < fighters.size() - 1; i++) {
				for (int k = i + 1; k < fighters.size(); k++) {
					ChampionshipFighter fi = fighters.get(i);
					ChampionshipFighter fk = fighters.get(k);
					if (fi.getNumber().equals(fk.getNumber())) {
						String msg = uic.FIGHTERS_HAVE_THE_SAME_NUMBERS();
						msg = MessageFormat.format(msg, fi.toString(), fk.toString(), fk.getNumber());
						
						throw new AKValidationException(msg);
					}
				}
			}
		}
	}

	@Override
	public void fullReload() {
		try {
			clearFightersFilter();
			clearYearWeightCriteria();
			
			reloadChampionship();
			reloadChampionshipFighters();
			reloadGroups();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		
	}
	
	private void reloadGroups() throws PersistenceException {
		if (getViewUI() == null) {
			return;
		}
		
		YearWeightCategoryLink pattern = new YearWeightCategoryLink();
		
		Object weight = getViewUI().getYearWeightSearchPanel().getCmbWeightCategory().getSelectedItem();
		if (weight instanceof WeightCategory) {
			pattern.setWeightCategory((WeightCategory)weight);
		}
		Object year = getViewUI().getYearWeightSearchPanel().getCmbYearCategory().getSelectedItem();
		if (year instanceof YearCategory) {
			pattern.setYearCategory((YearCategory)year);
		}

		getViewUI().getGroupTable().getTable().getKASModel().setDataRows(getGroupService().filterGroups(getChampionship(), pattern));
	}

	private void reloadChampionshipFighters() throws PersistenceException {
		Fighter pattern = new Fighter();
		pattern.setName(getViewUI().getFighterSearchPanel().getTxtName().getText());
		pattern.setSurname(getViewUI().getFighterSearchPanel().getTxtSurname().getText());
		Object country = getViewUI().getFighterSearchPanel().getCmbCountry().getSelectedItem();
		if (country instanceof Country) {
			pattern.setCountry((Country)country);
		}

		Object gender = getViewUI().getFighterSearchPanel().getCmbGender().getSelectedItem();
		if (gender instanceof ComboBoxItem) {
			pattern.setGender((String)((ComboBoxItem)gender).getId());
		}
		
		setChampionshipFighters(getChampionshipService().loadChampionshipFighters(getChampionship(), pattern));
		copyOnUIFighters();
	}

	private void reloadChampionship() throws PersistenceException {
		
		ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getCmbRules(), new SC.RULES(), false, uic);
		
		
		if (getChampionship() == null || getChampionship().getId() == null) {
			setChampionship(new Championship());
		}
		else {
			setChampionship(getChampionshipService().reloadChampionship(getChampionship()));
		}
		
		if (getParentWindow() != null) {
			getParentWindow().performUIState(getChampionship());
		}
		
		copyOnUIChampionshipOnly();
	}
	
	private void copyOnUIFighters() {
		getViewUI().getChampionshipFighterTable().getTable().getKASModel().setDataRows(getChampionshipFighters());
	}

	private void copyOnUIChampionshipOnly() {
		boolean showReportBtns = championship != null && championship.getId() != null;
		getViewUI().getBtnEachFighterReport().setVisible(showReportBtns);
		getViewUI().getBtnGroupPlaceReport().setVisible(showReportBtns);
		
		getViewUI().getTxtName().setText(getChampionship().getName());
		getViewUI().getDateChooser().setDate(getChampionship().getBeginningDate());
		
		getViewUI().getCmbRules().tryToSelectIfInEntrySet((getChampionship() == null || getChampionship().getRules() == null) ? SC.RULES.NIKO_STYLE : getChampionship().getRules());
		
		boolean visibleFlag = !(getChampionship() == null || getChampionship().getId() == null);
		getViewUI().getChampionshipFighterTablePanel().setEnabled(visibleFlag);
		getViewUI().getGroupTablePanel().setEnabled(visibleFlag);
		getViewUI().getCmbRules().setEnabled(!visibleFlag);
	}
	
	private void copyFromUIChampiohshipOnly() {
		getChampionship().setName(getViewUI().getTxtName().getText());
		getChampionship().setBeginningDate(getViewUI().getDateChooser().getDate());
		if (getViewUI().getCmbRules().getSelectedItem() instanceof ComboBoxItem) {
			getChampionship().setRules(((ComboBoxItem<String>)getViewUI().getCmbRules().getSelectedItem()).getId());
		}
	}

	private void validateChampionshipData() throws AKValidationException {
		Validator.validateMandatoryComponent(getViewUI().getTxtName(), uic.NAME());
		Validator.validateMandatoryComponent(getViewUI().getDateChooser(), uic.BEGINNING_DATE());
	}

	public UIStatePerformer<Championship> getParentWindow() {
		return parentWindow;
	}

	@Override
	public void addGroupsByYearCategory() {
		List<YearCategory> exceptYearCategories = new ArrayList<YearCategory>();//.getYearCategoriesFromList(getViewUI().getGroupTable().getTable().getKASModel().getDataRows());
		appManager.openDialog(YearCategorySearchDialog.class, exceptYearCategories, this);
	}

	@Override
	public void deleteSelectedGroups() {
		try {
			getGroupService().deleteGroups(getViewUI().getGroupTable().getTable().getKASModel().getDataCleanDeleted());
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}		
	}

	public List<ChampionshipFighter> getChampionshipFighters() {
		return championshipFighters;
	}

	public void setChampionshipFighters(List<ChampionshipFighter> championshipFighters) {
		this.championshipFighters = championshipFighters;
	}

	@Override
	public void addFighters() {
		List<Fighter> exceptFighters = getChampionshipService().getFightersFromList(getChampionshipFighters());
		appManager.openDialog(FighterSearchDialog.class, exceptFighters, this);
	}

	@Override
	public void dialogClosed(Class<?> dialog, Map<String, Object> dialogParams) {
		try {
			if (dialog == FighterSearchDialog.class) {
				List<Fighter> selectedFighters = (List<Fighter>)dialogParams.get(FighterSearchDialog.SELECTED_FIGHTERS);
				getChampionshipService().createChampionshipFighters(getChampionship(), selectedFighters);
				reloadChampionshipFighters();
			}
			else if (dialog == FighterDetailsDialog.class) {
				reloadChampionshipFighters();
			}
			else if (dialog == YearCategorySearchDialog.class) {
				List<YearCategory> selectedYearCategories = (List<YearCategory>)dialogParams.get(YearCategorySearchDialog.SELECTED_YEAR_CATEGORIES);
				getGroupService().createGroupsYear(getChampionship(), selectedYearCategories);
				reloadGroups();
			}
			else if (dialog == YearWeightCategoryLinkSearchDialog.class) {
				List<YearWeightCategoryLink> selectedYearWeightCategoryLink = (List<YearWeightCategoryLink>)dialogParams.get(YearWeightCategoryLinkSearchDialog.SELECTED_CATEGORIES);
				getGroupService().createGroupsByYearWeight(getChampionship(), selectedYearWeightCategoryLink);
				reloadGroups();
			}
			
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		} catch (AKBusinessException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void linkClickedOnChampionshipFighter(ChampionshipFighter value, String columnId) {
		if (uic.NAME().equals(columnId)){
			appManager.openDialog(FighterDetailsDialog.class, value.getFighter(), this);
		}
	}

	@Override
	public void addGroupsByWeightCategory() {
		appManager.openDialog(YearWeightCategoryLinkSearchDialog.class, this);		
	}

	@Override
	public void clearYearWeightCriteria() {
		try {
			ComboUIHelper.reloadWeightCategoryCombo(getViewUI().getYearWeightSearchPanel().getCmbWeightCategory(), true);
			ComboUIHelper.reloadYearCategoryCombo(getViewUI().getYearWeightSearchPanel().getCmbYearCategory(), true);
			ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getFighterSearchPanel().getCmbGender(), new SC.GENDER(), true, uic);

			
			getViewUI().getGroupTable().getTable().getKASModel().clear();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void searchGroupsByYearWeight() {
		try {
			reloadGroups();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void linkClickedOnGroup(FightingGroup value) {
		appManager.openIdentifiedFrame(GroupDetailsFrame.class, value.getId(), value, new UIStatePerformer<FightingGroup>() {
			@Override
			public void performUIState(FightingGroup param) {
				searchGroupsByYearWeight();
			}
		});
	}

	@Override
	public void clearFightersFilter() {
		try {
			ComboUIHelper.reloadCountryCombo(getViewUI().getFighterSearchPanel().getCmbCountry(), true);
			ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getFighterSearchPanel().getCmbGender(), new SC.GENDER(), true, uic);
			
			getViewUI().getFighterSearchPanel().getTxtName().setText("");
			getViewUI().getFighterSearchPanel().getTxtSurname().setText("");
			
			getViewUI().getChampionshipFighterTable().getTable().getKASModel().clear();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void filterFighters() {
		try {
			reloadChampionshipFighters();
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void showGroupPlaceReport() {
		if(championship != null && championship.getId() != null) {
			appManager.openIdentifiedFrame(ChampionshipGroupPlaceReportFrame.class, championship.getId(), championship);
		}
	}

	@Override
	public void showEachFighterReport() {
		if(championship != null && championship.getId() != null) {
			appManager.openIdentifiedFrame(ChampionshipEachFightReportFrame.class, championship.getId(), championship);
		}
	}

	@Override
	public void checkFighters() {
		try {
			List<ChampionshipFighter> fightersNotInGroup = getChampionshipService().loadAllFightersNotInAnyGroup(getChampionship());
			if (fightersNotInGroup.isEmpty()) {
				MessageHelper.showInformtionMessage(getViewUI(), uic.NOBODY_IS_FORGOTTEN_ALL_FIGHTERS_IN_GROUPS());
			}
			else {
				KASDebuger.println(fightersNotInGroup.toString());
				MessageHelper.showErrorMessage(getViewUI(), uic.SOME_FIGHTERS_ARE_NOT_IN_GROUPS() + ":\n" + fightersNotInGroup.toString());
			}
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

	@Override
	public void exportGroups() {
		List<FightingGroup> groups = getViewUI().getGroupTable().getTable().getSelectedObjects();
		exportGroups(groups);
	}

	@Override
	public void importGroups() {
		File file = FileUtils.getOpenPath(new CommonAKFileFilter("db"));
		if (file != null) {
			try {
				byte[] bytes = FileUtils.readFromFile(file);
				DBWrapper dbWrapper = (DBWrapper)DataManagementUtils.toObject(bytes);
				getServerSideServiceFactory().getService(MigrationService.class).saveFightingGroups(dbWrapper);
				MessageHelper.showInformtionMessage(null, uic.DATA_BASE_IMPORTED());
				
				fullReload();
			} catch (Throwable e) {
				MessageHelper.showErrorMessage(null, e);
			}
		}
	}

	private void exportGroups(List<FightingGroup> groups) {
		File file = FileUtils.getSavePath(new CommonAKFileFilter("db"), "LatviaKarateDataBase");
		if (file != null) {
			try {
				
				DBWrapper dbWrapper = getServerSideServiceFactory().getService(MigrationService.class).exportDataBase();
				
				dbWrapper.setFightingGroups(groups);
				dbWrapper.setChampionship(getChampionship());
				
				byte[] bytes = DataManagementUtils.toByteArray(dbWrapper);
				FileUtils.writeToFile(file, bytes);
				MessageHelper.showInformtionMessage(null, uic.DATA_BASE_EXPORTED());
			} catch (Throwable e) {
				MessageHelper.showErrorMessage(null, e);
			}
		}
	}
	
	@Override
	public void exportFinishedGroups() {
		try {
			List<FightingGroup> groups = getGroupService().loadStartedWithRealFightResultsGroups(getChampionship());
			if (groups.isEmpty()) {
				MessageHelper.showInformtionMessage(getViewUI(), uic.NO_FINISHED_GROUPS());
			}
			else {
				exportGroups(groups);
			}
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	@Override
	public void exportAllGroupsToExcel() {
		try {
			List<FightingGroup> groups = getGroupService().loadStartedWithRealFightResultsGroups(getChampionship());
			if (groups != null && !groups.isEmpty()) {
				Map<FightingGroup, List<FightResult>> fightResults = new HashMap<>();
				for (FightingGroup group : groups) {
					final List<FightResult> fr;
					if (SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
						fr = getFightResultService().loadOrCreateOlympicFightResults(group);
					} else if (SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
						fr = getFightResultService().loadOrCreateRoundRobinLastFightResults(group);
					} else {
						throw new IllegalArgumentException("Unsupported group type " + group.getType());
					}
					fightResults.put(group, fr);
				}
				TableToExcelExporter.drawWholeTreeToExcel(fightResults, getChampionship().toString(), uic);
			}
		} catch (Exception e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

}
