package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.menu.MenuInnerPanel;
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
public class Editor extends MenuInnerPanel {
	public static final String EMPTY_TITLE = "<Titel steht hier>";
	public static final String EMPTY_STORY = "<Hier Geschichte schreiben>";
	public static final FileFilter filter = new FileNameExtensionFilter("StoryBear (.bear)", "bear");
	
	private JLayeredPane baseLayerEditMode, baseLayerStart;
	
	private JTextArea editorEditMode;
	private WordSuggestor wordSuggEditMode;
	private JTextField headlineEditMode;
	private JLabel authorEditMode;
	private JLabel dateEditMode;
	private JScrollPane scrollpaneEditMode;

	private JLabel authorStart, dateStart, titleStart;
	private JList<Story> storyList;
	
	private Database db;
	private DefaultListModel<Story> storyListModel = new DefaultListModel<Story>();
	private Story currentStory;
	private Menu menu;
	private TextButton renderButton;
	private TextButton editButton;
	
	
	/**
	 * start editor
	 */
	public Editor(Menu menu){
		this.menu = menu;
		db = new Database();
		
		initializeEditMode();
		initializeStart();
		
		showStart();
		
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
	}
	
	/**
	 * show StartPanel
	 */
	private void showStart() {
		loadStories();
		
		authorStart.setText("");
		titleStart.setText("");
		dateStart.setText("");
		editButton.setEnabled(false);
		renderButton.setEnabled(false);
		
		baseLayerEditMode.setVisible(false);
		baseLayerStart.setVisible(true);
	}
	
	/**
	 * show editor
	 */
	private void showEditMode() {
		// set values
		editorEditMode.setText(currentStory.getText());
		headlineEditMode.setText(currentStory.getTitle());
		authorEditMode.setText("Autor: " + currentStory.getAuthor().getName());
		dateEditMode.setText("Datum: " + DateFormat.getDateInstance().format(currentStory.getChangeDate()));
		
		editorEditMode.setForeground(Color.black);
		headlineEditMode.setForeground(Color.black);
		
		scrollpaneEditMode.setViewportView(editorEditMode);
		
		wordSuggEditMode.startSuggestions();
		
		// switch panels
		baseLayerStart.setVisible(false);
		baseLayerEditMode.setVisible(true);
	}
	
	/**
	 * update current story
	 */
	private Story updateCurrentStory() {
		currentStory.setTitle(headlineEditMode.getText());
		currentStory.setText(editorEditMode.getText());
		
		currentStory.setChangeDate(new Date());
		currentStory.setVersion(1); // TODO: ?
		return currentStory;
	}
	
	/**
	 * load stories from database in list model
	 */
	private void loadStories() {
		storyListModel.clear();
		List<Story> stories = db.getAllStoriesFromDatabase();
		for(Story s : stories) {
			storyListModel.addElement(s);
		}
		storyList.setModel(storyListModel);
	}
	
	/**
	 * initialize StartScreen
	 */
	private void initializeStart() {
		baseLayerStart = new JLayeredPane();
		baseLayerStart.setBounds(0, 0 ,Menu.innerPanel.width, Menu.innerPanel.height);
		add(baseLayerStart);
		
		baseLayerStart.add(addMenuHeadline("Editor", false));
		baseLayerStart.add(addMenuHeadlineUnderlining(), false);
		
		// Story-List
		storyList = new JList<Story>(storyListModel);
        storyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        storyList.setBackground(Ressources.PAGECOLOR);
        storyList.setForeground(Ressources.PAGECOLOR);
        storyList.setFont(Menu.fontText[0]);
        storyList.setCellRenderer(new StoryListCellRenderer());
        storyList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Story s = storyList.getSelectedValue();
				if(s != null) {
					authorStart.setText("Autor: "+s.getAuthor());
					titleStart.setText(s.getTitle());
					dateStart.setText("Datum: "+DateFormat.getDateInstance().format(s.getChangeDate()));
					editButton.setEnabled(true);
					renderButton.setEnabled(true);
				}
			}
		});
        JScrollPane scrollpane = new Scrollbar(Ressources.PAGECOLOR);
        scrollpane.setBounds(Menu.leftPageX, (int)(140/Ressources.SCALE), Menu.pageWidth, (int)(300/Ressources.SCALE));
        scrollpane.setBackground(Ressources.PAGECOLOR);
        scrollpane.setForeground(Ressources.PAGECOLOR);
        scrollpane.setBorder(null);
        scrollpane.setViewportView(storyList);
        baseLayerStart.add(scrollpane);
		
        // Author
        authorStart = generateStandardLabel();
 		authorStart.setFont(Menu.fontHeadline[3]);
 		authorStart.setBounds(Menu.rightPageX,(int)(180/Ressources.SCALE),Menu.pageWidth,(int)(40/Ressources.SCALE));
 		baseLayerStart.add(authorStart);
 		
 		// Date
 		dateStart = generateStandardLabel();
 		dateStart.setFont(Menu.fontHeadline[3]);
 		dateStart.setBounds(Menu.rightPageX, (int)(210/Ressources.SCALE),Menu.pageWidth,(int)(40/Ressources.SCALE));
 		baseLayerStart.add(dateStart);
 		
	 	// Title
		titleStart = generateStandardLabel();
		titleStart.setFont(Menu.fontHeadline[1]);
		titleStart.setBounds(Menu.rightPageX, (int)(140/Ressources.SCALE),Menu.pageWidth,(int)(40/Ressources.SCALE));
		baseLayerStart.add(titleStart);
        
        // Edit-Button
        editButton = new TextButton("Bearbeiten", Menu.rightPageXUnscaled, 300, 250, 60);
        editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentStory = storyList.getSelectedValue();
				showEditMode();
			}
		});
        baseLayerStart.add(editButton);
        
        // new Story
 		renderButton = new TextButton("Rendern", Menu.rightPageXUnscaled + 250 + 50, 300, 250, 60);
 		renderButton.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent arg0) {
 				currentStory = storyList.getSelectedValue();
 				
 				if (currentStory != null) {
 					menu.loading.setVisible(true);
 					
 					TextAnalyzer analyzer = new TextAnalyzer();
 					analyzer.analyzeText(currentStory);
 					
 					menu.loading.setVisible(false);
 				}
 				
 				showStart();
 			}
 		});
 		baseLayerStart.add(renderButton);
        
        // Import-Button
		TextButton importButton = new TextButton("Importieren", Menu.leftPageXUnscaled, 450, 250, 60);
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				choose.addChoosableFileFilter(filter);
				choose.setFileFilter(filter);
				if (choose.showOpenDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
					try {
						FileInputStream f = new FileInputStream(choose.getSelectedFile());
						ObjectInputStream o = new ObjectInputStream(f);
						Story s = (Story) o.readObject();
						db.insertStoryToDatabase(s);
						f.close();
						o.close();
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				showStart();
			}
		});
		baseLayerStart.add(importButton);
		
		// new Story
		TextButton newButton = new TextButton("Neue Geschichte", Menu.leftPageXUnscaled + 250 + 50, 450, 250, 60);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Story s = new Story();
				s.setTitle("Unbenannte Geschichte");
				s.setAuthor(User.getCurrentUser());
				s.setChangeDate(new Date());
				s.setText("");
				s.setVersion(1);
				
				db.insertStoryToDatabase(s);
				
				showStart();
			}
		});
		baseLayerStart.add(newButton);
	}
	
	
	/**
	 * initialize edit mode
	 */
	private void initializeEditMode() {		
		baseLayerEditMode = new JLayeredPane();
		baseLayerEditMode.setBounds(0, 0 ,Menu.innerPanel.width, Menu.innerPanel.height);
		add(baseLayerEditMode);
		
		// WordSuggestor
		wordSuggEditMode = new WordSuggestor();
		wordSuggEditMode.setBounds((int)(40/Ressources.SCALE), (int)(335/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(520/Ressources.SCALE));
		wordSuggEditMode.setVisible(true);
		
		// Headline
		headlineEditMode = new JTextField();
		headlineEditMode.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headlineEditMode.setFont(Menu.fontHeadline[0]);
		headlineEditMode.setOpaque(false);
		headlineEditMode.setBorder(null);
		headlineEditMode.setVisible(true);
		headlineEditMode.addFocusListener(new EmptyTextFieldListener(EMPTY_TITLE, Color.GRAY, Color.BLACK).initializeCallerTextComponent(headlineEditMode));
		
		JLabel headlineUnderscore = new JLabel(new ImageIcon(Imagelib.getInstance().loadDesignImage("menu_headline_underscore")));
		headlineUnderscore.setBounds((int)(40/Ressources.SCALE), (int)(140/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(70/Ressources.SCALE));
		
		// Scrollpane
		scrollpaneEditMode = new Scrollbar(Ressources.PAGECOLOR);
		scrollpaneEditMode.setBounds((int)((1100-350)/Ressources.SCALE), (int)(10/Ressources.SCALE), (int)(600/Ressources.SCALE)+30, (int)(800/Ressources.SCALE));
		scrollpaneEditMode.setBackground(Ressources.PAGECOLOR);
		scrollpaneEditMode.setForeground(Ressources.PAGECOLOR);
		scrollpaneEditMode.setBorder(null);
		
		// Editor
		editorEditMode = new JTextArea();
		editorEditMode.setBounds((int)((1100-350)/Ressources.SCALE), (int)(10/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(800/Ressources.SCALE));
		editorEditMode.setBackground(Ressources.PAGECOLOR);
		editorEditMode.setBorder(null);
		editorEditMode.setLineWrap(true);
		editorEditMode.setWrapStyleWord(true);
		editorEditMode.setForeground(Color.black);
		editorEditMode.setFont(Menu.fontText[0]);
		
		// Author
		authorEditMode = new JLabel();
		authorEditMode.setText("Autor: ");
		authorEditMode.setFont(Menu.fontText[0]);
		authorEditMode.setBounds((int)(40/Ressources.SCALE),(int)(215/Ressources.SCALE),(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
		authorEditMode.setVisible(true);
		
		// Date
		dateEditMode = new JLabel();
		dateEditMode.setText("Datum: ");
		dateEditMode.setFont(Menu.fontText[0]);
		dateEditMode.setBounds((int)(440/Ressources.SCALE),(int)(215/Ressources.SCALE),(int)(200/Ressources.SCALE),(int)(30/Ressources.SCALE));
		dateEditMode.setHorizontalAlignment(SwingConstants.RIGHT);
		dateEditMode.setVisible(true);
		
		
		// Start
		TextButton start = new TextButton("zurück", 40, 265, 195, 60);
		baseLayerEditMode.add(start);
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
				showStart();
			}
		});
		
		// Export/ Share
		TextButton export = new TextButton("Exportieren", 40+195+10, 265, 195, 60);
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCurrentStory();
				
				JFileChooser choose = new JFileChooser();
				choose.addChoosableFileFilter(filter);
				choose.setFileFilter(filter);
				if (choose.showSaveDialog(Editor.this) == JFileChooser.APPROVE_OPTION) {
					try {
						FileOutputStream fos = new FileOutputStream(choose.getSelectedFile()+".bear");
						ObjectOutputStream o = new ObjectOutputStream(fos);
						o.writeObject(currentStory);
						o.close();
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// Save
		TextButton save = new TextButton("Speichern", 40+2*(195+10), 265, 195, 60);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCurrentStory();
				
				db.updateStory(currentStory);
				showEditMode();
			}
		});
		
		baseLayerEditMode.add(headlineUnderscore);
		baseLayerEditMode.add(save);
		baseLayerEditMode.add(headlineEditMode);
		baseLayerEditMode.add(scrollpaneEditMode);
		baseLayerEditMode.add(wordSuggEditMode);
		baseLayerEditMode.add(dateEditMode);
		baseLayerEditMode.add(authorEditMode);
		baseLayerEditMode.add(export);
	}
	
	/**
	 * Cell Renderer for list of stories
	 */
	private class StoryListCellRenderer extends DefaultListCellRenderer {
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         c.setText(((Story)value).getTitle());
	         c.setBackground(new Color(0,0,0,0));
	         c.setForeground(Color.black);
	         c.setBorder(null);
	         c.setCursor(Ressources.CURSORCLICKABLE);
	         if (isSelected) {
	             c.setForeground(Ressources.MENUCOLORSELECTED);
	         }
	         return c;
	     }
	}
}
