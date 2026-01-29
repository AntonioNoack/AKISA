package control;

import java.io.IOException;

import knowledge.LiquidWords;
//import knowledge.StaticWords;
import design.Console;
//import research.UnderstandEnglish;
import research.UnderstandHTML;

public class AKISA {
	
	public static ConsoleReader cr;

	public static final String mainfolder = "C:/Users/Antonio/Documents/AKISA/";
	
	public static void main(String[] args) throws InterruptedException{

		ini();
		welcome();

	}
	
	public static void ini(){
		new Thread(new ConsoleReader()).start();
	}
	
	public static void welcome(){
		Console.println("Welcome to AKISA.\nPlease speak english... because otherwise I could not understand you!");
	}
	
	public static void check(String in) throws IOException{
		/*http://www.collinsdictionary.com/dictionary/english/bill*/
		
		// Word = Wort ;)
		
		//boolean question = in.trim().endsWith("?");
		//Console.println(UnderstandEnglish.momentOfDoing/*timeAndSence*/(in, in.split(" ")));
		
		String[] words = in.split(" ");
		int wl=words.length;
		for(int i=0;i<wl;i++){
			
			String ns = words[i];// StaticWords.checkWord(cleanWord(words[i]), question, i);
			
			if(ns.equals(words[i])){//nicht gefunden
				ns = LiquidWords.checkWord(words[i]);
				if(ns.equals(words[i])){
					UnderstandHTML.getInformationAbout(words[i]);
				} else {
					words[i]=ns;
				}
			}else{
				words[i]=ns;
			}
			
			// Prüfe, was das Wort ist und lege daraus eine Abarbeitungsliste an
			Console.print(words[i]+((i==wl-1)?"":" "));
		}System.out.println();//*/
	}
	
	public static String cleanWord(String s){
		char c = s.charAt(s.length()-1);
		if(c<65 || c>122 || (c>90 && c<97) || (c>47 && c<58)){
			s=s.substring(0, s.length()-1);
		}
		
		if(true){
			int i=0;
			System.out.print(i);
		}
		
		return s;
	}
}
