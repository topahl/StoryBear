package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.res.MusicPlayer;
import com.opticalcobra.storybear.res.Ressources;

public class Loadingscreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5484011329695247582L;
	private JLayeredPane baseLayer;
	private JLabel image;

	public Loadingscreen(){
		MusicPlayer.getInstance().start();	// play music
		
		this.baseLayer = new JLayeredPane();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(Ressources.CURSORNORMAL);
		this.setResizable(false);
		this.setUndecorated(true);
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
		image = new JLabel();
		image.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		BufferedImage temp;
		try {
			temp = ImageIO.read(new File(Ressources.RESPATH+Ressources.LOADINGPICTURE));
			BufferedImage icon = new BufferedImage((int)(temp.getWidth()/Ressources.SCALE),(int)(temp.getHeight()/Ressources.SCALE),BufferedImage.TYPE_INT_ARGB);
			icon.getGraphics().drawImage(temp, 0, 0, (int)(temp.getWidth()/Ressources.SCALE),(int)(temp.getHeight()/Ressources.SCALE),null);
			image.setIcon(new ImageIcon(icon));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		baseLayer.add(image);
		this.pack();
		this.setVisible(true);
		
		Menu menu = new Menu(this);
	}
	
}
