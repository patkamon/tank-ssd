package command;

import tank.Tank;

public class CommandTurnNorth extends Command{
    public CommandTurnNorth(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        this.getTank().turnNorth();
    }
}
