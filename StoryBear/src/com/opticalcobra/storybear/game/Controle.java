package com.opticalcobra.storybear.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controle implements KeyListener {
	private char runDirection = 'n'; //n=not running, l=left, r=right
	private char jumpDirection = 'n';	//n=not, u=jump up, d=duck down
	
	public Controle(){
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
			case KeyEvent.VK_RIGHT:
				this.setRunDirection('r');
				break;
			case KeyEvent.VK_LEFT:
				this.setRunDirection('l');
				break;
			case KeyEvent.VK_UP:
				this.setJumpDirection('u');
				break;	
			case KeyEvent.VK_DOWN:
				this.setJumpDirection('d');
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			this.setRunDirection('n');
			break;
		case KeyEvent.VK_LEFT:
			this.setRunDirection('n');
			break;
		case KeyEvent.VK_UP:
			this.setJumpDirection('n');
			break;	
		case KeyEvent.VK_DOWN:
			this.setJumpDirection('n');
			break;
	}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			this.setRunDirection('r');
			break;
		case KeyEvent.VK_LEFT:
			this.setRunDirection('l');
			break;
		case KeyEvent.VK_UP:
			this.setJumpDirection('u');
			break;	
		case KeyEvent.VK_DOWN:
			this.setJumpDirection('d');
			break;
	}
	}

	public char getRunDirection() {
		return runDirection;
	}

	public void setRunDirection(char runDirection) {
		this.runDirection = runDirection;
	}

	public char getJumpDirection() {
		return jumpDirection;
	}

	public void setJumpDirection(char jumpDirection) {
		this.jumpDirection = jumpDirection;
	}

}
