/**
 * The file KasScrollPane.java was created on 2009.3.11 at 22:15:00
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollPane;

public class KasScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	public KasScrollPane(Component c, int verticalScrollbar, int horizontalScrollbar) {
		super(c, verticalScrollbar, horizontalScrollbar);
		
		init();
	}

	private void init() {
		getVerticalScrollBar().addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e) {
//				if (!getVerticalScrollBar().isVisible()) {
//					System.out.println("KasScrollPane.this.getParent().dispatchEvent(e);");
//					getParent().dispatchEvent(e);
//				}
			}
		});
	}
}
