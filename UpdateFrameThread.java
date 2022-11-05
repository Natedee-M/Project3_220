package Project3_220;

import static java.lang.Math.pow;

public class UpdateFrameThread extends Thread{
    private int FPS = 120;
    
    public void run(){//make thread die correctly
        double TimePerFrame = pow(10,9)*1.0/FPS;
        long now, lastFrame=System.nanoTime();
        
        while(true){
            now = System.nanoTime();
            if(now-lastFrame >= TimePerFrame){
                //call repaint()
                lastFrame = now;
            }
        }
    }
    
    public void changeFPS(int fps){ FPS = fps; }
}
