package kmu.itsp.score.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ScoreUtil {

	public static ArrayList<String> getAnswer(String path, String probName,
			String suffix) {
		ArrayList<String> answerList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			File answerFile = new File(path + probName + suffix);

			if (!answerFile.canRead()) {
				return null;
			}

			FileReader fr = new FileReader(answerFile);
			br = new BufferedReader(fr);
			String str = "";
			while ((str = br.readLine()) != null) {
				answerList.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return answerList;
	}

	// this method must move other class
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String cutDigitInDouble(String s) {
		double dbValue = Double.parseDouble(s);

		return formatDouble(dbValue + 0.0);
		// double value is -0.0 so -0.0 + 0.0 = 0.0
	}

	public static String formatDouble(double x) {
        return String.format("%.17g", x).replaceFirst("\\.?0+(e|$)", "$1");
	}
	
	public static String deletePathInStr(String str, String path) {
		String result = new String(str);

		while (result.contains(path)) {
			result = result.replace(path, "");
		}
		return result;
	}
}
