package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Text {
	static final String text = "Anne im Bett.  Anne ist krank. Sie liegt im Bett. Die Mutter fragt: Magst du lesen?"
			+ "Magst du malen? Anne hat keine Lust. Sie hat keinen Hunger. Sie trinkt lieber Tee. Aber bald ist Anne gesund."
			+ "Dann tobt sie im Haus herum.";
	static final String english = "This is the most complicated sentence you will have ever seen. I swear. Shut up and love your girl!";
	static String x;
	static {
		try {
			BufferedReader read = new BufferedReader(new FileReader(new File("C:/Users/Antonio/Desktop/hp.txt")));
			for(String s="";s!=null;s=read.readLine()){
				try {
					Integer.parseInt(s);
				} catch(NumberFormatException e){
					x+=s+"\n";
				}
			}
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
