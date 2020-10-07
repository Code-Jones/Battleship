package Client;

import Board.Coordinate;
import ProblemDomain.Player;

import java.util.ArrayList;

public class GameController {
    public ArrayList<Coordinate> globalGridPlayer;
    public ArrayList<Coordinate> globalGridOpponent;
    Player player;
    Player opponent;
    ClientGUI gui;
    public enum gameState {
        SETUP,
        PLAYING,
        FINISH
    }

    public GameController() {
//        this.gui = new ClientGUI("Battleship", this);
        this.globalGridPlayer = new ArrayList<>();
        this.globalGridOpponent = new ArrayList<>();
        this.player = new Player(true, globalGridPlayer, this);
        this.opponent = new Player(false, globalGridOpponent, this);
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.setUp();

    }
//    public void progressGameState() {
//        gameState.
//
//    }

    private void setUp() {
        this.gui = new ClientGUI("Battleship", this);
        System.out.println("setup");
    }

    public Player getPlayer() {
        return this.player;
    }

    public ClientGUI getGui() {
        return gui;
    }

    public Player getPlayer2Data() {
        return opponent;
    }

    public void boatsSet() {
        //todo make this to set state to play move
        System.out.println("should start playing now");
    }
}
