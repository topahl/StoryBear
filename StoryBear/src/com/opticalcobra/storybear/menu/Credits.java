package com.opticalcobra.storybear.menu;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;

import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

import javax.swing.ImageIcon;

public class Credits extends JLayeredPane {
	private JLabel label_1;
	private JTextArea beschreibung_1;
	private JLabel label_2;
	private JTextArea beschreibung_2;
	public Credits() {
		JLabel label = new JLabel();
		label.setText("Team");
        label.setFont(Menu.fontHeadline[0]);
        label.setForeground(Color.black);
        label.setBounds((int)(744/Ressources.SCALE), (int)(51/Ressources.SCALE), (int)(520/Ressources.SCALE), (int)(80/Ressources.SCALE));
        add(label, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        JTextArea beschreibung = new JTextArea();
        beschreibung.setLocation((int)(744/Ressources.SCALE), (int)(142/Ressources.SCALE));
        beschreibung.setSize((int)(346/Ressources.SCALE), (int)(552/Ressources.SCALE));
        beschreibung.setLineWrap(true);
        beschreibung.setText("Teamleitung\n\nProjektmanagement\n\nSimulation &\nAlgorithmus\n\nFrontend & GUI\n\n\nDesign");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setCursor(Ressources.CURSORNORMAL);
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontHeadline[2]);
        beschreibung.setForeground(Color.black);
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);

        beschreibung_2 = new JTextArea();
        beschreibung_2.setLocation((int)(1100/Ressources.SCALE), (int)(142/Ressources.SCALE));
        beschreibung_2.setSize((int)(332/Ressources.SCALE), (int)(594/Ressources.SCALE));
        beschreibung_2.setLineWrap(true);
        beschreibung_2.setText("Tobias Pahlings\n\nSven Wessiepe\n\nMiriam Marx\nMartika Schwan\n\nTobias Pahlings\nStephan Giesau\n\nStephan Giesau\n");
        beschreibung_2.setWrapStyleWord(true);
        beschreibung_2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung_2.setFocusable(false);
        beschreibung_2.setOpaque(false);
        beschreibung_2.setFont(Menu.fontHeadline[2]);
        beschreibung_2.setForeground(Color.black);
        add(beschreibung_2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        label_2 = new JLabel();
        label_2.setLocation((int)(10/Ressources.SCALE), (int)(51/Ressources.SCALE));
        label_2.setSize((int)(632/Ressources.SCALE), (int)(80/Ressources.SCALE));
		label_2.setText("Projekt");
        label_2.setFont(Menu.fontHeadline[0]);
        label_2.setForeground(Color.black);
        add(label_2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        beschreibung_1 = new JTextArea();
        beschreibung_1.setLocation((int)(10/Ressources.SCALE), (int)(141/Ressources.SCALE));
        beschreibung_1.setSize((int)(632/Ressources.SCALE), (int)(143/Ressources.SCALE));
        beschreibung_1.setLineWrap(true);
        beschreibung_1.setText("StoryBear ist entstanden als Projekt im Modul Software Engineering bei Eckhard Kruse an der DHBW Mannheim.\n(TINF12AI-BC)");
        beschreibung_1.setWrapStyleWord(true);
        beschreibung_1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung_1.setFocusable(false);
        beschreibung_1.setOpaque(false);
        beschreibung_1.setFont(Menu.fontText[0]);
        beschreibung_1.setForeground(Color.black);
        add(beschreibung_1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        label_1 = new JLabel();
        label_1.setLocation((int)(10/Ressources.SCALE), (int)(299/Ressources.SCALE));
        label_1.setSize((int)(632/Ressources.SCALE), (int)(80/Ressources.SCALE));
		label_1.setText("Disclaimer");
        label_1.setFont(Menu.fontHeadline[0]);
        label_1.setForeground(Color.black);
        add(label_1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        beschreibung = new JTextArea();
        beschreibung.setLineWrap(true);
        beschreibung.setText("Die Handlung und alle handelnden Personen sowie deren Namen sind frei erfunden. Jegliche Ähnlichkeiten mit lebenden oder realen Personen wären rein zufällig.");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontText[0]);
        beschreibung.setForeground(Color.black);
        beschreibung.setBounds((int)(10/Ressources.SCALE), (int)(390/Ressources.SCALE), (int)(632/Ressources.SCALE), (int)(143/Ressources.SCALE));
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
		
//		JLabel lblNewLabel = new JLabel("New label");
//		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Nicolas\\Dropbox\\Software Engineering Teil 2\\Grafiken\\Fertig\\menu_storybook_opened.png"));
//		lblNewLabel.setBounds(-376, -57, 1920, 1080);
//		add(lblNewLabel);
		setVisible(true);
	}
}
