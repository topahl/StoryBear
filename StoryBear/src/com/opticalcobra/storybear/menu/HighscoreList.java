package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.Ressources;

public class HighscoreList extends JLayeredPane {
	
	private ArrayList<StoryInfo> list;
	private JList level;
	private TextButton selectLevel;
	private Database db = new Database();
	private DefaultListModel<StoryInfo> model = new DefaultListModel<StoryInfo>();
	
	
	/*
	 * @author Miriam
	 * on the left book page it shows the levels and on the right the corresponding highscores
	 */
	public HighscoreList(){

		//right Book Page
		JTextField headline = new JTextField();
		headline.setBounds((int)(750/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(Menu.fontHeadline[0]);
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.setEditable(false);
		headline.setFocusable(false);
		headline.setText("Bestenliste");
		add(headline, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
        
        
        //left Book Page
        headline = new JTextField();
		headline.setBounds((int)(40/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(80/Ressources.SCALE));
		headline.setFont(Menu.fontHeadline[0]);
		headline.setOpaque(false);
		headline.setBorder(null);
		headline.setVisible(true);
		headline.setEditable(false);
		headline.setFocusable(false);
		headline.setText("Levelauswahl");
		add(headline, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		
		this.level = new JList<StoryInfo>();
		this.level.setCellRenderer(new LevelListCellRenderer());
		this.level.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.level.setOpaque(false);
		this.level.setBackground(new Color(0,0,0,0));
		this.level.setFont(Menu.fontText[0]);
		this.level.setForeground(Color.black);
		this.level.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectLevel.setEnabled(true);
			}
        });
		this.loadStories();
        
		JScrollPane scrollpane = new Scrollbar(Ressources.SHELFCOLOR);
		scrollpane.setViewportView(this.level);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.getViewport().setBackground(new Color(0,0,0,0));
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		JScrollBar sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.SHELFCOLOR);
        scrollpane.setBounds((int)(31/Ressources.SCALE), (int)(125/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(315/Ressources.SCALE));
        add(scrollpane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        this.selectLevel = new TextButton("Level wählen", 45, 150, 250, 50);
        this.selectLevel.setSize(315, 50);
        this.selectLevel.setLocation(31, 470);
        this.selectLevel.setEnabled(false);
        this.selectLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadHighscore();
			}
		});
        add(this.selectLevel, javax.swing.JLayeredPane.DEFAULT_LAYER);

		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
	}
	
	
	private void loadHighscore(){
		
	}
	
	private void loadStories() {
		ArrayList<StoryInfo> story = db.getAllLevelssFromDatabase();
		model.clear();
		for(int i=0 ; i<story.size();i++){
			model.addElement(story.get(i));
		}
        
        this.level.setModel(model);		
	}
	

	
	/*
	 * @author Miriam
	 * renders the Story Name and Author in the list of levels
	 */
	private class LevelListCellRenderer extends DefaultListCellRenderer {
		private JLabel title;
		private JLabel user;
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         //JComponent c = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         JPanel c = new JPanel();
	    	 c.setBackground(new Color(0,0,0,0));
	         c.setBorder(null);
	         c.setCursor(Ressources.CURSORCLICKABLE);
	         if (isSelected) {
	             c.setForeground(Ressources.MENUCOLORSELECTED);
	         }
	         
	         Story story = ((StoryInfo) value).getStory();
	         user = new JLabel();
	         user.setForeground(new Color(92, 90, 90));
	 		 title = new JLabel();
	 		 title.setText(story.getTitle());
	 		 user.setText("   von "+story.getAuthor().getName());
	 		 c.add(title);
	 		 c.add(user);
	 		 
	 		 if(isSelected){
	 			title.setForeground(new Color(186, 15, 15));
				user.setForeground(new Color(186, 15, 15));
	 		 }
	         
	         return c;
	     }
	}
	

}
