package research;

import java.io.IOException;
import java.util.ArrayList;

import research.UEIIBase.Tense;
import data.DataCell;
import design.Logger;
import idea.AKISA;
import idea.Subject;
import idea.Theory;

public class Brain {
	
	public static boolean testall = true;
	
	public static void makeTree(Subject s) {
		
		String x = s.beautifulName();
		if(x.contains(" ") || x.contains("|") || x.contains("<")){
			try {
				ArrayList<Theory> ths = new ArrayList<>();
				// <a|Problem is found>
				// das Subject enthält also mehrere Teile...
				// untersuche diese...
				// !!! 24.04.16
				if(x.startsWith("<")){
					x = x.substring(1, x.length()-1);
				}
				
				if(x.startsWith("a|") || x.startsWith("an|")){
					x = x.substring(x.indexOf('|')+1);
					ths.add(new Theory(s, Subject.BE, Subject.subjectFromString(x), UnderstandEnglishII.def, Tense.PRESENT, UnderstandEnglishII.interest()));
				}
				
				String[] t = x.replace('|', ' ').replace('>', ' ').replace('<', ' ').split("[ |]");
				if(t.length>0){
					for(String q:t){
						if(q.length()>0){
							ths.add(new Theory(s, Subject.CONTAINS, Subject.subjectFromString(q), UnderstandEnglishII.partOfObject, Tense.PRESENT, UnderstandEnglishII.interest()));
						}
					}
				}
				
				for(Theory th:ths){
					addTheory(th, 1);
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void addTheory(Theory toAdd, double weight){
		Theory now = null;
		for(Theory t:toAdd.datacell.memory){
			if(t.equalContents(toAdd)){
				now = t;
				break;
			}
		}
		
		if(now!=null){
			// die Theory existiert schon -> schaue nach Gleichdeutigkeit, sonst sind wir am Arsch...
			// was hat ne Theory eigentlich? Zeitform, Chance, Sicherheit, Object, Bridge, Fact
			float ochance = toAdd.chance, nchance = (now.chance * now.security + toAdd.chance * toAdd.security)/(now.security + toAdd.security);
			if(ochance*nchance<0 && Math.abs(ochance-nchance)>0.3 && now.security>3*toAdd.security && now.security>10){
				// unterschiedliches Vorzeichen -> Problem! -> aber es wird alles einbezogen, da ich ja nicht weis, was auch wahr ist... richtiges wird sich schon durchkämpfen...
				Logger.gedanken.warn("Conflict! Source inreliable! Infos are added trought!\n\t"+toAdd+"\n\t"+now);
			}
			toAdd.chance = nchance;
			toAdd.security+=now.security;
		}
		
		toAdd.datacell.update(toAdd);
	}
	
	public static boolean isList(Subject s){
		String name = s.beautifulName();
		return false;
	}
	
	public static ArrayList<Subject> list(Subject s){
		return null;
	}

	public static Subject getNumber(Subject a) {
		if(a==null){
			return null;
		}
		
		// prüfe die wichtigsten Eigenschaften nach einer Zahl, bzw. equals Zahl...
		ArrayList<Theory> good = new ArrayList<>();
		ArrayList<Subject> next = new ArrayList<>();
		try {
			for(Theory t:DataCell.load(a).memory){
				if((t.bridge.equals(Subject.subjectFromString("be")) || t.bridge.equals(Subject.subjectFromString("equal"))) && t.fact!=null){
					if(AKISA.isNumber(a)){
						good.add(t);
					} else {
						next.add(t.fact);
					}
				}
			}
			
			if(good.size()==0){
				for(Subject s:next){
					for(Theory t:DataCell.load(s).memory){
						if(t.bridge.equals(Subject.subjectFromString("be")) && t.fact!=null){
							if(AKISA.isNumber(a)){
								good.add(t);
							}
						}
					}
				}
			}
			
			if(good.size()==0){
				return null;
			} else {
				Subject best=good.get(0).fact;
				float score = good.get(0).chance;
				for(Theory s:good){
					if(s.chance>score){
						best = s.fact;
					}
				}
				return best;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
