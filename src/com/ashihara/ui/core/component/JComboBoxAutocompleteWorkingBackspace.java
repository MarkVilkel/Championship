/**
 * The file JComboBoxAutocompleteWorkingBackspace.java was created on 2007.23.8 at 23:42:09
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.component;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class JComboBoxAutocompleteWorkingBackspace extends PlainDocument {
	private static final long serialVersionUID = 1L;
	
	JComboBox comboBox;
//    ComboBoxModel model;
    JTextComponent editor;
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    boolean selecting=false;
    boolean hidePopupOnFocusLoss;
    boolean hitBackspace=false;
    boolean hitBackspaceOnSelection;
    
    public JComboBoxAutocompleteWorkingBackspace(final JComboBox comboBox) {
        this.comboBox = comboBox;
//        model = comboBox.getModel();
        editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.setDocument(this);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!selecting) highlightCompletedText(0);
            }
        });
        editor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
                hitBackspace=false;
                switch (e.getKeyCode()) {
                    // determine if the pressed key is backspace (needed by the remove method)
//                    case KeyEvent.VK_BACK_SPACE : hitBackspace=true;
//                    hitBackspaceOnSelection=editor.getSelectionStart()!=editor.getSelectionEnd();
//                    break;
                    // ignore delete key
//                    case KeyEvent.VK_DELETE : e.consume();
//                    comboBox.getToolkit().beep();
//                    break;
                }
            }
        });
        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
        // Highlight whole text when gaining focus
        editor.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                highlightCompletedText(0);
            }
            public void focusLost(FocusEvent e) {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss) comboBox.setPopupVisible(false);
            }
        });
        // Handle initially selected object
        Object selected = comboBox.getSelectedItem();
        if (selected!=null) setText(selected.toString());
        highlightCompletedText(0);
    }
    
    public void remove(int offs, int len) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        if (hitBackspace) {
            // user hit backspace => move the selection backwards
            // old item keeps being selected
            if (offs>0) {
                if (hitBackspaceOnSelection) offs--;
            } else {
                // User hit backspace with the cursor positioned on the start => beep
                comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            highlightCompletedText(offs);
        } else {
            super.remove(offs, len);
        }
    }
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        // insert the string into the document
        super.insertString(offs, str, a);
        // lookup and select a matching item
        Object item = lookupItem(getText(0, getLength()));
        
        boolean setSelectedItem = false;
        if (item != null) {
        	setSelectedItem = true;
        } else {
            // keep old item selected if there is no match
            item = comboBox.getSelectedItem();
            // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
            offs = offs-str.length();
            // provide feedback to the user that his input has been received but can not be accepted
            comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
        }
        
        final Object finaltem = item;
        final int finalOffs = offs;
        final String finalStr = str;
        final boolean finalSetSelectedItem = setSelectedItem;
        
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (finalSetSelectedItem) {
					setSelectedItem(finaltem);
				}
				setText(finaltem.toString());
				// select the completed part
				highlightCompletedText(finalOffs + finalStr.length());
			}
        });
    }
    
    private void setText(String text) {
        try {
            // remove all text and insert the completed string
            super.remove(0, getLength());
            super.insertString(0, text, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e.toString());
        }
    }
    
    private void highlightCompletedText(int start) {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }
    
    private void setSelectedItem(final Object item) {
    	selecting = true;
    	comboBox.setSelectedItem(item);
    	selecting = false;
    }
    
    private Object lookupItem(String pattern) {
		// iterate over all items
    	Object result = pattern;
    	int length = 1000;
		for (int i = 0, n = comboBox.getItemCount(); i < n; i++) {
			Object currentItem = comboBox.getItemAt(i);
			// current item starts with the pattern?
			if (currentItem!=null && startsWithIgnoreCase(currentItem.toString(), pattern) && 
					length>currentItem.toString().length()){
				result = currentItem;
				length = currentItem.toString().length();
			}
		}
		// }
		comboBox.setPopupVisible(false);
		return result;
	}
    
    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2) {
    	if (str1 == null) return false;
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
    
    private static void createAndShowGUI() {
        // the combo box (add/modify items if you like to)
        JComboBox comboBox = new JComboBox(new Object[] {"Ester", "Jordi", "Jordina", "Jorge", "Sergi"});
        // has to be editable
        comboBox.setEditable(true);
        // change the editor's document
        new JComboBoxAutocompleteWorkingBackspace(comboBox);
        
        // create and show a window containing the combo box
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(comboBox);
        frame.pack(); frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}