package command;

import tank.Tank;

public class CommandTurnEast extends Command{
    public CommandTurnEast(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        this.getTank().turnEast();
    }
}
