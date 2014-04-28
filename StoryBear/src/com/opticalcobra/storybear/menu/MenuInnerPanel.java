package com.opticalcobra.storybear.menu;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class MenuInnerPanel extends JLayeredPane {
	protected Database db = new Database();
	protected Imagelib imagelib = Imagelib.getInstance();
	protected Menu menu;
	
	public MenuInnerPanel(Menu menu) {
		this();
		
		// import
		this.menu = menu;
	}
	
	public MenuInnerPanel() {
		// general settings
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
	}

	/**
	 * 
	 * @param text
	 */
	protected JLabel addMenuHeadline(String text) {
		return addMenuHeadline(text, true);
	}
	
	protected JLabel addMenuHeadline(String text, boolean add) {
		JLabel menuHeadline = new JLabel();
		menuHeadline.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		menuHeadline.setFont(Menu.fontMenuHeadline);
		menuHeadline.setVisible(true);
		menuHeadline.setText(text);
		menuHeadline.setForeground(Color.black);
		
		if(add)
			add(menuHeadline);
		
		return menuHeadline;
	}
	
	/**
	 * 
	 */
	protected JLabel addMenuHeadlineUnderlining(boolean add) {
		JLabel underlining = new JLabel(new ImageIcon(imagelib.loadDesignImage("menu_headline_underscore")));
		underlining.setBounds((int)(40/Ressources.SCALE), (int)(70/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(70/Ressources.SCALE));
		
		if(add)
			add(underlining);
		
		return underlining;
	}
	
	protected JLabel addMenuHeadlineUnderlining() {
		return addMenuHeadlineUnderlining(true);
	}

	/**
	 * 
	 * @return
	 */
	protected JTextArea generateStandardTextArea() {
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setFocusable(false);
		text.setOpaque(false);
		text.setEditable(false);
		text.setFont(Menu.fontHeadline[2]);
		text.setForeground(Color.black);
		return text;
	}
	
	/**
	 * 
	 * @return
	 */
	protected JLabel generateStandardLabel() {
		JLabel label = new JLabel();
		label.setFont(Menu.fontHeadline[0]);
		label.setForeground(Color.black);
		return label;
	}
	
	/**
	 * 
	 * @return
	 */
	protected JTextField generateStandardTextField() {
		JTextField field = new JTextField();
		field.setFont(Menu.fontText[0]);
        field.setForeground(Color.black);
        field.setOpaque(false);
        field.setForeground(Color.black);
        field.setCaretColor(Color.black);
        field.setCursor(Ressources.CURSORCLICKABLE);
        field.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        return field;
	}
}