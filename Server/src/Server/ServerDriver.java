package Server;

import Gui.ServerGUI;
import ProblemDomain.ClientConnection;
import ProblemDomain.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 2
 *
 * Mostly freshly stolen from Nick Hamnett because I didn't want to rewrite it
 * Starts the server gui and the server socket. When a connection is made, it
 * is converted into a client connection. When 2 connections are made, they pair
 * together and start a single thread with both 2 connections.
 */

public class ServerDriver {
    ServerSocket listener;
    ServerGUI gui;

    public ServerDriver(ServerSocket listener, ServerGUI gui) {
        this.listener = listener;
        this.gui = gui;
    }

    public static void main(String[] args) throws IOException {

        ServerGUI gui = new ServerGUI();
        gui.display();

        ServerSocket listener = new ServerSocket(1234);
        ServerDriver serverDriver = new ServerDriver(listener, gui);
        ArrayList<ClientConnection> workerList = new ArrayList<>();

        while (listener.isBound()) {
            try {
                ServerGUI.addServerMessage(new Message("Server", "Ready to accept client"));
                Socket client = listener.accept();

                InputStream inputStream = client.getInputStream();
                ObjectInputStream objectInput = new ObjectInputStream(inputStream);

                OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

                ClientConnection connection = new ClientConnection(client, objectInput, objectOutput);
                workerList.add(connection);
                ServerGUI.addServerMessage(new Message("Server", "Client connected on: " + client.getPort()));


                if (workerList.size() % 2 == 0) {
                    ClientConnection connection1 = workerList.get(0);
                    ClientConnection connection2 = workerList.get(1);

                    ServerGUI.addServerMessage(new Message("Server", "New match connected"));

                    ClientHandler clientHandler = new ClientHandler(connection1, connection2);
                    Thread thread = new Thread(clientHandler);

                    thread.start();

                    workerList.remove(connection1);
                    workerList.remove(connection2);
                }
            } catch (IOException e) {
                serverDriver.disconnect(workerList);
                e.printStackTrace();
            }
        }
        listener.close();
    }


    public void disconnect(ArrayList<ClientConnection> workerList) {
        try {
            workerList.removeAll(workerList);
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void updateActive() throws IOException {
//        ArrayList<ServerWorker> workerList = listener.getWorkerList();
//        for (ServerWorker worker : workerList) {
//            sendMessage(worker.clientsUsername + " is online.");
//        }
//    }
}
