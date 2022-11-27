package Project3_220;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;

public class Goblin extends Creature{
    private static Goblin Prototype;
    private static Player player;
    private static ArrayList<Goblin> EnemyList;
    private static int    Level;
    private static final int Attack=0;
    private static final int Idle=2;
    private static final int Run=3;
    private static final int Hit=4;
    private static final int Death=5;

    private boolean cooldown = false;
    private int cooldownTime;
    private static class GoblinThread extends UpdateFrameThread{
        private boolean isAlive=true;
        private Goblin  goblin;
        private GoblinThread(Goblin g){ goblin = g; }
        public void goblinGotKilled(){ isAlive = false; }
        public void run(){
            try { Thread.sleep(100); } catch (InterruptedException e) { }

            double TimePerUpdate = pow(10,9)*1.0/UPS,  deltaU = 0;
            long previoustime = System.nanoTime();
            long lastcheck=System.currentTimeMillis(), currentTime, now, spawnTime = lastcheck;
            int update = 0;
            //long now, lastFrame=System.nanoTime();

            while(isAlive){
                currentTime = System.nanoTime();
                now = System.currentTimeMillis();
                deltaU += (currentTime - previoustime)/TimePerUpdate;
                previoustime = currentTime;
                if(deltaU >= 1){
                    if (!gamePause) goblin.update(++update);
                    deltaU--;
                }
                if(now - lastcheck >= 1000){
                    lastcheck = now;
                    update =0;
                }
            }
            long deadTime = System.currentTimeMillis();
            if ((deadTime-spawnTime)/1000<10-Goblin.Level) Goblin.player.gotScore((int) (((10-Goblin.Level)*1000-(deadTime-spawnTime))/100) + (20*(Goblin.Level+1)));
            else Goblin.player.gotScore(20*(Goblin.Level+1));
            try { Thread.sleep(3000); } catch (InterruptedException e) { }
            goblin.setIcon(null);
        }
    }
    private GoblinThread thread;

    public void update(int update){
        if (!hit) player.isHitting(this);
        if(update%50==0){
            checkStatus();
            updateAni();
        }
        if(update%velocity==0) doWalk();
    }
    private void checkStatus(){
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
                    if(atk && (frameindex==6||frameindex==7) && !cooldown) {
                        attack();
                        Goblin goblin = this;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(goblin.getCooldownTime());
                                } catch (InterruptedException e) { }
                                finally {
                                    goblin.finishCooldown();
                                }
                            }
                        }.start();
                    }
                    else if (!atk){
                        facingLeft = player.isOnTheLeft(getLocationOnScreen().x+width/2);
                        if(player.canBeAttacked(getLocationOnScreen().x,width)){
                            walking = false;
                            if(cooldown){
                                if(actionindex!=Idle) frameindex = 0;
                                actionindex = Idle;
                            }
                            else {
                                atk = true;
                                lastAtk = ++lastAtk%maxAtk;
                                actionindex = lastAtk;
                                frameindex = 0;
                            }
                        }
                        else {//walk to player
                            walking = true;
                            if(actionindex!=Run) frameindex = 0;
                            actionindex = Run;
                        }
                    }
                }
            }
        }
    }
        private void attack(){
            Rectangle playerHitBox = player.getHitBox();
            Rectangle t = getBounds();
            Rectangle attackRange = new Rectangle(t.x+30*42/8, t.y,  90*42/8, t.height);
            if (actionindex==Attack+1){
                attackRange = new Rectangle(t.x+10*42/8, t.y,  65*42/8, t.height);
                attackRange.setLocation(attackRange.x+65*42/8, attackRange.y);
            }
            if(playerHitBox.intersects(attackRange)){
                player.gotAttacked(new Random().nextInt(90,125));
            }
        }
        private int getCooldownTime(){ cooldown = true;  return cooldownTime; }
        private void finishCooldown(){ cooldown = false; }
    private void updateAni() {
        int maxindex = switch (actionindex) {
            case Idle, Death, Hit -> 4;
            case Attack, Attack + 1, Run -> 8;
            default -> 1;
        };
        frameindex = ++frameindex % maxindex;
        if(!facingLeft) setIcon(new ImageIcon(Animation[actionindex][frameindex]));
        else setIcon(new ImageIcon(Path.flipH(Animation[actionindex][frameindex])));

        if(frameindex == maxindex - 1){
            if (actionindex == Death) {
                thread.goblinGotKilled();
                EnemyList.remove(this);
            }
            else if (actionindex == Hit){
                hit = false;
                if (walking) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }
            else if (actionindex <= Attack + 1) {
                atk = false;
                if (walking) actionindex = Run;
                else actionindex = Idle;
                frameindex = 0;
            }
        }
    }
    private void doWalk(){
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
        public void gotAttacked(int damage){
            if (!spawning){
                int hp = getHP() - damage;
                if (hp <= 0) {
                    death = true;
                    hp = 0;
                    player.gotHeal(new Random().nextInt(100, 175));
                } else hit = true;
                setHP(hp);
                checkStatus();
            }
        }

    //Prepare and contruct a Goblin
    private Goblin(){}
    public static void LoadResource(Player p, ArrayList<Goblin> EnemyList, int Level){
        player = p;
        Goblin.EnemyList = EnemyList;
        Goblin.Level = Level;

        Prototype = new Goblin();
        Prototype.width = 150*420/80;
        Prototype.height = 100*420/80;
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
        temp.width = Prototype.width;
        temp.height = Prototype.height;
        temp.setMaxHP(500+150*(Level+1));
        temp.setHP(500+150*(Level+1));
        temp.velocity = 3;
        temp.lastAtk = 1;
        temp.maxAtk = 2;
        temp.actionindex = Idle;
//        temp.spawning = true;
        temp.cooldownTime = 1750-150*Level;
        temp.setBounds(locationToSpawn,560, temp.width,temp.height);
//        temp.setBackground(Color.LIGHT_GRAY);
        temp.setOpaque(false);

        temp.thread = new GoblinThread(temp);
        temp.thread.start();

        return temp;
    }
}
