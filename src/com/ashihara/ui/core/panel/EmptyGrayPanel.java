/**
 * The file EmptyGrayPanel.java was created on 2009.3.3 at 11:05:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class EmptyGrayPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}