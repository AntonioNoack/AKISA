package research.lang;

import control.AKISA;

public abstract class Sprache {
	
	protected static final String mainfolder = AKISA.mainfolder+"liquid/";
	
	public final String name;
	public Sprache(String name){
		this.name=name;
	}
	
	public abstract String infinitive(String s);
	public abstract boolean isVerb(String s);
}
