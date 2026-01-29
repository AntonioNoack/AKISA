package math;


/**
 * <br>1786418477ns sind 4537502932 Ticks, also ca 4-5 Ticks pro Random...
 * Math.random() ~ 72 Ticks
 * java.util.Random ~ 75 Ticks
 * */
public class Random {
	
	public static Random instance = new Random(System.currentTimeMillis());
	public long seed;
	
	/**
	 * Creates a new Random with seed as seed, even if to big to generate good results.
	 * */
	public Random(long seed){
		this.seed=seed;
	}
	
	/**
	 * this.seed = seed
	 * */
	public void setSeed(long seed){
		this.seed=seed;
	}
	
	/**
	 * returns a random value [0, 1[
	 * <br> by seed = (214013*seed+2531011)&0xffffffffffL;
	 * <br> return ((seed>>16)&0xffff) / 65536.0;
	 * */
	static double f16k = 0.0000152587890625;
	public double next(){
		seed = (214013*seed+2531011)&0xffffffffffL;
		return ((int)(seed>>16)&0xffff)*0.0000152587890625;
	}
	
	/**
	 * returns a random value [0, max[, but max should be <65536
	 * */
	public int nextInt(int max){
		return (int) (next()*max);
	}
	
	/**
	 * (float) [0, 1[;
	 * */
	public float nextFloat(){
		return (float) next();
	}

	/**
	 * [0, 1[ < 0.5
	 * */
	public boolean nextBoolean() {
		return next()<0.5;
	}
	
	public int nextBigInt(int i){
		return (((int)(next()*65536) << 16) + (int)(next()*65536)) % i;
	}

	public long nextLong() {
		return ((long)(next()*65536) << 48) + ((long)(next()*65536) << 32) + ((long)(next()*65536) << 16) + (long)(next()*65536);
	}
	
	public int rawNext(){
		return (int) (((seed = (214013*seed+2531011))>>16)&0xffffff);
	}
}
