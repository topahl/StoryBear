package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.opticalcobra.storybear.debug.Debugger;
import com.opticalcobra.storybear.game.Control;
import com.opticalcobra.storybear.main.OSTimer;
import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;


public class Menu extends JFrame{
	
	private Imagelib il = Imagelib.getInstance();
	
	private JLayeredPane baseLayer;
	private JLayeredPane buecherRegal;
	
	private JList<String> levelBuecher;
	
	public Menu(){

		
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		
		this.setResizable(false);
		this.setUndecorated(true);
		
		initComponents();
		initShelf();
		
		
		this.setVisible(true);
	}

	private void initShelf() {
		baseLayer.add(buecherRegal);
		buecherRegal.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		
		
		
		levelBuecher = new JList<String>();
		JScrollPane scrollpane = new Scrollbar();
		scrollpane.setViewportView(levelBuecher);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.setOpaque(false);
		scrollpane.setBackground(new Color(0,0,0,0));
		scrollpane.setBorder(null);
		JScrollBar sb = scrollpane.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(Ressources.SHELFCOLOR);
        scrollpane.setBounds((int)(96/Ressources.SCALE),(int)(60/Ressources.SCALE), (int)(1152/Ressources.SCALE), (int)(959/Ressources.SCALE));
        buecherRegal.add(scrollpane);
        levelBuecher.setCellRenderer(new BookRenderer());
        levelBuecher.setBorder(null);
        
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        model.addElement("hallo");
        model.addElement("Welt");
        model.addElement("1234");
        model.addElement("fjsei");
        levelBuecher.setModel(model);
		
        
        //Background
        JLabel shelf = new JLabel();
		shelf.setIcon(new ImageIcon(il.MenuImage(Imagelib.MENU_SHELF)));
		shelf.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height);
		buecherRegal.add(shelf);
		
	}

	private void initComponents() {
		baseLayer = new JLayeredPane();
		buecherRegal = new JLayeredPane();
		
		getContentPane().setBackground(Color.BLACK);
		//Letzte Einstellungen zum Fenster
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.baseLayer, javax.swing.GroupLayout.DEFAULT_SIZE,
				Ressources.SCREEN.width, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				this.baseLayer, javax.swing.GroupLayout.DEFAULT_SIZE,
				Ressources.SCREEN.height, Short.MAX_VALUE));
		baseLayer.setBounds(0, 0, Ressources.WINDOW.width, Ressources.WINDOW.height); // TODO : Fenster mittig anzeigen
		add(baseLayer);
		this.pack();

	}
	
}
