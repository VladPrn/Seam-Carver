package edu.seamcarving.java;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The class of image is resized according to seam carving.
 * @author Vladislav Pecherkin
 * @author Elena Kyznetsova
 */
public class ResizableImage extends JPanel {
	
	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The instance of class implement ISeamCarver.
	 * Using for resize image according to Seam Carving.
	 */
	private ISeamCarver carver;
	
	/**
	 * The first (begin) width of image before any resizing.
	 */
	private int beginWidth;
	
	/**
	 * The first (begin) height of image before any resizing.
	 */
	private int beginHeight;
	
	/**
	 * The constructor creates an instance of ResizableImage from path to image file.
	 * @param file the file of image from which will be created instance of the ResizableImage
	 * @param version determines whose implementation of Seam Carving use
	 * @param mVersion determines which metrics use for calculate energy of pixels
	 * @exception IOException unless can not find file or can not get access to file
	 */
	public ResizableImage(String file, SeamCarvingVersion version, MetricsVersion mVersion) throws IOException {
		super();
		MatrixPixels matrix = new MatrixPixels(new File(file));
		
		IMetrics metrics = null;
		switch (mVersion) {
   			case neighbors: metrics = new DifferenceBetweenNeighbors(); break;
   			case changedGradient: metrics = new ChangedDualGradientEnergyFunction(); break;
   			case sobels: metrics = new Sobels(); break;
   			case gradient: metrics = new DualGradientEnergyFunction(); break;
		}
		
		if (version == SeamCarvingVersion.pecherkin) {
			carver = new PecherkinSeamCarver(matrix, metrics);
		} else {
			carver = new KyznetsovaSeamCarver(matrix, metrics);
		}
		
		beginWidth = carver.width();
		beginHeight = carver.height();
	}
	
	@Override
	public void paint(Graphics g) {
		resize();
		g.drawImage(carver.getBufferedImage(), 0, 0, null);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		resize();
	}
	
	/**
	 * Save current (resized) image to file.
	 * @param output the file for saved picture
	 * @throws IOException unless can not write file or can not get access to file
	 */
	public void save(String output) throws IOException {
		String format = output.substring(output.lastIndexOf('.') + 1);
		ImageIO.write(carver.getBufferedImage(), format, new File(output));
	}
	
	/**
	 * Get begin width.
	 * Width of image before change size of image.
	 * @return the width of image using with creating of instance
	 */
	public int getBeginWidth() {
		return beginWidth;
	}
	
	/**
	 * Get begin height.
	 * Height of image before change size of image.
	 * @return the begin height of image using with creating of instance
	 */
	public int getBeginHeight() {
		return beginHeight;
	}
	
	/**
	 * The method resizing image according to Seam Carving.
	 */
	public void resize() {
		int dWidth = carver.width() - this.getWidth();
		int dHeight = carver.height() - this.getHeight();
		
		if (carver.height() > 0) {
			for (int i = 0; i < dHeight; i++) {	 
				carver.removeHorizontalSeam(carver.findHorizontalSeam());
			}
		}
		if (carver.width() > 0) {
			for (int i = 0; i < dWidth; i++) { 	 
				carver.removeVerticalSeam(carver.findVerticalSeam());
			}
		}
	}
}