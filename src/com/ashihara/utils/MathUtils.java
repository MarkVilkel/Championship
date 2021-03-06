/**
 * The file MathUtils.java was created on 2009.20.8 at 22:41:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.utils;

import java.math.BigDecimal;

public class MathUtils {
	
	public static double normalDoubleRound(double value){
		return roundDouble(value, 2);
	}
	
	public static double roundDouble(double value, int decimalPlace){
		double power_of_ten = 1;
		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;
		return Math.rint(value * power_of_ten) / power_of_ten;
	}
	
	public static String normalRound(double value){
		return round(value, 2);
	}
	
	public static String round(double value, int decimalPlace){
		String s = String.valueOf(roundDouble(value, decimalPlace));
		int actualDecimalsCount = 0;
		for (int i = 0; i < s.length(); i++)
			if (actualDecimalsCount>0)
				actualDecimalsCount++;
			else if (s.charAt(i) == '.' || s.charAt(i) == ',')
				actualDecimalsCount++;
		
		actualDecimalsCount--;
		
		for(int i = 0; i< decimalPlace-actualDecimalsCount; i++)
			s+="0";
		
		return s;
	}

	public static int calculateIdealFightersNumberForOlympic(int actualFightersNumber) {
		if (actualFightersNumber <= 0) {
			return 0;
		}
		if (actualFightersNumber == 1) {
			return 1;
		}
		
		int idealFightersNumber = 1;
		for (int i = 0; i < actualFightersNumber; i++) {
			idealFightersNumber *= 2;
			if (idealFightersNumber >= actualFightersNumber) {
				return idealFightersNumber;
			}
		}
		return idealFightersNumber;
	}
	
	public static int calculateOlympicFightLevelsNumber(int actualFightersNumber) {
		int idealFightersNumber = calculateIdealFightersNumberForOlympic(actualFightersNumber);
		int levelsCount = 1;//idealFightersNumber == actualFightersNumber ? 0 : 1;
		while (idealFightersNumber > 1) {
			levelsCount ++;
			idealFightersNumber /= 2;
		}
		return levelsCount;
	}
	
	public static long mul2(long value, long mulTimes) {
		for (int i = 0; i < mulTimes; i++) {
			value *= 2;
		}
		return value;
	}
	
	public static long muln(long value, long mulTimes, long n) {
		for (int i = 0; i < mulTimes; i++) {
			value *= n;
		}
		return value;
	}
	
	public static String removeTrailingZeros(double d) {
		 return new BigDecimal(Double.toString(d)).stripTrailingZeros().toPlainString();
	}

	private static int calculateFightsNumberForIdealFightersNummberForOlympic(int idealFightersNumber) {
		if (idealFightersNumber == 0 || idealFightersNumber == 1) {
			return 0;
		}
		
		int i = 0;
		while ((idealFightersNumber = idealFightersNumber / 2) >= 1) {
			i += idealFightersNumber;
		}
		return i;
	}

	public static int calculateTotalFightsNumberForOlympicAnalytic(int actualFightersNumber) {
		if (actualFightersNumber <= 1) {
			return 0;
		} else if (actualFightersNumber == 2) {
			return 1;
		} else if (actualFightersNumber == 3) {
			return 2;
		} else {
			return actualFightersNumber;
		}
	}
	
	public static int calculateTotalFightsCountForOlympic(int actualFightersNumber) {
		if (actualFightersNumber == 0 || actualFightersNumber == 1) {
			return 0;
		}

		int idealFightersNumber = calculateIdealFightersNumberForOlympic(actualFightersNumber);
		
		int fightersCountToGoToTheSecondLevel = idealFightersNumber - actualFightersNumber;
		int result = 0;
		if (fightersCountToGoToTheSecondLevel == 0) {
			result = calculateFightsNumberForIdealFightersNummberForOlympic(idealFightersNumber);
		} else {
			int zeroLevelFightersNumber = actualFightersNumber - fightersCountToGoToTheSecondLevel;
			result = zeroLevelFightersNumber / 2;
			
			int firstLevelFightersNumber = fightersCountToGoToTheSecondLevel + result;
			result += calculateFightsNumberForIdealFightersNummberForOlympic(firstLevelFightersNumber);
		}
		
		if (actualFightersNumber > 3) {
			result += 1; // for the 3 and 4 place
		}
		return result;
	}
	
	public static void main(String[] args) {
		for (int i = 0 ; i < 50; i ++) {
			System.out.println( i + " " + calculateTotalFightsCountForOlympic(i));
		}
	}

}
