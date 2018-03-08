/**
 * The file FightResult.java was created on 2011.15.10 at 10:44:28
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.rtu.exception.PersistenceException;

public interface FightResultService extends AKService {
	
	List<FightResult> loadOrCreateRoundRobinLastFightResults(FightingGroup group) throws PersistenceException;
	List<FightResult> loadPreviousFightResults(FightResult fightResult) throws PersistenceException;
	
	List<FightResult> loadOrCreateOlympicFightResults(FightingGroup group) throws PersistenceException;
	
	
	
	List<FightResult> saveFightResults(List<FightResult> list) throws PersistenceException;
	FightResult saveFightResult(FightResult fr) throws PersistenceException;
	FightResult createNextFightResult(FightResult previoudRounsFightResult, boolean copyPointsAndWarnings) throws PersistenceException;
	
	List<FighterPlace> loadGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException;
	List<FighterPlace> loadChampionshipResults(Championship championship) throws PersistenceException;
	
	
	Long getFirstFighterPointsForWin(FightSettings fightSettings, FightResult fightResult);
	Long getSecondFighterPointsForWin(FightSettings fightSettings, FightResult fightResult);
	
	List<FightResultReport> generateEachFightReport(
			Championship championship,
			FightingGroup group,
			ChampionshipFighter firstFighter,
			ChampionshipFighter secondFighter
	) throws PersistenceException;
	
	FightResult performFightResultOnFightAction(FightResult fightResult) throws PersistenceException;

}
