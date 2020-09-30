package com.battleship.problemdomain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientConnection {
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
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
