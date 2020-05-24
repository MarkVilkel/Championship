/**
 * The file InviteToFightPanel.java was created on 2010.14.4 at 21:39:35
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
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
import com.ashihara.ui.app.fight.view.listener.WinCheckListener;
import com.ashihara.ui.app.utils.UIUtils;
import com.ashihara.ui.core.mvc.view.UIView;
import com.ashihara.ui.core.panel.KASPanel;
import com.ashihara.ui.core.panel.OkButtonPanel;
import com.ashihara.ui.tools.MessageHelper;

public class FightPanel extends KASPanel implements UIView<IFightModelUI<?>> {

	private static final long serialVersionUID = 1L;
	
	private final FightResult fightResult;
	private final FightResult nextFightResult;
	private final List<FightResult> nextFightResults;
	private final boolean useAdvancedNextFightResults;
	
	private final FightSettings fightSettings;
	private FighterBattleInfoPanel secondFighterBattleInfoPanel;
	private FighterBattleInfoPanel firstFighterBattleInfoPanel;
	
	private JPanel buttonsPanel;
	private TimePanel timePanel;
	private KASPanel fightersPanel;
	private JButton btnNextRound;
	private IFightModelUI<?> modelUI;
	
	private final RulesManager rulesManager;
	
	public FightPanel(
			FightResult fightResult,
			boolean useAdvancedNextFightResults,
			FightResult nextFightResult,
			List<FightResult> nextFightResults,
			FightSettings fightSettings,
			IFightModelUI<?> modelUI,
			RulesManager rulesManager
	) {
		this.fightResult = fightResult;
		this.useAdvancedNextFightResults = useAdvancedNextFightResults;
		this.nextFightResult = nextFightResult;
		this.nextFightResults = nextFightResults;
		this.modelUI = modelUI;
		this.fightSettings = fightSettings;
		this.rulesManager = rulesManager;
		
		init();
	}

	private void init() {
		add(getTimePanel(), BorderLayout.NORTH);
		add(getFightersPanel(), BorderLayout.CENTER);
		
		
		List<FightResult> frs = new ArrayList<>();
		if (useAdvancedNextFightResults) {
			if (nextFightResults != null) {
				frs.addAll(nextFightResults);
			}
		} else if (nextFightResult != null) {
			frs.add(nextFightResult);
		}
		
		if (!frs.isEmpty()) {
			KASPanel southPanel = new KASPanel();
			int fightSize = frs.size();
			
			KASPanel nextFightsPanel = new KASPanel(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			for (int i = 0; i < fightSize; i ++) {
				c.gridy = i;
				placeNextFighters(nextFightsPanel, c, frs.get(i), fightSize > 1 ? i + 1 : null);
			}
			southPanel.add(nextFightsPanel, BorderLayout.CENTER);
			southPanel.add(getButtonsPanel(), BorderLayout.SOUTH);
			add(southPanel, BorderLayout.SOUTH);
		} else {
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
			
			firstFighterBattleInfoPanel.setWinByJudgeDecisionCheckListener(new WinCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
						getSecondFighterBattleInfoPanel().getCheckWinByTKO().setSelected(false);
					}
					getFirstFighterBattleInfoPanel().getCheckWinByTKO().setEnabled(!checked);
					getFirstFighterBattleInfoPanel().getPointsPanel().setHighlighted(checked);
				}
			});
			
			
			firstFighterBattleInfoPanel.setWinByTKOCheckListener(new WinCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
						getSecondFighterBattleInfoPanel().getCheckWinByTKO().setSelected(false);
					}
					
					getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(!checked);
					
					
					Long extraPointsForTKO = rulesManager.getExatraPointsForTKO();
					CountPanel pointsPanel = getFirstFighterBattleInfoPanel().getPointsPanel();
					
					pointsPanel.checkAndChangeCount(checked, extraPointsForTKO);
					pointsPanel.setHighlighted(checked);
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
			
			secondFighterBattleInfoPanel.setWinByJudgeDecisionCheckListener(new WinCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
						getFirstFighterBattleInfoPanel().getCheckWinByTKO().setSelected(false);
					}
					getSecondFighterBattleInfoPanel().getCheckWinByTKO().setEnabled(!checked);
					getSecondFighterBattleInfoPanel().getPointsPanel().setHighlighted(checked);
				}
			});

			secondFighterBattleInfoPanel.setWinByTKOCheckListener(new WinCheckListener() {
				@Override
				public void checked(boolean checked) {
					if (checked) {
						getFirstFighterBattleInfoPanel().getCheckWinByTKO().setSelected(false);
						getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
					}
					getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(!checked);
					
					Long extraPointsForTKO = rulesManager.getExatraPointsForTKO();
					CountPanel pointsPanel = getSecondFighterBattleInfoPanel().getPointsPanel();
					
					pointsPanel.checkAndChangeCount(checked, extraPointsForTKO);
					pointsPanel.setHighlighted(checked);
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
		
		if (!theSamePointsCount) {
			getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
			getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setSelected(false);
		}
		
		getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(theSamePointsCount);
		getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().setEnabled(theSamePointsCount);
		
		boolean isWinByJudgeDecisionOrTKO = false;
		if (getSecondFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected()) {
			getSecondFighterBattleInfoPanel().getPointsPanel().setHighlighted(true);
			isWinByJudgeDecisionOrTKO = true;
		}
		
		if (getFirstFighterBattleInfoPanel().getCheckWinByJudgeDecision().isSelected()) {
			getFirstFighterBattleInfoPanel().getPointsPanel().setHighlighted(true);
			isWinByJudgeDecisionOrTKO = true;
		}
		
		if (getSecondFighterBattleInfoPanel().getCheckWinByTKO().isSelected()) {
			isWinByJudgeDecisionOrTKO = true;
		}
		
		if (getFirstFighterBattleInfoPanel().getCheckWinByTKO().isSelected()) {
			isWinByJudgeDecisionOrTKO = true;
		}
		
		if (isWinByJudgeDecisionOrTKO) {
			getBtnNextRound().setEnabled(false);
		} else {
			boolean enabled = Math.abs(getSecondFighterBattleInfoPanel().getPointsPanel().getCount() - getFirstFighterBattleInfoPanel().getPointsPanel().getCount()) <= rulesManager.getMaxPointsDifferenceForTheNextRound();
			getBtnNextRound().setEnabled(enabled);
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
	
	private void placeNextFighters(KASPanel nextFightPanel, GridBagConstraints c, FightResult fr, Integer fightNumber) {
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		if (rulesManager.redFighterFromTheLeft()) {
			c.weightx = 5;
			nextFightPanel.add(createNextRedPanel(fr, FlowLayout.LEFT), c);
			
			c.weightx = 1;
			c.gridx = 1;
			nextFightPanel.add(createLblNext(fightNumber), c);
			
			c.weightx = 5;
			c.gridx = 2;
			nextFightPanel.add(createNextBluePanel(fr, FlowLayout.RIGHT), c);
		} else {
			c.weightx = 5;
			nextFightPanel.add(createNextBluePanel(fr, FlowLayout.LEFT), c);
			
			c.weightx = 1;
			c.gridx = 1;
			nextFightPanel.add(createLblNext(fightNumber), c);
			
			c.weightx = 5;
			c.gridx = 2;
			nextFightPanel.add(createNextRedPanel(fr, FlowLayout.RIGHT), c);
		}
	}

	private GradientPanel createNextBluePanel(FightResult nextFightResult, int horizontalOrientation) {
		GradientPanel nextBluePanel = new GradientPanel(UIUtils.BLUE);
		nextBluePanel.setLayout(new FlowLayout(horizontalOrientation));
		nextBluePanel.add(createLblNextBlueFighter(nextFightResult));
		return nextBluePanel;
	}

	private GradientPanel createNextRedPanel(FightResult nextFightResult, int horizontalOrientation) {
		GradientPanel nextRedPanel = new GradientPanel(UIUtils.RED);
		nextRedPanel.setLayout(new FlowLayout(horizontalOrientation));
		nextRedPanel.add(createLblNextRedFighter(nextFightResult));
		return nextRedPanel;
	}
	
	private JLabel createLabel(int size) {
		JLabel lbl = new JLabel();
		lbl.setFont(new Font(lbl.getFont().getName(), lbl.getFont().getStyle(), size));
		return lbl;
	}
	
	private JButton createButton(int size) {
		JButton btn = new JButton();
		btn.setFont(new Font(btn.getFont().getName(), btn.getFont().getStyle(), size));
		return btn;
	}

	private JLabel createLblNextRedFighter(FightResult nextFightResult) {
		JLabel lblNextRedFighter = createLabel(30);
		
		if (nextFightResult != null) {
			lblNextRedFighter.setText(nextFightResult.getRedFighter().getChampionshipFighter().toShortString());
		}
		return lblNextRedFighter;
	}

	private JLabel createLblNextBlueFighter(FightResult nextFightResult) {
		JLabel lblNextBlueFighter = createLabel(30);
		
		if (nextFightResult != null) {
			lblNextBlueFighter.setText(nextFightResult.getBlueFighter().getChampionshipFighter().toShortString());
		}
		return lblNextBlueFighter;
	}

	private JComponent createLblNext(Integer fightNumber) {
		final JComponent next;
		String text = uic.NEXT_FIGHT().toUpperCase() + (fightNumber != null ? (" " + fightNumber) : "");
		if (useAdvancedNextFightResults && (fightNumber == null || fightNumber.longValue() == 1)) {
			JButton btn = createButton(20);
			btn.addActionListener((e) -> {
				int result = MessageHelper.showConfirmationMessage(
						null,
						uic.NEXT_FIGHT(),
						uic.ARE_YOU_SURE_YOU_WANT_TO_START_THE_NEXT_FIGHT()
				);
				if (JOptionPane.YES_OPTION == result) {
					getModelUI().nextFight();
				}
			});
			btn.setText(text);
			btn.setHorizontalAlignment(SwingUtilities.CENTER);
			next = btn;
		} else {
			JLabel lbl = createLabel(20);
			lbl.setText(text);
			lbl.setHorizontalAlignment(SwingUtilities.CENTER);
			next = lbl;
		}
		return next;
	}
	
}
