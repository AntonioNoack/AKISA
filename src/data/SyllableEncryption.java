package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import control.AKISA;
import design.Sound;

public class SyllableEncryption {
	
	public static V[] vs = new V[49];
	static HashMap<String, String> ed = new HashMap<>(), de = new HashMap<>();
	static ArrayList<V> dic = new ArrayList<>();
	static void v(String v, int rel){
		dic.add(vs[dic.size()] = new V(v, rel));
	}
	
	static {
		init();
	}
	
	public static void main(String[] args){
		speak("An flying owl", false);
		
		//spreche("aaaaaaaaaaaaaaaaaaaaaaaaaa");
		//spreche(Text.x);
		
		//spreche("Dornröschen saß am Flussufer, wo sie gemütlich ein Buch las. Freitag ist Kekstag");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}
	
	/**
	 * Sentence -> s eh n t ax n s
	 * */
	public static String encode(String d){
		String ret = "";
		for(String s:d.toUpperCase().split(" ")){
			ret+=ed.get(s)+(char)44;
		}
		return ret;
	}
	
	/**
	 * s eh n t ax n s -> Sentence
	 * */
	public static String decode(String e){
		return de.get(e);
	}
	
	static boolean en;
	public static void speak(String sentence, boolean precalculated){
		if(!en){
			try {
				BufferedReader read = new BufferedReader(new FileReader(new File(AKISA.mainfolder+"static/beep.txt")));
				for(String line=read.readLine();line!=null;line=read.readLine()){
					if(!line.contains("\t"))continue;
					String d=line.substring(0, line.indexOf('\t')),// Sentence
							es=line.substring(line.lastIndexOf('\t')+1, line.length()), e="";// s eh n t ax n s
					
					for(String s:es.split(" ")){
						int hc = s.hashCode();
						for(V v:vs){
							if(v.c==hc){
								e+=""+(char)v.inde;
							}
						}
					}
					
					ed.put(d, e);
					de.put(e, d);
				}
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!precalculated){
			sentence = encode(sentence);
		}
		
		for(char c:sentence.toCharArray()){
			if(c>=0 && c<vs.length){
				call(vs[c]);
			}
		}
	}
	
	public static void spreche(String satz){
		satz = satz.toLowerCase().replace("y", "i").replace("nn", "n").replace("tt", "t").replace("mm", "m")
				
				.replace(".", c(44)).replace("ö", c(30)).replace("ä", c(31)).replace("ü", c(46)).replace("e ", c(31)+" ").replace("ett", c(31)+"t")
				.replace("x", "ks").replace("st", "scht").replace("sp", "schp").replace("sch", c(21))
				.replace("ei", c(37)).replace("ß", "s").replace("eu", c(38)).replace("ie", "i").replace("au", "ao")
				.replace("ach", "a"+c(48)).replace("uch", "u"+c(48))
				.replace("p", c(0)).replace("b", c(1)).replace("t", c(2)).replace("d", c(3)).replace("s", c(11)).replace("i", c(24)).replace("ch", c(16))
				.replace("v", c(9)).replace("m", c(5)).replace("n", c(6)).replace("l", c(7)).replace("r", c(47)).replace("e", c(45)).replace("o", c(26)).replace("a", c(25))
				.replace("u", c(34)).replace("w", c(10)).replace("h", " ").replace("k", c(4)).replace("j", c(8)).replace("g", c(15)).replace("f", c(9)).replace(" ", c(44));
		for(char c:satz.toCharArray()){
			if(c>=0 && c<vs.length){
				System.out.print(vs[c].v+" ");
				call(vs[c]);
			} else {
				System.out.println("?"+c+" ");
			}
		}
	}
	
	public static String c(int c){
		return (char)c+"";
	}
	
	public static void call(final V v){
		new Thread(new Runnable(){

			@Override
			public void run() {
				v.s.playSound();
			}
			
		}).start();
		try {
			Thread.sleep(v.sleep/350);
		} catch (InterruptedException e) {}
	}
	
	/**
	 * Huffmannkodiert sind das 5424 Zeichen pro 1126
	 * -> also ca 4.82 Zeichen... ganz gut für alternativ 1-3 Buchstaben, also 8-24 Bits
	 * */
	
	public static void init(){
		v("p", 31);
		v("b", 23);
		v("t", 68);
		v("d", 41);
		v("k", 47);
		// 5
		v("m", 31);
		v("n", 65);
		v("l", 55);
		v("r", 54);
		v("f", 18);
		// 10
		v("v", 12);
		v("s", 66);
		v("z", 36);
		v("hh", 8);
		v("w",  9);
		// 15
		v("g", 13);
		v("ch", 5);
		v("jh", 8);
		v("ng",16);
		v("th", 3);
		// 20
		v("dh", 122);
		v("sh",12);
		v("zh", 1);
		v("y",  8);
		v("iy",14);
		// 25
		v("aa", 9);
		v("ao",10);
		v("uw",10);
		v("er", 7);
		v("ih", 100);
		/// 30
		v("eh",24);
		v("ae",25);
		v("ah",15);
		v("oh",16);
		v("uh", 4);
		// 35
		v("ax",72);
		v("ey",20);
		v("ay",16);
		v("oy", 2);
		v("ow",15);
		// 40
		v("aw", 4);
		v("ia", 7);
		v("ea", 2);
		v("ua", 2);
		v("sil",0);
		
		ArrayList<V> save = new ArrayList<V>(dic);
		
		
		while(dic.size()>1){
			V a = lowest();
			dic.remove(a);
			V b = lowest();
			dic.remove(b);
			dic.add(new V(a, b));
		}
		
		//dic.get(0).show(0, 0);
		
		dic = save;
		
		v("e", 0);
		v("ue", 0);
		v("rr", 0);
		v("chr", 0);
		
	}
	
	static int ges;
	
	static V lowest(){
		int min = 10000;
		V ret = null;
		for(V v:dic){
			if(v.r<min){
				min=v.r;
				ret = v;
			}
		}
		return ret;
	}
	
	static class V {
		static int index;
		String v;int r, c, inde, sleep;
		Sound s;
		V a, b;
		public V(String v, int relative){
			this.v=v;r=relative;c=v.hashCode();
			inde=index++;
			File f = new File(AKISA.mainfolder+"beep/sounds/"+v+".wav");
			this.sleep=(int) f.length();
			try {
				s = new Sound(f);
			} catch (Exception e){}
			
		}
		
		public V(V v1, V v2){
			r = (a=v1).r + (b=v2).r;
		}
		
		public void show(int i, int l){
			if(a!=null){
				a.show(i*2+0, l+1);
				b.show(i*2+1, l+1);
			} else {
				String s = "00000000"+Integer.toBinaryString(i);
				s = s.substring(s.length()-l, s.length());
				System.out.println(s+" "+v+" "+r+" "+r*l);
				ges+=r;
			}
		}
	}
}
