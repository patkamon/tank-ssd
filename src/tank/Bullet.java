package tank;

import obstacle.Obstacle;

public class Bullet {


    private int x;
    private int y;

    private int dx;
    private int dy;



    public Bullet(int x, int y,int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void reset() {
        x = y = -99;
    }

    public void move() {
        x += dx ;
        y += dy ;
    }

    public void refreshState(int x, int y, int dx, int dy){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public boolean hit(Tank tank) {
        return x == tank.getX() && y == tank.getY();
    }
    public boolean hitObstacle(Obstacle o) {

        return (x == o.getX() && y == o.getY()) || (x-dx == o.getX() && y-dy == o.getY());
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
