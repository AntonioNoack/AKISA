package control;

import idea.Subject;
import idea.Theory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import research.UnderstandEnglishII;
import research.UnderstandHTML;
import data.Memory;

public class ConsoleReader implements Runnable {
	
	public static Subject YOU = Subject.subjectFromString("Antonio Noack");
	
	@Override
	public void run(){
		try {
			Memory.awake();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		try {
			String line = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				line = in.readLine();
				if(line.equalsIgnoreCase("save")){
					
					try {
						Memory.sleep();
						Memory.awake();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} else if(line.equalsIgnoreCase("quit")){
					break;
				} else if(line.startsWith("i ")){
					UnderstandHTML.getInformationAbout(line.substring(2));
				} else if(line.endsWith("?")){
					for(Theory t:new UnderstandEnglishII(line).toTheory()){
						Seele.onInfoFound(t, Subject.subjectFromString("Antonio Noack"));
					}
				} else {
					for(Theory t:new UnderstandEnglishII(line).toTheory()){
						Theory.add(t, 1.0);
					}
				}
			}
			System.out.println("\nThanks for your visit!");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Memory.sleep();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
