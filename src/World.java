import obstacle.Brick;
import obstacle.Obstacle;
import obstacle.Tree;
import obstacle.Steel;

import java.util.Random;

public class World  {

    private Obstacle [] steel;
    private Obstacle [] tree;
    private Brick [] brick;

    private int sizeX;
    private int sizeY;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        tree = new Obstacle[10];
        Random random = new Random();
        for(int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(random.nextInt(sizeX), random.nextInt(sizeY));
        }

        steel = new Obstacle[10];
        for(int i = 0; i < steel.length; i++) {
            steel[i] = new Steel(random.nextInt(sizeX), random.nextInt(sizeY));
        }
    }


    public Obstacle[] getTree() {
        return tree;
    }
    public Obstacle[] getSteel() {
        return steel;
    }

}
