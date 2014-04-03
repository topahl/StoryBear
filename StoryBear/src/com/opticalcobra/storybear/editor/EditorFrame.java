package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.opticalcobra.storybear.game.Hero;
import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

/**
 * 
 * @author Nicolas
 *
 */
public class EditorFrame extends JFrame {
	private JLayeredPane baseLayer;
	
	private StoryEditor editor;
	private WordSuggestor wordSugg;
	private JTextField headline;
	private JLabel background;
	
	public static final String EMPTY_TITLE = "<Titel steht hier>";
	public static final String EMPTY_STORY = "<Hier Geschichte schreiben>";
	
	public EditorFrame(){
		// Background
		try {
			background = new JLabel();
			background.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
			background.setIcon(new ImageIcon(ImageIO.read(new File(Ressources.RESPATH+"/images/editor_bg.png")))); // TODO load from database
			background.setVisible(true);
		} catch (IOException e) {
		}
		
		// WordSuggestor
		wordSugg = new WordSuggestor();
		wordSugg.setBounds(235, 255, 455, 517);
		wordSugg.setBorder(BorderFactory.createLineBorder(Color.blue));
		wordSugg.setVisible(true);
		
		
		
		// Headline
		headline = new JTextField();
		headline.setBounds(925, 70, 630, 80);
		headline.setFont(FontCache.getInstance().getFont("Standard", 60));
		headline.setBorder(null);
		headline.setVisible(true);
		headline.addFocusListener(new EmptyTextFieldListener(EMPTY_TITLE, Color.GRAY, Color.BLACK).initializeCallerTextComponent(headline));
		
		// Editor
		editor = new StoryEditor();
		editor.setBounds(925, 170, 630, 800);
		editor.setFont(FontCache.getInstance().getFont("Standard", 25));
		editor.setVisible(true);
		editor.addFocusListener(new EmptyTextFieldListener(EMPTY_STORY, Color.GRAY, Color.BLACK).initializeCallerTextComponent(editor));
		
		// BaseLayer
		baseLayer = new JLayeredPane();
		baseLayer.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		baseLayer.add(headline);
		baseLayer.add(editor);
		baseLayer.add(wordSugg);
		baseLayer.add(background);
		baseLayer.setVisible(true);
		
		// Frame
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);
		setUndecorated(true);
		setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		add(baseLayer);
		setVisible(true);
	}
	
	
	/**
	 * Testing purposes
	 * TODO: delete after testing
	 * @param args -
	 */
	public static void main(String args[]) {
		new EditorFrame();
	}
}
