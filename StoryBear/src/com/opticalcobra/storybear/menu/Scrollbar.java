package com.opticalcobra.storybear.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
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
	public Scrollbar(Color bg){
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar sb = this.getVerticalScrollBar();		
	    sb.setUI(new ScrollbarUI(bg));
	}
	static class ScrollbarUI extends MetalScrollBarUI {
	    private JButton up,down;
	    private ImageIcon iup,idown;
	    private Imagelib il= Imagelib.getInstance();
	    private Color background;
	    private boolean scaled = false;
	    
	    
	    public ScrollbarUI(Color bg) {
	        this.background=bg;
	        
	        iup = new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_UP));
			up = new JButton();
			up.setIcon(iup);
			up.setRolloverIcon(iup);
			up.setPressedIcon(iup);
//			up.setPreferredSize(new Dimension((int)(30/Ressources.SCALE),(int)(20/Ressources.SCALE)));
			up.setBorder(null);
			up.setOpaque(true);
	        up.setBorderPainted(false);
	        up.setBackground(background);
	        
	        idown = new ImageIcon(il.menuImage(Imagelib.MENU_SCROLL_DOWN));
	        down = new JButton();
			down.setIcon(idown);
			down.setRolloverIcon(idown);
			down.setPressedIcon(idown);
//			down.setPreferredSize(new Dimension((int)(30/Ressources.SCALE),(int)(20/Ressources.SCALE)));
			down.setBorder(null);
	        down.setBorderPainted(false);
	        down.setBackground(background);
			
	    }
	 
	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
	    	double scale = ((double)thumbBounds.width/30)*Ressources.SCALE;
	        int thumb = (int) (thumbBounds.height - (2*(int)(10*scale)));
	        Image imageThumb;
	       
	        if(!scaled){
	        	scaled = true;
	        	Image img = iup.getImage() ;  
        	    Image newimg = img.getScaledInstance( thumbBounds.width, (int) (15*scale),  java.awt.Image.SCALE_SMOOTH ) ;  
        	    iup= new ImageIcon( newimg );
        	    up.setIcon(iup);
    			up.setRolloverIcon(iup);
    			up.setPressedIcon(iup);
        	    Image img2 = idown.getImage() ;  
        	    Image newimg2 = img2.getScaledInstance( thumbBounds.width, (int) (15*scale),  java.awt.Image.SCALE_SMOOTH ) ;  
        	    idown= new ImageIcon( newimg2 );
        	    down.setIcon(idown);
    			down.setRolloverIcon(idown);
    			down.setPressedIcon(idown);
	        	
	        }
	        
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
	        	g2.setColor(new Color(128,128,128));
	        	g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_TOP),0,0,thumbBounds.width,(int) (10*scale), null);
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_BOTTOM),0,(thumbBounds.height-(int)(10*scale)),thumbBounds.width,(int) (10*scale),null);
		        g2.drawImage(il.menuImage(Imagelib.MENU_SCROLL_THUMB_MIDDLE), 0, (int)(10*scale), thumbBounds.width, thumb, null);
		        
		        g.drawImage(imageThumb, thumbBounds.x,thumbBounds.y, null);
	        }
	        
	        
	    }
	    
	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) { 
	    	g.setColor(background);
//	    	g.setColor(Color.BLUE);
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
