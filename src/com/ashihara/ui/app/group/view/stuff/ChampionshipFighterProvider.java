package com.ashihara.ui.app.group.view.stuff;

import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.pojo.ChampionshipFighter;

public class ChampionshipFighterProvider {
	
	private List<ChampionshipFighter> items;

	public List<ChampionshipFighter> getItems() {
		return items;
	}

	public List<ChampionshipFighter> getItemsCopy() {
		if (items != null) {
			return new ArrayList<>(items);
		}
		return new ArrayList<>(); 
	}

	public void setItems(List<ChampionshipFighter> items) {
		this.items = items;
	}

}
