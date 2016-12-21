package edu.seamcarving.test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import org.junit.*;

import edu.seamcarving.java.*;

/**
 * The class for unit testing of PecherkinSeamCarver.
 * @author Elena Kyznetsova
 */
public class PecherkinSeamCarverTest extends Assert {
	
	/**
	 * The test for file 3x7.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn3x7() throws IOException {
		test("3x7");
	}
	
	/**
	 * The test for file 4x6.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn4x6() throws IOException {
		test("4x6");
	}
	
	/**
	 * The test for file 5x6.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn5x6() throws IOException {
		test("5x6");
	}
	
	/**
	 * The test for file 6x5.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn6x5() throws IOException {
		test("6x5");
	}
	
	/**
	 * The test for file 7x3.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn7x3() throws IOException {
		test("7x3");
	}
	
	/**
	 * The test for file 10x12.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn10x12() throws IOException {
		test("10x12");
	}
	
	/**
	 * The test for file 12x10.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOn12x10() throws IOException {
		test("12x10");
	}
	
	/**
	 * The test for file mountains.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnMountains() throws IOException {
		test("mountains");
	}
	
	/**
	 * The test for file bridge.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnBridge() throws IOException {
		test("bridge");
	}
	
	/**
	 * The test for file lake.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnLake() throws IOException {
		test("lake");
	}
	
	/**
	 * The test for file sea.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnSea() throws IOException {
		test("sea");
	}
	
	/**
	 * The test for file BlackWhite.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnBlackWhite() throws IOException {
		test("BlackWhite");
	}
	
	/**
	 * The test for file Red.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnRed() throws IOException {
		test("Red");
	}
	
	/**
	 * The test for file Green.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnGreen() throws IOException {
		test("Green");
	}
	
	/**
	 * Delete temporary file after executing all tests.
	 */
	@AfterClass
	public static void deleteTmpFile() {
		File tmp = new File("tmp.txt");
		tmp.delete();
	}
	
	/**
	 * The universal test for seam carving.
	 * @param name the name of file that will be tested 
	 * @throws IOException unless can not create temporary file or get access to it
	 */
	@SuppressWarnings("deprecation")
	private void test(String name) throws IOException {
			File inputFile = new File("src/resources/" + name + ".png");
			File testFile = new File("src/resources/" + name + ".printseams.txt");
			
			ISeamCarver carver = new PecherkinSeamCarver(new MatrixPixels(inputFile), new DualGradientEnergyFunction());
			File result = new File("tmp.txt");
			
			PrintSeams.main(carver, result);
			assertTrue(TextComparator.compare(testFile, result));	
	
	}
}