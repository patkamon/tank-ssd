package obstacle;

import javax.swing.*;

public class Brick extends Obstacle {



    public Brick(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setImage(new ImageIcon("img/brick.jpeg").getImage());
    }
}
