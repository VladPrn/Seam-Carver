package edu.seamcarving.java;

/**
 * The interface that determinate general methods for calculating pixel energies
 * @author Elena Kyznetsova
 */
public interface IMetrics {
	
	/**
	 * Calculate energy of pixel (x, y).
	 * @param matrix of current image 
	 * @param x the column x
	 * @param y the row y
	 * @param width the column 
	 * @param height the row 
	 * @return energy of pixel at column x and row y
	 */
	public double energy(MatrixPixels matrix, int x, int y, int width, int height);
	
}