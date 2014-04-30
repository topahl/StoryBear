package com.opticalcobra.storybear.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.opticalcobra.storybear.editor.Loadingscreen;
import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.res.Button;

public class Control implements KeyListener, ActionListener {
//	private char runDirection = 'n'; 	//n=not running, l=left, r=right
//	private char jumpDirection = 'n';	//n=not, u=jump up, d=duck down
//	private boolean doubleJump = false;	
//	public char jumpStatus = 'n';				//n=no, y=yes, m=maybe --> tracks Doublejump
	
	public Window window;
	
	public Control(Window window) {
		this.window = window;
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
				Hero.getInstance().startJump();
				break;	
			case KeyEvent.VK_DOWN:
				break;
			case KeyEvent.VK_D:
				Hero.getInstance().setRunDirection('r');
				break;
			case KeyEvent.VK_A:
				Hero.getInstance().setRunDirection('l');
				break;
			case KeyEvent.VK_W:
				Hero.getInstance().startJump();
				break;	
			case KeyEvent.VK_S:
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
		case KeyEvent.VK_D:
		case KeyEvent.VK_A:
			Hero.getInstance().setRunDirection('n');
			break;
		case KeyEvent.VK_W:
			break;	
		case KeyEvent.VK_S:
			break;
		}
	}

	/**
	 * @author Miriam
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class.forName("com.opticalcobra.storybear.game.Control").getMethod(((Button) e.getSource()).getMethod(), null).invoke(this, null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void close(){
		window.dispose();
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
