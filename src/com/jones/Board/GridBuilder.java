package com.jones.Board;

import com.jones.Client.GameController;
import com.jones.ProblemDoimain.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GridBuilder extends Grid {
    private final GameController gameController;
    public boolean isPlayer;
    private Point firstPoint = new Point(0, 0);
    private Point secondNextPoint = new Point(0, 0);
    private Point thirdNextPoint = new Point(0, 0);
    private JPanel thePanel = new JPanel();
    private JPanel secondNextCell;
    private JPanel thirdNextCell;


    public GridBuilder(GameController gameController, boolean isPlayer) {
        super();
        this.gameController = gameController;
        this.isPlayer = isPlayer;
    }

    public void getJpanel(Point newPoint) {
        thePanel = this.getComponentAt(newPoint);
    }

    public void getComp2(Point newPoint) {
        secondNextCell = this.getComponentAt(newPoint);
    }

    public void getComp3(Point newPoint) {
        thirdNextCell = this.getComponentAt(newPoint);
    }

    @Override
    protected JPanel getCell() {

        JPanel firstCell = new JPanel();
        firstCell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        firstCell.setPreferredSize(new Dimension(25, 25));
        firstCell.setBackground(Color.BLUE);
//        firstCell.setBackground(this.isPlayer ? Color.BLUE : Color.RED); // fix this later?

        firstCell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                firstPoint = firstCell.getLocation();
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
                coordinates.add(new Coordinate(x, y));
                coordinates.add(new Coordinate(x2, y2));
                coordinates.add(new Coordinate(x3, y3));

                getComp2(secondNextPoint);
                getComp3(thirdNextPoint);

                firstCell.setBackground(Color.yellow);
                secondNextCell.setBackground(Color.yellow);
                thirdNextCell.setBackground(Color.yellow);


//                //fixme pls
                if (isPlayer) {
                    gameController.getPlayer().addShip(coordinates, Ship.ShipType.Submarine); // Create new ship object
//                    draw();
                }
//                else { // do i even need this
//                    gameController.getPlayer2Data().addShip(coordinates); // Create new ship object
//                    draw();
//                }

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
        });
        return firstCell;
    }

    public void draw() {


        int[][] temp = null;
        if (isPlayer) {
            temp = gameController.getPlayer().getSelfData();
        } else {
            temp = gameController.getPlayer2Data().getSelfData();
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (temp[i][j] == 1) {
                    int x = numberToPanel(i);
                    int y = numberToPanel(j);

                    Point p = new Point(x, y);
                    getJpanel(p);
                    thePanel.setBackground(Color.CYAN);
                }
                if (temp[i][j] == 0) {
                    int x = numberToPanel(i);
                    int y = numberToPanel(j);
                    System.out.println("\ninside black " + x + "      " + y);

                    Point p = new Point(Math.abs(x), Math.abs(y));
                    getJpanel(p);

                    thePanel.setBackground(Color.BLACK);

                }
            }
        }
    }

    public int numberToPanel(int s) {
        int temp = (s - 1) * 20;
        return temp;
    }

}