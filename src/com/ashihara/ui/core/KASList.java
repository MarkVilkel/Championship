/**
 * The file KASList.java was created on 2008.1.2 at 23:48:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core;

import javax.swing.JList;

public class KASList<T extends Object> extends JList{
	private static final long serialVersionUID = 1L;
	private KASListModel<T> kasModel;
	
	public KASList(){
		super();
		setModel(getKasModel());
//		setPreferredSize(new Dimension(150, 300));
		setAutoscrolls(true);
	}

	public KASListModel<T> getKasModel() {
		if (kasModel == null){
			kasModel = new KASListModel<T>();
		}
		return kasModel;
	}
}
