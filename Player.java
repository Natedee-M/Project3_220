package Project3_220;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Player extends Creature implements KeyListener{
    private int MP, lastAtk=0;
    private int actionindex=Idle, frameindex=0;
    private boolean pressA=false, pressD=false, roll=false;
    private boolean atk=false,    hit=false,    death=false;

    public Player(GameFrame gameFrame,GamePanel gamePanel){
        this.gameFrame = gameFrame;
        this.gamePanel=gamePanel;
        height = 420;
        width =  height*120/80;
        setMaxHP(1000);
        velocity=1;
        MP=800;

        importImages();

        setBounds(1280-width/2, 560-height, width, height);
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

            if (actionindex == Death && frameindex == maxindex - 1) frameindex--;
            else if (actionindex <= Attack + 2 && frameindex == maxindex - 1) {
                atk = false;
                if (pressA || pressD) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }
            else if (actionindex == Roll && frameindex == maxindex - 1) {
                roll = false;
                if (pressA || pressD) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }
        }
        doWalk();
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
            setStatus();
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
        setStatus();
    }
    private void setStatus(){
        if(death) actionindex = Death;
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
    private void doWalk(){
        if(!death&&!hit&&!atk) {
            if (facingLeft) {
                if(roll || (pressA && getLocationOnScreen().x+(25*width/80) - velocity >= gameFrame.getX()))
                    setLocation(getX() - velocity, getY());
            } else {
                if(roll || (pressD && getLocationOnScreen().x-(3*width/8) + width + velocity < gamePanel.getLocationOnScreen().x + gamePanel.getWidth()))
                    setLocation(getX() + velocity, getY());
            }
        }

        gamePanel.setlocation(getLocationOnScreen().x,width,facingLeft,velocity);
    }
    private void doAtk(){
        if(!hit&&!death&&!roll&&!atk){
            atk=true;
            actionindex=Attack+new Random().nextInt(3);
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
}
