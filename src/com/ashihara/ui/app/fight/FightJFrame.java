/**
 * The file InviteToFightDialog.java was created on 2010.14.4 at 21:42:37
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.enums.UIC;
import com.ashihara.ui.app.fight.model.FightModelUI;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;

public class FightJFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	private final FightResult fightResult;
	private final UIStatePerformer<FightResult> nextRoundCallbacker;
	private final FightSettings fightSettings;
	
	private FightModelUI fightModelUI;
	
	private boolean shown = false;
	private final boolean isNextRound;
	
	private final WindowAdapter windowAdapter;
	private final boolean advancedNextFights;
	private final List<FightResult> nextFights;
	private final UIStatePerformer<FightResult> closeCallbacker;


	public FightJFrame(
			FightResult fightResult,
			FightSettings fightSettings,
			boolean isNextRound,
			UIStatePerformer<FightResult> callbacker
	) {
		this(fightResult, fightSettings, isNextRound, callbacker, false, null, null);
	}

	public FightJFrame(
			FightResult fightResult,
			FightSettings fightSettings,
			boolean isNextRound,
			UIStatePerformer<FightResult> nextRoundCallbacker,
			boolean advancedNextFights,
			List<FightResult> nextFights,
			UIStatePerformer<FightResult> closeCallbacker
	) {
		super(
				fightResult.getRedFighter().getChampionshipFighter().getFighter().toString() +
				" VS " +
				fightResult.getBlueFighter().getChampionshipFighter().getFighter().toString() +
				" " +
				uic.FIGHT());
		
		this.fightResult = fightResult;
		this.nextRoundCallbacker = nextRoundCallbacker;
		this.isNextRound = isNextRound;
		this.fightSettings = fightSettings;
		this.advancedNextFights = advancedNextFights;
		this.nextFights = nextFights;
		this.closeCallbacker = closeCallbacker;
		
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3+5, Toolkit.getDefaultToolkit().getScreenSize().height*2/3 + 50));
		
		setContentPane(getFightModelUI().getViewUI());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		};
		addWindowListener(windowAdapter);
		
		ApplicationManager.getInstance().registerComponent(this);
		
		setResizable(true);
//		UIFactory.sizeCenterVisible(this, Toolkit.getDefaultToolkit().getScreenSize());
		setVisible(true);
	}

	private void onClose() {
		ApplicationManager.getInstance().unregisterComponent(this);
		
		if (!shown) {
			shown = true;
			getFightModelUI().dispose();
		}
		
		removeWindowListener(windowAdapter);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		onClose();
	}

	private FightModelUI getFightModelUI() {
		if (fightModelUI == null) {
			fightModelUI = new FightModelUI(fightResult, fightSettings, isNextRound, nextRoundCallbacker, advancedNextFights, nextFights, closeCallbacker);
		}
		return fightModelUI;
	}

}
