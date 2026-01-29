package research;

import idea.Theory;

import java.util.ArrayList;

import math.MathHelper;

public class UEIIBase extends MathHelper {
	
	public static final String halfVerbs = "-am-is-are-has-have-has-was-were-been-being-having-to-will-would-may-should-could-",
			LINE = "-------------------------------------------------";
	
	public int length, klammerIndex;
	public boolean active = true, True = true, False = false;
	public String sentence, rawSentence, infinitive, multiplier;
	public String[] words;
	public ArrayList<String> klammern;
	public ArrayList<Theory> ret = new ArrayList<>();
	public Tense tense;
	public void findPredicate(){
		// finde das Prädikat
		
		/*boolean negate = false;
		
		while(p.contains(" not")){
			p = p.substring(0, p.indexOf(" not"))+p.substring(p.indexOf(" not")+4);
			ps = p.split(" ");
			negate = !negate;
		}
		while(p.contains("n`t ")){
			p = p.replace("n`t "," ");
			ps = p.split(" ");
			negate = !negate;
		}*/
		length = words[0].length()+1;
		switch(words[0]){
		case "am"  :
		case "are":
		case "is":
			if(words.length==1 || neverVerb(words[1])){// das nächste ist kein Verb...
				// is a horse
				infinitive = "be";
				tense = Tense.PRESENT;
			} else if(words[1].endsWith("ing")){
				length+=words[1].length()+1;
				if(words[1].equals("being")){
					// is being called
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PRESENT_PROGRESSIVE;
					active = false;
					length+=words[2].length()+1;
				} else {
					// is calling
					infinitive = UnderstandEnglish.infinitiv(words[1]);
					tense = Tense.PRESENT_PROGRESSIVE;
				}
			} else {
				// is used
				infinitive = UnderstandEnglish.infinitiv(words[1]);
				tense = Tense.PRESENT;
				active = false;
				length+=words[1].length()+1;
			}
			break;
		case "were":
		case "was":
			if(neverVerb(words[1])){
				// was a horse
				infinitive = "be";
				tense = Tense.PAST;
			} else if(words[1].endsWith("ing")){
				length+=words[1].length()+1;
				if(words[1].equals("being")){
					// was being called
					infinitive = UnderstandEnglish.infinitiv(words[3]);
					tense = Tense.PAST_PROGRESSIVE;
					active = false;
					length+=words[2].length()+1;
				} else {
					// was calling
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PAST_PROGRESSIVE;
				}
			} else {
				// was used
				infinitive = "be";
				tense = Tense.PAST;
				active = false;
				length+=words[1].length()+1;
			}
			break;
		case "have":
		case "has":
			if(words[1].equals("to")){
				// have to kiss her
				infinitive = "must "+words[2];
				tense = Tense.PRESENT;
				length+=words[2].length()+4;//1+'to '
			} else if(words[1].equals("had")){
				length+=4;
				if(words[2].equals("to")){
					// have to have a horse
					infinitive = "must "+words[3];
					tense = Tense.PRESENT_PERFECT;
					length+=words[3].length()+4;
				} else {
					// have had a horse
					infinitive = "have";
					tense = Tense.PRESENT_PERFECT;
				}
			} else if(words[1].equals("been")){
				length+=5;
				if(neverVerb(words[2])){
					// he has been sth
					infinitive = "be";
					tense = Tense.PRESENT_PERFECT;
				} else if(words[2].endsWith("ing")){
					// have been riding a horse
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PRESENT_PERFECT_PROGRESSIVE;
					length+=words[2].length()+1;
				} else {
					// the book has been read
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PRESENT_PERFECT;
					active = false;
					length+=words[2].length()+1;
				}
			} else if(neverVerb(words[1])){
				// has a horse
				infinitive = "have";
				tense = Tense.PRESENT;
			} else {
				// have stolen a horse
				infinitive = UnderstandEnglish.infinitiv(words[1]);
				tense = Tense.PRESENT_PERFECT;
				length+=words[1].length()+1;
			}
			break;
		case "had":
			if(words[1].equals("to")){
				// had to kiss her
				infinitive = "must "+words[2];
				tense = Tense.PAST;
				length+=words[2].length()+4;
			} else if(neverVerb(words[1])){
				// had a horse
				infinitive = "have";
				tense = Tense.PAST;
			} else if(words[1].equals("had")){
				length+=4;
				if(words[2].equals("to")){
					// had had to kiss her
					infinitive = "must "+words[3];
					tense = Tense.PAST_PERFECT;
					length+=words[3].length()+4;
				} else {
					// had had a horse
					infinitive = "have";
					tense = Tense.PAST_PERFECT;
				}
			} else if(words[1].equals("been")){
				length+=5;
				if(words[2].endsWith("ing")){
					// had been wondering about
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PAST_PERFECT_PROGRESSIVE;
					length+=words[2].length()+1;
				} else {
					// the book had been read
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.PAST_PERFECT;
					active = false;
					length+=words[2].length()+1;
				}
			} else {
				// had won
				infinitive = UnderstandEnglish.infinitiv(words[1]);
				tense = Tense.PAST_PERFECT;
				length+=words[1].length()+1;
			}
			break;
					
		// !!! conditional bezieht sich hier auf If-Typen, aber ich glaube das müsste anders sein...
		case "will":
			if(words[1].equals("be")){
				if(words[2].endsWith("ing")){
					// I will be riding my bike
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.FUTURE_I_PROGRESSIVE;
				} else {
					// my bike will be rode by me
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.FUTURE_I;
					active = false;
				}
			} else if(words[1].equals("have")){
				if(words[2].equals("been")){
					if(words[3].endsWith("ing")){
						// will have been reading the book
						infinitive = UnderstandEnglish.infinitiv(words[3]);
						tense = Tense.FUTURE_II_PROGRESSIVE;
					} else {
						// will have been kissed
						infinitive = UnderstandEnglish.infinitiv(words[3]);
						tense = Tense.FUTURE_II;
						active = false;
					}
				} else {
					// will have read the book
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.FUTURE_II;
				}
			} else {
				// I will ride my bike
				// !!! I will start to learn to control my bike :)... oje...
				// I forgot to ...
				infinitive = words[1];
				tense = Tense.FUTURE_I;
			}
			break;
		case "would":
			if(words[1].equals("be")){
				if(words[2].endsWith("ing")){
					// it would be working
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.CONDITIONAL_I_PROGRESSIVE;
				} else if(isPastTense(words[2])){
					// it would be rode by me
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.CONDITIONAL_I;
					active = false;
				} else {
					// it would be great
					infinitive = "be";
					tense = Tense.CONDITIONAL_I;
				}
			} else if(words[1].equals("have")){
				if(words[2].equals("been")){
					if(words[3].endsWith("ing")){
						// it would have been working
						infinitive = UnderstandEnglish.infinitiv(words[3]);
						tense = Tense.CONDITIONAL_II_PROGRESSIVE;
					} else {
						// it would have been killed
						infinitive = UnderstandEnglish.infinitiv(words[3]);
						tense = Tense.CONDITIONAL_II;
						active = false;
					}
				} else if(isPastTense(words[2])){
					// it would have worked
					infinitive = UnderstandEnglish.infinitiv(words[2]);
					tense = Tense.CONDITIONAL_II;
				} else {
					// I would have a horse
					infinitive = "have";
					tense = Tense.CONDITIONAL_I;
				}
			} else {
				// would ride my bike
				infinitive = words[1];
				tense = Tense.CONDITIONAL_I;
			}
			break;
		case "may":
		case "should":
		case "could":
			infinitive = words[0]+" "+words[1];
			length += words[1].length()+1;
			tense = Tense.PRESENT;
			break;
		default:
			if(words[0].endsWith("ing")){// Spezialfall: its history going back
				infinitive = UnderstandEnglish.infinitiv(words[0]);
				tense = Tense.PRESENT_PROGRESSIVE;
			} else if(words[0].endsWith("s")){
				if(words[0].endsWith("tches")){
					infinitive = words[0].substring(0, words[0].length()-2);
				} else {
					infinitive = words[0].substring(0, words[0].length()-1);
				}
				tense = Tense.PRESENT;
			} else if(words[0].equals("founded")){
				// oje... !!! Problem...
				infinitive = "found/founded";
				tense = Tense.PRESENT_OR_PAST;
			} else if(words[0].endsWith("ed")){
				infinitive = UnderstandEnglish.infinitiv(words[0]);
				tense = Tense.PAST;
			} else {
				
				System.out.println("?");
				
				String merke;// !!! auch nicht ganz richtig
				if((merke=UnderstandEnglish.regularVerbform(words[0]))==null){
					infinitive = UnderstandEnglish.infinitiv(words[0]);
					tense = Tense.PRESENT;
				} else {
					
					System.out.println("nicht null");
					
					infinitive = merke;
					tense = Tense.PAST;
				}
			}
		}
		if(!active){
			infinitive = "p."+infinitive;
		}
		
		if(infinitive.length()<2){
			System.out.println("t infinitive: "+words[0]+" / "+infinitive+" "+tense.name());
		}
		
	}
	
	public static boolean neverVerb(String s){// !!! oder ist unregelmäßige e-Form...
		return UnderstandEnglishII.neverInfrontOfVerb(s) || (!s.endsWith("ed") && !s.endsWith("ing") && !"-has-have-got-".contains("-"+s+"-") && UnderstandEnglish.regularVerbform(s)==null);
	}
	
	public static void line(){
		System.out.println("\n"+LINE+"\n");
	}
	
	// !!!
	public static boolean isPastTense(String s){
		return !neverVerb(s);
	}
	
	public String findeKlammern(String sentence, String[] words) {
		for(int i=0;i<words.length;i++){
			if(words[i].equals(")")){// Klammer gefunden :)
				int j=i;
				for(;i>-1;i--){
					if(words[i].equals("(")){// 2. Klammerende gefunden...
						// i bis j ist also die kleinste Klammer...
						String kette = "(";
						for(int k=i+1;k<j;k++){
							kette+=" "+words[k];
						}
						kette+=" )";
						sentence = sentence.replace(kette, "# "+klammerIndex++);
						words	 = sentence.split(" ");
						klammern.add(kette.substring(2, kette.length()-2));
						break;
					}
				}
			}
		}
		return sentence;
	}
	
	public static enum Tense {
		CONDITIONAL_I('a', 0.3f), CONDITIONAL_I_PROGRESSIVE('b', 0.3f),
		CONDITIONAL_II('c', -1.3f), CONDITIONAL_II_PROGRESSIVE('d', -1.3f),
		FUTURE_I('e', 1f), FUTURE_I_PROGRESSIVE('f', 1f),
		FUTURE_II('g', -2f), FUTURE_II_PROGRESSIVE('h', -2f),
		PRESENT('i', 0f), PRESENT_PERFECT('j', -.5f), PRESENT_PERFECT_PROGRESSIVE('k', -.5f), PRESENT_PROGRESSIVE('l', 0f),
		PAST('m', -1f), PAST_PERFECT('n', -1.5f), PAST_PERFECT_PROGRESSIVE('o', -1.5f), PAST_PROGRESSIVE('p', -1f),
		PRESENT_OR_PAST('q', -.5f);
		
		public char shortCut;
		public float timeLine;
		boolean isPerfect;
		private Tense(char shortCut, float time){
			this.shortCut = shortCut;
			this.timeLine = time;
			isPerfect = name().contains("PERFECT");
		}
		
		public static Tense byShort(char c){
			for(Tense t:Tense.values()){
				if(t.shortCut == c)return t;
			}
			return null;
		}
		
		public boolean equalsAbout(Tense t) {
			return Math.abs(timeLine-t.timeLine)<0.9;
		}
	}
	
	public static enum Gender {
		I,
		YOU,
		MALE,
		FEMALE,
		PERSON,
		GROUP,
		OBJECT;
	}
	
	public static final String[] examples4Testing = new String[]{
			"You are AKISA.",
			"Your owner is Antonio Noack.",
			"Your name is AKISA.",
			"You are called AKISA by me.",
			"Your developer is Antonio Noack.",
			"You are an artificial intelligence.",
			null,
			"London is the capital city of England and the United Kingdom.",
			"It is the most populous city in the United Kingdom, with a metropolitan area of over 13 million inhabitants.",
			"Standing on the River Thames, London has been a major settlement for two millennia, its history going back to its founding by the Romans, who named it Londinium.",
			"London's ancient core, the City of London, largely retains its 1.12-square-mile (2.9 km2) mediaeval boundaries and in 2011 had a resident population of 7,375, making it the smallest city in England.",
			"Since at least the 19th century, the term London has also referred to the metropolis developed around this core.",
			"The bulk of this conurbation forms the Greater London administrative area (coterminous with the London region), governed by the Mayor of London and the London Assembly.",
			null,
			"A monarch is the sovereign head of state, officially outranking all other individuals in the realm.",
			"A monarch may exercise the most and highest authority in the state or others may wield that power on behalf of the monarch.",
			"Typically a monarch either personally inherits the lawful right to exercise the state's sovereign rights (often referred to as the throne or the crown) or is selected by an established process from a family or cohort eligible to provide the nation's monarch.",
			"Alternatively, an individual may become monarch by conquest, acclamation or a combination of means.",
			"A monarch usually reigns for life or until abdication.",
			"Monarchs' actual powers vary from one monarchy to another and in different eras; on one extreme, they may be autocrats (absolute monarchy) wielding genuine sovereignty; on the other they may be ceremonial heads of state who exercise little or no power or only reserve powers, with actual authority vested in a parliament or other body (constitutional monarchy).",
			null,
			"I love the girl Ali.",
			"Ali has been a very nice friend.",// had been ;(
			"She is the most beautiful girl in the world.",// let's discuss that again
			"MyWorld is a project by me(Antonio Noack(=Miner952x(=Corperate Raider(dein Bruder?(auf jeden Fall Ali's Freund(:$))))))",
			"I read",
			"I am trying to learn to study to kiss my girl.",// I gave it up. it's useless
			"I killed 100 fish for meal",// I did ^^
			null,
			"In the context of human society, a family (from Latin: familia) is a group of people affiliated by consanguinity (by recognized birth), affinity (by marriage), or co-residence and/or shared consumption (see Nurture kinship).",
			"Members of the immediate family may include, singularly or plurally, a spouse, parent, brother, sister, son and/or daughter.",
			"Members of the extended family may include grandparents, aunts, uncles, cousins, nephews nieces and/or siblings-in-law.",
			"In most societies, the family is the principal institution for the socialization of children.",
			"As the basic unit for raising children, anthropologists generally classify most family organization as matrifocal (a mother and her children); conjugal (a husband, his wife, and children; also called the nuclear family); avuncular (for example, a grandparent, a brother, his sister, and her children); or extended (parents and children co-reside with other members of one parent's family).",
			"Sexual relations among the members are regulated by rules concerning incest such as the incest taboo.",
			"\"Family\" is used metaphorically to create more inclusive categories such as community, nationhood, global village and humanism.",
			"Genealogy is a field which aims to trace family lineages through history.",
			"Family is also an important economic unit studied in family economics.",
			null,
			"Dolphins are a widely distributed and diverse group of fully aquatic marine mammals.",
			"They are an informal grouping within the order Cetacea, excluding whales and porpoises, so to zoologists the grouping is paraphyletic.",
			"The dolphins comprise the extant families Delphinidae (the oceanic dolphins), Platanistidae (the Indian river dolphins), Iniidae (the new world river dolphins), and Pontoporiidae (the brackish dolphins).",
			"There are 40 extant species of dolphins.",
			"Dolphins, alongside other cetaceans, belong to the clade Cetartiodactyla with even-toed ungulates, and their closest living relatives are the hippopotamuses, having diverged about 40 million years ago.",
			"Dolphins range in size from the 1.7 metres (5.6 ft) long and 50 kilograms (110 lb) Maui's dolphin to the 9.5 metres (31 ft) and 10 metric tons (11 short tons) killer whale.",
			"Several species exhibit sexual dimorphism, in that the males are larger than females.",
			"They have streamlined bodies and two limbs that are modified into flippers.",
			"Though not quite as flexible as seals, some dolphins can travel at 55.5 kilometres per hour (34.5 mph).",
			"Dolphins use their conical shaped teeth to capture fast moving prey.",
			"They have well-developed hearing - their hearing, which is adapted for both air and water, is so well developed that some can survive even if they are blind.",
			"Some species are well adapted for diving to great depths.",
			"They have a layer of fat, or blubber, under the skin to keep warm in the cold water.",
			"Although dolphins are widespread, most species prefer the warmer waters of the tropic zones, but some, like the right whale dolphin, prefer colder climates.",
			"Dolphins feed largely on fish and squid, but a few, like the killer whale, feed on large mammals, like seals.",
			"Male dolphins typically mate with multiple females every year, but females only mate every two to three years.",
			"Calves are typically born in the spring and summer months and females bear all the responsibility for raising them.",
			"Mothers of some species fast and nurse their young for a relatively long period of time.",
			"Dolphins produce a variety of vocalizations, usually in the form of clicks and whistles.",
			"Dolphins are sometimes hunted in places like Japan, in an activity known as dolphin drive hunting.",
			"Besides drive hunting, they also face threats from bycatch, habitat loss, and marine pollution.",
			"Dolphins have been depicted in various cultures worldwide.",
			"Dolphins occasionally feature in literature and film, as in the Warner Bros film Free Willy.",
			"Dolphins are sometimes kept in captivity and trained to perform tricks, but breeding success has been poor and the animals often die within a few months of capture.",
			"The most common dolphins kept are the killer whales and bottlenose dolphins."
		};
	/*while(index<types.length){
			switch(types[index]){
			case ADJECTIVE_ADVERB:
				break;
			case BACKTRACK:
				break;
			case BY:
				// sammle bis du deinen by-Agent komplett hast...
				// tausche ihn mit dem Subjekt...
				// mach weiter und sammle die restlichen Objekte...
				break;
			case END:
				// du bist fertig...
				ret.add(new Theory(subject, infinitive, "", def));
				return ret;
			case ITERATOR:
				// I read, eat and drink
				break;
			case LIST:// I read book as Harry Potter, xD and LOL
				System.out.println("Nonsence #list");
				break;
			case LOGICAL_OPERATOR:
				// macht keinen Sinn...
				System.out.println("Nonsence #logical");
				break;
			case NUMBER:
				break;
			case PREPOSITION:// Ali is | the most beautiful girl in the world
				// sammle die Prepositionen...
				String prepo="";
				while(types[index]==WordType.PREPOSITION){
					prepo+=" "+words[index];
					index++;
				}
				// sammle das Objekt
				if(types[index]==WordType.UNKNOWN || types[index]==WordType.ADJECTIVE_ADVERB){
					boolean hashTag=false;
					String object = "";
					while(index<words.length && (types[index]==WordType.UNKNOWN || types[index]==WordType.ADJECTIVE_ADVERB || (hashTag=words[index].equals("#")))){
						if(hashTag){
							ret.addAll(klammern(object.substring(1), prepo, klammern.get(MathHelper.stringToInt(words[++index], 0))));
							hashTag=false;
						} else {
							object+=" "+words[index];
						}
						index++;
					}
					System.out.println(">"+prepo+object);
					if(types[index]==WordType.PARTIZIP){// Beschreibung des Objektes durch typische Tätigkeit..
						System.out.println("prepo.xyz.partizip");
					} else if(types[index]==WordType.BACKTRACK){// Beschreibung durch Teilsatz...
						// wie findet man heraus, was alles zum Teilsatz gehört??? bis ';' ?
						String teilsatz="";
						for(int i=index+1;i<words.length;i++){
							teilsatz+=" "+words[i];
						}
						ret.add(new Theory(subject, infinitive+prepo, object, def));
						ret.addAll(new UnderstandEnglishII(subject+teilsatz).toTheory());
						return ret;
					} else if(types[index]==WordType.PREPOSITION){// nähere Beschreibung
						//Family [a be|PRESENT]_an_important economic unit studied_in_family economics
						// is studied in family economics...
						// 
						
					} else if(types[index]==WordType.LOGICAL_OPERATOR){//!!!oje...
						// done sth and worked xyz...
						// erstetze also das erste und gucke dir den Satz nochmal an... :D
						// -> Informationsverlust, wenn der Satz länger ist :(
						// -> der Satz wird also ausgeschnitten und die Teile einzeln untersucht...
						// I had done to do and kiss sb. tonight.
						//		-> I had done to do sb. tonight
						//		-> I had done to kiss sb. tonight
						//ret.addAll(new UnderstandEnglishII(subject+infinitive).toTheory());
						//return ret;
						
						// subject.does.prepo.object.and
						// doesOthers.prepo.object
						// ODER prepo.object
						
						// finde also heraus, ob das nächste ein Verb ist...
						index++;
						if(halfVerbs.contains("-"+words[index]+"-")){// neue Verbform -> kompliziert
							
						} else if(UnderstandEnglish.isVerb(words[index])){
							
						} else {
							// nur ein anderes Objekt -> relativ einfach
							ret.add(new Theory(subject, infinitive+prepo, object, def));
							continue;
						}
					} else if(types[index]==WordType.END){
						ret.add(new Theory(subject, infinitive+prepo, object, def));
						return ret;
					}
				} else {
					System.out.println("prepo."+types[index].name());
				}
				// habe alle Propositionen gefunden... wie gehts weiter?
				// mit Abjektiven/Adverbien oder Substantiven
				break;
			case SPECIAL:
				break;
			case UNKNOWN:
				// das gleiche wie bei Prepositions bloß ohne prepo... vllt sollte ich es doch mit Funktionen machen...
				// kann aber dann auf Iterator enden...
				String object="";
				boolean hashTag=false;
				while(types[index]==WordType.UNKNOWN || (index<words.length && (hashTag=words[index].equals("#")))){
					if(hashTag){
						ret.addAll(klammern(object.substring(1), "", klammern.get(MathHelper.stringToInt(words[++index], 0))));
						hashTag = false;
						index++;
					} else {
						object+=" "+words[index++];
					}
				}
				if(types[index]==WordType.END){
					ret.add(new Theory(subject, infinitive, object.substring(1), def));
				} else if(types[index]==WordType.ITERATOR){
					ret.add(new Theory(subject, infinitive, object.substring(1), def));
					while(types[index]==WordType.ITERATOR){
						index++;
						if(words[index].equals("or")){
							index++;
						}
						if(types[index]==WordType.UNKNOWN || types[index]==WordType.PARTIZIP){
							object = "";
							if(types[index]==WordType.PARTIZIP){
								object = " "+words[index++];
							}
							while(types[index]==WordType.UNKNOWN || (index<words.length && (hashTag=words[index].equals("#")))){
								if(hashTag){
									ret.addAll(klammern(object.substring(1), "", klammern.get(MathHelper.stringToInt(words[++index], 0))));
									hashTag = false;
									index++;
								} else {
									object+=" "+words[index++];
								}
							}
							ret.add(new Theory(subject, infinitive, object.substring(1), def));
						} else {
							System.out.println("Exception\n\t"+rawSentence+"\n\t"+types[index].name());
						}
					}
				} else if(types[index]==WordType.LIST){
					// gehe bis zum Ende der Liste und untersuche, ob es sich um Erweiterunges des Subjektes oder um Objekt-Beispiele handelt...
					index++;
					boolean adjectives = false;
					int y;
					for(y=index;y<words.length;y++){
						if(types[y]==WordType.ADJECTIVE_ADVERB){
							adjectives = true;
							break;
						} else if(types[y]!=WordType.UNKNOWN && types[y]!=WordType.ITERATOR && !(types[y]==WordType.LOGICAL_OPERATOR && words[y].equals("or"))){
							if(types[y]==WordType.SPECIAL){
								y++;
							} else {
								break;
							}
						}
					}

					if(object.startsWith(" most ")){
						object = object.substring(5);
					}
					
					if(adjectives){
						// suche alle möglichen Erweiterungen des letzten Begriffes, wie z.B. anthropologists[a classify|PRESENT]most family organization as matrifocal # 0 ; conjugal # 1 ; avuncular # 2 ; or extended # 3
						do {
							while(types[index]==WordType.ITERATOR || types[index]==WordType.LOGICAL_OPERATOR){
								index++;
							}
							String example = "";
							
							while(types[index]==WordType.UNKNOWN || types[index]==WordType.ADJECTIVE_ADVERB || types[index]==WordType.PARTIZIP){
								example+=" "+words[index];
								if(types[index+1]==WordType.SPECIAL && words[index+1].equals("#")){
									index++;
									ret.addAll(klammern(example.substring(1)+object, "", klammern.get(MathHelper.stringToInt(words[++index], 0))));
								}
								index++;
							}
							ret.add(new Theory(example.substring(1)+object, "be a", object, def));
						} while(types[index]==WordType.ITERATOR || types[index]==WordType.LOGICAL_OPERATOR);
						continue;
					} else {
						
					}
				}
				break;
			default:
				break;
			}
			break;
		}
		
		
		
		*/
}
