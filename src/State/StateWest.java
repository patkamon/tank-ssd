package State;

import command.Command;
import command.CommandTurnEast;
import command.CommandTurnWest;
import tank.Tank;

import javax.swing.*;
import java.awt.*;

public class StateWest extends State{
    @Override
    public Image getImg(String imgName) {
        return new ImageIcon("img/"+imgName +"_left.png").getImage();
    }

    @Override
    public void turnWest(Tank stateOwner) {
        stateOwner.setCurrentState(new StateSouth());
    }

    @Override
    public void turnEast(Tank stateOwner) {
        stateOwner.setCurrentState(new StateNorth());
    }

    @Override
    public Command turnNorth(Tank stateOwner, int tick) {
        return new CommandTurnWest(stateOwner, tick);
    }

    @Override
    public Command turnSouth(Tank stateOwner, int tick) {
        return new CommandTurnEast(stateOwner, tick);
    }
}
