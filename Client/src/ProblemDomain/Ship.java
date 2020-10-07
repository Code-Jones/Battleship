package ProblemDomain;

import Board.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 1
 *
 * Ship class holds information such as coordinates, size and ship type
 * Sent over server to populate the boards of opponent
 */

public class Ship implements Serializable {
    int shipSize;
    ArrayList<Coordinate> coordinates;
    ShipType shipType;
    public enum ShipType {
        Battleship,
        Cruiser,
        AirCraft,
        Destroyer,
        Submarine
    }
    // old
    public Ship(ArrayList<Coordinate> coordinates, ShipType shipType) {
        this.coordinates = coordinates;
        this.shipType = shipType;
    }
    // new
    public Ship(ArrayList<Coordinate> coordinates, ShipType shipType, int shipSize) {
        this.coordinates = coordinates;
        this.shipType = shipType;
        this.shipSize = shipSize;
    }

    public void setShipCoordinates(ArrayList<Coordinate> coordinates, String shipType) {
        for (Coordinate coordinate : coordinates) {
            coordinate.setPartOfShip(true, ShipType.valueOf(shipType));
            ShipType.valueOf(shipType);
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "coordinates=" + coordinates +
                ", shipType=" + shipType +
                '}';
    }

    public boolean isShipHit(Coordinate hit) {
        for (Coordinate coordinate : this.coordinates) {
            if (coordinate.isCoordinate(hit)) {
                hit.setHit(true);
                return true;
            }
        }
        return false;
    }

    public boolean isShipSunk() {
        for (Coordinate coordinate : this.coordinates) {
            if(!coordinate.isHit()) {
                return false;
            }
        }
        return true;
    }

    public void printShipCoordinates() {
        System.out.println(this.coordinates.toString());
    }

    public boolean isShip(Ship ship) {
        for (int i = 0; i < ship.coordinates.size(); i++) {
            if (!ship.coordinates.get(i).isCoordinate(this.coordinates.get(i))){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }
}
