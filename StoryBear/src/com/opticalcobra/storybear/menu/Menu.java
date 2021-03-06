package com.opticalcobra.storybear.menu;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.editor.Editor;
import com.opticalcobra.storybear.editor.Loadingscreen;
import com.opticalcobra.storybear.game.Hero;
import com.opticalcobra.storybear.res.Button;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.MusicButton;
import com.opticalcobra.storybear.res.MusicPlayer;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.menu.MenuButton;
import com.opticalcobra.storybear.res.Ressources;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JLabel;

public class Menu extends JFrame {
	public static final Rectangle innerPanel = new Rectangle(0, 0, (int)(1440/Ressources.SCALE), (int)(880/Ressources.SCALE));
	public static final Font fontMenuHeadline =  FontCache.getInstance().getFont("Standard", 60f);
	public static final Font fontHeadline[] = { FontCache.getInstance().getFont("Standard", 50f), FontCache.getInstance().getFont("Standard", 40f), FontCache.getInstance().getFont("Standard", 35f), FontCache.getInstance().getFont("Standard", 30f) };
	public static final Font fontText[] = { FontCache.getInstance().getFont("Standard", 28f) };

	public static final int leftPageXUnscaled = 390-350;
	public static final int rightPageXUnscaled = 1100-350;
	public static final int leftPageX = (int)(leftPageXUnscaled/Ressources.SCALE);
	public static final int rightPageX = (int)(rightPageXUnscaled/Ressources.SCALE);
	
	public static final int pageWidthUnscaled = 600;
	public static final int pageWidth = (int)(pageWidthUnscaled/Ressources.SCALE);
	
	
	public JLabel currentUser;
	public JLayeredPane main, editor, welcome, credits, user, manu, highscore;
	public BookBox bookBox;
	public Container me;
	public MenuButton navManu,navUser,navEditor,navHigh, navCredits, navExit;
	public JButton musicButton;
	public LoadingPanel loading;
	
	private Imagelib imagelib;
	private Database db = new Database();
	
	public Menu() {
		Hero.getInstance().cleanup();
		
		imagelib = Imagelib.getInstance(); //database Loading starts
		
		// Frame-Settings
		initializeFrame();
		
		// Loading
		initializeLoadingBox();
		
		// MusicButton
		initializeMusicButton();
		
		// BookBox
		initializeBookBox();
		
		// Inner Panels
		manu = createNewInnerPanel(new Manual());
		highscore = createNewInnerPanel(new HighscoreList());
		user = createNewInnerPanel(new UserPanel(this));
		editor = createNewInnerPanel(new Editor(this));
		credits = createNewInnerPanel(new Credits());
		
		// Set start screen
		main = (!User.isCurrentUserSet()) ? user : manu;
		
		// Navigation
		initializeUser();
		initializeNavigation();
		
		// Background
		initializeBackground();
		
		main.setVisible(true);
		
		if(!User.isCurrentUserSet()) {
			navManu.disable();
			navEditor.disable();
			navHigh.disable();
			navCredits.disable();
			bookBox.disable();
		} else {
			enableAllMenuButtons();
		}
	}

	public Menu(int levelId) {
		this();
		main.setVisible(false);
		setMain(highscore);
		main.setVisible(true);
		((HighscoreList) highscore).showStory(levelId);
	}
	
	public Menu(Loadingscreen ls) {
		this();
		ls.dispose(); //Diable Loading Screen
	}
	
	public JLayeredPane getMain() {
		return main;
	}
	
	public void setMain(JComponent innerComp) {
		this.main = (JLayeredPane) innerComp;
	}
	
	public void enableAllMenuButtons() {
		navManu.enable();
		navEditor.enable();
		navHigh.enable();
		navCredits.enable();
		bookBox.enable();
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
	
	private void initializeUser() {
		JLabel text = new JLabel();
		text.setText("Du spielst als:");
		text.setBounds((int)(17/Ressources.SCALE), (int)(12/Ressources.SCALE), (int)(250/Ressources.SCALE), (int)(60/Ressources.SCALE));
		text.setFont(FontCache.getInstance().getFont("Fontin_SC", 25f));
		text.setForeground(Ressources.TEXTBROWNCOLOR);
		getContentPane().add(text);
		currentUser = new JLabel();
		currentUser.setBounds((int)(17/Ressources.SCALE), (int)(40/Ressources.SCALE), (int)(250/Ressources.SCALE), (int)(60/Ressources.SCALE));
		currentUser.setFont(Menu.fontText[0]);
		currentUser.setText(User.isCurrentUserSet() ? User.getCurrentUser().getName() : "");
		currentUser.setForeground(Ressources.TEXTBROWNCOLOR);
		currentUser.setFont(FontCache.getInstance().getFont("Fontin_SC", 40f));
		getContentPane().add(currentUser);
	}
	
	private void initializeNavigation() {
		JLayeredPane navigation = new JLayeredPane();
		navigation.setBounds((int)(15/Ressources.SCALE), 0, (int)(315/Ressources.SCALE), (int)(1080/Ressources.SCALE));
		getContentPane().add(navigation);
		
		navManu = new MenuButton(0, 140, imagelib.loadDesignImage("label_anleitung_n"), imagelib.loadDesignImage("label_anleitung_h"), imagelib.loadDesignImage("label_anleitung_d"), this, manu);
		navUser = new MenuButton(0, 265, imagelib.loadDesignImage("label_user_n"), imagelib.loadDesignImage("label_user_h"), imagelib.loadDesignImage("label_user_d"), this, user);
		navEditor = new MenuButton(0, 400, imagelib.loadDesignImage("label_editor_n"), imagelib.loadDesignImage("label_editor_h"), imagelib.loadDesignImage("label_editor_d"), this, editor);
		navHigh = new MenuButton(0, 515, imagelib.loadDesignImage("label_highscore_n"), imagelib.loadDesignImage("label_highscore_h"), imagelib.loadDesignImage("label_highscore_d"), this, highscore);
		navCredits = new MenuButton(0, 715, imagelib.loadDesignImage("label_credits_n"), imagelib.loadDesignImage("label_credits_h"), imagelib.loadDesignImage("label_credits_d"), this, credits);
		navExit = new MenuButton(0, 840, imagelib.loadDesignImage("label_exit_n"), imagelib.loadDesignImage("label_exit_h"), imagelib.loadDesignImage("label_exit_d"), this, "close");
		
		navigation.add(navManu);
		navigation.add(navUser);
		navigation.add(navEditor);
		navigation.add(navHigh);
		navigation.add(navCredits);
		navigation.add(navExit);
	}
	
	private void initializeBackground() {
		JLabel background = new JLabel();
		background.setBounds(0, 0, (int)(1920/Ressources.SCALE), (int)(1080/Ressources.SCALE));
		getContentPane().add(background);
		background.setIcon(new ImageIcon(imagelib.loadDesignImage("menu_bg")));
	}
	
	private void initializeMusicButton() {
		BufferedImage[] normal = {imagelib.loadDesignImage("menu_sound_normal_on"),
				imagelib.loadDesignImage("menu_sound_hover_on"),
				imagelib.loadDesignImage("menu_sound_click_on")};
		BufferedImage[] mute = {imagelib.loadDesignImage("menu_sound_normal_mute"),
				imagelib.loadDesignImage("menu_sound_hover_mute"),
				imagelib.loadDesignImage("menu_sound_click_mute")};
		MusicButton musicButton = new MusicButton(mute, normal, 125, 1000);
		getContentPane().add(musicButton);
	}
	
	private void initializeLoadingBox() {
		loading = new LoadingPanel("Rendern...");
	}
	
	private void initializeFrame() {
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setCursor(Ressources.CURSORNORMAL);
		setResizable(false);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		setVisible(true);
	}
	
	public void dispose() {
		HeroButton.cleanup();
		super.dispose();
	}
}
