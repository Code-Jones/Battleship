package Board;

import Client.GameController;
import ProblemDomain.Coordinate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


/**
 * @author Matt Jones
 * @version 1
 * Grid builder builds the grid from grid abstract class.
 * Main method in here is the getTile which gets user pointer
 * to find and set ships for setup
 */
public class GridBuilder extends Grid {
    private final GameController gameController;
    public boolean isPlayer;
    public MouseListener setShipListener;
    public MouseListener setGamePlayListener;
    public ChangeListener makeMissileListener;
    private Point firstPoint = new Point(0, 0);
    private Point secondNextPoint = new Point(0, 0);
    private Point thirdNextPoint = new Point(0, 0);
    private JPanel thePanel = new JPanel();
    private JPanel secondNextCell;
    private JPanel thirdNextCell;
    private JPanel tile;


    public GridBuilder(GameController gameController, boolean isPlayer) {
        super(isPlayer); // builds grid here
        this.gameController = gameController;
        this.isPlayer = isPlayer;
    }

    public JPanel getJpanel(Point newPoint) {
        return thePanel = this.getComponentAt(newPoint);
    }

    public JPanel getComp2(Point newPoint) {
        return secondNextCell = this.getComponentAt(newPoint);
    }

    public void getComp3(Point newPoint) {
        thirdNextCell = this.getComponentAt(newPoint);
    }

    // listeners
    public void makeSetShipListener(JPanel tile) {
        this.setShipListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(gameController.getGameState());
                if (gameController.getGameState().equals(GameController.GameState.SETUP)) {

                    firstPoint = tile.getLocation();
                    double xPos = (firstPoint.getX() / 25 + 1);
                    int x = (int) xPos;
                    double yPos = (firstPoint.getY() / 25 + 1);
                    int y = (int) yPos;
//                if (isSettingShipMode) { // todo add this later if it works
//                                                might have to add 2 more for 5 spots

                    double xPos2 = (firstPoint.getX() / 25 + 2);
                    int x2 = (int) xPos2;
                    double yPos2 = (firstPoint.getY() / 25 + 1);
                    int y2 = (int) yPos2;


                    double xPos3 = (firstPoint.getX() / 25 + 3);
                    int x3 = (int) xPos3;
                    double yPos3 = (firstPoint.getY() / 25 + 1);
                    int y3 = (int) yPos3;

                    // fixme to add diverse ships/ rn it's only 3 square ships
//                        use shipType / already set up sorta
                    System.out.println("Location (X: " + x + " Y: " + y + ")");
                    System.out.println("Location (X: " + x2 + " Y: " + y2 + ")");
                    System.out.println("Location (X: " + x3 + " Y: " + y3 + ")");

                    secondNextPoint = new Point((int) (firstPoint.getX() + 25), (int) (firstPoint.getY()));
                    thirdNextPoint = new Point((int) (firstPoint.getX() + 50), (int) (firstPoint.getY()));

                    ArrayList<Coordinate> coordinates = new ArrayList<>();
                    coordinates.add(gameController.getPlayer().getCord(x, y));
                    coordinates.add(gameController.getPlayer().getCord(x2, y2));
                    coordinates.add(gameController.getPlayer().getCord(x3, y3));

                    getComp2(secondNextPoint);
                    getComp3(thirdNextPoint);

//              //fixme get ship type from drop down menu before
                    gameController.getPlayer().addShip(coordinates, gameController.getGui().currentShipType); // Create new ship object

                    // should color after
                    if (gameController.getPlayer().getCord(x, y).isPartOfShip && gameController.getPlayer().getCord(x2, y2).isPartOfShip && gameController.getPlayer().getCord(x3, y3).isPartOfShip) {
                        tile.setBackground(Color.yellow);
                        secondNextCell.setBackground(Color.yellow);
                        thirdNextCell.setBackground(Color.yellow);
                    }
                } else {
                    tile.setEnabled(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public void makePlayingListener(JPanel tile) {
        this.setGamePlayListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameController.getGameState().equals(GameController.GameState.PLAYING)) {
                    // clicks at point
                    Point point = tile.getLocation();
                    // turn into x / y the grid knows
                    int x = (int) (point.getX() / 25 + 1);
                    int y = (int) (point.getY() / 25 + 1);

                    //
                    JPanel cell = getComponentAt(point);
                    Coordinate cord = gameController.getOpponent().getCord(x, y);

                    if (cord.isPartOfShip()) {
                        System.out.println(cord.toString() + " is a hit");
                        gameController.getOpponent().getCord(x, y).setHit(true);
                        cell.setBackground(Color.yellow);
                    } else {
                        System.out.println(cord.toString() + " is a miss");
                        cell.setBackground(Color.GRAY);

                    }
                    cell.setEnabled(false);
                    cell.removeMouseListener(this);// fixme might need to get rid of this
                    gameController.getGui().sendCord(gameController.getOpponent().getCord(x, y));

                } else {
                    tile.setEnabled(false);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public void makeMissileListener(JPanel tile) {
        this.makeMissileListener = evt -> {
            System.out.println("event tostring : " + evt.toString());
            System.out.println("event source : " + evt.getSource());
            // clicks at point
            Point point = tile.getLocation();
            // turn into x / y the grid knows
            int x = (int) (point.getX() / 25 + 1);
            int y = (int) (point.getY() / 25 + 1);

            JPanel panelAtThisTile = getComponentAt(point);
            Coordinate playerCordOfThisTile = gameController.getPlayer().getCord(x, y);

            if (playerCordOfThisTile.isHit()) {
                System.out.println(" this is it");
                panelAtThisTile.setBackground(Color.red);
            }
        };
    }
    // can't get this to work
//    public void makeMissileListener(JPanel tile) {
//        this.makeMissileListener = evt -> {
//
//            if (gameController.getGameState().equals(GameController.GameState.PLAYING)) {
//                do {
//                    System.out.println("event property : " + evt.getPropertyName());
//                    System.out.println("event source : " + evt.getSource());
//                    System.out.println("event new value : " + evt.getNewValue());
//                    Point pnt = (Point) evt.getSource();
//                    int x = (int) (pnt.getX() / 25 + 1);
//                    int y = (int) (pnt.getY() / 25 + 1);
//                    JPanel cell = (JPanel) getComponentAt(x, y); // change
//                    if (gameController.getPlayer().getCord(x, y).isHit()) {
//                        System.out.println(" this is it");
//                        cell.setBackground(Color.red);
//                    }
//                    if (gameController.getGameState().equals(GameController.GameState.FINISH) {
//                        break;
//                    }
//                } while (true);
//            }
//        };
//    }

//    public void makeMissileListener(JPanel tile) {
//        // here's a property change listener nick
//        // i didn't even use a lambda expression so you can read it
//        this.makeMissileListener = new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//
//                if (evt.getPropertyName()) {
//                    System.out.println("this is state -<");
//                }
//                 i need the coordinate behind the panel
//                System.out.println(evt.getSource());
//                JPanel panel = (JPanel) evt.getSource();
//                    Point pnt = (Point) evt.getSource();
//                int x = (int) (pnt.getX() / 25 + 1);
//                int y = (int) (pnt.getY() / 25 + 1);
//                JPanel cell = getComponentAt();
//                if (gameController.getPlayer().getCord(x, y).isHit()) {
//                     get cord from players grid.
//                    Coordinate cord = gameController.getPlayer().getCord(x, y);
//                    if (cord.isHit()) {
//                        cell.setBackground(Color.RED);
//                    }
//                }
//            }
//        };
//    }

    // not working / do the shitty way
    /**
     * @return tile template for
     * <p>
     * makeSetUpTile makes tile template for both grids.
     * The listener in here listens for the pointer and determines
     * how to set a ship from that.
     */
//    public JPanel makeSetUpTile(boolean isPlayer) {
//        this.tile = new JPanel();
//        tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
//        tile.setPreferredSize(new Dimension(25, 25));
//
//        if (isPlayer) {
//            tile.setBackground(Color.BLUE);
//            makeSetShipListener(tile);
//            tile.addMouseListener(this.setShipListener);
//        } else {
//            tile.setEnabled(false);
//            tile.setBackground(Color.red);
//        }
//        return tile;
//    }

    /**
     * @return returns each tile for the grid to be built in grid
     */
    @Override
    protected JPanel getTile(boolean isPlayer) {
        this.tile = new JPanel();
        tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tile.setPreferredSize(new Dimension(25, 25));

        if (isPlayer) {
            tile.setBackground(Color.BLUE);
            makeSetShipListener(tile);
            tile.addMouseListener(this.setShipListener);
        } else {
            makePlayingListener(tile);
            tile.addMouseListener(this.setGamePlayListener);
            tile.setBackground(Color.red);
        }
        return this.tile;
    }
}