/**
 * The file ComboUIHelper.java was created on 2010.30.3 at 23:47:26
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;

import org.apache.commons.collections.map.HashedMap;

import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.ChampionshipService;
import com.ashihara.datamanagement.interfaces.CountryService;
import com.ashihara.datamanagement.interfaces.FighterService;
import com.ashihara.datamanagement.interfaces.FightingGroupService;
import com.ashihara.datamanagement.interfaces.WeightCategoryService;
import com.ashihara.datamanagement.interfaces.YearCategoryService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.Country;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.WeightCategory;
import com.ashihara.datamanagement.pojo.YearCategory;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.ashihara.enums.SC.Captionable;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.component.combo.ComboBoxItem;
import com.ashihara.ui.core.component.combo.KASComboBox;
import com.ashihara.ui.tools.ApplicationManager;
import com.rtu.exception.PersistenceException;

public class ComboUIHelper {
	public static class StringComparator implements Comparator<String>{
		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	}

	public static void reloadCountryCombo(AutoCompleteComboBox<Country> combo, boolean needNull) throws PersistenceException {
		List<Country> items = new ArrayList<Country>();
		items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(CountryService.class).loadAll();
		fillUpEntrySetForCombo(combo, items, needNull);
	}

	public static void fillUpEntrySetForCombo(JComboBox combo, List<? extends Object> entrySet, boolean needNullValue){
		combo.removeAllItems();
		if (needNullValue) {
			combo.addItem(null);
		}
		
		if (entrySet!=null) {
			for (Object o : entrySet) {
				combo.addItem(o);
			}
		}
	}

	public static void reloadWeightCategoryCombo(JComboBox combo, boolean needNull) throws PersistenceException {
		List<WeightCategory> items = new ArrayList<WeightCategory>();
		items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(WeightCategoryService.class).loadAll();
		fillUpEntrySetForCombo(combo, items, needNull);
	}
	
	public static void reloadLAFCombo(KASComboBox cmb, Set<String> classNames, boolean flag){
		List<String> entries = new ArrayList<String>();
		for (String cn : classNames) {
			entries.add(cn);
		}
		Collections.sort(entries, new StringComparator());
		fillUpEntrySetForCombo(cmb, entries, flag);
	}

	public static void reloadYearCategoryCombo(JComboBox combo, boolean needNull) throws PersistenceException {
		List<YearCategory> items = new ArrayList<YearCategory>();
		items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(YearCategoryService.class).loadAll();
		fillUpEntrySetForCombo(combo, items, needNull);
	}

	public static void reloadGroupTypeCombo(KASComboBox cmb, UIC uic, boolean needNull) {
		fillUpCaptionableWithCMItems(cmb, new SC.GROUP_TYPE(), needNull, uic);
	}

	public static <T> void fillUpCaptionableWithCMItems(KASComboBox combo, List<T> entrySet, Captionable<T> captionable, boolean needNullValue, UIC uic) {
		List<ComboBoxItem<T>> list = new ArrayList<ComboBoxItem<T>>();
		
		for (T idItem : entrySet) {
			T caption = captionable.getUICaption(idItem, uic);
			ComboBoxItem<T> cbItem = new ComboBoxItem<T>();
			
			cbItem.setId(idItem);
			cbItem.setUiCaption(caption);
			
			list.add(cbItem);
		}
		
		fillUpEntrySetForCombo(combo, list, needNullValue);
	}

	public static <T> void fillUpCaptionableWithCMItems(KASComboBox combo, Captionable<T> captionable, boolean needNullValue, UIC uic) {
		List<T> entrySet = new ArrayList<T>();
		for (T idItem : captionable.getAllValues()) {
			entrySet.add(idItem);
		}
		
		fillUpCaptionableWithCMItems(combo, entrySet, captionable, needNullValue, uic);
	}

	public static void reloadFightersSuitableForGroup(
			JComboBox cmb,
			FightingGroup group,
			boolean needNull,
			List<GroupChampionshipFighter> selected
	) throws PersistenceException {
		List<ChampionshipFighter> items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(FighterService.class).loadFightersSuitableForGroup(group);
		
//		List<ChampionshipFighter> filtered = removeSelected(items, selected);
		fillUpEntrySetForCombo(cmb, items, needNull);
	}
	
	private static List<ChampionshipFighter> removeSelected(
			List<ChampionshipFighter> items,
			List<GroupChampionshipFighter> selected
	) {
		Map<Long, ChampionshipFighter> map = new HashMap<Long, ChampionshipFighter>();
		
		if (items != null) {
			for (ChampionshipFighter cf : items) {
				map.put(cf.getId(), cf);
			}
		}
		
		if (selected != null) {
			for (GroupChampionshipFighter gcf : selected) {
				if (gcf.getChampionshipFighter() != null) {
					map.remove(gcf.getChampionshipFighter().getId());
				}
			}
		}
		
		List<ChampionshipFighter> result = new ArrayList<ChampionshipFighter>();
		result.addAll(map.values());
		
		return result;
	}

	public static void reloadFightersByGroup(JComboBox cmb, FightingGroup group, boolean needNull) throws PersistenceException {
		List<ChampionshipFighter> items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(FighterService.class).loadFightersByGroup(group);
		fillUpEntrySetForCombo(cmb, items, needNull);
	}
	
	public static void reloadChampionshipFighters(JComboBox cmb, Championship championship, boolean needNull) throws PersistenceException {
		List<ChampionshipFighter> items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(ChampionshipService.class).loadChampionshipFighters(championship);
		fillUpEntrySetForCombo(cmb, items, needNull);
	}
	
	public static void reloadChampionshipGroups(KASComboBox cmb, Championship championship) throws PersistenceException {
		List<FightingGroup> items = AKServerSessionManagerImpl.getInstance().getServerSession(
				ApplicationManager.getInstance().getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory().
				getService(FightingGroupService.class).loadGroups(championship);
		fillUpEntrySetForCombo(cmb, items, true);
	}

}
