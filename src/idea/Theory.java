package idea;

import java.io.IOException;

import control.ConsoleReader;
import control.Seele;
import data.DataCell;
import design.Logger;
import research.UEIIBase.Tense;
import research.UnderstandEnglishII;

public class Theory {
	// für die Gegenwart
	public static long tempus;
	public long time;
	public Subject object, bridge, fact;
	public Tense tense;
	public float chance = UnderstandEnglishII.def, security = 1;
	public DataCell datacell;
	
	/**
	 * Uses strings as keys, only for recognising text!
	 * @throws IOException 
	 *
	 * @deprecated use {@link #Theory(int, int, int, Tense, long)}} instead.
	 */
	public Theory(String object, String bridge, String fact, float chance, Tense tense, long interest) throws IOException {// float multiplier, boolean not, boolean active, collection of adverbs...
		
		this.object = Subject.subjectFromString(object);
		this.bridge = Subject.subjectFromString(bridge);
		this.fact	= (fact==null || fact.length()==0)?Subject.UNDEF:Subject.subjectFromString(fact);
		
		this.chance=chance;this.tense=tense;time = tempus+interest;datacell=DataCell.load(this.object);
	}

	public Theory(long o, long b, long f, float chance, Tense tense, long interest, DataCell cell){
		
		object = new Subject(o);
		bridge = new Subject(b);
		fact   = new Subject(f);
		
		this.chance = chance;
		this.tense=tense;time = tempus+interest;datacell=cell;
	}

	public Theory(Subject object, Subject bridge, Subject fact, float chance, Tense tense, int interest) throws IOException {
		this.object=object;
		this.bridge=bridge;
		this.fact=fact;
		this.chance=chance;this.tense=tense;time = tempus+interest;datacell=DataCell.load(this.object);
	}

	@Override public String toString(){
		return object.beautifulName() +
				"-" + bridge.beautifulName() +
				">" + (fact==null?"":fact.beautifulName()) +
				" " + (int)(chance*100)+"%";
	}
	
	@Override public boolean equals(Object o){
		if(o instanceof Theory){
			return equalContents((Theory)o);
		} else return false;
	}

	public boolean equalContents(Theory t) {
		return object.equals(t.object) && 
				(bridge==null?t.bridge==null:bridge.equals(t.bridge)) && 
				(fact==null?t.fact==null:fact.equals(t.fact)) && tense.equals(t.tense);
	}
	
	/**
	 * das eigentliche Nachdenken, Verarbeiten der Daten, auch wenn noch nicht darin stöbern
	 * ...
	 * AKISA-have>developer 90%
	 * AKISA's developer-be><|Antonio Noack> 90%
	 * AKISA-be><an|artificial intelligence> 90%
	 * 
	 * London-be><the|most populous city> 50%
	 * London-be><the|most populous city><in the|United Kingdom><with a|metropolitan area<of|over 13 million inhabitants>> 90%
	 * */
	public static void add(Theory toAdd, double weight){
		
		if(toAdd.datacell==null){
			// !!! kA... tja...
			System.out.println("can't add...");
			return;
		}
		
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
		Seele.onInfoFound(toAdd, ConsoleReader.YOU);
	}
}
