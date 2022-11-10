package Project3_220;

import static java.lang.Math.pow;
import javax.swing.JFrame;

public class UpdateFrameThread extends Thread{
    private final int FPS = 120;
    private final int UPS = 200;
    private JFrame frame;
    private boolean GameOn = true;
    
    //public UpdateFrameThread(JFrame frame)  { this.frame = frame; }
    public void setFrame(JFrame frame)      { this.frame = frame; }
    public void setGameOn(boolean b)        { GameOn = b; }
    
    public void run(){
        double TimePerFrame = pow(10,9)*1.0/FPS;
        double TimePerUpdate = pow(10,9)*1.0/UPS;
        long previoustime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;
        long currentTime;
        //long now, lastFrame=System.nanoTime();
        
        while(GameOn){
            currentTime = System.nanoTime();//now = 
            deltaU += (currentTime - previoustime)/TimePerUpdate;
            deltaF += (currentTime - previoustime)/TimePerFrame;
            previoustime = currentTime;
            if(deltaU >= 1){
                //update();
                deltaU--;
            }
            if(deltaF >= 1) {
                frame.repaint();
                deltaF--;
            }
            /*if(now-lastFrame >= TimePerFrame){
                //call repaint()
                lastFrame = now;
            }*/
        }
    }
    
    //public void changeFPS(int fps){ FPS = fps; }

    private void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
