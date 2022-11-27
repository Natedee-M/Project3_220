package Project3_220;

import java.awt.image.BufferedImage;
import javax.swing.*;

public abstract class Creature extends JLabel implements Path {
    protected GameFrame gameFrame;
    protected GamePanel gamePanel;
    protected int width, height;
    private   int MaxHP=800, HP=800;
    protected int velocity, lastAtk=0, maxAtk=1;
    protected int actionindex, frameindex=0;

    protected BufferedImage[][] Animation;
    protected boolean facingLeft = false, walking = false, spawning = true;
    protected boolean atk=false,    hit=false,    death=false;

    protected void setMaxHP(int maxHP)  { MaxHP = maxHP; }
    protected void setHP(int hp){
        if(hp<MaxHP) HP = hp;
        else HP = MaxHP;
    }
    protected int getHP()               { return HP; }

    //Enemy method
//    public abstract void checkStatus();
//    public void doWalk(int update){}
//    public abstract void doWalk();
//    public abstract void updateAni(int update);

}
