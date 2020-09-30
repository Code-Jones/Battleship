package com.battleship.server;

import com.battleship.problemdomain.ClientConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerDriver {

    public static void main(String[] args) throws IOException {
        ServerGUI serverGUI = new ServerGUI();
        serverGUI.display();
        ServerSocket listener = new ServerSocket(1234);
        serverGUI.addMessage("Waiting for connection port 1234...");
        ArrayList<ClientConnection> connections = new ArrayList<>();

        while (listener.isBound()) {
            try {
                Socket client = listener.accept();
                serverGUI.addMessage("Client connected.");

                InputStream inputStream = client.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                ClientConnection connection = new ClientConnection(client, objectInputStream, objectOutputStream);
                connections.add(connection);

                if (connections.size() % 2 == 0) {
                    ClientConnection connection1 = connections.get(0);
                    ClientConnection connection2 = connections.get(1);

                    // Spin up a thread to handle connections
                    ClientHandler clientHandler = new ClientHandler(connection1, connection2);
                    Thread thread = new Thread(clientHandler);

                    thread.start();

                    // Remove connections array list
                    connections.remove(connection1);
                    connections.remove(connection2);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        listener.close();
    }
}
