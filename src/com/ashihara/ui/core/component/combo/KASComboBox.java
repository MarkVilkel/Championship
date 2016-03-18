/**
 * The file KASComboBox.java was created on 2009.13.5 at 12:51:42
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component.combo;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import com.rtu.persistence.core.Do;

public class KASComboBox extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	public void addSelectionItemListener(final ItemListener listener) {
		addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					listener.itemStateChanged(e);
				} else if (ItemEvent.DESELECTED == e.getStateChange()) {
					listener.itemStateChanged(e);
				}
			}
		});
	}
	
	public void tryToSelectIfInEntrySet(Object itemToSelect) {
		for (int i = 0; i < getItemCount(); i++) {
			Object item = getItemAt(i);
			if (itemToSelect == null && item == null) {
				setSelectedItem(itemToSelect);
			}
			else if (itemToSelect != null && item != null) {
				if (itemToSelect.equals(item)) {
					setSelectedItem(itemToSelect);
				}
				else if (itemToSelect instanceof Do
						&& item instanceof Do
						&& itemToSelect.getClass() == item.getClass()
						&& ((Do) itemToSelect).getId() != null
						&& ((Do) itemToSelect).getId().equals(((Do) item).getId())) {
					setSelectedItem(itemToSelect);
				}
				else if (item instanceof ComboBoxItem && ((ComboBoxItem)item).getId().equals(itemToSelect)) {
					setSelectedItem(item);
				}
			}
		}
	}
	
	public List<Object> getAllItems() {
		List<Object> items = new ArrayList<Object>();
		for (int i = 0; i < getItemCount(); i++) {
			Object item = getItemAt(i);
			items.add(item);
		}
		return items;
	}
	
	public void setSelectedItemAsId(Object id) {
		for (Object o : getAllItems()) {
			if (o instanceof ComboBoxItem) {
				ComboBoxItem cbi = (ComboBoxItem) o;
				if (cbi.getId().equals(id)) {
					setSelectedItem(cbi);
					return;
				}
			}
		}
		setSelectedItem(id);
	}
}
