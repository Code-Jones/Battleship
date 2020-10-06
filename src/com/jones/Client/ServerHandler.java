package com.jones.Client;

import com.jones.ProblemDoimain.Message;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable {
	private final ClientGUI gui;
	private final Socket server;
	private final ObjectInputStream inputStream;
	private final ObjectOutputStream outputStream;

	public ServerHandler(ClientGUI gui, Socket server, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
		this.gui = gui;
		this.server = server;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Message receive = (Message) this.inputStream.readObject();
				this.gui.addClientMessage(receive);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
