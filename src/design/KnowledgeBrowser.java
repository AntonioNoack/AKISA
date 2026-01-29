package design;

import idea.Theory;

import java.io.File;
import java.io.IOException;

import control.AKISA;
import data.DataCell;

public class KnowledgeBrowser {
	public static void main(String[] args) throws IOException{
		for(File f0:new File(AKISA.mainfolder+"database/knowledge/").listFiles()){
			if(!f0.getName().equals("D"))
			for(File f1:f0.listFiles()){
				for(File f2:f1.listFiles()){
					for(File f3:f2.listFiles()){
						DataCell c = DataCell.load(f3);
						for(Theory t:c.memory){
							System.out.println(t);
						}
					}
				}
			}
		}
	}
}
