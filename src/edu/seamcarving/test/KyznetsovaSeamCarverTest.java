package edu.seamcarving.test;

import java.io.File;
import java.io.IOException;

import org.junit.*;

import edu.seamcarving.java.*;

/**
 * The class for unit testing of KyznetsovaSeamCarver.
 * @author Vladislav Pecherkin
 */
public class KyznetsovaSeamCarverTest extends Assert {
	
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
	 * The test for file kremlin1.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnKremlin1() throws IOException {
		test("kremlin1");
	}
	
	/**
	 * The test for file kremlin2.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnKremlin2() throws IOException {
		test("kremlin2");
	}
	
	/**
	 * The test for file kremlin3.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnKremlin3() throws IOException {
		test("kremlin3");
	}
	
	/**
	 * The test for file empty.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnEmpty() throws IOException {
		test("empty");
	}
	
	/**
	 * The test for file montain.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnMontain() throws IOException {
		test("montain");
	}
	
	/**
	 * The test for file night.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnNight() throws IOException {
		test("night");
	}
	
	/**
	 * The test for file noise.png
	 * @throws IOException unless can not find resource file, can not write temporary file or can not get access to temporary file
	 */
	@Test
	public void testFindSeamsOnNoise() throws IOException {
		test("noise");
	}
	
	/**
	 * Delete temporary file after executing all tests.
	 */
	@AfterClass
	public static void deleteTmpFile() {
		File tmp = new File("tmpfile.txt");
		tmp.delete();
	}
	
	/**
	 * The universal test for seam carving.
	 * @param name the name of file that will be tested 
	 * @throws IOException unless can not create temporary file or get access to it
	 */
	private void test(String name) throws IOException {
		ClassLoader ld = this.getClass().getClassLoader();
		File imgInput = new File(ld.getResource("resources/" + name + ".png").getFile().replaceAll("%20", " "));
		File test = new File(ld.getResource("resources/" + name + ".printseams.txt").getFile().replaceAll("%20", " "));
		
		ISeamCarver carver = new KyznetsovaSeamCarver(new MatrixPixels(imgInput), new DualGradientEnergyFunction());
		File result = new File("tmpfile.txt");
		PrintSeams.main(carver, result);
		
		assertTrue(FileComparator.compareFiles(test, result));
	}
}