/**
 * The file ResourceHelper.java was created on 2007.19.8 at 12:17:33
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.resources;

import javax.swing.ImageIcon;

public class ResourceHelper {
	public static final String LV_COAT = "LV-Coat.png";
	public static final String LV_FLAG = "LV-Flag.png";
	public static final String LATVIA_ASHIHARA_KARATE_LOGO = "NIKO.jpg";
	public static final String RUSSIA = "Russia.jpg";
	public final static String PLUS = "plus.gif";
	public final static String PLUS2 = "plus2.gif";
	public final static String PLUS3 = "plus3.gif";
	public final static String MINUS = "minus.gif";
	public final static String EXCEL = "excel.gif";
	public final static String SPLASH = "splashScreen.gif";
	public final static String CANCEL = "cancel.gif";
	public final static String CANCEL_16x16 = "cancel_16x16.gif";
	public final static String CLEAR = "clear.gif";
	public final static String REFRESH = "refresh.gif";	
	public final static String SEARCH = "search.gif";
	public final static String OK = "ok.gif";
	public final static String WORD = "word.gif";
	public final static String WORD_SMALL = "word_small.gif";
	public final static String ADD_DOWN = "add_down.gif";
	public final static String ADD_UP = "add_up.gif";
	public final static String COLLAPSE = "collapse.gif";
	public final static String CONNECT_OFF = "connectoff.gif";
	public final static String CONNECT_ON = "connecton.gif";
	public final static String COPY = "copy.gif";
	public final static String CUT = "cut.gif";
	public final static String DELETE = "delete.gif";
	public final static String EXPAND = "expand.gif";
	public final static String EXPAND_ALL = "expandAll.gif";
	public final static String GROUP = "group.gif";
	public final static String NEW_NODE_TOOL = "newNodeTool.gif";
	public final static String PASTE = "paste.gif";
	public final static String REDO = "redo.gif";
	public final static String TO_BACK = "toback.gif";
	public final static String TO_FRONT = "tofront.gif";
	public final static String UNDO = "undo.gif";
	public final static String UNGROUP = "ungroup.gif";
	public final static String ZOOM = "zoom.gif";
	public final static String ZOOM_IN = "zoomin.gif";
	public final static String ZOOM_OUT = "zoomout.gif";
	public final static String SELECTION_TOOL = "selectionTool.gif";
	public final static String IMPORTANT_CONNECTION = "importantConncetion.gif";
	public final static String CALENDAR = "calendar.gif";
	public final static String START = "start.gif";
	public final static String INFO = "info.gif";
	public final static String TICK = "tick.gif";
	public final static String DOWN_ARROW = "downArrow.gif";
	public final static String SAVE = "save.gif";
	public final static String EXIT = "exit.gif";
	public final static String BINOCULAR = "binocular.gif";
	public final static String NO_BINOCULAR = "no_binocular.gif";
	public final static String UPLOAD = "upload.gif";
	public final static String INFO_2 = "info_2.gif";
	public final static String MAIL = "mail.gif";
	public final static String AUTO_INSERTION = "autoInsertion.gif";
	public final static String ARROW_DOWN_PAPER = "arrowDownPaper.gif";
	public final static String HELP_CALCULATION_DESCRIPTION = "helpCalculationDescription.gif";
	public final static String HELP_TYPE_DESCRIPTION = "helpTypeDescription.gif";
	public final static String ARROW_STAR = "arrowStar.gif";
	public static final String ARCHIVE = "archive.gif";
	public static final String UP = "up.gif";
	public static final String DOWN = "down.gif";
	public static final String CHECK = "check.gif";
	public static final String RADIO = "radio.gif";
	public static final String COMBO = "combo.gif";
	public static final String EDIT = "edit.gif";
	public static final String SECTION = "section.gif";
	public static final String PAGE = "page.gif";
	public static final String NO_PHOTO = "no_photo.gif";
	
	public static ImageIcon getImageIcon(String name){
		return new ImageIcon(ResourceHelper.class.getResource(name));
	}
}
