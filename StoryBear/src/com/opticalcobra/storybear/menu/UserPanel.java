package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

import javax.swing.JTextField;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserPanel extends JLayeredPane {
	protected List<User> list;
	
	private JTextField textField;
	private JList benutzerliste;
	private TextButton selectUser;
	
	private Menu menu;
	
	private Database db = new Database();
	
	public UserPanel(Menu menu) {
		this.menu = menu;
		
		JTextArea beschreibung = new JTextArea();
        beschreibung.setLineWrap(true);
        beschreibung.setText("Melde dich mit deinem Nutzernamen an.");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setFocusCycleRoot(true);
        beschreibung.setCursor(Ressources.CURSORNORMAL);
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontHeadline[1]);
        beschreibung.setForeground(Color.black);
        beschreibung.setBounds((int)(30/Ressources.SCALE),(int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(89/Ressources.SCALE));
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        benutzerliste = new JList(new UserList());
        benutzerliste.setCellRenderer(new UserListCellRenderer());
        benutzerliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        benutzerliste.setOpaque(false);
        benutzerliste.setBackground(new Color(0,0,0,0));
        benutzerliste.setFont(Menu.fontText[0]);
        benutzerliste.setForeground(Color.black);
        benutzerliste.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectUser.setEnabled(true);
			}
        });
        JScrollPane scrollpane = new Scrollbar(); 
        scrollpane.setViewportView(benutzerliste);
        scrollpane.getViewport().setOpaque(false);
        scrollpane.setOpaque(false);
        scrollpane.setBackground(new Color(0,0,0,0));
        scrollpane.setBorder(null);
        JScrollBar sb = scrollpane.getVerticalScrollBar();
        sb.setPreferredSize(new Dimension(30,0));
        sb.setBackground(new Color(0,0,0,0));
        scrollpane.setBounds((int)(31/Ressources.SCALE), (int)(125/Ressources.SCALE), (int)(315/Ressources.SCALE), (int)(315/Ressources.SCALE));
        add(scrollpane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        textField = new JTextField();
        textField.setFont(Menu.fontText[0]);
        textField.setForeground(Color.black);
        textField.setOpaque(false);
        textField.setForeground(Color.black);
        textField.setBounds((int)(747/Ressources.SCALE), (int)(125/Ressources.SCALE), (int)(318/Ressources.SCALE), (int)(60/Ressources.SCALE));
        textField.setCaretColor(Color.black);
        textField.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        add(textField);
                
        selectUser = new TextButton("Benutzer wählen", 45, 150, 250, 50);
        selectUser.setSize(315, 50);
        selectUser.setLocation(31, 470);
        selectUser.setEnabled(false);
        selectUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectUser();
			}
		});
        add(selectUser, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        beschreibung = new JTextArea();
        beschreibung.setLineWrap(true);
        beschreibung.setText("Du hast noch keinen Nutzernamen? Leg dir hier einen neuen an.");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setFocusCycleRoot(true);
        beschreibung.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontHeadline[1]);
        beschreibung.setForeground(Color.black);
        beschreibung.setBounds((int)(750/Ressources.SCALE), (int)(25/Ressources.SCALE), (int)(600/Ressources.SCALE), (int)(89/Ressources.SCALE));
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        TextButton anlegen = new TextButton("Benutzer anlegen", 450, 150, 250, 50);
	    anlegen.setSize((int)(318/Ressources.SCALE), (int)(50/Ressources.SCALE));
	    anlegen.setLocation((int)(747/Ressources.SCALE), (int)(196/Ressources.SCALE));
	    anlegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addUser();
			}
		});
        add(anlegen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        
		loadUser();
		
		setBounds(0, 0, Menu.innerPanel.width, Menu.innerPanel.height);
	}
	
	private void selectUser() {
		User.setCurrentUser((User) benutzerliste.getSelectedValue());
		menu.currentUser.setText(User.isCurrentUserSet() ? User.getCurrentUser().getName() : "");
		menu.enableAllMenuButtons();
		menu.getMain().setVisible(false);
		menu.setMain(menu.highscore);
		menu.getMain().setVisible(true);
	}
	
	private void loadUser() {
		try {
			list = db.queryUserList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		benutzerliste.setModel(new UserList());
	}
	
	private void addUser() {
		try {
			db.addUser(new User(textField.getText()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadUser();
	}
	
	private class UserList implements ListModel {
		@Override
		public void addListDataListener(ListDataListener l) {
		}

		@Override
		public Object getElementAt(int index) {
			return list.get(index);
		}

		@Override
		public int getSize() {
			return list.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
		}
	}
	
	private class UserListCellRenderer extends DefaultListCellRenderer {
	     @Override
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         JComponent c = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         c.setBackground(new Color(0,0,0,0));
	         c.setBorder(null);
	         c.setCursor(Ressources.CURSORCLICKABLE);
	         if (isSelected) {
	             c.setForeground(Ressources.MENUCOLORSELECTED);
	         }
	         return c;
	     }
	}
}
