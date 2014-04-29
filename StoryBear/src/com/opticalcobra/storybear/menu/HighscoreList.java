package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import com.opticalcobra.storybear.db.HighscoreResult;
import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.Ressources;

public class HighscoreList extends MenuInnerPanel {
	
	private JList level;
	private JList scores;
	private TextButton selectLevel;
	private DefaultListModel<StoryInfo> modelSI = new DefaultListModel<StoryInfo>();
	private DefaultListModel<HighscoreResult> modelHR = new DefaultListModel<HighscoreResult>();
	
	/**
	 * @author Miriam
	 * on the left book page it shows the levels and on the right the corresponding highscores
	 */
	public HighscoreList(){
		addMenuHeadline("Bestenliste");
		addMenuHeadlineUnderlining();
		
		//right Book Page	
		JLabel head = generateStandardLabel();
		head.setBounds(Menu.rightPageX, (int)(120/Ressources.SCALE), Menu.pageWidth, (int)(80/Ressources.SCALE));
		head.setText("Bestenliste");
		add(head);
		
		//Highscores
		this.scores = new JList<HighscoreResult>();
		this.scores.setCellRenderer(new ScoreListCellRenderer());
		this.scores.setOpaque(false);
		this.scores.setBackground(new Color(0,0,0,0));
		this.scores.setFont(Menu.fontText[0]);
		this.scores.setForeground(Color.black);
		
		JScrollPane scrollpane = new Scrollbar(Ressources.PAGECOLOR);
		scrollpane.setViewportView(this.scores);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.getViewport().setBackground(new Color(0,0,0,0));
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		JScrollBar sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.PAGECOLOR);
        scrollpane.setBounds(Menu.rightPageX, (int)(190/Ressources.SCALE), Menu.pageWidth, (int)(600/Ressources.SCALE));
        add(scrollpane, javax.swing.JLayeredPane.DEFAULT_LAYER);
     
        
        //left Book Page
		//header
        JLabel headline = generateStandardLabel();
		headline.setBounds(Menu.leftPageX, (int)(120/Ressources.SCALE), Menu.pageWidth, (int)(80/Ressources.SCALE));
		headline.setText("Levelauswahl");
		add(headline);
		
		//Choose a level
		this.level = new JList<StoryInfo>();
		this.level.setCellRenderer(new LevelListCellRenderer());
		this.level.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.level.setOpaque(false);
		this.level.setBackground(new Color(0,0,0,0));
		this.level.setFont(Menu.fontText[0]);
		this.level.setForeground(Color.black);
		this.level.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent s) {
				try {
					loadHighscore();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
        });
		this.loadStories();
        
		scrollpane = new Scrollbar(Ressources.PAGECOLOR);
		scrollpane.setViewportView(this.level);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.getViewport().setBackground(new Color(0,0,0,0));
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		scrollpane.setCursor(Ressources.CURSORCLICKABLE);
		sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.PAGECOLOR);
        scrollpane.setBounds(Menu.leftPageX, (int)(190/Ressources.SCALE), Menu.pageWidth, (int)(600/Ressources.SCALE));
        add(scrollpane);
	}
	
	
	/** 
	 * @author Miriam
	 * loads the Highscore table of the corresponding story
	 */
	private void loadHighscore() throws SQLException{	
		int level_id = ((StoryInfo)(this.level.getSelectedValue())).getId();
		ArrayList<HighscoreResult> hr = db.getHighscoreForLevel(level_id);
		Collections.sort(hr, new Comparator<HighscoreResult>() { //sort the arraylist descending
	        @Override
	        public int compare(HighscoreResult  hr1, HighscoreResult  hr2)
	        {
	            return  ((Integer)(hr2.getScore())).compareTo((Integer)(hr1.getScore()));
	        }
	    });
		
		modelHR.clear();
		for(int i=0 ; i<hr.size();i++){
			modelHR.addElement(hr.get(i));
		}      
        this.scores.setModel(modelHR);
	}
	
	private void loadStories() {
		ArrayList<StoryInfo> story = db.getAllStorysFromDatabaseWithIds();
		Collections.sort(story);
		modelSI.clear();
		for(int i=0 ; i<story.size();i++){
			modelSI.addElement(story.get(i));
		}
        
        this.level.setModel(modelSI);		
	}
	

	
	/**
	 * @author Miriam
	 * renders the Story Name and Author in the list of levels
	 */
	private class LevelListCellRenderer extends DefaultListCellRenderer {
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    	 JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);	    	 
	    	 Story story = ((StoryInfo) value).getStory();
	         c.setText(story.getTitle() + " (" + story.getAuthor().getName() + ")");
	         c.setForeground(new Color(0, 0, 0));
	         c.setBackground(Ressources.TRANSPARENTCOLOR);
	         c.setBorder(null);
	         if(isSelected){
	        	 c.setForeground(new Color(186, 15, 15));
	         }
	         
	         return c;
	     }
	}
	
	
	/**
	 * @author Miriam
	 * renders the Story Name and Author in the list of levels
	 */
	private class ScoreListCellRenderer extends DefaultListCellRenderer {
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    	 JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    	 try {
	    		HighscoreResult hr = ((HighscoreResult) value);
				c.setText((index+1) + ") " + db.getUserName(hr.getUser_id()) + " ("+ String.valueOf(hr.getScore()) + ")");
				c.setForeground(Color.black);
				c.setBackground(Ressources.TRANSPARENTCOLOR);
				c.setBorder(null);
				c.setFocusable(false);
			} catch (SQLException e) {}
	         
	         return c;
	     }
	}


	public void showStory(int levelId) {
		for(int i=0; i<modelSI.size(); i++) 
			if(modelSI.get(i).getId() == levelId)
				level.setSelectedIndex(i);
	}
}
