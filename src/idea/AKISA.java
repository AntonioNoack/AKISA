package idea;

import research.Brain;

public class AKISA extends Subject {

	public static final long numberReserved = 0x8000000000000000L, numberSpace = 0x7fffffffffffffffL;
	
	public AKISA() {
		super(0);
	}
	
	private float floating(Subject s){
		if(isNumber(s) || isNumber(s = Brain.getNumber(s))){
			return Float.intBitsToFloat((int)s.i);
		} else return Float.NaN;
	}

	public Subject add(Subject a, Subject b){
		return new Subject(Float.floatToIntBits(floating(a)+floating(b)));
	}
	
	public Subject sub(Subject a, Subject b){
		return new Subject(Float.floatToIntBits(floating(a)-floating(b)));
	}
	
	public Subject multiply(Subject a, Subject b){
		return new Subject(Float.floatToIntBits(floating(a)*floating(b)));
	}
	
	public Subject divide(Subject a, Subject b){
		return new Subject(Float.floatToIntBits(floating(a)/floating(b)));
	}
	
	public Subject pow(Subject a, Subject b){
		return new Subject(Float.floatToIntBits((float)Math.pow(floating(a), floating(b))));
	}
	
	public void say(Theory a){
		System.out.println(a.object.beautifulName()+" -"+a.bridge.beautifulName()+"> "+(a.fact==null?"":a.fact.beautifulName()));
	}
	
	public static boolean isNumber(Subject a){
		if(a!=null && (a.i & 0xffffffff00000000L) == numberReserved){
			return true;
		}
		// sonst könnte es eine Zahl und ein Subject erkennen
		return false;
	}
	
	public void reportDone(Theory t){
		
	}
}
