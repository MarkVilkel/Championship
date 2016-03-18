/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fighter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.ui.app.fighter.model.FighterSearchResultModelUI;
import com.ashihara.ui.app.fighter.view.FighterSearchResultPanelViewUI;
import com.ashihara.ui.core.dialog.DialogCallBackListener;
import com.ashihara.ui.core.dialog.KASDialog;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class FighterSearchDialog extends KASDialog {

	private static final long serialVersionUID = 1L;
	
	public static final String SELECTED_FIGHTERS = "SELECTED_FIGHTERS";
	
	private FighterSearchResultModelUI fighterSearchResultModelUI;
	private final List<Fighter> exceptFighters;
	
	public FighterSearchDialog(List<Fighter> exceptFighters, DialogCallBackListener dialogCallBackListener) {
		super(ApplicationManager.getInstance().getUic().SEARCH_FIGHTERS(), dialogCallBackListener);
		this.exceptFighters = exceptFighters;
		
		
		getMainPanel().add(getFighterSearchResultModelUI().getViewUI(), BorderLayout.CENTER);
		
		UIFactory.sizeCenterVisible(this, new Dimension(800, 600));
	}

	public FighterSearchResultModelUI getFighterSearchResultModelUI() {
		if (fighterSearchResultModelUI == null) {
			fighterSearchResultModelUI = new FighterSearchResultModelUI(FighterSearchResultPanelViewUI.SELECT_MODE, exceptFighters);
		}
		return fighterSearchResultModelUI;
	}
	
	protected Map<String, Object> getDialogCallBackListenerParams() {
		Map<String, Object> paramsMap = super.getDialogCallBackListenerParams();
		paramsMap.put(SELECTED_FIGHTERS, getFighterSearchResultModelUI().getSelectedFighters());
		return paramsMap;
	}
}
