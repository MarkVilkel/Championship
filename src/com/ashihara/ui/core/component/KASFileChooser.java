/**
 * The file KASFileChooser.java was created on 2008.1.10 at 12:37:27
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.mvc.model.AKUIEventSender;
import com.ashihara.ui.tools.ApplicationManager;

public class KASFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	private KASFileChooser(){
		super();
	}
	public static KASFileChooser createKASSaveFileChooser(){
		KASFileChooser fc = createKASFileChooser();
		fc.setDialogTitle(uic.SAVE());
		return fc;
	}
	public static KASFileChooser createKASOpenFileChooser(){
		KASFileChooser fc = createKASFileChooser();
		fc.setDialogTitle(uic.OPEN());
		return fc;
	}
	
	private static KASFileChooser createKASFileChooser(){
		UIManager.put("FileChooser.cancelButtonText", uic.CANCEL());
		UIManager.put("FileChooser.cancelButtonToolTipText", uic.CANCEL());
		
		UIManager.put("FileChooser.saveButtonText", uic.SAVE());
		UIManager.put("FileChooser.saveButtonToolTipText", uic.SAVE());
		
		UIManager.put("FileChooser.openButtonText", uic.OPEN());
		UIManager.put("FileChooser.openButtonToolTipText", uic.OPEN());
		
		UIManager.put("FileChooser.fileNameLabelText", uic.FILE_NAME()+":");
		UIManager.put("FileChooser.filesOfTypeLabelText", uic.FILES_OF_TYPE()+":");
		
		UIManager.put("FileChooser.saveInLabelText", uic.SAVE_IN_FOLDER());
		UIManager.put("FileChooser.lookInLabelText", uic.LOOK_IN_FOLDER());
		
		UIManager.put("FileChooser.upFolderToolTipText", uic.UP_ONE_LEVEL());
		UIManager.put("FileChooser.homeFolderToolTipText", uic.DESKTOP());
		UIManager.put("FileChooser.newFolderToolTipText", uic.CREATE_NEW_FOLDER());
		
		UIManager.put("FileChooser.detailsViewButtonToolTipText", uic.DETAILS());
		UIManager.put("FileChooser.listViewButtonToolTipText", uic.LIST());
		KASFileChooser fc = new KASFileChooser();
		
		return fc;
	}
	
	public int showOpenDialog(Component c){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		try{
			return super.showOpenDialog(c);
		} finally{
			AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
		}
	}
	
	public int showSaveDialog(Component c){
		boolean isUIBlocked = AKUIEventSender.stopAndCheckUIVisibleProgress();
		try{
			return super.showSaveDialog(c);
		} finally{
			AKUIEventSender.startAndCheckUIVisibleProgress(isUIBlocked);
		}
	}
	
}
//1   /*
//2    *  $Id: KASFileChooser.java,v 1.1 2010/03/30 21:18:55 Mark Exp $ 
//3    *
//4    *  2002 (C) by Christian Garbs <mitch@cgarbs.de>
//5    *
//6    *  Licensed under GNU GPL (see COPYING for details)
//7    *
//8    */
//9   
//10  package de.cgarbs.swing;
//11  
//12  import de.cgarbs.util.Resource;
//13  import javax.swing.UIManager;
//14  
//15  /** This class localizes various Swing Components.
//16   * The localized Strings are taken from de.cgarbs.util.Resource class.
//17   * 
//18   * @author Christian Garbs <mitch@cgarbs.de>
//19   * @see de.cgarbs.util.Resource
//20   * @version $Id: KASFileChooser.java,v 1.1 2010/03/30 21:18:55 Mark Exp $
//21   */
//22  public class Localization
//23  {
//24      /** Localize the FileChooser Swing component.
//25       * These Resource Strings are read (including English defaults):
//26       * <pre>
//27       * FileChooser.cancelButtonText=Cancel
//28       * FileChooser.cancelButtonToolTipText=Cancel
//29       * FileChooser.detailsViewButtonToolTipText=Details
//30       * FileChooser.fileNameLabelText=File name:
//31       * FileChooser.filesOfTypeLabelText=Files of type:
//32       * FileChooser.helpButtonText=Help
//33       * FileChooser.helpButtonToolTipText=Help
//34       * FileChooser.homeFolderToolTipText=Home
//35       * FileChooser.listViewButtonToolTipText=List
//36       * FileChooser.lookInLabelText=Look in:
//37       * FileChooser.newFolderToolTipText=Create New Folder
//38       * FileChooser.openButtonText=Open
//39       * FileChooser.openButtonToolTipText=Open
//40       * FileChooser.saveButtonText=Save
//41       * FileChooser.saveButtonToolTipText=Save
//42       * FileChooser.upFolderToolTipText=Up One Level
//43       * FileChooser.updateButtonText=Update
//44       * FileChooser.updateButtonToolTipText=Update
//45       * </pre>
//46       */
//47      public static void localizeFileChooser()
//48      {
//49    UIManager.put("FileChooser.lookInLabelText", Resource.get("FileChooser.lookInLabelText"));
//50    UIManager.put("FileChooser.filesOfTypeLabelText", Resource.get("FileChooser.filesOfTypeLabelText"));
//51    UIManager.put("FileChooser.upFolderToolTipText", Resource.get("FileChooser.upFolderToolTipText"));
//52    UIManager.put("FileChooser.fileNameLabelText", Resource.get("FileChooser.fileNameLabelText"));
//53    UIManager.put("FileChooser.homeFolderToolTipText", Resource.get("FileChooser.homeFolderToolTipText"));
//54    UIManager.put("FileChooser.newFolderToolTipText", Resource.get("FileChooser.newFolderToolTipText"));
//55    UIManager.put("FileChooser.listViewButtonToolTipText", Resource.get("FileChooser.listViewButtonToolTipText"));
//56    UIManager.put("FileChooser.detailsViewButtonToolTipText", Resource.get("FileChooser.detailsViewButtonToolTipText"));
//57    UIManager.put("FileChooser.saveButtonText", Resource.get("FileChooser.saveButtonText"));
//58    UIManager.put("FileChooser.openButtonText", Resource.get("FileChooser.openButtonText"));
//59    UIManager.put("FileChooser.cancelButtonText", Resource.get("FileChooser.cancelButtonText"));
//60    UIManager.put("FileChooser.updateButtonText", Resource.get("FileChooser.updateButtonText"));
//61    UIManager.put("FileChooser.helpButtonText", Resource.get("FileChooser.helpButtonText"));
//62    UIManager.put("FileChooser.saveButtonToolTipText", Resource.get("FileChooser.saveButtonToolTipText"));
//63    UIManager.put("FileChooser.openButtonToolTipText", Resource.get("FileChooser.openButtonToolTipText"));
//64    UIManager.put("FileChooser.cancelButtonToolTipText", Resource.get("FileChooser.cancelButtonToolTipText"));
//65    UIManager.put("FileChooser.updateButtonToolTipText", Resource.get("FileChooser.updateButtonToolTipText"));
//66    UIManager.put("FileChooser.helpButtonToolTipText", Resource.get("FileChooser.helpButtonToolTipText"));
//67      }
//68  
//69      /** Localize the OptionPane Component.
//70       * These Resource Strings are read (including English defaults):
//71       * <pre>
//72       * OptionPane.cancelButtonText=Cancel
//73       * OptionPane.noButtonText=No
//74       * OptionPane.okButtonText=OK
//75       * OptionPane.yesButtonText=Yes
//76       * </pre>
//77       */
//78      public static void localizeOptionPane()
//79      {
//80    UIManager.put("OptionPane.cancelButtonText", Resource.get("OptionPane.cancelButtonText"));
//81    UIManager.put("OptionPane.noButtonText", Resource.get("OptionPane.noButtonText"));
//82    UIManager.put("OptionPane.okButtonText", Resource.get("OptionPane.okButtonText"));
//83    UIManager.put("OptionPane.yesButtonText", Resource.get("OptionPane.yesButtonText"));
//84      }
//85  }