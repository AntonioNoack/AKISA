package knowledge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StaticWords {
	public static String doppelSilbe="ALANARASATEAEDENERESHAHEHIINISITLEMENDNENGNTONOROURESESTTETHTITOVEWA";
	public static String dreierSilbe="ALLANDAREBUTENTERAEREEVEFORHADHATHENHERHINHISINGIONITHNOTOMEOULOURSHOTEDTERTHATHETHITIOULDVERWASWITYOU";
	public static String mainFolder = "C:/Users/Antonio/Documents/AKISA/static/";
	
	public static String checkWord(String word, boolean question, int i){
		word = search(word, question, i);
		return word;
	}
	
	public static String search(String s, boolean question, int i){
		try {
			File file = new File(mainFolder+s.toLowerCase().charAt(0)+".txt");
			FileReader fr = new FileReader(file);
			BufferedReader read = new BufferedReader(fr);
			for(String e;(e=read.readLine())!=null;){
				if(e.startsWith("?")){//Entscheidung zwischen zwei Fällen: Frage oder Antwort
					if(e.split("\\=")[0].equalsIgnoreCase("?"+s)){
						if(e.contains("|")){
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
							read.close();
							fr.close();
									
							return e.substring(1+s.length()).split("\\|")[(question && i==0)?0:1].substring(1);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
						}else{
							System.out.println("Missing |");
						}
					}
				}else{
					if(e.contains("=")){
						if(e.split("\\=")[0].equalsIgnoreCase(s)){
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
							read.close();
							fr.close();
							
							return e.substring(s.length()+1);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
						}
					}else{
						System.out.println("'"+e + "' enthält nicht '=' -> #000");
					}
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
