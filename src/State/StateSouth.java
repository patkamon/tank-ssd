package State;

import command.Command;
import command.CommandTurnNorth;
import command.CommandTurnSouth;
import tank.Tank;

import javax.swing.*;
import java.awt.*;

public class StateSouth extends State {
    @Override
    public String getState() {
        return "South";
    }

    @Override
    public Image getImg(String imgName) {
        return new ImageIcon("img/"+ imgName +"_bottom.png").getImage();
    }

    @Override
    public void turnWest(Tank stateOwner) {
        stateOwner.setCurrentState(new StateEast());
    }

    @Override
    public void turnEast(Tank stateOwner) {
        stateOwner.setCurrentState(new StateWest());
    }

    @Override
    public Command turnNorth(Tank stateOwner, int tick) {
        return new CommandTurnSouth(stateOwner, tick);
    }

    @Override
    public Command turnSouth(Tank stateOwner, int tick) {
        return new CommandTurnNorth(stateOwner, tick);
    }
}
