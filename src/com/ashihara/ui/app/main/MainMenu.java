/**
 * The file MainMenu.java was created on 2010.21.3 at 21:01:38
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.ui.app.championship.ChampionshipEditFrame;
import com.ashihara.ui.app.championship.ChampionshipSearchResultFrame;
import com.ashihara.ui.app.country.CountrySearchFrame;
import com.ashihara.ui.app.fighter.FightersSearchFrame;
import com.ashihara.ui.app.points.PointsDetailsDialog;
import com.ashihara.ui.app.settings.PreferencesDialog;
import com.ashihara.ui.app.weightCategory.WeightCategorySearchFrame;
import com.ashihara.ui.app.yearCategory.YearCategorySearchFrame;
import com.ashihara.ui.core.menu.AKMenuBar;
import com.ashihara.ui.core.menu.KASMenuItem;
import com.ashihara.ui.tools.ApplicationManager;

public class MainMenu extends AKMenuBar {

	private static final long serialVersionUID = 1L;
	
	private JMenu menuChampionship;
	private KASMenuItem itemSearchChampionship;
	private KASMenuItem itemNewChampionship;
	
	private JMenu menuPreset;
	private KASMenuItem itemCountries;
	private KASMenuItem itemPoints;
	private KASMenuItem itemFighters;

	private JMenu menuCategory;
	private KASMenuItem itemWeightCategories;
	private KASMenuItem itemYearCategories;
	
	private JMenu menuPreferences;
	private KASMenuItem itemLookAndFeel;
	
	
	@Override
	public void afterInit() {
	}

	@Override
	public void beforeInit() {
		add(getMenuChampionship());
		add(getMenuPreset());
		add(getMenuCategory());
		add(getMenuPreferences());
	}

	public JMenu getMenuChampionship() {
		if (menuChampionship == null) {
			menuChampionship = new JMenu(ApplicationManager.getInstance().getUic().CHAMPIONSHIP());
			
			menuChampionship.add(getItemSearchChampionship());
			menuChampionship.add(getItemNewChampionship());
		}
		return menuChampionship;
	}

	public JMenu getMenuPreset() {
		if (menuPreset == null) {
			menuPreset = new JMenu(ApplicationManager.getInstance().getUic().PRESET());
			
			menuPreset.add(getItemCountries());
			menuPreset.add(getItemFighters());
			menuPreset.add(getItemPoints());
		}
		return menuPreset;
	}

	public JMenuItem getItemFighters() {
		if (itemFighters == null) {
			itemFighters = new KASMenuItem(ApplicationManager.getInstance().getUic().FIGHTERS());
			itemFighters.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openModalFrame(FightersSearchFrame.class);
				}
			});
		}
		return itemFighters;
	}

	public JMenuItem getItemCountries() {
		if (itemCountries == null) {
			itemCountries = new KASMenuItem(ApplicationManager.getInstance().getUic().COUNTRIES());
			itemCountries.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openModalFrame(CountrySearchFrame.class);
				}
			});
		}
		return itemCountries;
	}

	public JMenuItem getItemWeightCategories() {
		if (itemWeightCategories == null) {
			itemWeightCategories = new KASMenuItem(ApplicationManager.getInstance().getUic().WEIGHT_CATEGORIES());
			itemWeightCategories.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openModalFrame(WeightCategorySearchFrame.class);
				}
			});
		}
		return itemWeightCategories;
	}

	public JMenuItem getItemYearCategories() {
		if (itemYearCategories == null) {
			itemYearCategories = new KASMenuItem(ApplicationManager.getInstance().getUic().YEAR_CATEGORIES());
			itemYearCategories.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openModalFrame(YearCategorySearchFrame.class);
				}
			});
		}
		return itemYearCategories;
	}

	public JMenu getMenuCategory() {
		if (menuCategory == null) {
			menuCategory = new JMenu(ApplicationManager.getInstance().getUic().CATEGORY());
			
			menuCategory.add(getItemWeightCategories());
			menuCategory.add(getItemYearCategories());
		}
		return menuCategory;
	}

	public JMenuItem getItemSearchChampionship() {
		if (itemSearchChampionship == null) {
			itemSearchChampionship = new KASMenuItem(ApplicationManager.getInstance().getUic().SEARCH_CHAMPIONSHIP());
			itemSearchChampionship.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openModalFrame(ChampionshipSearchResultFrame.class);
				}
			});
		}
		return itemSearchChampionship;
	}

	public JMenuItem getItemNewChampionship() {
		if (itemNewChampionship == null) {
			itemNewChampionship = new KASMenuItem(ApplicationManager.getInstance().getUic().NEW_CHAMPIONSHIP());
			itemNewChampionship.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ApplicationManager.getInstance().openIdentifiedFrame(ChampionshipEditFrame.class, null, new Championship());
				}
			});
			
		}
		return itemNewChampionship;
	}

	public KASMenuItem getMenuLookAndFeel() {
		if (itemLookAndFeel == null){
			itemLookAndFeel = new KASMenuItem(uic.LOOK_AND_FEEL());
			itemLookAndFeel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					appManager.openDialog(PreferencesDialog.class);
				}
			});
		}
		return itemLookAndFeel;
	}

	public JMenu getMenuPreferences() {
		if (menuPreferences == null) {
			menuPreferences = new JMenu(ApplicationManager.getInstance().getUic().PREFERENCES());
			
			menuPreferences.add(getMenuLookAndFeel());
		}
		return menuPreferences;
	}

	public KASMenuItem getItemPoints() {
		if (itemPoints == null){
			itemPoints = new KASMenuItem(uic.FIGHT_SETTINGS());
			itemPoints.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					appManager.openDialog(PointsDetailsDialog.class);
				}
			});
		}
		return itemPoints;
	}

}
