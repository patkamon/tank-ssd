package commandBullet;

import tank.Bullet;
import tank.Tank;

import java.util.List;

public class CommandBullet {

    private Bullet bullet;
    private int tick;
    private Tank tank;
    private int dx,dy;

    public  void execute(List<Bullet> bullets){
        bullets.add(tank.getBulletPool().requestBullet(tank.getX()+dx, tank.getY()+dy, dx,dy));
    }

    public int getTick() {
        return tick;
    }

    public CommandBullet(Tank tank, int tick) {
        this.tank = tank;
        if ( tank.getCurrentState().getState() ==  "West"){dx = -1; dy = 0;}
        else  if ( tank.getCurrentState().getState() ==  "South"){dx = 0; dy = 1;}
        else  if ( tank.getCurrentState().getState() ==  "East"){dx = 1; dy = 0;}
        else{dx = 0; dy = -1;}
        this.tick = tick;
        this.bullet = tank.getBulletPool().requestBullet(tank.getX(), tank.getY(), dx,dy);
    }

}
