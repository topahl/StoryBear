package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
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

public class BookBox extends JLayeredPane {
	public static final int postionNormal = (int)((1920-80)/Ressources.SCALE);
	public static final int postionAvailable = (int)((1920-1600)/Ressources.SCALE);
	
	private boolean available = false;
	
	private JLayeredPane baseLayer;
	private JLayeredPane buecherRegal;
	private MouseListener mouseAreaListener;
	private JLabel arrow;
	private JLabel mouseArea;
	private JList<StoryInfo> levelBuecher;
	private DefaultListModel<StoryInfo> model = new DefaultListModel<StoryInfo>();
	
	private Imagelib imagelib = Imagelib.getInstance();
	private Database db = new Database();
	
	private Menu menu;
	
	public BookBox(Menu menu) {
		this.menu = menu;		
		
		baseLayer = new JLayeredPane();
		
		setLocation(postionNormal, 0);
		setSize((int)(1600/Ressources.SCALE),(int)(1080/Ressources.SCALE));
		
		arrow = new JLabel(new ImageIcon(imagelib.loadDesignImage("menu_arrow_left")));
		arrow.setBounds((int)(0/Ressources.SCALE), (int)(20/Ressources.SCALE), (int)(60/Ressources.SCALE), (int)(60/Ressources.SCALE));
		add(arrow);
		
		// right shelf
		initializeRightShelf();
		
		// left shelf
		initShelf();
		
		// MouseIn-Out-Area
		mouseAreaListener = new MouseListener() {
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
		};
		mouseArea = new JLabel();
		mouseArea.setBounds(0,0,(int)(100/Ressources.SCALE),(int)(1080/Ressources.SCALE));
		add(mouseArea);
		
		// set standards
		levelBuecher.setSelectedIndex(0);
	}
	
	public void startGame() {
		@SuppressWarnings("unused")
		Window gui = new Window(levelBuecher.getSelectedValue().getId(), HeroButton.getType());
		menu.dispose();
	}

	public void disable() {
		mouseArea.removeMouseListener(mouseAreaListener); 
		mouseArea.setEnabled(false);
	}
	
	public void enable() {
		mouseArea.addMouseListener(mouseAreaListener);
		mouseArea.setEnabled(true);
	}
	
	public void makeAvailable() {
		setLocation(postionAvailable, 0);
		available = true;
		menu.getMain().setVisible(false);
		arrow.setIcon(new ImageIcon(imagelib.loadDesignImage("menu_arrow_right")));
		loadStories();
	}
	
	public void hide() {
		setLocation(postionNormal, 0);
		available = false;
		menu.getMain().setVisible(true);
		arrow.setIcon(new ImageIcon(imagelib.loadDesignImage("menu_arrow_left")));
	}
	
	public void hoverIn() {
		setCursor(Ressources.CURSORCLICKABLE);
		setLocation(((available) ? postionAvailable+(int)(15/Ressources.SCALE) : postionNormal-(int)(15/Ressources.SCALE)),0);
	}
	
	public void hoverOut() {
		setCursor(Ressources.CURSORNORMAL);
		setLocation(((available) ? postionAvailable : postionNormal),0);
	}

	private void initializeRightShelf() {
		JLabel chooseHeadline = new JLabel("Spieler-");
		chooseHeadline.setBounds((int)(1305/Ressources.SCALE), (int)(0/Ressources.SCALE), (int)(250/Ressources.SCALE), (int)(200/Ressources.SCALE));
		chooseHeadline.setFont(FontCache.getInstance().getFont("Fontin_SC", 45));
		chooseHeadline.setForeground(Color.white);
		baseLayer.add(chooseHeadline);
		chooseHeadline = new JLabel("auswahl");
		chooseHeadline.setBounds((int)(1305/Ressources.SCALE), (int)(45/Ressources.SCALE), (int)(250/Ressources.SCALE), (int)(200/Ressources.SCALE));
		chooseHeadline.setFont(FontCache.getInstance().getFont("Fontin_SC", 45));
		chooseHeadline.setForeground(Color.white);
		baseLayer.add(chooseHeadline);
		
		
		HeroButton[] heroButtons = new HeroButton[3];
		heroButtons[0] = new HeroButton('b', imagelib.loadDesignImage("hero_bear_normal"),imagelib.loadDesignImage("hero_bear_hover"),imagelib.loadDesignImage("hero_bear_selected"));
		heroButtons[1] = new HeroButton('f', imagelib.loadDesignImage("hero_fairy_normal"),imagelib.loadDesignImage("hero_fairy_hover"),imagelib.loadDesignImage("hero_fairy_selected"));
		heroButtons[2] = new HeroButton('p', imagelib.loadDesignImage("hero_prince_normal"),imagelib.loadDesignImage("hero_prince_hover"),imagelib.loadDesignImage("hero_prince_selected"));
		
		int buttonCnt = 0;
		for (HeroButton button : heroButtons) {
			button.setLocation((int)(1305/Ressources.SCALE),(int)((170+buttonCnt*215)/Ressources.SCALE));
			baseLayer.add(button);
			buttonCnt++;
		}
			
		TextButton start = new TextButton("Spielen", 1305, 860, 215, 130, 35, Color.white);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
				
			}
		});
		baseLayer.add(start);
	}
	
	/**
	 * @author Tobias
	 */
	private void initShelf() {
		buecherRegal = new JLayeredPane();
		
		buecherRegal.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		levelBuecher = new JList<StoryInfo>();
		JScrollPane scrollpane = new Scrollbar(Ressources.SHELFCOLOR);
		scrollpane.setViewportView(levelBuecher);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.getViewport().setBackground(new Color(0,0,0,0));
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		scrollpane.setCursor(Ressources.CURSORCLICKABLE);
		JScrollBar sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.SHELFCOLOR);
        scrollpane.setBounds((int)(96/Ressources.SCALE),(int)(60/Ressources.SCALE), (int)(1138/Ressources.SCALE), (int)(959/Ressources.SCALE));
        buecherRegal.add(scrollpane);
        levelBuecher.setCellRenderer(new BookRenderer());
        levelBuecher.setOpaque(false);
        levelBuecher.setBackground(new Color(0,0,0,0));
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
		ArrayList<StoryInfo> story = db.getAllLevelssFromDatabase();
		Collections.sort(story);
		model.clear();
		for(int i=0 ; i<story.size();i++){
			model.addElement(story.get(i));
		}
        
        levelBuecher.setModel(model);
		
	}
}
