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
	private int frameCounter[];
	private int stepcounter = 0;
	private GameCharacter character;
	private Controle controle;
	
	private boolean inAJump = false; //shows that jump is executed currently
	private boolean inADoubleJump = false;
	
	JLayeredPane pane1;
	JLayeredPane pane2;
	DummyRenderer renderer = new DummyRenderer();
	
	public Window(){
		
		this.timer = new Timer(Ressources.GAMESPEED,new OSTimer(this));
		frameCounter = new int[]{0,Ressources.WINDOW.width};
		
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		this.setResizable(false);
		this.setUndecorated(true);
		
		initComponents();
		
		//TODO Remove Dummy code
		//Dummy Code 

		pane1.setSize(Ressources.WINDOW.width, Ressources.WINDOW.width);
		renderer.getNextWindow(pane1);
		pane1.setLocation(0, 0);
		pane2.setSize(Ressources.WINDOW.width, Ressources.WINDOW.width);
		renderer.getNextWindow(pane2);
		pane2.setLocation(Ressources.WINDOW.width, 0);

		//Dummy Code end
		
		// initialize Game Character
		this.character = new GameCharacter();
		this.baseLayer.add(this.character);
		
		// initialize Controle 
		this.controle = new Controle();
		this.addKeyListener(this.controle);
		
		baseLayer.add(pane1);
		baseLayer.add(pane2);
		this.setVisible(true);
		this.timer.start();
	}
	
	
	/**
	 * @author Tobias
	 */
	private void initComponents(){
		//GrafikFrames erstellen
		pane1=new JLayeredPane();
		pane2=new JLayeredPane();
		for(int i=0;i<Ressources.WINDOW.width;i+=Ressources.RASTERSIZE){
			JLabel label=new JLabel();
			label.setBounds(i, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
			pane1.add(label);
			label=new JLabel();
			label.setBounds(i, 0, Ressources.RASTERSIZE, Ressources.WINDOW.height);
			pane2.add(label);
		}
		
		
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
		frameCounter[0]=(frameCounter[0]-1);
		frameCounter[1]=(frameCounter[1]-1);
		pane1.setLocation(frameCounter[0], 0);
		pane2.setLocation(frameCounter[1],0);
		if(frameCounter[0]==-Ressources.WINDOW.width){
			frameCounter[0]=Ressources.WINDOW.width;
			renderer.getNextWindow(pane1);
			
		}
		if(frameCounter[1]==-Ressources.WINDOW.width){
			frameCounter[1]=Ressources.WINDOW.width;
			renderer.getNextWindow(pane2);
		}
		
		//Navigation of the game character via the right, left, up and down keys
		if(this.stepcounter % 8 == 0){
			if((this.controle.getJumpDirection() == 'u') || (this.inAJump)){
				//this.inAJump = this.character.jump(this.controle.getRunDirection());
				this.inAJump = this.character.letCharacterJump(this.controle.getDoubleJump(),this.controle.getRunDirection());
				if(!this.character.inADoubleJump()){
					this.controle.setDoubleJump(false);
				}
				if(!this.inAJump)
					this.controle.jumpStatus = 'n';
			}
			else if(this.controle.getRunDirection() != 'n')
				this.character.run(this.controle.getRunDirection());
		}
		
		this.stepcounter++;
	}
}
