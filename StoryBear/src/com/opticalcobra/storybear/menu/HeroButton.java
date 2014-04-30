package com.opticalcobra.storybear.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.opticalcobra.storybear.res.Ressources;

public class HeroButton extends JButton implements ActionListener {
	private static HeroButton active;
	private static List<HeroButton> heroButtons = new ArrayList<HeroButton>();
	
	private char type;
	
	public HeroButton(char type, BufferedImage normal, BufferedImage hover, BufferedImage disabled) {
		if (active == null)
			active = this;
		
		this.type = type;
		heroButtons.add(this);
		
		setSize((int)(215/Ressources.SCALE),(int)(215/Ressources.SCALE));
		
		setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
		
		setIcon(new ImageIcon(normal));
		setRolloverIcon(new ImageIcon(hover));
		setDisabledIcon(new ImageIcon(disabled));
		
		setCursor(Ressources.CURSORCLICKABLE);
		
		addActionListener(this);
		
		refresh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		active = this;
		refresh();
	}
	
	public static void refresh() {
		for (HeroButton h : heroButtons)
			h.setEnabled(active != h);
	}
	
	public static char getType() {
		return (active == null) ? 'b' : active.type;
	}
	
	public static void cleanup() {
		heroButtons = new ArrayList<HeroButton>();
		active = null;
	}
}
