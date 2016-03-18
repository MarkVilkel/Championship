/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.fighter.FighterDetailsDialog;
import com.ashihara.ui.app.fighter.view.FighterSearchResultPanelViewUI;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.component.combo.ComboBoxItem;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class FighterSearchResultModelUI extends AKAbstractModelUI<FighterSearchResultPanelViewUI> implements IFighterSearchResultModelUI<FighterSearchResultPanelViewUI>, DialogCallBackListener {
	
	private FighterSearchResultPanelViewUI viewUI;
	private Fighter fighter = new Fighter();
	private final List<Fighter> exceptFighters;
	private List<Fighter> selectedFighters = new ArrayList<Fighter>();

	public FighterSearchResultModelUI(){
		this(FighterSearchResultPanelViewUI.EDIT_MODE);
	}
	
	public FighterSearchResultModelUI(String mode){
		this(mode, null);
	}
	
	public FighterSearchResultModelUI(String mode, List<Fighter> exceptFighters){
		super();
		this.exceptFighters = exceptFighters;
		
		viewUI = new FighterSearchResultPanelViewUI(AKUIEventSender.newInstance(this), mode);
		
		viewUI.init();
		clear();
		viewUI.getModelUI().search();
	}
	
	public FighterSearchResultPanelViewUI getViewUI() {
		return viewUI;
	}
	
	public void search() {
		try {
			copyFromUI();
			List<Fighter> result = getFighterService().searchByPattern(getFighter(), exceptFighters); 
			getViewUI().getFighterTable().getTable().getKASModel().setDataRows(result);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getFighterTable().resetHeaderCenterResultCaption(getViewUI().getFighterSearchPanel().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setFighter(new Fighter());
		copyOnUI();
		getViewUI().getFighterTable().getTable().getKASModel().clear();
		getViewUI().getFighterTable().resetHeaderCenterResultCaption(null);
		
		ComboUIHelper.fillUpCaptionableWithCMItems(getViewUI().getFighterSearchPanel().getCmbGender(), new SC.GENDER(), true, uic);
		try {
			ComboUIHelper.reloadCountryCombo(getViewUI().getFighterSearchPanel().getCmbCountry(), true);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}
	
	
	public void deleteSelectedTableRows() {
		List<Fighter> list = getViewUI().getFighterTable().getTable().getKASModel().getDataDeleted();
		try {
			getFighterService().deleteFighters(list);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
			search();
		}
	}

	private void copyOnUI(){
		getViewUI().getFighterSearchPanel().getTxtName().setText(getFighter().getName());
		getViewUI().getFighterSearchPanel().getTxtSurname().setText(getFighter().getSurname());
		getViewUI().getFighterSearchPanel().getCmbCountry().setSelectedItem(getFighter().getCountry());
		getViewUI().getFighterSearchPanel().getCmbGender().setSelectedItem(getFighter().getGender());
	}
	
	private void copyFromUI(){
		getFighter().setName(getViewUI().getFighterSearchPanel().getTxtName().getText());
		getFighter().setSurname(getViewUI().getFighterSearchPanel().getTxtSurname().getText());
		Country country = null;
		if (getViewUI().getFighterSearchPanel().getCmbCountry().getSelectedItem() instanceof Country) {
			country = (Country)getViewUI().getFighterSearchPanel().getCmbCountry().getSelectedItem();
		}
		
		String gender = null;
		if (getViewUI().getFighterSearchPanel().getCmbGender().getSelectedItem() instanceof ComboBoxItem) {
			gender = (String)((ComboBoxItem)getViewUI().getFighterSearchPanel().getCmbGender().getSelectedItem()).getId();
		}

		getFighter().setCountry(country);
		getFighter().setGender(gender);
	}

	private Fighter getFighter() {
		return fighter;
	}

	private void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}

	public void onAddTableRows(Integer countToAdd) {
		appManager.openDialog(FighterDetailsDialog.class, new Fighter(), this);
	}

	public void onLinkClicked(Fighter value, String columnId) {
		if (uic.NAME().equals(columnId)){
			appManager.openDialog(FighterDetailsDialog.class, value, this);
		}
	}

	public void dialogClosed(Class<?> dialog, Map<String, Object> map) {
		if (dialog == FighterDetailsDialog.class){
			getViewUI().getModelUI().search();
		}
	}

	public void setViewUI(FighterSearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public List<Fighter> getSelectedFighters() {
		return selectedFighters;
	}

	@Override
	public void selectFighters() {
		getViewUI().disposeParent();
		selectedFighters = getViewUI().getFighterTable().getTable().getSelectedObjects(); 
	}

	@Override
	public void importFighters(File file) {
		try {
			getFighterService().importFighters(file);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
	}

}