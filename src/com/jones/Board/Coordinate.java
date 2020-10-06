package com.jones.Board;

import java.util.ArrayList;

public class Coordinate {
    public int x;
    public int y;
    public boolean isPartOfShip;
    boolean hit;

    @Override
    public String toString() {
        return "Coordinate{" +
                "x =" + x +
                ", y= " + y +
                ", isPartOfShip= " + isPartOfShip +
                ", hit= " + hit +
                '}';
    }

    // for making new ships
    public Coordinate(int x, int y, boolean isPartOfShip) {
        this.x = x;
        this.y = y;
        this.isPartOfShip = true;
    }

    // for making default coordinates
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.isPartOfShip = false;
    }

    public boolean isCoordinate(Coordinate coordinate) {
        return coordinate.getX() == this.x && coordinate.getY() == this.y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public void setPartOfShip(boolean partOfShip) {
        isPartOfShip = partOfShip;
    }

    public boolean isEdge(Coordinate coordinate) {
        return coordinate.x == 1 || coordinate.x == 10 || coordinate.y == 1 || coordinate.y == 10;
    }

}
