package command;

import tank.Tank;

public class CommandTurnLeft extends Command{
    public CommandTurnLeft(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        getTank().getCurrentState().turnWest(getTank());
    }
}
