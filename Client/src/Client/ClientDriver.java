package Client;

import ProblemDomain.Message;

import java.io.*;
import java.net.Socket;

public class ClientDriver implements Runnable {
    Socket server;
    String ip;
    int port;
    String username;
    ObjectOutputStream outputToServer;
    ObjectInputStream inputFromServer;
    ClientGUI gui;
    private boolean notConnected = true;

    public ClientDriver(String ip, int port, String username, ObjectInputStream inputFromServer, ObjectOutputStream outputToServer) {
        this.username = username;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        while (!this.server.isClosed()) {
            try {
                this.server = new Socket(this.ip, this.port);
                System.out.println(server.getLocalPort());
                this.outputToServer = new ObjectOutputStream(server.getOutputStream());
                inputFromServer = new ObjectInputStream(server.getInputStream());

                System.out.println("2");

                System.out.println("3");
                receiveMessages();
                notConnected = false;
                System.out.println("connected ?");
            } catch (IOException e) {
                System.out.println("Client could not find server");
                e.printStackTrace();
            }

        }
    }


    public void sendMessageToServer(Message message) {
        try {
            this.outputToServer.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            gui.addClientMessage(new Message(this.username, "Network error, could not send message"));
        }
    }

    public void receiveMessages() {
        while (!this.server.isClosed()) {
            try {
                Message receive = (Message) inputFromServer.readObject();
                this.gui.addClientMessage(receive);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            notConnected = true;
            sendMessageToServer(new Message(this.username, "Disconnected"));
            gui.addClientMessage(new Message(this.username, "Disconnected"));
            this.inputFromServer.close();
            this.outputToServer.close();
            this.server.close();
            System.out.println(this.username + " is disconnected");
        } catch (IOException e) {
            e.printStackTrace();
            gui.addClientMessage(new Message(this.username, "Unable to disconnect"));
        }

    }


}

