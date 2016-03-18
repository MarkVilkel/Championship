/**
 * The file ChampionshipFighterTable.java was created on 2012.14.4 at 16:20:56
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.enums.CM;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.table.KASColumn;

public class ChampionshipFighterTable extends AddDeleteButtonsTablePanel<GroupChampionshipFighter> {
	
	private static final long serialVersionUID = 1L;
	
	private final ChampionshipFighterTableCellEditor championshipFighterTableCellEditor;

	public ChampionshipFighterTable(KASComboBox cmb) {
		
		super(GroupChampionshipFighter.class);
		
		this.championshipFighterTableCellEditor = new ChampionshipFighterTableCellEditor(cmb);
		
		getButtonsPanel().getTxtCountToAdd().setVisible(false);
		
		CM.GroupChampionshipFighter gcf = new CM.GroupChampionshipFighter(); 
		getTable().getKASModel().addColumn(new KASColumn(uic.FIGHTERS(), gcf.getChampionshipFighter(), true, championshipFighterTableCellEditor));
		showRowCount(uic.FIGHTERS_COUNT());
	}

	public ChampionshipFighterTableCellEditor getChampionshipFighterTableCellEditor() {
		return championshipFighterTableCellEditor;
	}
}
