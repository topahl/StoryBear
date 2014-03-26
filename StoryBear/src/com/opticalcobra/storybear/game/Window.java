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
	private int frameCounter;
	private int stepcounter = 0;
	private GameCharacter character;
	
	private int jumpCounter = 0;
	
	JLabel label = new JLabel();
	JLabel characterLabel = new JLabel();
	
	public Window(){
		
		this.timer = new Timer(Ressources.GAMESPEED,new OSTimer(this));
		
		
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		this.setResizable(false);
		this.setUndecorated(true);
		
		initComponents();
		
		//TODO Remove Dummy code
		//Dummy Code 
		
		label.setText("Hallo Welt");
		label.setBounds(0,0,100,100);
		baseLayer.add(label);
		//Dummy Code end
		
		//Dummycode von Miri
		this.character = new GameCharacter();
		this.characterSpawns();
		//Ende Dummy von Miri
		

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
	 * @author Miriam
	 */
	private void characterSpawns(){
		//Dummy
		this.characterLabel.setText("Jump");
		this.characterLabel.setBounds(this.character.getPositionX(),
				this.character.getPositionY(),
				this.character.getHeight(),
				this.character.getWidth());
		baseLayer.add(this.characterLabel);	
	}
	
	/**
	 * @author Miriam
	 */
	private void jump(){
		int newPositionX;
		float newPositionY;
		float time = this.jumpCounter / ((float) Ressources.GAMESPEED);
		
		newPositionY = 20 * time * time - 20 * time + 5;
		newPositionY = this.character.getCurrentLevel() + newPositionY * Ressources.GAMESPEED;
		
		this.character.setPositionY((int) (newPositionY));
		this.characterLabel.setBounds(this.character.getPositionX(),
				this.character.getPositionY(),
				this.character.getHeight(),
				this.character.getWidth());
		
		if(this.jumpCounter == 10)
			this.jumpCounter = 0;
		else
			this.jumpCounter++;
	}
	
	
	/**
	 * 
	 */
	public void step(){
		frameCounter=(frameCounter+1) %Ressources.SCREEN.width;
		label.setLocation(frameCounter, 0);
		
		if(this.stepcounter % 10 == 0)
			this.jump();
		
		this.stepcounter++;
	}
}
