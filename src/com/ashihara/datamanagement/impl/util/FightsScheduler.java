package com.ashihara.datamanagement.impl.util;
    
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;

public class FightsScheduler {
    
    private final int ninjaCount; // koli4estvo u4astengoff
    private final int fights; // obsheje koli4estvo bojoff
    private final int minOtdih; // min koli4estvo roundov otdiha dlja odnogo u4astnega
    
    private final List<GroupChampionshipFighter> queue; // psevdo o4eredj ninzj v ozhidanii boja
    private final List<FightResult> result;
    
    public FightsScheduler(List<GroupChampionshipFighter> fighters) {
        ninjaCount = fighters.size();
        
        queue = new LinkedList<GroupChampionshipFighter>();
        
        int rounds = 0;
        for (int i = 0; i < ninjaCount; i++) {
            queue.add(fighters.get(i)); // zapolnjaem o4eredj prioritetov po porjadku
            
            rounds += i; // zaodno pods4itivaem koli4estvo bojov
        }
        fights = rounds;
        
        minOtdih = fights / ninjaCount - 1; // ne dokazano, no pohodu tak estj
        
        result = new ArrayList<FightResult>(fights);
        
        calculate();
    }
    
    private void calculate() {
        
        for (int currentFightIdx = 0; currentFightIdx < fights; currentFightIdx++) {
            
        	FightResult fight = null;
            boolean found = false;
            int firstNinjaIdx = 0; // index in queue, I hope it always will be 0
            do {
            	GroupChampionshipFighter ninja1 =  queue.get(firstNinjaIdx);
                
                int secondNinjaIdx = 0; // index in queue
                do {
                    if (secondNinjaIdx == firstNinjaIdx) {
                        secondNinjaIdx++;
                    }
                    GroupChampionshipFighter ninja2 = queue.get(secondNinjaIdx);
                    fight = createFightResult(ninja1, ninja2, 1);
                    found |= checkUniqueness(fight);
                    
                    secondNinjaIdx++;
                } while(! found && (secondNinjaIdx < ninjaCount - (2*minOtdih) || secondNinjaIdx < firstNinjaIdx)); // 2-oe uslovie == ne trogaem nedavno vistupajushih
                
                firstNinjaIdx++;
            } while(! found);
            
            // perenosim v konec o4eredi
            if (! queue.remove(fight.getFirstFighter())) System.out.println("hrenj");
            if (! queue.remove(fight.getSecondFighter())) System.out.println("hrenj");
            queue.add(fight.getFirstFighter());
            queue.add(fight.getSecondFighter());
            
            result.add(fight);
        }
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

	private boolean checkUniqueness(FightResult fight) {
		GroupChampionshipFighter f1 = fight.getFirstFighter();
		GroupChampionshipFighter f2 = fight.getSecondFighter();

		for (FightResult fr : result) {
			if (
					fr.getFirstFighter().getChampionshipFighter().getId().equals(f1.getChampionshipFighter().getId()) &&
					fr.getSecondFighter().getChampionshipFighter().getId().equals(f2.getChampionshipFighter().getId())
					||
					fr.getFirstFighter().getChampionshipFighter().getId().equals(f2.getChampionshipFighter().getId()) &&
					fr.getSecondFighter().getChampionshipFighter().getId().equals(f1.getChampionshipFighter().getId())
			) {
				return false;
			}
		}
		return true;
	}


	
    
//    private boolean checkUniqueness(FightResult fight) {
//        for (FightResult f : result) {
//            if (f.equals(fight)) {
//                return false;
//            }
//        }
//        return true;
//    }
    
    public List<FightResult> getResult() {
        return result;
    }
    
    
    
    
//    public static void main(String... args) {
//    	for (int N = 1; N < 100; N++) {
//        	
//    		
//        	int fightCount = 0;
//        	for (int i = 0; i < N; i++) {
//        		fightCount += i;
//        	}
//        	
//        	System.out.println("Ninja count = " + N + " fight count = " + fightCount);
//        	
//            FightsScheduler creator = new FightsScheduler(N);
//            List<Fight> list = creator.getResult();
//            
//            if (fightCount != list.size()) {
//            	throw new IllegalArgumentException(fightCount + " != " + list.size());
//            }
//            
//            for (Fight f : list) {
//            	System.out.println(f.ninja1 + " - " + f.ninja2);
//            }
//            
//            for (int i = 0; i < list.size() - 1; i++) {
//            	for (int k = i + 1; k < list.size(); k++) {
//            		if (list.get(i).equals(list.get(k))) {
//            			throw new IllegalArgumentException("Not uniqe record exists");
//            		}
//            	}
//            }
//    	}
//    	
//    }
    
    
    
//    public class Fight {
//        private int ninja1, ninja2;
//        
//        public Fight(int ninja1, int ninja2) {
//            this.ninja1 = ninja1;
//            this.ninja2 = ninja2;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj)
//                return true;
//            if (obj == null)
//                return false;
//            if (getClass() != obj.getClass())
//                return false;
//            
//            Fight other = (Fight) obj;
//            
//            if (
//                    (ninja1 == other.ninja1 && ninja2 == other.ninja2)
//                    ||
//                    (ninja1 == other.ninja2 && ninja2 == other.ninja1)
//            ) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        
//    }
    
}
