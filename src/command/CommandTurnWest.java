package command;

import tank.Tank;

public class CommandTurnWest extends Command{
    public CommandTurnWest(Tank tank, int tick) {
        super(tank, tick);
    }

    @Override
    public void execute() {
        this.getTank().turnWest();
    }
}
