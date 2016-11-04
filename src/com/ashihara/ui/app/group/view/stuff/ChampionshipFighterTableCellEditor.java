/**
 * The file RoundRobinTableCellEditor.java was created on 2012.14.4 at 16:22:25
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.ui.app.utils.ComboUIHelper;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.editor.KASTableCellEditor;

public class ChampionshipFighterTableCellEditor extends DefaultCellEditor implements KASTableCellEditor {
	private static final long serialVersionUID = 1L;

	private Map<Integer, KASComboBox> cmbMap = new HashMap<>();
	private final ChampionshipFighterProvider championshipFighterProvider;
	
	private KASComboBox cmb;
	
	public ChampionshipFighterTableCellEditor(ChampionshipFighterProvider championshipFighterProvider) {
		super(new JTextField());
		
		this.championshipFighterProvider = championshipFighterProvider;
	}
	
	@Override
	public Component getTableCellEditorComponent(
			JTable table, 
			Object value, 
			boolean isSelected, 
			final int row, 
			final int column
	){
		if (column == 0) {
			cmb = getCmb(row);
			refillEntrySet(cmb, table, row, column);
			return cmb;
		}
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
	
	private void refillEntrySet(KASComboBox cmb, JTable table, int row, int column) {
		List<ChampionshipFighter> items = championshipFighterProvider.getItemsCopy();
		for (int r = 0; r < table.getModel().getRowCount(); r++) {
			if (r != row) {
				Object value = table.getValueAt(r, column);
				items.remove(value);
			}
		}
		
		Object selected = cmb.getSelectedItem();
		ComboUIHelper.fillUpEntrySetForCombo(cmb, items, true);
		if (selected != null) {
			cmb.tryToSelectIfInEntrySet(selected);
		}
		
	}

	@Override
	public Object getCellEditorValue() {
		return cmb.getSelectedItem();
	}

	public KASComboBox getCmb(int index) {
		KASComboBox cmb = cmbMap.get(index);
		if (cmb == null) {
			cmb = new KASComboBox();
			cmbMap.put(index, cmb);
			cmb.setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if (value instanceof ChampionshipFighter) {
						ChampionshipFighter cf = (ChampionshipFighter) value;
						Component c = super.getListCellRendererComponent(list, cf.toLongString(), index, isSelected, cellHasFocus);
						return c;
					}
					return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				}
			});
		}
		
		return cmb;
	}

}
