package design;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Musictests {
	public static final int SAMPLE_RATE = 16 * 1024;
	static int index = 0;
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, SAMPLE_RATE);
		line.start();

		for(int i=0;i<500;i++)  {
			byte[] toneBuffer = createSinWaveBuffer();
			line.write(toneBuffer, 0, toneBuffer.length);
		}
		
		line.drain();
		line.close();
	}
	
	private static byte[] createSinWaveBuffer() {
		byte[] output = new byte[1024];
		for(int i=0;i<1024;index++){
			int k = (int)((s(0.253)+s(0.8)*0.4+0.14*s(0.36))*(250));
			
			output[i++] = (byte)(k>>8);
			output[i++] = (byte)(k);
		}
		return output;
	}
	
	private static double s(double s){
		return Math.sin(s*index);
	}
}
