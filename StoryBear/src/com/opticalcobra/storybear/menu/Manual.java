package com.opticalcobra.storybear.menu;

import javax.swing.JTextArea;
import com.opticalcobra.storybear.res.Ressources;

public class Manual extends MenuInnerPanel {
	
	public Manual() {
		addMenuHeadline("Anleitung");
		addMenuHeadlineUnderlining();
		
		intitializeManual();
	}
	
	private void intitializeManual() {
		JTextArea text = generateStandardTextArea();
        text.setBounds(Menu.leftPageX, (int)(120/Ressources.SCALE), Menu.pageWidth, (int)(800/Ressources.SCALE));
        text.setText("Steuerung:\r\n  [ ^ ]   Springen\r\n  [->]   nach Rechts laufen\r\n  [<-]   nach Links laufen\r\n\r\nHighscore:\r\n  sammel XY ein, um Punkte zu sammeln");
        add(text);
        
        JTextArea text2 = generateStandardTextArea();
        text2.setBounds(Menu.rightPageX, (int)(120/Ressources.SCALE), Menu.pageWidth, (int)(800/Ressources.SCALE));
        text2.setText("");
        add(text2);
	}
}
