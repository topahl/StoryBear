package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.metal.MetalScrollBarUI;

import com.opticalcobra.storybear.res.Imagelib;
import com.opticalcobra.storybear.res.Ressources;

public class Scrollbar extends JScrollPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6473015188076754224L;

	public Scrollbar(){
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar sb = this.getVerticalScrollBar();
	    sb.setUI(new ScrollbarUI());
		
	}
	static class ScrollbarUI extends MetalScrollBarUI {
	    private JButton up,down;
	    private Imagelib il= Imagelib.getInstance();
	    
	    ScrollbarUI() {
	        
			up = new JButton();
			up.setIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_UP)));
			up.setRolloverIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_UP)));
			up.setPressedIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_UP)));
			up.setPreferredSize(new Dimension((int)(30/Ressources.SCALE),(int)(20/Ressources.SCALE)));
			up.setBorder(null);
	        up.setBorderPainted(false);
	        up.setContentAreaFilled(false);
	        
			down = new JButton();
			down.setIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_DOWN)));
			down.setRolloverIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_DOWN)));
			down.setPressedIcon(new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_DOWN)));
			BufferedImage imageDown = new BufferedImage((int)(60/Ressources.SCALE), (int)(40/Ressources.SCALE), BufferedImage.TYPE_INT_ARGB);
			imageDown.getGraphics().drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_TOP), (int)(15/Ressources.SCALE), 0, null);
			down.setPreferredSize(new Dimension((int)(30/Ressources.SCALE),(int)(20/Ressources.SCALE)));
			down.setBorder(null);
	        down.setBorderPainted(false);
	        down.setContentAreaFilled(false);
			
	    }
	 
	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
	        
	        int thumb = (int) (thumbBounds.height - (30/Ressources.SCALE));
	        Image imageThumb;
	        
	        if(thumb<0){
	        	imageThumb = new BufferedImage(thumbBounds.width, (int)(30/Ressources.SCALE), BufferedImage.TYPE_INT_ARGB);
	        	Graphics g2 = imageThumb.getGraphics();
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_TOP),0,0, null);
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_BOTTOM),0,(int)(15/Ressources.SCALE),null);
		        
		        g.drawImage(imageThumb, thumbBounds.x,thumbBounds.y + (int)((thumbBounds.height-(30/Ressources.SCALE))/2), null);
	        }
	        else{
	        	imageThumb = new BufferedImage(thumbBounds.width, thumbBounds.height, BufferedImage.TYPE_INT_ARGB);
	        	Graphics g2 = imageThumb.getGraphics();
	        	g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_TOP),0,0, null);
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_BOTTOM),0,(int)(thumbBounds.height-(15/Ressources.SCALE)),null);
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_MIDDLE), 0, (int)(15/Ressources.SCALE), (int)(30/Ressources.SCALE), thumb, null);
		        
		        g.drawImage(imageThumb, thumbBounds.x,thumbBounds.y, null);
	        }
	        
	        
	    }
	    
	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) { 
	    	g.setColor(Ressources.SHELFCOLOR);
	    	g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	    }
	    
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        return up;
	    }

	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        return down;
	    }
	    
	}
}
