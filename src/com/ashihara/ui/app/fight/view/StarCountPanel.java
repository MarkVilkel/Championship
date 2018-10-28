/**
 * The file StarCountPanel.java was created on 2011.6.12 at 22:49:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.app.fight.view;

import javax.swing.Box;

import com.ashihara.ui.app.fight.view.listener.CountProvider;

public class StarCountPanel extends CountPanel {

	private static final long serialVersionUID = 1L;

	private final int verticalStrutSize;

	public StarCountPanel(
			int verticalStrutSize,
			int textSize,
			long minCount,
			long maxCount,
			Long maxSumCount,
			CountProvider oponentCountProvider
	) {
		super(textSize, minCount, maxCount, maxSumCount, oponentCountProvider);
		
		this.verticalStrutSize = verticalStrutSize;
		add(Box.createVerticalStrut(verticalStrutSize), 0);
	}

	
	public void init() {
		super.init();
	}
	
	public void showCount() {
		super.showCount();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			builder.append("X ");
		}
		getLblCount().setText(builder.toString());
		
	}

}
