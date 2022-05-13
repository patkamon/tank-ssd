package command;

import tank.Tank;

public class CommandStop extends Command{
    public CommandStop(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        this.getTank().stop();
    }
}
