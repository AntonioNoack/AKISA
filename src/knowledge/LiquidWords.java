package knowledge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LiquidWords {
	
	public static String mainFolder = "C:/Users/Antonio/Documents/AKISA/liquid/";
	
	public static String checkWord(String word){
		return search(word);
	}
	
	// suche einfach ein Wort: gibt es für es ein Synonym? -> suche später dann danach
	// Synonyme sind im Moment noch als London-NewYork abgelegt
	//		wann wäre AKISA denn fähig selber Synonyme festzulegen? -> nur mit Duden
	public static String search(String s){
		if(s.length()<2){
			System.out.println("\nto short word: "+s);
			return s;
		}
		
		try {
			(new File(mainFolder+s.toLowerCase().charAt(0))).mkdir();
			
			File file = new File(mainFolder+s.toLowerCase().charAt(0)+"/"+s.toLowerCase()+".txt");
			if(!file.exists())file.createNewFile();
			
			FileReader fr = new FileReader(file);
			BufferedReader read = new BufferedReader(fr);
			String e;
			if((e=read.readLine())!=null){
				if(e.split("-").length>0 && e.split("-")[0].equalsIgnoreCase(s)){
					
					read.close();
					fr.close();
						
					return e.substring(s.length()+1);
				}
			}
			
			read.close();
			fr.close();
			
		} catch (IOException e) {
			System.out.println("IO Exception in StaticWords.search while looking for '"+s+"' -> #001");
		}
		
		return s;
	}
}
