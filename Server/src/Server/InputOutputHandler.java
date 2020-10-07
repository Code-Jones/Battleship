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
        boolean turn = true;
        while (!this.input.getSocket().isClosed() && !this.output.getSocket().isClosed()) {
            try {
                // receives generic object, then checks instanceof to find object type
                Object object = this.input.getObjectInput().readObject();
                if (object instanceof Message) {
                    Message message = (Message) object;
                    // sets who's turn it is
                    //todo could be improved / this is super quick fix / change to have admin attribute so you can print username -> turn
                    if (message.getUsername().equals("Admin") && message.getMessage().equals("is my turn?")) {
                        if (turn) {
                            turn = false;
                            Message temp = new Message("Admin", "false");
                            this.output.getObjectOutput().writeObject(temp);
//                            this.output.getObjectOutput().writeObject(new Message("Admin", "false"));
//                            this.output.getObjectOutput().flush(); // <- added this
                        } else {
                            turn = true;
                            Message temp = new Message("Admin", "true");
                            this.output.getObjectOutput().writeObject(temp);
//                            this.output.getObjectOutput().writeObject(new Message("Admin", "true"));
//                            this.output.getObjectOutput().flush(); // <- added this
                        }
                    }
                    ServerGUI.addServerMessage(message);
                    this.output.getObjectOutput().writeObject(message);
//                    this.output.getObjectOutput().flush(); // <- added this
                } else if (object instanceof Ship) {
                    System.out.println("ship here");
					Ship ship = (Ship) object;
					ServerGUI.addServerMessage(new Message("test", "Received ship" + ship.getShipType()));
					this.output.getObjectOutput().writeObject(ship);
//                    this.output.getObjectOutput().flush(); // <- added this
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("sent something, not message");
                e.printStackTrace();
            }
        }
    }

}
