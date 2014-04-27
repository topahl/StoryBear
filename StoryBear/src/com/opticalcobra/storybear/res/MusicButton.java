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
		
		if(!MusicPlayer.getInstance().isRunning())
			switchLists();
		
		setCursor(Ressources.CURSORCLICKABLE);
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
		setIcon(now[0]);
		setRolloverIcon(now[1]);
		setPressedIcon(now[2]);
		setFocusable(false);
		setBounds((int)(x/Ressources.SCALE), (int)(y/Ressources.SCALE), now[0].getIconWidth(), now[0].getIconHeight());
		
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MusicPlayer.getInstance().toggle();
		
		switchLists();
		
		setIcon(now[0]);
		setRolloverIcon(now[1]);
		setPressedIcon(now[2]);
	}
	
	private void switchLists() {
		ImageIcon[] temp = now;
		now = next;
		next = temp;
	}
}
