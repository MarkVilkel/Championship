/**
 * The file DBWrapper.java was created on 2012.21.4 at 01:06:23
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo.wraper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.pojo.Championship;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.rtu.persistence.core.Do;

public class DBWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Map<Class<Do>, List<Do>> dbObjectsMap;
	
	private Championship championship;
	private List<FightingGroup> fightingGroups;


	public Map<Class<Do>, List<Do>> getDbObjectsMap() {
		return dbObjectsMap;
	}


	public void setDbObjectsMap(Map<Class<Do>, List<Do>> dbObjectsMap) {
		this.dbObjectsMap = dbObjectsMap;
	}


	public Championship getChampionship() {
		return championship;
	}


	public void setChampionship(Championship championship) {
		this.championship = championship;
	}


	public List<FightingGroup> getFightingGroups() {
		return fightingGroups;
	}


	public void setFightingGroups(List<FightingGroup> fightingGroups) {
		this.fightingGroups = fightingGroups;
	}
}
