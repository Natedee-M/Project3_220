package Project3_220;

import javax.swing.*;

public class GamePanel extends JLayeredPane implements Path{
    private GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame){
        this.gameFrame=gameFrame;
        setBounds(-640, 0, 1280*2, 720);
        setLayout(null);
        
        for(int i=0; i<2; i++){
            JLabel g = new JLabel(new ImageIcon(BGPath+"GameBG1.png"));
            g.setBounds(i*1280, 0, 1280, 720);
            add(g,JLayeredPane.DRAG_LAYER);
        }
    }

    public void setlocation(int xonscreen, int width, boolean facingLeft){
        if(facingLeft){
            if(xonscreen<gameFrame.getX()+gameFrame.getWidth()/5 && getX()+1<=0)
                setLocation(getX()+1, getY());
        }
        else {
            if(xonscreen+width>gameFrame.getX()+gameFrame.getWidth()*4/5 &&
              getX()+getWidth()-1>gameFrame.getWidth())
                setLocation(getX()-1, getY());
        }
    }
}
