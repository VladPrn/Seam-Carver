package edu.seamcarving.java;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * The frame (extend JFrame) that resizes only downwards (for user).
 * Size can be programmatically increased.
 * Created for replace the bugged method setMaximumSize.
 * @author Vladislav Pecherkin
 */
public class ConstantlyDecreasingFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	 @Override
     public void paint(Graphics g) {
         Dimension d = getSize();
         Dimension m = getMaximumSize();
         boolean resize = d.width > m.width || d.height > m.height;
         d.width = Math.min(m.width, d.width);
         d.height = Math.min(m.height, d.height);
         if (resize) {
        	 setVisible(false);
             setSize(d);
             setVisible(true);
         } else {
        	 setMaximumSize(d);
         }
         super.paint(g);
     }
	 
	 @Override
	 public void setBounds(int x, int y, int width, int height) {
		 setMaximumSize(new Dimension(width, height));
		 super.setBounds(x, y, width, height);
	 }
}