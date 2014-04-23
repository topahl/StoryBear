package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
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

import javax.swing.JTextField;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserPanel extends JLayeredPane {
	protected List<User> list;
	
	private JTextField textField;
	private JList benutzerliste;
	private TextButton selectUser;
	
	private Database db = new Database();
	
	public UserPanel() {
		JTextArea beschreibung = new JTextArea();
        beschreibung.setLineWrap(true);
        beschreibung.setText("Melden Sie sich mit Ihrem Nutzernamen an.");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setFocusCycleRoot(true);
        beschreibung.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontHeadline[1]);
        beschreibung.setForeground(Color.black);
        beschreibung.setBounds(30,25, 600, 89);
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        benutzerliste = new JList(new UserList());
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
        scrollpane.setBounds(31, 125, 315, 315);
        add(scrollpane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        textField = new JTextField();
        textField.setFont(Menu.fontText[0]);
        textField.setForeground(Color.black);
        textField.setOpaque(false);
        textField.setForeground(Color.black);
        textField.setBounds(747, 125, 318, 60);
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
        beschreibung.setText("Sie haben noch keinen Nutzernamen? Legen Sie hier einen neuen an.");
        beschreibung.setWrapStyleWord(true);
        beschreibung.setFocusCycleRoot(true);
        beschreibung.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        beschreibung.setFocusable(false);
        beschreibung.setOpaque(false);
        beschreibung.setFont(Menu.fontHeadline[1]);
        beschreibung.setForeground(Color.black);
        beschreibung.setBounds(750,25, 600, 89);
        add(beschreibung, javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        TextButton anlegen = new TextButton("Benutzer anlegen", 450, 150, 250, 50);
	    anlegen.setSize(318, 50);
	    anlegen.setLocation(747, 196);
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
		User u = (User) benutzerliste.getSelectedValue();
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
	
}
