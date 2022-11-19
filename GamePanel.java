package Project3_220;

import java.awt.*;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePanel extends JLayeredPane implements Path{
    private final Rectangle ground = new Rectangle(0, 560, 1280*2, 160);
    private GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame){
        this.gameFrame=gameFrame;
        setBounds(-640, 0, 1280*2, 720);
        setLayout(null);
        
        for(int i=0; i<2; i++){
            JLabel g = new JLabel(new ImageIcon(BGPath+"GameBG1.png"));
            g.setBounds(i*1280, 0, 1280, 720);
            add(g);
        }
    }

    public void setlocation(int xscreen, int px, int vel){
        if(vel>0 && xscreen<gameFrame.getX()+gameFrame.getWidth()/5   && getX()+vel<=0 ||
           vel<0 && xscreen>gameFrame.getX()+gameFrame.getWidth()*4/5 && getX()+getWidth()+vel>gameFrame.getWidth())
                setLocation(getX()+vel, 0);
    }
}
