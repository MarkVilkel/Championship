/**
 * The file ChampionshipResultPanelViewUI.java was created on 2011.17.10 at 22:24:29
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.championship.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.model.IChampionshipGroupPlaceReportModelUI;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.SearchClearButtonPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.core.table.LinkClickingListener;
import com.ashihara.ui.tools.UIFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ChampionshipGroupPlaceReportViewUI extends KASPanel implements UIView<IChampionshipGroupPlaceReportModelUI<?>>, LinkClickingListener<FighterPlace>{

	private static final long serialVersionUID = 1L;
	
	private IChampionshipGroupPlaceReportModelUI<?> modelUI;
	
	private ChampionshipReportTable fightResultPanel;
	private SearchClearButtonPanel searchClearButtonPanel;
	private KASPanel searchPanelDetails;
	private KASComboBox cmbGroups;
	
	public ChampionshipGroupPlaceReportViewUI(IChampionshipGroupPlaceReportModelUI<?> modelUI) {
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getSearchClearButtonPanel(), BorderLayout.NORTH);
		add(getFightResultPanel(), BorderLayout.CENTER);
	}

	@Override
	public IChampionshipGroupPlaceReportModelUI<?> getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IChampionshipGroupPlaceReportModelUI<?> modelUI) {
		this.modelUI = modelUI;
	}
	
	public class ChampionshipReportTable extends SimpleTablePanel<FighterPlace> {
		private static final long serialVersionUID = 1L;

		public ChampionshipReportTable() {
			super(FighterPlace.class);
		}
	}

	public ChampionshipReportTable getFightResultPanel() {
		if (fightResultPanel == null) {
			fightResultPanel = new ChampionshipReportTable();
			
			fightResultPanel.getTable().addLinkClickingListener(this);
			
			CM.FighterPlace cmFighterPlace = new CM.FighterPlace(); 
			
			fightResultPanel.getTable().getKASModel().addColumn(KASColumn.createLinkColumn(uic.GROUP(), cmFighterPlace.getGCFighter().getFightingGroup()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.FIGHTER(), cmFighterPlace.getGCFighter().getChampionshipFighter()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.COUNTRY(), cmFighterPlace.getGCFighter().getChampionshipFighter().getFighter().getCountry()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.FIRST_CATEGORY(), cmFighterPlace.getFirstCategoryWarnings()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.SECOND_CATEGORY(), cmFighterPlace.getSecondCategoryWarnings()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.POINTS(), cmFighterPlace.getPoints()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.RESULT_SCORE(), cmFighterPlace.getPointsForWin()));
			fightResultPanel.getTable().getKASModel().addColumn(new KASColumn(uic.PLACE(), cmFighterPlace.getPlace()));
		
			fightResultPanel.showRowCount(uic.RESULTS_COUNT());
		}
		return fightResultPanel;
	}

	private SearchClearButtonPanel getSearchClearButtonPanel() {
		if (searchClearButtonPanel == null) {
			searchClearButtonPanel = new SearchClearButtonPanel(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getModelUI().clear();
						}
					},
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							getModelUI().filter();
						}
					}
			);
			
			searchClearButtonPanel.setDetailsPanel(getSearchPanelDetails(), true);
		}
		return searchClearButtonPanel;
	}
	
	public KASPanel getSearchPanelDetails() {
		if (searchPanelDetails == null){
			searchPanelDetails = new KASPanel();
			FormLayout fl = new FormLayout(
					"30dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref",
					"pref, 10dlu, pref, 2dlu, pref, 10dlu");

			CellConstraints cc = new CellConstraints();

			PanelBuilder builder = new PanelBuilder(fl, searchPanelDetails);

			builder.addSeparator("  " + uic.SEARCH_BY_CRITERIA(), cc.xyw(1, 1, 4));

			builder.addLabel(uic.GROUP(), cc.xy(2, 3));
			builder.add(getCmbGroups(), cc.xy(2, 5));
			
		}
		
		return searchPanelDetails;
	}

	public KASComboBox getCmbGroups() {
		if (cmbGroups == null) {
			cmbGroups = UIFactory.createKasComboBox20(DEFAULT_CONTROLS_LENGTH);
			cmbGroups.addKeyListener(new EnterEscapeKeyAdapter(getSearchClearButtonPanel().getBtnSearch(), getSearchClearButtonPanel().getBtnClear()));
		}
		return cmbGroups;
	}

	@Override
	public void linkClicked(FighterPlace value, String columnId) {
		getModelUI().linkClicked(value, columnId);
	}

}
