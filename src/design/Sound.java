package design;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {

    Clip clip;
    
    public Sound(File f){
    	try {
    	    AudioInputStream stream;
    	    AudioFormat format;
    	    DataLine.Info info;
    	    
    	    stream = AudioSystem.getAudioInputStream(f);
    	    format = stream.getFormat();
    	    info = new DataLine.Info(Clip.class, format);
    	    clip = (Clip) AudioSystem.getLine(info);
    	    clip.open(stream);
    	} catch (Exception e) {}
    }

    boolean sec=false;
    public void playSound(){
    	if(clip.isRunning()){
    		clip.stop();
    	}
    	clip.setFramePosition(0);
    	clip.start();
    }
}