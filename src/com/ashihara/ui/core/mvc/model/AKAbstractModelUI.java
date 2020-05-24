/**
 * The file AKAbstractModelUI.java was created on 2009.8.1 at 15:51:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.mvc.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.core.session.AKClientSession;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.ChampionshipPlanService;
import com.ashihara.datamanagement.interfaces.ChampionshipService;
import com.ashihara.datamanagement.interfaces.CountryService;
import com.ashihara.datamanagement.interfaces.FightResultService;
import com.ashihara.datamanagement.interfaces.FightSettingsService;
import com.ashihara.datamanagement.interfaces.FighterPhotoService;
import com.ashihara.datamanagement.interfaces.FighterService;
import com.ashihara.datamanagement.interfaces.FightingGroupService;
import com.ashihara.datamanagement.interfaces.MigrationService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.interfaces.WeightCategoryService;
import com.ashihara.datamanagement.interfaces.YearCategoryService;
import com.ashihara.enums.CM;
import com.ashihara.enums.UIC;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.tools.ApplicationManager;
import com.rtu.persistence.TransactionType;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.service.core.ServerSideServiceFactory;

public abstract class AKAbstractModelUI<T extends UIView> implements UIModel<T> {
	protected static ApplicationManager appManager = ApplicationManager.getInstance();
	protected static UIC uic = appManager.getUic();
	
	@Override
	public void finishAsynchronicCall(UIModel clazz, Method method, Object[] args, Object invocationResult){
		
	}
	
	
	protected ServerSideServiceFactory<DMIdentifiedService> getServerSideServiceFactory() {
		AKClientSession session = ApplicationManager.getInstance().getClientSession();
		return AKServerSessionManagerImpl.getInstance().getServerSession(session.getSessionHandler().getSessionId()).getServerSideServiceFactory(); 
	}
	
	protected FighterService getFighterService() {
		return getServerSideServiceFactory().getService(FighterService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected CountryService getCountryService() {
		return getServerSideServiceFactory().getService(CountryService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected FighterPhotoService getPhotoService() {
		return getServerSideServiceFactory().getService(FighterPhotoService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected WeightCategoryService getWeightCategoryService() {
		return getServerSideServiceFactory().getService(WeightCategoryService.class, TransactionType.COMMIT_TRANSACTION);
	}

	protected YearCategoryService getYearCategoryService() {
		return getServerSideServiceFactory().getService(YearCategoryService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected UserService getUserService() {
		return getServerSideServiceFactory().getService(UserService.class, TransactionType.COMMIT_TRANSACTION);
	}

	protected ChampionshipService getChampionshipService() {
		return getServerSideServiceFactory().getService(ChampionshipService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected ChampionshipPlanService getChampionshipPlanService() {
		return getServerSideServiceFactory().getService(ChampionshipPlanService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected FightingGroupService getGroupService() {
		return getServerSideServiceFactory().getService(FightingGroupService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected FightSettingsService getFightSettingsService() {
		return getServerSideServiceFactory().getService(FightSettingsService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected FightResultService getFightResultService() {
		return getServerSideServiceFactory().getService(FightResultService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	protected MigrationService getMigrationService() {
		return getServerSideServiceFactory().getService(MigrationService.class, TransactionType.COMMIT_TRANSACTION);
	}
	
	@Override
	public void unlink() {
		performUnlink(this);
	}
	
	public static void performUnlink(UIView viewUI) {
		viewUI.getModelUI().setViewUI(null);
		viewUI.setModelUI(null);
	}
	
	public static void performUnlink(AKAbstractModelUI model) {
		UIView view = model.getViewUI(); 
		view.setModelUI(null);
		model.setViewUI(null);
	}
	
	public static void performUnlink(List<? extends AKAbstractModelUI> models) {
		for (AKAbstractModelUI model : models) {
			performUnlink(model);
		}
	}
	
	protected <T extends Object> List<T> asList(T ... tt) {
		List<T> list = new ArrayList<T>();
		for (T t : tt) {
			list.add(t);
		}
		return list;
	}
	
//	Conceptual Models
	protected CM.User getCmUser() { 
		return new CM.User();
	}
	
	protected CM.Country getCmCountry() { 
		return new CM.Country();
	}
	
	protected CM.Fighter getCmFighter() { 
		return new CM.Fighter();
	}

	protected CM.FighterPhoto getCmFighterPhoto() { 
		return new CM.FighterPhoto();
	}
	
	protected CM.WeightCategory getCmWeightCategory() { 
		return new CM.WeightCategory();
	}
	
	protected CM.YearCategory getCmYearCategory() { 
		return new CM.YearCategory();
	}

	protected CM.YearWeightCategoryLink getCmYearWeightCategoryLink() { 
		return new CM.YearWeightCategoryLink();
	}
	
	protected CM.GroupChampionshipFighter getCmGroupChampionshipFighter() { 
		return new CM.GroupChampionshipFighter();
	}

}