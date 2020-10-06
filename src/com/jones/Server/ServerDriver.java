package com.jones.Server;

import com.jones.ProblemDoimain.ClientConnection;
import com.jones.ProblemDoimain.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerDriver {
//    TODO main shortlist
//      - make game boards
//      - make placing function
//      - make cord class
//      - send players move
//      - set boards
//      - make win conditions
//      - improve ui
//      - make profile class to transfer identity
//      - make sure i've followed rules
//      - make jar files

    public static void main(String[] args) throws IOException {
        ServerGUI gui = new ServerGUI();
        gui.display();
        ServerSocket listener = new ServerSocket(1234);
        ArrayList<ClientConnection> workerList = new ArrayList<>();

        while (listener.isBound()) {
            try {
                ServerGUI.addServerMessage(new Message("Server","Ready to accept client"));
                Socket client = listener.accept();

                InputStream inputStream = client.getInputStream();
                ObjectInputStream objectInput = new ObjectInputStream(inputStream);

                OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

//                ObjectInputStream objectInput = new ObjectInputStream(new ObjectInputStream(client.getInputStream()));
//                ObjectOutputStream objectOutput = new ObjectOutputStream(new ObjectOutputStream(client.getOutputStream()));

                ClientConnection connection = new ClientConnection(client, objectInput, objectOutput);
                workerList.add(connection);
                ServerGUI.addServerMessage(new Message("Server","Client connected on: " + client.getPort()));


                if (workerList.size() % 2 == 0) {
                    ClientConnection connection1 = workerList.get(0);
                    ClientConnection connection2 = workerList.get(1);

                    ServerGUI.addServerMessage(new Message("Server","New match connected"));

                    ClientHandler clientHandler = new ClientHandler(connection1, connection2);
                    Thread thread = new Thread(clientHandler);

                    thread.start();

                    workerList.remove(connection1);
                    workerList.remove(connection2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listener.close();
    }

//        public ArrayList<ServerWorker> getWorkerList () {
//            return workerList;
//        }
//
//        public void removeWorker (ServerWorker serverWorker){
//            this.workerList.remove(serverWorker);
//        }

    //    private boolean disconnect() {
//        try {
//            server.removeWorker(this);
//            ArrayList<ServerWorker> workerList = server.getWorkerList();
//            for (ServerWorker worker : workerList) {
//                // shouldn't need this if i remove this worker from list
////            if (!worker.username.equals(this.username))
//                worker.sendMessage(worker.clientsUsername + " is offline.");
//            }
//            socket.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    public void updateActive() throws IOException {
//        ArrayList<ServerWorker> workerList = server.getWorkerList();
//        for (ServerWorker worker : workerList) {
//            sendMessage(worker.clientsUsername + " is online.");
//        }
//    }
}
