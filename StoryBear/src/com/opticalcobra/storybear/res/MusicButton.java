package com.opticalcobra.storybear.res;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MusicButton extends JButton implements ActionListener {
	ImageIcon[] now = new ImageIcon[3];;
	ImageIcon[] next = new ImageIcon[3];;
	
	public MusicButton(BufferedImage[] normal, BufferedImage[] mute, int x, int y) {		
		for(int i = 0; i<3; i++) {
			now[i] = new ImageIcon(normal[i]);
			next[i] = new ImageIcon(mute[i]);
		}
		
		setCursor(Ressources.CURSORCLICKABLE);
		setIcon(now[0]);
		setRolloverIcon(now[1]);
		setPressedIcon(now[2]);
		setFocusable(false);
		setBounds(x, y, now[0].getIconWidth(), now[0].getIconHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MusicPlayer.getInstance().toggle();
		ImageIcon[] temp = now;
		now = next;
		next = temp;
		
		setIcon(now[0]);
		setRolloverIcon(now[1]);
		setPressedIcon(now[2]);
	}
}
