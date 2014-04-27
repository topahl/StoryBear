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


public class TextButton extends JButton implements MouseListener{
	private final static int borderThick = 2;
	private final static float fontSize = (float)(28f/Ressources.SCALE);
	private final static Font font = FontCache.getInstance().getFont("Fontin_R", fontSize);
	
	private Border standardBorder;
	private Border hoverBorder;
	private Color standardColor = Color.black;
	private Color hoverColor = Ressources.MENUCOLORSELECTED;
	
	public TextButton(String text, int x, int y, int width, int height, float size, Color standard) {
		this(text, x, y, width, height, size);
		setForeground(standard);
		standardColor = standard;
		standardBorder = BorderFactory.createLineBorder(standard, borderThick);
		setStyle(standard, standardBorder);
	}
	
	public TextButton(String text, int x, int y, int width, int height, float size) {
		this(text, x, y, width, height);
		setFontSize(size);
	}
	
	public TextButton(String text, int x, int y, int width, int height) {
		standardBorder = BorderFactory.createLineBorder(standardColor, borderThick);
		hoverBorder	= BorderFactory.createLineBorder(hoverColor, borderThick);
		
		addMouseListener(this);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setText(text.toUpperCase());
		setFont(font);
		setCursor(Ressources.CURSORCLICKABLE);
		setBounds((int)(x/Ressources.SCALE), (int)(y/Ressources.SCALE), (int)(width/Ressources.SCALE), (int)(height/Ressources.SCALE));
		setFocusable(false);
		
		setStyle(standardColor, standardBorder);
	}
	
	public void setFontSize(float size) {
		setFont(getFont().deriveFont(size));
	}
	
	private void setStyle(Color color, Border border) {
		setBorder(border);
		setForeground(color);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(isEnabled())
			setStyle(standardColor, standardBorder);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(isEnabled())
			setStyle(hoverColor, hoverBorder);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(isEnabled())
		 setStyle(standardColor, standardBorder);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}