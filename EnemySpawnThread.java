package Project3_220;

import javax.swing.*;
//import java.awt.*;
import java.util.*;

public class EnemySpawnThread extends Thread{
    private boolean gameStart = true;
    private GamePanel gamepanel;
    private int Level;
    private int Wave;
    private ArrayList<Goblin> EnemyList;

    public EnemySpawnThread(GamePanel gamepanel, Player player, int Level, int Wave){//GameFrame gameFrame,
        this.gamepanel = gamepanel;
        this.Level = Level;
        this.Wave = Wave;
        EnemyList = new ArrayList<>();
        Goblin.LoadResource(player, EnemyList, Level);
    }
    public void run(){
        /*while (gameStart){
            try {
                enemySpawn();
                Thread.sleep(5000);//1)make it random  2)depends on difficulty lv.  3)Show wave number
            }catch (InterruptedException e){e.printStackTrace();}
        }*/
        boolean clearWave = true;
        for(int i=0, enemyNum=(int) Math.ceil(Level/2.0)+2;  i<Wave && gameStart;  i++){
//            if (clearWave) System.out.printf("Wave: %d | Enemy: %d\n", (i+1), enemyNum);
            for(int j=0; j<enemyNum && gameStart && clearWave; j++){
                try {
                    Thread.sleep(new Random().nextInt(1500,3500));
                }catch (InterruptedException e){e.printStackTrace();}
                finally {
                    enemySpawn();
                }
            }

            if(EnemyList.size()==0){
                clearWave=true;
                enemyNum += (1+Level);
            }else {
                clearWave=false;
                i--;
            }
        }
        //if(gameStart) game Summary
    }
    public void enemySpawn(){
        Goblin temp = Goblin.createGoblin(new Random().nextInt(640,1132));

        gamepanel.add(temp,new Random().nextInt(3,5)*100);
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
    }
    public void UpdateAllEnemy(int num){
        for(int i = 0 ; i < EnemyList.size() ; i++) {
            if(num%20==0) EnemyList.get(i).checkStatus();
            EnemyList.get(i).updateAni(num);
            EnemyList.get(i).doWalk(num);
            //doDamage
            //gotDamage
        }
        if(num%3==0) for(int i = 0 ; i < EnemyList.size() ; i++) EnemyList.get(i).Walking();
    }
    public void UpdateAllProjectileLocation(int num){
        if(num%3==0) for (int i = 0 ; i < ProjectileList.size() ; i++) ProjectileList.get(i).UpdateLocation();
    }*/
}
