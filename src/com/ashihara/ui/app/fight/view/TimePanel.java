/**
 * The file TimePanel.java was created on 2011.12.10 at 21:34:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.ashihara.ui.core.panel.KASPanel;

public class TimePanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private final SimpleDateFormat DATE_FOMAT = new SimpleDateFormat("mm:ss");
	
	private Timer timer;
	private final String tatami;
	private final String fightNumber;
	private final String fightInfo;
	private final long roundNumberForUI;
	
	private final int LBL_SIZE = 35;
	
	private enum State {
		STARTED,
		PAUSED,
		STOPED
	}
	
	private JButton btnStartPause;
	private JButton btnStop;
	private JButton btnInc;
	private JButton btnDec;
	private JButton btn1Minute;
	private JButton btn2Minutes;
	private JButton btn3Minutes;
	
	
	private JLabel lblTime;
	private JLabel lblTatami;
	private JLabel lblFightNumber;
	private JLabel lblFightInfo;
	private JLabel lblRoundNumber;
	private KASPanel buttonsPanel;
	private KASPanel infoPanel;
	
	private State state;
	
	private long time = 3 * 60 * 1000;
	private final long PERIOD = 1000; 
	private final long TIME_INC_PERIOD = 10 * 1000;
	
	
	public TimePanel(
			String tatami,
			long roundNumberForUI,
			long time,
			String fightNumber,
			String fightInfo
	) {
		this.tatami = tatami;
		this.roundNumberForUI = roundNumberForUI;
		this.time = time;
		this.fightNumber = fightNumber;
		this.fightInfo = fightInfo;
		
		init();
		pause();
		showTime();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getBtnStartPause().requestFocus();
			}
		});
	}

	private void init() {
		setLayout(new BorderLayout());
		
		add(getInfoPanel(), BorderLayout.WEST);
		add(getLblRoundNumber(), BorderLayout.EAST);
		add(getLblTime(), BorderLayout.CENTER);
		add(getButtonsPanel(), BorderLayout.SOUTH);
		
		timer = new Timer();
		timer.schedule(
				new TimerTask() {
					@Override
					public void run() {
						onTimerTick();
					}
				},
				0,
				PERIOD
		);
	}

	protected void onTimerTick() {
		synchronized (this) {
			if (State.STARTED.equals(state)) {
				time -= PERIOD;
				if (time < 0) {
					time = 0;
					stop();
				}
				else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							showTime();
						}
					});
				}
			}
		}
	}

	protected void showTime() {
		if (time == 0) {
			getLblTime().setForeground(Color.RED);
		}
		else {
			getLblTime().setForeground(Color.BLACK);
		}
		
		getLblTime().setText("<html><b>" + DATE_FOMAT.format(time) + "</b></html>");
		getLblTime().repaint();
	}

	protected void pause() {
		synchronized (this) {
			getBtnStartPause().setText(">");
			state = State.PAUSED;
		}
	}

	protected void start() {
		synchronized (this) {
			getBtnStartPause().setText("||");
			state = State.STARTED;
		}
	}
	
	protected void stop() {
		synchronized (this) {
			getBtnStartPause().setText(">");
			time = 0;
			state = State.STOPED;
			showTime();
		}
	}
	
	protected void incTime() {
		synchronized (this) {
			time += TIME_INC_PERIOD;
			showTime();
		}
	}

	protected void decTime() {
		synchronized (this) {
			time -= TIME_INC_PERIOD;
			if (time < 0) {
				time = 0;
				stop();
			}
			else {
				showTime();
			}
		}
	}


	private JButton getBtnStartPause() {
		if (btnStartPause == null) {
			btnStartPause = new JButton(">");
			btnStartPause.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (State.PAUSED.equals(state) || State.STOPED.equals(state)) {
						start();
					}
					else if (State.STARTED.equals(state)) {
						pause();
					}
				}
			});
			Dimension dim = btnStartPause.getPreferredSize();
			btnStartPause.setPreferredSize(new Dimension(dim.width + 30, dim.height));
		}
		return btnStartPause;
	}

	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton("\u9644");
			
			btnStop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stop();
				}
			});
		}
		return btnStop;
	}

	private JButton getBtnInc() {
		if (btnInc == null) {
			btnInc = new JButton(">>");
			
			btnInc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					incTime();
				}
			});
		}
		return btnInc;
	}

	private JButton getBtnDec() {
		if (btnDec == null) {
			btnDec = new JButton("<<");
			
			btnDec.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					decTime();
				}
			});
		}
		return btnDec;
	}

	private JLabel getLblTime() {
		if (lblTime == null) {
			lblTime = new JLabel();
			
			Font font = lblTime.getFont();
			lblTime.setFont(new Font(font.getName(), font.getStyle(), 100));
			lblTime.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblTime;
	}

	private KASPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new KASPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
			
			buttonsPanel.add(getBtnStartPause());
			buttonsPanel.add(Box.createHorizontalStrut(10));
			buttonsPanel.add(getBtnDec());
			buttonsPanel.add(getBtnInc());
			buttonsPanel.add(Box.createHorizontalStrut(10));
			buttonsPanel.add(getBtn1Minute());
			buttonsPanel.add(getBtn2Minutes());
			buttonsPanel.add(getBtn3Minutes());
			buttonsPanel.add(Box.createHorizontalStrut(10));
			buttonsPanel.add(getBtnStop());
		}
		return buttonsPanel;
	}
	
	public void dispose() {
		synchronized (this) {
			if (timer != null) {
				timer.cancel();
			}
		}
	}
	
	
	private JLabel getLblTatami() {
		if (lblTatami == null) {
			String text = uic.TATAMI() + " - " + tatami;
			lblTatami = new JLabel(text);
			
			Font font = lblTatami.getFont();
			lblTatami.setFont(new Font(font.getName(), font.getStyle(), LBL_SIZE));
			lblTatami.setHorizontalAlignment(SwingUtilities.CENTER);

		}
		return lblTatami;
	}

	public void setTime(int time) {
		synchronized (this) {
			this.time = time;
			showTime();		
		}
	}

	private JLabel getLblRoundNumber() {
		if (lblRoundNumber == null) {
			lblRoundNumber = new JLabel(uic.ROUND() + " - " + roundNumberForUI);
			
			Font font = lblRoundNumber.getFont();
			lblRoundNumber.setFont(new Font(font.getName(), font.getStyle(), LBL_SIZE));
			lblRoundNumber.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblRoundNumber;
	}

	private JButton getBtn1Minute() {
		if (btn1Minute == null) {
			btn1Minute = new JButton(1 + " " + uic.MIN());
			
			btn1Minute.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setTime(60 * 1000);
				}
			});
		}
		return btn1Minute;
	}

	private JButton getBtn2Minutes() {
		if (btn2Minutes == null) {
			btn2Minutes = new JButton(2 + " " + uic.MIN());
			
			btn2Minutes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setTime(2 * 60 * 1000);
				}
			});
		}
		return btn2Minutes;
	}

	private JButton getBtn3Minutes() {
		if (btn3Minutes == null) {
			btn3Minutes = new JButton(3 + " " + uic.MIN());
			
			btn3Minutes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setTime(3 * 60 * 1000);
				}
			});
		}
		return btn3Minutes;
	}

	public KASPanel getInfoPanel() {
		if (infoPanel == null) {
			int rows = 1;
			if (fightNumber != null) {
				rows++;
			}
			if (fightInfo != null) {
				rows++;
			}
			infoPanel = new KASPanel(new GridLayout(rows, 1));
			infoPanel.add(getLblTatami());
			if (fightNumber != null) {
				infoPanel.add(getLblFightNumber());
			}
			if (fightInfo != null) {
				infoPanel.add(getLblFightInfo());
			}
		}
		return infoPanel;
	}

	public JLabel getLblFightNumber() {
		if (lblFightNumber == null) {  
			lblFightNumber = new JLabel(uic.NR() + " " + fightNumber);
			Font font = lblFightNumber.getFont();
			lblFightNumber.setFont(new Font(font.getName(), font.getStyle(), LBL_SIZE));
			lblFightNumber.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblFightNumber;
	}

	public JLabel getLblFightInfo() {
		if (lblFightInfo == null) {  
			lblFightInfo = new JLabel(fightInfo);
			Font font = lblFightInfo.getFont();
			lblFightInfo.setFont(new Font(font.getName(), font.getStyle(), LBL_SIZE));
			lblFightInfo.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblFightInfo;
	}

}
