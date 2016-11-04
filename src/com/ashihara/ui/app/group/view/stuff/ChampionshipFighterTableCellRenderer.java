/**
 * The file ChampionshipFighterTableCellRenderer.java was created on 12 окт. 2016 г. at 23:29:21
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.view.stuff;

import java.awt.Component;

import javax.swing.JTable;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.ui.core.renderer.KASDefaultRenderer;

public class ChampionshipFighterTableCellRenderer extends KASDefaultRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value instanceof ChampionshipFighter) {
			ChampionshipFighter cf = (ChampionshipFighter) value;
			return super.getTableCellRendererComponent(table, cf.toLongString(), isSelected, hasFocus, row, column);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
