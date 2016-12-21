package edu.seamcarving.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class for compare two files.
 * Ignore difference between /r/n and /n.
 * @author Vladislav Pecherkin
 */
public class FileComparator {
	
	/**
	 * Method for compare two files.
	 * @param a the first file
	 * @param b the second file
	 * @return true if files equals false otherwise
	 * @throws IOException unless can not find file a or b, can not get access to file a or b.
	 */
	@SuppressWarnings("resource")
	public static boolean compareFiles(File a, File b) throws IOException {
		BufferedReader bra = new BufferedReader(new FileReader(a));
		BufferedReader brb = new BufferedReader(new FileReader(b));
		
		while (bra.ready() && brb.ready()) {
			String stra = bra.readLine();
			String strb = brb.readLine();
			if (!stra.equals(strb)) {
				bra.close();
				brb.close();
				return false;
			}
		}
	
		boolean result = (bra.ready() == brb.ready());
		bra.close();
		brb.close();
		return result;
	}
}