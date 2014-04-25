package com.opticalcobra.storybear.res;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private String method;

	//invisible Button
	public Button(String method, int width, int height, int x, int y){
		this.method = method;
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(width,height));
        setCursor(Ressources.CURSORCLICKABLE);
        setBounds(x, y, width,height);
	}

	//visible Button
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
        
        setBounds(x, y, normal.getWidth(),normal.getHeight());
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}