package com.battleship.client;

import com.battleship.problemdomain.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerHandler implements Runnable {
	private ClientGUI gui;
	private Socket server;
	private ObjectInputStream ois;
	
	public ServerHandler(ClientGUI gui, Socket server, ObjectInputStream ois) {
		this.gui = gui;
		this.server = server;
		this.ois = ois;
	}

	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Message receive = (Message) this.ois.readObject();
				this.gui.addMessage(receive.toString());
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
