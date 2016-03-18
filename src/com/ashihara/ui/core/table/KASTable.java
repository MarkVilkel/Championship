/**
 * The file KASTable.java was created on 2007.9.8 at 23:38:44
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.table;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.editor.KASTableCellEditor;
import com.ashihara.ui.core.renderer.KASHeaderRenderer;
import com.ashihara.ui.tools.ApplicationManager;

public class KASTable <T> extends JTable {
	private static final long serialVersionUID = 1L;
	private KASGenericTableModel<T> mvModel;
	private final Class<T> rowClass;
	private UIC uic = ApplicationManager.getInstance().getUic();
	
	private List<LinkClickingListener<T>> linkClickingListenerList; 
	
	public KASTable(Class<T> rowClass){
		super();
		this.rowClass = rowClass;
		setModel(getKASModel());
		setVisible(true);
		
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		setRowHeight(20);
		setSelectionBackground(new Color(160,200,255));
		setSelectionForeground(new Color(255,0,0));
		getTableHeader().setDefaultRenderer(new KASHeaderRenderer());
				
		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				KASTable<T> table = (KASTable<T>) e.getSource();
				table.requestFocus();
				Point point = e.getPoint();
		    	int col = table.columnAtPoint(point);
		    	int row = table.rowAtPoint(point);
		    	T object = getObjectUnderMouse(e);
		    	if (col >= 0 ){
		    		TableColumn tc = table.getColumnModel().getColumn(col);
//		    		TableCellRenderer renderer = getCellRenderer(row, col);
//		    		if (renderer instanceof LinkColumnRenderer && ((LinkColumnRenderer)renderer).isLinkActive() && e.getClickCount() == 1) { 
//		    			linkClicked(object, table.getColumnName(col));
//		    		}
		    		if ((table.getKASModel().getColumnByName(tc.getHeaderValue().toString()).isLinkColumn() ||
		    				table.getKASModel().getColumnByName(tc.getHeaderValue().toString()).isFakeLinkColumn())
		    				&& e.getClickCount() == 1) {
		    			linkClicked(object, table.getColumnName(col));
		    		}
		    	}
			}

			public void mousePressed(MouseEvent arg0) {
			}
			
			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e) {
				KASTable table = (KASTable) e.getSource();
	    		Point point = e.getPoint();
	    		
		    	int col = KASTable.this.columnAtPoint(point);
		    	int row = KASTable.this.rowAtPoint(point);
		    	
				String name = KASTable.this.getColumnName(col);
				KASColumn c = getKASModel().getColumnByName(name);
				KASRow<T> kasRow = getKASModel().getRow(row);
				
				TableCellRenderer renderer = getCellRenderer(row, col);
				
				if (c != null){
					if (c.isLinkColumn() || c.isFakeLinkColumn()){
//					if ((renderer instanceof LinkColumnRenderer)) {
//						table.setCursor(((LinkColumnRenderer)renderer).getLinkCursor());
						table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//					table.setToolTipText("Click twice to execute link action!");
						table.setToolTipText(uic.CLICK_ONCE_TO_EXECUTE_LINK_ACTION());
					}
					else if (c.getIsEditable() && kasRow != null && kasRow.getEditable()){
						table.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
//					table.setToolTipText("Click twice to start cell editing!");
						table.setToolTipText(uic.CLICK_TWICE_TO_START_CELL_EDITTING());
					}
					else{
						table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						if (row>=0 && row<table.getRowCount() && table.getValueAt(row, col)!=null) {
							table.setToolTipText(splitStringForToolTip(table.getValueAt(row, col).toString()));
						}
						else {
							table.setToolTipText(null);
						}
					}
				}
			}

			public void mouseDragged(MouseEvent arg0) {
				
			}

		});
	}
	
	public void linkClicked(T valueAt, String columnId){
		if (isEnabled()){
			for (LinkClickingListener<T> current : getLinkClickingListenerList())
				current.linkClicked(valueAt, columnId);
		}
	}
	
	private T getObjectUnderMouse(MouseEvent e){
		KASTable<T> table = (KASTable<T>) e.getSource();
		Point point = e.getPoint();
    	int col = table.columnAtPoint(point);
    	int row = table.rowAtPoint(point);
    	
    	if (row < 0 || row>= table.getRowCount())
    		return null;
    	
    	T valueAt = table.getKASModel().getRow(row).getDataRow();
    	return valueAt;
	}

	public KASGenericTableModel<T> getKASModel() {
		if (mvModel == null){
			mvModel = new KASGenericTableModel<T>(getRowClass());
			mvModel.addTableModelListener(new TableModelListener(){
				public void tableChanged(TableModelEvent arg0) {
					KASTable.this.repaint();
				}
			}); 
		}
		return mvModel;
	}
	
	public Class<T> getRowClass() {
		return rowClass;
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {
		String name = this.getColumnName(column);
		KASColumn c = getKASModel().getColumnByName(name); 
		if (c!=null)
			return c.getCellRenderer();
		else return super.getCellRenderer(row, column);
	}
	
	public TableCellEditor getCellEditor(int row, int column) {
		String name = this.getColumnName(column);
		KASColumn c = getKASModel().getColumnByName(name); 
		if (c.getCellEditor() instanceof KASTableCellEditor){
			return c.getCellEditor();
		}
			
		return super.getCellEditor(row, column);
	}
	
	public void addColumn(KASColumn column){
		getKASModel().addColumn(column);
	}

	private List<LinkClickingListener<T>> getLinkClickingListenerList() {
		if (linkClickingListenerList == null){
			linkClickingListenerList = new ArrayList<LinkClickingListener<T>>();
		}
		return linkClickingListenerList;
	}
	
	public void addLinkClickingListener(LinkClickingListener<T> listener){
		getLinkClickingListenerList().add(listener);
	}

	public List<T> getSelectedObjects(){
		int[] selected = getSelectedRows();
		List<T> selectedObjects = new ArrayList<T>();
		for (int i : selected){
			selectedObjects.add(getKASModel().getRows().get(i).getDataRow());	
		}			
		return selectedObjects;
	}
	
	public T getSingleSelectedRow(){
		int index = getSelectedRow();
		if (index >= getKASModel().getRows().size() || index<0)
			return null;
		
		return getKASModel().getRow(index).getDataRow();
	}

	public void setSingleSelection() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
    protected String splitStringForToolTip(String text){
    	String splittedText = new String("<html>");
    	splittedText+=splitString(text);
    	splittedText+="</html>";
    	return splittedText;
    }
    
    private String splitString(String text){
    	if (text == null || text.length()<=0) return text;
    	
    	int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    	int textWidth = getFontMetrics(getFont()).stringWidth(text);
    	
    	if (textWidth <= screenWidth)
    		return text;
    	
    	int splitCount = textWidth/screenWidth;
    	int splittedTextPartLength = text.length() / (splitCount + 1);
    	
    	String splittedText = new String("");
    	int startIndex = 0;
    	int	endIndex = splittedTextPartLength+1 < text.length() ? splittedTextPartLength : text.length();
    	for (int k = endIndex; k>=0; k--){
    		if (text.charAt(k) == ' '){
    			endIndex = k;
    			break;
    		}
    	}
    	splittedText+=text.substring(startIndex,endIndex);
   		splittedText+="<br>";
    	splittedText+=splitString(text.substring(endIndex, text.length()));
    	return splittedText;
    }
}
