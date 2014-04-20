package com.opticalcobra.storybear.menu;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.res.FontCache;
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
		private int [] offset = new int[4]; 
		private int bookNumber=-1;
				
	
	
	public BookRenderer(){
		offset[0]=(int) (170/Ressources.SCALE);
		offset[1]=(int) (210/Ressources.SCALE);
		offset[2]=(int) (130/Ressources.SCALE);
		offset[3]=(int) (140/Ressources.SCALE);
		pane= new JPanel();
		user = new JLabel();
		title = new JLabel();
		book = new JLabel();
		
		pane.setSize((int)(1050/Ressources.SCALE), (int)(144/Ressources.SCALE));
		pane.add(user);
		pane.add(title);
		title.setFont(fc.getFont("Standard",(float) (25/Ressources.SCALE)));
		user.setFont(fc.getFont("Fontier_R", (float) (15/Ressources.SCALE)));
	}
	
	
	@Override
	public Component getListCellRendererComponent (
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {
		
		//Story story = (Story) value;
		
		bookNumber++;
		user.setBounds(offset[bookNumber%4], (int)(40/Ressources.SCALE), (int)(300/Ressources.SCALE), (int)(60/Ressources.SCALE));
		user.setText("text");
		title.setBounds(offset[bookNumber%4], (int)(100/Ressources.SCALE), (int)(300/Ressources.SCALE), (int)(40/Ressources.SCALE));
		title.setText("text");

		return pane;
	}
}
