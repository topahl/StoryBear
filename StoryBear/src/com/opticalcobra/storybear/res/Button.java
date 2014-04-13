package com.opticalcobra.storybear.res;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton implements ActionListener, MouseListener{
	//invisible Button
	public Button(int width, int height,String funktionsname, int x, int y, MouseMotionListener ml){
		addMouseMotionListener(ml);
		addMouseListener(this);
		addActionListener(this);
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(width,height));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBounds(x, y, width,height);
	}

	//visible Button
	public Button(BufferedImage normal, BufferedImage hover, BufferedImage clicked,BufferedImage disabled, String funktionsname, int x, int y,MouseMotionListener ml){
		addMouseMotionListener(ml);
		addMouseListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

}