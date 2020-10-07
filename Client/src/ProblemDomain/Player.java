package ProblemDomain;

import Client.GameController;

import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 1
 *
 * Player class which holds information about the current player
 * and it's opponent. This class holds the global grid which has
 * the coordinates that relate to this users game board. Fleet is
 * a collection of ships so it is easy to collect and transfer.
 */

public class Player {

    public boolean isPlayer;
    public boolean isTurn;
    public int numOfShipsSunk = 0;
    public ArrayList<Ship> fleet = new ArrayList<>();
    public ArrayList<Coordinate> globalGrid;
    public GameController gameController;

    /**
     * @param isPlayer is this our player or opponent
     * @param globalGridPlayer coordinate grid that relates to game board
     * @param gameController controller that handles gameplay
     */
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

    public void playerTurn() {

    }

    /**
     * This add ship function is important to adding ships from the coordinates
     * the users is clicking on at the moment. Also determines the ship type of the ship.
     * When all 5 are built, they are sent to the server to be sent to opponent.
     *
     * @param coordinates where the user is clicking / relate to grid
     * @param shipType type of ship being built
     */
    public void addShip(ArrayList<Coordinate> coordinates, Ship.ShipType shipType) {
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
                fleet.add(new Ship(temp, shipType));
                for (Coordinate cord : temp) {
                    cord.setPartOfShip(true, shipType);
                }
            }
            // for testing
            System.out.println("Fleet size : " + fleet.size());
        }

        // removes option after being built
        switch (String.valueOf(shipType)) {
            case "AirCraft":
                gameController.getGui().removeBoatFromComboBox(Ship.ShipType.AirCraft);
                break;
            case "Battleship":
                gameController.getGui().removeBoatFromComboBox(Ship.ShipType.Battleship);
                break;
            case "Cruiser":
                gameController.getGui().removeBoatFromComboBox(Ship.ShipType.Cruiser);
                break;
            case "Destroyer":
                gameController.getGui().removeBoatFromComboBox(Ship.ShipType.Destroyer);
                break;
            case "Submarine":
                gameController.getGui().removeBoatFromComboBox(Ship.ShipType.Submarine);
                break;
        }
        // sends to server and starts next game state
        if (fleet.size() == 5) {
            gameController.setGameState(GameController.GameState.PLAYING);
            sendFleetToServer(fleet);
        }
    }
    // just cleans the code a bit in main function
    public void sendFleetToServer(ArrayList<Ship> fleet) {
        for (Ship ship : fleet) {
            gameController.getGui().sendShip(ship);
            System.out.println("\n" + ship.toString());
        }
    }

    public Coordinate getCord(int x, int y) {
        for (Coordinate coordinate : globalGrid) {
            if (coordinate.x == x && coordinate.y == y) {
                return coordinate;
            }
        } return null;
    }

    /**
     * Gets input coordinates from the users pointer, to match with the
     * global grid coordinates
     *
     * @param input user clicked coordinates
     * @param globalList this players global grid
     * @return coordinates matching
     */
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

    // mostly for testing
    public void getShipData(Ship ship) {
        for (Coordinate coordinate : ship.coordinates) {
            System.out.println(ship.shipType + " " + coordinate.toString());
        }
    }

    // to clean up main functions
    public void addToFleet(Ship ship) {
        fleet.add(ship);
        if (fleet.size() == 5) {
            populateOpponentBoard();
        }
    }

    /**
     * populate the opponents board so that when a user clicks
     * i know if it's a hit or not. Doesn't show anything on gui
     */
    private void populateOpponentBoard() {
        //wow so efficient !
        for (Coordinate coordinate : globalGrid) {
            for (Ship ship : fleet) {
                for (Coordinate shipCoordinate : ship.getCoordinates()) {
                    if (coordinate.isCoordinate(shipCoordinate)) {
//                        System.out.println(shipCoordinate.toString() + "clearly working ? ");
                        coordinate.setPartOfShip(true, ship.shipType); // fixme stopped right here
                    }
                }

            }
        }
        // todo for testing
        System.out.println(fleet.get(0).toString());
    }


}
