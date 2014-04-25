package com.opticalcobra.storybear.menu;

import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

public class Manual extends JLayeredPane {
	public Manual() {
		JTextField headline = new JTextField();
		headline.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(Menu.fontHeadline[0]);
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.setEditable(false);
		headline.setFocusable(false);
		headline.setText("Anleitung");
		add(headline, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		JTextArea text = new JTextArea();
        text.setLocation((int)(40/Ressources.SCALE), (int)(125/Ressources.SCALE));
        text.setSize((int)(600/Ressources.SCALE), (int)(800/Ressources.SCALE));
        text.setLineWrap(true);
        text.setText("Steuerung:\r\n  [ ^ ]   Sprignen\r\n  [->]   nach Rechts laufen\r\n  [<-]   nach Links laufen");
        text.setWrapStyleWord(true);
        text.setCursor(Ressources.CURSORNORMAL);
        text.setFocusable(false);
        text.setOpaque(false);
        text.setFont(Menu.fontHeadline[2]);
        text.setForeground(Color.black);
        add(text, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        text = new JTextArea();
        text.setLocation((int)(1100-350/Ressources.SCALE), (int)(125/Ressources.SCALE));
        text.setSize((int)(600/Ressources.SCALE), (int)(800/Ressources.SCALE));
        text.setLineWrap(true);
        text.setText("");
        text.setWrapStyleWord(true);
        text.setCursor(Ressources.CURSORNORMAL);
        text.setFocusable(false);
        text.setOpaque(false);
        text.setFont(Menu.fontHeadline[2]);
        text.setForeground(Color.black);
        add(text, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
	}
}
