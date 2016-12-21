package edu.seamcarving.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * The class for compare two files.
 * @author Elena Kyznetsova
 */
public class TextComparator {
	
	/**
	 * Method for compare two files.
	 * @param txt1 - first file
	 * @param txt2 - second file
	 * @return true if files equals false if files isn't equals
	 * @throws IOException 
	 */
	public static boolean compare(File txt1, File txt2) throws IOException{
		BufferedReader btxt1 = new BufferedReader(new FileReader(txt1));
		BufferedReader btxt2 = new BufferedReader(new FileReader(txt2));
		
		String s1 = btxt1.readLine(),
			s2 = btxt2.readLine();
		while((s1 = btxt1.readLine())!=null && (s2 = btxt2.readLine())!=null){
			if (!s1.equals(s2)) {
				btxt1.close();
				btxt2.close();
				return false;
			}
		} 
		
		if (btxt1.ready() != btxt2.ready()) {
			btxt1.close();
			btxt2.close();
			return false;
		}
		btxt1.close();
		btxt2.close();
		return true;
	}

}
