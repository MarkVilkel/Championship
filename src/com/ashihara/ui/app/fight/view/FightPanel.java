/**
 * The file InviteToFightPanel.java was created on 2010.14.4 at 21:39:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.enums.SC;
import com.ashihara.ui.app.championship.data.RulesManager;
import com.ashihara.ui.app.fight.model.IFightModelUI;
import com.ashihara.ui.app.fight.view.CountPanel.CountListener;
import com.ashihara.ui.app.fight.view.listener.PointsCountListener;
import com.ashihara.ui.app.fight.view.listener.WarningsCountListener;
import com.ashihara.ui.app.fight.view.listener.WinByJudgeDecisionCheckListener;
import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.OkButtonPanel;
import com.ashihara.ui.tools.MessageHelper;

public class FightPanel extends KASPanel implements UIView<IFightModelUI<?>> {

	private static final long serialVersionUID = 1L;
	
	private final FightResult fightResult;
	private final FightResult nextFightResult;
	
	private final FightSettings fightSettings;
	private FighterBattleInfoPanel secondFighterBattleInfoPanel;
	private FighterBattleInfoPanel firstFighterBattleInfoPanel;
	
	private JPanel buttonsPanel;
	private TimePanel timePanel;
	private KASPanel fightersPanel;
	private JButton btnNextRound;
	private IFightModelUI<?> modelUI;
	
	private KASPanel nextFightPanel;
	private GradientPanel nextBluePanel, nextRedPanel;
	private JLabel lblNextRedFighter, lblNextBlueFighter, lblNext, lblVs;
	
	private final RulesManager rulesManager;
	
	public FightPanel(
			FightResult fightResult,
			FightResult nextFightResult,
			FightSettings fightSettings,
			IFightModelUI<?> modelUI,
			RulesManager rulesManager
	) {
		this.fightResult = fightResult;
		this.nextFightResult = nextFightResult;
		this.modelUI = modelUI;
		this.fightSettings = fightSettings;
		this.rulesManager = rulesManager;
		
		init();
	}

	private void init() {
		
		add(getTimePanel(), BorderLayout.NORTH);
		add(getFightersPanel(), BorderLayout.CENTER);
		

		if (nextFightResult != null) {
			KASPanel southPanel = new KASPanel();
			southPanel.add(getNextFightPanel(), BorderLayout.CENTER);
			southPanel.add(getButtonsPanel(), BorderLayout.SOUTH);
			add(southPanel, BorderLayout.SOUTH);
		}
		else {
			add(getButtonsPanel(), BorderLayout.SOUTH);
		}
	}

	public FighterBattleInfoPanel getFirstFighterBattleInfoPanel() {
		if (firstFighterBattleInfoPanel == null) {
			firstFighterBattleInfoPanel = new FighterBattleInfoPanel(
					fightResult.getRedFighter().getChampionshipFighter(),
					UIUtils.RED,
					rulesManager
			);
			firstFighterBattleInfoPanel.setWarningsChangeListener(
					new WarningsCountListener(
							getSecondFighterBattleInfoPanel().getPointsPanel(),
							rulesManager
					)
			);
			firstFighterBattleInfoPanel.addPointsChangeListener(
					new PointsCountListener(
							getSecondFighterBattleInfoPanel().getPointsPanel(),
							rulesManager
					)
			);
			
			firstFighterBattleInfoPanel.addPointsChangeListener(new CountListener() {
				@Override
				public void countIncreased(CountPanel countPanel) {
					performButtonNextRoundEnability();
				}
				@Override
				public void countDecreased(CountPanel countPanel) {
					performButtonNextRoundEnability();
				}
			});
			
			firstFighterBattleInfoPanel.setWinByJudgeDecisionCheckListener(new WinByJudgeDecisionCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
					}
					getFirstFighterBattleInfoPanel().getPointsPanel().setHighlighted(checked);
				}
			});
		}
		return firstFighterBattleInfoPanel;
	}

	public FighterBattleInfoPanel getSecondFighterBattleInfoPanel() {
		if (secondFighterBattleInfoPanel == null) {
			secondFighterBattleInfoPanel = new FighterBattleInfoPanel(
					fightResult.getBlueFighter().getChampionshipFighter(),
					UIUtils.BLUE,
					rulesManager
			);
			secondFighterBattleInfoPanel.setWarningsChangeListener(
					new WarningsCountListener(
							getFirstFighterBattleInfoPanel().getPointsPanel(),
							rulesManager
					)
			);
			secondFighterBattleInfoPanel.addPointsChangeListener(
					new PointsCountListener(
							getFirstFighterBattleInfoPanel().getPointsPanel(),
							rulesManager
					)
			);
			
			secondFighterBattleInfoPanel.addPointsChangeListener(new CountListener() {
				@Override
				public void countIncreased(CountPanel countPanel) {
					performButtonNextRoundEnability();
				}
				@Override
				public void countDecreased(CountPanel countPanel) {
					performButtonNextRoundEnability();
				}
			});
			
			secondFighterBattleInfoPanel.setWinByJudgeDecisionCheckListener(new WinByJudgeDecisionCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
					}
					getSecondFighterBattleInfoPanel().getPointsPanel().setHighlighted(checked);
				}
			});

		}
		return secondFighterBattleInfoPanel;
	}
	
	public TimePanel getTimePanel() {
		if (timePanel == null) {
			long roundNumber = fightResult.getRoundNumber().longValue();
			long roundNumberForUI = roundNumber;
			
			if (fightResult.getFirstFighter() != null && SC.GROUP_TYPE.OLYMPIC.equals(fightResult.getFirstFighter().getFightingGroup().getType())) {
				roundNumberForUI ++;
			}
			
			timePanel = new TimePanel(
					fightSettings.getTatami(),
					roundNumberForUI,
					fightSettings.getTimeForRound(roundNumberForUI)
			);
			
			if (roundNumberForUI >= rulesManager.getMaxRoundsCount()) {
				getBtnNextRound().setVisible(false);
			}
		}
		return timePanel;
	}

	public void dispose() {
		getTimePanel().dispose();
	}

	@Override
	public IFightModelUI<?> getModelUI() {
		return modelUI;
	}

	@Override
	public void setModelUI(IFightModelUI<?> modelUI) {
		this.modelUI = modelUI;
	}

	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel(new BorderLayout(1, 1));
			
			OkButtonPanel okButtonPanel = new OkButtonPanel(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					disposeParent();
				}
			});
			
			JButton btnSwitchFighters = new JButton(uic.SWITCH_FIGHTERS());
			btnSwitchFighters.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getModelUI().switchFighters();
				}
			});
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(getBtnNextRound());
//			panel.add(btnSwitchFighters);
			
			buttonsPanel.add(panel, BorderLayout.WEST);
			buttonsPanel.add(okButtonPanel, BorderLayout.EAST);

		}
		return buttonsPanel;
	}
	
	private KASPanel getFightersPanel() {
		if (fightersPanel == null) {
			fightersPanel = new KASPanel(new GridLayout(1, 2));
			
			if (rulesManager.redFighterFromTheLeft()) {
				fightersPanel.add(getFirstFighterBattleInfoPanel());
				fightersPanel.add(getSecondFighterBattleInfoPanel());
				
			} else {
				fightersPanel.add(getSecondFighterBattleInfoPanel());
				fightersPanel.add(getFirstFighterBattleInfoPanel());
			}
		}
		return fightersPanel;
	}

	public void performButtonNextRoundEnability() {
		boolean theSamePointsCount = getSecondFighterBattleInfoPanel().getPointsPanel().getCount() == getFirstFighterBattleInfoPanel().getPointsPanel().getCount();
		boolean enabled = Math.abs(getSecondFighterBattleInfoPanel().getPointsPanel().getCount() - getFirstFighterBattleInfoPanel().getPointsPanel().getCount()) <= rulesManager.getMaxPointsDifferenceForTheNextRound();
		getBtnNextRound().setEnabled(enabled);
		
		if (!theSamePointsCount) {
			getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
			getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
		}
		
		getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(theSamePointsCount);
		getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(theSamePointsCount);
		
		if (getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected()) {
			getSecondFighterBattleInfoPanel().getPointsPanel().setHighlighted(true);
		}
		
		if (getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected()) {
			getFirstFighterBattleInfoPanel().getPointsPanel().setHighlighted(true);
		}
	}

	private JButton getBtnNextRound() {
		if (btnNextRound == null) {
			btnNextRound = new JButton(uic.NEXT_ROUND());
			btnNextRound.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = MessageHelper.showConfirmationMessage(
							null,
							uic.NEXT_ROUND(),
							uic.ARE_YOU_SURE_YOU_WANT_TO_START_THE_NEXT_ROUND()
					);
					if (JOptionPane.YES_OPTION == result) {
						getModelUI().nextRound();
					}
				}
			});
		}
		return btnNextRound;
	}

	private KASPanel getNextFightPanel() {
		if (nextFightPanel == null) {
			nextFightPanel = new KASPanel(new GridLayout(1, 3));
			
			if (rulesManager.redFighterFromTheLeft()) {
				nextFightPanel.add(getNextRedPanel());
				nextFightPanel.add(getLblNext());
				nextFightPanel.add(getNextBluePanel());
			} else {
				nextFightPanel.add(getNextBluePanel());
				nextFightPanel.add(getLblNext());
				nextFightPanel.add(getNextRedPanel());
			}
			
		}
		return nextFightPanel;
	}

	private GradientPanel getNextBluePanel() {
		if (nextBluePanel == null) {
			nextBluePanel = new GradientPanel(UIUtils.BLUE);
			
			nextBluePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			nextBluePanel.add(getLblNextBlueFighter());
		}
		return nextBluePanel;
	}

	private GradientPanel getNextRedPanel() {
		if (nextRedPanel == null) {
			nextRedPanel = new GradientPanel(UIUtils.RED);
			
			nextRedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			nextRedPanel.add(getLblNextRedFighter());
		}
		return nextRedPanel;
	}
	
	private JLabel createLabel(int size) {
		JLabel lbl = new JLabel();
		lbl.setFont(new Font(lbl.getFont().getName(), lbl.getFont().getStyle(), size));
		return lbl;
	}
	

	private JLabel getLblNextRedFighter() {
		if (lblNextRedFighter == null) {
			lblNextRedFighter = createLabel(30);
			
			if (nextFightResult != null) {
				lblNextRedFighter.setText(nextFightResult.getRedFighter().getChampionshipFighter().toShortString());
			}
		}
		return lblNextRedFighter;
	}

	private JLabel getLblNextBlueFighter() {
		if (lblNextBlueFighter == null) {
			lblNextBlueFighter = createLabel(30);
			
			if (nextFightResult != null) {
				lblNextBlueFighter.setText(nextFightResult.getBlueFighter().getChampionshipFighter().toShortString());
			}
		}
		return lblNextBlueFighter;
	}

	private JLabel getLblNext() {
		if (lblNext == null) {
			lblNext = createLabel(30);
			
			lblNext.setText(uic.NEXT_FIGHT().toUpperCase());
			lblNext.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblNext;
	}

	private JLabel getLblVs() {
		if (lblVs == null) {
			lblVs = createLabel(20);
			
			lblVs.setText(uic.VS());
		}
		return lblVs;
	}

}
