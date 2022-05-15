package command;

import tank.Tank;

public class CommandTurnSouth extends Command{
    public CommandTurnSouth(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        this.getTank().turnSouth();
    }
}
