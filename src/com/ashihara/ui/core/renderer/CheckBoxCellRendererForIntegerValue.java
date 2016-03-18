/**
 * The file CheckBoxCellRendererForIntegerValue.java was created on 2007.27.8 at 17:38:50
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.renderer;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

public class CheckBoxCellRendererForIntegerValue extends KASDefaultRenderer{
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;
	private JPanel panel;
	
	private final Integer forTrue;
	
	public CheckBoxCellRendererForIntegerValue(Integer forTrue){
		this.forTrue = forTrue;
		getCheckBox().setHorizontalAlignment(JCheckBox.CENTER);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Integer val = (Integer)value;
		if (val.equals(forTrue))
			getCheckBox().setSelected(true);
		else 
			getCheckBox().setSelected(false);
		
		getCheckBox().setBackground(comp.getBackground());
		getPanel().setBackground(comp.getBackground());
		return getPanel();
	}

	public JCheckBox getCheckBox() {
		if (checkBox == null){
			checkBox = new JCheckBox();
		}
		return checkBox;
	}

	public JPanel getPanel() {
		if (panel == null){
			panel = new JPanel(new BorderLayout());
			panel.add(getCheckBox(), BorderLayout.CENTER);
		}
		return panel;
	}
}