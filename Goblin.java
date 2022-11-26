package Project3_220;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Goblin extends Creature{
    private static Goblin Prototype;
    private static Player player;
    private static int    Level;
    private static final int Attack=0;
    private static final int Idle=2;
    private static final int Run=3;
    private static final int Hit=4;
    private static final int Death=5;

    private boolean cooldown = false;
    private int cooldownTime;

    @Override
    public void checkStatus(){
        if(!spawning){
            if(death) {
                if (actionindex!=Death) frameindex=0;
                actionindex = Death;
            }
            else {
                if (hit) {
                    if (actionindex!=Hit) frameindex=0;
                    actionindex = Hit;
                    atk = walking = false;
                }
                else {
                    if(!atk){
                        if(player.canBeAttacked(getLocationOnScreen().x,width)){
                            if(cooldown){
                                if(actionindex!=Idle) frameindex = 0;
                                actionindex = Idle;
                            }
                            else {
                                atk = true;
                                lastAtk = ++lastAtk%maxAtk;
                                actionindex = lastAtk;
                                frameindex = 0;
                                attack();
                            }
                        }
                        else {//walk to player
                            facingLeft = player.isOnTheLeft(getLocationOnScreen().x);
                            walking = true;
                            if(actionindex!=Run) frameindex = 0;
                            actionindex = Run;
                        }
                    }
                }
            }
        }
    }
    public void attack(){
        Rectangle playerHitBox = player.getHitBox();
        Rectangle t = getBounds();
        Rectangle attackRange = new Rectangle(t.x+30*42/8, t.y,  90*42/8, t.height);
        if(playerHitBox.intersects(attackRange)){
            player.gotAttacked(new Random().nextInt(90,125));
        }
    }
    @Override
    public void updateAni(int update) {
        if(update%20==0){
            int maxindex = switch (actionindex) {
                case Idle, Death, Hit -> 4;
                case Attack, Attack + 1, Run -> 8;
                default -> 1;
            };
            frameindex = ++frameindex % maxindex;
            if(!facingLeft) setIcon(new ImageIcon(Animation[actionindex][frameindex]));
            else setIcon(new ImageIcon(Path.flipH(Animation[actionindex][frameindex])));

            if (actionindex == Death && frameindex == maxindex - 1) frameindex--;//kill thread
            else if (actionindex <= Attack + 1 && frameindex == maxindex - 1) {
                atk = false;
                if (walking) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }
            /*else if (actionindex == Roll && frameindex == maxindex - 1) {
                roll = false;
                if (pressA || pressD) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }*/
        }
    }
    public void doWalk(int update){
        if(update%velocity==0) doWalk();
    }
    @Override
    public void doWalk() {
        if(spawning){
            if (getY()>560-getHeight()) setLocation(getX(),getY()-2);
            else spawning = false;
        }
        else if(actionindex == Run){
            if(facingLeft){
                setLocation(getX()-1,getY());
            }else {
                setLocation(getX()+1,getY());
            }
        }
    }

    private Goblin(){}
    public static void LoadResource(Player p, int Level){
        player = p;
        Goblin.Level = Level;

        Prototype = new Goblin();
        String[] picname = {"Attack", "Attack2", "Idle", "Run", "Hit", "Death"};
        Prototype.Animation = new BufferedImage[picname.length][8];

        for (int i = 0; i < picname.length; i++) {
            try {
                BufferedImage fullpic = ImageIO.read(new File(SSPath+"Goblin/"+picname[i]+".png"));

                for (int j = 0; j < fullpic.getWidth()/150; j++)
                    Prototype.Animation[i][j] = Path.resizeBuffer(fullpic.getSubimage(j*150,0,150,100),Prototype.width,Prototype.height);
            } catch (IOException e) { }
        }
    }
    public static Goblin createGoblin(int locationToSpawn){
        Goblin temp = new Goblin();
        temp.Animation = Prototype.Animation;

//        temp.gameFrame = gameFrame;
//        temp.gamePanel = gamePanel;
        temp.width = 150*420/80;
        temp.height = 100*420/80;
        temp.setMaxHP(500+150*(Level+1));
        temp.setHP(500+150*(Level+1));
        temp.velocity = 2;
        temp.lastAtk = 1;
        temp.maxAtk = 2;
        temp.actionindex = Idle;
//        temp.spawning = true;
        temp.cooldownTime = 1500-175*Level;
        temp.setBounds(locationToSpawn,560, temp.width,temp.height);
//        temp.setBackground(Color.LIGHT_GRAY);
        temp.setOpaque(false);

        return temp;
    }
}
