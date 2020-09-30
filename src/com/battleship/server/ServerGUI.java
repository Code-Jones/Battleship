package com.battleship.server;

import com.battleship.problemdomain.Message;

import javax.swing.*;
import java.awt.*;

public class ServerGUI {
    private final JFrame frame;
    private DefaultListModel<String> listModel;

    public ServerGUI() {
        this.frame = new JFrame("Server Terminal");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(400, 600);
        this.frame.add(this.createTerminalPanel(), BorderLayout.CENTER);
    }

    private JPanel createTerminalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        this.listModel = new DefaultListModel<>();
        JList<String> terminalList = new JList<>(this.listModel);
        terminalList.setBackground(Color.black);
        terminalList.setForeground(Color.WHITE);
        this.listModel.addElement("Server Gui built");

        JScrollPane scrollPane = new JScrollPane(terminalList);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public void addMessage(Message message) {
        this.listModel.addElement(message.toString());
    }


    protected void display() {
        this.frame.setVisible(true);
    }
}
