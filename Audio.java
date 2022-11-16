package Project3_220;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
    private Clip clip;

    public Audio(String filename){
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    
    public void playOnce() { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop() { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()     { clip.stop(); }
}