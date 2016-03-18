package com.ashihara.datamanagement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ashihara.datamanagement.core.session.AKServerSessionManager;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.interfaces.ChampionshipService;
import com.ashihara.datamanagement.interfaces.CountryService;
import com.ashihara.datamanagement.interfaces.FightResultService;
import com.ashihara.datamanagement.interfaces.FightSettingsService;
import com.ashihara.datamanagement.interfaces.FighterService;
import com.ashihara.datamanagement.interfaces.FightingGroupService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.interfaces.WeightCategoryService;
import com.ashihara.datamanagement.interfaces.YearCategoryService;
import com.ashihara.datamanagement.pojo.User;
import com.ashihara.enums.CM;
import com.ashihara.enums.SC;
import com.ashihara.enums.UIC;
import com.rtu.exception.PersistenceException;
import com.rtu.persistence.PersistenceHelper;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.service.core.ServerSideServiceFactory;
import com.rtu.session.ServerSession;
import com.rtu.session.interfaces.ClientSession;

public abstract class AbstractAKServiceImpl {
	protected Random randomGenerator = new Random(192518753);
	private ClientSession clientSession;
	private PersistenceHelper persistenceHelper;
	private ServerSideServiceFactory<DMIdentifiedService> serverSideServiceFactory;
	private ServerSession serverSession;
	private AKServerSessionManager aKServerSessionManager;
	private User sessionUser;
	
	public PersistenceHelper getHelper() {
		if (persistenceHelper == null) {
			persistenceHelper = getSession().getPersistenceHelper();
		}
		return persistenceHelper;
	}
	
	protected String getNextRangomId(){
		return String.valueOf(Math.abs(randomGenerator.nextLong()));
	}

	protected AKServerSessionManager getKasServerSessionManager() {
		if (aKServerSessionManager == null) {
			aKServerSessionManager = AKServerSessionManagerImpl.getInstance();
		}
		return aKServerSessionManager;
	}

	protected ServerSideServiceFactory<DMIdentifiedService> getServerServiceFactory() {
		if (serverSideServiceFactory == null) {
			serverSideServiceFactory = getKasServerSessionManager().getServerSession(getClientSession().getSessionHandler().getSessionId()).getServerSideServiceFactory();
		}
		return serverSideServiceFactory;
	}
	
	public ClientSession getClientSession() {
		return clientSession;
	}

	public void setClientSession(ClientSession clientSession) {
		this.clientSession = clientSession;
	}

	protected ServerSession getSession() {
		if (serverSession == null) {
			serverSession = getKasServerSessionManager().getServerSession(getClientSession().getSessionHandler().getSessionId()); 
		}
		return serverSession;
	}
	
//	Services
	
	private UserService getUserService() {
		return getServerServiceFactory().getService(UserService.class);
	}

	protected FighterService getFighterService() {
		return getServerServiceFactory().getService(FighterService.class);
	}

	protected FightSettingsService getFightSettingsService() {
		return getServerServiceFactory().getService(FightSettingsService.class);
	}
	
	protected FightingGroupService getFightingGroupService() {
		return getServerServiceFactory().getService(FightingGroupService.class);
	}
	
	protected ChampionshipService getChampionshipService() {
		return getServerServiceFactory().getService(ChampionshipService.class);
	}
	
	protected CountryService getCountryService() {
		return getServerServiceFactory().getService(CountryService.class);
	}
	
	protected FightResultService getFightResultService() {
		return getServerServiceFactory().getService(FightResultService.class);
	}
	
	protected FightSettingsService getPointsService() {
		return getServerServiceFactory().getService(FightSettingsService.class);
	}
	
	protected YearCategoryService getYearCategoryService() {
		return getServerServiceFactory().getService(YearCategoryService.class);
	}

	protected WeightCategoryService getWeightCategoryService() {
		return getServerServiceFactory().getService(WeightCategoryService.class);
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
	
	protected CM.ChampionshipFighter getCmChampionshipFighter() { 
		return new CM.ChampionshipFighter();
	}
	
	protected CM.Championship getCmChampionship() { 
		return new CM.Championship();
	}
	
	protected CM.FightingGroup getCmFightingGroup() { 
		return new CM.FightingGroup();
	}
	
	protected CM.GroupChampionshipFighter getCmGroupChampionshipFighter() { 
		return new CM.GroupChampionshipFighter();
	}
	
	protected CM.FightSettings getCmFightSettings() { 
		return new CM.FightSettings();
	}
	
	protected CM.FightResult getCmFightResult() { 
		return new CM.FightResult();
	}
	
	
	protected boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		return s.isEmpty();
	}
	
	protected UIC getUIC() throws PersistenceException {
		return SC.UI_PREFERENCES.UI_LANGUAGE.getUIC(getSessionUser().getUiLanguage());
	}
	
	protected UIC getUIC(User user) throws PersistenceException {
		return SC.UI_PREFERENCES.UI_LANGUAGE.getUIC(user.getUiLanguage());
	}
	
	protected User getSessionUser() throws PersistenceException {
		if (sessionUser == null) {
			String userId = getSession().getSessionHandler().getUserId();
			sessionUser = getUserService().loadByName(userId);
		}
		return sessionUser; 
	}
	
	protected <T extends Object> List<T> asList(T ... tt) {
		List<T> list = new ArrayList<T>();
		for (T t : tt) {
			list.add(t);
		}
		return list;
	}

}