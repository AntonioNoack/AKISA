package research;

import java.io.File;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import math.MathHelper;
import knowledge.Coder;
import data.AKISAData;

public class UnderstandEnglish extends MathHelper{
	public static String mainFolder = "C:/Users/Antonio/Documents/AKISA/liquid/";
	
	@SuppressWarnings("unused")
	public static void maidn(String[] args){
		String[] ss = new String[]{
				"London is the capital city of England and the United Kingdom.",
				"It is the most populous city in the United Kingdom, with a metropolitan area of over 13 million inhabitants.",
				"Standing on the River Thames, London has been a major settlement for two millennia, its history going back to its founding by the Romans, who named it Londinium.",
				"London's ancient core, the City of London, largely retains its 1.12-square-mile (2.9 km2) mediaeval boundaries and in 2011 had a resident population of 7,375, making it the smallest city in England.",
				"Since at least the 19th century, the term London has also referred to the metropolis developed around this core.",
				"The bulk of this conurbation forms the Greater London administrative area (coterminous with the London region), governed by the Mayor of London and the London Assembly.",
				
				"A monarch is the sovereign head of state, officially outranking all other individuals in the realm.",
				"A monarch may exercise the most and highest authority in the state or others may wield that power on behalf of the monarch.",
				"Typically a monarch either personally inherits the lawful right to exercise the state's sovereign rights (often referred to as the throne or the crown) or is selected by an established process from a family or cohort eligible to provide the nation's monarch. Alternatively, an individual may become monarch by conquest, acclamation or a combination of means.",
				"A monarch usually reigns for life or until abdication.",
				"Monarchs' actual powers vary from one monarchy to another and in different eras; on one extreme, they may be autocrats (absolute monarchy) wielding genuine sovereignty; on the other they may be ceremonial heads of state who exercise little or no power or only reserve powers, with actual authority vested in a parliament or other body (constitutional monarchy).",
				"I love the girl Ali.",
				
				"In the context of human society, a family (from Latin: familia) is a group of people affiliated by consanguinity (by recognized birth), affinity (by marriage), or co-residence and/or shared consumption (see Nurture kinship).",
				"Members of the immediate family may include, singularly or plurally, a spouse, parent, brother, sister, son and/or daughter.",
				"Members of the extended family may include grandparents, aunts, uncles, cousins, nephews nieces and/or siblings-in-law.",
				"In most societies, the family is the principal institution for the socialization of children.",
				"As the basic unit for raising children, anthropologists generally classify most family organization as matrifocal (a mother and her children); conjugal (a husband, his wife, and children; also called the nuclear family); avuncular (for example, a grandparent, a brother, his sister, and her children); or extended (parents and children co-reside with other members of one parent's family). Sexual relations among the members are regulated by rules concerning incest such as the incest taboo.",
				"\"Family\" is used metaphorically to create more inclusive categories such as community, nationhood, global village and humanism.",
				"Genealogy is a field which aims to trace family lineages through history.",
				"Family is also an important economic unit studied in family economics.",
		};
		
		for(String s:ss){
			s=s.replace(", ", " , ").replace(". ", " ");// , ist ein neues Wort
			
			if(s.endsWith(".")){
				s=s.substring(0, s.length()-1);
			}

			
			String[] key = s.split(" ");
			String[] ckey = s.split(" ");
			
			int[] data = new int[key.length];
			
			int i=0;
			if(key[0].endsWith("ing")){
				//Standing on the River Thames, the City of London,...
				// = the City of London <is standing> on the River Thames
				// = London.City <stand> on River_Thames
				
				//was ist eigentlich das Format?
				//London.txt
				//'City'stand<on>'River Thames'
				//S=subject, V=verb, C=conditions
				String S, V, C="";
				V=key[0].substring(0,key[0].length()-3).toLowerCase();
				while(!key[++i].equals(",")){
					//es folgt eine Reihe von Worten bis zum Komma
					C+=key[i]+" ";
				}
				i++;
				
				if(key[i].equals("the")){i++;}
				
				/////////////////////////////
				// find Subject
				
				//echtes Wort
				if(key[i].charAt(0)<91){
					S=key[i];
				}else{
					S="noch unbekannt";
					//adjektive?
				}
				
				i++;
				
				if(key[i].equals("of")){
					C+=" of "+C;
				}
				
				System.out.println("'"+S+"'"+V+C);
				
			}else if(key[0].endsWith("ly") && !contains(key[0].toLowerCase(),"apply bely comply dally imply multiply ply rely reply supply ally anomaly assembly belly bully dolly doily family -fly gully hillbilly holly homily jelly lily monopoly panoply potbelly rally reply supply tally underbelly".split(" "))){// 
				System.out.println("ly @ "+s);
			}
			
			//time subject verb object obejct object time
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//			data: um Ordnung zu schaffen, werden die einzelnen Teile des Satzes analysiert
			//
			//	S																P			O
			//	London															is			the capital city of England and the UK.
			//	It																is			the most populous city in the United Kingdom, with...
			//	Standing on the River Thames, the City of London, large[ly]		retains		its 1.12...
			//
			//
			
			
			
			
			/////////////////////////////////////////
			//	finde Prepositionen
		/*	for(int i=0;i<key.length;i++){
				switch(key[i]){
				case "a":
				case "in":
				case "at":
				case "an":
				case "by":
				case "the":
				case "on":
				case "up":
				case "til":
				case "until":
				case "less":
				case "more":
				case "for":
				case "with":
				case "without":
				case "this":
				case "to":
				case "of":
					data[i]= 2;
					break;
				case "and":
					data[i]= 4;
					break;
				case ",":
					data[i]= 1;
					break;
				}
			}
			
			
			/////////////////////////////////////////
			//			finde Prädikate
			int i=-1, fi;
			is:for(fi=0;fi<key.length;fi++){
				for(int k=0;k<5;k++){
					if(fi>=key.length)break is;
					fi+=wordNeedsOthers(key[fi], fi<key.length-1?key[fi+1]:"");
				}
				if(data[fi]==0){
					key[fi] = clearForVerb(infinitiv(key[fi]));
					if(isVerb(key[fi]) && (fi>ckey.length-2 || data[fi+1]!=1)){
						data[fi] = 3;
						if(i==-1){
							i=fi;
						}
					}
				}
			}
			
			System.out.println(s);
			for(i=0;i<key.length;i++){
				String fv="";
				while(i<ckey.length && data[i]==3){
					fv+=ckey[i]+" ";
					i++;
				}
				
				if(!fv.equals("")){
					while(fv.endsWith(" "))fv=fv.substring(0, fv.length()-1);
					System.out.print("["+timeAndSence(fv,fv.split(" "))+"] ");
				}else System.out.print(ckey[i]+"{"+data[i]+"} ");
			}System.out.println("\n");*/
		}
	}
	
	static boolean contains(String s, String[] in){
		for(String m:in){
			if(m.startsWith("-") && s.endsWith(m.substring(1))){
				return true;
			}
			if(s.equals(m)){
				return true;
			}
		}
		return false;
	}
	
	static String clearForVerb(String s){
		if(s.endsWith(".") || s.endsWith(",") || s.endsWith("?")){
			s=s.substring(0, s.length()-1);
		}
		for(char c:s.toCharArray()){
			if(c<97 || c>122){
				return "";
			}
		}
		return s;
	}
	
	static int wordNeedsOthersHelp(String s){
		switch(s.toLowerCase()){
		case "at":
		case "a":
		case "an":
		case "to":
		case "with":
		case "the":
		case "long":
		case "this":
		case "on":
		case "of":
		case "in":
			return 2;
		case "within":
		case "its":
		case "for":return 3;
		}
		return 0;
	}
	
	static int wordNeedsOthers(String first, String following){
		int i, j;
		if((i=wordNeedsOthersHelp(first))!=0){
			if((j=wordNeedsOthersHelp(following))!=0){
				return j;
			}return i;
		}
		return 0;
	}
	
	static boolean isVerb(String verb){
		
		verb = infinitiv(verb);
		verb = verb.toLowerCase().replaceAll("[^a-z]", "");
		
		File f = new File(mainFolder+"oth/"+verb+".oth");
		if(f.exists()){
			return false;
		}
		
		f = new File(mainFolder+"ver/"+verb+".ver");
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
			f = new File(mainFolder+"oth/"+verb+".oth");
			try {
				f.createNewFile();
			} catch (IOException e1) {System.out.println("NO DATA WRITABLE: UnderstandEnglish: isVerb? - !"+verb);}
		}
		return b;
	}
	
	static String lastSubject, lastTime="000";
	@SuppressWarnings("unused")
	public static String[] findLogics(String topic, String in){
		
		String[][] s = textblockToLines(in);
		String[] fin = new String[s.length*6];int finS = 0;
		
		
		
		for(String[] line : s){
			// one line = one sentence
			String sentence = "";
			
			for(String my:line){
				sentence+=my+" ";
			}
			
			// finde Zeit, Subjekt, Prädikat, indObejkt, dirObjekt, Ort und Zeit; Nebensatz
			//		Zeit und Ort können auch mehrfach auftreten!
			
			/*	 The gray wolf (Canis lupus[a]) also known as the timber wolf,[3][4] or western wolf,[b] is a canid native to the wilderness and remote areas of
				North America, Eurasia, and northern, eastern and western Africa. It is the largest extant member of its family, with males averaging 43â€“45 kg
				(95â€“99 lb), and females 36â€“38.5 kg (79â€“85 lb).[6] Like the red wolf, it is distinguished from other Canis species by its larger size and less pointed
				features, particularly on the ears and muzzle.[7] Its winter fur is long and bushy, and predominantly a mottled gray in color, although nearly pure
				white, red, or brown to black also occur.[4] As of 2005[update],[8] 37 subspecies of C. lupus are recognised by MSW3. The nominate subspecies is the
				Eurasian wolf (Canis lupus lupus),[9] also known as the common wolf.[10]
			 */
			//		|
			//		V
			//
			/*	Wolf:  = The gray wolf = Canis lupus = timber wolf = western wolf
			 * 		a:is:present A canid_native TO the wilderness and remote areas of N-America, EuAsia, N+O+W-Africa
				The gray wolf (Canis lupus) also known as the timber wolf, or western wolf, is a canid native to the wilderness and remote areas of North America, Eurasia, and northern, eastern and western Africa.
			 *		It->Wolf a:is:present largest extant member of its->Wolf´s family
			 *			with males averaging 43-45kg=95-99lb
			 *			and females  averaging 36-38.5kg=79-85lb
				It is the largest extant member of its family, with males averaging 43-45 kg (95-99 lb), and females 36-38.5 kg (79-85 lb).
				Like the red wolf, it is distinguished from other Canis species by its larger size and less pointed features, particularly on the ears and muzzle.
				Its winter fur is long and bushy, and predominantly a mottled gray in color, although nearly pure white, red, or brown to black also occur.
				As of 2005, 37 subspecies of C. lupus are recognised by MSW3.
				The nominate subspecies is the Eurasian wolf (Canis lupus lupus), also known as the common wolf.
		 */
			
			for(String as:line){
				if(as.equalsIgnoreCase(topic)){
					lastSubject = topic;
				}
			}
			
			
			// London is the capital city of England
		}
		
		//return new String[]{"London","= capital city OF England and the UK",...,
		//							"(has) /HISTORY",
		//								"going back to its(London) founding by the romans, who named it Londium",...,
		//							"/HISTORY",
		//							"(has) /ANCIENT CORE",...,
		//								"(had) //POPULATION",
		//									"of 7,375 in 2011",
		//									"making it(ancient core) the smallest city in England",
		//								"//POPULATION",
		//							"/ANCIENT CORE"}
		

		// Ben's mother, Felicity, gave me a present. !
		// = the mother of ben = felicity
		// felicity gave me a present
		
		
		return null;
	}
	
	/*
	 * Satzbau:
	 * 
	 * HS		time + S + P + indO + dirO + place + time
	 * 			time:	on the ... of
	 * 					in the year 452
	 * 					in summer/1985
	 * 
	 * 				   S:	I/he/she/it/you/we/you/they
	 * 						Persons / Objects
	 * 							-> topic or new subObject
	 * 
	 * 					   P:	HV + ever + VV
	 * 
	 * 						   indO:
	 * 
	 * 								  dirO:
	 * 										 place:
	 * NS		Konjunktion(because) - || -
	 * */
	
	public static boolean isTimePreposition(String s){
		if("/at/in/on/in/within/during/".contains("/"+s+"/") || isNumber(s))return true;
		return false;
	}
	
	public static boolean isNumber(String s){
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	public static String placeOfDoing(String p, String[]ps){
		
		
		
		
		return "X.X";
	}
	
	public static String momentOfDoing(String p, String[]ps){
		
		//
		// Möglichkeiten:
		//	000 keine Zeitabgabe
		//	001	Zeitpunkt
		//	010	Zeitpunkt bis Zeitpunkt (immer inklusiv)
		//	011	Zeitdauer
		//	100	Zeitpunkt bis ?								(irgendwann -> später im Text genannt? -> Problem? -> mal gucken)
		//	101	? bis Zeitpunkt								(am besten letzter Zeitpunkt)
		//	110	Zeitdauer pro Zeitdauer: Prozentigkeit mit max.Wert
		//
		//	die Zeit endet mit 0; endet sie mit 1 wird eine zweite Zeitbedingung angehangen
		//
		//	Zeitpunkt:						  	 3Bit	ID:	+	Länge:
		//		Mo/Di/Mi/Do/Fr/Sa/So					000		fest: 000 bis 110
		//		14:20:02.001							001		fest: 24*60*60*1000 -> 27 Bit -> Aufgerundet auf 32 Bit: 
		//																					10010110100101...0101 * 24h / 2^32-1
		//		12.01.-456								010		fest: 32 Tage -> 5 Bit + 16 Monate = 4 Bit + Zahl von -4.500.000.000a bis +irgendwas -> ~16Mrd. = 34 Bit --> sind 43 Bit
		//		Christmas/Easter +/- 365T23:45:12		011		ID für Festtage: nach Liste: max 1024 Tage = 10 Bit + 512 Tage = 9 Bit + 17 Bit für die Uhrzeit: 10010100100...1 * 24h / 2^17-1 --> 36 Bit
		//		morning/noon/afternoon/evening/night	100		 + early/normal/late -> 000 bis 100 + 00 bis 10 = 5 Bit
		//		...(school etc)							...		später...
		//		
		//	Zeitdauer:
		//		0 bis 1048576 = 20 Bit * 10^x Sekunden -> von -128 bis 127 (byte) -> 28 Bit
		//
		//	Prozentigkeit(1sek pro Jahr + maxWert)
		//		0 bis 1048576 / 1048576 * 100%
		//								maxWert nach Zeitpunkt
		
		switch(ps[0]){
		case "at":
			if(ps.length==1)return "nix";
			if(ps.length==2 && !isNumber(ps[1]) && !p.contains(":")){
				if(ps[1].equals("night")){// at night (sunset - sunrise)
					return "001:10001";
				}else{// at Christmas / Easter
					return searchforDate(ps[1]);//Christmas -> 24.12. etc. ...
				}
			}else if(ps.length==3 && ps[2].equals("night")){
				switch(ps[1]){// viele viele Adjektive: finde heraus, was das jeweilige heißt...
				case "early":return "001:10000";
				case "deep": return "001:10001";
				case "late": return "001:10010";
				}
				System.out.println("#night");
			}else if(p.equals("at the same time")){// at the same time
				return lastTime;
			}else if(p.startsWith("at the end of")){// at the end of the week
				System.out.println("#end of");
			}else if(p.startsWith("at the beginning of")){
				System.out.println("#beginning");
			}else if(isNumber(ps[1]) && (ps.length==2 || (ps.length==3 && ps[2].equals("o`clock")))){// at 6 o`clock
				return "001:"+Coder.boolsFromInt(Math.round(Integer.parseInt(ps[1])*178956970.625f), 32);
			}else{
				p=p.replace(":", " ");
				ps=p.split(" ");
				if(isNumber(ps[1]) && isNumber(ps[2]) && (ps.length==3 || !isNumber(ps[3]))){// at 7:45 am/pm/a.m./p.m.
					if(ps.length==3){
						return "001:"+(Coder.boolsFromInt(Math.round((Integer.parseInt(ps[1])*60+Integer.parseInt(ps[2]))*2982616.1770833f), 32));
					}else if(p.endsWith("am") || p.toLowerCase().endsWith("a.m.")){
						return "001:"+(Coder.boolsFromInt(Math.round((Integer.parseInt(ps[1])*60+Integer.parseInt(ps[2]))*2982616.1770833f), 32));
					}else if(p.endsWith("pm") || p.toLowerCase().endsWith("p.m.")){
						return "001:"+(Coder.boolsFromInt(Math.round((Integer.parseInt(ps[1])*60+720+Integer.parseInt(ps[2]))*2982616.1770833f), 32));
					}
				}
			}
			// (Zeit)
		case "on":
			switch(ps[1].toLowerCase().replace(".","")){// on Sunday / Friday
			case "monday":
			case "mon":
				return "000:000";
			case "tuesday":
			case "tue":
			case "tues":
				return "000:001";
			case "wednesday":
			case "wed":
				return "000:010";
			case "thursday":
			case "thu":
			case "thur":
			case "thurs":
				return "000:011";
			case "friday":
			case "fri":
				return "000:100";
			case "saturday":
			case "caturday":
			case "sat":
				return "000:101";
			case "sunday":
			case "sun":
				return "000:110";
			}
			if(ps.length==3){
				String s="010:"+Coder.boolsFromInt(MathHelper.numberWithoutEnding(ps[1]), 5);
				switch(ps[2]){// on 25th December
				case "january":
					return s+"0000";
				case "february":
					return s+"0001";
				case "march":
					return s+"0010";
				case "april":
					return s+"0011";
				case "may":
					return s+"0100";
				case "june":
					return s+"0101";
				case "july":
					return s+"0110";
				case "august":
					return s+"0111";
				case "september":
					return s+"1001";
				case "october":
					return s+"1010";
				case "november":
					return s+"1011";
				case "december":
					return s+"1100";
				}
			}
			
			
			// on Good Friday / Easter Sunday / my birthday
			// on the morning of September 11th
			// (Zeit) -> on the internet? -> Zeit ist dann, wenn eine zeitliche Einheit auftritt: Wochentage, Monate, Jahre oder Zahlen / am und pm
		case "in":
			switch(ps[1]){// on 25th December
			case "january":
				return "0000";
			case "february":
				return "0001";
			case "march":
				return "0010";
			case "april":
				return "0011";
			case "may":
				return "0100";
			case "june":
				return "0101";
			case "july":
				return "0110";
			case "august":
				return "0111";
			case "september":
				return "1001";
			case "october":
				return "1010";
			case "november":
				return "1011";
			case "december":
				return "1100";
			}
			// in July
			// in September
			// in 1985
			// in summer
			// in the summer of 69
			// in the morning / afternoon / evening
			// in a minute / in two weeks
			// (Zeit oder Zeitdauer)
		case "within":
		case "for":
			return "20Bit + 10^Byte Seconds";
			// within a day
			// (Zeitdauer)
		/////////////////////////////
			// for three weeks
			// (Zeitdauer)
		case "during":
			// during the holidays
			// (Zeit bis Zeit)
		case "since":
		case "after":
			// after school
			// (ab Zeit)
		////////////////////////////
			// since Monday
			// since 1985
			// (ab Zeit)
		case "before":
			// before Christmas
			// (bis Zeit-1)
		case "between":
			// between Monday and Friday
			// between 12 am and 12 pm exclusive
			// between 1523 and 1645 inclusive
			// (Zeit bis Zeit - immer inklusiv, wenn nicht genannt)
		case "by":
			// by Thursday
			// (bis Zeit)
		case "from":// to / till / until
			// from Monday to / till / until Wednessday
			// from 1922 to 2013
			// from 15th December 1245 to 31th December
			// (von Zeit zu Zeit)
		case "till":
			// till tomorrow
			// (bis)
		case "until":
			// until the day after tomorrow
			// (bis)
		case "up":
			if(ps[1].equals("to")){
				
			}
			// up to 6 hours a day
			// up to 21 milliseconds a 1000 years
			// (Zeit pro Zeit)
	///////////////////////////////////////////////////////////////////////////////////////////////////////
		//case "ago":
			// 6 years ago
		//case "past":
			// 23 minutes past 8 (8:23)
		//case "to":
			// 23 minutes to 5 (4:37)
		}
		
		return "nix";
	}
	
	public static String searchforDate(String s) {
		return s;
	}

	public static String[][] textblockToLines(String in){
		String[][] r = new String[in.split(" ").length][1200];// 200 = max Satzlänge
		
		int i = 0, j = 0;
		
		for(String s:in.split(" ")){
			if(s.length()>0 && s.charAt(s.length()-1)=='.'){
				r[i++][j]=s.substring(0, s.length()-1);
				j = 0;
			}else{
				r[i][j++]=s;
			}
		}
		
		String[][] ret = new String[i][1200];
		for(;i>0;i--){
			for(j=0;j<1200;j++){
				ret[i-1][j] = r[i-1][j];
			}
		}
		
		return ret;
	}
	
	public static String timeAndSence(String p, String[] ps){
		/*
		
		... is able TO inf.... -> not ok
		 ... have TO inf.... -> ok
		 
		man gehe davon aus, das Gegenüber könne perfektes Englisch sprechen(sonst wird es wohl unmöglich bzw. ich brauche Beispiele.)
		
		reTurn =: activ/passiv : is;has;(infinitiv) : past;past-perfect;...
		
		
		 * */
		boolean negate = false;
		
		while(p.contains(" not")){
			p = p.substring(0, p.indexOf(" not"))+p.substring(p.indexOf(" not")+4);
			ps = p.split(" ");
			negate = !negate;
		}
		while(p.contains("n`t ")){
			p = p.replace("n`t "," ");
			ps = p.split(" ");
			negate = !negate;
		}
		
		// tja...
		if(p.contains("must ")){
			p = p.replace("must ","have to ");
			ps = p.split(" ");
		}else if(p.contains("should ")){
			p = p.replace("should ","should to ");
			ps = p.split(" ");
		}else if(p.contains("may ")){
			p = p.replace("may ","may to ");
			ps = p.split(" ");
		}else if(p.contains("could ")){
			p = p.replace("could ","would can to ");
			ps = p.split(" ");
		}
		
		String reTurn = "unknown: maybe present:" + p,end="",s;
		if(p.contains(" to ")){
			end = p.substring(p.indexOf(" to ")).replace(" to ", ">");
			p = p.substring(0, p.indexOf(" to "));
			ps = p.split(" ");
		}
		
		switch(ps[0]){
		
		case "am"  :
		case "are":
		case "is":
			if(ps.length==1){
				reTurn = "a:be:present";
			}else if(p.contains("ing")){
				if(ps.length==2){
					// is calling
					reTurn = "a:"+removeIng(ps[1])+":present progressive";
				}else if(ps.length==3){// or is/was being called
					reTurn = "p:"+infinitiv(ps[2])+":present progressive";
				}
			}else{
				reTurn = "p:"+infinitiv(ps[1])+":present";
			}
			break;
		case "were":
		case "was":
			if(ps.length==1){
				reTurn = "a:be:past";
			}else if(p.contains("ing")){
				if(ps.length==2){
					reTurn = "a:"+removeIng(ps[1])+":past progressive";
				}else if(ps.length==3){
					reTurn = "p:"+infinitiv(ps[2])+":past progressive";
				}
			}else{
				reTurn = "p:"+infinitiv(ps[1])+":past";
			}
			break;
		case "have":
		case "has":
			if(ps.length==1){
				reTurn = "a:have:present";
			}else if(ps[1].equals("had")){
				if(ps.length==2){
					reTurn = "a:have:present perfect";
				}else if(ps.length==4 && ps[2].equals("to")){
					reTurn = "a:must "+infinitiv(ps[3])+":present perfect";
				}
			}else if(ps[1].equals("been")){
				if(ps.length==2){//has/have been
					reTurn="a:be:present perfect";
				}else if(ps[2].endsWith("ing")){
					reTurn = "a:"+infinitiv(ps[2])+":present perfect progressive";
				}else{
					reTurn = "p:"+infinitiv(ps[2])+":present perfect";
				}
			}else{
				reTurn = "a:"+infinitiv(ps[1])+":present perfect";
			}
			break;
		case "had":
			if(ps.length==1){
				reTurn = "a:have:past";
			}else if(ps[1].equals("had")){
				if(ps.length==2){
					reTurn = "a:have:past perfect";
				}else if(ps.length==4 && ps[2].equals("to")){
					reTurn = "a:must "+infinitiv(ps[3])+":past perfect";
				}
			}else if(ps[1].equals("been")){
				if(ps[2].endsWith("ing")){
					reTurn = "a:"+infinitiv(ps[2])+":past perfect progressive";
				}else{
					reTurn = "p:"+infinitiv(ps[2])+":past perfect";
				}
			}else{
				reTurn = "p:"+infinitiv(ps[1])+":past perfect";
			}
			break;
		case "will":
			if(ps.length==2){
				// I will ride my bike
				reTurn = "a:"+ps[1]+":future-I";
			}else if(ps.length==3){
				// I will be riding my bike
				if(ps[2].endsWith("ing")){
					reTurn = "a:"+removeIng(ps[2])+":future-I progressive";
				}else if(ps[1].equals("be")){// My bike will be rode by me
					reTurn = "p:"+infinitiv(ps[2])+":future-I";
				}else if(p.contains("have")){
					reTurn = "a:"+infinitiv(ps[2])+":future-II";
				}
			}else if(ps.length==4){
				if(ps[3].endsWith("ing")){
					reTurn = "a:"+removeIng(ps[3])+":future-II progressive";
				}else if(ps[2].equals("been")){
					reTurn = "p:"+infinitiv(ps[3])+":future-II";
				}
			}
			break;
		case "would":
			if(ps.length==2){
				// I would ride my bike
				reTurn = "a:"+ps[1]+":conditional-I";
			}else if(ps.length==3){
				// I would be riding my bike
				if(ps[2].endsWith("ing")){
					reTurn = "a:"+removeIng(ps[2])+":conditional-I progressive";
				}else if(ps[1].equals("be")){// My bike would be rode by me
					reTurn = "p:"+infinitiv(ps[2])+":conditional-I";
				}else if(p.contains("have")){
					reTurn = "a:"+infinitiv(ps[2])+":conditional-II";
				}
			}else if(ps.length==4){
				if(ps[3].endsWith("ing")){
					reTurn = "a:"+removeIng(ps[3])+":conditional-II progressive";
				}else if(ps[2].equals("been")){
					reTurn = "p:"+infinitiv(ps[3])+":conditional-II";
				}
			}
			break;
		default:
			if(ps.length==1 && p.endsWith("s")){
				if(p.endsWith("tches")){
					reTurn = "a:"+p.substring(0,p.length()-2)+":present";
				}else{
					reTurn = "a:"+p.substring(0,p.length()-1)+":present";
				}
			}else if(ps.length==1){
				if(p.endsWith("ed")){
					reTurn = "a:"+infinitiv(p)+":past";
				}else if(p.endsWith("ing")){
					reTurn = "a:"+infinitiv(p)+":present progressive";
				}else{
					reTurn = "a:"+((s = regularVerbform(p))==null?p:s)+":"+(s==null?"present":"past");
				}
			}else if(p.equals("going back")){// Spezialfall
				reTurn = "a:return:present progressive";
			}
		}
		
		s = reTurn.split("\\:")[1];
		if(negate){
			reTurn +=" not";
		}
		
		return reTurn.replace(s, s+end);
	}
	
	/**
	 * found	-> find
	 * founded	-> found
	 * */
	public static String infinitiv(String s){
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
		return (e=regularVerbform(s))==null?(s.endsWith("s")?s.substring(0, max(s.length()-1, 0)):s):e;
	}
	
	public static String removeIng(String s){
		s=s.substring(0, s.length()-3);
		if(s.equals("rid") || s.equals("lov"))return s+"e";// es gibt wohl noch wesentlich mehr, aber ich kenne leider keine Quelle für diese bzw. bin zu faul ;)
		return s;
	}
	
	public static String partizipII(String infinitiv){
		for(String line:AKISAData.irregularVerbs){
			if(line.startsWith(infinitiv+".")){
				return line.split("\\.")[2].split("/")[0];
			}
		}
		return infinitiv+"ed";
	}
	
	public static String regularVerbform(String s){
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
