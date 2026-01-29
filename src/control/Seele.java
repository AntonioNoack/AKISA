package control;

import research.Combination;
import research.Combination.C;
import research.Combination.S;
import idea.Subject;
import idea.Theory;

/**
 * Selbstreflektion, ob und wie es am besten ist, Infos zu übergeben
 * Die Frage nach der AuW ist ehr ein gesellschaftlicher Lernprozess, aber die Aufgabe muss wohl ich als Vater übernehmen...
 * warte dafür war das I ja da... geht also schon...
 * */
public class Seele {
	public static final Subject I = Subject.I;
	public static void onInfoFound(Theory subject, Subject partner){
		/*if(containsME(subject.object)){// Ich bin/tue etwas muss nicht untersucht werden, da es schon wurde; taucht es hier auf, soll es als richtig betrachtet werden... oje...
			System.out.println("me");
		} else {
			System.out.println("notme");
			// es bin nicht ich -> wie sieht es mit Combination aus? Darf man soetwas sagen?
			ArrayList<C> c = Combination.thoughtsAbout(new Theory(partner.i, subject.object.i, -1, 0.7f, Tense.PRESENT, 0L, null));
			if(c.size()<1){
				
			} else {
				// Hau raus xD
				for(C a:c){
					System.out.println("-> ");
					for(S s:a.way){
						s.t.toString();
					}
				}
			}
		}*/
		for(C a:Combination.thoughtsAbout(subject)){
			for(S s:a.way){
				System.out.println(s.t.toString());
			}
		}
	}
	
	static boolean containsME(Subject s){
		return false;// !!!
	}
}
