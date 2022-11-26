package Project3_220;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Player extends Creature implements KeyListener{
    private static final int Attack=0;
    private static final int Idle=3;
    private static final int Run=4;
    private static final int Roll=5;
    private static final int Hit=6;
    private static final int Death=7;

    private boolean pressA=false, pressD=false, roll=false;
    private int MP;

    public Player(GameFrame gameFrame,GamePanel gamePanel){
        this.gameFrame = gameFrame;
        this.gamePanel=gamePanel;
        height = 420;
        width =  height*120/80;
        setMaxHP(1000);
        setHP(1000);
        velocity=1;
        lastAtk = 2; maxAtk = 3;
        actionindex = Idle;
//        MP=800;

        importImages();

        setBounds(1280-width/2, 560-height, width, height);
//        setBackground(Color.GRAY);
        setOpaque(false);
    }
    private void importImages(){
        String[] picname = {"Attack", "Attack2", "AttackCombo", "Idle", "Run", "Roll", "Hit", "Death"};
        Animation = new BufferedImage[picname.length][12];

        for (int i = 0; i < picname.length; i++) {
            try {
                BufferedImage fullpic = ImageIO.read(new File(SSPath+"Knight/_"+picname[i]+".png"));

                for (int j = 0; j < fullpic.getWidth()/120; j++)
                    Animation[i][j] = Path.resizeBuffer(fullpic.getSubimage(j*120,0,120,80),width,height);

                if(i==Hit) Animation[i][1] =Animation[i][2] =Animation[i][3] =Animation[i][0];
            } catch (IOException e) { }
        }
    }

    public void updateAni(int update){
        if(update%20==0){
            int maxindex = switch (actionindex) {
                case Attack, Hit -> 4;
                case Attack + 1 -> 6;
                case Attack + 2, Death, Idle, Run -> 10;
                case Roll -> 12;
                default -> 1;
            };
            frameindex = ++frameindex % maxindex;
            if(!facingLeft) setIcon(new ImageIcon(Animation[actionindex][frameindex]));
            else setIcon(new ImageIcon(Path.flipH(Animation[actionindex][frameindex])));

            if(frameindex == maxindex - 1){
                if (actionindex == Death) frameindex--;
                else if (actionindex == Hit){
                    hit = false;
                }
                else if (actionindex <= Attack + 2) {
                    atk = false;
                    if (pressA || pressD) actionindex = Run;
                    else actionindex = Idle;
                    frameindex = 0;
                }
                else if (actionindex == Roll) {
                    roll = false;
                    if (pressA || pressD) actionindex = Run;
                    else actionindex = Idle;
                    frameindex = 0;
                }
            }
        }
        if(update%velocity==0) doWalk();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(!death&&!hit&&!roll&&!atk) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    pressA = true;
                    facingLeft = true;
                    break;
                case KeyEvent.VK_D:
                    pressD = true;
                    facingLeft = false;
                    break;
                case KeyEvent.VK_K:
                    doAtk();
                    break;
                case KeyEvent.VK_SPACE:
                    doRoll();
                    break;
            }
            checkStatus();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                 pressA= false;
                 if(pressD) facingLeft=false;
                break;
            case KeyEvent.VK_D:
                 pressD= false;
                 if(pressA) facingLeft=true;
                break;
        }
        checkStatus();
    }
    @Override
    public void checkStatus() {//private void setStatus()
        if(death) {
            if (actionindex!=Death) frameindex=0;
            actionindex = Death;
        }
        else{
            if(hit) {
                actionindex = Hit;
                atk=false;
            }
            else{
                if(roll) actionindex=Roll;
                else{
                    if(!atk){
                        if(pressA || pressD){
                            if(actionindex!=Run) frameindex=0;
                            actionindex=Run;
                        }
                        else{
                            if(actionindex!=Idle) frameindex=0;
                            actionindex=Idle;
                        }
                    }
                }
            }
        }
    }
    public void doWalk(){
        if(!death&&!hit&&!atk) {
            if (facingLeft) {
                if(roll || (pressA && getLocationOnScreen().x+(3*width/8) - 1 >= gameFrame.getX()))
                    setLocation(getX() - 1, getY());
            } else {
                if(roll || (pressD && getLocationOnScreen().x+(5*width/8) + 1 < gamePanel.getLocationOnScreen().x + gamePanel.getWidth()))
                    setLocation(getX() + 1, getY());
            }
        }

        gamePanel.setlocation(getLocationOnScreen().x,width,facingLeft);
    }

    private void doAtk(){
        if(!hit&&!death&&!roll&&!atk){
            atk=true;
            lastAtk = ++lastAtk%maxAtk;
            actionindex=lastAtk;
            frameindex=0;
        }
    }
    private void doRoll(){
        if(!hit&&!death&&!roll){
            roll=true;
            frameindex=0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) { }
    //override
    @Override
    public void setHP(int hp){
        super.setHP(hp);
        //set the HP bar
    }

    //Working with Enemy
    public Rectangle getHitBox(){
        Rectangle t = getBounds();
        return new Rectangle(t.x+45*42/8, t.y,  30*42/8, t.height);
    }
    public boolean canBeAttacked(int x, int width){
        x = x+width*2/5;
        int px = getLocationOnScreen().x+this.width*3/8;
        int hitBoxWidth = this.width/4;
        return x==px || (x>px && x-px+hitBoxWidth<=width/10) || (x<px && px-x+width/5<=width/10);
    }
    public boolean isOnTheLeft(int x){
        return getLocationOnScreen().x<x;
    }
    public void gotAttacked(int damage){
        int hp = getHP()-damage;
        if(hp<=0){
            death = true;
            hp = 0;
        }
        else {
            hit = true;
            frameindex = 0;
        }
        setHP(hp);
        System.out.println(hp);

        checkStatus();
    }
}
