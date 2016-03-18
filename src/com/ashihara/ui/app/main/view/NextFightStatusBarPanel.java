package com.ashihara.ui.app.main.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.component.KASLinkLabel;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.util.NextFightChangeListener;
import com.ashihara.ui.util.NextFightManager;

public class NextFightStatusBarPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private KASLinkLabel lnkClear;
	
	private JLabel lblNextFight;
	private JLabel lblVs;
	private JLabel redFighter;
	private JLabel blueFighter;
	
	public NextFightStatusBarPanel() {
		
		init();
	}

	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setOpaque(false);
		
		add(getLblNextFight());
		add(getRedFighter());
		add(getLblVs());
		add(getBlueFighter());
		add(getLnkClear());
		
		NextFightManager.getInstance().addNextFightChangeListener(new NextFightChangeListener() {
			@Override
			public void nextFightChanged(FightResult fightResult) {
				boolean visible = fightResult != null;
				if (fightResult == null) {
					getRedFighter().setText("");
					getBlueFighter().setText("");					
				}
				else {
					getRedFighter().setText(fightResult.getRedFighter().getChampionshipFighter().toString());
					getBlueFighter().setText(fightResult.getBlueFighter().getChampionshipFighter().toString());
				}
				
				getLnkClear().setVisible(visible);
				getLblNextFight().setVisible(visible);
				getLblVs().setVisible(visible);
			}
		});
	}

	private KASLinkLabel getLnkClear() {
		if (lnkClear == null) {
			lnkClear = new KASLinkLabel(uic.CLEAR());
			lnkClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					NextFightManager.getInstance().setNextFightResult(null);
					NextFightManager.getInstance().setNextUiStatePerformer(null);
				}
			});
			
			lnkClear.setVisible(false);
		}
		return lnkClear;
	}

	private JLabel getRedFighter() {
		if (redFighter == null) {
			redFighter = new JLabel();
			redFighter.setBackground(UIUtils.RED);
			redFighter.setOpaque(true);
		}
		return redFighter;
	}

	private JLabel getBlueFighter() {
		if (blueFighter == null) {
			blueFighter = new JLabel();
			blueFighter.setBackground(UIUtils.BLUE);
			blueFighter.setOpaque(true);
		}
		return blueFighter;
	}

	private JLabel getLblNextFight() {
		if (lblNextFight == null) {
			lblNextFight = new JLabel(uic.NEXT_FIGHT() + " - ");
			
			lblNextFight.setVisible(false);
		}
		return lblNextFight;
	}

	private JLabel getLblVs() {
		if (lblVs == null) {
			lblVs = new JLabel(uic.VS());
			
			lblVs.setVisible(false);
		}
		return lblVs;
	}

}
