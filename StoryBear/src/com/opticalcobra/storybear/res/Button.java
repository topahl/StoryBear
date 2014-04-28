package com.opticalcobra.storybear.res;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private String method;

	public Button(BufferedImage normal, BufferedImage hover, BufferedImage clicked, BufferedImage disabled, int x, int y){
		setIcon(new ImageIcon(normal));
		setRolloverIcon(new ImageIcon(hover));
        setPressedIcon(new ImageIcon(clicked));
        setDisabledIcon(new ImageIcon(disabled));
        
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(normal.getWidth(),normal.getHeight()));
        setCursor(Ressources.CURSORCLICKABLE);
        
        setBounds((int) (x/Ressources.SCALE), (int) (y/Ressources.SCALE), (int) (normal.getWidth()/Ressources.SCALE), (int) (normal.getHeight()/Ressources.SCALE));
	}
	
	public Button(String method, int width, int height, int x, int y){
		this.method = method;
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(width,height));
        setCursor(Ressources.CURSORCLICKABLE);
        setBounds((int) (x/Ressources.SCALE), (int) (y/Ressources.SCALE), (int) (width/Ressources.SCALE), (int) (height/Ressources.SCALE));
	}

	public Button(String method, BufferedImage normal, BufferedImage hover, BufferedImage clicked, BufferedImage disabled, int x, int y){
		this.method = method;
		setIcon(new ImageIcon(normal));
		setRolloverIcon(new ImageIcon(hover));
        setPressedIcon(new ImageIcon(clicked));
        setDisabledIcon(new ImageIcon(disabled));
        
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(normal.getWidth(),normal.getHeight()));
        setCursor(Ressources.CURSORCLICKABLE);
        
        setBounds((int) (x/Ressources.SCALE), (int) (y/Ressources.SCALE), (int) (normal.getWidth()/Ressources.SCALE), (int) (normal.getHeight()/Ressources.SCALE));
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}