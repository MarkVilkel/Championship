/**
 * The file FightSystemModel.java was created on 2010.19.4 at 23:00:07
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group.model;

import java.util.List;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.ashihara.ui.core.mvc.model.AKAbstractModelUI;
import com.ashihara.ui.core.mvc.view.UIView;
import com.rtu.exception.PersistenceException;

public abstract class AbstractFightSystemModel<T extends UIView<?>> extends AKAbstractModelUI<T> {

	public abstract void save() throws AKValidationException, PersistenceException;
	public abstract void setGroup(FightingGroup fightingGroup);
	public abstract void reset() throws PersistenceException;
	public abstract void setVisible(boolean b);
	public abstract void startGroup() throws PersistenceException;
	public abstract List<FighterPlace> loadGroupTournamentResults(FightingGroup fightingGroup) throws PersistenceException;
}
