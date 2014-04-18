package com.opticalcobra.storybear.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control implements KeyListener {
//	private char runDirection = 'n'; 	//n=not running, l=left, r=right
//	private char jumpDirection = 'n';	//n=not, u=jump up, d=duck down
//	private boolean doubleJump = false;	
//	public char jumpStatus = 'n';				//n=no, y=yes, m=maybe --> tracks Doublejump
	
	
	public Control(){
		
	}

	/**
	 * @author Miriam
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
			case KeyEvent.VK_RIGHT:
				Hero.getInstance().setRunDirection('r');
				break;
			case KeyEvent.VK_LEFT:
				Hero.getInstance().setRunDirection('l');
				break;
			case KeyEvent.VK_UP:
				System.out.println("Ich werde gefangen");
				Hero.getInstance().startJump();
				break;	
			case KeyEvent.VK_DOWN:
				break;
		}
	}

	/**
	 * @author Miriam
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			Hero.getInstance().setRunDirection('n');
			break;
		case KeyEvent.VK_UP:
			break;	
		case KeyEvent.VK_DOWN:
			break;
		}
	}

	/**
	 * @author Miriam
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

//	public char getRunDirection() {
//		return runDirection;
//	}
//
//	public void setRunDirection(char runDirection) {
//		this.runDirection = runDirection;
//	}
//
//	public char getJumpDirection() {
//		return jumpDirection;
//	}
//
//	public void setJumpDirection(char jumpDirection) {
//		this.jumpDirection = jumpDirection;
//	}
//	
//	public boolean getDoubleJump() {
//		return doubleJump;
//	}
//
//	public void setDoubleJump(boolean doubleJump) {
//		this.doubleJump = doubleJump;
//	}

}
