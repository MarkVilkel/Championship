/**
 * The file StudentDetailsDialog.java was created on 2008.30.1 at 23:57:04
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.plan.view;

import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.enums.CM;
import com.ashihara.ui.app.championship.view.ChampionshipEditPanelViewUI.GroupTypeRenderer;
import com.ashihara.ui.app.fight.view.GradientPanel;
import com.ashihara.ui.app.fighter.view.GenderRenderer;
import com.ashihara.ui.app.plan.model.IFightingGroupSearchModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.OkCancelButtonPanel;
import com.ashihara.ui.core.panel.SimpleTablePanel;
import com.ashihara.ui.core.renderer.KASSmallHeaderRenderer;
import com.ashihara.ui.core.table.KASColumn;

public class FightingGroupSearchPanelViewUI extends GradientPanel implements UIView<IFightingGroupSearchModelUI>{
	private static final long serialVersionUID = 1L;
	
	private IFightingGroupSearchModelUI modelUI;
	private OkCancelButtonPanel okCancelButtonPanel;
	private SimpleTablePanel<FightingGroup> groupTable;
	
	public FightingGroupSearchPanelViewUI(IFightingGroupSearchModelUI modelUI){
		this.modelUI = modelUI;
		
		init();
	}

	private void init() {
		add(getGroupTable(), BorderLayout.CENTER);
		add(getOkCancelButtonPanel(), BorderLayout.SOUTH);
	}


	public OkCancelButtonPanel getOkCancelButtonPanel() {
		if (okCancelButtonPanel == null) {
			okCancelButtonPanel = new OkCancelButtonPanel(
					(e) -> modelUI.ok(),
					(e) -> modelUI.cancel()
			);
			updateOkBtn();
		}
		return okCancelButtonPanel;
	}

	@Override
	public IFightingGroupSearchModelUI getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IFightingGroupSearchModelUI modelUI) {
		this.modelUI = modelUI;
	}
	
	public SimpleTablePanel<FightingGroup> getGroupTable() {
		if (groupTable == null) {
			groupTable = new SimpleTablePanel<>(FightingGroup.class);

			CM.FightingGroup cmGroup = new CM.FightingGroup();
			
			groupTable.getTable().getTableHeader().setDefaultRenderer(new KASSmallHeaderRenderer());
			
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.TITLE(), cmGroup.getName()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.TYPE(), cmGroup.getType(), new GroupTypeRenderer()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.GENDER(), cmGroup.getGender(), new GenderRenderer()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.YEAR_CATEGORY(), cmGroup.getYearWeightCategoryLink().getYearCategory()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.WEIGHT_CATEGORY(), cmGroup.getYearWeightCategoryLink().getWeightCategory()));
			groupTable.getTable().getKASModel().addColumn(new KASColumn(uic.TATAMI(), cmGroup.getTatami()));
			
			groupTable.showRowCount(uic.GROUPS_COUNT());
			
			groupTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					updateOkBtn();
				}
			});
		}
		
		return groupTable;
	}

	private void updateOkBtn() {
		getOkCancelButtonPanel().getBtnOk().setEnabled(getGroupTable().getTable().getSingleSelectedRow() != null);		
	}

}