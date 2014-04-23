package com.opticalcobra.storybear.menu;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.editor.Editor;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.menu.MenuButton;
import com.opticalcobra.storybear.res.Ressources;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.SQLException;

import javax.swing.JLabel;

public class Menu extends JFrame {
	public static final Rectangle innerPanel = new Rectangle(0, 0, (int)(1440/Ressources.SCALE), (int)(880/Ressources.SCALE));
	public static final Font fontHeadline[] = { FontCache.getInstance().getFont("Standard", (float) (50f/Ressources.SCALE)), FontCache.getInstance().getFont("Standard", (float) (40f/Ressources.SCALE)), FontCache.getInstance().getFont("Standard", (float) (35f/Ressources.SCALE)) };
	public static final Font fontText[] = { FontCache.getInstance().getFont("Standard", (float) (28f/Ressources.SCALE)) };
	
	public JLayeredPane main, editor, welcome, credits, user, manu, highscore;
	public JLayeredPane bookBox;
	public Container me;
	
	private Imagelib imagelib;
	private Database db = new Database();
	
	public Menu() {
		imagelib = Imagelib.getInstance();

		// Frame-Settings
		initializeFrame();
		
		// Inner Panels
		manu = createNewInnerPanel(new Manual());
		highscore = createNewInnerPanel();
		user = createNewInnerPanel();
		editor = createNewInnerPanel(new Editor());
		credits = createNewInnerPanel(new Credits());
		
		// Set start screen
		main = user;
		
		// BookBox
		initializeBookBox();
		
		// Navigation
		initializeNavigation();
		
		// Background
		initializeBackground();
	}
	
	
	//TODO: delete
	public static void main(String[] args) {
		new Menu();
	}
	
	public JLayeredPane getMain() {
		return main;
	}
	
	public void setMain(JComponent innerComp) {
		this.main = (JLayeredPane) innerComp;
	}
	
	public void close() {
		try {
			db.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	private JLayeredPane createNewInnerPanel() {
		JLayeredPane panel = new JLayeredPane();
		createNewInnerPanel(panel);
		return panel;
	}
	
	private JLayeredPane createNewInnerPanel(JLayeredPane panel) {
		panel.setBounds((int)(350/Ressources.SCALE), (int)(100/Ressources.SCALE), innerPanel.width, innerPanel.height);
		panel.setVisible(false);
		getContentPane().add(panel);
		return panel;
	}
	
	private void initializeBookBox() {
		bookBox = new BookBox(this);
		getContentPane().add(bookBox);
	}
	
	private void initializeNavigation() {
		JLayeredPane navigation = new JLayeredPane();
		navigation.setBounds((int)(15/Ressources.SCALE), 0, (int)(315/Ressources.SCALE), (int)(1080/Ressources.SCALE));
		getContentPane().add(navigation);
		
		MenuButton navManu = new MenuButton(0, 110, imagelib.loadDesignImage("label_anleitung_n"), imagelib.loadDesignImage("label_anleitung_h"), imagelib.loadDesignImage("label_anleitung_d"), this, manu);
		MenuButton navUser = new MenuButton(0, 235, imagelib.loadDesignImage("label_user_n"), imagelib.loadDesignImage("label_user_h"), imagelib.loadDesignImage("label_user_d"), this, user);
		MenuButton navEditor = new MenuButton(0, 370, imagelib.loadDesignImage("label_editor_n"), imagelib.loadDesignImage("label_editor_h"), imagelib.loadDesignImage("label_editor_d"), this, editor);
		MenuButton navHigh = new MenuButton(0, 485, imagelib.loadDesignImage("label_highscore_n"), imagelib.loadDesignImage("label_highscore_h"), imagelib.loadDesignImage("label_highscore_d"), this, highscore);
		MenuButton navCredits = new MenuButton(0, 685, imagelib.loadDesignImage("label_credits_n"), imagelib.loadDesignImage("label_credits_h"), imagelib.loadDesignImage("label_credits_d"), this, credits);
		MenuButton navExit = new MenuButton(0, 810, imagelib.loadDesignImage("label_exit_n"), imagelib.loadDesignImage("label_exit_h"), imagelib.loadDesignImage("label_exit_d"), this, "close");
		
		navigation.add(navManu);
		navigation.add(navUser);
		navigation.add(navEditor);
		navigation.add(navHigh);
		navigation.add(navCredits);
		navigation.add(navExit);
		
//		navManu.disable();
//		navEditor.disable();
//		navHigh.disable();
//		navCredits.disable();
	}
	
	private void initializeBackground() {
		JLabel book = new JLabel();
		book.setBounds(0, 0, (int)(1920/Ressources.SCALE), (int)(1080/Ressources.SCALE));
		getContentPane().add(book);
		book.setIcon(new ImageIcon(imagelib.loadDesignImage("menu_book_opened")));
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, (int)(1920/Ressources.SCALE), (int)(1080/Ressources.SCALE));
		getContentPane().add(background);
		background.setIcon(new ImageIcon(imagelib.loadDesignImage("menu_bg")));
	}
	
	private void initializeFrame() {
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		setVisible(true);
	}
}
