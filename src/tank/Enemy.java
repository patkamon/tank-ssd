package tank;


public class Enemy extends Tank {

    private boolean isDead;
    public Enemy(int x, int y, String imgName, int boardx, int boardy) {
        super(x, y, imgName, boardx, boardy);
        isDead = false;
    }


    public boolean isDead() {
        return isDead;
    }



    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public void reset() {
        super.reset();
        isDead = false;
    }
}


