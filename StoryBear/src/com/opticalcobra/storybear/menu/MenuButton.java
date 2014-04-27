package com.opticalcobra.storybear.menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.res.Ressources;

public class MenuButton extends JButton implements MouseListener {
	private ActionListener action;
	private ImageIcon iconNormal, iconHover, iconDisabled;
	
	public MenuButton(int x, int y, BufferedImage normal, BufferedImage hover, BufferedImage disabled) {
		setBounds((int)(x/Ressources.SCALE), (int)(y/Ressources.SCALE), normal.getWidth(), normal.getHeight());
		
		iconNormal = new ImageIcon(normal);
		iconHover = new ImageIcon(hover);
		iconDisabled = new ImageIcon(disabled);
        
		enable();
		
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(Ressources.CURSORCLICKABLE);
	}
	
	public MenuButton(int x, int y, BufferedImage normal, BufferedImage hover, BufferedImage disabled, Menu object, JComponent innerComp) {
		this(x, y, normal, hover, disabled);
		action = new PanelSwitcher(object, innerComp);
        addActionListener(action);
	}
	
	public MenuButton(int x, int y, BufferedImage normal, BufferedImage hover, BufferedImage disabled, Menu object, String method) {
		this(x, y, normal, hover, disabled);
		action = new MethodCaller(object, method);
        addActionListener(action);
	}
	
	public void disable() {
		removeActionListener(action);
		setRolloverIcon(null);
		setIcon(iconDisabled);
		setCursor(Ressources.CURSORNORMAL);
	}
	
	public void enable() {
		addActionListener(action);
		setRolloverIcon(iconHover);
		setIcon(iconNormal);
		setCursor(Ressources.CURSORCLICKABLE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	protected class PanelSwitcher implements ActionListener {
		JComponent innerComp;
		Menu object;
		public PanelSwitcher(Menu object2, JComponent innerComp) {
			this.innerComp = innerComp;
	        this.object = object2;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			object.getMain().setVisible(false);
			object.setMain(innerComp);
			object.getMain().setVisible(true);
		}
	}
	
	protected class MethodCaller implements ActionListener {
		Object object;
		Method method;
		public MethodCaller(Object object, String method) {
			this.object = object;
			try {
				this.method = object.getClass().getMethod(method, null);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				method.invoke(object, null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}