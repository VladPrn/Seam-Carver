package edu.seamcarving.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The class that contains a picture as a matrix of pixels.
 * Able to cut its size.
 * @author Vladislav Pecherkin
 */
public class MatrixPixels {
	
	/**
	 * Base element which store image.
	 */
	private final BufferedImage img;
	
	/**
	 * Width of current image.
	 */
	private int width;
	
	/**
	 * Height of current image.
	 */
	private int height;
	
	/**
	 * The constructor creates an instance of class from file path.
	 * @param file the path to picture
	 */
	public MatrixPixels(File file) throws IOException {
		img = ImageIO.read(file);
		width = img.getWidth();
		height = img.getHeight();
	}
	
	/**
	 * Get picture as instance of BufferedImage
	 * @return the BufferedImage of current matrix.
	 */
	public BufferedImage getBufferedImage() {
		WritableRaster raster = img.copyData(null);
		BufferedImage res = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB);
		res.setData(raster);
		return res;
	}
	
	/**
	 * Get current width of matrix.
	 * @return the width of current matrix
	 */
	public int width() {
		return width;
	}
	
	/**
	 * Decrement the width of current matrix.
	 * @return (width - 1)
	 */
	public int widthDecrement() {
		return --width;
	}
	
	/**
	 * Get current height of matrix.
	 * @return the height of current picture
	 */
	public int height() {
		return height;
	}
	
	/**
	 * Decrement the height of current matrix.
	 * @return (height - 1)
	 */
	public int heightDecrement() {
		return --height;
	}
	
	/**
	 * Returns the color of pixel (column, row).
	 * @param column the column index
	 * @param row the row index
	 * @return the color of pixel (column, row)
	 * @throws IndexOutOfBoundsException unless both {0 <= column < width} and {0 <= row < height}
	 */
	public Color get(int column, int row) {
		if (!validateColumn(column)) {
			throw new IndexOutOfBoundsException("column must be between 0 and " + width());
		}
		if(!validateRow(row)) {
			throw new IndexOutOfBoundsException("row must be between 0 and " + height());
		}
		return new Color(img.getRGB(column, row));
	}
	
	/**
	 * Set the color to pixel (column, row).
	 * @param column the column index
	 * @param row the row index
	 * @param color the color for setting pixel
	 * @throws IndexOutOfBoundsException unless both {0 <= column < width} and {0 <= row < height}
	 */
	public void set(int column, int row, Color color) {
		if (!validateColumn(column)) {
			throw new IndexOutOfBoundsException("column must be between 0 and " + width());
		}
		if(!validateRow(row)) {
			throw new IndexOutOfBoundsException("row must be between 0 and " + height());
		}
		img.setRGB(column, row, color.getRGB());
	}
	
	/**
	 * Check the column on bounds {0 <= column < width}.
	 * @param column to column index
	 * @return true if column is valid
	 */
	public boolean validateColumn(int column) {
		if (column < 0 || column >= width()) {
            return false;
		}
		return true;
	}
	
	/**
	 * Check the row on bounds {0 <= row < height}.
	 * @param row to row index
	 * @return true if row is valid
	 */
	public boolean validateRow(int row) {
		if (row < 0 || row >= height()) {
            return false;
		}
		return true;
	}
}