/**
 * The file ColumnLayout.java was created on 2008.15.8 at 16:39:14
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.ashihara.ui.core.panel.KASPanel;

public class ColumnLayout implements LayoutManager2 {
	protected int margin_height;

	protected int margin_width;

	protected int spacing;

	protected int alignment;

	// Constants for the alignment argument to the constructor.
	public static final int LEFT = 0;

	public static final int CENTER = 1;

	public static final int RIGHT = 2;

	/** The constructor. See comment above for meanings of these arguments */
	public ColumnLayout(int margin_height, int margin_width, int spacing,
			int alignment) {
		this.margin_height = margin_height;
		this.margin_width = margin_width;
		this.spacing = spacing;
		this.alignment = alignment;
	}

	/**
	 * A default constructor that creates a ColumnLayout using 5-pixel margin
	 * width and height, 5-pixel spacing, and left alignment
	 */
	public ColumnLayout() {
		this(5, 5, 5, LEFT);
	}

	/**
	 * The method that actually performs the layout. Called by the Container
	 */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension parent_size = parent.getSize();
		Component kid;
		int nkids = parent.getComponentCount();
		int x0 = insets.left + margin_width; // The base X position
		int x;
		int y = insets.top + margin_height; // Start at the top of the column

		for (int i = 0; i < nkids; i++) { // Loop through the kids
			kid = parent.getComponent(i); // Get the kid
			if (!kid.isVisible())
				continue; // Skip hidden ones
			Dimension pref = kid.getPreferredSize(); // How big is it?
			switch (alignment) { // Compute X coordinate
			default:
			case LEFT:
				x = x0;
				break;
			case CENTER:
				x = (parent_size.width - pref.width) / 2;
				break;
			case RIGHT:
				x = parent_size.width - insets.right - margin_width
						- pref.width;
				break;
			}
			// Set the size and position of this kid
			kid.setBounds(x, y, pref.width, pref.height);
			y += pref.height + spacing; // Get Y position of the next one
		}
	}

	/** The Container calls this to find out how big the layout should to be */
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, 1);
	}

	/** The Container calls this to find out how big the layout must be */
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, 2);
	}

	/** The Container calls this to find out how big the layout can be */
	public Dimension maximumLayoutSize(Container parent) {
		return layoutSize(parent, 3);
	}

	// Compute min, max, or preferred size of all the visible children
	protected Dimension layoutSize(Container parent, int sizetype) {
		int nkids = parent.getComponentCount();
		Dimension size = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		int num_visible_kids = 0;

		// Compute maximum width and total height of all visible kids
		for (int i = 0; i < nkids; i++) {
			Component kid = parent.getComponent(i);
			Dimension d;
			if (!kid.isVisible())
				continue;
			num_visible_kids++;
			if (sizetype == 1)
				d = kid.getPreferredSize();
			else if (sizetype == 2)
				d = kid.getMinimumSize();
			else
				d = kid.getMaximumSize();
			if (d.width > size.width)
				size.width = d.width;
			size.height += d.height;
		}

		// Now add in margins and stuff
		size.width += insets.left + insets.right + 2 * margin_width;
		size.height += insets.top + insets.bottom + 2 * margin_height;
		if (num_visible_kids > 1)
			size.height += (num_visible_kids - 1) * spacing;
		return size;
	}

	// Other LayoutManager(2) methods that are unused by this class
	public void addLayoutComponent(String constraint, Component comp) {
	}

	public void addLayoutComponent(Component comp, Object constraint) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public void invalidateLayout(Container parent) {
	}

	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}
}

class ColumnLayoutPane extends KASPanel {
	private static final long serialVersionUID = 1L;

	public ColumnLayoutPane() {
		// Get rid of the default layout manager.
		// We'll arrange the components ourselves.
		this.setLayout(new ColumnLayout(5, 5, 10, ColumnLayout.RIGHT));

		// Create some buttons and set their sizes and positions explicitly
		for (int i = 0; i < 6; i++) {
			int pointsize = 8 + i * 2;
			JButton b = new JButton("Point size " + pointsize);
			b.setFont(new Font("helvetica", Font.BOLD, pointsize));
			this.add(b);
		}
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.setContentPane(new ColumnLayoutPane());
		f.pack();
		f.setVisible(true);
	}
}
