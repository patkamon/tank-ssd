import State.StateNorth;
import State.StateSouth;
import obstacle.Brick;
import obstacle.Obstacle;
import obstacle.Tree;
import obstacle.Steel;

import tank.Bullet;
import tank.Enemy;
import tank.Player;
import tank.Tank;

import java.util.*;

public class World extends Observable {

    private Obstacle [] steel;
    private Obstacle [] tree;
    private Obstacle[] brick;

    private Obstacle[] saveBrick;

    private List<Bullet> bullets;
    private List<Bullet> eBullets;

    private int sizeX;
    private int sizeY;

    private Player [] player;
    private Enemy [] enemy;

    private Thread thread;
    private int tick;

    private boolean notOver;
    private boolean isWining;
    private long delayed = 1000/2;

    private boolean isSinglePlayer;

    public World(int sizeX, int sizeY,boolean isSinglePlayer) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        tree = new Obstacle[30];

        bullets = new ArrayList<Bullet>();
        eBullets = new ArrayList<Bullet>();
        this.isSinglePlayer = isSinglePlayer;

        if (isSinglePlayer){
            player = new Player[1];
            player[0] = new Player(0,sizeY-1,"player1",sizeX,sizeY);
            enemy = new Enemy[2];
            enemy[0] = new Enemy(0,0,"enemy1",sizeX,sizeY);
            enemy[1] = new Enemy(sizeX-1,sizeY-1,"enemy2",sizeX,sizeY);

        }else{
            player = new Player[2];
            player[0] = new Player(0,sizeY-1,"player1",sizeX,sizeY);
            player[1] = new Player(sizeX-1,0,"player2",sizeX,sizeY);
            enemy = new Enemy[0];
        }

        tree = new Obstacle[10];

        Random random = new Random();
        for(int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(random.nextInt(sizeX), random.nextInt(sizeY));
        }

        steel = new Obstacle[20];
        for(int i = 0; i < steel.length; i++) {
            steel[i] = new Steel(random.nextInt(1,sizeX-1), random.nextInt(1,sizeY-1));
        }

        brick = new Obstacle[20];
        saveBrick = new Obstacle[20];
        for(int i = 0; i < brick.length; i++) {
            brick[i] = new Brick(random.nextInt(sizeX), random.nextInt(1,sizeY-1));
            saveBrick[i] = new Brick(brick[i].getX(),brick[i].getY());
        }
    }


    public List<Bullet> getBullets() {
        return bullets;
    }


    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public List<Bullet> geteBullets() {
        return eBullets;
    }


    public Player[] getPlayer() {
        return player;
    }

    public Enemy[] getEnemy() {
        return enemy;
    }

    public int getTick() {
        return tick;
    }

    public Obstacle[] getTree() {
        return tree;
    }
    public Obstacle[] getSteel() {
        return steel;
    }
    public Obstacle[] getBrick() {
        return brick;
    }

    public void load(){
        // load save brick
        for(int i = 0; i < brick.length; i++) {
            brick[i] = new Brick(saveBrick[i].getX(),saveBrick[i].getY());
        }
    }
    public void start() {
//        load();
        brick[0].setX(-99);
        System.out.println(brick[0].getX());
        System.out.println(saveBrick[0].getX());
        // set position
        for(int i =0 ; i<player.length; i++){
            player[i].reset();
            player[i].setCurrentState(new StateNorth());
        }
        for(Bullet bullet : bullets) {
            bullet.reset();
        }
        for(Bullet bullet : eBullets) {
            bullet.reset();
        }
        player[0].setPosition(0,sizeY-1);
        if (!isSinglePlayer) {
            player[1].setPosition(sizeX-1,0);
        }else{
            for(int i =0 ; i<enemy.length; i++){
                enemy[i].reset();
                enemy[i].setCurrentState(new StateSouth());
            }
            enemy[0].setPosition(0,0);
            enemy[1].setPosition(sizeX-1,sizeY-1);
        }

        tick = 0;
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    tick++;
                    for(int i =0 ; i<player.length; i++){
                        player[i].move(brick,steel);
                        player[i].stop();
                    }
                    for(Bullet bullet : bullets) {
                        bullet.move();
                    }
                    for(Bullet bullet : eBullets) {
                        bullet.move();
                    }
                    for(int i =0 ; i<enemy.length; i++){
                        enemy[i].move(brick,steel);
                    }
                    checkCollisions();
                    if (isSinglePlayer) {
                        checkWining();
                    }
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }


    private void checkWining(){
        for(Enemy e: enemy){
            if (!e.isDead()){
                return;
            }
        }
        isWining = true;
        notOver = false;
    }


    private void checkCollisions() {
        if (isSinglePlayer) {
            for(Tank enemy: enemy){
                if (enemy.hit(player[0])) {
                    notOver = false;
                }
            }
            for(Bullet bullet : bullets) {
                if(bullet.hit(player[0])){
                    notOver = false;
                }
                for(Enemy e: enemy){
                    if (bullet.hit(e)) {
                        bullet.reset();
                        e.setDead(true);
                        e.setPosition(-999,-999);
                    }
                }
                for(Obstacle o: brick){
                    if (bullet.hitObstacle(o)) {
                        bullet.reset();
                        o.setX(-99);
                        o.setY(-99);
                    }
                }
                for(Obstacle o: steel){
                    if (bullet.hitObstacle(o)) {
                        bullet.reset();
                    }
                }
            }
            for(Bullet bullet : eBullets) {
                if(bullet.hit(player[0])){
                    notOver = false;
                }
                for(Enemy e: enemy){
                    if (bullet.hit(e)) {
                        bullet.reset();
                    }
                }
                for(Obstacle o: brick){
                    if (bullet.hitObstacle(o)) {
                        bullet.reset();
                        o.setX(-99);
                        o.setY(-99);
                    }
                }
                for(Obstacle o: steel){
                    if (bullet.hitObstacle(o)) {
                        bullet.reset();
                    }
                }
            }
        }else{
            if (player[0].hit(player[1])) {
                notOver = false;
            }
            for(Bullet bullet : bullets) {
                for(Tank player: player)
                if(bullet.hit(player)){
                    notOver = false;
                }

            }
            for(Bullet bullet : eBullets) {
                if(bullet.hit(player[0])){
                    notOver = false;
                }
                for(Enemy e: enemy){
                    if (bullet.hit(e)) {
                        e.setDead(true);
                        e.setPosition(-999,-999);
                    }
                }
            }
        }
    }

    public boolean isWining() {
        return isWining;
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
