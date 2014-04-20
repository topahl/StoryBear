package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import com.opticalcobra.storybear.debug.Debugger;
import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.Button;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.TestMemory;

public class Window extends JFrame {
	
	private JLayeredPane baseLayer; // base Layer on witch all other displaying is done
	private Timer timer;
	private GameLayer vg2;
	private GameLayer mg;
	private GameLayer bg;
	private GameLayer clouds;
	
	private int stepCounter = 1;
	private int stepCounterLayer = 0;
	private Control controle;
	
	private Button buttonMenu;
	private Button buttonBreak;
	private Button buttonExit;
	private JLabel labelScore = new JLabel();
	
//	private boolean inAJump = false; //shows that jump is executed currently
//	private boolean inADoubleJump = false;
	

	DummyRenderer renderer = new DummyRenderer();
	DummyRendererMG rendererMG = new DummyRendererMG();
	BackgroundRenderer rendererBG = new BackgroundRenderer();
	CloudRenderer rendererCloud = new CloudRenderer();
	
	public Window(){
		
		this.timer = new Timer();	
		
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		this.setResizable(false);
		this.setUndecorated(true);
		
		initComponents();
		
		
		// initialize Controle 
		this.controle = new Control();
		this.addKeyListener(this.controle);
		this.setFocusable(true);
		timer.scheduleAtFixedRate(new OSTimer(this),Ressources.GAMESPEED, Ressources.GAMESPEED);
		this.setVisible(true);
	}
	
	
	/**
	 * @author Tobias
	 */
	private void initComponents() {
		baseLayer.setBackground(Ressources.SKYCOLOR);
		getContentPane().setBackground(Color.BLACK);
		//Letzte Einstellungen zum Fenster
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.baseLayer, javax.swing.GroupLayout.DEFAULT_SIZE,
				Ressources.SCREEN.width, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.baseLayer, javax.swing.GroupLayout.DEFAULT_SIZE,
				Ressources.SCREEN.height, Short.MAX_VALUE));
		baseLayer.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height); // TODO : Fenster mittig anzeigen
		add(baseLayer);
		this.pack();
		
		if(Ressources.DEBUG){
			Debugger.main(null);
		}
		
		
		//initialize Buttons
		this.buttonMenu = new Button(Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_MENU_BLACK),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_MENU_WHITE),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_MENU_GREY),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_MENU_BLACK),
				"buttonMenu",
				Ressources.BUTTONDISTANCE, Ressources.BUTTONDISTANCE
				);
		this.baseLayer.add(this.buttonMenu);
		
		this.buttonBreak = new Button(Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_BREAK_BLACK),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_BREAK_WHITE),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_BREAK_GREY),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_BREAK_BLACK),
				"buttonBreak",
				2*Ressources.BUTTONDISTANCE+Ressources.BUTTONSIZE, Ressources.BUTTONDISTANCE
				);	
		this.baseLayer.add(this.buttonBreak);
		
		this.buttonExit = new Button(Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_EXIT_WHITE),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_EXIT_GREY),
				Imagelib.getInstance().MenuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				"buttonExit",
				3*Ressources.BUTTONDISTANCE+2*Ressources.BUTTONSIZE, Ressources.BUTTONDISTANCE
				);
		this.baseLayer.add(this.buttonExit);
		
		//initialize Label for Highscore
		this.labelScore.setBounds(Ressources.WINDOW.width - Ressources.SCOREDISTANCERIGHT - 200,
				Ressources.SCOREDISTANCEUP, 200, Ressources.SCORETEXTSIZE);
		this.labelScore.setFont(new Font("Fontin Sans RG",Font.PLAIN,Ressources.SCORETEXTSIZE));
		this.labelScore.setText("123456");
		this.labelScore.setHorizontalAlignment(SwingConstants.RIGHT);
		this.labelScore.setVisible(true);
		this.baseLayer.add(this.labelScore);
		
		
		// initialize Hero
		//this.hero = new Hero('b');	//TODO: einlesen, welcher hero-Typ vom User ausgewählt wurde
		this.baseLayer.add(Hero.getInstance());
		
		
		
		
		//TODO Remove Dummy code
		//Dummy Code 
		vg2=new GameLayer(renderer);
		vg2.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		vg2.setLocation(0, 0);
		baseLayer.add(vg2);
		
		mg=new GameLayer(rendererMG);
		mg.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		mg.setLocation(0, 0); //TODO: -100 entfernen
		baseLayer.add(mg);
		
		bg=new GameLayer(rendererBG);
		bg.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		bg.setLocation(0, 0); //TODO: -100 entfernen
		baseLayer.add(bg);
		
		clouds=new GameLayer(rendererCloud);
		clouds.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		clouds.setLocation(0, 0); //TODO: -100 entfernen
		baseLayer.add(clouds);
		//Dummy Code end
				
		Hero.getInstance().setRingbuffer(renderer.getRingbuffer());
		Hero.getInstance().initHero('b');
	}
	
	
	/**
	 * the different layers are moving in different speeds
	 * 
	 * @author Martika
	 */
	private void layerStep(){
		vg2.step();
		if(stepCounterLayer % 2 == 0)
			mg.step();
		if(stepCounterLayer % 4 == 0){
			bg.step();
		}
		if(stepCounterLayer % 6 == 0){
			clouds.step();
		}
		
		Hero.getInstance().runFreazing(stepCounterLayer);
		
		stepCounterLayer++;
	}
	
	
	/**
	 * 
	 */
	public synchronized void step(){
		
		if(stepCounter % 6 == 0){
			clouds.step();
		}
			
		//Navigation of the hero via the right, left, up and down keys
		if(stepCounter % 4 == 0){
			Hero.getInstance().heroStep(stepCounterLayer);
		}
		
		//Hero klebt an der unsichtbaren Wand an Kachel 5
		if(Hero.getInstance().getRunDirection() == 'r' && Hero.getInstance().getX() + (Ressources.CHARACTERWIDTH / 2) >= Ressources.RASTERSIZE*5){
			if (Hero.getInstance().isHeroAllowedToWalk()){
				layerStep();
			}
		}
		
		stepCounter++;
		repaint();
	}
}
