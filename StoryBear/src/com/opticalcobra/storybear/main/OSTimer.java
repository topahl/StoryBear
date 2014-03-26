package com.opticalcobra.storybear.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.opticalcobra.storybear.game.Window;

public class OSTimer implements ActionListener {
	
	Window window;
	
	public OSTimer(Window window){
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.window.step();
		
	}

}
