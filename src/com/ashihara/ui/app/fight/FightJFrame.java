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

import javax.swing.JFrame;

import com.ashihara.datamanagement.pojo.FightResult;
import com.ashihara.datamanagement.pojo.FightSettings;
import com.ashihara.enums.UIC;
import com.ashihara.ui.app.fight.model.FightModelUI;
import com.ashihara.ui.core.interfaces.UIStatePerformer;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.UIFactory;

public class FightJFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected static UIC uic = ApplicationManager.getInstance().getUic();
	
	private final FightResult fightResult;
	private final UIStatePerformer<FightResult> callbacker;
	private final FightSettings fightSettings;
	
	private FightModelUI fightModelUI;
	
	private boolean shown = false;
	private final boolean isNextRound;
	
	public FightJFrame(
			FightResult fightResult,
			FightSettings fightSettings,
			boolean isNextRound,
			UIStatePerformer<FightResult> callbacker
	) {
		super(
				fightResult.getRedFighter().getChampionshipFighter().getFighter().toString() +
				" VS " +
				fightResult.getBlueFighter().getChampionshipFighter().getFighter().toString() +
				" " +
				uic.FIGHT());
		
		this.fightResult = fightResult;
		this.callbacker = callbacker;
		this.isNextRound = isNextRound;
		this.fightSettings = fightSettings;
		
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3+5, Toolkit.getDefaultToolkit().getScreenSize().height*2/3 + 50));
		
		setContentPane(getFightModelUI().getViewUI());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onClose();
			}
			
		});
		
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
		
	}
	
	public void dispose(){
		super.dispose();
		onClose();
	}

	private FightModelUI getFightModelUI() {
		if (fightModelUI == null) {
			fightModelUI = new FightModelUI(fightResult, fightSettings, isNextRound, callbacker);
		}
		return fightModelUI;
	}

}
