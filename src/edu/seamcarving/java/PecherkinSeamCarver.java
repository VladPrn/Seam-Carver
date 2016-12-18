package edu.seamcarving.java;

import java.awt.image.BufferedImage;

/**
 * The class executing seam carving to image.
 * @author Vladislav Pecherkin
 */
public class PecherkinSeamCarver implements ISeamCarver {
	
	/**
	 * Matrix of pixels of current image.
	 */
	private MatrixPixels matrix;
	
	/**
	 * The metrics for calculate of energy of pixels
	 */
	private IMetrics metrics;
	
	/**
	 * Width of current image.
	 */
	private int width;
	
	/**
	 * Height of current image.
	 */
	private int height;
	
	/**
	 * Map (matrix) of pixels energy.
	 */
	private double[][] energyMap;
	
	/**
	 * Map (matrix) of pixels weight.
	 * For dynamic programming.
	 */
	private double[][] weightMap;
	
	/**
	 * Map (matrix) of directions for fast recovery of seams with minimal weight.
	 */
	private int[][] arrowMap;
	
	/**
	 * The constructor creates an instance of class from MatrixPixels.
	 * @param matrix the matrix will be used for create instance of class
	 * @param metrics the metrics for calculate energy of pixels
	 */
	public PecherkinSeamCarver(MatrixPixels matrix, IMetrics metrics) {
		   this.matrix = matrix;
		   this.width = matrix.width();
		   this.height = matrix.height();
		   this.energyMap = new double[height][width];
		   this.weightMap = new double[height][width]; 
		   this.arrowMap = new int[height][width]; 
		   this.metrics = metrics;
		   setEnergyMap();
	}
	   
	@Override
	public BufferedImage getBufferedImage() {
		return matrix.getBufferedImage();
	}
	   
	@Override
	public int width() {
		return width;
	}
	
	@Override
	public int height() {
		return height;
	}
	
	@Override
	public double energy(int x, int y) {
		return metrics.energy(matrix, x, y, width, height);
	}
	
	@Override
	public int[] findHorizontalSeam() {
		setHorizontalWeightMap();
		int[] res = new int[width];
		
		int imin = 0;
		double min = 1e+9;
		for (int i = 0; i < height; i++) {
			if (weightMap[i][width - 1] < min) {
				imin = i;
				min = weightMap[i][width - 1];
			}
		}
		
		int icurr = imin;
		res[width - 1] = icurr;
		for (int j = width - 1; j >= 1; j--) {
			icurr += arrowMap[icurr][j];
			res[j - 1] = icurr;
		}
		
		return res;
	}
	
	@Override
	public int[] findVerticalSeam() {
		setVerticalWeightMap();
		int[] res = new int[height];
			
		int jmin = 0;
		double min = 1e+9;
		for (int j = 0; j < width; j++) {
			if (weightMap[height - 1][j] < min) {
				jmin = j;
				min = weightMap[height - 1][j];
			}
		}
			
		int jcurr = jmin;
		res[height - 1] = jcurr;
		for (int i = height - 1; i >= 1; i--) {
			jcurr += arrowMap[i][jcurr];
			res[i - 1] = jcurr;
		}
		return res;
	}
	   
	@Override
	public void removeHorizontalSeam(int[] a) {
		if (a.length != width) {
			throw new IllegalArgumentException("size of array must be equally to width of picture");
		}
		
		for (int j = 0; j < width; j++) {
			if (a[j] >= height) {
				throw new IllegalArgumentException("seam points must be on picture");
			}
			for (int i = a[j] + 1; i < height; i++) {
				matrix.set(j, i - 1, matrix.get(j, i));
				energyMap[i - 1][j] = energyMap[i][j];
			}
		}
		
		for (int j = 0; j < width; j++) {
			if (a[j] - 1 > 0) {
				energyMap[a[j] - 1][j] = energy(j, a[j] - 1);
			}
			energyMap[a[j]][j] = energy(j, a[j]);
		}
		
		height = matrix.heightDecrement();;
	}
	   
	@Override
	public void removeVerticalSeam(int[] a) {
		if (a.length != height) {
			throw new IllegalArgumentException("size of array must be equally to height of picture");
		}
		
		for (int i = 0; i < height; i++) {
			if (a[i] >= width) {
				throw new IllegalArgumentException("seam points must be on picture");
			}
			for (int j = a[i] + 1; j < width; j++) {
				matrix.set(j - 1, i, matrix.get(j, i));
				energyMap[i][j - 1] = energyMap[i][j];
			}
		}
		
		for (int i = 0; i < height; i++) {
			if (a[i] - 1 > 0) {
				energyMap[i][a[i] - 1] = energy(a[i] - 1, i);
			}
			energyMap[i][a[i]] = energy(a[i], i);
		}
		
		width = matrix.widthDecrement();
	}
	
	/**
	 * Calculate energy of each pixel of picture.
	 */
	private void setEnergyMap() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				energyMap[i][j] = energy(j, i);
			}
		}
	}
	   
	/**
	 * Calculate matrix of weight of minimal paths (dynamic programming).
	 * Find minimal path to each pixel.
	 * Set weight equals to energy for trivial pixels (0, y).
	 * Call function setMin for all not trivial pixels in a particular order.
	 * For horizontal seams.
	 */
	private void setHorizontalWeightMap() {
		for (int i = 0; i < height; i++) {
			weightMap[i][0] = energyMap[i][0];
		}
		
		for (int j = 1; j < width; j++) {
			for (int i = 0; i < height; i++) {
				setMin(j, i, true);
			}
		}
	}
	
	/**
	 * Calculate matrix of weight of minimal paths (dynamic programming).
	 * Find minimal path to each pixel.
	 * Set weight equals to energy for trivial pixels (x, 0).
	 * Call function setMin for all not trivial pixels in a particular order.
	 * For vertical seams.
	 */
	private void setVerticalWeightMap() {
		for (int j = 0; j < width; j++) {
			weightMap[0][j] = energyMap[0][j];
		}
		
		for (int i = 1; i < height; i++) {
			for (int j = 0; j < width; j++) {
				setMin(j, i, false);
			}
		}
	}
	
	/**
	 * Calculate minimal weight to pixel (x, y) by already calculate weights (dynamic programming).
	 * @param x the column x
	 * @param y the row y
	 * @param horizontal the determinate for horizontal (true) or for vertical (false) seam calculate minimal weight
	 */
	private void setMin(int x, int y, boolean horizontal) {
		int[] dx = new int[3];
		int[] dy = new int[3];
		if (horizontal) {
			dx[0] = dx[1] = dx[2] = -1;
			dy[0] = -1;
			dy[1] = 0;
			dy[2] = 1;
		} else {
			dy[0] = dy[1] = dy[2] = -1;
			dx[0] = -1;
			dx[1] = 0;
			dx[2] = 1;
		}
		
		double min = 1e+9;
		for (int i = 0; i < 3; i++) {
			if (matrix.validateColumn(x + dx[i]) && matrix.validateRow(y + dy[i]) && weightMap[y + dy[i]][x + dx[i]] < min) {
				min = weightMap[y + dy[i]][x + dx[i]];
				arrowMap[y][x] = horizontal ? dy[i] : dx[i];
			}
		}
		
		weightMap[y][x] = min + energyMap[y][x];
	}
}