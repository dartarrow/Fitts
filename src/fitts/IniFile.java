package fitts;

/* .ini file reader and writer
 * 
 */

import java.io.*;
import java.util.Scanner;
import java.awt.Point;

/**
 * Class containing settings that can be saved and later read from fitts.ini file
 */

public class IniFile {
	String id;
	int weightList, targetList, typeList, keyTypeList, diamList, inType;
	Point[] coords = new Point[5];
	
	public IniFile() {
		id = null;
		weightList = 0;
		targetList = 0;
		typeList = 0;
		keyTypeList = 0;
		diamList = 0;
		inType = 0;
		for (int i = 0; i < 5; i++)
			coords[i] = new Point(0, 0);
	}

	/**
	 * Save current settings to ini file
	 */

	public void save() throws IOException {
		File iniFile;
		PrintWriter pw;
		try {
			iniFile = new File(new File(".").getCanonicalPath() + "/fitts.ini");
			pw = new PrintWriter(iniFile);
			pw.println("id = " + id);
			pw.println("weightList = " + weightList);
			pw.println("targetList = " + targetList);
			pw.println("typeList = " + typeList);
			pw.println("keyTypeList = " + keyTypeList);
			pw.println("diamList = " + diamList);
			pw.println("inType = " + inType);
			pw.println("coords");
			for (int i = 0; i < 5; i++)
				pw.println(coords[i].x + " " + coords[i].y);
			pw.close();
		} catch (IOException e) {
			throw e;

		}
	}

	/**
	 * Load fitts.ini from the root of Fitts
	 */

	public void load() throws FileNotFoundException, IOException { // Load the
																	// ini file
		Scanner s = null;
		int i = 0;
		int MAX_BUF = 32;
		String[] buf = new String[MAX_BUF];
		try {
			File iniFile = new File(new File(".").getCanonicalPath()
					+ "/fitts.ini");

			System.out.println(iniFile);
			s = new Scanner(new BufferedReader(new FileReader(iniFile)));
			while (s.hasNext() && i < MAX_BUF) {
				buf[i++] = s.next();

			}
			if (i >= 2)
				id = buf[2];
			if (i >= 5)
				weightList = new Double(buf[5]).intValue();
			if (i >= 8)
				targetList = new Double(buf[8]).intValue();
			if (i >= 11)
				typeList = new Double(buf[11]).intValue();
			if (i >= 14)
				keyTypeList = new Double(buf[14]).intValue();
			if (i >= 17)
				diamList = new Double(buf[17]).intValue();
			if (i >= 20)
				inType = new Double(buf[20]).intValue();
			if (i >= 31) {
				for (int q = 0; q < 5; q++) {
					coords[q] = new Point(new Double(buf[22 + q * 2])
							.intValue(), new Double(buf[23 + q * 2]).intValue());

				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (s != null) {
				s.close();
			}

		}
	}
}
