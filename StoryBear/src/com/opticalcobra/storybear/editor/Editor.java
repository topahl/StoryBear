package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.menu.TextButton;

/**
 * 
 * @author Nicolas
 *
 */
public class Editor extends JLayeredPane {
	private JLayeredPane baseLayer;
	
	private StoryEditor editor;
	private WordSuggestor wordSugg;
	private JTextField headline;
	private JLabel background;
	private TextButton export, save, start;

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
//		wordSugg = new WordSuggestor();
//		wordSugg.setBounds(98, 67, 455, 479);
//		wordSugg.setBorder(BorderFactory.createLineBorder(Color.blue));
//		wordSugg.setVisible(true);
		
		// Headline
		headline = new JTextField();
		headline.setBounds((int)(902/Ressources.SCALE), (int)(11/Ressources.SCALE), (int)(630/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(FontCache.getInstance().getFont("Standard", 60));
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.addFocusListener(new EmptyTextFieldListener(EMPTY_TITLE, Color.GRAY, Color.BLACK).initializeCallerTextComponent(headline));
		
		// Editor
		editor = new StoryEditor();
		editor.setBounds((int)(902/Ressources.SCALE), (int)(102/Ressources.SCALE), (int)(630/Ressources.SCALE), (int)(800/Ressources.SCALE));
		editor.setFont(FontCache.getInstance().getFont("Fontin_R", 25));
		editor.setVisible(true);
		editor.addFocusListener(new EmptyTextFieldListener(EMPTY_STORY, Color.GRAY, Color.BLACK).initializeCallerTextComponent(editor));
		
		// Author
		JLabel author = new JLabel();
		author.setText("Autor: ");
		author.setBounds((int)(200/Ressources.SCALE),0,(int)(190/Ressources.SCALE),(int)(30/Ressources.SCALE));
		author.setVisible(true);
		
		// Date
		JLabel date = new JLabel();
		date.setText("Datum: ");
		date.setBounds((int)(200/Ressources.SCALE),0,(int)(190/Ressources.SCALE),(int)(30/Ressources.SCALE));
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setVisible(true);
		
		// BaseLayer
		baseLayer = new JLayeredPane();
		baseLayer.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		
		// Buttons
		start = new TextButton("Spiel starten", 60, 300, 195, 60);
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
		
		export = new TextButton("Exportieren", 60+195+10, 300, 195, 60);
		baseLayer.add(export);
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
		
		save = new TextButton("Speichern", 60+2*(195+10), 300, 195, 60);
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
//		baseLayer.add(wordSugg);
		baseLayer.add(date);
		baseLayer.add(author);
		baseLayer.add(background);
		baseLayer.setVisible(true);
		
		// Frame
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
		add(baseLayer);
		setVisible(true);
	}
}
