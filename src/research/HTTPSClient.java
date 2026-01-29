package research;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSClient {
	
	/*Certificate[] certs = con.getServerCertificates();
	for(Certificate cert : certs){
		System.out.println("Cert Type : " + cert.getType());
		System.out.println("Cert Hash Code : " + cert.hashCode());
		System.out.println("Cert Public Key Algorithm : " 
												+ cert.getPublicKey().getAlgorithm());
		System.out.println("Cert Public Key Format : " 
												+ cert.getPublicKey().getFormat());
		System.out.println("\n");
	}*/
	
	public static InputStream streaming(String surl) throws MalformedURLException, IOException{
		return ((HttpsURLConnection) new URL(surl).openConnection()).getInputStream();
	}
}