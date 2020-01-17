/**
 * The file CountPanel.java was created on 2011.12.10 at 22:29:12
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.ashihara.ui.core.panel.KASPanel;

public class CountPanel extends KASPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton btnPlus;
	private JButton btnMinus;
	
	private JLabel lblCount;
	
	protected long count = 0;
	
	private final long minCount;
	private long maxCount;
	protected final int textSize;
	
	private final List<CountListener> countListeners = new ArrayList<CountListener>();
	
	
	public CountPanel(
			int textSize,
			long minCount,
			long maxCount
	) {
		this.textSize = textSize;
		this.minCount = minCount;
		this.maxCount = maxCount;
		
		init();
	}

	protected void init() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		
		add(getBtnMinus());
		add(Box.createHorizontalStrut(3));
		add(getBtnPlus());
		
		add(Box.createHorizontalStrut(10));
		add(getLblCount());
		
		setOpaque(false);
		
		showCount();
	}

	protected JButton getBtnPlus() {
		if (btnPlus == null) {
			btnPlus = new JButton("+");
//			btnPlus = UIFactory.createPlusButton();
			
//			btnPlus.setBorderPainted(false);
			
			btnPlus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					increaseCount(1);
				}
			});
			
			btnPlus.setOpaque(false);
		}
		return btnPlus;
	}

	protected JButton getBtnMinus() {
		if (btnMinus == null) {
			btnMinus = new JButton("\u2013");
//			btnMinus = UIFactory.createMinusButton();
			
//			btnMinus.setBorderPainted(false);
			
			btnMinus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					decreaseCount(1);
				}
			});
			
			btnMinus.setOpaque(false);
		}
		return btnMinus;
	}

	
	public void checkAndChangeCount(boolean increase, Long c) {
		if (c == null) {
			return;
		}
		if (increase) {
			increaseCount(c);
		} else {
			decreaseCount(c);
		}
	}
	
	public void increaseCount(long c) {
		count += c;
		
		if (count > maxCount) {
			count = maxCount;
		} else {
			fireCountIncreased();
		}

		showCount();
	}

	public void setCount(long c) {
		count = c;
		
		if (count > maxCount) {
			count = maxCount;
		}
	}
	
	public void decreaseCount(long c) {
		count -= c;
		
		if (count < minCount) {
			count = minCount;
		} else {
			fireCountDecreased();
		}
		
		showCount();
	}
	
	public void showCount() {
		getLblCount().setText(String.valueOf(count));
		setHighlighted(count >= maxCount);
	}

	protected JLabel getLblCount() {
		if (lblCount == null) {
			lblCount = new JLabel();
			
			Font font = lblCount.getFont();
			lblCount.setFont(new Font(font.getName(), font.getStyle(), textSize));
			lblCount.setHorizontalAlignment(SwingUtilities.CENTER);
		}
		return lblCount;
	}

	public long getCount() {
		return count;
	}
	
	public void addCountListener(CountListener l) {
		countListeners.add(l);
	}
	
	public void removeCountListener(CountListener l) {
		countListeners.remove(l);
	}
	
	public List<CountListener> getCountPanelListeners() {
		List<CountListener> result = new ArrayList<CountListener>();
		result.addAll(countListeners);
		return result;
	}
	
	private void fireCountIncreased() {
		for (CountListener l : countListeners) {
			l.countIncreased(this);
		}
	}
	
	private void fireCountDecreased() {
		for (CountListener l : countListeners) {
			l.countDecreased(this);
		}
	}
	
	public interface CountListener {
		void countIncreased(CountPanel countPanel);
		void countDecreased(CountPanel countPanel);
	}

	public long getMinCount() {
		return minCount;
	}

	public long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(long maxCount) {
		this.maxCount = maxCount;
	}

	public void setHighlighted(boolean b) {
		Color color = b ? Color.GREEN : Color.BLACK;
		getLblCount().setForeground(color);
	}

}



