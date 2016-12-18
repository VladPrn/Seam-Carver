package edu.seamcarving.java;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * The class executing seam carving to image.
 * @author Elena Kyznetsova
 */
public class KyznetsovaSeamCarver implements ISeamCarver {
	
	/** Matrix of current image   */
	private MatrixPixels matrix;
	/** Energy pixels of current image  */
	private double[][] energyMatrix;
	/**  Width  of current image  */
	private int width;
	/**  Height of current image  */
	private int height;
	/**  Metrics for calculate energy  */
	private IMetrics metrics;
	
	/**
	 * The constructor creates an instance of class from file instance of class MatrixPixels
	 * @param instance of class MatrixPixels
	 * @param metrics the metrics for calculate energy of pixels
	 */
	public KyznetsovaSeamCarver(MatrixPixels matrix, IMetrics metrics) {
		this.matrix = matrix;
		height = matrix.height();
		width = matrix.width();
		this.metrics = metrics;
		double[][] energyMatrix = new double[width][height];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				energyMatrix[j][i] = energy(j,i);
			}
		}
			
		this.energyMatrix = energyMatrix;
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
	
	/**
	 * Raises a number to power.
	 * @param  a - number.
	 * @param  b - power.
	 * @return a to the power of
	 */
	public double pow(int a, int b) {
		double result = 1;
		for(int i = 0; i < b; i++){
			result *= a;
		}
		return result;
	}
	
	
	@Override
	public double energy(int x, int y) {
		return metrics.energy(matrix, x, y, width, height);
	}

	@Override
	public int[] findHorizontalSeam() {
		double[][] weightMatrix = new double[width][height];
		int[] seam = new int[width];
		for(int i = 0; i <  width; i++){
			for(int j = 0; j < height; j++){
				weightMatrix[i][j] = energyMatrix[i][j];
				
			}
		}
		for(int i = 1; i < width; i++){
			for(int j = 0; j < height; j++){
				if(j == 0){
					weightMatrix[i][j] +=  Math.min(weightMatrix[i - 1][j], weightMatrix[i - 1][j + 1]);
					continue;
				}
				if(j == height - 1){
					weightMatrix[i][j] += Math.min(weightMatrix[i - 1][j - 1], weightMatrix[i - 1][j]);
					continue;
				}
				weightMatrix[i][j] += Math.min(Math.min(weightMatrix[i - 1][j - 1],weightMatrix[i - 1][j]),weightMatrix[i - 1][j + 1]);

			}

		}
		
		int minI = 0; 
				
		for(int j = 0; j <height ; j++){
			if(weightMatrix[width - 1][j] <  weightMatrix[width - 1][minI]) {
				minI = j;
			}
		}
		seam[width - 1] = minI;
		int j = minI;
		for(int i = width - 2; i >= 0; i--){
			j = minI;
			if(j == 0){
				if(weightMatrix[i][j] <  weightMatrix[i][j + 1]) {
					seam[i]  = j;
					 minI = j;
					continue;
				} else {
					seam[i]  = j + 1;
					 minI = j + 1;
					continue;
				}	
			}
			
			if(j == height - 1){
				if(weightMatrix[i][j] <  weightMatrix[i][j - 1]) {
					seam[i]  = j;
					 minI = j;
					continue;
				} else {
					seam[i]  = j - 1;
					 minI = j - 1;
					continue;
				}	
			}
			

			
			double min = weightMatrix[i][j - 1];
			minI = j - 1;
			seam[i] = j - 1;

			if (weightMatrix[i][j] < min) {
			seam[i] = j;
			minI = j;
			min = weightMatrix[i][j];
			}

			if (weightMatrix[i][j + 1] < min) {
			seam[i] = j + 1;
			minI = j + 1;
			min = weightMatrix[i][j + 1];
			}
			
		}	
	
		return seam;
	}

	@Override
	public int[] findVerticalSeam() {
		double[][] weightMatrix = new double[width][height];
		int[] seam = new int[height];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				weightMatrix[j][i] = energyMatrix[j][i];
				
			}
		}
		for(int i = 1; i < height; i++){
			for(int j = 0; j < width; j++){
				if(j == 0){
					weightMatrix[j][i] +=  Math.min(weightMatrix[j][i - 1], weightMatrix[j + 1][i - 1]);
					continue;
				}
				if(j == width - 1){
					weightMatrix[j][i] += Math.min(weightMatrix[j - 1][i - 1], weightMatrix[j][i - 1]);
					continue;
				}
				weightMatrix[j][i] += Math.min(Math.min(weightMatrix[j - 1][i - 1],weightMatrix[j][i - 1]),weightMatrix[j + 1][i - 1]);
			}
		}
		
		int minI = 0; 
				
		for(int j = 0; j < width; j++){
			if(weightMatrix[j][height - 1] <  weightMatrix[minI][height - 1]) {
				minI = j;
			}
		}
		seam[height - 1] = minI;
		int j = minI;
		for(int i = height - 2; i >= 0; i--){
			j = minI;
			if(j == 0){
				if(weightMatrix[j][i] <  weightMatrix[j + 1][i]) {
					seam[i]  = j;
					 minI = j;
					continue;
				} else {
					seam[i]  = j + 1;
					 minI = j + 1;
					continue;
				}	
			}
			
			if(j == width - 1){
				if(weightMatrix[j][i] <  weightMatrix[j - 1][i]) {
					seam[i]  = j;
					 minI = j;
					continue;
				} else {
					seam[i]  = j - 1;
					 minI = j - 1;
					continue;
				}	
			}
			
			double min = weightMatrix[j - 1][i];
			minI = j - 1;
			seam[i] = j - 1;

			if (weightMatrix[j][i] < min) {
			seam[i] = j;
			minI = j;
			min = weightMatrix[j][i];
			}

			if (weightMatrix[j + 1] [i]< min) {
			seam[i] = j + 1;
			minI = j + 1;
			min = weightMatrix[j + 1][i];
			}
			
			
		}
		return seam;
	}

	@Override
	public void removeHorizontalSeam(int[] a) {
		
		for(int j = 0; j < width; j++){	
			for(int i =  a[j]; i < height - 1; i++){
				Color clPixel = matrix.get(j, i + 1);
				matrix.set(j, i, clPixel);
			}
		}
		
		height--;
		matrix.heightDecrement();
			
		for(int i = 0; i < width; i++){
			int begin = a[i] - 1;
			int end = a[i] + 1;
			if (begin < 0) {
				begin = 0;
			}
			if (end > height - 1) {
				end = height - 1;
			}
			for(int j = begin; j < height; j++){
				if(j >= end) {
					energyMatrix[i][j] = energyMatrix[i][j + 1];
					continue;
					
				}
				
				energyMatrix[i][j] = energy(i, j);
			}
		}

	}

	@Override
	public void removeVerticalSeam(int[] a) {
		for(int i = 0; i < height; i++){
			for(int j = a[i]; j < width - 1; j++){
				Color clPixel = matrix.get(j + 1, i);
				matrix.set(j, i, clPixel);
			}
		}
		width--;
		matrix.widthDecrement();

		for(int i = 0; i < height; i++){
			int begin = a[i] - 1;
			int end = a[i] + 1;
			if (begin < 0) {
				begin = 0;
			}
			if (end > width - 1) {
				end = width - 1;
			}
			for(int j = begin; j < width; j++){
				if(j >= end) {
					energyMatrix[j][i] = energyMatrix[j + 1][i];
					continue;
				}
				
				energyMatrix[j][i] = energy(j, i);		
			}
		}	
	}
}