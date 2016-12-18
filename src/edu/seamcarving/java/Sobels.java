package edu.seamcarving.java;

import java.awt.Color;

/**
 * The class calculating pixel energies by Sobel's method 
 * @author Elena Kyznetsova
 */
public class Sobels implements IMetrics{
	

	/**
	 * Raises a number to power.
	 * @param  a - number.
	 * @param  b - power.
	 * @return a to the power of
	 */
	public double pow(double a, int b) {
		double result = 1;
		for(int i = 0; i < b; i++){
			result *= a;
		}
		return result;
	}
	
	@Override
	public double energy(MatrixPixels matrix, int x, int y, int width, int height) {
		if(x == 0 || y == 0 || x == width - 1 || y == height - 1){
			return 195075;
		}
		
		Color clPixelIncY1 = matrix.get(x - 1, y - 1);
		Color clPixelIncY2 = matrix.get(x , y - 1);
		Color clPixelIncY11 = matrix.get(x + 1, y - 1);
		Color clPixelDecY1 = matrix.get(x - 1, y + 1);
		Color clPixelDecY2 = matrix.get(x, y + 1);
		Color clPixelDecY11 = matrix.get(x + 1, y + 1);
			
		Color clPixelIncX1 = matrix.get(x - 1, y - 1);
		Color clPixelIncX2 = matrix.get(x - 1, y);
		Color clPixelIncX11 = matrix.get(x - 1, y + 1);
		Color clPixelDecX1 = matrix.get(x + 1, y - 1);
		Color clPixelDecX2 = matrix.get(x + 1, y);
		Color clPixelDecX11 = matrix.get(x + 1, y + 1);
		
		double sumX = 0, sumY = 0, enPixel = 0;
		
		sumX += (clPixelIncX1.getRed() + clPixelIncX1.getGreen() + clPixelIncX1.getBlue()) / 3;
		sumX += ((clPixelIncX2.getRed() + clPixelIncX2.getGreen() + clPixelIncX2.getBlue()) / 3) * 2;
		sumX += (clPixelIncX11.getRed() + clPixelIncX11.getGreen() + clPixelIncX11.getBlue()) / 3;
		sumX -= (clPixelDecX1.getRed() + clPixelDecX1.getGreen() + clPixelDecX1.getBlue()) / 3;
		sumX -= ((clPixelDecX2.getRed() + clPixelDecX2.getGreen() + clPixelDecX2.getBlue()) / 3) * 2;
		sumX -= (clPixelDecX11.getRed() + clPixelDecX11.getGreen() + clPixelDecX11.getBlue()) / 3;
		
		sumY += (clPixelIncY1.getRed() + clPixelIncY1.getGreen() + clPixelIncY1.getBlue()) / 3;
		sumY += ((clPixelIncY2.getRed() + clPixelIncY2.getGreen() + clPixelIncY2.getBlue()) / 3) * 2;
		sumY += (clPixelIncY11.getRed() + clPixelIncY11.getGreen() + clPixelIncY11.getBlue()) / 3;
		sumY -= (clPixelDecY1.getRed() + clPixelDecY1.getGreen() + clPixelDecY1.getBlue()) / 3;
		sumY -= ((clPixelDecY2.getRed() + clPixelDecY2.getGreen() + clPixelDecY2.getBlue()) / 3) * 2;
		sumY -= (clPixelDecY11.getRed() + clPixelDecY11.getGreen() + clPixelDecY11.getBlue()) / 3;
		
		enPixel = Math.sqrt((pow(sumX, 2) + pow(sumY, 2)));
		
		return enPixel;
	}

}
