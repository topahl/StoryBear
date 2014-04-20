package com.opticalcobra.storybear.res;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton implements ActionListener{
	//invisible Button
	public Button(int width, int height,String funktionsname, int x, int y){
		addActionListener(this);
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(width,height));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBounds(x, y, width,height);
	}

	//visible Button
	public Button(BufferedImage normal, BufferedImage hover, BufferedImage clicked,BufferedImage disabled, String funktionsname, int x, int y){
		addActionListener(this);

		setIcon(new ImageIcon(normal));
		setRolloverIcon(new ImageIcon(hover));
        setPressedIcon(new ImageIcon(clicked));
        setDisabledIcon(new ImageIcon(disabled));
        
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(normal.getWidth(),normal.getHeight()));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        setBounds(x, y, normal.getWidth(),normal.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}



}