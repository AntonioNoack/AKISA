package math;

import java.util.HashMap;

public class MathHelper {

	public static int max(int a, int b){return a>b?a:b;}
	public static double max(double a, double b){return a>b?a:b;}
	
	public static int min(int a, int b) {
		return a<b?a:b;
	}
	
	public static double abs(double a){
		return a<0?-a:a;
	}
	
	public static long pow(int x, int lenght) {
		long r = x;
		for(int i=1;i<lenght;i++){
			r=r*x;
		}
		return r;
	}
	
	public static int numberWithoutEnding(String s){
		if(s.endsWith("st") || s.endsWith("nd") || s.endsWith("th")){
			s=s.substring(0, s.length()-2);
		}
		return stringToInt(s, 0);
	}
	
	public static int stringToInt(String s, int other) {
		int ret = 0;
		if(s.startsWith("-")){
			s=s.substring(1);
			for(char c:s.toCharArray()){
				if(c>='0' && c<='9'){
					ret = ret*10+c-48;
				} else return other;
			}
			return -ret;
		} else {
			for(char c:s.toCharArray()){
				if(c>='0' && c<='9'){
					ret = ret*10+c-48;
				} else return other;
			}
			return ret;
		}
	}
	
	public static boolean isNumber(String s){
		if(!ini)ini();
		if(s.endsWith("st") || s.endsWith("nd") || s.endsWith("th")){
			s=s.substring(0, s.length()-2);
		}
		if(numbers.containsKey(s)){
			return true;
		}
		for(char c:s.toCharArray()){
			if((c<'0' || c>'9')&&c!='.'){
				return false;
			}
		}
		return true;
	}
	public static HashMap<String, Number> numbers = new HashMap<>();
	public static boolean ini=false;
	public static void ini(){
		ini=true;
		numbers.put("one",	1);
		numbers.put("two",	2);
		numbers.put("three",3);
		numbers.put("four", 4);
		numbers.put("five", 5);
		numbers.put("six",	6);
		numbers.put("seven",7);
		numbers.put("eight",8);
		numbers.put("nine", 9);
		numbers.put("ten", 10);
		numbers.put("eleven", 11);
		numbers.put("twelfe", 12);
		numbers.put("thirteen", 13);
		numbers.put("eighteen", 18);
		numbers.put("twenty", 20);
		numbers.put("thirty", 30);
		
		numbers.put("tousand",	1000);
		numbers.put("tousands", 1000);
		numbers.put("million",	1000000);
		numbers.put("millions", 1000000);
		numbers.put("billion",	1000000000);
		numbers.put("billions", 1000000000);
		numbers.put("trillion", 1000000000000L);
		numbers.put("trillion", 1000000000000L);
	}
}
