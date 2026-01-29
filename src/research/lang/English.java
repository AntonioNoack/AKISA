package research.lang;

import java.io.File;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import research.UnderstandHTML;
import data.AKISAData;

public class English extends Sprache {

	public English() {
		super("English");
	}

	@Override public String infinitive(String s) {
		switch(s){
		case "am":
		case "is":
		case "are":
		case "been":
		case "was":
		case "were":
			return "be";
		case "has":
			return "have";
		case "this":
			return "this";
		case "named":
		case "naming":
			return "name";
		case "making":
			return "make";
		case "regulated":
			return "regulate";
		case "found":
			return "find";
		case "using":
		case "used":
			return "use";
		case "aimed":
			return "aime";
		}
		String e = "";
		if(s.endsWith("ed")){
			if(s.endsWith("ied")){
				return s.substring(0, s.length()-3)+"y";
			} else if(s.length()>6 && s.charAt(s.length()-3)==s.charAt(s.length()-4)){//doppelter Laut am Ende sollte ja gestrichen werden... referred -> refer
				return s.substring(0, s.length()-3);
			} else {
				return s.substring(0, s.length()-2);
			}
		} else if(s.endsWith("ing")){
			return removeIng(s);
		}
		return (e=regularVerbform(s))==null?(s.endsWith("s")?s.substring(0, Math.max(s.length()-1, 0)):s):e;
	}
	
	@Override public boolean isVerb(String verb) {
		verb = verb.toLowerCase().replaceAll("[^a-z]", "");
		
		File f = new File(mainfolder+"oth/"+verb+".oth");
		if(f.exists()){
			return false;
		}
		
		f = new File(mainfolder+"ver/"+verb+".ver");
		if(f.exists()){
			return true;
		}
		
		Document c = UnderstandHTML.getDataFromURL("http://wordweb.info/developer/SQL/search.pl?w="+verb);
		Elements es =c.select("B");
		boolean b=false;
		for(Element e : es){
			if(e.text().equals("Verb: "+verb)){
				try {
					f.createNewFile();
				} catch (IOException e1) {System.out.println("NO DATA WRITABLE: UnderstandEnglish: isVerb? - "+verb);}
				b = true;
				break;
			}
		}
		if(!b){
			f = new File(mainfolder+"oth/"+verb+".oth");
			try {
				f.createNewFile();
			} catch (IOException e1) {System.out.println("NO DATA WRITABLE: UnderstandEnglish: isVerb? - !"+verb);}
		}
		return b;
	}
	
	private String removeIng(String s){
		s=s.substring(0, s.length()-3);
		if(s.equals("rid") || s.equals("lov"))return s+"e";// es gibt wohl noch wesentlich mehr, aber ich kenne leider keine Quelle für diese bzw. bin zu faul ;)
		return s;
	}
	
	private String regularVerbform(String s){
		for(String line:AKISAData.irregularVerbs){
			if(line.contains(s)){
				if(line.endsWith("."+s) || line.contains("."+s+".") || line.contains("/"+s+".") || line.contains("."+s+"/")){
					return line.split("\\.")[0];
				}
			}
		}
		return null;
	}

	

}
