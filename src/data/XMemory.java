package data;

import idea.Subject;
import idea.Theory;

import java.io.IOException;
import java.util.ArrayList;

public class XMemory extends Memory {

	public static final XMemory instance = new XMemory();
	protected XMemory() {
		super(5000);
	}

	@Override public ArrayList<Theory> getLocalInfos(Subject topic) {
		for(DataCell c:DataCell.loaded){
			if(c.name.equals(topic)){
				return c.memory;
			}
		}
		try {
			return DataCell.load(topic).memory;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override public void loadIN(Subject topic) throws IOException {
		DataCell.load(topic);
	}
}
