package data;

import idea.Subject;
import idea.Theory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NavigableSet;
import java.util.TreeSet;

import research.UEIIBase.Tense;

public abstract class Memory {

	public static final Comparator<Theory> compare = new Comparator<Theory>(){
		@Override public int compare(Theory o1, Theory o2) {	
			return (int) (o1.time-o2.time);
		}
	};
	
	public final int refresh;
	public long lastRefresh;
	protected Memory(int refresh){
		this.refresh=refresh;
		myMemory.put(this, new TreeSet<Theory>(compare));
	}
	
	public static HashMap<Memory, NavigableSet<Theory>> myMemory = new HashMap<>();
	
	public abstract ArrayList<Theory> getLocalInfos(Subject topic);
	
	public abstract void loadIN(Subject topic) throws IOException;
	
	public void clearGarbage(NavigableSet<Theory> memory){
		if(Theory.tempus-lastRefresh>refresh){
			lastRefresh = Theory.tempus;
			myMemory.put(this, memory.headSet(new Theory(0, 0, 0, 0, Tense.PRESENT, lastRefresh, null), true));
			for(Theory t:myMemory.get(this)){
				if(t.datacell!=null){
					t.datacell.marker++;
				}
			}
		}
	}
	
	public static void updateGarbageCollection(){
		DataCell.set0();
		for(Memory m:myMemory.keySet()){
			m.clearGarbage(myMemory.get(m));
		}
		DataCell.unload0();
	}
	
	/**
	 * eine noch philosophische Frage...
	 * -> Verarbeitung spricht gemeinsames Abspeichern geänderter Daten
	 * XMemory und TMemory verschwindet
	 * @throws IOException 
	 * */
	public static void sleep() throws IOException {
		for(DataCell cell:DataCell.loaded){
			if(cell.changed){
				cell.save();
			}
		}
	}

	/**
	 * starte die Morgenruitine oder erinnere dich an interessante Dinge von gestern... xD
	 * */
	public static void awake(){
		TMemory.instance.hashCode();
		XMemory.instance.hashCode();
	}
}
