/**
 * The file ScrollPaneReadOnlyTableEditor.java was created on 2009.6.6 at 14:31:50
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

import com.ashihara.ui.core.component.KASJTextArea;

public class ScrollPaneReadOnlyTableEditor extends AbstractCellEditor implements KASTableCellEditor{
	private static final long serialVersionUID = 1L;
	private KASJTextArea textArea;
	private Object value;

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		getTextArea().setText(String.valueOf(value));
		setValue(value);
		return getTextArea();
	}

	public Object getCellEditorValue() {
		return getValue();
	}

	public KASJTextArea getTextArea() {
		if (textArea == null) {
			textArea = new KASJTextArea();
			textArea.setEditable(false);
		}
		return textArea;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
