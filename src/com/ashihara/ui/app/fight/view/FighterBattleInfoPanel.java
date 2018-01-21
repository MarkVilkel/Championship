/**
 * The file InviteToFightPanel.java was created on 2010.14.4 at 21:21:49
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.FighterPhotoService;
import com.ashihara.datamanagement.pojo.AbstractBlob;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FighterPhoto;
import com.ashihara.ui.app.fight.view.CountPanel.CountListener;
import com.ashihara.ui.core.component.KASLabel;
import com.ashihara.ui.core.panel.ImageIconPanel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.utils.DataManagementUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.persistence.TransactionType;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.service.core.ServerSideServiceFactory;

public class FighterBattleInfoPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private KASLabel txtName, txtSurname, txtWeight, txtNumber, txtKyu, txtDan, txtFullYears, txtCountry;
	private GradientPanel fighterDetailsPanel;
	
	private final ChampionshipFighter championshipFighter;
	private final Color color;
	
	private ImageIconPanel imagePanel;
	
	private final int NORMAL_SIZE = 30;
	private final int BIG_SIZE = 40;
	private final int LARGE_SIZE = 60;
	
	private CountPanel pointsPanel;
	private CountPanel firstCategoryPanel;
	private CountPanel secondCategoryPanel;
	
	public FighterBattleInfoPanel(
			ChampionshipFighter championshipFighter,
			Color color
	){
		super();
		
		this.championshipFighter = championshipFighter;
		this.color = color;
		
		init();
	}

	private void init() {
		copyOnUI();
		
		add(getDetailsUIPanel(), BorderLayout.CENTER);
		
		setBorder(BorderFactory.createEtchedBorder());
		
	}

	private void copyOnUI() {
		getTxtCountry().setText(championshipFighter.getFighter().getCountry().toString());
		getTxtDan().setText(championshipFighter.getFighter().getDan() == null ? "" : championshipFighter.getFighter().getDan().toString());
		getTxtKyu().setText(championshipFighter.getFighter().getKyu() == null ? "" : championshipFighter.getFighter().getKyu().toString());
		getTxtFullYears().setText(championshipFighter.getFighter().getFullYearsOld().toString());
		getTxtName().setText(championshipFighter.getFighter().getName());
		getTxtSurname().setText(championshipFighter.getFighter().getSurname());
		getTxtWeight().setText(championshipFighter.getFighter().getWeight().toString());
		getTxtNumber().setText(String.valueOf(championshipFighter.getNumber()));
	}
	
	private KASLabel createLbl(int size) {
		KASLabel lbl = new KASLabel();
		
		lbl.setFont(new Font(lbl.getFont().getName(), lbl.getFont().getStyle(), size));
		lbl.setBold();
		
		return lbl;
	}
	private KASLabel createLbl() {
		return createLbl(NORMAL_SIZE);
	}
	
	private KASLabel createCaptionLbl(String text, int size, boolean underline) {
		KASLabel lbl = new KASLabel();
		
		lbl.setFont(new Font(lbl.getFont().getName(), lbl.getFont().getStyle(), size));
		lbl.setUnderline(underline);
		lbl.setText(text);
		
		return lbl;
	}
	
	private KASLabel createCaptionLbl(String text, int size) {
		return createCaptionLbl(text, size, true);
	}
	
	private KASLabel createCaptionLbl(String text) {
		return createCaptionLbl(text, NORMAL_SIZE);
	}

	public KASPanel getDetailsUIPanel() {
		if (fighterDetailsPanel == null){
//			fighterDetailsPanel = new GradientPanel(color);
//			FormLayout fl = new FormLayout(
//					"right:75dlu, pref, pref, 20dlu, pref",
//					"pref, 7dlu, pref, 7dlu, pref, 7dlu, " +
//					"pref, 7dlu, pref, 7dlu, pref, 7dlu, " +
//					"pref, 7dlu, pref, 20dlu, pref, 7dlu, " +
//					"pref, 7dlu, pref, 7dlu, pref, 7dlu, pref"
//			);
//
//			CellConstraints cc = new CellConstraints();
//
//			PanelBuilder builder = new PanelBuilder(fl, fighterDetailsPanel);
//			
//			builder.add(createCaptionLbl(uic.NUMBER()+"  ", BIG_SIZE), cc.xy(3, 3));
//			builder.add(getTxtNumber(), cc.xy(5, 3));
//			
//			builder.add(createCaptionLbl(uic.NAME()+"  "), cc.xy(3, 5));
//			builder.add(getTxtName(), cc.xy(5, 5));
//			
//			builder.add(createCaptionLbl(uic.SURNAME()+"  "), cc.xy(3, 7));
//			builder.add(getTxtSurname(), cc.xy(5, 7));
//
//			builder.add(createCaptionLbl(uic.COUNTRY()+"  "), cc.xy(3, 9));
//			builder.add(getTxtCountry(), cc.xy(5, 9));
//			
//			builder.add(createCaptionLbl(uic.FULL_YEARS()+"  "), cc.xy(3, 11));
//			builder.add(getTxtFullYears(), cc.xy(5, 11));
//			
//			builder.add(createCaptionLbl(uic.WEIGHT()+"  "), cc.xy(3, 13));
//			builder.add(getTxtWeight(), cc.xy(5, 13));
//			
//			if (fighter.getKyu() != null) {
//				builder.add(createCaptionLbl(uic.KYU()+"  "), cc.xy(3, 15));
//				builder.add(getTxtKyu(), cc.xy(5, 15));
//			}
//			else {
//				builder.add(createCaptionLbl(uic.DAN()+"  "), cc.xy(3, 15));
//				builder.add(getTxtDan(), cc.xy(5, 15));
//			}
//			
//			builder.add(createCaptionLbl(uic.POINTS()+":  ", BIG_SIZE, false), cc.xy(3, 17));
//			builder.add(getPointsPanel(), cc.xy(5, 17));
//			
//			builder.add(createCaptionLbl(uic.FIRST_CATEGORY()+":  ", BIG_SIZE, false), cc.xy(3, 19));
//			builder.add(getFirstCategoryPanel(), cc.xy(5, 19));
//
//			builder.add(createCaptionLbl(uic.SECOND_CATEGORY()+":  ", BIG_SIZE, false), cc.xy(3, 21));
//			builder.add(getSecondCategoryPanel(), cc.xy(5, 21));
			
			fighterDetailsPanel = new GradientPanel(color);
			fighterDetailsPanel.setLayout(new GridBagLayout());
			
	    	GridBagConstraints c = new GridBagConstraints();
	    	
	    	c.weighty = 0.5;
	    	c.weightx = 0.5;
	    	c.gridx = 0;
	    	c.gridy = 0;
	    	
//	    	c.fill = GridBagConstraints.VERTICAL;
	    	
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.NUMBER(), BIG_SIZE), getTxtNumber()), c);
	    	
	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.NAME()), getTxtName()), c);
	    	
	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.SURNAME()), getTxtSurname()), c);

	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.COUNTRY()), getTxtCountry()), c);
	    	
	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.AGE()), getTxtFullYears()), c);

	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.WEIGHT()), getTxtWeight()), c);
	    	
	    	c.ipady = 10;
	    	c.gridy ++;
	    	if (championshipFighter.getFighter().getKyu() != null) {
	    		fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.KYU()), getTxtKyu()), c);
	    	}
	    	else {
	    		fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.DAN()), getTxtDan()), c);
	    	}

	    	c.ipady = 0;
	    	c.weighty = 0.3;
	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.POINTS()+":", LARGE_SIZE, false), getPointsPanel()), c);

	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.FIRST_CATEGORY()+":", BIG_SIZE, false), getFirstCategoryPanel()), c);

	    	c.gridy ++;
	    	fighterDetailsPanel.add(createLabelValuePanel(createCaptionLbl(uic.SECOND_CATEGORY()+":", BIG_SIZE, false), getSecondCategoryPanel()), c);

		}
		
		return fighterDetailsPanel;
	}
	
	private JPanel createLabelValuePanel(JComponent c1, JComponent c2) {
    	JPanel panel = new JPanel(new GridLayout(1, 1, 20, 0));
    	panel.setOpaque(false);
    	
    	JPanel left = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    	left.add(c1);
    	left.setOpaque(false);

    	JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    	right.add(c2);
    	right.setOpaque(false);
    	
    	panel.add(left);
    	panel.add(right);
    	
    	return panel;
	}

	public KASLabel getTxtName() {
		if (txtName == null){
			txtName = createLbl();
		}
		return txtName;
	}

	public KASLabel getTxtSurname() {
		if (txtSurname == null){
			txtSurname = createLbl();
		}
		return txtSurname;
	}

	public KASLabel getTxtWeight() {
		if (txtWeight == null) {
			txtWeight = createLbl();
		}
		return txtWeight;
	}
	
	public KASLabel getTxtNumber() {
		if (txtNumber == null) {
			txtNumber = createLbl(BIG_SIZE);
		}
		return txtNumber;
	}

	public ImageIconPanel getImagePanel() {
		if (imagePanel == null) {
			FighterPhoto photo;
			try {
				photo = getPhotoService().loadByFighter(championshipFighter.getFighter());
				imagePanel = new ImageIconPanel(getImage(photo));
			} catch (PersistenceException e) {
				e.printStackTrace();
				imagePanel = new ImageIconPanel(null);
			} catch (AKBusinessException e) {
				e.printStackTrace();
				imagePanel = new ImageIconPanel(null);
			}
			imagePanel.setPreferredSize(new Dimension(150, 300));
		}
		return imagePanel;
	}
	
	private ImageIcon getImage(AbstractBlob photo) throws AKBusinessException {
		ImageIcon ii = (ImageIcon)DataManagementUtils.toObject(photo.getBlob());
		return ii;
	}
	
	public KASLabel getTxtKyu() {
		if (txtKyu == null) {
			txtKyu = createLbl();
		}
		return txtKyu;
	}

	public KASLabel getTxtDan() {
		if (txtDan == null) {
			txtDan = createLbl();
		}
		return txtDan;
	}

	public KASLabel getTxtFullYears() {
		if (txtFullYears == null) {
			txtFullYears = createLbl();
		}
		return txtFullYears;
	}

	public KASLabel getTxtCountry() {
		if (txtCountry == null) {
			txtCountry = createLbl();
		}
		return txtCountry;
	}
	
	protected ServerSideServiceFactory<DMIdentifiedService> getServerSideServiceFactory() {
		AKClientSession session = ApplicationManager.getInstance().getClientSession();
		return AKServerSessionManagerImpl.getInstance().getServerSession(session.getSessionHandler().getSessionId()).getServerSideServiceFactory(); 
	}
	
	protected FighterPhotoService getPhotoService() {
		return getServerSideServiceFactory().getService(FighterPhotoService.class, TransactionType.COMMIT_TRANSACTION);
	}

	public CountPanel getPointsPanel() {
		if (pointsPanel == null) {
			pointsPanel = new CountPanel(LARGE_SIZE, 0, 100);
		}
		return pointsPanel;
	}

	public CountPanel getFirstCategoryPanel() {
		if (firstCategoryPanel == null) {
			firstCategoryPanel = new StarCountPanel(LARGE_SIZE, BIG_SIZE, 0, 4);
		}
		return firstCategoryPanel;
	}

	public CountPanel getSecondCategoryPanel() {
		if (secondCategoryPanel == null) {
			secondCategoryPanel = new StarCountPanel(LARGE_SIZE, BIG_SIZE, 0, 4);
		}
		return secondCategoryPanel;
	}

	public void setWarningsChangeListener(CountListener warningsChangeListener) {
		getSecondCategoryPanel().addCountListener(warningsChangeListener);
		getFirstCategoryPanel().addCountListener(warningsChangeListener);
		
	}
	
	public void addPointsChangeListener(CountListener pointsChangeListener) {
		getPointsPanel().addCountListener(pointsChangeListener);
	}

}
