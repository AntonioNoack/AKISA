package knowledge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {	
	public static String mainFolder = "C:/Users/Antonio/Documents/AKISA/liquid/";

	public static void write(String topic, String[] data){
		char[] c = Coder.code(data);
		try {
			FileWriter fw = new FileWriter(new File(mainFolder+topic.toLowerCase().charAt(0)+"/"+topic.toLowerCase()));
			
			fw.write(c);
			fw.flush();
			fw.close();
			
		} catch (IOException e) {System.out.println(e.getLocalizedMessage());}
	}
}