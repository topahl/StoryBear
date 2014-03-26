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
	
	JLayeredPane pane1;
	JLayeredPane pane2;
	DummyRenderer renderer = new DummyRenderer();
	
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
		
		pane1 = renderer.getNextWindow();
		pane1.setLocation(0, 0);

		//Dummy Code end
		
		//Dummycode von Miri
		this.character = new GameCharacter(this.baseLayer);
		//Ende Dummy von Miri
		
		baseLayer.add(pane1);
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
		frameCounter=(frameCounter+1) %Ressources.SCREEN.width;		
		
		if(this.stepcounter % 8 == 0)
			this.character.jump();	//TODO: auf JUMP-Taste überprüfen und nur dann alle mod10 Schritte jump() aufrufen
		
		this.stepcounter++;
	}
}
