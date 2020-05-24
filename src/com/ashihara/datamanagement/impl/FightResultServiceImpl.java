/**
 * The file FightResultImpl.java was created on 2011.15.10 at 10:44:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import static com.ashihara.datamanagement.impl.util.ObjectUtils.areTheSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.ashihara.datamanagement.impl.util.FightsScheduler;
import com.ashihara.datamanagement.interfaces.FightResultService;
import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.ChampionshipFighter;
import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.datamanagement.pojo.Fighter;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;
import com.ashihara.datamanagement.pojo.wraper.FightResultForPlan;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.utils.MathUtils;
import com.rtu.exception.PersistenceException;
import com.rtu.hql.HqlQuery;
import com.rtu.hql.expression.ExpressionHelper;

public class FightResultServiceImpl extends AbstractAKServiceImpl implements FightResultService {
	
	@Override
	public List<FightResult> loadOrCreateRoundRobinLastFightResults(FightingGroup group) throws PersistenceException {
		if (!SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
			throw new PersistenceException("Group must be " + SC.GROUP_TYPE.ROUND_ROBIN);
		}
		
		List<GroupChampionshipFighter> fighters = getFightingGroupService().loadGroupChampionshipFighters(group);
		List<FightResult> fightResults = loadLastFightResults(fighters);
		
		List<FightResult> newlyCreatedList = getFRs(fighters);
		
		if (newlyCreatedList != null && !newlyCreatedList.isEmpty()) {
			newlyCreatedList = removeDuplicated(newlyCreatedList, fightResults);
			newlyCreatedList = saveFightResults(newlyCreatedList);
			fightResults.addAll(newlyCreatedList);
		}
		
		fightResults.forEach(fr -> {
			fr.setRedFighter(fr.getFirstFighter());
			fr.setBlueFighter(fr.getSecondFighter());
		});
		
		sortFightResultByCreationOrder(fightResults);
		
		return fightResults;
	}
	
	private List<FightResult> removeDuplicated(
			List<FightResult> from,
			List<FightResult> existed
	) {
		List<FightResult> toRemove = new ArrayList<>();
		
		for (FightResult frFrom : from) {
			for (FightResult frExisted : existed) {
				if (
						(frFrom.getFirstFighter().getId().equals(frExisted.getFirstFighter().getId()) &&
						frFrom.getSecondFighter().getId().equals(frExisted.getSecondFighter().getId())) 
						||
						(frFrom.getSecondFighter().getId().equals(frExisted.getFirstFighter().getId()) &&
						frFrom.getFirstFighter().getId().equals(frExisted.getSecondFighter().getId())) 
				) {
					toRemove.add(frFrom);
				}
			}
		}
		from.removeAll(toRemove);
		return from;
	}

	private void sortFightResultByCreationOrder(List<FightResult> fightResults) {
		Collections.sort(fightResults, new Comparator<FightResult>() {
			@Override
			public int compare(FightResult o1, FightResult o2) {
				if (getFirstFightResult(o1).getId().longValue() > getFirstFightResult(o2).getId().longValue()) {
					return 1;
				}
				else {
					return -1;
				}
			}
			
		});
	}

	private FightResult getFirstFightResult(FightResult fr) {
		while (fr.getPreviousRoundFightResult() != null) {
			fr = fr.getPreviousRoundFightResult();
		}
		return fr;
	}
	
	/**
	 * Not including the passed fight result
	 */
	private List<FightResult> getAllPreviousFightResults(FightResult fr) {
		List<FightResult> result = new ArrayList<>();
		while (fr.getPreviousRoundFightResult() != null) {
			fr = fr.getPreviousRoundFightResult();
			result.add(fr);
		}
		return result;
	}
	
	private List<FightResult> getFRs(List<GroupChampionshipFighter> fighters) {
		if (fighters == null || fighters.isEmpty()) {
			return new ArrayList<>();
		}
		FightsScheduler fs = new FightsScheduler(fighters);
		return fs.getResult();
	}
	
	private FightResult createFightResult(
			GroupChampionshipFighter f1,
			GroupChampionshipFighter f2,
			long roundNumber
	) {
		FightResult fr = new FightResult();
		fr.setFirstFighter(f1);
		fr.setSecondFighter(f2);
		fr.setRoundNumber(new Long(roundNumber));
		
		return fr;
	}
	
	private FightResult loadFightResultById(Long id) throws PersistenceException {
		if (id == null) {
			return null;
		}
		return getHelper().loadById(FightResult.class, id);
	}
	
	private List<FightResult> loadLastFightResults(List<GroupChampionshipFighter> fighters) throws PersistenceException {
		if (fighters == null || fighters.isEmpty()) {
			return new ArrayList<>();
		}
		HqlQuery<FightResult> hql = getHelper().createHqlQuery(FightResult.class, getCmFightResult());
		hql.addExpression(ExpressionHelper.or(
				ExpressionHelper.in(getCmFightResult().getFirstFighter(), fighters),
				ExpressionHelper.in(getCmFightResult().getSecondFighter(), fighters)
				));
		hql.addExpression(ExpressionHelper.isNull(getCmFightResult().getNextRoundFightResult()));
		
		List<FightResult> result = getHelper().loadByHqlQuery(hql);
		
		return result;
	}
	
	@Override
	public List<FightResult> saveFightResults(List<FightResult> list) throws PersistenceException {
		return getHelper().saveAll(list);
	}

	@Override
	public FightResult saveFightResult(FightResult fr) throws PersistenceException {
		return getHelper().save(fr);
	}

	@Override
	public FightResult createNextFightResult(
			FightResult previousRoundFightResult,
			boolean copyPointsAndWarnings
	) throws PersistenceException {
		FightResult fr = createFightResult(
				previousRoundFightResult.getFirstFighter(),
				previousRoundFightResult.getSecondFighter(),
				previousRoundFightResult.getRoundNumber().longValue() + 1
		);
		fr.setPreviousRoundFightResult(previousRoundFightResult);
		fr.setOlympicLevel(previousRoundFightResult.getOlympicLevel());
		fr.setOlympicPositionOnLevel(previousRoundFightResult.getOlympicPositionOnLevel());
		
		if (copyPointsAndWarnings) {
			fr.setFirstFighterPoints(previousRoundFightResult.getFirstFighterPoints());
			fr.setFirstFighterFirstCategoryWarnings(previousRoundFightResult.getFirstFighterFirstCategoryWarnings());
			fr.setFirstFighterSecondCategoryWarnings(previousRoundFightResult.getFirstFighterSecondCategoryWarnings());
			
			fr.setSecondFighterPoints(previousRoundFightResult.getSecondFighterPoints());
			fr.setSecondFighterFirstCategoryWarnings(previousRoundFightResult.getSecondFighterFirstCategoryWarnings());
			fr.setSecondFighterSecondCategoryWarnings(previousRoundFightResult.getSecondFighterSecondCategoryWarnings());
		}
		
		fr = saveFightResult(fr);
		
		return fr;
	}

	@Override
	public List<FightResult> loadPreviousFightResults(FightResult fightResult) throws PersistenceException {
		HqlQuery<FightResult> hql = getHelper().createHqlQuery(FightResult.class, getCmFightResult());
		
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getFirstFighter(), fightResult.getFirstFighter()));
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getSecondFighter(), fightResult.getSecondFighter()));
		hql.addExpression(ExpressionHelper.lt(getCmFightResult().getId(), fightResult.getId()));
		
		List<FightResult> result = getHelper().loadByHqlQuery(hql);
		
		return result;
	}

	@Override
	public Long getFirstFighterPointsForWin(
	        FightSettings fightSettings,
	        FightResult fightResult,
	        RulesManager rulesManager
	) {
		final long CRITICAL_WARNINGS_COUNT = rulesManager.getMaxPenaltyCount();
		
		if (
				fightResult.getFirstFighterFirstCategoryWarnings() >= CRITICAL_WARNINGS_COUNT ||
				fightResult.getFirstFighterSecondCategoryWarnings() >= CRITICAL_WARNINGS_COUNT
		) {
			//lost
			return fightSettings.getForLoosing();
		} else if (
				fightResult.getSecondFighterFirstCategoryWarnings() >= CRITICAL_WARNINGS_COUNT ||
				fightResult.getSecondFighterSecondCategoryWarnings() >= CRITICAL_WARNINGS_COUNT
		) {
			// won
			return fightSettings.getForWinning();
		} else if (fightResult.getFirstFighterPoints() > fightResult.getSecondFighterPoints()) {
			// won
			return fightSettings.getForWinning();
		} else if (fightResult.getFirstFighterPoints() < fightResult.getSecondFighterPoints()) {
			//lost
			return fightSettings.getForLoosing();
		} else if (Boolean.TRUE.equals(fightResult.getFirstFighterWinByJudgeDecision())) {
            // won
            return fightSettings.getForWinning();
        } else if (Boolean.TRUE.equals(fightResult.getSecondFighterWinByJudgeDecision())) {
            // lost
            return fightSettings.getForLoosing();
		} else if (Boolean.TRUE.equals(fightResult.getFirstFighterWinByTKO())) {
            // won
            return fightSettings.getForWinning();
        } else if (Boolean.TRUE.equals(fightResult.getSecondFighterWinByTKO())) {
            // lost
            return fightSettings.getForLoosing();
		} else {
			// draw
			return fightSettings.getForDraw();
		}
	}
	
	@Override
	public Long getSecondFighterPointsForWin(
	        FightSettings fightSettings,
	        FightResult fightResult,
	        RulesManager rulesManager
	) {
	    final long CRITICAL_WARNINGS_COUNT = rulesManager.getMaxPenaltyCount();
	    
		if (
				fightResult.getSecondFighterFirstCategoryWarnings() >= CRITICAL_WARNINGS_COUNT ||
				fightResult.getSecondFighterSecondCategoryWarnings() >= CRITICAL_WARNINGS_COUNT
		) {
			//lost
			return fightSettings.getForLoosing();
		} else if (
				fightResult.getFirstFighterFirstCategoryWarnings() >= CRITICAL_WARNINGS_COUNT ||
				fightResult.getFirstFighterSecondCategoryWarnings() >= CRITICAL_WARNINGS_COUNT
		) {
			// won
			return fightSettings.getForWinning();
		} else if (fightResult.getSecondFighterPoints() < fightResult.getFirstFighterPoints()) {
			//lost
			return fightSettings.getForLoosing();
		} else if (fightResult.getSecondFighterPoints() > fightResult.getFirstFighterPoints()) {
			// won
			return fightSettings.getForWinning();
		} else if (Boolean.TRUE.equals(fightResult.getSecondFighterWinByJudgeDecision())) {
		    // won
		    return fightSettings.getForWinning();
		} else if (Boolean.TRUE.equals(fightResult.getFirstFighterWinByJudgeDecision())) {
		    // lost
		    return fightSettings.getForLoosing();
		} else if (Boolean.TRUE.equals(fightResult.getSecondFighterWinByTKO())) {
		    // won
		    return fightSettings.getForWinning();
		} else if (Boolean.TRUE.equals(fightResult.getFirstFighterWinByTKO())) {
		    // lost
		    return fightSettings.getForLoosing();
		} else {
			// draw
			return fightSettings.getForDraw();
		}
	}

	@Override
	public List<FighterPlace> loadGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException {
		List<FighterPlace> result;
		
		if (SC.GROUP_TYPE.ROUND_ROBIN.equals(fightingGroup.getType())) {
			result = loadRoundRobinGroupTournamentResults(fightingGroup);
		}
		else if (SC.GROUP_TYPE.OLYMPIC.equals(fightingGroup.getType())) {
			result = loadOlympicGroupTournamentResults(fightingGroup);
		}
		else {
			throw new IllegalArgumentException("Unsupported group type");
		}
		
		return result;
	}
	
	private List<FighterPlace> loadOlympicGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException {
		List<FightResult> fightResults = loadOrCreateOlympicFightResults(fightingGroup);
		List<GroupChampionshipFighter> groupChampionshipFighters = getFightingGroupService().loadGroupChampionshipFighters(fightingGroup);
		
		Map<Long, FighterPlace> result = createFighterPlaces(fightResults, groupChampionshipFighters);
		FightSettings fightSettings = getFightSettingsService().load();
		
		
		int actualFightersNumber = groupChampionshipFighters.size();
		int idealFightersNumber = MathUtils.calculateIdealFightersNumberForOlympic(actualFightersNumber);
		int levelsNumber = MathUtils.calculateOlympicFightLevelsNumber(actualFightersNumber);
		
		if (idealFightersNumber > actualFightersNumber) {
			for (FightResult fr : fightResults) {
				if (fr.getOlympicLevel().longValue() == 0) {
					if (fr.getFirstFighter() != null) {
						FighterPlace fp = result.get(fr.getFirstFighter().getChampionshipFighter().getFighter().getId());
						if (fp != null && fp.getPointsForWin() != null) {
							fp.setPointsForWin(fp.getPointsForWin() - fightSettings.getForWinning());
						}
					}
					if (fr.getSecondFighter() != null) {
						FighterPlace fp = result.get(fr.getSecondFighter().getChampionshipFighter().getFighter().getId());
						if (fp != null && fp.getPointsForWin() != null) {
							fp.setPointsForWin(fp.getPointsForWin() - fightSettings.getForWinning());
						}
					}
				}
			}
		}
		
		for (FightResult fr : fightResults) {
			if (fr.getOlympicLevel().longValue() == levelsNumber - 2) {
				if (
						fr.getFirstFighterPoints() != null &&
						fr.getSecondFighterPointsForWin() != null &&
						(
								fr.getFirstFighterPoints().longValue() > 0 ||
								fr.getSecondFighterPointsForWin().longValue() > 0 ||
								Boolean.TRUE.equals(fr.getFirstFighterWinByJudgeDecision()) ||
								Boolean.TRUE.equals(fr.getSecondFighterWinByJudgeDecision()) ||
								Boolean.TRUE.equals(fr.getFirstFighterWinByTKO()) ||
								Boolean.TRUE.equals(fr.getSecondFighterWinByTKO())
						) &&
						fr.getOlympicPositionOnLevel() == 0
				) {
					if (fr.getFirstFighter() != null) {
						FighterPlace fp = result.get(fr.getFirstFighter().getChampionshipFighter().getFighter().getId());
						if (fp != null && fp.getPointsForWin() != null) {
							fp.setPointsForWin(fp.getPointsForWin() + fightSettings.getForWinning());
						}
					}
					if (fr.getSecondFighter() != null) {
						FighterPlace fp = result.get(fr.getSecondFighter().getChampionshipFighter().getFighter().getId());
						if (fp != null && fp.getPointsForWin() != null) {
							fp.setPointsForWin(fp.getPointsForWin() + fightSettings.getForWinning());
						}
					}
				}
			}
		}
		
		List<FighterPlace> fighterPlaces = new ArrayList<>();
		fighterPlaces.addAll(result.values());
		fighterPlaces = setupPlaces(fighterPlaces);
		
		return fighterPlaces;
	}

	private List<FighterPlace> loadRoundRobinGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException {
		List<FightResult> fightResults = loadOrCreateRoundRobinLastFightResults(fightingGroup);
		List<GroupChampionshipFighter> groupChampionshipFighters = getFightingGroupService().loadGroupChampionshipFighters(fightingGroup);
		
		Map<Long, FighterPlace> result = createFighterPlaces(fightResults, groupChampionshipFighters);
		
		List<FighterPlace> fighterPlaces = new ArrayList<>();
		fighterPlaces.addAll(result.values());
		
		fighterPlaces = setupPlaces(fighterPlaces);
		
		return fighterPlaces;
	}
	
	private Map<Long, FighterPlace> createFighterPlaces(
			List<FightResult> fightResults,
			List<GroupChampionshipFighter> groupChampionshipFighters
	) {
		Map<Long, FighterPlace> result = new HashMap<>();
		
		for (GroupChampionshipFighter gcFighter : groupChampionshipFighters) {
			Fighter fighter = gcFighter.getChampionshipFighter().getFighter();
			FighterPlace fp = new FighterPlace(gcFighter);
			
			result.put(fighter.getId(), fp);
			
			for (FightResult fr : fightResults) {
				fp = addPoints(fp, fighter, fr);
			}
		}
		return result;
	}
	
	private List<FighterPlace> setupPlaces(List<FighterPlace> fighterPlaces) {
		Collections.sort(fighterPlaces, new Comparator<FighterPlace>() {
			@Override
			public int compare(FighterPlace o1, FighterPlace o2) {
				return doCompare(o1, o2);
			}
		});
		
		int place = 0;
		FighterPlace previous = null;
		for (FighterPlace fp : fighterPlaces) {
			int compared = previous == null ? 0 : doCompare(previous, fp);
			if (compared != 0 || place == 0) {
				place ++;
			}
			fp.setPlace(place);
			
			previous = fp;
		}
		
		return fighterPlaces;
	}

	private int doCompare(FighterPlace o1, FighterPlace o2) {
		if (o1.getPointsForWin() > o2.getPointsForWin()) {
			return -1;
		} else if (o1.getPointsForWin() < o2.getPointsForWin()) {
			return 1;
		} else {
			long pts1 = o1.getPoints() != null ? o1.getPoints().longValue() : 0;
			long pts2 = o2.getPoints() != null ? o2.getPoints().longValue() : 0;
			if (pts1 > pts2) {
				return -1;
			} else if (pts1 < pts2) {
				return 1;
			} else {
			    if (o1.getGCFighter().getChampionshipFighter().getFighter().getWeight() > o2.getGCFighter().getChampionshipFighter().getFighter().getWeight()) {
			        return 1;
			    } else if (o1.getGCFighter().getChampionshipFighter().getFighter().getWeight() < o2.getGCFighter().getChampionshipFighter().getFighter().getWeight()) {
			        return -1;
			    } else {
			        return 0;
			    }
			}
		}
	}

//	private int doCompare(FighterPlace o1, FighterPlace o2) {
//		if (o1.getPointsForWin() > o2.getPointsForWin()) {
//			return -1;
//		} else if (o1.getPointsForWin() < o2.getPointsForWin()) {
//			return 1;
//		} else {
//			if (o1.getFirstCategoryWarnings() > o2.getFirstCategoryWarnings()) {
//				return 1;
//			} else if (o1.getFirstCategoryWarnings() < o2.getFirstCategoryWarnings()) {
//				return -1;
//			} else {
//				if (o1.getSecondCategoryWarnings() > o2.getSecondCategoryWarnings()) {
//					return 1;
//				} else if (o1.getSecondCategoryWarnings() < o2.getSecondCategoryWarnings()) {
//					return -1;
//				} else {
//					if (o1.getGCFighter().getChampionshipFighter().getFighter().getWeight() > o2.getGCFighter().getChampionshipFighter().getFighter().getWeight()) {
//						return 1;
//					} else if (o1.getGCFighter().getChampionshipFighter().getFighter().getWeight() < o2.getGCFighter().getChampionshipFighter().getFighter().getWeight()) {
//						return -1;
//					} else {
//						if (o1.getGCFighter().getChampionshipFighter().getFighter().getBirthday().getTime() > o2.getGCFighter().getChampionshipFighter().getFighter().getBirthday().getTime()) {
//							return 1;
//						} else if (o1.getGCFighter().getChampionshipFighter().getFighter().getBirthday().getTime() < o2.getGCFighter().getChampionshipFighter().getFighter().getBirthday().getTime()) {
//							return -1;
//						} else {
//							return 0;
//						}
//					}
//				}
//			}
//		}
//	}
	
	private FighterPlace addPoints(
			FighterPlace fighterPlace,
			Fighter fighter,
			FightResult fightResult
	) {
		
		if (fightResult.getFirstFighter() == null || fightResult.getSecondFighter() == null) {
			return fighterPlace;
		}
		
		Long pointsForWin;
		Long points;
		Long firstCategoryWarnings;
		Long secondCategoryWarnings;
		Long wonByJudgeDecisionCount;
		Long wonByTKOCount;
		
		if (fightResult.getFirstFighter().getChampionshipFighter().getFighter().getId().equals(fighter.getId())) {
			points = fightResult.getFirstFighterPoints();
			firstCategoryWarnings = fightResult.getFirstFighterFirstCategoryWarnings();
			secondCategoryWarnings = fightResult.getFirstFighterSecondCategoryWarnings();
			pointsForWin = fightResult.getFirstFighterPointsForWin();
			wonByJudgeDecisionCount = Boolean.TRUE.equals(fightResult.getFirstFighterWinByJudgeDecision()) ? 1L : null;
			wonByTKOCount = Boolean.TRUE.equals(fightResult.getFirstFighterWinByTKO()) ? 1L : null;
		}
		else if (fightResult.getSecondFighter().getChampionshipFighter().getFighter().getId().equals(fighter.getId())) {
			points = fightResult.getSecondFighterPoints();
			firstCategoryWarnings = fightResult.getSecondFighterFirstCategoryWarnings();
			secondCategoryWarnings = fightResult.getSecondFighterSecondCategoryWarnings();
			pointsForWin = fightResult.getSecondFighterPointsForWin();
			wonByJudgeDecisionCount = Boolean.TRUE.equals(fightResult.getSecondFighterWinByJudgeDecision()) ? 1L : null;
			wonByTKOCount = Boolean.TRUE.equals(fightResult.getSecondFighterWinByTKO()) ? 1L : null;
		}
		else {
			return fighterPlace;
		}
		
		if (pointsForWin != null) {
			fighterPlace.setPointsForWin(fighterPlace.getPointsForWin() + pointsForWin);
		}
		
		if (points != null) {
			fighterPlace.setPoints(fighterPlace.getPoints() + points);
		}
		
		if (firstCategoryWarnings != null) {
			fighterPlace.setFirstCategoryWarnings(fighterPlace.getFirstCategoryWarnings() + firstCategoryWarnings);
		}
		
		if (secondCategoryWarnings != null) {
			fighterPlace.setSecondCategoryWarnings(fighterPlace.getSecondCategoryWarnings() + secondCategoryWarnings);
		}
		
		if (wonByJudgeDecisionCount != null) {
		    fighterPlace.setWonByJudgeDecisionCount(fighterPlace.getWonByJudgeDecisionCount() + wonByJudgeDecisionCount);
		}

		if (wonByTKOCount != null) {
		    fighterPlace.setWonByTKOCount(fighterPlace.getWonByTKOCount() + wonByTKOCount);
		}

		return fighterPlace;
	}

	@Override
	public List<FighterPlace> loadChampionshipResults(Championship championship) throws PersistenceException {
		List<FightingGroup> groups = getFightingGroupService().loadGroups(championship);
		List<FighterPlace> result = new ArrayList<>();
		if (groups != null) {
			for (FightingGroup group : groups) {
				List<FighterPlace> groupResults = loadGroupTournamentResults(group);
				if (result != null) {
					result.addAll(groupResults);
				}
			}
		}
		return result;
	}

	@Override
	public List<FightResultReport> generateEachFightReport(
			Championship championship,
			FightingGroup group,
			ChampionshipFighter firstFighter,
			ChampionshipFighter secondFighter
	) throws PersistenceException {
		HqlQuery<FightResult> hql = getHelper().createHqlQuery(FightResult.class, getCmFightResult());
		
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getFirstFighter().getChampionshipFighter().getChampionship(), championship));
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getSecondFighter().getChampionshipFighter().getChampionship(), championship));
		hql.addExpression(ExpressionHelper.isNull(getCmFightResult().getNextRoundFightResult()));
		
		if(group != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFightResult().getFirstFighter().getFightingGroup(), group));
			hql.addExpression(ExpressionHelper.eq(getCmFightResult().getSecondFighter().getFightingGroup(), group));
		}
		
		if (firstFighter != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFightResult().getFirstFighter().getChampionshipFighter(), firstFighter));
		}
		
		if (secondFighter != null) {
			hql.addExpression(ExpressionHelper.eq(getCmFightResult().getSecondFighter().getChampionshipFighter(), secondFighter));
		}
		
		List<FightResult> fightResults = getHelper().loadByHqlQuery(hql);
		fightResults = sortByGroup(fightResults);
		
		List<FightResultReport> reportResult = new ArrayList<>();
		
		for (FightResult fr : fightResults) {
			List<FightResult> previousFightResults = loadPreviousFightResults(fr);
			
			FightResultReport frr = new FightResultReport();
			frr.setLastRound(fr);
			frr.setPreviousRounds(previousFightResults);
			
			reportResult.add(frr);
		}
		
		return reportResult;
	}
	
	private List<FightResult> sortByGroup(List<FightResult> list) {
		Collections.sort(list, new Comparator<FightResult>() {
			@Override
			public int compare(FightResult o1, FightResult o2) {
				return o1.getFirstFighter().getFightingGroup().getName().compareTo(o2.getFirstFighter().getFightingGroup().getName());
			}
		});
		return list;
	}

	@Override
	public List<FightResult> loadOrCreateOlympicFightResults(FightingGroup group) throws PersistenceException {
		if (!SC.GROUP_STATUS.STARTED.equals(group.getStatus())) {
			return new ArrayList<>();
		}
		
		if (!SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
			throw new PersistenceException("Group must be " + SC.GROUP_TYPE.OLYMPIC);
		}
		
		List<FightResult> result = new ArrayList<>();
		
		List<GroupChampionshipFighter> fighters = getFightingGroupService().loadGroupChampionshipFighters(group);
		
		if (
				fighters == null ||
				fighters.isEmpty()
		) {
			return result;
		}
		
		List<FightResult> fightResultsFromFighters = loadORCreateBasicOlympicFightResultsFromFighters(fighters);
		List<FightResult> fightResultsFromFightResults = loadORCreateOlympicFightResultsFromFightResults(fightResultsFromFighters, fighters);
		FightResult thirdFourthPlaceFightResult = loadOrCreateThridFourthPlaceFightResult(fightResultsFromFightResults, fighters);
		
		result.addAll(fightResultsFromFightResults);
		
		if (thirdFourthPlaceFightResult != null) {
			result.add(thirdFourthPlaceFightResult);
		}
		
		for (FightResult fr : result) {
			fr.setRedFighter(fr.getFirstFighter());
			fr.setBlueFighter(fr.getSecondFighter());
		}

		setUpChildrenAndParents(result);
		
		sortFightResultForOlympicTree(result);
		
		return result;
	}
	
	
	private void setUpChildrenAndParents(List<FightResult> allFightResults) {
		for (FightResult fr : allFightResults) {
			List<FightResult> parents = findParents(fr, allFightResults);
			
			FightResult firstFighterParent = getFirstFighterParent(fr, parents);
			FightResult secondFighterParent = getSecondFighterParent(fr, parents);
			
			fr.setFirstFighterParent(firstFighterParent);
			fr.setSecondFighterParent(secondFighterParent);
			
			if (firstFighterParent != null) {
				firstFighterParent.getChildren().add(fr);
			}
			
			if (secondFighterParent != null) {
				secondFighterParent.getChildren().add(fr);
			}
		}
	}

	private FightResult getFirstFighterParent(FightResult child, List<FightResult> parents) {
		for (FightResult fr : parents) {
			if (
					(fr.getFirstFighter() != null && areTheSame(fr.getFirstFighter(), child.getFirstFighter())) ||
					(fr.getSecondFighter() != null && areTheSame(fr.getSecondFighter(), child.getFirstFighter()))
			) {
				return fr;
			}
		}
		return null;
	}

	private FightResult getSecondFighterParent(FightResult child, List<FightResult> parents) {
		for (FightResult fr : parents) {
			if (
					(fr.getSecondFighter() != null && areTheSame(fr.getSecondFighter(), child.getSecondFighter())) ||
					(fr.getFirstFighter() != null && areTheSame(fr.getFirstFighter(), child.getSecondFighter()))
			) {
				return fr;
			}
		}
		return null;
	}
	
	private List<FightResult> findParents(FightResult child, List<FightResult> allFightResults) {
		List<FightResult> result = new ArrayList<>();
		
		for (FightResult fr : allFightResults) {
			if (fr.getOlympicLevel().longValue() + 1 == child.getOlympicLevel().longValue()) {
				if (
						(fr.getFirstFighter() != null && areTheSame(fr.getFirstFighter(), child.getFirstFighter())) ||
						(fr.getSecondFighter() != null && areTheSame(fr.getSecondFighter(), child.getSecondFighter())) ||
						(fr.getFirstFighter() != null && areTheSame(fr.getFirstFighter(), child.getSecondFighter())) ||
						(fr.getSecondFighter() != null && areTheSame(fr.getSecondFighter(), child.getFirstFighter()))
				) {
					result.add(fr);
				}
			}
		}
		
		return result;
	}

	private FightResult loadOrCreateThridFourthPlaceFightResult(
			List<FightResult> allFightResults,
			List<GroupChampionshipFighter> fighters
	) throws PersistenceException {
		if (fighters.size() <= 3) {
			return null;
		}
		
		int levelsCount = MathUtils.calculateOlympicFightLevelsNumber(fighters.size());
		
		if (levelsCount <= 2) {
			return null;
		}
		
		List<FightResult> suitableCandidates = new ArrayList<>();
		
		for (FightResult fr : allFightResults) {
			if (fr.getOlympicLevel().longValue() == levelsCount - 3) {
				suitableCandidates.add(fr);
			}
		}
		
		
		GroupChampionshipFighter firstFighter = null;
		GroupChampionshipFighter secondFighter = null;
		
		for (FightResult fr : suitableCandidates) {
			if (fr.getFirstFighterPointsForWin() != null && fr.getSecondFighterPointsForWin() != null) {
				if (fr.getFirstFighterPointsForWin().longValue() < fr.getSecondFighterPointsForWin().longValue()) {
					if (fr.getOlympicPositionOnLevel() == 0) {
						firstFighter = fr.getFirstFighter();
					}
					else {
						secondFighter = fr.getFirstFighter();
					}
				}
				else if (fr.getFirstFighterPointsForWin().longValue() > fr.getSecondFighterPointsForWin().longValue()) {
					if (fr.getOlympicPositionOnLevel().longValue() == 0) {
						firstFighter = fr.getSecondFighter();
					}
					else {
						secondFighter = fr.getSecondFighter();
					}
				}
			}
		}
		
		if (firstFighter != null && secondFighter != null) {
			if (!fighterCombinationAlreadyExists(allFightResults, firstFighter, secondFighter)) {
				FightResult result = createFightResult(firstFighter, secondFighter, 0);
				
				result.setOlympicLevel(new Long(levelsCount - 2));
				result.setOlympicPositionOnLevel(new Long(1));
				
				result = saveFightResult(result);
				
				return result;
			}
		}
		
		return null;
	}

	private List<FightResult> loadORCreateOlympicFightResultsFromFightResults(
			List<FightResult> basicFightResults,
			List<GroupChampionshipFighter> fighters
	) throws PersistenceException {
		
		int actualFightersNumber = fighters.size();
//		int idealFightersNumber = MathUtils.calculateIdealFightersNumberForOlympic(actualFightersNumber);
		int levelsNumberCount = MathUtils.calculateOlympicFightLevelsNumber(actualFightersNumber);
		
		List<FightResult> allFightResults = loadLastFightResults(fighters);
		List<FightResult> notBasicFightResults = new ArrayList<>();
		
		notBasicFightResults.addAll(allFightResults);
		notBasicFightResults.removeAll(basicFightResults);
		
		for (FightResult fr : allFightResults) {
			if (fr.getOlympicLevel().longValue() >= levelsNumberCount) {
				// there will be no any more fights
				continue;
			}
			
			if (fr.getFirstFighter() == null || fr.getSecondFighter() == null) {
				// basic not completed fight result
				continue;
			}
			if (fr.getFirstFighterPointsForWin() == null || fr.getSecondFighterPointsForWin() == null) {
				// there was no fight yet
				continue;
			}
			
			GroupChampionshipFighter firstFighterForTheNextLevel;
			
			if (fr.getFirstFighterPointsForWin().longValue() == fr.getSecondFighterPointsForWin().longValue()) {
				// fight is not over yet, it has to be continued
				continue;
			}
			else if (fr.getFirstFighterPointsForWin().longValue() > fr.getSecondFighterPointsForWin().longValue()) {
				// first fighter won the must be the next fight
				firstFighterForTheNextLevel = fr.getFirstFighter();
			}
			else {
				// second fighter won the must be the next fight
				firstFighterForTheNextLevel = fr.getSecondFighter();
			}
			
			GroupChampionshipFighter secondFighterForTheNextLevel;
			
			if (fr.getOlympicLevel().longValue() == levelsNumberCount - 2) {
				secondFighterForTheNextLevel = null; // the first fighter is a winner, there will be no fights any more
			}
			else {
				secondFighterForTheNextLevel = detectSecondFighterFromPreviousFight(
						fr,
						allFightResults,
						firstFighterForTheNextLevel,
						fighters
						);
				
				if (secondFighterForTheNextLevel == null) {
					secondFighterForTheNextLevel = detectSecondFighterFromBasicLevelFighters(
							fr,
							basicFightResults,
							firstFighterForTheNextLevel,
							fighters
							);
				}
			}
			

			boolean fighterCombinationAlreadyExists = fighterCombinationAlreadyExists(notBasicFightResults, firstFighterForTheNextLevel, secondFighterForTheNextLevel);
			
			if (!fighterCombinationAlreadyExists) {
				FightResult nextFightResult;

				boolean isFighterFirst = fr.getOlympicPositionOnLevel().longValue() % 2 == 0;
				
				if (isFighterFirst) {
					nextFightResult = createFightResult(firstFighterForTheNextLevel, secondFighterForTheNextLevel, 0);
				}
				else {
					nextFightResult = createFightResult(secondFighterForTheNextLevel, firstFighterForTheNextLevel, 0);
				}
				
				nextFightResult.setOlympicLevel(fr.getOlympicLevel().longValue() + 1);
				nextFightResult.setOlympicPositionOnLevel((fr.getOlympicPositionOnLevel().longValue()) / 2);
				
				if (secondFighterForTheNextLevel != null) {
					nextFightResult = saveFightResult(nextFightResult);
				}
				notBasicFightResults.add(nextFightResult);
			}
		}
		
		List<FightResult> result = getUniqueBasicResults(basicFightResults, notBasicFightResults);
		result.addAll(notBasicFightResults);
		
		return result;
	}
	
	private List<FightResult> getUniqueBasicResults(
			List<FightResult> basicFightResults,
			List<FightResult> notBasicFightResults
	) {
		List<FightResult> result = new ArrayList<>();
		
		for (FightResult basicFR : basicFightResults) {
			boolean contains = false;
			for (FightResult notBasicFR : notBasicFightResults) {
				if (
						(
								areTheSame(notBasicFR.getFirstFighter(), basicFR.getFirstFighter()) &&
								areTheSame(notBasicFR.getSecondFighter(), basicFR.getSecondFighter())
						)
						||
						(
								areTheSame(notBasicFR.getSecondFighter(), basicFR.getFirstFighter()) &&
								areTheSame(notBasicFR.getFirstFighter(), basicFR.getSecondFighter())
						)
						||
						(
								basicFR.getFirstFighter() == null &&
								basicFR.getSecondFighter() != null &&
								(
										areTheSame(notBasicFR.getSecondFighter(), basicFR.getSecondFighter()) ||
										areTheSame(notBasicFR.getFirstFighter(), basicFR.getSecondFighter())
								)
						)
						||
						(
								basicFR.getFirstFighter() != null &&
								basicFR.getSecondFighter() == null &&
								(
										areTheSame(notBasicFR.getSecondFighter(), basicFR.getFirstFighter()) ||
										areTheSame(notBasicFR.getFirstFighter(), basicFR.getFirstFighter())
								)
						)
				) {
					contains = true;
					break;
				}
			}
			
			if (!contains) {
				result.add(basicFR);
			}
		}
		return result;
	}

	private GroupChampionshipFighter detectSecondFighterFromBasicLevelFighters(
			FightResult fightResult,
			List<FightResult> basicFightResults,
			GroupChampionshipFighter firstFighterForTheNextLevel,
			List<GroupChampionshipFighter> fighters
	) {
		List<FightResult> suitableCandidates = new ArrayList<>();
		for (FightResult fr : basicFightResults) {
			if (
					!fr.equals(fightResult) &&
					fr.getOlympicLevel().equals(fightResult.getOlympicLevel() + 1) &&
					(fr.getFirstFighter() == null || fr.getSecondFighter() == null)
			) {
				suitableCandidates.add(fr);
			}
		}
		
		if (suitableCandidates.isEmpty()) {
			return null;
		}

		for (FightResult fr : suitableCandidates) {
			long targetPos = fightResult.getOlympicPositionOnLevel().longValue();
			long currentPos = fr.getOlympicPositionOnLevel().longValue();
			
			if (targetPos / 2 == currentPos) {
				return fr.getFirstFighter() != null ? fr.getFirstFighter() : fr.getSecondFighter();
			}
		}
		
		return null;
	}

	private boolean fighterCombinationAlreadyExists(
			List<FightResult> fightResuls,
			GroupChampionshipFighter firstFighter,
			GroupChampionshipFighter secondFighter
	) {
		for (FightResult fr : fightResuls) {
			if (
					(
							areTheSame(fr.getFirstFighter(), firstFighter) &&
							areTheSame(fr.getSecondFighter(), secondFighter)
					)
					||
					(
							areTheSame(fr.getFirstFighter(), secondFighter) &&
							areTheSame(fr.getSecondFighter(), firstFighter)
					)
			) {
				return true;
			}

		}
		return false;
	}
	
	private GroupChampionshipFighter detectSecondFighterFromPreviousFight(
			FightResult fightResult,
			List<FightResult> allFightResults,
			GroupChampionshipFighter firstFighterForTheNextLevel,
			List<GroupChampionshipFighter> fighters
	) {
		List<FightResult> suitableCandidates = new ArrayList<>();
		for (FightResult fr : allFightResults) {
			if (!fr.equals(fightResult) && fr.getOlympicLevel().equals(fightResult.getOlympicLevel())) {
				suitableCandidates.add(fr);
			}
		}
		
		if (suitableCandidates.isEmpty()) {
			return null;
		}
		
		sortFightResultByCreationOrder(suitableCandidates);
		
		for (FightResult fr : suitableCandidates) {
			if (fr.getFirstFighter().equals(firstFighterForTheNextLevel) || fr.getSecondFighter().equals(firstFighterForTheNextLevel)) {
				// the same fighter, skip this fight result
				continue;
			}
			
			if (
					(
							fightResult.getOlympicPositionOnLevel().longValue() % 2 == 0 &&
							fightResult.getOlympicPositionOnLevel().longValue() + 1 == fr.getOlympicPositionOnLevel().longValue()
					)
					||
					(
							fightResult.getOlympicPositionOnLevel().longValue() % 2 != 0 &&
							fightResult.getOlympicPositionOnLevel().longValue() - 1 == fr.getOlympicPositionOnLevel().longValue()
					)
			) {
				if (fr.getFirstFighterPointsForWin() == null || fr.getSecondFighterPointsForWin() == null) {
					return null;
				}
				else if (fr.getFirstFighterPointsForWin().longValue() > fr.getSecondFighterPointsForWin().longValue()) {
					return fr.getFirstFighter();
				}
				else if (fr.getFirstFighterPointsForWin().longValue() < fr.getSecondFighterPointsForWin().longValue()) {
					return fr.getSecondFighter();
				}
				else {
					return null;
				}
			}
		}
		
		return null;
	}

	private List<FightResult> loadORCreateBasicOlympicFightResultsFromFighters(List<GroupChampionshipFighter> fighters) throws PersistenceException {
		List<FightResult> result = new ArrayList<>();
		
		GroupChampionshipFighter firstFighter = null;
		
		int levelChangeIndex = 0;
		long lastOlympicLevel = 0;
		int positionOnLevel = 0;
		
		for (int i = 0; i < fighters.size(); i++) {
			GroupChampionshipFighter gcf = fighters.get(i);
			if (lastOlympicLevel != gcf.getOlympicLevel().longValue()) {
				positionOnLevel = result.size() / 2;
				lastOlympicLevel = gcf.getOlympicLevel().longValue();
				if ((fighters.size() - i) % 2 != 0) {
					levelChangeIndex = 1;
				}
			}
			
			if ((i + levelChangeIndex) % 2 == 0) {
				firstFighter = gcf;
			}
			else {
				if (firstFighter == null) {
					FightResult fightResult = createFightResult(null, gcf, 0);
					fightResult.setOlympicLevel(gcf.getOlympicLevel());
					fightResult.setOlympicPositionOnLevel(Long.valueOf(positionOnLevel));
					result.add(fightResult);
					
					positionOnLevel ++;
				}
				else if (firstFighter.getOlympicLevel().equals(gcf.getOlympicLevel())) {
					GroupChampionshipFighter secondFighter = gcf;
					FightResult fightResult = loadUniqueLastFightResult(firstFighter, secondFighter);
					if (fightResult == null) {
						fightResult = createFightResult(firstFighter, secondFighter, 0);
						fightResult.setOlympicLevel(gcf.getOlympicLevel());
						fightResult.setOlympicPositionOnLevel(Long.valueOf(positionOnLevel));
						fightResult = saveFightResult(fightResult);
						
						positionOnLevel ++;
					}
					
					fightResult.setRedFighter(firstFighter);
					fightResult.setBlueFighter(secondFighter);
					
					result.add(fightResult);
					firstFighter = null;
				}
				else {
					throw new PersistenceException("Both fighters must have the same olympic level " + firstFighter + " " + gcf);
				}
			}
		}
		
		if (firstFighter != null) {
			FightResult fightResult = createFightResult(firstFighter, null, 0);
			fightResult.setOlympicLevel(firstFighter.getOlympicLevel());
			fightResult.setOlympicPositionOnLevel(Long.valueOf(positionOnLevel));
			result.add(fightResult);
		}
		
		return result;
		
	}

	private FightResult loadUniqueLastFightResult(
			GroupChampionshipFighter firstFighter,
			GroupChampionshipFighter secondFighter
	) throws PersistenceException {
		HqlQuery<FightResult> hql = getHelper().createHqlQuery(FightResult.class, getCmFightResult());
		
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getFirstFighter(), firstFighter));
		hql.addExpression(ExpressionHelper.eq(getCmFightResult().getSecondFighter(), secondFighter));
		
		hql.addExpression(ExpressionHelper.isNull(getCmFightResult().getNextRoundFightResult()));
		
		List<FightResult> result = getHelper().loadByHqlQuery(hql);
		if (result == null || result.isEmpty()) {
			return null;
		}
		if (result.size() > 1) {
			throw new PersistenceException("By olympic system last fight result must be unique for the two olympic fighters " + firstFighter + " and " + secondFighter);
		}
		
		return result.get(0);
	}
	
	private void sortFightResultForOlympicTree(List<FightResult> fightResults) {
		Collections.sort(fightResults, new Comparator<FightResult>() {
			@Override
			public int compare(FightResult o1, FightResult o2) {
				if (o1.getOlympicLevel().longValue() < o2.getOlympicLevel().longValue()) {
					return -1;
				}
				else if (o1.getOlympicLevel().longValue() > o2.getOlympicLevel().longValue()) {
					return 1;
				}
				else {
					if (o1.getOlympicPositionOnLevel().longValue() < o2.getOlympicPositionOnLevel().longValue()) {
						return -1;
					}
					else {
						return 1;
					}
				}
			}
		});
	}

	@Override
	public FightResult performFightResultOnFightAction(FightResult fightResult) throws PersistenceException {
		String type = fightResult.getFirstFighter().getFightingGroup().getType();
		
		if (SC.GROUP_TYPE.ROUND_ROBIN.equals(type)) {
			return saveFightResult(fightResult);
		}
		else if (SC.GROUP_TYPE.OLYMPIC.equals(type)) {
			return performOlympicFightResultOnFightAction(fightResult);
		}
		else {
			throw new IllegalArgumentException("Unsupported group type " + type);
		}
	}

	
	private FightResult performOlympicFightResultOnFightAction(FightResult fightResult) throws PersistenceException {
		FightResult fightResultInDB = loadFightResultById(fightResult.getId()); 
		
		Boolean dbFirstFighterWon = hasFirstFighterWon(fightResultInDB);
		Boolean actuallFirstFighterWon = hasFirstFighterWon(fightResult);
		
		if (!theSame(dbFirstFighterWon, actuallFirstFighterWon)) {
			List<FightResult> children = loadOlympicChildrenFightResults(fightResult);
			deleteStackOfFightResults(children);
		}
		
		return saveFightResult(fightResult);
	}
	
	private void deleteStackOfFightResults(List<FightResult> fightResults) throws PersistenceException {
		for (FightResult fr : fightResults) {
			List<FightResult> stack = getFightResultStack(fr);
			deleteFightResults(stack);
		}
	}
	
	private void deleteFightResults(List<FightResult> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	private List<FightResult> getFightResultStack(FightResult fightResult) {
		List<FightResult> result = new ArrayList<>();
		
		while (fightResult.getNextRoundFightResult() != null) {
			fightResult = fightResult.getNextRoundFightResult();
		}
		
		List<FightResult> previousFightResults = getAllPreviousFightResults(fightResult);
		
		result.add(fightResult);
		result.addAll(previousFightResults);
		
		return result;
	}

	/**
	 * Returns tree branch for the passed parent, not including this parent 
	 */
	private List<FightResult> loadOlympicChildrenFightResults(FightResult parent) throws PersistenceException {
		List<FightResult> all = loadOrCreateOlympicFightResults(parent.getFirstFighter().getFightingGroup());
		List<FightResult> result = new ArrayList<>();
		
		for (FightResult fr : all) {
			if (parent.getId().equals(fr.getId())) {
				collectAllChildrenRecursively(fr, result);
				break;
			}
		}
		
		return result;
	}

	private void collectAllChildrenRecursively(FightResult fightResult, List<FightResult> result) {
		if (fightResult == null || fightResult.getChildren() == null) {
			return;
		}
		
		for (FightResult fr : fightResult.getChildren()) {
			result.add(fr);
			collectAllChildrenRecursively(fr, result);
		}
	}

	private boolean theSame(Boolean b1, Boolean b2) {
		if (b1 == null && b2 == null) {
			return true;
		}
		else if (b1 != null && b2 != null) {
			return b1.equals(b2);
		}
		else {
			return false;
		}
	}

	private Boolean hasFirstFighterWon(FightResult fr) {
		Boolean won = null;
		
		if (fr.getFirstFighterPoints() != null && fr.getSecondFighterPoints() != null) {
			if (fr.isFirstFighterWon()) {
				won = Boolean.TRUE;
			} else if (fr.isSecondFighterWon()) {
				won = Boolean.FALSE;
			}
		}

		return won;
	}

	@Override
	public List<FightResultForPlan> loadOrCreateFightResults(List<FightingGroup> grs, boolean finalsAtTheEnd) throws PersistenceException {
		List<FightingGroup> groups = new ArrayList<>(grs);
		getChampionshipPlanService().sortGroupsForPlan(groups);
		
		Map<FightingGroup, List<FightResultForPlan>> result = new LinkedHashMap<>();
		for (FightingGroup group : groups) {
			if (SC.GROUP_TYPE.ROUND_ROBIN.equals(group.getType())) {
				List<FightResult> frs = loadOrCreateRoundRobinLastFightResults(group);
				List<FightResultForPlan> frsForPlan = frs.stream().map(fr -> new FightResultForPlan(fr, group, false)).collect(Collectors.toList());
				result.put(group, frsForPlan);
			} else if (SC.GROUP_TYPE.OLYMPIC.equals(group.getType())) {
				List<FightResult> frs = loadOrCreateOlympicFightResults(group);
				List<GroupChampionshipFighter> fighters = getFightingGroupService().loadGroupChampionshipFighters(group);
				
				int levelsNumberCount = MathUtils.calculateOlympicFightLevelsNumber(fighters.size());
				List<FightResultForPlan> frsForPlan = frs.stream().filter(fr -> fr.getOlympicLevel() + 1 < levelsNumberCount).map(fr -> new FightResultForPlan(fr, group, false)).collect(Collectors.toList());
				
				int totalFightsCount = MathUtils.calculateTotalFightsNumberForOlympicAnalytic(fighters.size());
				int restFights = totalFightsCount - frs.size();
				
				for (int i = 0; i < restFights; i ++) {
					frsForPlan.add(new FightResultForPlan(null, group, false));
				}
				result.put(group, frsForPlan);
			}
		}
		
		List<FightResultForPlan> organized = organize(result, finalsAtTheEnd);
		
		return organized;
	}

	private List<FightResultForPlan> organize(
			Map<FightingGroup, List<FightResultForPlan>> frsMap,
			boolean finalsAtTheEnd
	) throws PersistenceException {
		Map<FightingGroup, List<FightResultForPlan>> copy = new LinkedHashMap<>(frsMap);
		
		List<FightResultForPlan> ordinary = new ArrayList<>();
		List<FightResultForPlan> finals = new ArrayList<>();
		
		while (!copy.isEmpty()) {
			Set<FightingGroup> groups = new LinkedHashSet<>(copy.keySet());
			for (FightingGroup group : groups) {
				List<FightResultForPlan> frs = copy.get(group);
				if (frs.isEmpty()) {
					copy.remove(group);
				} else {
					FightResultForPlan fr = frs.remove(0);
					if (
							finalsAtTheEnd &&
							isOlympic(fr) &&
							frs.size() < 2
					) {
						finals.add(fr);
					} else {
						ordinary.add(fr);
					}
				}
			}
		}
		
		List<FightResultForPlan> result = new ArrayList<>();
		result.addAll(ordinary);
		
		if (!finals.isEmpty()) {
			finals = organizeFinals(finals);
			result.addAll(finals);
		}
		
		final AtomicInteger numberInPlan = new AtomicInteger();
		result.forEach(fr -> {
			if (!fr.isFake()) {
				fr.setNumberInPlan(String.valueOf(numberInPlan.incrementAndGet()));	
			}
		});
		
		return result;
	}

	private List<FightResultForPlan> organizeFinals(List<FightResultForPlan> finals) throws PersistenceException {
		
		Map<FightingGroup, List<FightResultForPlan>> finalsMap = new LinkedHashMap<>();
		for (FightResultForPlan frfp : finals) {
			finalsMap.computeIfAbsent(frfp.getFightingGroup(), (f) -> new ArrayList<>()).add(frfp);
		}
		
		List<FightResultForPlan> result = new ArrayList<>();
		int iteration = 0;
		while (!finalsMap.isEmpty()) {
			if (iteration == 0) {
				FightResultForPlan fakeFr = new FightResultForPlan(null, null, true);
				fakeFr.setNumberInPlan(getUIC().SEMI_FINALS());
				result.add(fakeFr);
			} else {
				FightResultForPlan fakeFr = new FightResultForPlan(null, null, true);
				fakeFr.setNumberInPlan(getUIC().FINALS());
				result.add(fakeFr);
			}
			Set<FightingGroup> groups = new LinkedHashSet<>(finalsMap.keySet());
			for (FightingGroup group : groups) {
				List<FightResultForPlan> frs = finalsMap.get(group);
				if (frs.isEmpty()) {
					finalsMap.remove(group);
				} else if (frs.size() >= 2 || iteration >= 1) {
					FightResultForPlan fr = frs.remove(0);
					result.add(fr);
					if (frs.isEmpty()) {
						finalsMap.remove(group);
					}
				}
			}
			iteration++;
		}
		return result;
	}

	private boolean isOlympic(FightResultForPlan fr) {
		return SC.GROUP_TYPE.OLYMPIC.equals(fr.getFightingGroup().getType());
	}

}

