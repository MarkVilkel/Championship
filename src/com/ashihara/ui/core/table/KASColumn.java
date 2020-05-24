/**
 * The file MVColumn.java was created on 2007.12.7 at 16:34:34
 * by
 * @author Marks Vilkelis.
 */

package com.ashihara.ui.core.table;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.ashihara.ui.core.renderer.KASDefaultRenderer;
import com.ashihara.ui.core.renderer.KASHeaderRenderer;
import com.ashihara.ui.core.renderer.KASTableCellRenderer;
import com.ashihara.ui.core.renderer.LinkColumnRenderer;
import com.rtu.persistence.core.Attribute;

public class KASColumn extends TableColumn{
	private static final long serialVersionUID = 1L;
	
	private String columnName;
	private Attribute attribute;
	
	private Boolean isEditable;
	
	public static final Attribute COUNT_COLUMN = new Attribute(){
		@Override
		public String getAttributeName() {
			return "COUNT_COLUMN";
		}
		@Override
		public String getAttributePath() {
			return getAttributeName();
		}
	};
	public static final Attribute NOTHING_TO_DO_COLUMN = new Attribute() {
		@Override
		public String getAttributeName() {
			return "NOTHING_TO_DO_COLUMN";
		}

		@Override
		public String getAttributePath() {
			return "NOTHING_TO_DO_COLUMN";
		}
	};
	
	public static final Attribute RETURN_WHOLE_OBJECT_COLUMN = new Attribute() {
		@Override
		public String getAttributeName() {
			return "RETURN_WHOLE_OBJECT_COLUMN";
		}

		@Override
		public String getAttributePath() {
			return "RETURN_WHOLE_OBJECT_COLUMN";
		}
	};
	
	private boolean isLinkColumn = false;
	private boolean isFakeLinkColumn = false;
	private boolean isFakeLinkCountColumn = false;
	
	public static KASColumn createLinkColumn(String columnName, Attribute propertyName){
		return createLinkColumn(columnName, propertyName, new LinkColumnRenderer());
	}
	
	public static KASColumn createLinkColumn(String columnName, Attribute propertyName, KASTableCellRenderer renderer){
		KASColumn col = new KASColumn(columnName, propertyName, false, renderer);
		col.setIsLinkColumn(true);
		return col;
	}
	
	public static KASColumn createFakeLinkColumn(String columnName, final String cellCaption){
		KASColumn col = new KASColumn(columnName, new Attribute(){
			@Override
			public String getAttributeName() {
				return cellCaption;
			}

			@Override
			public String getAttributePath() {
				return getAttributeName();
			}
		}, 
		false, new LinkColumnRenderer());
		
		col.setFakeLinkColumn(true);
		return col;
	}

	public static KASColumn createFakeCountLinkColumn(final String columnName){
		KASColumn col = new KASColumn(columnName, new Attribute(){
			@Override
			public String getAttributeName() {
				return columnName;
			}

			@Override
			public String getAttributePath() {
				return getAttributeName();
			}
			
		}, false, new LinkColumnRenderer());
		col.setFakeLinkColumn(true);
		col.setFakeLinkCountColumn(true);
		return col;
	}

	public static KASColumn createCountColumn(String caption){
		return new KASColumn(caption, COUNT_COLUMN, false);
	}
	
	public static KASColumn createImageColumn(String caption, TableCellRenderer renderer){
		return new KASColumn(caption, NOTHING_TO_DO_COLUMN, false, renderer);
	}
	
	public KASColumn(){
		setIsEditable(true);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	public KASColumn(int modelIndex){
		super(modelIndex);
		setIsEditable(true);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	public KASColumn(int modelIndex, int width){
		super(modelIndex, width);
		setIsEditable(true);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	public KASColumn(int modelIndex, int width, TableCellRenderer renderer, TableCellEditor editor){
		super(modelIndex, width, renderer, editor);
		setIsEditable(true);
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName, Boolean isEditable){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(isEditable);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(false);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName, TableCellRenderer renderer){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(false);
		setCellRenderer(renderer);
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName, Boolean isEditable, TableCellRenderer renderer){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(isEditable);
		setCellRenderer(renderer);
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName, Boolean isEditable, TableCellEditor editor){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(isEditable);
		setCellEditor(editor);
		setHeaderRenderer(new KASHeaderRenderer());
		setCellRenderer(new KASDefaultRenderer());
	}
	
	public KASColumn(String columnName, Attribute propertyName, Boolean isEditable, TableCellEditor editor, TableCellRenderer renderer){
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(isEditable);
		setCellEditor(editor);
		setHeaderRenderer(new KASHeaderRenderer());
		setCellRenderer(renderer);
	}
	
	public KASColumn(int modelIndex, String columnName, Attribute propertyName, Boolean isEditable){
		super(modelIndex);
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(isEditable);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(int modelIndex, int width, String columnName, Attribute propertyName){
		super(modelIndex, width);
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(true);
		setCellRenderer(new KASDefaultRenderer());
		setHeaderRenderer(new KASHeaderRenderer());
	}
	
	public KASColumn(int modelIndex, int width, TableCellRenderer renderer, TableCellEditor editor, String columnName, Attribute propertyName){
		super(modelIndex, width, renderer, editor);
		setColumnName(columnName);
		setAttr(propertyName);
		setIsEditable(true);
		setHeaderRenderer(new KASHeaderRenderer());
	}

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
		setIdentifier(new String(columnName));
	}
	
	public String getAttribute() {
		return attribute.getAttributePath();
	}
	
	public Attribute getAttr() {
		return attribute;
	}
	
	public void setAttribute(final String attribute) {
		this.attribute = new Attribute(){
			@Override
			public String getAttributeName() {
				return attribute;
			}
			@Override
			public String getAttributePath() {
				return getAttributeName();
			}
		};
	}
	
	public void setAttr(Attribute attribute) {
		this.attribute = attribute;
		
//		String modified = String.valueOf(getPropertyName().charAt(0)).toUpperCase().concat(getPropertyName().substring(1));
//		
//		getMethodName = new String("get"+modified);
//		setMethodName = new String("set"+modified);
	}
	
//	public String getGetMethodName() {
//		return getMethodName;
//	}
//	public String getSetMethodName() {
//		return setMethodName;
//	}
	
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isLinkColumn() {
		return isLinkColumn;
	}

	public void setIsLinkColumn(boolean isLinkColumn) {
		this.isLinkColumn = isLinkColumn;
	}

	public boolean isFakeLinkColumn() {
		return isFakeLinkColumn;
	}

	public void setFakeLinkColumn(boolean isFakeLinkColumn) {
		this.isFakeLinkColumn = isFakeLinkColumn;
	}

	public boolean isFakeLinkCountColumn() {
		return isFakeLinkCountColumn;
	}

	public void setFakeLinkCountColumn(boolean isFakeLinkCountColumn) {
		this.isFakeLinkCountColumn = isFakeLinkCountColumn;
	}

}