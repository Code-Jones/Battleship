package com.battleship.problemdomain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientConnection {
	private final Socket socket;
	private final ObjectInputStream objectInputStream;
	private final ObjectOutputStream objectOutputStream;
	
	public ClientConnection(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		this.socket = socket;
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}
}
