package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class BookRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2910580241335354011L;
		private JPanel pane;
		private JLabel user;
		private JLabel title;
		private JLabel userShadow;
		private JLabel titleShadow;
		private JLabel book;
		private FontCache fc = FontCache.getInstance();
		private Imagelib il = Imagelib.getInstance();
		private int [] books = {Imagelib.MENU_BOOK_1,Imagelib.MENU_BOOK_2,Imagelib.MENU_BOOK_3,Imagelib.MENU_BOOK_4};
//		private Color [] colors = {new Color(200,156,7),new Color(249,194,9),new Color(255,255,255), new Color(249,194,9),new Color(249,194,9),new Color(0,0,0)};
		private Color normalColor = new Color(200,156,7);
		private Color selectedColor = new Color(255,255,255);		
		private Color hoverColor = new Color(251,214,87);	
		private Color shadowColor = new Color(0,0,0,0.5f);
		private int titleTop = 40;
		private int userTop = 95;
		private int [] bookLeft = {159,205,123,133};
		private JLabel bg;
	
	
	public BookRenderer(){
		pane= new JPanel();
		user = new JLabel();
		title = new JLabel();
		book = new JLabel();
		bg = new JLabel();
		userShadow = new JLabel();
		titleShadow = new JLabel();
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pane);
		pane.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.book, javax.swing.GroupLayout.DEFAULT_SIZE,
				(int) (1050/Ressources.SCALE), Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.book, javax.swing.GroupLayout.DEFAULT_SIZE,
				(int) (144/Ressources.SCALE), Short.MAX_VALUE));
		
		pane.setSize((int)(1050/Ressources.SCALE), (int)(144/Ressources.SCALE));
		pane.setBackground(new Color(0,0,0,0));
		pane.setOpaque(true);
		pane.add(user);
		pane.add(title);
		pane.add(userShadow);
		pane.add(titleShadow);
		pane.add(bg);
		pane.add(book);
		title.setFont(fc.getFont("Standard",(float) (55/Ressources.SCALE)));
		user.setFont(fc.getFont("Fontin_SC", (float) (28/Ressources.SCALE)));
		titleShadow.setFont(title.getFont());
		userShadow.setFont(user.getFont());
		book.setBounds(0,0,(int) (1050/Ressources.SCALE), (int)(144/Ressources.SCALE));
		bg.setBounds(0,0,(int) (1050/Ressources.SCALE), (int)(144/Ressources.SCALE));
		
		this.setBorder(null);	
	}
	
	
	@Override
	public Component getListCellRendererComponent (
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {
		
		Story story = ((StoryInfo) value).getStory();
		bg.setIcon(new ImageIcon(il.menuImage(books[index%4])));
		bg.setLocation((int) ((bookLeft[index%4]-120)/Ressources.SCALE), 0);
		
		title.setForeground(normalColor);
		title.setText(story.getTitle());
		title.setBounds((int) (bookLeft[index%4]/Ressources.SCALE), (int)(titleTop/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(60/Ressources.SCALE));
		
		titleShadow.setForeground(shadowColor);
		titleShadow.setText(story.getTitle());
		titleShadow.setBounds((int) ((bookLeft[index%4]+3)/Ressources.SCALE), (int)((titleTop+3)/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(60/Ressources.SCALE));
		
		user.setForeground(normalColor);
		user.setText("von "+story.getAuthor().getName());
		user.setBounds((int) (bookLeft[index%4]/Ressources.SCALE), (int)(userTop/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(40/Ressources.SCALE));
		
		userShadow.setForeground(shadowColor);
		userShadow.setText("von "+story.getAuthor().getName());
		userShadow.setBounds((int) ((bookLeft[index%4]+3)/Ressources.SCALE), (int)((userTop+3)/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(40/Ressources.SCALE));
		
		if (selected) {
			title.setForeground(selectedColor);
			user.setForeground(selectedColor);
		}

		return pane;
	}
}
