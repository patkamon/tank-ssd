package tank;

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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void refreshState(int x, int y, int dx, int dy){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
