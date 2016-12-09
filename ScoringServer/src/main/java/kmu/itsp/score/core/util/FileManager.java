package kmu.itsp.score.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileManager {

	public static File createStringToFile(String dirPath, String text,
			String suffix) {
		UUID uuid = UUID.randomUUID();
		if (suffix == null) {
			suffix = "";
		}

		try {
			File file = File.createTempFile("temp_", suffix, new File(dirPath));
			if (file.canWrite()) {
				FileWriter writer = new FileWriter(file);
				writer.write(text);
				writer.close();
			}
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static void CopyFile(OutputStream out, InputStream in) throws IOException{
		byte[] buffer = new byte[1024];
		int read = 0;
		while((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		in.close();
	}
	
	public static String getFileText(File file) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String temp = "";
			String str;
			while ((str = br.readLine()) != null) {
				temp+=str+"\n";
			}
			if(temp.length()>0)
			{
				temp=temp.substring(0, temp.length()-1);
			}
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "ERROR:-1";
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
