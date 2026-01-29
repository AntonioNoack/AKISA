package research;

import idea.Theory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import control.AKISA;

public class UnderstandHTML {
	
	public static Document getDataFromURL(String url){
		
			
		File alternative = new File(AKISA.mainfolder+"database/lib/"+url.substring(url.indexOf(':')+1).replace(':', '_').replace('*', '_').replace('?', '_').replace('"', '_').replace('<', '_').replace('>', '_').replace('|', '_')+(url.endsWith(".html") || url.endsWith(".php") ? "":".html"));
		String ret = "";
		
		if(alternative.exists()){
			try {
				System.out.println(alternative.getAbsolutePath());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(alternative), "UTF8"));
				for(String z;(z=br.readLine())!=null;){
					ret+=z+"\n";
				}
				br.close();
				
				return Jsoup.parse(ret);
			
			} catch (IOException e) {
				System.out.println("I could not found "+url);
			}
		} else {
			try {
				BufferedReader br = null;
				
				try {
					br = new BufferedReader(new InputStreamReader(url.startsWith("https")?HTTPSClient.streaming(url):new URL(url).openConnection().getInputStream()));
					for(String z;(z=br.readLine())!=null;){
						ret+=z=z+"\n";	
					}
					br.close();
				} catch (IOException e) {
					System.out.println("I could not found "+url);
					ret = "<html>"+url+" couldn't be found.</html>";
				}
				
				if(!alternative.getParentFile().exists()){
					alternative.getParentFile().mkdirs();
				}
				
				FileWriter fw = new FileWriter(alternative);
				fw.write(ret);
				fw.flush();
				fw.close();
				
				return Jsoup.parse(ret);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Jsoup.parse("<html></html>");
	}
	
	public static void getInformationAbout(String searchfor) throws IOException {
		Document doc = getDataFromURL("https://en.wikipedia.org/wiki/"+searchfor);
		System.out.println(doc.title()+" -> "+doc.data().length());
		Elements text = doc.select("p");
		
		for(Element e:text){
			for(String s:extractHTML(e.html())){
				if(s.length()>0){
					try {
						for(Theory th:new UnderstandEnglishII(s).toTheory()){
							System.out.println(th.toString());
							Theory.add(th, 1.0);
						}
					} catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	public static String[] extractHTML(String s){
		s = s.replace("&nbsp;", " ").replace("i.e. ", " ").replace(" ca.", " about ").replace("e.g.", " for example ");
		ArrayList<String> ret = new ArrayList<>();
		String that="";
		boolean k = false;
		char[] c = s.toCharArray();
		for(int i=0;i<c.length;i++){
			switch(c[i]){
			case '<':
				while(c[i]!='>'){i++;}
				break;
			case '[':
				while(c[i]!=']'){i++;}
				break;
			case '.':
			case '!':
			case '?':
			case ';':
				if(!k && (i+1==c.length || (c[i+1]==' ' && c[i-1]!=' ') || c[i+1]=='\n')){
					ret.add(that);
					that="";
					i++;
					break;
				}
			case '(':
			case ')':
				k=!k;
			default:
				that+=c[i];
			}
		}
		if(that.length()>0){
			ret.add(that);
		}
		
		return ret.toArray(new String[ret.size()]);
	}
}
