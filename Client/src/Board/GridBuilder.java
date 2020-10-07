package Board;

import Client.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


/**
 * @author Matt Jones
 * @version 1
 *
 * Grid builder builds the grid from grid abstract class.
 * Main method in here is the getTile which gets user pointer
 * to find and set ships for setup
 */
public class GridBuilder extends Grid {
    private final GameController gameController;
    public boolean isPlayer;
    public MouseListener setShipListener;
    private Point firstPoint = new Point(0, 0);
    private Point secondNextPoint = new Point(0, 0);
    private Point thirdNextPoint = new Point(0, 0);
    private JPanel thePanel = new JPanel();
    private JPanel secondNextCell;
    private JPanel thirdNextCell;

    public GridBuilder(GameController gameController, boolean isPlayer) {
        super(); // builds grid here
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

    /**
     * @return returns each tile for the grid to be built in grid
     *
     * getTile makes every tile for both grids.
     * The listener in here listens for the pointer and determines
     * how to set a ship from that.
     */
    @Override
    protected JPanel getTile() {
        JPanel tile = new JPanel();
        tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tile.setPreferredSize(new Dimension(25, 25));
        tile.setBackground(Color.BLUE);
        this.setShipListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

//                }

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
                getComp2(secondNextPoint);

//              //fixme get ship type from drop down menu before
                gameController.getPlayer().addShip(coordinates, gameController.getGui().currentShipType); // Create new ship object

                // should color after
                if (gameController.getPlayer().getCord(x, y).isPartOfShip && gameController.getPlayer().getCord(x2, y2).isPartOfShip && gameController.getPlayer().getCord(x3, y3).isPartOfShip) {
                    tile.setBackground(Color.yellow);
                    secondNextCell.setBackground(Color.yellow);
                    thirdNextCell.setBackground(Color.yellow);
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
        tile.addMouseListener(setShipListener);
        return tile;
    }
}