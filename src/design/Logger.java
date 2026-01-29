package design;

public class Logger {
	public static Logger ue2 = new Logger(), gedanken = new Logger(), engine = new Logger();
	private Logger(){
		
	}
	
	public void log(String s){
		Console.print("INFO: "+s);
	}
	
	public void warn(String s){
		Console.print("WARN: "+s);
	}
	
	public void error(String s){
		Console.print("ERRR: "+s);
	}
}
