package com.opticalcobra.storybear.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import com.opticalcobra.storybear.game.Window;

public class OSTimer extends TimerTask {
	
	Window window;
	
	public OSTimer(Window window){
		this.window = window;
	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		try {
			this.window.step();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		
	}

}
