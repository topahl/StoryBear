package com.opticalcobra.storybear.res;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * is Singleton
 * handles and caches all uses Fonts
 * @author Nicolas
 */
public class FontCache {
	private static FontCache instance;

	private Map<String, Font> fonts;
	
	/**
	 * load standard fonts
	 */
	private FontCache(){
		fonts = new HashMap<String, Font>();
		fonts.put("Standard", loadFont("Kingthings.ttf"));
	}
	
	/**
	 * load font from font folder
	 * @param name name of font file
	 * @return loaded font
	 */
	private Font loadFont(String name) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Ressources.RESPATH+"fonts\\"+name));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get font by name
	 * @param name
	 * @return
	 */
	public Font getFont(String name) {
		Font font = fonts.get(name);
		
		if(font == null) {
			font = new Font(name, Font.TYPE1_FONT, 1);
			if(font != null)
				fonts.put(name, font);
			return font;
		} else
			return font;
		
	}
	
	/**
	 * get font by name
	 * @param name
	 * @param size relative size
	 * @return
	 */
	public Font getFont(String name, float size) {
		//TODO: cache
		return getFont(name).deriveFont((float) (size/Ressources.SCALE));
	}
	
	/**
	 * Singeltone
	 * @return instance of FontCache
	 */
	public static FontCache getInstance() {
		instance = (instance == null) ? new FontCache() : instance;
		return instance;
	}
	
	class FontInfo {
		float size;
		String name;
		public FontInfo(String name, float size) {
			this.name = name;
			this.size = size;
		}
		public boolean equals(Object obj) {
			FontInfo info = (FontInfo) obj;
			if (name.equals(info.name) && size == info.size)
				return true;
			return false;
		}
	}
}
