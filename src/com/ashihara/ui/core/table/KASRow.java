/**
 * The file KASRow.java was created on 2008.16.8 at 11:42:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.table;

public class KASRow <T> {
	private boolean editable = true;
	private T dataRow;

	public KASRow(T dataRow){
		super();
		this.dataRow = dataRow;
	}
	
	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public T getDataRow() {
		return dataRow;
	}

	public void setDataRow(T dataRow) {
		this.dataRow = dataRow;
	} 
	
	public boolean equals(Object object) {
		if (object instanceof KASRow) {
			KASRow row = (KASRow) object;
			if (getEditable() == row.getEditable()) {
				if (getDataRow() == null && row.getDataRow() == null) {
					return true;
				}
				else if (getDataRow() != null && getDataRow().equals(row.getDataRow())) {
					return true;
				}
			}
			
			return false;
		}
		else {
			return super.equals(object);
		}
	}
}
