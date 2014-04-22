package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;


public class TextButton extends JButton implements ActionListener, MouseListener{
	private final static int borderThick = 2;
	private final static Color standardColor = Color.black;
	private final static Color hoverColor = Color.red;
	private final static float fontSize = (float)(25f/Ressources.SCALE);
	private final static Font font = FontCache.getInstance().getFont("Fontin_R", fontSize);
	
	
	private Border standardBorder;
	private Border hoverBorder;
	
	public TextButton(String text, int x, int y, int width, int height) {
		standardBorder = BorderFactory.createLineBorder(standardColor, borderThick);
		hoverBorder	= BorderFactory.createLineBorder(hoverColor, borderThick);
		
		addMouseListener(this);
		addActionListener(this);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setText(text.toUpperCase());
		setFont(font);
		setBounds((int)(x/Ressources.SCALE), (int)(y/Ressources.SCALE), (int)(width/Ressources.SCALE), (int)(height/Ressources.SCALE));
		setFocusable(false);
		
		setStyle(standardColor, standardBorder);
	}
	
	private void setStyle(Color color, Border border) {
		setBorder(border);
		setForeground(color);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		setStyle(standardColor, standardBorder);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setStyle(hoverColor, hoverBorder);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setStyle(standardColor, standardBorder);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}