package data;

import idea.Subject;
import idea.Theory;

import java.util.ArrayList;

/**
 * stellt das Kurzzeitgedächtnis von 2s bis 20min dar
 * wenn wir mal auch an uninteressante Dinge kommen müssten die hier gefiltert werden
 * */
public class TMemory extends Memory {

	public static final TMemory instance = new TMemory();
	protected TMemory() {
		super(100);
	}

	/**
	 * gibt das verfügbare Wissen zu dem Thema an
	 * ... allerdings im Kutzzeitgedächtnis, d.h. die Infos müssen grade erst aufgetaucht sein... !!!
	 * */
	@Override public ArrayList<Theory> getLocalInfos(Subject topic) {
		return new ArrayList<>();
	}

	@Override public void loadIN(Subject topic) {
		myMemory.get(this).addAll(XMemory.instance.getLocalInfos(topic));
	}
}
