package Client;

import Board.Coordinate;
import ProblemDomain.Player;
import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 1
 *
 * Main game controller that handles the connection for players,
 * gui, and server. Also controls game state to make game work in stages
 */
public class GameController {
    public ArrayList<Coordinate> globalGridPlayer;
    public ArrayList<Coordinate> globalGridOpponent;
    Player player;
    Player opponent;
    ClientGUI gui;
    GameState currentGameState;



    public enum GameState {
        SETUP,
        PLAYING,
        FINISH
    }

    public GameController() {

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
//        GameState.
//
//    }

    private void setUp() {
        this.gui = new ClientGUI("Battleship", this);

        this.currentGameState = GameState.SETUP;
        System.out.println("Setup Started");
    }
    public void setGameState(GameState gameState) {
        this.currentGameState = gameState;
        System.out.println("Game state changed to : " + gameState);
    }

    public void


    public Player getPlayer() {
        return this.player;
    }

    public ClientGUI getGui() {
        return gui;
    }

    public Player getPlayer2Data() {
        return opponent;
    }


}
