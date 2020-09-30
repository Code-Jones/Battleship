package com.battleship.server;

import com.battleship.problemdomain.ClientConnection;
import com.battleship.problemdomain.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerDriver {
    private final ServerSocket listener;
    private ArrayList<ClientConnection> connections;
    private final ServerGUI serverGUI;

    ServerDriver() throws IOException {
        this.listener = new ServerSocket(1234);
        this.connections = new ArrayList<>();
        this.serverGUI = new ServerGUI();
        serverGUI.display();
        init();
    }

    public void init() throws IOException {
        serverGUI.addMessage(new Message("Server","Waiting for connection port 1234..."));
        this.connections = new ArrayList<>();


        while (listener.isBound()) {
            try {
                Socket client = listener.accept();
                serverGUI.addMessage(new Message("Server","Client connected."));

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
