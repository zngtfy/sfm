package com.cws.sfm.common;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author ToanNguyen 2018-Nov-15 (verified)
 *
 */
public class ZFile {
	// region -- Fields --

	private static final Logger _log = Logger.getLogger(ZFile.class.getName());

	// end

	// region -- Methods --

	/**
	 * Read file
	 * 
	 * @param file Full path file name
	 * @return
	 */
	public static String read(String file) {
		String res = "";

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			StringBuilder sb = new StringBuilder();
			String ls = System.getProperty("line.separator");

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(ls);
			}

			res = sb.toString();
			br.close();
		} catch (Exception ex) {
			if (ZConfig._printTrace) {
				ex.printStackTrace();
			}
			if (ZConfig._writeLog) {
				_log.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}

		return res;
	}

	/**
	 * Write file
	 * 
	 * @param file Full path file name
	 * @param s    String data
	 */
	public static void write(String file, String s) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] strToBytes = s.getBytes();
			outputStream.write(strToBytes);

			outputStream.close();
		} catch (Exception ex) {
			if (ZConfig._printTrace) {
				ex.printStackTrace();
			}
			if (ZConfig._writeLog) {
				_log.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Get real path of Java application at runtime + sub-path
	 * 
	 * @param s Sub-path
	 * @return
	 */
	public static String getPath(String s) {
		return System.getProperty("user.dir") + "/" + s + "/";
	}

	// end
}