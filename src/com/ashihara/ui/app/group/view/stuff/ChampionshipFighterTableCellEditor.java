/**
 * The file RoundRobinTableCellEditor.java was created on 2012.14.4 at 16:22:25
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.editor.KASTableCellEditor;

public class ChampionshipFighterTableCellEditor extends DefaultCellEditor implements KASTableCellEditor {
	private static final long serialVersionUID = 1L;

	private KASComboBox cmb;
	
	public ChampionshipFighterTableCellEditor(KASComboBox cmb) {
		super(new JTextField());
		
		this.cmb = cmb;
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
			return cmb;
		}
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
	
	@Override
	public Object getCellEditorValue() {
		return cmb.getSelectedItem();
	}

}
