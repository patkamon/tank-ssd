import obstacle.Brick;
import obstacle.Obstacle;
import obstacle.Tree;
import obstacle.Steel;

import java.util.Random;

public class World  {

    private Obstacle [] steel;
    private Obstacle [] tree;
    private Obstacle[] brick;

    private int sizeX;
    private int sizeY;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        tree = new Obstacle[30];
        Random random = new Random();
        for(int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(random.nextInt(sizeX), random.nextInt(sizeY));
        }

        steel = new Obstacle[20];
        for(int i = 0; i < steel.length; i++) {
            steel[i] = new Steel(random.nextInt(sizeX), random.nextInt(sizeY));
        }

        brick = new Obstacle[20];
        for(int i = 0; i < brick.length; i++) {
            brick[i] = new Brick(random.nextInt(sizeX), random.nextInt(sizeY));
        }
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

}
