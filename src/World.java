import State.StateNorth;
import obstacle.Brick;
import obstacle.Obstacle;
import obstacle.Tree;
import tank.Player;
import tank.Tank;

import javax.swing.*;
import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private Obstacle [] tree;
    private Brick [] brick;

    private int sizeX;
    private int sizeY;

    private Tank[] player;

    private Tank [] enemy;

    private Thread thread;
    private int tick;

    private boolean notOver;
    private long delayed = 1000/2;

    private boolean isSinglePlayer;

    public World(int sizeX, int sizeY,boolean isSinglePlayer) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.isSinglePlayer = isSinglePlayer;

        if (isSinglePlayer){
            player = new Tank[1];
            player[0] = new Player(0,sizeY-1,"player1");
            enemy = new Tank[3];

        }else{
            player = new Tank[2];
            player[0] = new Player(0,sizeY-1,"player1");
            player[1] = new Player(sizeX-1,sizeY-1,"player2");
            enemy = new Tank[2];
        }



        tree = new Obstacle[10];
        Random random = new Random();
        for(int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(random.nextInt(sizeX), random.nextInt(sizeY));
        }
    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public Tank[] getPlayer() {
        return player;
    }

    public Tank[] getEnemy() {
        return enemy;
    }

    public int getTick() {
        return tick;
    }

    public Obstacle[] getTree() {
        return tree;
    }

    public void start() {
        // set position
        for(int i =0 ; i<player.length; i++){
            player[i].reset();
        }
        tick = 0;
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    tick++;
                    //TODO:fix to loop
                    for(int i =0 ; i<player.length; i++){
                        player[i].move();
                        player[i].stop();
                    }
                    System.out.println(tick);
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }

    private void waitFor(long delayed){
        try{
            Thread.sleep(delayed);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
