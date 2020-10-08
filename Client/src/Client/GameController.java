package Client;

import ProblemDomain.Coordinate;
import ProblemDomain.Player;

import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 1
 * <p>
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

    public GameController() {
        this.currentGameState = GameState.SETUP;
        this.globalGridPlayer = new ArrayList<>();
        this.globalGridOpponent = new ArrayList<>();
        this.player = new Player(true, globalGridPlayer, this);
        this.opponent = new Player(false, globalGridOpponent, this);
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.setUp();
    }

    private void setUp() {
        this.gui = new ClientGUI("Battleship", this);
        System.out.println("Setup Started");
    }

    public GameState getGameState() {
        return currentGameState;
    }

    public void setGameState(GameState gameState) {
        this.currentGameState = gameState;
        System.out.println("Game state changed to : " + gameState);
    }

    public Player getPlayer() {
        return this.player;
    }

    public ClientGUI getGui() {
        return gui;
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public enum GameState {
        SETUP,
        PLAYING,
        FINISH
    }
}
