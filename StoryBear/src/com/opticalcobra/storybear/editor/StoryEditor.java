package com.opticalcobra.storybear.editor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.opticalcobra.storybear.menu.Scrollbar;
import com.opticalcobra.storybear.res.FontCache;
import com.opticalcobra.storybear.res.Ressources;

public class StoryEditor extends JPanel {
	private JTextArea editor;
	
	public StoryEditor() {
		editor = new JTextArea();
		editor.setBounds(0,0,(int)(600/Ressources.SCALE), (int)(800/Ressources.SCALE));
		editor.setFont(FontCache.getInstance().getFont("Standard", (float)(28f/Ressources.SCALE)));
		editor.setVisible(true);
		editor.setOpaque(false);
		editor.setLineWrap(true);
		editor.setWrapStyleWord(true);
		editor.setForeground(Color.black);
		editor.addFocusListener(new EmptyTextFieldListener(Editor.EMPTY_STORY, Color.GRAY, Color.BLACK).initializeCallerTextComponent(editor));
		
		// TODO: ScrollBar funktionstüchtig machen
		Scrollbar sb = new Scrollbar(Ressources.PAGECOLOR);
		sb.setViewportView(editor);
		sb.getViewport().setOpaque(false);
		sb.getViewport().setBackground(new Color(0,0,0,0));
		sb.setOpaque(false);
		sb.setBackground(new Color(0,0,0,0));
		sb.setBorder(null);
		JScrollBar bar = sb.getVerticalScrollBar();
		bar.setPreferredSize(new Dimension(30,0));
		bar.setBackground(Ressources.SHELFCOLOR);
		sb.setBounds((int)(96/Ressources.SCALE),(int)(60/Ressources.SCALE), (int)(1152/Ressources.SCALE), (int)(959/Ressources.SCALE));
		
		add(sb);
		add(editor);
		
		setOpaque(false);
	}
	
	public String getText() {
		return editor.getText();
	}
	
	public void setText(String text) {
		editor.setText(text);
	}
}
