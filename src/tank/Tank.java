package tank;

import State.*;
import obstacle.Obstacle;

import java.awt.*;

public abstract class Tank {

    private int x;
    private int y;

    private int dx;
    private int dy;

    private State currentState;

    private String imgName;

    private BulletPool bulletPool;
    private int boardX;
    private int boardY;

    public Tank(int x, int y, String imgName,int boardx, int boardy) {
        this.x = x;
        this.y = y;
        this.imgName = imgName;
        this.currentState = new StateNorth();
        this.bulletPool = new BulletPool();
        this.boardX = boardx;
        this.boardY = boardy;
    }


    public BulletPool getBulletPool() {
        return bulletPool;
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


    public void move(Obstacle[] brick, Obstacle[] steel) {

        if (this.x + dx >= boardX || this.x + dx <=-1) {return;}
        else if (this.y + dy >= boardY || this.y + dy <=-1) {return;}
        else if (wasBlock(brick) || wasBlock(steel)){return ;}
        else {
            this.x += dx;
            this.y += dy;
        }
    }

    public boolean wasBlock(Obstacle[] obs){
        for(Obstacle o: obs){
            if (o.getX() == this.x+dx && o.getY() == this.y+dy){return true;}
        }
        return false;
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
