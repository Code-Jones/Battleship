package com.jones.ProblemDoimain;

import com.jones.Board.Coordinate;

import java.util.ArrayList;

public class Player {
    public boolean isPlayer;
    private int[][] playerTable;
    private int[][] opponentTable;
    private int numOfShipsSunk = 0;
    private ArrayList<Ship> fleet = new ArrayList<>();
    public ArrayList<Coordinate> globalGrid;

    public Player(boolean isPlayer, ArrayList<Coordinate> globalGridPlayer) {
        this.isPlayer = isPlayer;
        this.globalGrid = globalGridPlayer;

        for (int i = 1; i < 11 ; i++) {
            for (int j = 1; j < 11; j++) {
                globalGrid.add(new Coordinate(i, j));
            }
        }
    }

    public boolean addShip(ArrayList<Coordinate> coordinates, Ship.ShipType type) {
        boolean goodToGo = true;

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
                        break; // fixme break
                    }
                }
            }
        }
        System.out.println(goodToGo);
        if (shipsLeft() < 5 && goodToGo) {
            // probably could be more efficient
            ArrayList<Coordinate> temp = getCoordinateFromLists(coordinates, globalGrid);
            fleet.add(new Ship(temp, type));
            for (Coordinate cord : temp) {
                cord.setPartOfShip(true);
            }
        }
        // for testing

        for (Ship ship : fleet) {
            getShipData(ship);
        }
        return goodToGo;
//            setSelfData(coordinates);
    }


//    public void setSelfData(ArrayList<Coordinate> coordinates){
//        //fixme idk what he was thinking with this
//    }

    public int shipsLeft(){
        int temp = fleet.size();
        return temp;
    }
    // i need to get the input coordinates and match them with the globalList
    // than mark those as setPartOfShip and input them into another list? to be
    // feed into new Ship()

    //should work ?!
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

    public boolean isEdge(Coordinate coordinate) {
        return coordinate.x == 1 || coordinate.x == 10 || coordinate.y == 1 || coordinate.y == 10;
    }

    public void getShipData(Ship ship) {
        for (Coordinate coordinate : ship.coordinates) {
            System.out.println(ship.shipType + " " + coordinate.toString());

        }
    }
    public int[][] getSelfData() {

        return null;
    }
}
