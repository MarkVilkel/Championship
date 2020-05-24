/**
 * The file ObjectUtils.java was created on 12 марта 2020 г. at 21:25:23
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl.util;

import com.ashihara.datamanagement.pojo.GroupChampionshipFighter;

public class ObjectUtils {

	public static boolean areTheSame(GroupChampionshipFighter f1, GroupChampionshipFighter f2) {
		boolean matched = false;
		if (f1 == null && f2 == null) {
			matched = true;
		} else if (
				f1 != null &&
				f2 != null &&
				f1.getId().equals(f2.getId())
		) {
			matched = true;
		}
		return matched;
	}


}
