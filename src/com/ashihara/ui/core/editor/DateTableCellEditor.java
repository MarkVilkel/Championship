/**
 * The file DateTableCellEditor.java was created on 2007.25.9 at 20:21:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;

import com.ashihara.ui.core.component.date.AKDateChooser;

public class DateTableCellEditor extends AbstractCellEditor implements TableCellEditor, KASTableCellEditor{
	private static final long serialVersionUID = 1L;
	
	private AKDateChooser dateChooser = new AKDateChooser();
	
	public DateTableCellEditor(){
		super();
		
		getDateChooser().getJTextFieldDateEditor().setEditable(false);
		dateChooser.getPopupMenu().addPopupMenuListener(new PopupMenuListener(){
			private Boolean terminateEdit;
			
			public void popupMenuCanceled(PopupMenuEvent e){
			}
			
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
				JTable table = (JTable)getDateChooser().getParent();
				table.putClientProperty("terminateEditOnFocusLost",terminateEdit);
			}
			
			public void popupMenuWillBecomeVisible(PopupMenuEvent e){
				JTable table = (JTable)getDateChooser().getParent();
				terminateEdit = (Boolean)table.getClientProperty("terminateEditOnFocusLost");
				if(terminateEdit != null && terminateEdit == Boolean.TRUE){
					table.putClientProperty("terminateEditOnFocusLost",Boolean.FALSE);
				}		
			}
		});
	}
	
	public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            int clickCount = 2;
            return ((MouseEvent)evt).getClickCount() >= clickCount;
        }
        return true;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Date date = null;
		if (value instanceof Date)
			date = (Date) value;

		dateChooser.setDate(date);
		
		return dateChooser;
	}

	public Object getCellEditorValue() {
		return dateChooser.getDate();
	}

	public AKDateChooser getDateChooser() {
		return dateChooser;
	}
}
