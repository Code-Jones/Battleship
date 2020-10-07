package Server;

import Gui.ServerGUI;
import ProblemDomain.ClientConnection;
import ProblemDomain.Message;
import ProblemDomain.Ship;

import java.io.IOException;
/**
 * @author Matt Jones
 * @version 2
 *
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it
 * Handles two client connection's input and output object streams
 * and on a single thread. Receives object and then checks what instanceof
 * the object is, then sends it to the other connection.
 */

public class InputOutputHandler implements Runnable {
    private final ClientConnection input;
    private final ClientConnection output;

    public InputOutputHandler(ClientConnection input, ClientConnection output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
            try {
                // receives generic object, then checks instanceof to find object type
                Object object = this.input.getObjectInput().readObject();
                if (object instanceof Message) {
                    Message message = (Message) object;
                    ServerGUI.addServerMessage(message);
                    this.output.getObjectOutput().writeObject(message);
                } else if (object instanceof Ship) {
                    System.out.println("ship here");
					Ship ship = (Ship) object;
					ServerGUI.addServerMessage(new Message("test", "Received ship" + ship.getShipType()));
					this.output.getObjectOutput().writeObject(ship);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("sent something, not message");
                e.printStackTrace();
            }
        }
    }

}
