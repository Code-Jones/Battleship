package com.jones.Server;

import com.jones.ProblemDomain.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGUI {
    private final JFrame frame;
    public static DefaultListModel<String> listModel;

    public ServerGUI() {
        this.frame = new JFrame("Server Terminal");
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                super.windowClosing(event);
                close();
            }
        });
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(450, 700);
        this.frame.add(this.createTerminalPanel(), BorderLayout.CENTER);
    }

    private JPanel createTerminalPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        JList<String> terminalList = new JList<>(listModel);
        terminalList.setBackground(Color.black);
        terminalList.setForeground(Color.WHITE);
        listModel.addElement("Server working");
        JScrollPane scrollPane = new JScrollPane(terminalList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

//    public void addServerMessage(Message message) {
//        this.listModel.addElement(message.toString());
//    }
    public static void addServerMessage(Message message) {
        listModel.addElement(message.toString());
    }

//    public static void addMessage(String message) {listModel.addElement(message);}

    protected void display() {
        this.frame.setVisible(true);
    }
    private void close() {
        // todo check for open clients and save data
        System.exit(0);
    }
}
