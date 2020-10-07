package Board;

import ProblemDomain.Ship;

import java.io.Serializable;

/**
 * @author Matt Jones
 * @version 1
 *
 * Coordinate class which holds coordinates which relate to the game boards.
 * Each ship is built of coordinates
 */

public class Coordinate implements Serializable {
    public int x;
    public int y;
    public boolean isPartOfShip;
    boolean hit;
    Ship.ShipType shipType;

    // for making default coordinates
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.isPartOfShip = false;
        this.shipType = null;
    }
    // for populating game board
    public Coordinate(int x, int y, Ship.ShipType shipType) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.isPartOfShip = false;
        this.shipType = shipType;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x =" + x +
                ", y= " + y +
                ", isPartOfShip= " + isPartOfShip +
                ", hit= " + hit +
                '}';
    }

    public boolean isCoordinate(Coordinate coordinate) {
        return coordinate.getX() == this.x && coordinate.getY() == this.y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isPartOfShip() {
        return isPartOfShip;
    }

    public void setPartOfShip(boolean partOfShip, Ship.ShipType shipType) {
        isPartOfShip = partOfShip;
        this.shipType = shipType;
    }

    public boolean isEdge(Coordinate coordinate) {
        return coordinate.x == 1 || coordinate.x == 10 || coordinate.y == 1 || coordinate.y == 10;
    }

}
