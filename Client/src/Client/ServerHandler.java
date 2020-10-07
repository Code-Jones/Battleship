package Client;

import ProblemDomain.Coordinate;
import ProblemDomain.Message;
import ProblemDomain.Ship;
import Exception.ObjectNotRecognized;

import java.io.*;
import java.net.Socket;
/**
 * @author Matt Jones
 * @version 2
 *
 * Freshly stolen from Nick Hamnett because i didn't want to rewrite it and modified
 * Connection class that handles a client connection with the server.
 * Handles the input object streams and receives objects.
 * Puts into generic object class and checks instance of to determine actions needed.
 */

public class ServerHandler implements Runnable {
	private final ClientGUI gui;
	private final Socket server;
	private final ObjectInputStream inputStream;

	public ServerHandler(ClientGUI gui, Socket server, ObjectInputStream inputStream) {
		this.gui = gui;
		this.server = server;
		this.inputStream = inputStream;
	}

	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Object object = this.inputStream.readObject();
				if (object instanceof Message) {
					Message receive = (Message) object;
					if (receive.getUsername().equals("Admin"))
						if (receive.getMessage().equals("true")) {
							gui.gameController.player.isTurn = true;
							gui.gameController.opponent.isTurn = false;
							System.out.println("is my turn");
						} else {
							gui.gameController.player.isTurn = false;
							gui.gameController.opponent.isTurn = true;
							System.out.println("not my turn");
						}
					this.gui.addClientMessage(receive);
				} else if (object instanceof Ship) {
					Ship ship = (Ship) object;
					this.gui.gameController.opponent.addToFleet(ship);
					System.out.println(this.gui.gameController.opponent.fleet.size());
				} else if (object instanceof Coordinate) {
					Coordinate cord = (Coordinate) object;
					this.gui.gameController.player.sendMissile(cord);
					System.out.println(this.gui.gameController.opponent.fleet.size());
				} else {
					throw new ObjectNotRecognized("Received Object not recognized");
				}
			} catch (ClassNotFoundException | IOException | ObjectNotRecognized e) {
				e.printStackTrace();
			}
		}
	}

}
