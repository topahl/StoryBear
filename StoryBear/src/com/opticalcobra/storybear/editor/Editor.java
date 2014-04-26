package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.menu.Scrollbar;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;
import com.opticalcobra.storybear.menu.TextButton;

import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;

/**
 * 
 * @author Nicolas
 *
 */
public class Editor extends JLayeredPane {
	private JLayeredPane baseLayerEdit, baseLayerStart;
	
	private JTextArea editor;
	private WordSuggestor wordSugg;
	private JTextField headline;
	private JLabel date, author;
	private JScrollPane scrollpane;
	private TextButton export, save, start;

	private Database db;
	
	public static final String EMPTY_TITLE = "<Titel steht hier>";
	public static final String EMPTY_STORY = "<Hier Geschichte schreiben>";
	
	private JList<Story> storyList;
	private DefaultListModel<Story> storyListModel = new DefaultListModel<Story>();

	private JLabel title;

	private JLabel authorStart;

	private JLabel dateStart;

	private JLabel titleStart;

	protected FileFilter filter = new FileNameExtensionFilter("StoryBear (.bear)", "bear");;
	
	/**
	 * 
	 */
	public Editor(){
		db = new Database();
		initializePanel();
		initializeStart();
//		loadStory(1);
	}
	
	/**
	 * load Story in Editor
	 * @param story
	 */
	public void loadStory(int id) {
		Story story = db.getStoryFromDatabase(id);
		loadStory(story);
	}
	public void loadStory(Story story) {
		editor.setText(story.getText());
		headline.setText(story.getTitle());
		author.setText(author.getText() + story.getAuthor().getName());
		date.setText(date.getText() + DateFormat.getDateInstance().format(story.getChangeDate()));
		
		editor.setForeground(Color.black);
		headline.setForeground(Color.black);
		
		scrollpane.setViewportView(editor);
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
	
	private void loadStories() {
		storyListModel.clear();
		List<Story> stories = db.getAllStoriesFromDatabase();
		for(Story s : stories) {
			storyListModel.addElement(s);
		}
		storyList.setModel(storyListModel);
	}
	
	private void initializeStart() {
		baseLayerStart = new JLayeredPane();
		baseLayerStart.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		// Headline
		JTextField headline = new JTextField();
		headline.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(Menu.fontHeadline[0]);
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.setEditable(false);
		headline.setFocusable(false);
		headline.setText("Editor");
		baseLayerStart.add(headline);
		
		// Story-List
		storyList = new JList<Story>(storyListModel);
        storyList.setCellRenderer(new StoryListCellRenderer());
        storyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        storyList.setOpaque(false);
        storyList.setBackground(new Color(0,0,0,0));
        storyList.setFont(Menu.fontText[0]);
        storyList.setForeground(Color.black);
        storyList.setSelectedIndex(storyList.getFirstVisibleIndex());
        storyList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Story s = storyList.getSelectedValue();
				if(s != null) {
					authorStart.setText("Autor: "+s.getAuthor());
					titleStart.setText("Titel: "+s.getAuthor());
					dateStart.setText("Datum: "+DateFormat.getDateInstance().format(s.getChangeDate()));
				}
			}
		});
        JScrollPane scrollpane = new Scrollbar(Ressources.SHELFCOLOR); 
        scrollpane.setViewportView(storyList);
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setOpaque(false);
        scrollpane.setBackground(new Color(0,0,0,0));
        scrollpane.setBorder(null);
        JScrollBar sb = scrollpane.getVerticalScrollBar();
        sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(new Color(0,0,0,0));
        scrollpane.setBounds((int)(750/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(300/Ressources.SCALE), (int)(300/Ressources.SCALE));
        baseLayerStart.add(scrollpane);
		
        // Author
        authorStart = new JLabel();
 		authorStart.setText("Autor: ");
 		authorStart.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
 		authorStart.setBounds((int)(40/Ressources.SCALE),(int)(215/Ressources.SCALE),(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
 		authorStart.setVisible(true);
 		baseLayerStart.add(authorStart);
 		
 		// Date
 		dateStart = new JLabel();
 		dateStart.setText("Datum: ");
 		dateStart.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
 		dateStart.setBounds(40, 260,(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
 		dateStart.setVisible(true);
 		baseLayerStart.add(dateStart);
 		
	 	// Title
		titleStart = new JLabel();
		titleStart.setText("Titel: ");
		titleStart.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
		titleStart.setBounds(40, 174,(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
		titleStart.setVisible(true);
		baseLayerStart.add(titleStart);
        
        // Edit-Button
        TextButton editButton = new TextButton("Bearbeiten", 40, 255, 195, 60);
        editButton.setLocation(816, 453);
        editButton.addMouseListener(new MouseListener() {
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
				loadStory(storyList.getSelectedValue());
				baseLayerStart.setVisible(false);
				baseLayerEdit.setVisible(true);
			}
		});
        baseLayerStart.add(editButton);
        
        // Import-Button
		TextButton importButton = new TextButton("Importieren", 40+195+10, 255, 195, 60);
		importButton.setLocation(40, 379);
		importButton.addMouseListener(new MouseListener() {
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
				choose.addChoosableFileFilter(filter);
				choose.setFileFilter(filter);
				if (choose.showOpenDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
					try {
						FileInputStream f = new FileInputStream(choose.getSelectedFile());
						ObjectInputStream o = new ObjectInputStream(f);
						Story s = (Story) o.readObject();
						db.insertStoryToDatabase(s);
					} catch (IOException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} 
		});
		baseLayerStart.add(importButton);
        
				
		loadStories();
		
        // Frame
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
		add(baseLayerStart);
		baseLayerStart.setVisible(true);
	}
	
	
	/**
	 * initialize all elements
	 */
	private void initializePanel() {		
		// WordSuggestor
		wordSugg = new WordSuggestor();
		wordSugg.setBounds((int)(40/Ressources.SCALE), (int)(335/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(520/Ressources.SCALE));
		wordSugg.setVisible(true);
		
		// Headline
		headline = new JTextField();
		headline.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(FontCache.getInstance().getFont("Standard", (float)(60f/Ressources.SCALE)));
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.addFocusListener(new EmptyTextFieldListener(EMPTY_TITLE, Color.GRAY, Color.BLACK).initializeCallerTextComponent(headline));
		
		JLabel headlineUnderscore = new JLabel(new ImageIcon(Imagelib.getInstance().loadDesignImage("menu_headline_underscore")));
		headlineUnderscore.setBounds((int)(40/Ressources.SCALE), (int)(140/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(70/Ressources.SCALE));
		
		// Scrollpane
		scrollpane = new Scrollbar(Ressources.PAGECOLOR);
		scrollpane.setBounds((int)((1100-350)/Ressources.SCALE), (int)(10/Ressources.SCALE), (int)(600/Ressources.SCALE)+30, (int)(800/Ressources.SCALE));
		scrollpane.setBackground(Ressources.PAGECOLOR);
		scrollpane.setForeground(Ressources.PAGECOLOR);
		scrollpane.setBorder(null);
		
		// Editor
		editor = new JTextArea();
		editor.setBounds((int)((1100-350)/Ressources.SCALE), (int)(10/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(800/Ressources.SCALE));
		editor.setBackground(Ressources.PAGECOLOR);
		editor.setBorder(null);
		editor.setLineWrap(true);
		editor.setWrapStyleWord(true);
		editor.setForeground(Color.black);
		editor.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
		
		
		// Author
		author = new JLabel();
		author.setText("Autor: ");
		author.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
		author.setBounds((int)(40/Ressources.SCALE),(int)(215/Ressources.SCALE),(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
		author.setVisible(true);
		
		// Date
		date = new JLabel();
		date.setText("Datum: ");
		date.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
		date.setBounds((int)(440/Ressources.SCALE),(int)(215/Ressources.SCALE),(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setVisible(true);
		
		// baseLayerEdit
		baseLayerEdit = new JLayeredPane();
		baseLayerEdit.setSize(Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		
		// Buttons
		start = new TextButton("zurück", 40, 265, 195, 60);
		baseLayerEdit.add(start);
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
				loadStories();
				baseLayerEdit.setVisible(false);
				baseLayerStart.setVisible(true);
			}
		});
		
		export = new TextButton("Exportieren", 40+195+10, 265, 195, 60);
		baseLayerEdit.add(export);
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
				choose.addChoosableFileFilter(filter);
				choose.setFileFilter(filter);
				if (choose.showSaveDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
					Story s = new Story();
					s.setAuthor(User.getCurrentUser());
					s.setTitle(title.getText());
					s.setChangeDate(new Date());
					s.setVersion(1);
					s.setText(editor.getText());
					try {
						FileOutputStream fos = new FileOutputStream(choose.getSelectedFile()+".bear");
						ObjectOutputStream o = new ObjectOutputStream(fos);
						o.writeObject(s);
						o.close();
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} 
		});
		
		save = new TextButton("Speichern", 40+2*(195+10), 265, 195, 60);
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
		
		baseLayerEdit.add(headlineUnderscore);
		baseLayerEdit.add(save);
		baseLayerEdit.add(headline);
		baseLayerEdit.add(scrollpane);
		baseLayerEdit.add(wordSugg);
		baseLayerEdit.add(date);
		baseLayerEdit.add(author);
		baseLayerEdit.setVisible(true);
		
		// Frame
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
		add(baseLayerEdit);
		baseLayerEdit.setVisible(false);
	}
	
	private class StoryListCellRenderer extends DefaultListCellRenderer {
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         c.setText(((Story)value).getTitle());
	         c.setBackground(new Color(0,0,0,0));
	         c.setBorder(null);
	         c.setCursor(Ressources.CURSORCLICKABLE);
	         if (isSelected) {
	             c.setForeground(Ressources.MENUCOLORSELECTED);
	         }
	         return c;
	     }
	}
}
