package Project3_220;

import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public abstract class Creature extends JLabel implements Path {
    protected Creature self;
    protected GameFrame gameFrame;
    protected GamePanel gamePanel;
    protected int width;
    protected int height;
    private int MaxHP;
    private int HP;
    protected int velocity;

    protected ImageIcon[][] SpriteSheet;

    protected boolean walk = false;
    protected boolean facingLet = false;

    //    protected
    protected void setMaxHP(int maxHP){ MaxHP = maxHP; }
    protected void setHP(int hp){
        HP = hp;
        //set the hp bar
    }
}
