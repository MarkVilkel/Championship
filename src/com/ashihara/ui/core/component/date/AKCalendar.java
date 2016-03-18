/**
 * The file KasCalendar.java was created on 2009.2.12 at 19:36:30
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.date;

import com.ashihara.ui.tools.ApplicationManager;
import com.toedter.calendar.JCalendar;

public class AKCalendar extends JCalendar {
	private static final long serialVersionUID = 1L;
	
	public AKCalendar() {
		super(ApplicationManager.getInstance().getUic().GET_LOCALE());
		
		getDayChooser().setLocale(ApplicationManager.getInstance().getUic().GET_LOCALE());
		getMonthChooser().setLocale(ApplicationManager.getInstance().getUic().GET_LOCALE());
		getYearChooser().setLocale(ApplicationManager.getInstance().getUic().GET_LOCALE());
	}

}
