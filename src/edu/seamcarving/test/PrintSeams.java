package edu.seamcarving.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.seamcarving.java.ISeamCarver;

/**
 * Class for print energy of each image, as well as both seams, and the total energy of each seam.
 * @author given in task
*/
public class PrintSeams {

	/**
	 * Print energy map (energy for each pixel) to file, mark [] horizontal seam and computing total energy.
	 * @param sc the instance of ISeamCarver 
	 * @param pw the PrintWriter for output file
	 */
    private static void printHorizontalSeam(ISeamCarver sc, PrintWriter pw)
    {        
        double totalSeamEnergy = 0;

        int[] horizontalSeam = sc.findHorizontalSeam();
        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                if (j == horizontalSeam[i])
                {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                pw.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }
            pw.println();
        }                
        
        pw.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
    }

    /**
	 * Print energy map (energy for each pixel) to file, mark [] vertical seam and computing total energy.
	 * @param sc the instance of ISeamCarver 
	 * @param pw the PrintWriter for output file
	 */
    private static void printVerticalSeam(ISeamCarver sc, PrintWriter pw)
    {
        double totalSeamEnergy = 0;

        int[] verticalSeam = sc.findVerticalSeam();
        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                if (i == verticalSeam[j])
                {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                pw.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }

            pw.println();
        }                
        
        pw.printf("\nTotal energy: %.0f\n\n\n", totalSeamEnergy);
    }

    /**
     * Write testing materials (energy, first seams, total energy) to file.
     * @param sc the instance of ISeamCarver
     * @param out the file for output result
     * @throws FileNotFoundException unless can not write data to output file
     */
    public static void main(ISeamCarver sc, File out) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(out);
        pw.printf("image is %d columns by %d rows\n", sc.width(), sc.height());
        
        pw.printf("Displaying horizontal seam calculated.\n");
        printHorizontalSeam(sc, pw);

        pw.printf("Displaying vertical seam calculated.\n");
        printVerticalSeam(sc, pw);
        pw.close();
    }
}