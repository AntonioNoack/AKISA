package knowledge;

import math.MathHelper;

public class Coder {
	
	//Codierung:
	
	//London -> London
	//
	//00 = is
	//01 = has
	//10 = other
	//11 = Object of London
	//
	//
	////////////////////////////////////////////////
	//London = an Object
	//ID: 0000000000000000000000000000000000000000000000000000000110100001
	//'London''is''capital city of 'new york''
	//'London''has''history of'London''
	//
	//'1''2''3'0000		'1''2''4'0001'5'0000	''
	//
	
	public static char[] code(String[] data){
		// data = new String[]{"London","= capital city OF England and the UK",...,
		//							"(has) /HISTORY",
		//								"going back to its(London) founding by the romans, who named it Londium",...,
		//							"/HISTORY",
		//							"(has) /ANCIENT CORE",...,
		//								"(had) //POPULATION",
		//									"of 7,375 in 2011",
		//									"making it(ancient core) the smallest city in England",
		//								"//POPULATION",
		//							"/ANCIENT CORE"}
		
		
		
		return null;
	}
	
	public static String[] data(char[] code){
		
		
		
		return null;
	}
	
	public static String boolsFromInt(int i, int lenght){
		long max = MathHelper.pow(2, lenght);
		String res = "";
		while(max>1){
			if(max<=i){
				i-=max;
				res+="1";
			}else{
				res+="0";
			}
			max/=2;
		}
		return res;
	}
	
	/*
		Input aus Writer, Output an Writer: soll Platz sparen

		bzw. Input aus AKISA und Output an AKISA

	///////////////////////////////////////////////////////////////////
	
		w/london.txt
		
		@ Wikipedia
			 London (i/ËˆlÊŒndÉ™n/)[3] is the capital city of England and the United Kingdom.[4] It is the most populous city in the United Kingdom, with a
			metropolitan area of over 13 million inhabitants. Standing on the River Thames, London has been a major settlement for two millennia, its history going back
			to its founding by the Romans, who named it Londinium.[5] London's ancient core, the City of London, largely retains its 1.12-square-mile
			(2.9 km2) mediaeval boundaries and in 2011 had a resident population of 7,375, making it the smallest city in England. Since at least the 19th century,
			the term London has also referred to the metropolis developed around this core.[6] The bulk of this conurbation forms the Greater London
			administrative area (coterminous with the London region),[7][8][note 1] governed by the Mayor of London and the London Assembly.[9][note 2]
			
		London
			= capital city OF England and the UK
			it = most populous city in the UK, with 1* metropolian area OF over 13 million inhabitants
			standing on the river Thames, London(S)
										  London has been a major settlement for two millennia
			
			HISTORY
				going back to its(London) founding by the romans, who named it Londium.
			
			ANCIENT CORE						<-----------------------\
				= City of London										|
				largely retains its 1.12 sqMile medival boundaries		|
					and...												|
				in 2011 had a resident population of 7,375				|
																		|
				POPULATION (OF 7,375 (in 2011))							|
					making it(London) the smallest city in England		|
																		|
			TERM LONDON													|
				since at least the 19th Century							|
				has also referred to the METROPOLIS DEVELOPMENT AROUND this CORE
				
																A
							  __________________________________|
							  |
							  
				the bulk of THIS concurbation forms the Greater London administrative area ()
					= Großteil
							  |		 A conurbation is a region comprising a number of cities, large towns, and other urban areas that, through population growth and physical
							  |		expansion, have merged to form one continuous urban and industrially developed area. In most cases, a conurbation is a polycentric urban agglomeration,
							  |		in which transportation has developed to link areas to create a single urban labour market or travel to work area.[1]
							  |		conurbation
							  		
							governed by the mayor of london and the london assembly
				
				
				
				// 	 Water is a transparent fluid which forms the world's streams, lakes, oceans and rain, and is the major constituent of the fluids of living
					things. As a chemical compound, a water molecule contains one oxygen and two hydrogen atoms that are connected by covalent bonds. Water is a liquid at
					standard ambient temperature and pressure, but it often co-exists on Earth with its solid state, ice; and gaseous state, steam (water vapor).
	
	
	*/
}
