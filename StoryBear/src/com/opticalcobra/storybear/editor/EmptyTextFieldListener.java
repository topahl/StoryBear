package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;
/**
 * 
 * @author Nicolas
 *
 */
public class EmptyTextFieldListener implements FocusListener {
	private JTextComponent textComp;
	String emptyText;
	Color emptyColor;
	Color standardColor;
	
	public EmptyTextFieldListener(String emptyText, Color emptyColor, Color standardColor) {
		this.emptyText = emptyText;
		this.emptyColor = emptyColor;
		this.standardColor = standardColor;
	}
	
	@Override
	public void focusLost(FocusEvent evt) {
		getComponent(evt);
		
		if(textComp.getForeground() == Color.BLACK && textComp.getText().equals("")) {
			textComp.setText(emptyText);
			textComp.setForeground(Color.GRAY);
		}
	}
	
	@Override
	public void focusGained(FocusEvent evt) {
		getComponent(evt);
		
		if(textComp.getForeground() == Color.GRAY && textComp.getText().equals(emptyText)) {
			textComp.setText("");
			textComp.setForeground(Color.BLACK);
		}
	}
	
	private void getComponent(FocusEvent evt) {
		 textComp = (textComp == null) ? (JTextComponent) evt.getSource() : textComp;
	}
	
	public EmptyTextFieldListener initializeCallerTextComponent(JTextComponent text) {
		text.setText(emptyText);
		text.setForeground(emptyColor);
		return this;
	}
}
