package com.jones.Client;

import com.jones.ProblemDomain.Message;
import com.jones.ProblemDomain.Ship;
import com.jones.Server.ServerGUI;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable {
	private final ClientGUI gui;
	private final Socket server;
	private final ObjectInputStream inputStream;

	public ServerHandler(ClientGUI gui, Socket server, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
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
					Message receive = (Message) this.inputStream.readObject();
					this.gui.addClientMessage(receive);
				} else if (object instanceof Ship) {
					Ship ship = (Ship) object;
					this.gui.gameController.opponent.addToFleet(ship);
					System.out.println(this.gui.gameController.opponent.fleet.size());
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
