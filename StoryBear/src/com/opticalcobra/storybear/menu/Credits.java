package com.opticalcobra.storybear.menu;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.opticalcobra.storybear.res.Ressources;

public class Credits extends MenuInnerPanel {
	public static int startY = 120;
	public static int distanceY = 75;
	
	public Credits() {
		addMenuHeadline("Credits");
		addMenuHeadlineUnderlining();
		
		intitializeCredits();
	}

	private void intitializeCredits() {
		//left page
		JLabel project = generateStandardLabel();
        project.setBounds(Menu.leftPageX, (int)(startY/Ressources.SCALE), Menu.pageWidth, (int)(80/Ressources.SCALE));
		project.setText("Projekt");
        add(project);
        
        JTextArea textProject = generateStandardTextArea();
        textProject.setBounds(Menu.leftPageX, (int)((startY+distanceY)/Ressources.SCALE), Menu.pageWidth, (int)(200/Ressources.SCALE));
        textProject.setText("StoryBear ist entstanden als Projekt im Modul Software Engineering bei Eckhard Kruse an der DHBW Mannheim.\n(TINF12AI-BC)");
        add(textProject);
        
        // right page
        JLabel disclaimer = generateStandardLabel();
        disclaimer.setBounds(Menu.leftPageX, (int)((startY+250)/Ressources.SCALE), Menu.pageWidth, (int)(80/Ressources.SCALE));
		disclaimer.setText("Disclaimer");
        add(disclaimer);
        
        JTextArea textDisclaimer = generateStandardTextArea();
        textDisclaimer.setBounds(Menu.leftPageX, (int)((startY+250+distanceY)/Ressources.SCALE), Menu.pageWidth, (int)(300/Ressources.SCALE));
        textDisclaimer.setText("Die Handlung und alle handelnden Personen sowie deren Namen sind frei erfunden. Jegliche Ähnlichkeiten mit lebenden oder realen Personen wären rein zufällig.\nDas Entwicklerteam haftet nicht für eventuelle Schäden an Hard- und Software von mit StoryBear bespielten Computern.");
        add(textDisclaimer);
		
		JLabel team = generateStandardLabel();
		team.setBounds(Menu.rightPageX, (int)(startY/Ressources.SCALE), Menu.pageWidth, (int)(80/Ressources.SCALE));
		team.setText("Team");
        add(team);
		
        JTextArea textTeam = generateStandardTextArea();
        textTeam.setBounds(Menu.rightPageX, (int)((startY+distanceY)/Ressources.SCALE), (int)(350/Ressources.SCALE), (int)(600/Ressources.SCALE));
        textTeam.setText("Teamleitung\n\nProjektmanagement\n\nSpiellogik &\nAlgorithmus\n\nFrontend & GUI\n\n\nDesign & Grafik\n\n\n\nMusik\n");
        add(textTeam);

        JTextArea textMembers = generateStandardTextArea();
        textMembers.setBounds(Menu.rightPageX+(int)(320/Ressources.SCALE), (int)((startY+distanceY)/Ressources.SCALE), (int)(330/Ressources.SCALE), (int)(600/Ressources.SCALE));
        textMembers.setText("Tobias Pahlings\n\nSven Wessiepe\n\nMiriam Marx\nMartika Schwan\n\nNicolas Marin\nStephan Giesau\n\nStephan Giesau\nNicolas Marin\nSven Wessiepe\n\nStephan Giesau\nSven Wessiepe");
        add(textMembers);
	}
}
