package commandBullet;

import tank.Bullet;
import tank.Tank;

import java.util.List;

public class CommandBullet {

    private Bullet bullet;
    private int tick;

    public  void execute(Tank tank, List<Bullet> bullets){
        int dx,dy;
        if (tank.getCurrentState().getState() ==  "West"){dx = -1; dy = 0;}
        else  if (tank.getCurrentState().getState() ==  "South"){dx = 0; dy = 1;}
        else  if (tank.getCurrentState().getState() ==  "East"){dx = 1; dy = 0;}
        else{dx = 0; dy = -1;}

        bullets.add(tank.getBulletPool().requestBullet(tank.getX()+dx, tank.getY()+dy, dx,dy));
    }

    public int getTick() {
        return tick;
    }

    public CommandBullet(Bullet bullet, int tick) {
        this.tick = tick;
        this.bullet = bullet;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
