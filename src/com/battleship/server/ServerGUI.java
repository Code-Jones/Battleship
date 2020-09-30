package com.battleship.server;

import com.battleship.problemdomain.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGUI {
    private final JFrame frame;
    public DefaultListModel<String> listModel;
    private ServerDriver serverDriver;

    public ServerGUI() {
        this.frame = new JFrame("Server Terminal");
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                close();
            }
        });
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
    private void close() {
        // todo check for open clients and save data
        System.exit(0);
    }
}
