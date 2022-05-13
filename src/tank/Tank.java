package tank;

import State.*;
import java.awt.*;

public abstract class Tank {

    private int x;
    private int y;

    private int dx;
    private int dy;

    private State currentState;

    private String imgName;


    public Tank(int x, int y, String imgName) {
        this.x = x;
        this.y = y;
        this.imgName = imgName;
        this.currentState = new StateNorth();
    }

    public Image getImage() {
        return currentState.getImg(imgName);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public void turnNorth() {
        dx = 0;
        dy = -1;
        setCurrentState(new StateNorth());
    }

    public void turnSouth() {
        dx = 0;
        dy = 1;
        setCurrentState(new StateSouth());
    }

    public void turnWest() {
        dx = -1;
        dy = 0;
        setCurrentState(new StateWest());
    }

    public void turnEast() {
        dx = 1;
        dy = 0;
        setCurrentState(new StateEast());
    }

    public void stop() {
        dx = 0;
        dy = 0;
    }


    public void turnDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reset() {
        dx = dy = 0;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hit(Tank tank) {
        return x == tank.x && y == tank.y;
    }



}
