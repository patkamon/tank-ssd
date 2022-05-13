package obstacle;

import java.awt.*;

public abstract class Obstacle {

    private int x;
    private int y;


    private Image image;

    public int getX() {
        return x;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
