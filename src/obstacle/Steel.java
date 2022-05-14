package obstacle;

import javax.swing.*;

public class Steel extends Obstacle {

    public Steel(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setImage(new ImageIcon("img/block.jpeg").getImage());
    }



}