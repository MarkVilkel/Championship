/**
 * The file ChampionshipFighterTable.java was created on 2012.14.4 at 16:20:56
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.enums.CM;
import com.ashihara.ui.core.panel.AddDeleteButtonsTablePanel;
import com.ashihara.ui.core.table.KASColumn;
import com.ashihara.ui.tools.UIFactory;

public class ChampionshipFighterTable extends AddDeleteButtonsTablePanel<GroupChampionshipFighter> {
	
	private static final long serialVersionUID = 1L;
	
	private final ChampionshipFighterTableCellEditor championshipFighterTableCellEditor;
	
	private final ChampionshipFighterProvider championshipFighterProvider;
	
	private JButton btnAddAllSuitable;

	public ChampionshipFighterTable(ChampionshipFighterProvider championshipFighterProvider) {
		
		super(GroupChampionshipFighter.class);
		
		this.championshipFighterProvider = championshipFighterProvider;
		
		this.championshipFighterTableCellEditor = new ChampionshipFighterTableCellEditor(championshipFighterProvider);
		
		getButtonsPanel().getTxtCountToAdd().setVisible(false);
		getButtonsPanel().add(getBtnAddAllSuitable());
		
		CM.GroupChampionshipFighter gcf = new CM.GroupChampionshipFighter(); 
		getTable().getKASModel().addColumn(new KASColumn(uic.FIGHTERS(), gcf.getChampionshipFighter(), true, championshipFighterTableCellEditor, new ChampionshipFighterTableCellRenderer()));
		showRowCount(uic.FIGHTERS_COUNT());
	}

	public ChampionshipFighterTableCellEditor getChampionshipFighterTableCellEditor() {
		return championshipFighterTableCellEditor;
	}

	public JButton getBtnAddAllSuitable() {
		if (btnAddAllSuitable == null) {
			btnAddAllSuitable = UIFactory.createPlusButton();
			btnAddAllSuitable.setPreferredSize(new Dimension(60, UIFactory.DEFAULT_BTN_HEIGHT));
			btnAddAllSuitable.setText(uic.ALL());
			btnAddAllSuitable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<ChampionshipFighter> allItems = championshipFighterProvider.getItemsCopy();
					List<GroupChampionshipFighter> addedItems = getTable().getKASModel().getDataRows();
					for (GroupChampionshipFighter gcf : addedItems) {
						allItems.remove(gcf.getChampionshipFighter());
					}
					allItems.forEach((item) -> {
						GroupChampionshipFighter gcf = new 	GroupChampionshipFighter();
						gcf.setChampionshipFighter(item);
						getTable().getKASModel().addDataRow(gcf);
					});
				}
			});
		}
		return btnAddAllSuitable;
	}
	
}
