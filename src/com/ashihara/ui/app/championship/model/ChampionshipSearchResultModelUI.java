/**
 * The file FighterSearchResultModelUI.java was created on 2009.8.1 at 15:27:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.model;

import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.ui.app.championship.ChampionshipEachFightReportFrame;
import com.ashihara.ui.app.championship.ChampionshipEditFrame;
import com.ashihara.ui.app.championship.ChampionshipGroupPlaceReportFrame;
import com.ashihara.ui.app.championship.view.ChampionshipSearchResultPanelViewUI;
import com.ashihara.ui.app.country.CountryDetailsDialog;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.MessageHelper;
import com.rtu.exception.PersistenceException;

public class ChampionshipSearchResultModelUI extends AKAbstractModelUI<ChampionshipSearchResultPanelViewUI> implements IChampionshipSearchResultModelUI<ChampionshipSearchResultPanelViewUI>, DialogCallBackListener {
	
	private ChampionshipSearchResultPanelViewUI viewUI;
	private Championship pattern;

	public ChampionshipSearchResultModelUI(){
		super();
		viewUI = new ChampionshipSearchResultPanelViewUI(AKUIEventSender.newInstance(this));
		
		viewUI.init();
		clear();
		viewUI.getModelUI().search();
	}
	
	public void search() {
		try {
			copyFromUI();
			
			getViewUI().getChampionshipTable().getTable().getKASModel().setDataRows(
					getChampionshipService().searchByPattern(getPattern()));
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
		}
		getViewUI().getChampionshipTable().resetHeaderCenterResultCaption(getViewUI().getSearchClearButtonPanel().isSearchCriteriaFilled());
	}
	
	public void clear() {
		setPattern(new Championship());
		copyOnUI();
		getViewUI().getChampionshipTable().getTable().getKASModel().clear();
		getViewUI().getChampionshipTable().resetHeaderCenterResultCaption(null);
		
	}
	
	
	public void deleteSelectedTableRows() {
		List<Championship> list = getViewUI().getChampionshipTable().getTable().getKASModel().getDataDeleted();
		try {
			getChampionshipService().deleteChampionships(list);
		} catch (PersistenceException e) {
			MessageHelper.handleException(getViewUI(), e);
			search();
		}
	}

	private void copyOnUI(){
		getViewUI().getTxtName().setText(getPattern().getName());
	}
	
	private void copyFromUI(){
		getPattern().setName(getViewUI().getTxtName().getText());
	}

	public void onLinkClicked(Championship value, String columnId) {
		if (uic.NAME().equals(columnId)){
			appManager.openIdentifiedFrame(ChampionshipEditFrame.class, value.getId(), value);
		}
		else if (uic.GROUP_PLACE_REPORT().equals(columnId)) {
			appManager.openIdentifiedFrame(ChampionshipGroupPlaceReportFrame.class, value.getId(), value);
		}
		else if (uic.EACH_FIGHT_REPORT().equals(columnId)) {
			appManager.openIdentifiedFrame(ChampionshipEachFightReportFrame.class, value.getId(), value);
		}
	}

	public void dialogClosed(Class<?> dialog, Map<String, Object> map) {
		if (dialog == CountryDetailsDialog.class){
			getViewUI().getModelUI().search();
		}
	}

	public void setViewUI(ChampionshipSearchResultPanelViewUI viewUI) {
		this.viewUI = viewUI;		
	}

	public ChampionshipSearchResultPanelViewUI getViewUI() {
		return viewUI;
	}

	public Championship ChampionshipSearchResultPanelViewUI() {
		return pattern;
	}

	public void setPattern(Championship pattern) {
		this.pattern = pattern;
	}

	public Championship getPattern() {
		return pattern;
	}

}