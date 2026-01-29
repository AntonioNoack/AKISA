package design;

import java.util.Random;

public class Console {
	
	public static Random r = new Random();
	
	public static void schreibeBlocktext(String s, int mlenght){
		String ns="";s+=" ";
		for(int i=0;i<s.length();i++){
			while(s.charAt(i)=='['){
				while(s.charAt(i++)!=']'){}
				i++;
			}
			ns+=s.charAt(i);
		}
		s = ns.replace("  ", " ");
		
		for(int i=0;i<mlenght;i++){
			System.out.print("-");
		}System.out.println();
		String[] ss=s.split(" ");
		int i=0;
		for(String ms:ss){
			i+=ms.length();
			if(i>mlenght){
				System.out.println();
				i=0;
			}else{
				print(" ");
			}
			print(ms);
		}
		System.out.println();
	}
	
	public static void print(String s){
		for(char c:s.toCharArray()){
			System.out.print(c);
			try {
				Thread.sleep(r.nextInt(3)*10);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public static void println(String s){
		System.out.println(s);
		//print(s);
		//System.out.println();
	}
}
