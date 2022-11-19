package Project3_220;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Creature implements KeyListener{
    private static final int Attack=0;
    private static final int Idle=1;
    private static final int Run=2;
    private static final int Roll=3;
    private static final int Hit=4;
    private static final int Death=5;
    private int Status;
    private int MP;

    public Player(GameFrame gameFrame,GamePanel gamePanel){
        this.gameFrame = gameFrame;
        this.gamePanel=gamePanel;
        self = this;
        height = 280;
        width =  height*120/80;
        setMaxHP(1000);
        setHP(1000);
        velocity=15;
        MP=800;
        Status = 2;

        String[] picname = {"Attack","Attack2","AttackCombo",
                "Idle","Run","Roll","Hit","Death"};//CrouchTransition","Crouch","CrouchWalk","CrouchAttack",
        SpriteSheet = new ImageIcon[picname.length][12];
        for (int i = 0; i < picname.length ; i++) {
            ImageIcon pic = new ImageIcon(SSPath+"Knight/_"+picname[i]);
            for (int j = 0; j < pic.getIconWidth()/120; j++) {
                Image image = Path.crop(pic.getImage(),j*120,0,120,80).getScaledInstance(width,height,Image.SCALE_SMOOTH);
                SpriteSheet[i][j] = new ImageIcon(image);
            }
        }

        setBounds(1280-width/2, 560-height, width, height);
        setBackground(Color.LIGHT_GRAY);
        setOpaque(true);
    }

    public void updateAni(int update){
        int i,j;
        switch(Status){
            case Attack:
                i = new Random().nextInt(3);
                break;
        }
        j = switch (i){
            case 0->
        };
        setIcon(SpriteSheet[i][j]);
    }
//    private int getSpriteSheetNum
    private void setStatus(){//int status
        if(walk){
            Status = Run;
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                if(getLocationOnScreen().x-velocity>gameFrame.getX())
                    setLocation(getX()-velocity, getY());
                walk = true;
                gamePanel.setlocation(getLocationOnScreen().x,getX(),velocity);
                break;
            case KeyEvent.VK_D:
                if(getLocationOnScreen().x+width+velocity<gamePanel.getLocationOnScreen().x+gamePanel.getWidth())
                    setLocation(getX()+velocity, getY());
                walk = true;
                gamePanel.setlocation(getLocationOnScreen().x+width,getX()+width,velocity*-1);
                break;
            /*case KeyEvent.VK_S://
                //roll
                break;*/
        }
        setStatus();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                walk = false;
                break;
            case KeyEvent.VK_D:
                walk = false;
                break;
            /*case KeyEvent.VK_S://
                //roll
                break;*/
        }
        setStatus();
    }

    public int getVelocity(){ return velocity; }
    @Override
    public void keyTyped(KeyEvent e) { }
}
