package com.jones.ProblemDoimain;

import com.jones.Board.Coordinate;
import com.jones.Client.GameController;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    public boolean isPlayer;
    public int numOfShipsSunk = 0;
    private ArrayList<Ship> fleet = new ArrayList<>();
    public ArrayList<Coordinate> globalGrid;
    public GameController gameController;

    public Player(boolean isPlayer, ArrayList<Coordinate> globalGridPlayer, GameController gameController) {
        this.isPlayer = isPlayer;
        this.globalGrid = globalGridPlayer;
        this.gameController = gameController;
        for (int i = 1; i < 11 ; i++) {
            for (int j = 1; j < 11; j++) {
                globalGrid.add(new Coordinate(i, j));
            }
        }
    }

    public void addShip(ArrayList<Coordinate> coordinates, Ship.ShipType type) {
        boolean goodToGo = true;

        if (fleet.size() < 5) {
            for (Coordinate coordinate : coordinates) {
                for (Coordinate globalCord : globalGrid) {
                    if (globalCord.isCoordinate(coordinate)) { // match globalGrid to coordinate given
                        if (globalCord.isEdge(coordinate)) { // check if edge grid and ship can fit
                            // fixme only horizontal rn
                            if (coordinate.x >= 8 || coordinate.y >= 8) { // todo change this to size of boat later / 8 bc 3 spaces
                                goodToGo = false;
                                System.out.println("Ship is too close to the edge");
                            }
                        }
                        if (globalCord.isPartOfShip) {  // checks if space is taken by other ships
                            goodToGo = false;
                            System.out.println("Ship takes space already taken");
                        }
                    }
                }
            }

            // for testing
            System.out.println("Ship is made : " + goodToGo);

            if (goodToGo) {
                // probably could be more efficient
                ArrayList<Coordinate> temp = getCoordinateFromLists(coordinates, globalGrid);
                fleet.add(new Ship(temp, type));
                for (Coordinate cord : temp) {
                    cord.setPartOfShip(true);
                }
            }
            // for testing
            System.out.println("Fleet size : " + fleet.size());
        }
        if (fleet.size() == 5) {
            sendFleetToServer(fleet);
            gameController.boatsSet();
        }
    }

    public void sendFleetToServer(ArrayList<Ship> fleet) {
        for (Ship ship : fleet) {
            gameController.getGui().sendShip(ship);
            System.out.println("\n\n" + Arrays.toString(ship.coordinates.toArray()));
        }
    }


    public Coordinate getCord(int x, int y) {
        for (Coordinate coordinate : globalGrid) {
            if (coordinate.x == x && coordinate.y == y) {
                return coordinate;
            }
        } return null;
    }

    // i need to get the input coordinates and match them with the globalList
    // than mark those as setPartOfShip and input them into another list? to be
    // feed into new Ship()
    public ArrayList<Coordinate> getCoordinateFromLists(ArrayList<Coordinate> input, ArrayList<Coordinate> globalList) {
        ArrayList<Coordinate> temp = new ArrayList<>();
        for (Coordinate inputCord : input) {
            for (Coordinate globalCord : globalList) {
                if (globalCord.isCoordinate(inputCord)) {
                    temp.add(globalCord);
                }
            }
        }
        return temp;
    }

    public void getShipData(Ship ship) {
        for (Coordinate coordinate : ship.coordinates) {
            System.out.println(ship.shipType + " " + coordinate.toString());
        }
    }

}
