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
    private int MaxMP, MP, score=0;
    private Thread regenMP;

    public Player(GameFrame gameFrame,GamePanel gamePanel){
        this.gameFrame = gameFrame;
        this.gamePanel=gamePanel;
        height = 420;
        width =  height*120/80;
        setMaxHP(1000);
        setHP(1000);
        velocity = 2;
        lastAtk = 2; maxAtk = 3;
        actionindex = Idle;
        MaxMP = MP = 800;

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

                if(i==Hit)
                    for (int j = 1; j < 8; j++)
                        Animation[i][j] =Animation[i][0];
            } catch (IOException e) { }
        }
    }

    public void updateAni(int update){
        if(update%30==0){
            int maxindex = getMaxFrameIndex();
            frameindex = ++frameindex % maxindex;
            if(!facingLeft) setIcon(new ImageIcon(Animation[actionindex][frameindex]));
            else setIcon(new ImageIcon(Path.flipH(Animation[actionindex][frameindex])));

            if(frameindex == maxindex - 1){
                if (actionindex == Death) frameindex--;//exit game
                else if (actionindex == Hit){
                    hit = false;
                    if (pressA || pressD) actionindex = Run;
                    else actionindex = Idle;
                    frameindex = 0;
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
        if (regenMP==null || !regenMP.isAlive()){
            regenMP = new Thread(){
                @Override
                public void run() {
                    while (MP<MaxMP){
                        try { Thread.sleep(40); } catch (InterruptedException ex) {}
                        setMP(2);
                    }
                }
            };
            regenMP.start();
        }
        checkStatus();
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
    public void checkStatus() {
        if(death) {
            if (actionindex!=Death) frameindex=0;
            actionindex = Death;
        }
        else{
            if(hit) {
                if (actionindex!=Hit) frameindex=0;
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
        if(!hit&&!death&&!roll&&!atk&&MP>=60){
            atk=true;
            lastAtk = ++lastAtk%maxAtk;
            actionindex=lastAtk;
            frameindex=0;
            setMP(-60);
        }
    }
    private void doRoll(){
        if(!hit&&!death&&!roll&&MP>=120){
            roll=true;
            frameindex=0;
            setMP(-120);
        }
    }
    @Override
    public void setHP(int hp){
        super.setHP(hp);
        //set the HP bar
    }
    private int getMaxFrameIndex(){
        return switch (actionindex) {
            case Attack -> 4;
            case Attack + 1 -> 6;
            case Hit -> 8;
            case Attack + 2, Death, Idle, Run -> 10;
            case Roll -> 12;
            default -> 1;
        };
    }
    private void setMP(int usedMP){
        if(MP+usedMP>MaxMP) MP=MaxMP;
        else if (MP+usedMP<0) MP=0;
        else {
            MP += usedMP;
        }
        //set MP bar
    }

    //Working with Enemy
    public void gotScore(int score){ this.score += score; }
    public void gotHeal(int heal){
        setHP(getHP()+heal);
    }
    public void isHitting(Goblin goblin){
        if(atk){
            Rectangle gb = goblin.getBounds();
            Rectangle goblinHitBox = new Rectangle(gb.x+gb.width*60/150,gb.y, gb.width*30/150,gb.height);
            Rectangle playAttackRange = new Rectangle(getX()+4,getY(), width/3,height);
            if(!facingLeft) playAttackRange.setLocation(getX()+width*3/5, getY());
            if(playAttackRange.intersects(goblinHitBox) &&
                    ((actionindex<Attack+2&&(frameindex==2||frameindex==1)) ||
                            (actionindex==Attack+2&&(frameindex==1||frameindex==2||frameindex==6||frameindex==7)))){
                goblin.gotAttacked(new Random().nextInt(90,125));
                if(actionindex == Attack+2) goblin.gotAttacked(new Random().nextInt(100,140));
            }
        }
    }
    public Rectangle getHitBox(){
        Rectangle t = getBounds();
        return new Rectangle(t.x+45*42/8, t.y,  t.width/4, t.height);
    }
    public boolean canBeAttacked(int x, int width){
        x = x+width*45/150;
        int px = getLocationOnScreen().x+this.width*45/120;
        return x==px || (x>px && x-px<=this.width*30/120) || (x<px && px-x<=width*60/150);
    }
    public boolean isOnTheLeft(int x){
        return getLocationOnScreen().x+this.width/2 < x;
    }
    public void gotAttacked(int damage){
        int hp = getHP()-damage;
        if (roll) hp += damage;
        else {
            if(hp<=0){
                death = true;
                hp = 0;
            }
            else hit = true;
            setHP(hp);
        }

        checkStatus();
    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
