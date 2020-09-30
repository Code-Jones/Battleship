package com.battleship.server;

import com.battleship.problemdomain.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ServerGUI {
    private final JFrame frame;
    private DefaultListModel listModel;
    private JList terminalList;

    public ServerGUI() {
        this.frame = new JFrame("Server Terminal");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(300, 400);
        this.frame.add(this.createTerminalPanel(), BorderLayout.CENTER);
    }



    private JPanel createTerminalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        this.listModel = new DefaultListModel();
        this.terminalList = new JList(this.listModel);
        JScrollPane scrollPane = new JScrollPane(this.terminalList);
        panel.add(scrollPane, BorderLayout.CENTER);


            // Get the text to send from the text field.
            String text = textField.getText();

            // Create a Message object.
            Message send = new Message(this.username, text);

            try {
                // Send Message to the server.
                this.objectOutputStream.writeObject(send);

                // If it's sent successfully, clear the text field.
                textField.setText("");

                // If it's sent successfully, add message to chat list.
                this.addMessage(send.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                this.addMessage("Unable to send message.");
            }



        });

        inputButtonPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputButtonPanel, BorderLayout.SOUTH);

        return panel;
}
