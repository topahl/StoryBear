package com.opticalcobra.storybear.menu;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.SwingConstants;

public class LoadingPanel extends JDialog {
	JProgressBar bar;
	
	public LoadingPanel(String text) {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0.6f));
		getContentPane().setLayout(null);
		setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
//		setAlwaysOnTop(true);
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModal(true);
		
		JLabel textLade = new JLabel(text);
		textLade.setHorizontalAlignment(SwingConstants.CENTER);
		textLade.setFont(FontCache.getInstance().getFont("Fontin_R", 45f));
		textLade.setForeground(Color.white);
		textLade.setBounds((Ressources.WINDOW.width-200)/2-100, (Ressources.WINDOW.height-100)/2-50, 400, 100);
		getContentPane().add(textLade);
		
		bar = new JProgressBar(0,100);
		bar.setBackground(Color.black);
		bar.setForeground(Ressources.PAGECOLOR);
		bar.setBounds((Ressources.WINDOW.width-400)/2-200, (Ressources.WINDOW.height-20)/2-10, 800, 40);
		getContentPane().add(bar);
	}
	
	public void setMaximum(int max) {
		bar.setMaximum(max);
	}
	public void setValue(int v) {
		bar.setValue(v);
	}
	
	public void update() {
		bar.setValue(bar.getValue()+1);
	}
	
	public void setVisible(boolean b) {
		bar.setValue(0);
		super.setVisible(b);
	}
}
