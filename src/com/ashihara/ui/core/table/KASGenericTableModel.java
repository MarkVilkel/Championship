/**
 * The file MVGenericTableModel.java was created on 2007.12.7 at 15:31:59
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class KASGenericTableModel <T> extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	private List<KASRow<T>> rows = new ArrayList<KASRow<T>>();
	private List<KASColumn> columns = new ArrayList<KASColumn>();
	private final Class<T> rowClass;
	private List<KASRow<T>> added = new ArrayList<KASRow<T>>();
	private List<KASRow<T>> deleted = new ArrayList<KASRow<T>>();
	private List<KASRow<T>> modified = new ArrayList<KASRow<T>>();
	
	private boolean canEdit = true;

	public KASGenericTableModel(Class<T> rowClass){
		super();
		this.rowClass = rowClass;
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		KASColumn kasColumn = getColumns().get(col);
		
		if (KASColumn.COUNT_COLUMN.getAttributePath().equals(kasColumn.getAttribute()) || kasColumn.isFakeLinkColumn() || 
				kasColumn.isFakeLinkCountColumn() || KASColumn.NOTHING_TO_DO_COLUMN.getAttributePath().equals(kasColumn.getAttribute())) { 
			return String.class;
		}
		if (KASColumn.RETURN_WHOLE_OBJECT_COLUMN.getAttributePath().equals(kasColumn.getAttribute())) {
			return rowClass; 
		}

		String path = kasColumn.getAttribute();
		return StringPathHelper.getClassByPath(path, rowClass);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int col){
		return canEdit && getColumns().get(col).getIsEditable() && getRow(rowIndex).getEditable();
	}

	@Override
	public int getColumnCount() {
		return getColumns().size();
	}

	@Override
	public int getRowCount() {
		return getRows().size();	
	}

	@Override
	public Object getValueAt(int row, int col){
		KASColumn kasColumn = getColumns().get(col);
		
		if (KASColumn.NOTHING_TO_DO_COLUMN.getAttributePath().equals(kasColumn.getAttribute())){
			return new String("");
		}
		if (KASColumn.COUNT_COLUMN.getAttributePath().equals(kasColumn.getAttribute()) || kasColumn.isFakeLinkCountColumn()) { 
			return new String (String.valueOf(row+1));
		}
		if (kasColumn.isFakeLinkColumn()) { 
			return new String (kasColumn.getAttribute());
		}
		if (KASColumn.RETURN_WHOLE_OBJECT_COLUMN.getAttributePath().equals(kasColumn.getAttribute())){
			return getDataRow(row);
		}

		String path = kasColumn.getAttribute();
		
		return StringPathHelper.invokeGetMethodForPath(path, getDataRow(row));
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		KASColumn kasColumn = getColumns().get(col);
		
		if (getRows().size() <= row) {
			return;
		}
		if (KASColumn.NOTHING_TO_DO_COLUMN.getAttributePath().equals(kasColumn.getAttribute())) {
			return;
		}
		if (KASColumn.RETURN_WHOLE_OBJECT_COLUMN.getAttributePath().equals(kasColumn.getAttribute())) {
			return;
		}
		if (KASColumn.COUNT_COLUMN.getAttributePath().equals(kasColumn.getAttribute())) { 
			return;
		}
		if (kasColumn.isFakeLinkColumn() || kasColumn.isFakeLinkCountColumn()) {
			return;
		}
			
		String path = kasColumn.getAttribute();
		
		StringPathHelper.invokeSetMethodForPath(path, getDataRow(row), value);
		
		getModified().add(getRows().get(row));
		
        fireTableCellUpdated(row, col);
    }
	
	@Override
	public String getColumnName(int column) {
		if (getColumns().size()<=column || column<0) {
			return null;
		}
		return getColumns().get(column).getColumnName().toString();
	}

	
	/**
	 * Don't use this method to add NEW ROWS!!!!
	 * Use setRows() method or addRow()
	 */
	public List<KASRow<T>> getRows() {
		return rows;
	}
	
	/**
	 * Don't use this method to add NEW DATA ROWS!!!!
	 * Use setDataRows() method  or addDataRow()
	 */
	public List<T> getDataRows() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> rowz = getRows();
		if (rowz != null) {
			for (KASRow<T> row : rowz) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}

	public KASRow<T> getRow(int num){
		if (num >= 0 && getRows().size() > num) {
			return getRows().get(num);
		}
		return null;
	}
	public T getDataRow(int num){
		return getRows().get(num).getDataRow();
	}
	
	public void setRows(List<KASRow<T>> rows) {
		if (rows != null) {
			this.rows = rows;
		}
		else { 
			this.rows = new ArrayList<KASRow<T>>();
		}
		
		getAdded().clear();
		getModified().clear();
		getDeleted().clear();
		fireTableDataChanged();
	}
	
	public void setDataRows(List<T> dataRows) {
		if (dataRows != null){
			List<KASRow<T>> rowz = new ArrayList<KASRow<T>>();
			for (T t : dataRows) {
				rowz.add(new KASRow<T>(t));
			}
			setRows(rowz);
		}
	}
	
	protected List<KASColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<KASColumn> columns) {
		this.columns = columns;
		fireTableDataChanged();
		fireTableStructureChanged();
	}
	public Class<T> getRowClass() {
		return rowClass;
	}
	
	public void addNewRow(int count){
		if (count>0){
			try {
				for (int i = 0; i<count; i++){
					T obj = getRowClass().newInstance();
					getRows().add(new KASRow<T>(obj));
					getAdded().add(new KASRow<T>(obj));
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			fireTableDataChanged();
		}
	}
	
	public void addDataRow(T row){
		if (row != null){
			addDataRows(Collections.singletonList(row));
		}
	}
	
	public void addRows(List<KASRow<T>> rows){
		if (rows != null) {
			getRows().addAll(rows);
			getAdded().addAll(rows);
			fireTableDataChanged();
		}
	}
	
	public void addRow(KASRow<T> row){
		if (row != null) {
			addRows(Collections.singletonList(row));
		}
	}
	
	public void deleteRows(int[] selected){
		if (selected.length > 0){
			for (int row: selected){
				getDeleted().add(getRows().get(row));
			}
			getRows().removeAll(getDeleted());
			
			fireTableDataChanged();
		}
	}
	
	private void performListsCleaning() {
		modified.removeAll(deleted);
		added.removeAll(deleted);
		modified.removeAll(added);
	}
	
	public List<KASRow<T>> getAdded() {
		return added;
	}
	
	public List<KASRow<T>> getDeleted() {
		return deleted;
	}
	public List<KASRow<T>> getModified() {
		return modified;
	}
	
	public List<KASRow<T>> getCleanAdded() {
		performListsCleaning();
		return added;
	}
	
	public List<KASRow<T>> getCleanDeleted() {
		performListsCleaning();
		return deleted;
	}
	public List<KASRow<T>> getCleanModified() {
		performListsCleaning();
		return modified;
	}

	public List<T> getDataAdded() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> addedRows = getAdded();
		if (addedRows != null) {
			for (KASRow<T> row : addedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	public List<T> getDataDeleted() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> deletedRows = getDeleted();
		if (deletedRows != null) {
			for (KASRow<T> row : deletedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	public List<T> getDataModified() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> modifiedRows = getModified();
		if (modifiedRows != null) {
			for (KASRow<T> row : modifiedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	public List<T> getDataCleanAdded() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> addedRows = getCleanAdded();
		if (addedRows != null) {
			for (KASRow<T> row : addedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	public List<T> getDataCleanDeleted() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> deletedRows = getCleanDeleted();
		if (deletedRows != null) {
			for (KASRow<T> row : deletedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	public List<T> getDataCleanModified() {
		List<T> data = new ArrayList<T>();
		List<KASRow<T>> modifiedRows = getCleanModified();
		if (modifiedRows != null) {
			for (KASRow<T> row : modifiedRows) {
				data.add(row.getDataRow());
			}
		}
		return data;
	}
	
	public void clear(){
		getRows().clear();
		getAdded().clear();
		getModified().clear();
		getDeleted().clear();
		fireTableDataChanged();
	}
	
	public void addColumn(KASColumn column){
		getColumns().add(column);
		fireTableStructureChanged();
	}
	
	public void removeColumn(KASColumn column){
		getColumns().remove(column);
		fireTableDataChanged();
		fireTableStructureChanged();
	}
	
	public KASColumn getColumnByName(String name){
		for (KASColumn c : getColumns()) {
			if (c.getColumnName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public void addDataRows(List<T> dataRows) {
		if (dataRows != null) {
			List<KASRow<T>> rows = new ArrayList<KASRow<T>>();
			for (T dataRow : dataRows) {
				rows.add(new KASRow<T>(dataRow));
			}
			addRows(rows);
		}
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
}