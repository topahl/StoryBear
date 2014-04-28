package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.db.SuggestionWord;
import com.opticalcobra.storybear.exceptions.ImageNotFoundException;
import com.opticalcobra.storybear.menu.Menu;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;


public class WordSuggestor extends JPanel {
	private Database db = new Database();
	private JPanel base;
	
	public WordSuggestor() {
		setOpaque(false);
		setLayout(null);
		
		UIManager.put("ToolTip.background", Ressources.TRANSPARENTCOLOR);
		UIManager.put("ToolTip.background", Ressources.TRANSPARENTCOLOR);
		UIManager.put("ToolTip.border", null);
		UIManager.put("ToolTip.font", FontCache.getInstance().getFont("Fotin_R", 15));
		ToolTipManager.sharedInstance().setInitialDelay(5);
		
		base = new JPanel();
		base.setOpaque(false);
		base.setSize((int)(600/Ressources.SCALE), (int)(520/Ressources.SCALE));
		base.setLayout(null);
		add(base);
	}
	
	public void startSuggestions() {
		new Thread(new Relaoder()).start();
	}
	
	private void loadWords() {
		base.setVisible(false);
		base.removeAll();
		
		List<SuggestionWord> words = db.getRandomSuggestioWord(8);
		
		JLabel strike;
		for(int i=0; i<2; i++)
			for (int j=0; j<4; j++) {
				JLabel sugg;
				try {
					if (j > 0) {
						strike = new JLabel(new ImageIcon(Imagelib.getInstance().loadDesignImage("menu_wordsuggestion_strike")));
						strike.setBounds((int)((j*(120+40)-40)/Ressources.SCALE),(int)(i*250/Ressources.SCALE),(int)(40/Ressources.SCALE),(int)(240/Ressources.SCALE));
						base.add(strike);
					}
					SuggestionWord word = words.get(j+i*4);
					sugg = new Suggestion(Imagelib.getInstance().loadObjectPic(word.getImageId(), "ilb"), word.getWord());
					sugg.setBounds((int)(j*(120+40)/Ressources.SCALE), (int)(i*250/Ressources.SCALE), (int)(120/Ressources.SCALE), (int)(240/Ressources.SCALE));
					sugg.setToolTipText(word.getWord());
					base.add(sugg);
				} catch (ImageNotFoundException e) {
					e.printStackTrace();
				}
			}
		base.setVisible(true);
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
//			setIcon(null);
//			setText(text);
		}

		@Override
		public void mouseExited(MouseEvent e) {
//			setIcon(image);
//			setText(null);
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	private class Relaoder implements Runnable {
		@Override
		public void run() {
			while (isVisible()) {
				loadWords();
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
