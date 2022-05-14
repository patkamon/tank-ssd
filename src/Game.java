import obstacle.Obstacle;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame{

    private int boardSizeX = 13;
    private int boardSizeY = 13;
    private GridUI gridUI;

    private World world;

    private int score;

    private Thread thread;
    private long delayed = 1000/3;

    public Game() {
        setup();
        gridUI = new GridUI();
        add(gridUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        world = new World(boardSizeX,boardSizeY);
    }

    public void setup(){
        // setup for starting game
        score = 0;
    }



    public void start(){
        setVisible(true);
        thread = new Thread(){
            @Override
            public void run(){
                    repaint();
                    waitFor(delayed);
                }
        };
        thread.start();


    }


    private void waitFor(long delayed){
        try{
            Thread.sleep(delayed);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 50;

        private Image grass;


        public GridUI() {
            setPreferredSize(new Dimension(boardSizeX * CELL_PIXEL_SIZE, boardSizeY * CELL_PIXEL_SIZE));
            grass = new ImageIcon("img/grass.jpeg").getImage();
        }


        @Override
        public void paint(Graphics g) {
            super.paint(g);


            g.setColor(Color.black);
            g.fillRect(0, 0, boardSizeX*CELL_PIXEL_SIZE, boardSizeY*CELL_PIXEL_SIZE);

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



    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }


}
