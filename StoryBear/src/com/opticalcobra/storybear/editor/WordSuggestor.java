package com.opticalcobra.storybear.editor;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class WordSuggestor extends JPanel {
	private JLabel image;
	private JLabel text;
	
	public WordSuggestor() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO: load from database
				text.setText("X");
				image.setIcon(null);
			}
		});
		
		text = new JLabel();
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setBounds(48, 335, 250, 38);
		
		image = new JLabel();
		image.setBackground(Color.BLACK);
		image.setBounds(23, 25, 300, 300);
		
		// Dummy TODO: delete
		text.setText("Dummy");
		image.setText("Dummy");
		
		
		text.setForeground(Color.BLACK);
		setLayout(null);
		
		add(image);
		add(text);
	}
	
	
}
