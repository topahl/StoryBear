package com.opticalcobra.storybear.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
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
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.Button;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.MusicButton;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.StoryBearRandom;

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
	
	private MusicButton buttonMenu;
	private Button buttonBreak;
	private Button buttonExit;
	private JLabel labelScore = new JLabel();
	
	public StoryInfo level = null;
	public char heroType;
	
	private Database db = new Database();
	

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
		
		//init Randoom
//		StoryBearRandom.getInstance().setSeed(level.getHash());
		StoryBearRandom.getInstance().setSeed(StoryBearRandom.hash(level.getStory().getText()));
		
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
		
		rtw= RenderThreadWrapper.getInstance();
		//create remderer
		renderer = new DummyRenderer(level);
		rendererfg2 = new RendererFG2(renderer.getTileQue(), level); //renderer.getRingbuffer(), 
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
		BufferedImage[] normal = {Imagelib.getInstance().loadDesignImage("game_sound_normal_on"),
				Imagelib.getInstance().loadDesignImage("game_sound_hover_on"),
				Imagelib.getInstance().loadDesignImage("game_sound_click_on")};
		BufferedImage[] mute = {Imagelib.getInstance().loadDesignImage("game_sound_normal_mute"),
				Imagelib.getInstance().loadDesignImage("game_sound_hover_mute"),
				Imagelib.getInstance().loadDesignImage("game_sound_click_mute")};
		this.buttonMenu = new MusicButton(normal, mute, (int)(Ressources.BUTTONDISTANCE*Ressources.SCALE), (int)(Ressources.BUTTONDISTANCE*Ressources.SCALE));
		this.baseLayer.add(this.buttonMenu);
		
		this.buttonExit = new Button("close",
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_WHITE),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_GREY),
				Imagelib.getInstance().menuImage(Imagelib.GAME_BUTTON_EXIT_BLACK),
				(int)((2*Ressources.BUTTONDISTANCE+Ressources.BUTTONSIZE)*Ressources.SCALE), (int)(Ressources.BUTTONDISTANCE*Ressources.SCALE)
				);
		this.buttonExit.setFocusable(false);
		this.buttonExit.addActionListener(controle);
		this.baseLayer.add(this.buttonExit);
		
		//initialize Label for Highscore
		this.labelScore.setBounds(Ressources.WINDOW.width - Ressources.SCOREDISTANCERIGHT - 200,
				Ressources.SCOREDISTANCEUP, 200, Ressources.SCORETEXTSIZE);
		this.labelScore.setFont(FontCache.getInstance().getFont("Fontin_R",Ressources.SCORETEXTSIZE));
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
		
		ia=new InteractionLayer(rendererInteraction);
		ia.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		ia.setLocation(0, 0);
		
		//ia can just be rendered after fg1 was renderd. therefore we need a initializemethod
		ia.initialize(renderer.getTileQue());
		
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
	
	
	/*
	 * @author Miriam
	 * is called if game is won/lost or if the user exits the game
	 */
	public void saveHighscore() throws SQLException{
		this.db.addHighscore(User.getCurrentUser().getId(), this.level.getId(), Hero.getInstance().getHighscore());
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
		Hero.getInstance().setInRunFreazing(true);
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
		
		Hero.getInstance();
		//Hero klebt an der unsichtbaren Wand an Kachel 5
		if(Hero.getInstance().getRunDirection() == 'r' && Hero.getInstance().getX() + (Hero.getInstance().getWidth()/2) >= Ressources.RASTERSIZE*5){
			if (Hero.getInstance().getX()+ (Hero.getInstance().getWidth()/2) == Ressources.RASTERSIZE*5 && !Hero.getInstance().isInRunFreazing()
					&& stepCounterLayer%Ressources.RASTERSIZE < Ressources.RUNCONSTANT){
				Hero.getInstance().setQueCounter(Hero.getInstance().getQueCounter()+1);
			}
			layerStep();
		}
		
		stepCounter++;
		repaint();
	}
}
