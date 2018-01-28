/**
 * The file UIFactory.java was created on 2007.18.8 at 18:08:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.component.KASLinkLabel;
import com.ashihara.ui.core.component.KasButton;
import com.ashihara.ui.core.component.KasScrollPane;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.core.component.textField.KASTextField;
import com.ashihara.ui.core.layout.ColumnLayout;
import com.ashihara.ui.core.layout.OrientableFlowLayout;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.resources.ResourceHelper;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.BrownSugar;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.DesertBlue;
import com.jgoodies.looks.plastic.theme.DesertBluer;
import com.jgoodies.looks.plastic.theme.DesertGreen;
import com.jgoodies.looks.plastic.theme.DesertRed;
import com.jgoodies.looks.plastic.theme.DesertYellow;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;
import com.jgoodies.looks.plastic.theme.ExperienceGreen;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.LightGray;
import com.jgoodies.looks.plastic.theme.Silver;
import com.jgoodies.looks.plastic.theme.SkyBlue;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.jgoodies.looks.plastic.theme.SkyGreen;
import com.jgoodies.looks.plastic.theme.SkyKrupp;
import com.jgoodies.looks.plastic.theme.SkyPink;
import com.jgoodies.looks.plastic.theme.SkyRed;
import com.jgoodies.looks.plastic.theme.SkyYellow;
import com.vlsolutions.swing.docking.ui.DockingUISettings;

public class UIFactory {
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	private static List<Class> lookAndFeelList;
	private static List<Class> themeList;
	private static Map<String, Class> lookAndFeelMap;
	private static Map<String, Class> themeMap;

	
	public static final int DEFAULT_BTN_HEIGHT = 21;
	public static final String GAP = "&nbsp;";
	
	public static JPanel createJPanel(){
		return new JPanel();
	}
	
	/**
	 * Creates panel with BorderLayout
	 * @return new JPanel(new BorderLayout());
	 */
	public static JPanel createJPanelBL(){
		return new JPanel(new BorderLayout());
	}
	
	public static JPanel createJPanelBLWithEB(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());

		return panel;
	}

	
	/**
	 * Creates panel with Flow Left Layout
	 * @return new JPanel(new FlowLayout(FlowLayout.LEFT));
	 */
	public static KASPanel createJPanelFLL(){
		KASPanel panel = new KASPanel(new FlowLayout(FlowLayout.LEFT));
		return panel;
	}
	
	public static JPanel createJPanelFLL(JComponent comp){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(comp);
		return panel;
	}


	public static JPanel createJPanelFLLWithBorder(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createEtchedBorder());
		return panel;
	}
	
	public static JPanel createJPanelFLCWithBorder(){
		JPanel panel = new JPanel(new FlowLayout(OrientableFlowLayout.CENTER));
		panel.setBorder(BorderFactory.createEtchedBorder());
		return panel;
	}
	
	public static KASPanel createKASPanelFLTop(){
		KASPanel panel = new KASPanel(new FlowLayout(OrientableFlowLayout.TOP));
		return panel;
	}
	
	public static KASPanel createKASPanelColumnLayoutLeft(){
		KASPanel panel = new KASPanel();
		panel.setLayout(new ColumnLayout(5, 5, 10, ColumnLayout.LEFT));
		return panel;
	}
	
	public static JPanel createJPanelFLRWithBorder(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEtchedBorder());
		return panel;
	}
	
	/**
	 * Creates panel with Flow Right Layout
	 * @return new JPanel(new FlowLayout(FlowLayout.RIGHT));
	 */
	public static KASPanel createJPanelFRL(){
		return new KASPanel(new FlowLayout(FlowLayout.RIGHT));
	}

	public static JPanel createJPanelFRL(JComponent comp){
		if (comp instanceof JLabel)
			((JLabel)comp).setPreferredSize(new Dimension(((JLabel)comp).getPreferredSize().width, 20));
		JPanel panel  = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(comp);
		return panel;
	}
	
	/**
	 * Creates JButton
	 * @return new JButton(caption);
	 */
	public static JButton createJButton(String caption){
		return new JButton(caption);
	}
	
	
	/**
	 * Creates JTextField with specified width and default height
	 * @return new JTetxField();
	 */
	public static JTextField createJTextField(Integer width){
		JTextField txt = new JTextField();
		Dimension d = new Dimension(width, txt.getPreferredSize().height);
		txt.setPreferredSize(new Dimension(d));
		return txt;
	}
	
	public static KASTextField createKASTextField(Integer width){
		KASTextField txt = new KASTextField();
		Dimension d = new Dimension(width, txt.getPreferredSize().height);
		txt.setPreferredSize(new Dimension(d));
		return txt;
	}
	
	public static JSpinner createJSpinner(Integer width){
		JSpinner sp = new JSpinner();
		sp.setPreferredSize(new Dimension(width, sp.getPreferredSize().height));
		return sp;
	}
	
	public static JTextField createJTextField(){
		JTextField txt = new JTextField();
		txt.setPreferredSize(new Dimension(150, txt.getPreferredSize().height));
		return txt;
	}
	
	public static JTextField createJTextFieldMin(Integer width){
		JTextField txt = new JTextField();
		txt.setPreferredSize(new Dimension(width, txt.getPreferredSize().height));
		txt.setMinimumSize(new Dimension(width, txt.getPreferredSize().height));
		return txt;
	}
	

	public static JLabel createJLabel20Min(String caption){
		JLabel lbl = new JLabel(caption);
		lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, 20));
		lbl.setMinimumSize(new Dimension(lbl.getPreferredSize().width, 20));
		return lbl;
	}
	
	public static JPasswordField createJPasswordField(Integer width){
		JPasswordField txt = new JPasswordField();
		txt.setPreferredSize(new Dimension(width, txt.getPreferredSize().height));
		return txt;
	}
	
	public static JPasswordField createJPasswordField20(Integer width){
		JPasswordField txt = new JPasswordField();
		txt.setPreferredSize(new Dimension(width, 20));
		return txt;
	}
	
	public static KASComboBox createKasComboBox20(Integer width){
		KASComboBox cmb = new KASComboBox();
		cmb.setPreferredSize(new Dimension(width, 20));
		return cmb;
	}
	
	public static <T extends Object> AutoCompleteComboBox<T> createAutoCompleteComboBox20(Class<T> returnType, Integer width){
		AutoCompleteComboBox<T> cmb = new AutoCompleteComboBox<T>(returnType);
		cmb.setPreferredSize(new Dimension(width, 20));
		return cmb;
	}
	
	public static JCheckBox createJCheckBox(Integer width){
		JCheckBox cmb = new JCheckBox();
		cmb.setPreferredSize(new Dimension(width, 20));
		return cmb;
	}


	public static JLabel createJLabel20(String text){
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(label.getPreferredSize().width,20));
		return label;
	}
	
	/**
	 * Put on screen center specified component.
	 * @param component
	 */
	public static void componentToCenter(Window component){
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = component.getSize();
	    if (frameSize.height > screenSize.height) {
	      frameSize.height = screenSize.height;
	    }
	    if (frameSize.width > screenSize.width) {
	      frameSize.width = screenSize.width;
	    }
	    component.setLocation( (screenSize.width - frameSize.width) / 2,
	                      (screenSize.height - frameSize.height) / 2);
	}
	
	public static void centerVisible(Window component){
		UIFactory.componentToCenter(component);
		component.setVisible(true);
	}
	
	public static void sizeCenterVisible(Window component, Dimension size){
		component.setSize(size);
		centerVisible(component);
	}
	
	public static JPanel createPanelWithLabeledComponent(String label, JComponent component){
		JPanel subPanel = createJPanelFLL();
		subPanel.add(new JLabel(label));
		subPanel.add(component);
		return subPanel;
	}
	
	public static JPanel createRightPanelWithLabeledComponent(String label, JComponent component){
		JPanel subPanel = createJPanelFRL();
		subPanel.add(new JLabel(label));
		subPanel.add(component);
		return subPanel;
	}
	
	public static JPanel createJPanelWithEB(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());

		return panel;
	}

	public static JButton createCancelButton() {
		JButton btn = new JButton(uic.CANCEL());
		btn.setToolTipText(uic.CANCEL());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.CANCEL));
		return btn;
	}
	public static JButton createSearchButton() {
		JButton btn = new JButton(uic.SEARCH());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.SEARCH));
		btn.setToolTipText(uic.SEARCH());
		return btn;
	}
	
	public static JButton createPlusButton() {
		JButton btn = new JButton();
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.PLUS));
		btn.setPreferredSize(new Dimension(40, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createPlus2Button() {
		JButton btn = new JButton();
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.PLUS2));
		btn.setPreferredSize(new Dimension(40, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createPlus3Button() {
		JButton btn = new JButton();
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.PLUS3));
		btn.setPreferredSize(new Dimension(40, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createMinusButton() {
		JButton btn = new JButton();
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.MINUS));
		btn.setPreferredSize(new Dimension(40, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createOkButton() {
		JButton btn = new JButton(uic.OK());
		btn.setToolTipText(uic.OK());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));
		return btn;
	}
	
	public static JButton createSmallOkButton() {
		JButton btn = createOkButton();
		btn.setPreferredSize(new Dimension(80, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallOkButtonNoLabel() {
		JButton btn = createOkButton();
		btn.setText("");
		btn.setPreferredSize(new Dimension(20, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallBackButton() {
		JButton btn = new JButton(uic.BACK());
		btn.setPreferredSize(new Dimension(70, DEFAULT_BTN_HEIGHT));
		return btn;
	}

	public static JButton createClearButton() {
		JButton btn = new JButton(uic.CLEAR());
		btn.setToolTipText(uic.CLEAR());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.CLEAR));
		return btn;
	}
	
	public static JButton createSaveButton() {
		JButton btn = new JButton(uic.SAVE());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.SAVE));
		btn.setToolTipText(uic.SAVE());
		return btn;
	}

	public static JButton createResetButton() {
		JButton btn = new JButton(uic.REFRESH());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.REFRESH));
		btn.setToolTipText(uic.REFRESH());
		return btn;
	}
	
	public static JButton createSmallCancelButton() {
		JButton btn = createCancelButton();
		btn.setPreferredSize(new Dimension(90, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallCancelButtonNoLabel() {
		JButton btn = createCancelButton();
		btn.setText("");
		btn.setPreferredSize(new Dimension(21, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallSearchButton() {
		JButton btn = createSearchButton();
		btn.setPreferredSize(new Dimension(90, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallClearButton() {
		JButton btn = createClearButton();
		btn.setPreferredSize(new Dimension(85, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallClearButtonNoLabel() {
		JButton btn = createClearButton();
		btn.setText("");
		btn.setPreferredSize(new Dimension(20, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallSaveButton() {
		JButton btn = createSaveButton();
		btn.setPreferredSize(new Dimension(95, DEFAULT_BTN_HEIGHT));
		return btn;
	}

	public static JButton createSmallResetButton() {
		JButton btn = createResetButton();
		btn.setPreferredSize(new Dimension(95, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createSmallChangePasswordButton() {
		JButton btn = new JButton("Change password");
		btn.setPreferredSize(new Dimension(140, DEFAULT_BTN_HEIGHT));
		return btn;
	}

	public static JButton createSmallButton(String caption, int width) {
		JButton btn = createSmallButton(null, caption, width);
		return btn;
	}

	public static JButton createSmallButton(Icon logo, String caption, int width) {
		JButton btn = new JButton(caption);
		btn.setPreferredSize(new Dimension(width, DEFAULT_BTN_HEIGHT));
		btn.setMaximumSize(new Dimension(width, DEFAULT_BTN_HEIGHT));
		if (logo != null) {
			btn.setIcon(logo);
		}
		return btn;
	}
	
	public static void setEnabled(boolean b, Component component){
		if (!(component instanceof JButton) && !(component instanceof JLabel)){
			component.setEnabled(b);
			if (component instanceof Container)
				for (Component c : ((Container)component).getComponents())
					setEnabled(b,c);
		}
	}

	public static KasButton createAddButton(){
		KasButton btn = new KasButton(">");
		btn.setPreferredSize(new Dimension(50, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	public static KasButton createAddAllButton(){
		KasButton btn = new KasButton(">>");
		btn.setPreferredSize(new Dimension(50, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	public static JButton createButton(){
		JButton btn = new JButton();
		return btn;
	}
	public static KasButton createRemButton(){
		KasButton btn = new KasButton("<");
		btn.setPreferredSize(new Dimension(50, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	public static KasButton createRemAllButton(){
		KasButton btn = new KasButton("<<");
		btn.setPreferredSize(new Dimension(50,DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static KasScrollPane createScrollPane(Component c){
		KasScrollPane pane = createScrollPane(c, new Dimension(c.getPreferredSize().width+15, c.getPreferredSize().height+20));
		return pane;
	}
	
	public static KasScrollPane createScrollPane(Component c, Dimension size){
		KasScrollPane pane = new KasScrollPane(c, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(size);
		pane.setBorder(BorderFactory.createEmptyBorder());
		pane.setWheelScrollingEnabled(true);
		pane.getVerticalScrollBar().setUnitIncrement(10);
		return pane;
	}
	
	public static JButton createWordButton(){
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.WORD_SMALL));
		btn.setPreferredSize(new Dimension(21, DEFAULT_BTN_HEIGHT));
		btn.setToolTipText(uic.EXPORT_TO_FILE());
		return btn;
	}
	
	public static JButton createPublishButton(){
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.CALENDAR));
		btn.setPreferredSize(new Dimension(95, DEFAULT_BTN_HEIGHT));
		btn.setText(uic.PUBLISH());
		btn.setToolTipText(uic.PUBLISH());
		return btn;
	}

	public static JButton createTickedButton(int i) {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.OK));
		btn.setPreferredSize(new Dimension(i, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createDecreaseButton(int i) {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.DOWN_ARROW));
		btn.setPreferredSize(new Dimension(i, DEFAULT_BTN_HEIGHT));
		return btn;
	}

	public static JButton createBinocularButton() {
		return createBinocularButton(21);
	}

	public static JButton createBinocularButton(int width) {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.BINOCULAR));
		btn.setPreferredSize(new Dimension(width, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createNoBinocularButton(int width) {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.NO_BINOCULAR));
		btn.setPreferredSize(new Dimension(width, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createNoBinocularButton() {
		return createNoBinocularButton(21);
	}
	
	public static JButton createArrowDownPaperButton() {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.ARROW_DOWN_PAPER));
		btn.setPreferredSize(new Dimension(21, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static JButton createArchiveButton() {
		JButton btn = new JButton(ResourceHelper.getImageIcon(ResourceHelper.ARCHIVE));
		btn.setText(uic.TO_ARCHIVE());
		btn.setPreferredSize(new Dimension(81, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	
	public static KASLinkLabel createOkLink() {
		KASLinkLabel ok = new KASLinkLabel(uic.OK());
		ok.setIcon(ResourceHelper.getImageIcon(ResourceHelper.OK));
		return ok;
	}

	public static KASLinkLabel createClearLink() {
		KASLinkLabel link = new KASLinkLabel(uic.CLEAR());
		link.setIcon(ResourceHelper.getImageIcon(ResourceHelper.CLEAR));
		return link;
	}

	public static KASLinkLabel createCancelLink() {
		KASLinkLabel link = new KASLinkLabel(uic.CANCEL());
		link.setIcon(ResourceHelper.getImageIcon(ResourceHelper.CANCEL));
		return link;
	}

	public static JButton createSmallUploadButton() {
		JButton btn = new JButton(uic.UPLOAD());
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.UPLOAD));
		btn.setPreferredSize(new Dimension(120, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	public static JButton createExcelButton(){
		JButton btnExportToExcel = new JButton(ResourceHelper.getImageIcon(ResourceHelper.EXCEL));
		btnExportToExcel.setPreferredSize(new Dimension(21, DEFAULT_BTN_HEIGHT));
		btnExportToExcel.setToolTipText(uic.EXPORT_TO_EXCEL_FILE());
		return btnExportToExcel;
	}

	public static JButton createExportButton(){
		JButton btnExportToExcel = new JButton(ResourceHelper.getImageIcon(ResourceHelper.AUTO_INSERTION));
		btnExportToExcel.setPreferredSize(new Dimension(21, DEFAULT_BTN_HEIGHT));
		btnExportToExcel.setToolTipText(uic.EXPORT_CONTENT_TO_FILE());
		return btnExportToExcel;
	}
	
    public static void refreshComponentTreeUI(Component c) {
    	refreshComponentTreeUIByType(c);
        c.invalidate();
        c.validate();
        c.repaint();
    }

    private static void refreshComponentTreeUIByType(Component c) {
        if (c instanceof JComponent) {
            ((JComponent) c).updateUI();
        }
        Component[] children = null;
        if (c instanceof JMenu) {
            children = ((JMenu)c).getMenuComponents();
        }
        else if (c instanceof Container) {
            children = ((Container)c).getComponents();
        }
        if (children != null) {
            for(int i = 0; i < children.length; i++) {
            	refreshComponentTreeUIByType(children[i]);
            }
        }
    }
    
	public static List<Class> getLookAndFeelList(){
		if (lookAndFeelList == null){
			lookAndFeelList = new ArrayList<Class>();
			
			lookAndFeelList.add(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class);
//			lookAndFeelList.add(com.sun.java.swing.plaf.motif.MotifLookAndFeel.class);
			
			lookAndFeelList.add(com.jgoodies.looks.plastic.PlasticLookAndFeel.class);
			lookAndFeelList.add(com.jgoodies.looks.plastic.Plastic3DLookAndFeel.class);
			lookAndFeelList.add(com.jgoodies.looks.plastic.PlasticXPLookAndFeel.class);
			
			lookAndFeelList.add(javax.swing.plaf.metal.MetalLookAndFeel.class);
		}
		return lookAndFeelList;
	}
	
	public static List<Class> getThemeList() {
		if (themeList == null){
			themeList = new ArrayList<Class>();
			
			themeList.add(OceanTheme.class);
			themeList.add(BrownSugar.class);
			themeList.add(DarkStar.class);
			themeList.add(LightGray.class);
			themeList.add(Silver.class);
			
			themeList.add(SkyBlue.class);
			themeList.add(SkyBluer.class);
			themeList.add(SkyGreen.class);
			themeList.add(SkyKrupp.class);
			themeList.add(SkyPink.class);
			themeList.add(SkyRed.class);
			themeList.add(SkyYellow.class);
			
			themeList.add(DesertBlue.class);
			themeList.add(DesertBluer.class);
			themeList.add(DesertGreen.class);
			themeList.add(DesertRed.class);
			themeList.add(DesertYellow.class);
			
			themeList.add(ExperienceBlue.class);
			themeList.add(ExperienceGreen.class);
			themeList.add(ExperienceRoyale.class);
		}
		return themeList;
	}
	
	public static Map<String, Class> getLookAndFeelMap() {
		if (lookAndFeelMap == null){
			lookAndFeelMap = new HashMap<String, Class>();
			for (Class lf : UIFactory.getLookAndFeelList()){
				lookAndFeelMap.put(lf.getSimpleName().replace("LookAndFeel", ""), lf);
			}
		}
		return lookAndFeelMap;
	}
	
	public static Map<String, Class> getThemeMap() {
		if (themeMap == null){
			themeMap = new HashMap<String, Class>();
			for (Class theme : UIFactory.getThemeList()){
				themeMap.put(theme.getSimpleName(), theme);
			}
		}
		return themeMap;
	}

	public static void installLAF(String lafName, String themeName){
		try {
			Class laf = getLookAndFeelMap().get(lafName);
			LookAndFeel lfObject = (LookAndFeel)laf.newInstance();
			
			Class theme = getThemeMap().get(themeName);
			MetalTheme themeObject = (MetalTheme)theme.newInstance();
			
			if (lafName.endsWith("Metal")) {
				MetalLookAndFeel.setCurrentTheme(themeObject);
			} 
			else if (lafName.endsWith("Plastic")) {
				PlasticLookAndFeel.setCurrentTheme(themeObject);
			} 
			else if (lafName.endsWith("Plastic3D")) {
				Plastic3DLookAndFeel.setCurrentTheme(themeObject);
			} 
			else if (lafName.endsWith("PlasticXP")) {
				PlasticXPLookAndFeel.setCurrentTheme(themeObject);
			}
			
			
			UIManager.installLookAndFeel(lfObject.getName(), laf.getClass().getName());
			UIManager.setLookAndFeel(lfObject);
		} catch (Exception e) {
			e.printStackTrace();
			MessageHelper.showErrorMessage(null, uic.CAN_NOT_SET_LOOK_AND_FEEL());
		}
		UIFactory.refreshComponentTreeUI(ApplicationManager.getInstance().getMainFrame());
	}
	
	public static boolean canPerformThemes(String lafName){
		if (lafName == null) {
			return false;
		}
		
		boolean flag = false;
		if (lafName.endsWith("Metal") || lafName.endsWith("Plastic") ||
				lafName.endsWith("Plastic3D") || lafName.endsWith("PlasticXP")) {
			flag =  true;
		}
		return flag;
	}

	public static void installLanguageForDockable(UIC uic) {
		DockingUISettings.getInstance().installUI();
		
		UIManager.put("DockViewTitleBar.closeButtonText", uic.CLOSE());
		UIManager.put("DockViewTitleBar.minimizeButtonText", uic.MINIMIZE());
		UIManager.put("DockViewTitleBar.maximizeButtonText", uic.MAXIMIZE());
		UIManager.put("DockViewTitleBar.restoreButtonText", uic.RESTORE());
		UIManager.put("DockViewTitleBar.floatButtonText", uic.DETACH());
		UIManager.put("DockViewTitleBar.attachButtonText", uic.ATTACH());
		
		UIManager.put("DockTabbedPane.closeButtonText", uic.CLOSE());
		UIManager.put("DockTabbedPane.minimizeButtonText", uic.MINIMIZE());
		UIManager.put("DockTabbedPane.maximizeButtonText", uic.MAXIMIZE());
		UIManager.put("DockTabbedPane.restoreButtonText", uic.RESTORE());
		UIManager.put("DockTabbedPane.floatButtonText", uic.DETACH());
		UIManager.put("DockTabbedPane.attachButtonText", uic.ATTACH());
	}

	public static KASTextField createKASTextField() {
		KASTextField txt = new KASTextField();
		txt.setPreferredSize(new Dimension(150, txt.getPreferredSize().height));
		return txt;
	}
	
	public static <T extends Object> T createObject(Class<T> clazz, Object ... constructorParams) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor c = findAppropriateConstructor(clazz.getConstructors(), constructorParams);
		if (c == null) {
			KASDebuger.println("Could not find class '"+clazz+"' constructor for specified params '"+constructorParams+"'");
			return null;
		}
		return (T)c.newInstance(constructorParams);
	}
	
	private static Constructor findAppropriateConstructor(Constructor[] constructors, Object... constructorParams){
		if (constructorParams == null || constructorParams.length == 0){
			for (Constructor c : constructors){
				if (c.getParameterTypes() == null || c.getParameterTypes().length == 0)
					return c;
			}
			return null;
		}
			
		for (Constructor c : constructors){
			if (c.getParameterTypes() != null && c.getParameterTypes().length == constructorParams.length){
				boolean match = compareConstructorParamTypes(c.getParameterTypes(), constructorParams);
				if (match){
					return c;
				}
			}
		}
		
		return null;
	}
	
	private static boolean compareConstructorParamTypes(Class[] paramClasses, Object ... constructorParams){
		for (Class paramType : paramClasses){
			boolean paramTypesEquals = false;
			for (Object param : constructorParams){
				if (compareTypes(paramType, param.getClass())){
					paramTypesEquals = true;
					break;
				}
			}
			if (!paramTypesEquals)
				return false;
		}
		return true;
	}
	
	private static boolean compareTypes(Class type1, Class type2){
		if (type1.equals(type2))
			return true;
		for (Class inter : type2.getInterfaces()){
			if (type1.equals(inter))
				return true;
		}
		return false;
	}

	public static void unlinkAnyExistedModelsAndViews(Component c) {
		if (c instanceof UIView) {
			UIView viewUI = (UIView)c;

			AKAbstractModelUI.performUnlink(viewUI);
		}
		if (c instanceof Container) {
			for (Component component : ((Container)c).getComponents()){
				unlinkAnyExistedModelsAndViews(component);
			}
		}
	}

	public static JButton createSmallCopyButton(int width) {
		JButton btn = createSmallButon(width);
		btn.setIcon(ResourceHelper.getImageIcon(ResourceHelper.COPY));
		return btn;
	}

	public static JButton createSmallButon(int width) {
		JButton btn = new JButton();
		btn.setPreferredSize(new Dimension(width, DEFAULT_BTN_HEIGHT));
		return btn;
	}
	
	public static String toBold(String s) {
		return "<html><b>" + s + "</b></html>"; 
	}

	public static String toBoldWithGap(String s, long shift) {
		String txt = "<html><b>";
		for (long i = 0; i < shift; i++) {
			txt += GAP;
		}
		txt += s + "</b></html>";
		return txt;  
	}
	
	public static JLabel createBoldShiftedLabel(String text, long shift) {
		return new JLabel(toBoldWithGap(text, shift));
	}
	
}