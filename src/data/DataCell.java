package data;

import idea.Subject;
import idea.Theory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import research.UEIIBase.Tense;
import control.AKISA;

public class DataCell {
	
	public static final String mainfolder = AKISA.mainfolder+"/database";
	public static final TreeSet<DataCell> loaded = new TreeSet<DataCell>(new Comparator<DataCell>(){
		@Override public int compare(DataCell o1, DataCell o2) {
			return o1.code()<o2.code()?-1:1;
		}
	});
	public static void set0(){
		for(DataCell c:loaded){
			c.marker = 0;
		}
	}
	
	public static void unload0(){
		for(DataCell c:loaded){
			if(c.marker==0){
				loaded.remove(c);
				try {c.save();} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
	
	public static DataCell load(Subject name) throws IOException {
		
		if(name==null){
			return null;
		}
		
		DataCell syn = new DataCell(name);
		DataCell synthetic = loaded.floor(syn);
		if(synthetic == null || synthetic.code() != syn.code()){
			File f = name.file();
			if(f.exists()){
				Path path = Paths.get(f.getAbsolutePath());
				syn = new DataCell(name, Files.readAllBytes(path));
				loaded.add(syn);
				return syn;
			} else {
				syn = new DataCell(name);
				loaded.add(syn);
				return syn;
			}
		} else {
			return synthetic;
		}
	}
	
	public static DataCell load(File f) throws IOException{
		Path path = Paths.get(f.getAbsolutePath());
		return new DataCell(null, Files.readAllBytes(path));
	}
	
	Subject name;
	boolean changed;
	int marker;
	public ArrayList<Theory> memory = new ArrayList<>();
	protected DataCell(Subject name){
		this.name=name;
	}
	
	byte[] data;
	int daindex=0, subindex=7;

	public void update(Theory t){
		changed = true;
		memory.remove(t);
		memory.add(t);
	}
	
	protected DataCell(Subject name, byte[] data){
		this(name);
		this.data=data;
		// a - b > c tense chance security
		
		// !!! lade...
		
		switch(readInt(4)){
		case 0:
			for(int i=readInt(4);i>0;i--){
				long a=readLong(), b=readLong(), c=readLong();
				Tense tense = Tense.byShort((char)readByte());
				float chance = Float.intBitsToFloat(readInt(4));
				float security = Float.intBitsToFloat(readInt(4));
				
				Theory t = new Theory(a, b, c, chance, tense, Theory.tempus+1000, this);
				t.security = security;
				memory.add(t);
			}
			break;
		default:
			daindex=0;
			System.out.println("Unknown protocolversion :( ("+readInt(4)+")");
		}
		
		Theory.tempus++;
	}
	
	public void save() throws IOException{
		File f = name.file();
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		FileOutputStream w = new FileOutputStream(f);
		
		writeInt(w, 0, 4);
		writeInt(w, memory.size(), 4);
		for(Theory th:memory){
			// schreibe die Theory...
			// a - b > c tense chance security
			writeLong(w, th.object.i);
			writeLong(w, th.bridge.i);
			writeLong(w, th.fact.i);
			writeInt(w, th.tense.shortCut, 1);
			writeInt(w, Float.floatToRawIntBits(th.chance), 4);
			writeInt(w, Float.floatToRawIntBits(th.security), 4);
		}
		
		w.close();
	}
	
	public long code(){
		return Hash.code(name+".datacell");
	}
	
	private void writeInt(FileOutputStream w, int integer, int size) throws IOException {
    	byte[] bytes = new byte[size];
    	for(int i=size-1;i>=0;i--){
    		bytes[i] = (byte) (integer&0xff);
    		integer>>=8;
    	}
    	w.write(bytes);
    }
	
	private void writeLong(FileOutputStream w, long l) throws IOException {
    	byte[] bytes = new byte[8];
    	for(int i=7;i>=0;i--){
    		bytes[i] = (byte) (l&0xff);
    		l>>=8;
    	}
    	w.write(bytes);
    }
	
	private long readLong(){
		long ret=0;
		for(int i=0;i<8;i++){
			ret = (ret<<8) + readByte();
		}
		return ret;
	}
	
	private int readInt(int bytes){
		int ret=0;
		for(int i=0;i<bytes;i++){
			ret = (ret<<8) + readByte();
		}
		return ret;
	}
	
	private int readByte(){
		int v = 7-subindex;
		return ((data[daindex++]<<v) + (daindex==data.length?0:data[daindex]<<(v-8))) & 255;
	}
	
	/*private int readBool(){
		if(subindex<0){
			subindex=7;
			daindex++;
		}
		return (data[daindex]>>subindex--) & 1;
	}*/
}
