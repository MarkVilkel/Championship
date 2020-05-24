package com.ashihara.datamanagement.core.service;

import java.lang.reflect.Proxy;

import com.ashihara.datamanagement.impl.ChampionshipPlanServiceImpl;
import com.ashihara.datamanagement.impl.ChampionshipServiceImpl;
import com.ashihara.datamanagement.impl.CountryServiceImpl;
import com.ashihara.datamanagement.impl.ExceptionServiceImpl;
import com.ashihara.datamanagement.impl.FightResultServiceImpl;
import com.ashihara.datamanagement.impl.FightSettingsServiceImpl;
import com.ashihara.datamanagement.impl.FighterPhotoServiceImpl;
import com.ashihara.datamanagement.impl.FighterServiceImpl;
import com.ashihara.datamanagement.impl.FightingGroupServiceImpl;
import com.ashihara.datamanagement.impl.MigrationServiceImpl;
import com.ashihara.datamanagement.impl.SecurityServiceImpl;
import com.ashihara.datamanagement.impl.UserServiceImpl;
import com.ashihara.datamanagement.impl.WeightCategoryServiceImpl;
import com.ashihara.datamanagement.impl.YearCategoryServiceImpl;
import com.ashihara.datamanagement.interfaces.ChampionshipPlanService;
import com.ashihara.datamanagement.interfaces.ChampionshipService;
import com.ashihara.datamanagement.interfaces.CountryService;
import com.ashihara.datamanagement.interfaces.ExceptionService;
import com.ashihara.datamanagement.interfaces.FightResultService;
import com.ashihara.datamanagement.interfaces.FightSettingsService;
import com.ashihara.datamanagement.interfaces.FighterPhotoService;
import com.ashihara.datamanagement.interfaces.FighterService;
import com.ashihara.datamanagement.interfaces.FightingGroupService;
import com.ashihara.datamanagement.interfaces.MigrationService;
import com.ashihara.datamanagement.interfaces.SecurityService;
import com.ashihara.datamanagement.interfaces.UserService;
import com.ashihara.datamanagement.interfaces.WeightCategoryService;
import com.ashihara.datamanagement.interfaces.YearCategoryService;
import com.rtu.persistence.TransactionType;
import com.rtu.service.core.AbstractServerServiceFactory;
import com.rtu.service.core.DMIdentifiedService;
import com.rtu.session.interfaces.ClientSession;

public class AKServerServiceFactoryImpl<T extends DMIdentifiedService> extends AbstractServerServiceFactory<T> implements AKServerServiceFactory<T> {
	
	public AKServerServiceFactoryImpl(ClientSession clientSession) {
		super(clientSession);
	}

	@Override
	protected void initServices() {
		register(SecurityService.class, new SecurityServiceImpl());
		register(UserService.class, new UserServiceImpl());
		register(FighterService.class, new FighterServiceImpl());
		register(ExceptionService.class, new ExceptionServiceImpl());
		register(CountryService.class, new CountryServiceImpl());
		register(FighterPhotoService.class, new FighterPhotoServiceImpl());
		register(WeightCategoryService.class, new WeightCategoryServiceImpl());
		register(YearCategoryService.class, new YearCategoryServiceImpl());
		register(ChampionshipService.class, new ChampionshipServiceImpl());
		register(ChampionshipPlanService.class, new ChampionshipPlanServiceImpl());
		register(FightingGroupService.class, new FightingGroupServiceImpl());
		register(FightSettingsService.class, new FightSettingsServiceImpl());
		register(FightResultService.class, new FightResultServiceImpl());
		register(MigrationService.class, new MigrationServiceImpl());
	}

	@Override
	protected T createNewServerServiceInvocationHandler(T service, TransactionType transactionType, ClientSession clientSession) {
		Object proxy = Proxy.newProxyInstance(service.getClass().getClassLoader(), 
				service.getClass().getInterfaces(), new AKServerServiceInvocationHandler<T>(service, transactionType, clientSession));
		
		return (T) proxy;
	}
}