package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.game.Window;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class BookBox extends JLayeredPane implements ListSelectionListener {
	public static final int postionNormal = (int)((1920-80)/Ressources.SCALE);
	public static final int postionAvailable = (int)((1920-1600)/Ressources.SCALE);
	
	private boolean available = false;
	private boolean hover = false;
	
	private JLayeredPane baseLayer;
	private JLayeredPane buecherRegal;
	private JList<StoryInfo> levelBuecher;
	private DefaultListModel<StoryInfo> model = new DefaultListModel<StoryInfo>();
	
	private Imagelib imagelib = Imagelib.getInstance();
	private Database db = new Database();
	
	private Menu menu;
	
	public BookBox(Menu menu) {
		this.menu = menu;
		
//		// TODO: Dummy-data
//		List<StoryInfo> stories = new ArrayList<StoryInfo>();
//		StoryInfo s = new StoryInfo();
//		Story st = new Story();
//		st.setTitle("Test 123");
//		st.setAuthor(new User("Hans"));
//		s.setStory(st);
//		stories.add(s);
//		s = new StoryInfo();
//		st = new Story();
//		st.setTitle("Das Märchen");
//		st.setAuthor(new User("Peter"));
//		s.setStory(st);
//		stories.add(s);
//		// Dummy-data END
		
		
		setLocation(postionNormal, 0);
		setSize((int)(1600/Ressources.SCALE),(int)(1080/Ressources.SCALE));
		
		// Books
//		JScrollPane bookList = new JScrollPane();
//		int bookCouter = 0;
//		for(StoryInfo info : stories) {
//			JComponent book = createBook(info);
//			book.setLocation((new Random().nextInt(80)), bookCouter*145+65);
//			bookList.add(book);
//			bookCouter++;
//		}
//		bookList.setBounds(60, 60, 960, 960);
//		add(bookList);
////		bookList.setViewportView(persHighscoreList);
//		bookList.getViewport().setOpaque(false);
//		bookList.setOpaque(false);
//		bookList.setBackground(new Color(0,0,0,0));
//		bookList.setBorder(null);
		
		initShelf();
		
//		// Background
//		JLabel boxImage = new JLabel(new ImageIcon(Imagelib.getInstance().loadDesignImage("menu_box_bg")));
//		boxImage.setVisible(true);
//		boxImage.setSize(1600, 1080);
//		add(boxImage);
		
		// MouseIn-Out-Area
		JLabel mouseArea = new JLabel();
		mouseArea.setBounds(0,0,(int)(100/Ressources.SCALE),(int)(1080/Ressources.SCALE));
		mouseArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {
				hoverOut();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				hoverIn();
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				hoverOut();
				if(available)
					hide();
				else
					makeAvailable();
			}
		});
		add(mouseArea);
	}
	
	public void makeAvailable() {
		setLocation(postionAvailable, 0);
		available = true;
		menu.getMain().setVisible(false);
	}
	
	public void hide() {
		setLocation(postionNormal, 0);
		available = false;
		menu.getMain().setVisible(true);
	}
	
	public void hoverIn() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setLocation(((available) ? postionAvailable+(int)(15/Ressources.SCALE) : postionNormal-(int)(15/Ressources.SCALE)),0);
		
	}
	
	public void hoverOut() {
		setCursor(Cursor.getDefaultCursor());
		setLocation(((available) ? postionAvailable : postionNormal),0);
	}
	
//	private JComponent createBook(StoryInfo storyInfo) {
//		JLayeredPane book = new JLayeredPane();
//		
//		JLabel title = new JLabel(storyInfo.getStory().getTitle());
//		title.setBounds(130, 10, 700, 100);
//		title.setForeground(Color.black);
//		title.setFont(FontCache.getInstance().getFont("Standard", 30f));
//		book.add(title);
//		
////		JLabel author = new JLabel(storyInfo.getStory().getAuthor().getName());
//		JLabel author = new JLabel("Peter");
//		author.setForeground(Color.black);
//		author.setBounds(145, 80, 700, 20);
//		author.setFont(FontCache.getInstance().getFont("Fontin_R", 20f));
//		book.add(author);
//		
//		
//		String[] imageNames = {"menu_box_book_red", "menu_box_book_blue", "menu_box_book_green", "menu_box_book_brown"};
//		
//		JLabel image = new JLabel(new ImageIcon(Imagelib.getInstance().loadDesignImage(imageNames[(new Random().nextInt(3))])));
//		image.setBounds(0,0,972,150);
//		book.add(image);
//		
//		book.setBounds(0,0,972,150);
//		
//		book.setVisible(true);
//		return book;
//	}
	
	/**
	 * @author Tobias
	 */
	private void initShelf() {
		baseLayer = new JLayeredPane();
		buecherRegal = new JLayeredPane();
		
		buecherRegal.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		levelBuecher = new JList<StoryInfo>();
		JScrollPane scrollpane = new Scrollbar();
		scrollpane.setViewportView(levelBuecher);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.getViewport().setBackground(new Color(0,0,0,0));
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		JScrollBar sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.SHELFCOLOR);
        scrollpane.setBounds((int)(96/Ressources.SCALE),(int)(60/Ressources.SCALE), (int)(1152/Ressources.SCALE), (int)(959/Ressources.SCALE));
        buecherRegal.add(scrollpane);
        levelBuecher.setCellRenderer(new BookRenderer());
        buecherRegal.setOpaque(false);
        buecherRegal.setBackground(new Color(0,0,0,0));
        levelBuecher.setOpaque(false);
        levelBuecher.setBackground(new Color(0,0,0,0));
        levelBuecher.addListSelectionListener(this);
        baseLayer.add(buecherRegal);
        loadStories();
        
        JLabel shelf = new JLabel();
		shelf.setIcon(new ImageIcon(imagelib.menuImage(Imagelib.MENU_SHELF)));
		shelf.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		buecherRegal.add(shelf);
		
		baseLayer.setVisible(true);
		baseLayer.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		add(baseLayer);
	}
	
	/**
	 * @author Tobias
	 */
	private void loadStories() {
		ArrayList<StoryInfo> story = db.getAllStorysFromDatabase();
		model.clear();
		for(int i=0 ; i<story.size();i++){
			model.addElement(story.get(i));
		}
        
        levelBuecher.setModel(model);
		
	}
	
	/**
	 * @author Tobias
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unused")
		Window gui = new Window(e.getFirstIndex());
		menu.dispose();
	}
}
