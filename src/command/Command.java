package command;

import tank.Tank;

 public abstract class Command {
    private Tank tank;
    private int tick;

    public abstract void execute();

    public int getTick() {
        return tick;
    }

    public Command(Tank tank, int tick) {
        this.tick = tick;
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }
}