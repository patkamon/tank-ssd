import command.*;
import commandBullet.CommandBullet;
import obstacle.Obstacle;
import tank.Bullet;
import tank.Enemy;
import tank.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game extends JFrame implements Observer {

    private int boardSizeX = 13;
    private int boardSizeY = 13;
    private GridUI gridUI;
    private Gui gui;
    private boolean isSinglePlayer;

    private World world;

    private List<Command> commandList = new ArrayList<Command>();
    private List<CommandBullet> commandBulletList = new ArrayList<CommandBullet>();

    public Game() {
        super();

        setLayout(new BorderLayout());

        gui = new Gui();
        add(gui, BorderLayout.SOUTH);
        world = new World(boardSizeX,boardSizeY,true);
        gridUI = new GridUI();
        add(gridUI, BorderLayout.CENTER);

        setSize(boardSizeX*50, boardSizeY*55);


        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        gridUI.repaint();
        gui.updateTick(world.getTick());

        for(Command c: commandList){
            if (world.getTick() == c.getTick()){
                c.execute();
            }
        }
        for(CommandBullet cb: commandBulletList){
            if (world.getTick() == cb.getTick()){
                cb.execute(world.getBullets() );
                }
        }
        if (world.isWining() && isSinglePlayer){
            gui.showGameWiningLabel();
            gui.enableReplayButton();
            return;
        }
        else if(world.isGameOver()) {
            gui.showGameOverLabel();
            gui.enableReplayButton();
            return;
        }

        for(Enemy e: world.getEnemy()){
            if (!e.isDead()) {
                if (e.getY() < world.getPlayer()[0].getY()) {
                    commandList.add(new CommandTurnSouth(e, world.getTick() + 1));
                } else if (e.getY() > world.getPlayer()[0].getY()) {
                    commandList.add(new CommandTurnNorth(e, world.getTick() + 1));
                } else if (e.getX() < world.getPlayer()[0].getX()) {
                    commandList.add(new CommandTurnEast(e, world.getTick() + 1));
                } else if (e.getX() > world.getPlayer()[0].getX()) {
                    commandList.add(new CommandTurnWest(e, world.getTick() + 1));
                }
                if (e.getX() == world.getPlayer()[0].getX() || e.getY() == world.getPlayer()[0].getY()) {
                    CommandBullet cb = new CommandBullet(e, world.getTick() + 1);
                    commandBulletList.add(cb);
                    cb.execute(world.geteBullets());
                }
            }
        }
    }

    class Gui extends JPanel {

        private JLabel tickLabel;
        private JButton singleButton;
        private JButton twoButton;
        private JButton replayButton;
        private JLabel gameOverLabel;
        private JLabel gameWiningLabel;

        public Gui() {
            setLayout(new FlowLayout());
            tickLabel = new JLabel("Tick: 0");
            add(tickLabel);


            singleButton = new JButton("Single-Player");
            singleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    singleButton.setEnabled(false);
                    twoButton.setEnabled(false);
                    Game.this.requestFocus();
                    isSinglePlayer = true;


                    Game.this.addKeyListener(new Controller());
                    Game.this.world = new World(boardSizeX,boardSizeY,isSinglePlayer);
                    Game.this.world.addObserver(Game.this);
                    Game.this.world.start();
                }
            });
            add(singleButton);

            twoButton = new JButton("Two-Player");
            twoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    singleButton.setEnabled(false);
                    twoButton.setEnabled(false);
                    Game.this.requestFocus();
                    isSinglePlayer = false;

                    Game.this.addKeyListener(new Controller());
                    Game.this.world = new World(boardSizeX,boardSizeY,isSinglePlayer);
                    Game.this.world.addObserver(Game.this);
                    Game.this.world.start();

                }
            });
            add(twoButton);


            replayButton = new JButton("Replay");
            replayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    world.start();
                    replayButton.disable();

                }
            });
            replayButton.setEnabled(false);
            add(replayButton);
            gameOverLabel = new JLabel("GAME OVER");
            gameOverLabel.setForeground(Color.red);
            gameOverLabel.setVisible(false);
            add(gameOverLabel);

            gameWiningLabel = new JLabel("You Win");
            gameWiningLabel.setForeground(Color.red);
            gameWiningLabel.setVisible(false);
            add(gameWiningLabel);
        }


        public void updateTick(int tick) {
            tickLabel.setText("Tick: " + tick);
        }

        public void showGameOverLabel() {
            gameOverLabel.setVisible(true);
        }
        public void showGameWiningLabel() {
            gameWiningLabel.setVisible(true);
        }

        public void enableReplayButton() {
            replayButton.setEnabled(true);
        }
    }


    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;


        public GridUI() {
            setPreferredSize(new Dimension(boardSizeX * CELL_PIXEL_SIZE, boardSizeY * CELL_PIXEL_SIZE));
            setDoubleBuffered(true);
        }


        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.black);
            g.fillRect(0, 0, boardSizeX*CELL_PIXEL_SIZE, boardSizeY*CELL_PIXEL_SIZE);

            paintPlayer(g);
            paintEnemy(g);
            paintBullet(g);
            paintTree(g);
            paintSteel(g);
            paintBrick(g);

            g.setColor(Color.lightGray);
            for(int i = 0; i < boardSizeX; i++) {
                g.drawLine(i * CELL_PIXEL_SIZE, 0, i * CELL_PIXEL_SIZE, boardSizeY*CELL_PIXEL_SIZE);
            }
            for(int i = 0; i < boardSizeY; i++) {
                g.drawLine(0, i * CELL_PIXEL_SIZE, boardSizeX*CELL_PIXEL_SIZE, i * CELL_PIXEL_SIZE);
            }
        }

        private void paintTree(Graphics g) {
            Obstacle[] listTree = world.getTree();
            for(int i = 0; i < listTree.length; i++) {
                int x = listTree[i].getX()*CELL_PIXEL_SIZE;
                int y = listTree[i].getY()*CELL_PIXEL_SIZE;
                g.drawImage(listTree[i].getImage(),x ,y ,CELL_PIXEL_SIZE,CELL_PIXEL_SIZE,Color.black,null);
            }

        }

        private void paintBullet(Graphics g){
            // Draw bullets
            g.setColor(Color.green);
            for(Bullet bullet : world.getBullets()) {
                g.fillOval((bullet.getX()*CELL_PIXEL_SIZE)+20, (bullet.getY()*CELL_PIXEL_SIZE)+20, 10, 10);
            }
            g.setColor(Color.red);
            for(Bullet bullet : world.geteBullets()) {
                g.fillOval((bullet.getX()*CELL_PIXEL_SIZE)+20, (bullet.getY()*CELL_PIXEL_SIZE)+20, 10, 10);
            }

        }
        private void paintSteel(Graphics g) {
            Obstacle[] listSteel = world.getSteel();
            for (int i = 0; i < listSteel.length; i++) {
                int x = listSteel[i].getX() * CELL_PIXEL_SIZE;
                int y = listSteel[i].getY() * CELL_PIXEL_SIZE;
                g.drawImage(listSteel[i].getImage(), x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, Color.black, null);
            }
        }
        private void paintBrick(Graphics g) {
            Obstacle[] listBrick = world.getBrick();
            for (int i = 0; i < listBrick.length; i++) {
                int x = listBrick[i].getX() * CELL_PIXEL_SIZE;
                int y = listBrick[i].getY() * CELL_PIXEL_SIZE;
                g.drawImage(listBrick[i].getImage(), x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, Color.black, null);
            }
        }

        private void paintPlayer(Graphics g) {
            Tank[] players = world.getPlayer();
            for(int i = 0; i < players.length; i++) {
                int x = players[i].getX()*CELL_PIXEL_SIZE;
                int y = players[i].getY()*CELL_PIXEL_SIZE;
                g.drawImage(players[i].getImage(),x-6 ,y-6 ,CELL_PIXEL_SIZE+12,CELL_PIXEL_SIZE+12,null,null);
            }
        }

        private void paintEnemy(Graphics g) {
            Tank[] enemy = world.getEnemy();
            for(int i = 0; i < enemy.length; i++) {
                int x = enemy[i].getX()*CELL_PIXEL_SIZE;
                int y = enemy[i].getY()*CELL_PIXEL_SIZE;
                g.drawImage(enemy[i].getImage(),x-6 ,y-6 ,CELL_PIXEL_SIZE+12,CELL_PIXEL_SIZE+12,null,null);
            }
        }


    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(world.isSinglePlayer()){
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    Command c = world.getPlayer()[0].getCurrentState().turnNorth(world.getPlayer()[0],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Command c = world.getPlayer()[0].getCurrentState().turnSouth(world.getPlayer()[0],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Command c = new CommandTurnRight(world.getPlayer()[0], world.getTick());
                    commandList.add(c);
                    c.execute();
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    Command c = new CommandTurnLeft(world.getPlayer()[0], world.getTick());
                    commandList.add(c);
                    c.execute();
                }
                else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    CommandBullet cb = new CommandBullet(world.getPlayer()[0],world.getTick());
                    commandBulletList.add(cb);
                    cb.execute(world.getBullets() );
                }
            }else{
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    Command c = world.getPlayer()[1].getCurrentState().turnNorth(world.getPlayer()[1],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Command c = world.getPlayer()[1].getCurrentState().turnSouth(world.getPlayer()[1],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Command c = new CommandTurnRight(world.getPlayer()[1], world.getTick());
                    commandList.add(c);
                    c.execute();
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    Command c = new CommandTurnLeft(world.getPlayer()[1], world.getTick());
                    commandList.add(c);
                    c.execute();
                }else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    CommandBullet cb = new CommandBullet(world.getPlayer()[1],world.getTick());
                    commandBulletList.add(cb);
                    cb.execute(world.geteBullets());
                }

                if(e.getKeyCode() == KeyEvent.VK_W) {
                    Command c = world.getPlayer()[0].getCurrentState().turnNorth(world.getPlayer()[0],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_S) {
                    Command c = world.getPlayer()[0].getCurrentState().turnSouth(world.getPlayer()[0],world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_D) {
                    Command c = new CommandTurnRight(world.getPlayer()[0], world.getTick());
                    commandList.add(c);
                    c.execute();
                }else if(e.getKeyCode() == KeyEvent.VK_A) {
                    Command c = new CommandTurnLeft(world.getPlayer()[0], world.getTick());
                    commandList.add(c);
                    c.execute();
                } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    CommandBullet cb = new CommandBullet(world.getPlayer()[0],world.getTick());
                    commandBulletList.add(cb);
                    cb.execute(world.getBullets() );
                }
            }
        }

        }

    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }


}
