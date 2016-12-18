package edu.seamcarving.java;

import java.awt.Color;

/**
 * The class calculating pixel energies by method Difference Between Neighbors
 * @author Elena Kyznetsova
 */
public class DifferenceBetweenNeighbors implements IMetrics {

	@Override
	public double energy(MatrixPixels matrix, int x, int y, int width, int height) {
		double enPixel = 0;
		
		if(x == 0 || y == 0 || x == width - 1 || y == height - 1){
			return 195075;
		}
	
		Color clPixel = matrix.get(x, y);
		Color clPixelIncX = matrix.get(x + 1, y);
		Color clPixelIncY = matrix.get(x, y + 1);
	
		enPixel += Math.abs((clPixel.getRed() + clPixel.getGreen() + clPixel.getBlue())/3 - (clPixelIncX.getRed() + clPixelIncX.getGreen() + clPixelIncX.getBlue())/3);
		enPixel += Math.abs((clPixel.getRed() + clPixel.getGreen() + clPixel.getBlue())/3 - (clPixelIncY.getRed() + clPixelIncY.getGreen() + clPixelIncY.getBlue())/3); 
		enPixel /= 2;
	
		return enPixel;
	}	
}