/**
 * The file KASDateChooser.java was created on 2008.6.3 at 00:12:49
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.date;

import javax.swing.JPopupMenu;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class AKDateChooser extends JDateChooser{
	private static final long serialVersionUID = 1L;

	public AKDateChooser(){
		super("dd/MM/yyyy", "##/##/####", '_');
	}
	
	public JPopupMenu getPopupMenu(){
		return popup;
	}
	
	public JTextFieldDateEditor getJTextFieldDateEditor(){
		return (JTextFieldDateEditor)dateEditor;
	}
}
