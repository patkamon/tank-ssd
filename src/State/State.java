package State;

import command.Command;
import tank.Tank;

import java.awt.*;

public abstract class State {

    public abstract String getState();

    public abstract Image getImg(String imgName);

    public abstract void turnWest(Tank stateOwner);
    public abstract void turnEast(Tank stateOwner);

    public abstract Command turnNorth(Tank stateOwner, int tick);
    public abstract Command turnSouth(Tank stateOwner, int tick);


}
