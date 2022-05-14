import State.StateNorth;
import obstacle.Brick;
import obstacle.Obstacle;
import obstacle.Tree;
import tank.Bullet;
import tank.Player;
import tank.Tank;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private Obstacle [] tree;
    private Brick [] brick;

    private Thread s;
    private List<Bullet> bullets;

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
        bullets = new ArrayList<Bullet>();
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

    public void burstBullets(Tank tank) {
        int dx,dy;
        if (tank.getCurrentState().getState() ==  "West"){dx = -1; dy = 0;}
        else  if (tank.getCurrentState().getState() ==  "South"){dx = 0; dy = 1;}
        else  if (tank.getCurrentState().getState() ==  "East"){dx = 1; dy = 0;}
        else{dx = 0; dy = -1;}

        bullets.add(player[0].getBulletPool().requestBullet(tank.getX()+dx, tank.getY()+dy, dx,dy));
        shrink();

    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void shrink(){
        s = new Thread() {
            @Override
            public void run() {
                while(getBullets().size() <= 5) {
                    try {
                        Thread.sleep(30000);
                        for(int i =5 ; i< player[0].getBulletPool().getBullets().size(); i++){
                            player[0].getBulletPool().getBullets().remove(i);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("No No");
                    }
                }
            }
        };
        s.start();
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
        for(Bullet bullet : bullets) {
            bullet.reset();
        }
        player[0].setPosition(0,sizeY-1);
        if (!isSinglePlayer) {
            player[1].setPosition(sizeX-1,sizeY-1);
        }

        tick = 0;
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    tick++;
                    if (tick>30){
                        notOver = false;
                    }
                    //TODO:fix to loop
                    for(int i =0 ; i<player.length; i++){
                        player[i].move();
                        player[i].stop();
                    }
                    for(Bullet bullet : bullets) {
                        bullet.move();
                    }
//                    tickBullet();
                    checkCollisions();
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }


    public void tickBullet() {
        moveBullets();
        cleanupBullets();
    }

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
        }
    }

    private void cleanupBullets() {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets) {
            if(bullet.getX() < 0 ||
                    bullet.getX() > sizeX ||
                    bullet.getY() < 0 ||
                    bullet.getY() > sizeY) {
                toRemove.add(bullet);
            }
        }
        for(Bullet bullet : toRemove) {
            bullets.remove(bullet);
            player[0].getBulletPool().releaseBullet(bullet);
        }
    }
    private void checkCollisions() {
        if (isSinglePlayer) {
//            for (Enemy e : allEnemies) {
//                if (e.hit(player)) {
//                    notOver = false;
//                }
//            }
        }else{
            if (player[0].hit(player[1])) {
                notOver = false;
            }
        }
    }

    private void waitFor(long delayed){
        try{
            Thread.sleep(delayed);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public boolean isGameOver() {
        return !notOver;
    }

}
