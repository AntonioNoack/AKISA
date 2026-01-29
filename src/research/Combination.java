package research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import math.Random;
import data.Memory;
import data.TMemory;
import idea.Subject;
import idea.Theory;

public class Combination {
	
	/**
	 * eine Frage oder Aussage...
	 * 
	 * */
	public static ArrayList<C> thoughtsAbout(Theory t){
		
		boolean n = t.fact==null;
		Combination cc = new Combination(t.object, n?t.bridge:t.fact, n?t.fact:t.bridge);
		ArrayList<C> c = cc.solved;
		if(c.size()>0){
			// I know the answer
			
			return c;
		} else {
			
			System.out.println("I don't know whether "+t);
			
			// !!! cool... add to database? I don't really know... let's google about it...
			for(int i=0;i<100;i++){
				Subject s = cc.a.get(Random.instance.nextInt(cc.a.size()));
				if(s!=null && TMemory.instance.getLocalInfos(s).size()<50){
					//try {
					//	UnderstandHTML.getInformationAbout(s.beautifulName());
					//} catch (IOException e) {}
				}
			}
			return c;
		}
	}
	
	private ArrayList<Subject> a = new ArrayList<>(), b = new ArrayList<>();
	private ArrayList<Theory> ta = new ArrayList<>(), tb= new ArrayList<>();
	private ArrayList<Integer> c = new ArrayList<>(), d = new ArrayList<>();
	private ArrayList<Memory> mem = new ArrayList<>();
	private ArrayList<C> solved = new ArrayList<>();
	private int ai, bi;
	private Combination(Subject active, Subject done, Subject bridge){
		
		// durchsuche das Hirn auf 1. Ebene
		for(Memory m:Memory.myMemory.keySet()){
			mem.add(m);
		}

		a.add(active);
		ta.add(null);
		c.add(-1);
		b.add(done);
		d.add(-1);
		tb.add(null);
		
		boolean fn = true;
		while(fn && solved.size()==0){
			fn = false;
			int step=a.size();
			for(;ai<step;ai++){
				for(Memory m:mem){
					for(Theory t:m.getLocalInfos(a.get(ai))){
						fn |= test(ai, t, a, b, c, d, ta, tb);
					}
				}
			}
			
			step = b.size();
			for(;bi<step;bi++){
				for(Memory m:mem){
					for(Theory t:m.getLocalInfos(b.get(bi))){
						fn |= test(bi, t, b, a, d, c, tb, ta);
					}
				}
			}
		}
	}
	
	private boolean test(int ai, Theory t, ArrayList<Subject> a, ArrayList<Subject> b, ArrayList<Integer> c, ArrayList<Integer> d, ArrayList<Theory> ts, ArrayList<Theory> ts2){
		boolean added = false;
		if(b.contains(t.object)){
			solved.add(new C(t, ai, b.indexOf(t.object), ts, ts2, c, d));
		} else if(b.contains(t.bridge)){
			solved.add(new C(t, ai, b.indexOf(t.bridge), ts, ts2, c, d));
		} else if(t.fact!=null && b.contains(t.fact)){
			solved.add(new C(t, ai, b.indexOf(t.fact), ts, ts2, c, d));
		} else {
			if(!a.contains(t.object)){
				a.add(t.object);
				c.add(ai);
				ts.add(t);
				added=true;
			}
			if(!a.contains(t.bridge)){
				a.add(t.bridge);
				c.add(ai);
				ts.add(t);
				added=true;
			}
			if(t.fact!=null && !a.contains(t.fact)){
				a.add(t.fact);
				c.add(ai);
				ts.add(t);
				added=true;
			}
		}
		return added;
	}
	
	public static class C {
		private static final Comparator<S> com = new Comparator<S>(){
			@Override public int compare(S s, S t) {
				return t.i-s.i;
			}
		};
		public TreeSet<S> way = new TreeSet<S>(com);
		public C(Theory t, int a, int b, ArrayList<Theory> ta, ArrayList<Theory> tb, ArrayList<Integer> c, ArrayList<Integer> d){
			way.add(new S(t, 0));
			int i=1;
			while(a>0){
				way.add(new S(ta.get(a), i++));
				a = c.get(a);
			}
			
			i=-1;
			while(b>0){
				way.add(new S(tb.get(b), i--));
				b = d.get(b);
			}
		}
	}
	
	public static class S {
		int i;
		public Theory t;
		public S(Theory t, int index){
			this.t=t;i=index;
		}
	}
}
