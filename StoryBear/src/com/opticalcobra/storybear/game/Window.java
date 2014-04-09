package com.opticalcobra.storybear.game;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.Ressources;

public class Window extends JFrame {
	
	private JLayeredPane baseLayer; // base Layer on witch all other displaying is done
	private Timer timer;
	private GameLayer vg2;
	private GameLayer mg;
	private GameLayer bg;
	
	
	private int stepcounter = 0;
	private Hero hero;
	private Control controle;
	
	private boolean inAJump = false; //shows that jump is executed currently
	private boolean inADoubleJump = false;
	

	DummyRenderer renderer = new DummyRenderer();
	DummyRendererMG rendererMG = new DummyRendererMG();
	BackgroundRenderer rendererBG = new BackgroundRenderer();
	
	public Window(){
		
		this.timer = new Timer(Ressources.GAMESPEED,new OSTimer(this));	
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		this.setResizable(false);
		this.setUndecorated(true);
		
		initComponents();
		
		// initialize Game Character
		this.hero = new Hero();
		this.baseLayer.add(this.hero);
		
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
		//Dummy Code end
		
		
		
		// initialize Controle 
		this.controle = new Control();
		this.addKeyListener(this.controle);
		
		this.setVisible(true);
		this.timer.start();
	}
	
	
	/**
	 * @author Tobias
	 */
	private void initComponents(){

		
		
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

		this.setBackground(Color.black);
		this.pack();
	}
	
	
	/**
	 * 
	 */
	public void step(){
		vg2.step();
		if(this.stepcounter % 2 == 0)
			mg.step();
		//Navigation of the hero via the right, left, up and down keys
		if(this.stepcounter % 4 == 0){
			bg.step();
			if((this.controle.getJumpDirection() == 'u') || (this.inAJump)){
				//this.inAJump = this.character.jump(this.controle.getRunDirection());
				this.inAJump = this.hero.letHeroJump(this.controle.getDoubleJump(),this.controle.getRunDirection());
				if(!this.hero.inADoubleJump()){
					this.controle.setDoubleJump(false);
				}
				if(!this.inAJump)
					this.controle.jumpStatus = 'n';
			}
			else if(this.controle.getRunDirection() != 'n')
				this.hero.run(this.controle.getRunDirection());
		}
		
		this.stepcounter++;
	}
}
