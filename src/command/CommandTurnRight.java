package command;

import tank.Tank;

public class CommandTurnRight extends Command{
    public CommandTurnRight(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        getTank().getCurrentState().turnEast(getTank());
    }
}
