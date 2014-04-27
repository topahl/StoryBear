package com.opticalcobra.storybear.menu;

import javax.swing.JDialog;

import com.opticalcobra.storybear.res.Ressources;

import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.SwingConstants;

public class LoadingPanel extends JDialog {
	public LoadingPanel() {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0));
		setLayout(null);
		setBounds(100, 100, Ressources.WINDOW.width-200, Ressources.WINDOW.height-200);
		setModal(true);
		
		JLabel textLade = new JLabel("Lade...");
		textLade.setHorizontalAlignment(SwingConstants.CENTER);
		textLade.setFont(Menu.fontHeadline[0]);
		textLade.setForeground(Color.white);
		textLade.setBounds((Ressources.WINDOW.width-200)/2-200, (Ressources.WINDOW.height-200)/2-200, 400, 400);
		getContentPane().add(textLade);
		setVisible(true);
	}
}
