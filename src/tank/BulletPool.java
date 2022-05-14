package tank;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<Bullet>();
    public BulletPool(){
        int size = 5;
        for (int i = 0; i < size; i++){
            bullets.add(new Bullet(-999, -999, 0, 0));
        }
    }
    public Bullet requestBullet(int x, int y, int dx, int dy){
        try {
            Bullet bullet = bullets.remove(0);
            bullet.refreshState(x, y, dx, dy);
            return bullet;
        }
        catch (Exception e){
            bullets.add(new Bullet(-999, -999, 0, 0));
            Bullet bullet = bullets.remove(0);
            bullet.refreshState(x, y, dx, dy);
            return bullet;
        }
    }

}
