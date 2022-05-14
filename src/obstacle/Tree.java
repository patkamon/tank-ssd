package obstacle;

import obstacle.Obstacle;

import javax.swing.*;

public class Tree extends Obstacle {

    public Tree(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setImage(new ImageIcon("img/grass_.jpeg").getImage());
    }



}
