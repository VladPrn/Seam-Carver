package edu.seamcarving.java;

import java.awt.image.BufferedImage;

/**
 * The interface that determinate general methods for seam carving.
 * @author given in task
 */
public interface ISeamCarver {
	
	/**
	 * Get image as instance of BufferedImage
	 * @return the current image
	 */
	public BufferedImage getBufferedImage();
	
	/**
	 * Get current width of image.
	 * @return the width in pixels of current image
	 */
	public int width();
	
	/**
	 * Get current width of image.
	 * @return the height in pixels of current image
	 */
	public int height();
	
	/**
	 * Calculate energy of pixel (x, y).
	 * @param x the column x
	 * @param y the row y
	 * @return energy of pixel at column x and row y
	 */
	public double energy(int x, int y);
	
	/**
	 * Find the horizontal seam with minimal weight.
	 * @return the sequence of indices (x) for horizontal seam
	 */
	public int[] findHorizontalSeam();
	
	/**
	 * Find the vertical seam with minimal weight.
	 * @return the sequence of indices (y) for vertical seam
	 */
	public int[] findVerticalSeam();
	
	/**
	 * Remove horizontal seam from picture.
	 * @param a the sequence of indices (x) for horizontal seam
	 * @throws IndexOutOfBoundsException unless both {0 <= a[j] < height}
	 * @throws IllegalArgumentException unless size of array not equally to width of picture
	 */
	public void removeHorizontalSeam(int[] a);
	
	/**
	 * Remove vertical seam from picture.
	 * @param a the sequence of indices (y) for vertical seam
	 * @throws IndexOutOfBoundsException unless both {0 <= a[i] < width}
	 * @throws IllegalArgumentException unless size of array not equally to height of picture
	 */
	public void removeVerticalSeam(int[] a);
}