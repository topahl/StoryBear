package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.game.Hero;
import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.res.Button;

import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;

/**
 * 
 * @author Nicolas
 *
 */
public class Editor extends JFrame {
	private JLayeredPane baseLayer;
	
	private StoryEditor editor;
	private WordSuggestor wordSugg;
	private JTextField headline;
	private JLabel background;
	private Button save, export, start;
	
	private Database db;
	
	public static final String EMPTY_TITLE = "<Titel steht hier>";
	public static final String EMPTY_STORY = "<Hier Geschichte schreiben>";
	
	/**
	 * 
	 */
	public Editor(){
		db = new Database();
		initializePanel();
	}
	
	/**
	 * load Story in Editor
	 * @param story
	 */
	public void loadStory(int id) {
		Story story = db.getStoryFromDatabase(id);
		editor.setText(story.getText());
		editor.setLineWrap(true);
		editor.setWrapStyleWord(true);
		headline.setText(story.getTitle());
		editor.setForeground(Color.black);
		headline.setForeground(Color.black);
	}
	
	/**
	 * save Story to DB
	 */
	public void save() {
		db.insertStoryToDatabase(generateStory());
	}
	
	private Story generateStory() {
		Story newStory = new Story();
		newStory.setText(editor.getText());
		newStory.setText(headline.getText());
		newStory.setChangeDate(new Date());
		//newStory.setAuthor();		//TODO: set author
		return newStory;
	}
	
	/**
	 * Testing purposes
	 * TODO: delete after testing
	 * @param args -
	 */
	public static void main(String args[]) {
		Editor e = new Editor();
		e.loadStory(1);
	}
	
	/**
	 * initialize all elements
	 */
	private void initializePanel() {
		// Background
		background = new JLabel();
		background.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		background.setIcon(null);
		background.setVisible(true);
		
		// WordSuggestor
		wordSugg = new WordSuggestor();
		wordSugg.setBounds(98, 67, 455, 479);
		wordSugg.setBorder(BorderFactory.createLineBorder(Color.blue));
		wordSugg.setVisible(true);
		
		// Headline
		headline = new JTextField();
		headline.setBounds(902, 11, (int)(630/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(FontCache.getInstance().getFont("Standard", 60));
		headline.setBorder(null);
		headline.setVisible(true);
		headline.addFocusListener(new EmptyTextFieldListener(EMPTY_TITLE, Color.GRAY, Color.BLACK).initializeCallerTextComponent(headline));
		
		// Editor
		editor = new StoryEditor();
		editor.setBounds(902, 102, (int)(630/Ressources.SCALE), (int)(800/Ressources.SCALE));
		editor.setFont(FontCache.getInstance().getFont("Fontin_R", 25));
		editor.setVisible(true);
		editor.addFocusListener(new EmptyTextFieldListener(EMPTY_STORY, Color.GRAY, Color.BLACK).initializeCallerTextComponent(editor));
		
		// BaseLayer
		baseLayer = new JLayeredPane();
		baseLayer.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		
		// Buttons
		start = new Button(0, 0, (String) null, 0, 0, (MouseMotionListener) null);
		start.setText("Spiel starten");
		start.setBounds(732, 323, 141, 46);
		baseLayer.add(start);
		start.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO: Start game
			}
		});
		
		export = new Button(0, 0, (String) null, 0, 0, (MouseMotionListener) null);
		export.setBounds(641, 222, 232, 80);
		baseLayer.add(export);
		export.setText("Exportieren");
		export.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser choose = new JFileChooser();
				if (choose.showSaveDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
					TextAnalyzer ta = new TextAnalyzer();
					StoryInfo si = ta.analyzeText(generateStory());
					try {
						FileOutputStream fos = new FileOutputStream(choose.getSelectedFile());
						ObjectOutputStream o = new ObjectOutputStream(fos);
						o.writeObject(si);
						o.close();
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} 
		});
		
		save = new Button(0, 0, (String) null, 0, 0, (MouseMotionListener) null);
		save.setText("Speichern");
		save.setBounds(732, 399, 141, 46);
		save.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				save();
			}
		});
		
		baseLayer.add(save);
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
		getContentPane().add(baseLayer);
		setVisible(true);
	}
}
