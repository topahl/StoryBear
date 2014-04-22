package com.opticalcobra.storybear.editor;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.opticalcobra.storybear.exceptions.ImageNotFoundException;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;


public class WordSuggestor extends JPanel {

	public WordSuggestor() {
		setOpaque(false);
		setLayout(null);
	
		for(int i=0; i<2; i++)
			for (int j=0; j<4; j++) {
				JLabel sugg;
				try {
					//TODO: load random image instead of hero pic (#22)
					sugg = new Suggestion(Imagelib.getInstance().loadHeroPic('n', 'b'), "Bär");
					sugg.setBounds((int)(j*(120+40)/Ressources.SCALE), (int)(i*250/Ressources.SCALE), (int)(120/Ressources.SCALE), (int)(240/Ressources.SCALE));
					add(sugg);
				} catch (ImageNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
	}
	
	private class Suggestion extends JLabel implements MouseListener {
		ImageIcon image;
		String text;
		
		public Suggestion(BufferedImage image, String text) {
			this.image = new ImageIcon(image);
			this.text = text;
			setIcon(this.image);
			addMouseListener(this);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			setIcon(null);
			setText(text);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setIcon(image);
			setText(null);
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}
