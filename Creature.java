package Project3_220;

import java.awt.image.BufferedImage;
import javax.swing.*;

public abstract class Creature extends JLabel implements Path {
    protected static final int Attack=0;
    protected static final int Idle=3;
    protected static final int Run=4;
    protected static final int Roll=5;
    protected static final int Hit=6;
    protected static final int Death=7;

    protected GameFrame gameFrame;
    protected GamePanel gamePanel;
    protected int width, height;
    private   int MaxHP, HP;
    protected int velocity;

    protected BufferedImage[][] Animation;
    protected boolean facingLeft = false;
    protected void setMaxHP(int maxHP){
        MaxHP = maxHP;
        setHP(MaxHP);
    }
    protected void setHP(int hp){
        HP = hp;
        //set the hp bar
    }
}
