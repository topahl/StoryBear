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

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.debug.Debugger;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.Button;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class Window extends JFrame {
	
	private JLayeredPane baseLayer; // base Layer on witch all other displaying is done
	private Timer timer;
	private GameLayer fg1;
	private GameLayer fg2;
	private GameLayer mg;
	private GameLayer bg;
	private GameLayer clouds;
	private InteractionLayer ia;
	
	private int stepCounter = 1;
	private int stepCounterLayer = 0;
	private Control controle;
	
	private Button buttonMenu;
	private Button buttonBreak;
	private Button buttonExit;
	private JLabel labelScore = new JLabel();
	
	public StoryInfo level = null;
	public char heroType;
	
//	private boolean inAJump = false; //shows that jump is executed currently
//	private boolean inADoubleJump = false;

	DummyRenderer renderer;
	RendererFG2 rendererfg2;
	DummyRendererMG rendererMG;
	BackgroundRenderer rendererBG;
	CloudRenderer rendererCloud;
	InteractionRenderer rendererInteraction;
	RenderThreadWrapper rtw;
	
	public Window(){
		this(7, 'b'); //Change here default Level
	}
	
	public Window(int level_num, char heroType){
		this.level = new Database().getStoryInfoFromDatabase(level_num);
		this.level.setId(level_num);
		this.heroType = heroType;
		this.timer = new Timer();
		
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(Ressources.CURSORNORMAL);
		this.setResizable(false);
		this.setUndecorated(true);
		
		// initialize Controle 
		this.controle = new Control(this);
		this.addKeyListener(this.controle);
		
		initComponents();
		
		this.setFocusable(true);
		timer.scheduleAtFixedRate(new OSTimer(this),Ressources.GAMESPEED, Ressources.GAMESPEED);
		this.setVisible(true);
	}
	
	
	/**
	 * @author Tobias
	 */
	private void initComponents() {
		
		rtw= new RenderThreadWrapper();
		rtw.start();
		//create remderer
		renderer = new DummyRenderer(level);
		rendererfg2 = new RendererFG2(level); //renderer.getRingbuffer(), 
		rendererMG = new DummyRendererMG();
		rendererBG = new BackgroundRenderer();
		rendererCloud = new CloudRenderer();
		rendererInteraction = new InteractionRenderer(renderer.getTileQue(), level.getElements());
		
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
		this.buttonMenu = new Button("menu",
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_MENU_BLACK),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_MENU_WHITE),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_MENU_GREY),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_MENU_BLACK),
				Ressources.BUTTONDISTANCE, Ressources.BUTTONDISTANCE
				);
		this.buttonMenu.setFocusable(false);
		this.buttonMenu.addActionListener(controle);
		this.baseLayer.add(this.buttonMenu);
		
		this.buttonBreak = new Button("pause",
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_BREAK_BLACK),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_BREAK_WHITE),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_BREAK_GREY),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_BREAK_BLACK),
				2*Ressources.BUTTONDISTANCE+Ressources.BUTTONSIZE, Ressources.BUTTONDISTANCE
				);	
		this.buttonBreak.setFocusable(false);
		this.buttonBreak.addActionListener(controle);
		this.baseLayer.add(this.buttonBreak);
		
		this.buttonExit = new Button("close",
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_WHITE),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_GREY),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				3*Ressources.BUTTONDISTANCE+2*Ressources.BUTTONSIZE, Ressources.BUTTONDISTANCE
				);
		this.buttonExit.setFocusable(false);
		this.buttonExit.addActionListener(controle);
		this.baseLayer.add(this.buttonExit);
		
		//initialize Label for Highscore
		this.labelScore.setBounds(Ressources.WINDOW.width - Ressources.SCOREDISTANCERIGHT - 200,
				Ressources.SCOREDISTANCEUP, 200, Ressources.SCORETEXTSIZE);
		this.labelScore.setFont(new Font("Fontin Sans RG",Font.PLAIN,Ressources.SCORETEXTSIZE));
		this.labelScore.setText(((Integer)(Hero.getInstance().getHighscore())).toString());
		this.labelScore.setHorizontalAlignment(SwingConstants.RIGHT);
		this.labelScore.setVisible(true);
		this.baseLayer.add(this.labelScore);
		
		
		
		fg1=new GameLayer(renderer);
		fg1.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		fg1.setLocation(0, 0);
		
		fg2=new GameLayer(rendererfg2);
		fg2.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		fg2.setLocation(0, 0);
		
		ia=new InteractionLayer();
		ia.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		ia.setLocation(0, 0);
		
		//ia can just be rendered after fg1 was renderd. therefore we need a initializemethod
		ia.initialize(rendererInteraction, renderer.getTileQue());
		
		mg=new GameLayer(rendererMG);
		mg.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		mg.setLocation(0, 0); //TODO: -100 entfernen
		
		bg=new GameLayer(rendererBG);
		bg.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		bg.setLocation(0, 0); //TODO: -100 entfernen
		
		clouds=new GameLayer(rendererCloud);
		clouds.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		clouds.setLocation(0, 0); //TODO: -100 entfernen
		
				
		Hero.getInstance().setTileQue(renderer.getTileQue());
		Hero.getInstance().initHero(heroType);
		
		//Add in Order:
		baseLayer.add(fg2);
		// initialize Hero
		//this.hero = new Hero('b');	//TODO: einlesen, welcher hero-Typ vom User ausgewählt wurde
		baseLayer.add(Hero.getInstance());
		baseLayer.add(ia);
		baseLayer.add(fg1);
		baseLayer.add(mg);
		baseLayer.add(bg);
		baseLayer.add(clouds);
		
	}
	
	
	/**
	 * the different layers are moving in different speeds
	 * 
	 * @author Martika
	 */
	private void layerStep(){
		fg1.step();
		fg2.step();
		ia.step();
		if(stepCounterLayer % 2 == 0)
			mg.step();
		if(stepCounterLayer % 4 == 0){
			bg.step();
		}
		if(stepCounterLayer % 6 == 0){
			clouds.step();
		}
		
		Hero.getInstance().runFreazing(stepCounterLayer);
		this.labelScore.setText(((Integer)(Hero.getInstance().getHighscore())).toString());
		
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
			this.labelScore.setText(((Integer)(Hero.getInstance().getHighscore())).toString());
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
