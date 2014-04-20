package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.opticalcobra.storybear.editor.Story;
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
		private JLabel book;
		private FontCache fc = FontCache.getInstance();
		private Imagelib il = Imagelib.getInstance();
		private int [] offset = new int[4]; 
		private int [] books = {Imagelib.MENU_BOOK_1,Imagelib.MENU_BOOK_2,Imagelib.MENU_BOOK_3,Imagelib.MENU_BOOK_4};
		private Color [] colors = {new Color(200,156,7),new Color(249,194,9),new Color(255,255,255), new Color(249,194,9),new Color(249,194,9),new Color(0,0,0)};
				
	
	
	public BookRenderer(){
		offset[0]=(int) (170/Ressources.SCALE);
		offset[1]=(int) (210/Ressources.SCALE);
		offset[2]=(int) (130/Ressources.SCALE);
		offset[3]=(int) (140/Ressources.SCALE);
		pane= new JPanel();
		user = new JLabel();
		title = new JLabel();
		book = new JLabel();
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
		pane.add(book);
		title.setFont(fc.getFont("Standard",(float) (60/Ressources.SCALE)));
		user.setFont(fc.getFont("Fontier_SC", (float) (25/Ressources.SCALE)));
		book.setBounds(0,0,(int) (1050/Ressources.SCALE), (int)(144/Ressources.SCALE));
		this.setBorder(null);
		
	}
	
	
	@Override
	public Component getListCellRendererComponent (
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {
		
		Story story = (Story) value;
		book.setIcon(new ImageIcon(il.MenuImage(books[index%4])));
		
		title.setForeground(colors[index%colors.length]);
		title.setText(story.getTitle());
		title.setBounds(offset[index%4], (int)(20/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(60/Ressources.SCALE));
		
		user.setForeground(colors[index%colors.length]);
		user.setText("von "+story.getAuthor().getName());
		user.setBounds(offset[index%4], (int)(80/Ressources.SCALE), (int)(700/Ressources.SCALE), (int)(40/Ressources.SCALE));
		

		return pane;
	}
}
