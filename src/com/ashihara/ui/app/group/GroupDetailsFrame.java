/**
 * The file FighterDetailsDialog.java was created on 2010.30.3 at 23:44:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.group;

import java.awt.BorderLayout;

import com.ashihara.datamanagement.pojo.FightingGroup;
import com.ashihara.ui.app.group.model.GroupEditModel;
import com.ashihara.ui.core.dockable.AKIdentifiedDockable;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;

public class GroupDetailsFrame extends AKIdentifiedDockable<Long> {

	private static final long serialVersionUID = 1L;
	
	private GroupEditModel groupEditModel;
	private final FightingGroup group;
	private final UIStatePerformer<FightingGroup> groupsManager;

	
	
	public GroupDetailsFrame(FightingGroup group) {
		this(group, null);
	}
	
	public GroupDetailsFrame(FightingGroup group, UIStatePerformer<FightingGroup> groupsManager) {
		super(ApplicationManager.getInstance().getUic().GROUP(), group.getId());
		this.groupsManager = groupsManager;
		
		this.group = group;
		changeTitle(ApplicationManager.getInstance().getUic().GROUP() + " '" + group + "'");
		
		getMainPanel().add(getGroupEditModel().getViewUI(), BorderLayout.CENTER);
	}

	public GroupEditModel getGroupEditModel() {
		if (groupEditModel == null) {
			groupEditModel = new GroupEditModel(group, groupsManager);
		}
		return groupEditModel;
	}

}
