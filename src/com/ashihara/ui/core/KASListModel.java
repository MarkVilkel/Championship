/**
 * The file KASListModel.java was created on 2008.1.2 at 23:49:03
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class KASListModel<T extends Object> extends DefaultListModel{
	private static final long serialVersionUID = 1L;

	public List<T> getAllElements(){
		List<T> list = new ArrayList<T>();
		for (int i = 0 ; i < size(); i++){
			T t  = (T) get(i);
			list.add(t);
		}
		return list;
	}
	
	public void addAll(List<T> list){
		for (T t : list)
			addElement(t);
	}
	
	public void setAll(List<T> list){
		removeAllElements();
		for (T t : list)
			addElement(t);
	}

	public List<T> removeElements(Object[] selected) {
		List<T> removed = new ArrayList<T>();
		for (Object t : selected){
			removed .add((T)t);
			removeElement(t);
		}
		return removed;
	}
}
