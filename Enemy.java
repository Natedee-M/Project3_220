package Project3_220;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import java.util.Arrays;

class Enemy extends Creature{
    private int enemyID;
    private boolean isAlive = true , isWalk = true;
    private JLayeredPane ContentPane;//gamePanel in Creature?
    private Player player;
    private boolean Shooting = false;
    private ArrayList EnemyList;
    private ArrayList ProjectileList;
    public Enemy(JLayeredPane ContentPane, Player player, ArrayList EnemyList,ArrayList ProjectileList) {
        //this.maxhp = setMaxHP();
        this.ContentPane = ContentPane;
        this.player = player;
        this.EnemyList = EnemyList;
        this.ProjectileList = ProjectileList;
        setMaxHP(100);setHP(100);

        height = 280;
        width = height*120/80;

        velocity = 1;
        enemyID = (int) (Math.random() * (2));
        if(enemyID==0)setBackground(Color.RED);else setBackground(Color.blue);
        System.out.println(enemyID);
    }

    public void checkAlive(){if(super.getHP() <= 0){isAlive = false;ContentPane.remove(this);EnemyList.remove(this);}}
    public void Walking(){
        if(getLocation().y > 560-height) {setLocation(getLocation().x,getLocation().y-2);}
        else if( getLocation().y == 560-height ){
            if(enemyID == 0) {
                setLocation(getLocation().x-(1*setDir(player)),getLocation().y);
                isWalk = true;
            }
            else if(enemyID == 1 && getPlayerDelta(player)<400){
                isWalk = false;
//                if(!Shooting) {ProjectileSpawner spawner = new ProjectileSpawner(this,ContentPane,ProjectileList);Shooting = true;}
            }else {
                setLocation(getLocation().x-(1*setDir(player)),getLocation().y);
                isWalk = true;
                Shooting = false;
            }
            checkAlive();
        }
    }

    public int setDir(Player player){
        if(player.getLocation().x>getLocation().x) return -1;
        else if(player.getLocation().x<getLocation().x) return 1;
        else {return 0;}
    }

    public int getPlayerDelta(Player player){
        return Math.abs(player.getLocation().x - getLocation().x);
    }

    public boolean getStill(){
        return !isWalk;
    }

    public Player getPlayer(){
        return player;
    }
}
