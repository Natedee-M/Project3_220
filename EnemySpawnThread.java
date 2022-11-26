package Project3_220;

import javax.swing.*;
//import java.awt.*;
import java.util.*;

public class EnemySpawnThread extends Thread{
    private boolean gameStart = true;
    private GamePanel gamepanel;
//    private GameFrame gameFrame;
//    private Player player;
    private int Level;
    private int Wave;

    private ArrayList<Creature> EnemyList;
//    private ArrayList<Projectile> ProjectileList;

    public EnemySpawnThread(GamePanel gamepanel, Player player, int Level, int Wave){//GameFrame gameFrame,
//        this.gameFrame = gameFrame;
        this.gamepanel = gamepanel;
//        this.player = player;
        this.Level = Level;
        this.Wave = Wave;
        EnemyList = new ArrayList<>();
//        ProjectileList = new ArrayList<>();
        Goblin.LoadResource(player, Level);
    }
    public void run(){
        /*while (gameStart){
            try {
                enemySpawn();
                Thread.sleep(5000);//1)make it random  2)depends on difficulty lv.  3)Show wave number
            }catch (InterruptedException e){e.printStackTrace();}
        }*/
        boolean clearWave = true;
        int ene=0;
        for(int i=0, enemyNum=new Random().nextInt(2,(int) Math.ceil(Level/2.0)+5);  i<Wave && gameStart;  i++){
            for(int j=0; j<enemyNum && gameStart && clearWave; j++){
                try {
                    Thread.sleep(new Random().nextInt(1000,5000));
                }catch (InterruptedException e){e.printStackTrace();}
                finally {
                    enemySpawn();
                    System.out.println(++ene);
                }
            }

            if(EnemyList.size()==0){
                clearWave=true;
            }else {
                clearWave=false;
                i--;
            }
            enemyNum += new Random().nextInt(1,3+Level);
        }
    }
    public void enemySpawn(){
        Creature temp = Goblin.createGoblin(new Random().nextInt(0,1280*2));
//        if(new Random().nextBoolean()) EnemyList.add(Goblin.createGoblin(new Random().nextInt(0,1280*2)));
//        else EnemyList.add(Skeleton.createSkeleton(new Random().nextInt(0,1280*2)));

        gamepanel.add(temp,JLayeredPane.DRAG_LAYER);//new Random().nextInt(3,5)*100
        EnemyList.add(temp);
    }
    /*public void enemySpawn(){
        Enemy enemyRef;
        int rand = (int)( Math.random()*(1280*2) );//random location to spawn
        System.out.println(rand);
        enemyRef = new Enemy(gamepanel,player,EnemyList,ProjectileList);
        EnemyList.add(enemyRef);
        enemyRef.setBounds(rand, 700, enemyRef.width, enemyRef.height);//move to its constructor
        enemyRef.setOpaque(true);//move to its constructor
        gamepanel.add(enemyRef,JLayeredPane.MODAL_LAYER);
        gamepanel.validate();
    }*/
    public void UpdateAllEnemy(int num){
        for(int i = 0 ; i < EnemyList.size() ; i++) {
            if(num%20==0) EnemyList.get(i).checkStatus();
            EnemyList.get(i).updateAni(num);
            EnemyList.get(i).doWalk(num);
            //doDamage
            //gotDamage
        }
//        if(num%3==0) for(int i = 0 ; i < EnemyList.size() ; i++) EnemyList.get(i).Walking();
    }
    /*public void UpdateAllProjectileLocation(int num){
        if(num%3==0) for (int i = 0 ; i < ProjectileList.size() ; i++) ProjectileList.get(i).UpdateLocation();
    }*/
}
