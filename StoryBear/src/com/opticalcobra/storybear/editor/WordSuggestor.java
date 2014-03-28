package com.opticalcobra.storybear.editor;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;


public class WordSuggestor extends JLayeredPane {
	private JLabel image;
	private JLabel text;
	private JLabel buttonLeft;
	private JLabel buttonRight;
	
	public WordSuggestor() {
		text = new JLabel();
		image = new JLabel();
		buttonLeft = new JLabel();
		buttonRight = new JLabel();
		
		text.setText("Haus");
		image.setText("Dummy");
		buttonLeft.setText("Links");
		buttonRight.setText("Rechts");
		
		text.setVisible(true);
		image.setVisible(true);
		buttonLeft.setVisible(true);
		buttonRight.setVisible(true);
		
		text.setForeground(Color.WHITE);
		text.setBounds(0, 0, 100, 15);
		
		add(image);
		add(text);
		add(buttonLeft);
		add(buttonRight);
	}
}
