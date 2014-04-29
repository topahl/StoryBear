package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.Ressources;

import javax.swing.JTextField;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserPanel extends MenuInnerPanel {
	public static int startY = 140;
	
	protected List<User> list;
	private JList userlist;
	private TextButton selectUser;
	private JTextField usernameField;
	
	
	public UserPanel(Menu menu) {
		super(menu);
		
		addMenuHeadline("Benutzer");
		addMenuHeadlineUnderlining();
		
		JTextArea textUsername = generateStandardTextArea();
		textUsername.setBounds(Menu.leftPageX, (int)(startY/Ressources.SCALE), Menu.pageWidth, (int)(65/Ressources.SCALE));
		textUsername.setText("Melde dich mit deinem Nutzernamen an.");
        add(textUsername);
        
        userlist = new JList(new UserList());
        userlist.setCellRenderer(new UserListCellRenderer());
        userlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userlist.setFont(Menu.fontText[0]);
        userlist.setForeground(Color.black);
        userlist.setBackground(Ressources.PAGECOLOR);
        userlist.setCursor(Ressources.CURSORCLICKABLE);
        userlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectUser.setEnabled(true);
			}
        });
        
        JScrollPane scrollpane = new Scrollbar(Ressources.PAGECOLOR);
        scrollpane.setBounds(Menu.leftPageX, (int)((startY+60)/Ressources.SCALE), Menu.pageWidth, (int)(540/Ressources.SCALE));
        scrollpane.setBackground(Ressources.PAGECOLOR);
        scrollpane.setForeground(Ressources.PAGECOLOR);
        scrollpane.setBorder(null);
        scrollpane.setViewportView(userlist);
        add(scrollpane);
                
        selectUser = new TextButton("Benutzer wählen", Menu.leftPageXUnscaled, 760, 300, 60);
        selectUser.setEnabled(false);
        selectUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectUser();
			}
		});
        add(selectUser);
        
        textUsername = generateStandardTextArea();
        textUsername.setText("Du hast noch keinen Nutzernamen? Leg dir hier einen neuen an.");
        textUsername.setBounds(Menu.rightPageX, startY, Menu.pageWidth, (int)(100/Ressources.SCALE));
        add(textUsername);
        
        usernameField = generateStandardTextField();
        usernameField.setBounds(Menu.rightPageX, (int)((startY+100)/Ressources.SCALE), (int)(300/Ressources.SCALE), (int)(60/Ressources.SCALE));
        add(usernameField);
        
        TextButton addUser = new TextButton("Benutzer anlegen", Menu.rightPageXUnscaled+(int)(320/Ressources.SCALE), (int)((startY+100)/Ressources.SCALE), 280, 60);
	    addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addUser();
			}
		});
        add(addUser);
        
        
		loadUser();
	}
	
	private void selectUser() {
		User.setCurrentUser((User) userlist.getSelectedValue());
		menu.currentUser.setText(User.isCurrentUserSet() ? User.getCurrentUser().getName() : "");
		menu.enableAllMenuButtons();
		menu.getMain().setVisible(false);
		menu.setMain(menu.highscore);
		menu.getMain().setVisible(true);
	}
	
	private void loadUser() {
		try {
			list = db.queryUserList();
			Collections.sort(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		userlist.setModel(new UserList());
	}
	
	private void addUser() {
		try {
			db.addUser(new User(usernameField.getText()));
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
	         c.setBackground(Ressources.TRANSPARENTCOLOR);
	         c.setBorder(null);
	         c.setVisible(true);
	         
	         if (isSelected) {
	             c.setForeground(Ressources.MENUCOLORSELECTED);
	         }
	         return c;
	     }
	}
}
