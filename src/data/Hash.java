package data;

import java.util.HashMap;

public class Hash {
	
	public static String code(long i){
		try {
			return data.get((int)(i>>32)).get((int)i);
		} catch (Exception e){
			return null;
		}
	}
	
	public static long code(String s){
		long l = HashCode(s);
		int a  = (int) (l>>32), b = (int) l;
		if(!data.containsKey(a)){
			data.put(a, new HashMap<Integer, String>());
		}
		data.get(a).put(b, s);
		return l;
	}
	
	public static HashMap<Integer, HashMap<Integer, String>> data = new HashMap<>();
	public static final long seed = 0xacbdef123456789L;
	public static long HashCode(String s){
		final int m = 0x5bd1e995;

		// Initialize the hash to a 'random' value

		// Mix 4 bytes at a time into the hash

		char[] data = s.toCharArray();
		int len = data.length;
		long h = seed ^ len;
		int i=0;
		
		for(i=0;i+8<len;){
			long d=0;
			for(int j=0;j<9;j++){
				d<<=7;
				d+=data[i++];
			}
			
			d *= m;
			d ^= d >> 56;
			d *= m;
			
			h *= m;
			h ^= d;
		}
		
		long d = 0;
		for(;i<len;){
			d<<=7;
			d+=data[i++];
		}
		
		d *= m;
		d ^= d >> 56;
		d *= m;
		
		h *= m;
		h ^= d;

		// Do a few final mixes of the hash to ensure the last few
		// bytes are well-incorporated.

		h ^= h >> 13;
		h *= m;
		h ^= h >> 15;

		return h;
	}
}
