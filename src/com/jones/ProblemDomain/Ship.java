package com.jones.ProblemDomain;

import com.jones.Board.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {
    ArrayList<Coordinate> coordinates;
    public enum ShipType {
        Battleship,
        Cruiser,
        Aircraft,
        Destroyer,
        Submarine
    }

    @Override
    public String toString() {
        return "Ship{" +
                "coordinates=" + coordinates +
                ", shipType=" + shipType +
                '}';
    }

    ShipType shipType;

    public Ship(ArrayList<Coordinate> coordinates, ShipType shipType) {
        this.coordinates = coordinates;
        this.shipType = shipType;
    }

    public void setShipCoordinates(ArrayList<Coordinate> coordinates, String shipType) {
        for (Coordinate coordinate : coordinates) {
            coordinate.setPartOfShip(true);
            ShipType.valueOf(shipType);
        }
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
