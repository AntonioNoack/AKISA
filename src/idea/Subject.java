package idea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import research.Brain;
import control.AKISA;
import data.Hash;

public class Subject {
	
	public static final Subject
			I = subjectFromString("AKISA"),
			BE = subjectFromString("be"),
			CONTAINS = subjectFromString("contain"),
			UNDEF = new Subject(idea.AKISA.numberReserved | Float.floatToRawIntBits(Float.NaN), "undef");
	
	public final long i;
	public String s;
	public Subject(long i){
		this.i=i;
	}
	
	public Subject(long l, String n){
		this(l);
		s = n;
	}
	
	public String simpleName(){
		return s==null?Long.toString(i):s;
	}
	
	public String beautifulName(){
		if(s!=null){
			return s;
		} else {
			File f = new File(AKISA.mainfolder+"database/i/"+file(i)+".nme");
			if(f.exists()){
				try {
					FileInputStream read = new FileInputStream(f);
					byte[] data = new byte[(int) f.length()];
					read.read(data);
					read.close();
					return this.s = new String(data);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		}
	}
	
	public static Subject subjectFromString(String s){
		if(s==null || s.length()==0){
			throw new RuntimeException("illegal subject name");
		}
		
		if(s.startsWith("<|")){
			s = s.substring(2, s.length()-1);
		}
		
		if(s.equals("you")){
			return I;
		}
		
		// wenn eine Zahl
		boolean zahl=true;
		for(char c:s.toCharArray()){
			if((c<'0' || c>'9') && c!='.' && c!=','){
				zahl=false;
				break;
			}
		}
		
		if(zahl){
			return new Subject(idea.AKISA.numberReserved | Float.floatToRawIntBits(Float.parseFloat(s)), s);
		}
		
		long key = Hash.code(s) & 0x7fffffffffffffffL;
		Subject ret = new Subject(key, s);
		File f = new File(AKISA.mainfolder+"database/i/"+file(key)+".nme");
		if(!f.exists()){
			f.getParentFile().mkdirs();
			try {
				FileWriter fw = new FileWriter(f);
				fw.write(s);
				fw.close();
				
				Brain.makeTree(ret);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(Brain.testall){
			Brain.makeTree(ret);
		}
		return ret;
	}
	
	public boolean equals(String s){
		try {
			return i == Float.floatToRawIntBits(Float.parseFloat(s));
		} catch(NumberFormatException e){
			return (Hash.code(s) & 0x7fffffffffffffffL) == i;
		}
	}
	
	@Override public boolean equals(Object o){
		if(o instanceof Subject){
			return ((Subject)o).i==i;
		}
		return false;
	}
	
	@Override public int hashCode(){
		return (int) (i ^ (i<<32));
	}
	
	@Override public String toString(){
		return beautifulName();
	}

	/**
	 * durch ints nummeriert...
	 * */
	public File file() {
		return new File(AKISA.mainfolder+"database/knowledge/"+file(i)+".aki");
	}
	
	private static String file(long i){
		return ((i>>48)&0xffff)+"/"+((i>>32)&0xffff)+"/"+((i>>16)&0xffff)+"/"+((i)&0xffff);
	}
	
}
