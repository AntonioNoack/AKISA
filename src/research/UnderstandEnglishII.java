package research;

import idea.Theory;

import java.io.IOException;
import java.util.ArrayList;

import math.MathHelper;
import design.Console;

public class UnderstandEnglishII extends UEIIBase {
	
	public static final float defi = 0.9f, partOfObject = 0.5f;
	public static float def = defi;
	
	//https://www.youtube.com/watch?feature=player_embedded&v=maW0KmHs_sM Programm schreiben, dass sowas macht ^^ - stiehlt jedem die Hoffnung aber es wäre geil xD
	
	public static void main(String[] args) throws IOException, InterruptedException{
		for(String s:examples4Testing){
			if(s==null){
				UnderstandEnglishII.lastSubClear();
			} else {
				for(Theory t:new UnderstandEnglishII(s, 1).toTheory()){
					Console.println(t.toString());
				}
			}
		}
	}
	
	public static ArrayList<String> lastSub = new ArrayList<>();
	public static String lastSubject(int priority){
		if(lastSub.size()==0){
			return "Unknown";
		}
		return lastSub.get(min(lastSub.size()-1, priority));
	}
	public static void lastSubClear() {
		lastSub.clear();
	}
	public static void lastSubClear(String searchfor) {
		lastSub.clear();
		lastSub.add(searchfor);
	}
	
	public static String I="Antonio Noack", INSTANCE="AKISA";// muss noch ergänzt werden - durch Nachfragen!!!
	
	public UnderstandEnglishII(String sentence){
		this(sentence, -1);
	}
	public UnderstandEnglishII(String sentence, int priority){
		
		if(priority>=0){
			while(priority<lastSub.size()){
				lastSub.remove(priority++);
			}
		}
		
		while(sentence.endsWith(".") || sentence.endsWith("?") || sentence.endsWith("!")){
			sentence = sentence.substring(0, sentence.length()-1);
		}
		this.rawSentence = this.sentence = sentence;
		
		line();
		System.out.println(sentence);
	}
	
	/**
	 * mache aus einem Aussagesatz alle enthaltenen Zusammenhänge
	 * Chancen noch nicht enthalten, da die ja erst berechnet werden müssen und von anderen Zusammenhängen abhängig sind
	 * 
	 * der bearbeitete Satz bezieht sich immer auf die davor bearbeiteten, wie bei einem Homo Sapiens Sapiens, wenn Parallelität also in Frage kommt ist dies unbedingt zu beachten !!!
	 * Fragen brauchen ihre eigenen Funktionen!
	 * 
	 * alle Sätze müssten exakt richtig geschrieben sein!
	 * @throws IOException 
	 * */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<Theory> toTheory() throws IOException{
		
		def = defi;
		
		// Präpositionen entfernen -> it bzw. it_ muss noch durch den Vorgänger ersetzt werden
		sentence = antiPrepositions(sentence);
		words = sentence.split("[ _]");
		
		/* Wörter auf ing, die keine Substantivierung / Verlaufsform sind
		Browning, Channing, Ewing, morning
		bunting, shilling, farthing
		Having a specifed quality, characteristic, or nature; of the kind of
		sweeting, whiting, gelding*/
		
		String bridge=null, fact=null;
		int cursor = 0;
		
		if((isAdverb(words[0]) || isAdjektiv(words[0]) && words[1].equals(","))){// !!! Adverben vor einem Satz werden erstmal weggelassen...
			sentence = sentence.substring(words[0].length()+3);
			words	 = sentence.split("[ _]");
		}
		
		if(words[0].endsWith("ing")){
			int komma;
			// Standing_on_the River Thames , London has been_a major settlement_for two millennia , its history going back_to its founding_by_the Romans , who named it Londinium.
			// London-stand-on_the River Thames
			
			bridge = sentence.substring(0, komma=proPrepositionen(rawSentence).indexOf(' ')).replace(words[0], UnderstandEnglish.infinitiv(words[0].toLowerCase()));
			fact = sentence.substring(komma+1, komma=sentence.indexOf(',')-1);
			// object ist alles nach dem Komma UND vor dem Prädikat...
			
			sentence = sentence.substring(komma+3);
			words	 = sentence.split("[ _]");
		}
		
		if(words[0].equalsIgnoreCase("with")){
			length = 0;
			while(!words[cursor].equals(",")){
				length+=words[cursor++].length()+1;
			}
			
			sentence = sentence.substring(length+2)+" with "+sentence.substring(5, length-1);
			words	 = sentence.split("[ _]");
			
			System.out.println("\\ "+sentence);
		}
		
		if(words[0].equals("However") && words[1].equals(",")){
			sentence = sentence.substring(9);
			words	 = sentence.split("[ _]");
		}
		
		while(timeOrCondition(words[0])){// statt einem Subjekt kommt ein Ort oder eine Zeit... wie ist das bloß bei beidem?
			//In_the_context_of_human society , _a family (from Latin: familia)
			//As_the_basic unit_for_raising children , anthropologists generally
			length = 0;
			while(!words[cursor].equals(",")){
				length+=words[cursor++].length()+1;
			}
			System.out.println("h ["+sentence.substring(0, length-1)+"]");
			sentence = sentence.substring(length+2);
			words	 = sentence.split("[ _]");
			
			cursor = 0;
		}
		
		// finde das Subjekt...
		length = 0;
		while(
				!UnderstandEnglish.isVerb(length==0?words[cursor]:UnderstandEnglish.infinitiv(words[cursor]))
			||	(!"-am-are-is-was-were-".contains("-"+words[cursor]+"-") && cursor+1<words.length && words[cursor+1].endsWith("ing"))
			||	(cursor>0 && (words[cursor-1].endsWith("'s") || words[cursor-1].endsWith("'")))// naja die Notlösung für drive hunting...
			||	(cursor>0 && firstLetters(words[cursor-1])!=null) && !directFirstLetters(words[cursor-1])){
			
			length+=words[cursor++].length()+1;
			if(cursor < words.length && words[cursor].equals(",")){// wenn das nächste ein Komma ist...
				//cursor++;// muss irgendwann noch ein Komma kommen (wenn es keine Zeitform ist...)
				// -> neue Theorie
				
				cursor++;
				length+=2;
				
				// London's ancient core, the City of London,
				int l1=length;
				String subject = sentence.substring(0, l1-3);
				
				while(cursor<words.length && !words[cursor].equals(",")){
					length+=words[cursor++].length()+1;
				}
				
				retadd(subject, "be", sentence.substring(l1, min(sentence.length(), length)).replace("_", " "), def, Tense.PRESENT, interest());
				
				sentence = subject+sentence.substring(min(sentence.length(), length+1));
				length=subject.length()-1;
				
			}
			
			while(neverInfrontOfVerb(words[cursor-1])){
				length+=words[cursor++].length()+1;
			}
			
			if(cursor==words.length){
				break;
			}
		}
		Gender g;String letts;
		if((g=firstLetters(letts=sentence.split("[ _]")[0]))!=null){
			if(g==Gender.I){
				if(letts.equalsIgnoreCase("I")){// direkt
					sentence = I+sentence.substring(letts.length());
				} else {// hänge ein 's an
					sentence = I+"'s"+sentence.substring(letts.length());
					length+=2;
				}
				length+=I.length()-letts.length();
			} else if(g==Gender.YOU){// das Programm selbst ist gemeint...
				if(letts.equalsIgnoreCase("you")){// direkt
					sentence = INSTANCE+sentence.substring(letts.length());
				} else {// hänge ein 's an
					sentence = INSTANCE+"'s"+sentence.substring(letts.length());
					length+=2;
				}
				length+=INSTANCE.length()-letts.length();
			} else {
				for(String lastsub:lastSub){
					if(directFirstLetters(letts)){
						sentence = lastsub+sentence.substring(letts.length());
					} else {// hänge ein 's an
						sentence = lastsub+"'s"+sentence.substring(letts.length());
						length+=2;
					}
					
					length+=lastsub.length()-letts.length();
					
					ret.add(new Theory(lastsub, "be", g.name(), def*partOfObject, Tense.PRESENT, interest()));//!!!
				}
			}
		}
		
		if(length<1){
			return ret;
		}
		
		String subject = sentence.substring(0, length-1);
		
		lastSub.add(subject);
		
		// finde heraus ob das Subjekt noch weiter beschrieben ist...
		// London's ancient core ,_the_City_of_London , largely retains its 1.12-square-mile 
		
		// -> London-have|Present>ancient core
		// -> London's ancient core-be|Present>the City of London
		// -> the City of London EQUALS London's ancient core -> verlinkung direkt darauf
		// also ist be|Present super bedeutsam :)
		// genauso have|Present
		
		String adverb=null;
		String[] subarr = subject.split("[ _]");
		if(isAdverb(adverb=subarr[subarr.length-1])){
			subject = subject.substring(0, subject.length()-adverb.length()-1);
		} else if(isAdverb(adverb=subarr[0])){
			subject = subject.substring(adverb.length()+1);
		} else {
			adverb 	= null;
		}
		
		String second = null;
		if(subject.contains(",")){
			second = subject.substring(subject.indexOf(',')+1, subject.length());
			if(second.contains(",")){
				second = second.substring(1, second.indexOf(',')-1);
			} else {
				System.out.println("3 [ERROR no second ','] "+second+" | "+rawSentence);
				second = null;
			}
		}
		
		sentence = sentence.substring(min(sentence.length(), length));
		words	 = sentence.split("[ _]");
		
		if(second!=null){
			subject = subject.substring(0, subject.indexOf(',')-1)+subject.substring(subject.indexOf(',')+second.length()+3, subject.length()-1);
			retadd(subject, "be", second, def, Tense.PRESENT, interest());//!!!
		}
		
		// die length wird aufs neue verwendet! sentence.substring(length darf nicht mehr drunter !!!)
		
		boolean slashS=false;
		if(subject.contains("' ") || (slashS=subject.contains("'s"))){// Besitzeszugehörigkeit :) - nur einfach!!
			String first;
			first = subject.substring(0, length=subject.indexOf('\''));
			second = subject.substring(length+(slashS?3:2), subject.length());
			retadd(first, "have", second, def, Tense.PRESENT, interest());
		}
		
		if(bridge!=null){
			retadd(subject, bridge, fact, def, Tense.PRESENT, interest());
		}
		
		// der Satz enthält möglicherweise sometimes, always, usually, often, never
		
		for(int index=0;index<words.length;index++){
			if("-rarely-sometimes-typically-always-usually-often-never-largely-".contains("-"+words[index]+"-")){
				multiplier = words[index];
				break;
			} else if(!halfVerbs.contains("-"+words[index]+"-")){
				break;
			}
		}
		
		if(multiplier!=null){
			words = sentence.replace('_', ' ').replace(multiplier, "").replace("  ", " ").split(" ");
		}
		
		findPredicate();
		
		if(multiplier!=null){
			length+=multiplier.length()+1;
			def=multiplier(multiplier);
		}
		
		if(sentence.length()+1==length){
			System.out.println(subject+" / "+infinitive);
			retadd(subject, infinitive, null, def, tense, interest());
			return ret;
		} else {
			sentence = sentence.substring(MathHelper.min(sentence.length()-1, length)).replace("_", " ");
			words	 = sentence.split("[ ]");
		}
		
		// es gibt Klammern...
		
		klammerIndex=0;
		klammern = new ArrayList<>();
		
		// finde die Objekte und weitere Zusammenhänge...
		// also... wie sind Folgeobjekte aufgebaut?
		
		sentence = antiPrepositions(findeKlammern(sentence, words));
		words	 = sentence.replace('_', ' ').trim().split(" ");
		
		int index=0;
		while(index<words.length && words[index].equals("to") && !isPreposition(words[index+1])){
			infinitive+=" "+words[++index];
			sentence=sentence.substring(words[index].length()+4);
			index++;
		}
		
		if(isPartizip(infinitive.substring(max(0, infinitive.lastIndexOf(' '))))){
			
			System.out.println("Das echte Prädikat wurde angeblich übersehen...");
			
			// das echte Prädikat wurde übersehen... The most dolphins kept are ...
			// genauso kann das aber auch noch später austreten dank Adj. und Adv. The most dolphins kept for tweo weeks shily are ...
			
			words = sentence.split("[ _]");
			
			subject += " "+UnderstandEnglish.partizipII(infinitive);
			
			findPredicate();
			
			// !!! predicate

			for(int i=0;i<words.length;i++){
				if("-sometimes-always-usually-often-never-largely-".contains("-"+words[i]+"-")){
					multiplier = words[i];
					break;
				} else if(!halfVerbs.contains("-"+words[i]+"-")){
					break;
				}
			}
			
			
			if(multiplier!=null){
				words = sentence.replace('_', ' ').replace(multiplier, "").replace("  ", " ").split(" ");
			}
			
			findPredicate();
			
			// predicate
			
			if(multiplier!=null){
				length+=multiplier.length()+1;
			}
			
			sentence = sentence.substring(MathHelper.min(sentence.length()-1, length)).replace("_", " ");
			words	 = sentence.split("[ ]");
		}
		
		/*
		0 = Preposition
		1 = Adjektiv
		2 = Adverb
		3 = Substantiv
		4 = logische Verknüpfung
		5 = Steigerung
		6 = Zahl
		*/
		
		// 0	 3	   3	0	 3	   4   0	3		3
		//the capital city of England and the United Kingdom.
		
		// 0	5	  1		  3  0   0	  3		  3	   4  0	  0		1		  3	  0   0   6     6        3
		//the most populous city in the United Kingdom , with a metropolitan area of over 13 million inhabitants.
		
		// 0	0	1		   0   0	1		2  1	1		0	0	 0   1      1    0   0    1       1    0   1    1
		// a major settlement for two millennia , its history going back to its founding by the Romans , who named it Londinium.
		
		// Jeder Satz besteht aus SPOOOOOO,OOOO...
		// also theoretisch...
		
		// finde alle Os und kombiniere sie :)
		// <object>
		
		WordType[] types = WordType.wordTypes(words);
		
		ArrayList<ArrayList<String>> objects = new ArrayList<>();
		objects.add(new ArrayList<String>());
		while(in(types[index], WordType.PREPOSITION, WordType.UNKNOWN, WordType.BY, WordType.ITS, WordType.IT)){// es folgen Objekte...
			
			String pre="", obj;
			if(in(types[index], WordType.PREPOSITION, WordType.BY)){// eine Reihe von Pradikaten und so...
				pre = words[index++];
				while(in(types[index], WordType.PREPOSITION)){
					pre+=" "+words[index++];
				}

				System.out.println("4 pre: "+pre);
			}
			
			// <pre|wonderful object>
			if(in(types[index], WordType.UNKNOWN, WordType.PARTIZIP, WordType.ITS, WordType.IT)){
				if(types[index]==WordType.ITS){
					obj = lastSubject(10)+"'s";
					index++;
				} else if(types[index]==WordType.IT){
					obj = lastSubject(0);
					index++;
				} else {
					obj = words[index++];
				}
				
				while(in(types[index], WordType.UNKNOWN)){
					obj+=" "+words[index++];
				}
				if(types[index]==WordType.HASHTAG){
					ret.addAll(klammern(obj, pre, klammern.get(MathHelper.stringToInt(words[index+1], 0))));
					index+=2;
				}
				
				System.out.println("5 obj: "+obj);
				
				if(types[index]!=WordType.END && words[index].equalsIgnoreCase("of")){// der nächste Teil gehört unbedingt dazu...
					do {
						ArrayList<String> ofs = new ArrayList<>();
						String obs = "of";
						index++;
						while(in(types[index], WordType.PREPOSITION)){// Tuluhulus are the most interesting landmarks of the UK and Nothern Ireland.
							obs+=" "+words[index++];
						}
						if(in(types[index], WordType.UNKNOWN, WordType.PARTIZIP, WordType.IT, WordType.ITS)){
							
							if(types[index]==WordType.IT){
								obs+="|"+lastSubject(0);
							} else if(types[index]==WordType.ITS){
								obs+="|"+lastSubject(10)+"'s";
							} else {
								obs+="|"+words[index];
							}
							
							index++;
							
							while(in(types[index], WordType.UNKNOWN)){
								obs+=" "+words[index++];
							}
							if(types[index]==WordType.HASHTAG){
								ret.addAll(klammern(obs, pre, klammern.get(MathHelper.stringToInt(words[index+1], 0))));
								index+=2;
							}
							
							ofs.add("<"+obs+">");
							
							if(in(types[index], WordType.ITERATOR, WordType.LOGICAL_OPERATOR)){
								// eine Liste von Of's...
								
								do {
									while(in(types[index], WordType.ITERATOR, WordType.LOGICAL_OPERATOR)){index++;}
									
									obs = "of ";
									while(types[index]==WordType.PREPOSITION){
										obs += words[index++]+" ";
									}
									obs = obs.substring(0, obs.length()-1)+"|";
									while(in(types[index], WordType.UNKNOWN, WordType.PARTIZIP)){
										obs += words[index++]+" ";
									}
									obs = obs.substring(0, obs.length()-1);
									if(types[index]==WordType.HASHTAG){
										ret.addAll(klammern(obs, pre+"|"+obj, klammern.get(MathHelper.stringToInt(words[index+1], 0))));
										index+=2;
									}
									
									ofs.add("<"+obs+">");
									
								} while (in(types[index], WordType.ITERATOR, WordType.LOGICAL_OPERATOR));
								
								
								// kopiere alle ofs ins Objects...
								// das ist aber nicht wirklich richtig, wenn noch Of's folgen...
								
								//if(index<words.length && words[index].equals("to")){
								//	System.out.println("Error: can't create OF after a list of Of's");
								//	System.exit(0);
								//}
								
								System.out.println(subject+" "+lastSub.size());
								for(String s:lastSub){
									System.out.println(s);
								}
								
								for(String of:ofs){
									retadd(subject, infinitive, "<"+obj+of+">", def, tense, interest());
								}
								
							} else {
								for(ArrayList<String> a:objects){
									a.add("<"+pre+"|"+obj+"<"+obs+">>");
								}
							}
						} else {
							//System.exit(-1);// !!! selbstverständlich darf das finale Programm hier nicht enden
							
							throw new RuntimeException("6 Error at index "+index+": no object after prepositions and of and preposition...");
						}
					} while(types[index]!=WordType.END && words[index].equalsIgnoreCase("of"));
					
					// gewissermaßen gehört hier das folgende noch rein... auch wenn vllt nicht so einfach, da Listen ja auftreten können
					// -> wir hoffen einfach mal, dass ein Satz nicht so kompliziert wird...
					
					// zuende schreiben!!!
					
					/*for(ArrayList<String> a:objects){
						a.add("<"+pre+"|"+obj+">");
					}
					if(types[index]==WordType.LIST){
						//Dolphins occasionally in_ literature? and& film? , as||| in_ the_ Warner? Bros? film? Free? Willy? 
					}*/
					
				} else if(in(types[index], WordType.LOGICAL_OPERATOR, WordType.ITERATOR)){
					while(in(types[++index], WordType.LOGICAL_OPERATOR, WordType.ITERATOR));
					// finde die passende letzte Preposition
					/*
					 * Möglichkeiten:
					 *  in Germany and the US
					 * 	by me and you
					 *  from Jena and from Germany
					 *  from Jena in the see and from Berlin
					 *  I kissed my girl and crushed her upto the sky
					 *  ... other versions are a bit more difficult ...
					 * */
					
					if(isTruePreposition(words[index])){
						// by me and you
						// from Jena and from Germany
						// from Jena in the see and from Berlin
						
						// -> finde die letzte gleiche Preposition... Orts und Zeitangaben kann man eventuell noch verfeinern...
						// gibt es keine, wird einfach weiter gemacht... oder? erstmal nen Error, damit uns gezeigt wird, welche Stellen es betrifft
						
						System.out.println("7 *by me and you*");
						
					} else if((types[index]==WordType.PARTIZIP && tense.isPerfect) || (false && UnderstandEnglish.isVerb(words[index]) && types[index+2]!=WordType.LIST)){
						
						// I learn and kiss
						
						String satz = "";
						while(index<words.length){
							satz += " "+words[index++];
						}
						
						line();
						System.out.println("y "+subject+satz);
						
						ret.addAll(new UnderstandEnglishII(subject+satz).toTheory());
						
					} else if(false && (types[index]==WordType.PARTIZIP || (UnderstandEnglish.isVerb(words[index]) && types[index+2]!=WordType.LIST))){//, as...
					
						// I learn and you kiss
						
						// alt
						
						// I kiss my girl and crush her upto the sky
						// -> break to start anyhow...

						for(ArrayList<String> a:objects){
							a.add("<"+pre+"|"+obj+">");
						}
						
						for(ArrayList<String> a:objects){
							if(a.size()>1){
								String objj = "";
								for(String objs:a){
									objj+=objs;
									retadd(subject, infinitive, objs, partOfObject, tense, interest());
								}
								retadd(subject, infinitive, objj, def, tense, interest());
							} else {
								retadd(subject, infinitive, a.get(0), def, tense, interest());
							}
							
							if(index<words.length){
								System.out.println("8 \t\ttja|"+words[index]);
							}
						}
						
						infinitive = UnderstandEnglish.infinitiv(words[index]);
						index++;
						
						while(index<words.length && words[index].equals("to") && !isPreposition(words[index+1])){
							infinitive+=" "+words[++index];
							index++;
						}
						
						System.out.println("9 \ti "+infinitive+" -> "+types[index]);
						
						objects.clear();
						objects.add(new ArrayList<String>());
						
						continue;
					} else if(types[index]==WordType.BACKTRACK){

						String satz = obj;
						index++;
						while(types[index]!=WordType.END){
							satz+=" "+words[index++];
						}
						
						line();
						
						System.out.println("a "+satz);
						
						lastSub.add(subject);
						
						retadd(subject, infinitive, obj, def, tense, interest());
						ret.addAll(new UnderstandEnglishII(satz, 10).toTheory());
						
					} else if(False){
						// in Germany, Sweden and the US
						// I bear you and my mother bears all the reponsibilities for you
						
						// -> letzte Prepo
						// allerdings nur, wenn von der gleichen Art... naja... vllt gibts ja nen Hinweis, der uns sagt, mit wems aufgezählt wurde,
						// auch wenn selbst das nicht wirklich hilfreich ist
						
						// -> vervielfältige die ArrayList ganz nach Bedarf...
						
						// finde also heraus, ob das folgende ein beginnender neuer Satz ist...
						// wenn ja untersuche ihn auch so und beende den aktuellen einfach mit den bisherigen Daten
						// sonst finde das Objekt und füge es zur Liste hinzu
						
						System.out.println("b *neuer Satz* "+words[index]);
						
						if(/*neuerSatz*/True){
							
							String satz = "";
							while(types[index]!=WordType.END){
								satz+=" "+words[index++];
							}
							
							line();
							
							System.out.println("c "+satz);
							
							lastSub.add(subject);
							ret.addAll(new UnderstandEnglishII(satz.substring(1), 10).toTheory());
							
						} else {
							
							// alle Objekte der Aufzählung...
							ArrayList<String> news = new ArrayList<>();
							news.add(obj);
							
							index--;
							do {
								while(in(types[++index], WordType.ITERATOR, WordType.LOGICAL_OPERATOR));
								// finde die Objekte...
								// zuerst alle Prepositionen... die es zwar nicht geben dürfte, aber naja...
								if(types[index]==WordType.PREPOSITION){
									throw new RuntimeException("d Error: Preposition in Liste von Nicht-an-Prepositionen gebundenen Objekten");
								}
								obj = words[index++];
								while(in(types[index], WordType.UNKNOWN)){
									obj+=" "+words[index++];
								}
								if(types[index]==WordType.HASHTAG){
									ret.addAll(klammern(obj, pre, klammern.get(MathHelper.stringToInt(words[index+1], 0))));
									index+=2;
								}
								news.add(obj);
							} while (in(types[index], WordType.ITERATOR, WordType.LOGICAL_OPERATOR));
							
							ArrayList<ArrayList<ArrayList<String>>> superlist = new ArrayList<>();
							
							for(String add:news){
								ArrayList<ArrayList<String>> that = (ArrayList<ArrayList<String>>) objects.clone();
								for(ArrayList<String> thet:that){
									thet.add("<"+pre+"|"+add+">");
								}
								superlist.add(that);
							}
							
							objects = new ArrayList<>();
							for(ArrayList<ArrayList<String>> listOlist:superlist){
								objects.addAll(listOlist);
							}
						}
					}
				} else {
					for(ArrayList<String> a:objects){
						a.add("<"+pre+"|"+obj+">");
					}
					if(types[index]==WordType.LIST){
						//Dolphins occasionally in_ literature? and& film? , as||| in_ the_ Warner? Bros? film? Free? Willy?
						System.out.println("e *seltsame Liste*");
					}
				}
			} else {
				throw new RuntimeException("f Error at index "+index+": no object after prepositions...");
				//System.exit(-1);// !!! selbstverständlich darf das finale Programm hier nicht enden
			}
		}
		
		/* -ed kann auch an das angehangen werden :)
		   bezieht sich auf das direkt letzte Objekt
		[In_the_context_of_human society]
		a_family ( from Latin: familia )
		a_ group? of_ people? affiliated(o) by_ consanguinity? # 0? , affinity? # 1? , or& co-residence? and/or shared(o) consumption? # 2? 
				|affiliated
		a_family ( from Latin: familia )-be><a|group<of|people>> 90%
		*/
		
		if(objects.get(0).size()>0){
			for(ArrayList<String> a:objects){
				if(a.size()>1){
					String obj = "";
					for(String objs:a){
						obj+=objs;
						retadd(subject, infinitive, objs, partOfObject, tense, interest());
					}
					retadd(subject, infinitive, obj, def, tense, interest());
				} else {
					retadd(subject, infinitive, a.get(0), def, tense, interest());
				}
			}
		}
		
		if(index<words.length && (types[index]==WordType.PARTIZIP && tense.isPerfect || false && UnderstandEnglish.isVerb(words[index]))){
			String satz = "";
			while(types[index]!=WordType.END){
				satz+=" "+words[index++];
			}
			
			ret.addAll(new UnderstandEnglishII(subject+satz, 10).toTheory());
		}
		
		if(True)return ret;
		
		System.out.println(subject+"\n\t"+"["+(active?"a ":"p ")+infinitive+"|"+tense.name()+"]"+sentence.replace(",", ",\n\t").replace(" and ", " and\n\t").replace(" or ", " or\n\t").replace(";", ";\n\t")+"\n");
		
		return ret;
	}
	
	@SuppressWarnings("deprecation")
	void retadd(String subject, String infinitive, String obj, float def, Tense tense, long interest) throws IOException {

		if(infinitive.length()==0){
			throw new RuntimeException("Infinitive.lenght = 0. /"+subject+"/"+obj+"/"+def+"/"+tense.name());
		}
		
		if(obj!=null){
			int i = obj.indexOf('|'), j = obj.indexOf(' ');
			if(i>0 && j>0 && j<i){
				i=j;
			}
			String s;
			if(i!=-1 && isTruePreposition(s=obj.substring(1, i))){
				
				if(s.equalsIgnoreCase("by")){
					ret.add(new Theory(obj.substring(i+1, obj.length()-1).trim(), infinitive.startsWith("p.")?infinitive.substring(2):infinitive, subject, def, tense, interest));
				}
				
				infinitive += " "+s;
				obj = "<"+obj.substring(i, obj.length()).trim();
			}
		}
		
		ret.add(new Theory(subject, infinitive, obj, def, tense, interest));
	}
	
	float multiplier(String multiplier) {
		if(multiplier==null)return def;
		switch(multiplier.toLowerCase()){
		case "never":return -1;
		case "always":return +1;
		case "sometimes":return 0.3f;
		case "usually":return 0.8f;
		case "rarely":return 0.2f;
		case "typically":return 0.93f;// nur leichte Abschwächung
		case "often":return 0.67f;
		case "largely":return 0.7f;
		default:return def;
		}
	}

	public static ArrayList<Theory> klammern(String subject, String pre, String content){
		ArrayList<Theory> ret = new ArrayList<>();
		System.out.println("*Klammer die Klapp*\t"+subject+"\n\t"+content);
		//String[] words	 = content.replace('_', ' ').split(" ");
		//WordType[] types = wordTypes(words);
		//System.out.println(content);
		
		return ret;
	}
	
	// a mother and her children
	// a husband , his wife , and children ; called the nuclear family
	// for example , a grandparent , a brother , his sister , and her children

	public static boolean isAdjektiv(String s) {
		return s.endsWith("able") || s.endsWith("ible") || s.endsWith("ous") || s.endsWith("al") || s.endsWith("ful") || s.endsWith("ic") || s.endsWith("ive") || s.endsWith("less")
				|| "-bad-bright-clever-cold-common-complete-dark-deep-difficult-distant-elementary-good-great-honest-hot-main-morose-old-quiet-real-red-silent-simple-strange-wicked-wide-young".contains("-"+s+"-");
	}
	
	// !!! but wird ja nicht immer so verwendet sondern auch als außer...
	public static boolean isCut(String s){
		return "-but-".contains("-"+s+"-");
	}
	
	public static boolean isList(String s){
		return "-as-like-".contains("-"+s+"-");
	}
	
	/**
	 * used, practised, ...
	 * */
	public static boolean isPartizip(String s){
		return s.length()>0 && notUpperCase(s.charAt(0)) && (UnderstandEnglish.regularVerbform(s)!=null || s.endsWith("ed"));
	}
	
	public static boolean notUpperCase(char c){
		return c<'A' || c>'Z';
	}
	
	public static boolean isNumber(String s){return MathHelper.isNumber(s);}
	public static boolean isBacktracking(String s){return "-who-which-".contains("-"+s+"-");}
	public static boolean isIts(String s){return "-their-it-its-".contains("-"+s+"-");}
	public static boolean isPreposition(String s){return "-a-about-an-in-at-by-the-on-up-til-until-from-for-with-within-without-to-of-muchof-manyof-someof-fewof-".contains("-"+s+"-");}
	public static boolean isTruePreposition(String s){return "-about-in-at-by-on-up-til-until-for-from-with-within-without-to-of-".contains("-"+s+"-");}
	public static boolean isIterator(String s){return "-;-,-.-or-and/or-".contains("-"+s+"-");}
	public static boolean isLogicalOperator(String s){return "-and-or-".contains("-"+s+"-");}
	
	public static boolean neverInfrontOfVerb(String word){// +alle Adjektive... aber nicht Adverbien
		return "-actual-a-an-in-at-by-the-on-up-til-until-less-more-for-with-within-without-this-to-of-and-or-manyof-muchof-someof-fewof-".contains("-"+word.toLowerCase()+"-");}
	
	public static boolean timeOrCondition(String word){
		return "-by-over-since-for-besides-as-in-on-up-til-until-".contains("-"+word.toLowerCase()+"-");
	}
	
	/**
	 * gilt nur für das letzte Element!
	 * und testet nicht die unregelmäßigen Verben!
	 * */
	public static boolean isAdverb(String s){
		return s.endsWith("ly") && !("-ally-anomaly-assembly-belly-billy-bully-dolly-doily-family-fly-gully-hillbilly-holly-homily-jelly-lily-monopoly-panoply-potbelly-rally-reply-supply-tally-underbelly"
				+ "-italy-melancholy-alley-sally-elly-jilly-kelly-molly-milly-nelly-polly-sally-tilly-willy-").contains("-"+s.toLowerCase()+"-") && !s.endsWith("fly");
	}
	
	/**
	 * entfernt Präpositionen und berichtigt die englische Zahlenschreibweise
	 * Punkte werden nicht entfernt, da sie IM Satz nichts zu suchen haben.
	 * */
	public static String antiPrepositions(String s){
		return (" "+s)
				.replace("There are ", "It is ")
				.replace("There is ", "It is 1 ")
				.replace("There has been ", "It has been ")
				.replace("There were ", "It were ")
				.replace("There was ", "It was 1 ")
				.replace(" much of ", " muchof ").replace(" many of ", " manyof ").replace(" some of ", " someof ").replace(" few of ", " fewof ")
				.replace(" also ", " ")// also braucht keiner, aber sometimes, oder always schon... -> in der Prädikatrausfindefunktion muss das raus gefiltert werden, aber später wieder hinzugefügt
				.replace(" a ", " _a_ ").replace(" in ", " _in_ ").replace(" at ", " _at_ ").replace(" an ", " _an_ ").replace(" by ", " _by_ ").replace(" the ", " _the_ ").replace(" on ", " _on_ ")
				.replace(" up ", " _up_ ").replace(" til ", " _til_ ").replace(" until ", " _until_ ").replace(" less ", " _less_ ").replace(" more ", " _more_ ").replace(" for ", " _for_ ")
				.replace(" with ", " _with_ ").replace(" without ", " _without_ ").replace(" this ", " _this_ ").replace(" to ", " _to_ ").replace(" of ", " _of_ ")
				//.replace(" and ", " _and_ ").replace(" or ", " _or_ ") -> keine Präposition sondern ehr eine logische Verknüpfung
				.replace(",0", "0").replace(",1", "1").replace(",2", "2").replace(",3", "3").replace(",4", "4").replace(",5", "5").replace(",6", "6").replace(",7", "7").replace(",8", "8").replace(",9", "9")
				.replace("(", " ( ").replace(")", " ) ").replace(";", " ; ").replace(" for example ", " as ")// ja das ist identisch und deshalb ok
				.replace("  ", " ").replace(" _", "_").replace("_ ", "_").replace("__", "_").replace(", ", ",").replace(",", " , ").replace(" _", "_").replace("_ ", "_").replace("  ", " ").trim();
	}
	
	public static String proPrepositionen(String s){
		return s.replace("There are ", "It exist ").replace("There is ", "It exists 1 ")
				.replace(" a ", " _a ").replace(" in ", " _in ").replace(" at ", " _at ").replace(" an ", " _an ").replace(" by ", " _by ").replace(" the ", " _the ").replace(" on ", " _on ")
				.replace(" up ", " _up_ ").replace(" til ", " _til_ ").replace(" until ", " _until_ ").replace(" less ", " _less_ ").replace(" more ", " _more_ ").replace(" for ", " _for ")
				.replace(" with ", " _with ").replace(" without ", " _without ").replace(" this ", " _this ").replace(" to ", " _to ").replace(" of ", " _of ")
				//.replace(" and ", " _and_ ").replace(" or ", " _or_ ") -> keine Präposition sondern ehr eine logische Verknüpfung
				.replace(",0", "0").replace(",1", "1").replace(",2", "2").replace(",3", "3").replace(",4", "4").replace(",5", "5").replace(",6", "6").replace(",7", "7").replace(",8", "8").replace(",9", "9")
				.replace("(", " ( ").replace(")", " ) ").replace(";", " ; ").replace(" for example ", " as ")
				.replace("  ", " ").replace(" _", "_").replace("_ ", "_").replace("__", "_").replace(", ", ",").replace(",", " , ").replace(" _", "_").replace("_ ", "_").replace("  ", " ");
	}
	
	public static Gender firstLetters(String s){
		if(s.equals("who")){
			return Gender.PERSON;
		} else if(s.equalsIgnoreCase("you") || s.equalsIgnoreCase("your") || s.equalsIgnoreCase("yourself")){
			return Gender.YOU;
		} else if(s.equalsIgnoreCase("I") || s.equalsIgnoreCase("me") || s.equalsIgnoreCase("my") || s.equalsIgnoreCase("myself")){
			return Gender.I;
		} else if(s.equalsIgnoreCase("he") || s.equalsIgnoreCase("his") || s.equalsIgnoreCase("himself")){
			return Gender.MALE;
		} else if(s.equalsIgnoreCase("she") || s.equalsIgnoreCase("her") || s.equalsIgnoreCase("herself")){
			return Gender.FEMALE;
		} else if(s.equalsIgnoreCase("it") || s.equalsIgnoreCase("its") || s.equalsIgnoreCase("itself")){
			return Gender.OBJECT;
		} else if(s.equalsIgnoreCase("they") || s.equalsIgnoreCase("them") || s.equalsIgnoreCase("themselfes") || s.equalsIgnoreCase("themselves")){
			return Gender.GROUP;
		} else return null;
	}
	
	public static boolean directFirstLetters(String s){
		return s.equalsIgnoreCase("who") || s.equalsIgnoreCase("I") || s.equalsIgnoreCase("you") || s.equalsIgnoreCase("he") || s.equalsIgnoreCase("she") || s.equalsIgnoreCase("her") || s.equalsIgnoreCase("it") || s.equals("they");
	}
	
	public static boolean in(WordType type, WordType... types){
		for(WordType typ:types){
			if(type==typ){
				return true;
			}
		}
		return false;
	}
	
	public static int interest(){
		return (int) (2500*max(abs(def), abs(1.0-def)));
	}
}
