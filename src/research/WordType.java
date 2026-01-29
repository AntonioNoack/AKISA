package research;

public enum WordType {
	OPEN(""),
	CLOSE(""),
	HASHTAG(""),
	LIST("|||"),
	CUT("<!>"),
	PARTIZIP("(o)"),
	BY("by"),
	BACKTRACK("<-"),
	IT("X"),
	ITS("Y"),
	UNKNOWN("?"),
	ITERATOR(""),
	//ADJECTIVE_ADVERB("|adj"), <- irrelevant?
	PREPOSITION("_"),
	LOGICAL_OPERATOR("&"),
	END(".");
	
	public String sh;
	WordType(String sh){
		this.sh=sh;
	}
	
	public static WordType[] wordTypes(String[] words){
		WordType[] types = new WordType[words.length+1];
		types[words.length]=WordType.END;
		for(int i=0;i<words.length;i++){
			switch(words[i].toLowerCase()){
			case "#":  types[i]=HASHTAG;break;
			case "it": types[i]=IT;break;
			case "its":types[i]=ITS;break;
			case "(":  types[i]=OPEN;break;
			case ")":  types[i]=CLOSE;break;
			default:
				if(UnderstandEnglishII.isList(words[i])){
					types[i]=LIST;
				} else if(UnderstandEnglishII.isCut(words[i])){
					types[i]=CUT;
				} else if(UnderstandEnglishII.isIts(words[i])){
					types[i]=ITS;
				} else if(UnderstandEnglishII.isLogicalOperator(words[i])){
					types[i]=LOGICAL_OPERATOR;
				} else if(UnderstandEnglishII.isBacktracking(words[i])){
					types[i]=BACKTRACK;
				} else if(UnderstandEnglishII.isIterator(words[i])){
					types[i]=ITERATOR;
				} else if(UnderstandEnglishII.isPreposition(words[i]) || (i==0 && words[0].equals("back"))){
					types[i]=PREPOSITION;
				} else if(UnderstandEnglishII.isPartizip(words[i])){
					types[i]=PARTIZIP;
				//} else if(UnderstandEnglishII.isAdverb(words[i]) || UnderstandEnglishII.isAdjektiv(words[i])){
				//	types[i]=WordType.ADJECTIVE_ADVERB;
				} else types[i]=WordType.UNKNOWN;
			}
			System.out.print(words[i]+types[i].sh+" ");
		}
		System.out.println();
		return types;
	}
}
