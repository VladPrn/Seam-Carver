package edu.seamcarving.java;

import java.awt.Color;

/**
 * The class calculating pixel energies by method Changed Dual Gradient Energy Function
 * @author Elena Kyznetsova
 */
public class ChangedDualGradientEnergyFunction implements IMetrics {

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
	public double energy(MatrixPixels matrix, int x, int y,int width,int height) {
		Color clPixelDecX;
		Color clPixelIncX;
		Color clPixelDecY;
		Color clPixelIncY;
		if(x == 0){
			 clPixelDecX = matrix.get(width - 1, y);
		} else {
			 clPixelDecX = matrix.get(x - 1, y);
		}
		if(x == width - 1){
			 clPixelIncX = matrix.get(0, y);
		} else {
			 clPixelIncX = matrix.get(x + 1, y);
		}
		
		if(y == 0){
			 clPixelDecY = matrix.get(x, height - 1);
		} else {
			 clPixelDecY = matrix.get(x, y - 1);
		}
		
		if(y == height - 1){
			 clPixelIncY = matrix.get(x, 0);
		} else {
			 clPixelIncY = matrix.get(x, y + 1);
		}

		double enPixel = 0;
		
		enPixel = pow((clPixelIncX.getRed() - clPixelDecX.getRed()),2); 
		enPixel += pow((clPixelIncX.getGreen() - clPixelDecX.getGreen()),2);
		enPixel += pow((clPixelIncX.getBlue() - clPixelDecX.getBlue()),2); 
		enPixel += pow((clPixelIncY.getRed() - clPixelDecY.getRed()),2); 
		enPixel += pow((clPixelIncY.getGreen() - clPixelDecY.getGreen()),2);
		enPixel += pow((clPixelIncY.getBlue() - clPixelDecY.getBlue()),2); 
		
		return enPixel;
	}
}