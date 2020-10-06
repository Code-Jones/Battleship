package com.jones.Client;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jones.Board.GridBuilder;
import com.jones.ProblemDoimain.Message;
import com.jones.ProblemDoimain.Ship;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientGUI {
    private final JFrame frame;
    private final String username;
    private final String ip;
    private final int port;
    public DefaultListModel<Message> chatListModel;
    public ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;
    JList<Message> chatList;
    MouseListener playModeListener;
    MouseListener setShipsListener;
    boolean isHorizontal;
    JPanel playerBoard;
    JPanel opponentBoard;
    MouseListener setPointerListener;
    GameController gameController;

    public ClientGUI(String title, GameController gameController) {

        //networks stuff
//        this.username = JOptionPane.showInputDialog("Enter your username");
        this.username = "matt";
        this.ip = "localhost";
        this.port = 1234;

        // frame stuff
        this.frame = new JFrame("Battleship");
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        this.frame.setTitle(title);
        this.frame.setSize(900, 500);

        //temp
        this.gameController = gameController;
        this.isHorizontal = true;
        setListeners();
        buildGUI();
        connectToServer();
    }

    // gui stuff
    private void setListeners() {
        // all the listeners for the game
        this.playModeListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getComponent().getBackground() != Color.YELLOW)
                    e.getComponent().setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getComponent().getBackground() != Color.YELLOW)
                    e.getComponent().setBackground(Color.BLUE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                e.getComponent().setBackground(Color.YELLOW);


//                if (isHorizontal) {
//                    for (int i = x; i < (x + 5) ; i++) {
//                        e.getComponent().setBackground(Color.RED);
//                    }
//                }
            }
        };

//        this.setPointerListener = new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                System.out.println(e.getPoint().toString());
//            }
//        };

        this.setShipsListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                e.getComponent().setBackground(Color.YELLOW);
//                String[] cord = e.getSource().toString().split(" ");
//                int x = Integer.parseInt(cord[0]);
//                int y = Integer.parseInt(cord[1]);
//
//
//                System.out.println("x: " + x + " y: " + y);

//                addToSetShipList(x, y);
            }
        };

    }


    private void buildGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));

        JPanel chatPanel = this.createChatPanel();
        mainPanel.add(chatPanel, BorderLayout.EAST);

        JPanel topPanel = this.createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = this.createPlayBoardPanel();
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel setPanel = this.createSetPanel();
        mainPanel.add(setPanel, BorderLayout.WEST);

        this.frame.add(mainPanel);
        this.frame.setVisible(true);
    }

    private JPanel createSetPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 2, 20, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        JLabel placeLabel = new JLabel();
        placeLabel.setFont(new Font("Source Code Pro", -1, 16));
        placeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeLabel.setText("Ship Ledger");
        panel.add(placeLabel, BorderLayout.NORTH);
        JButton setButton = new JButton();
        setButton.setText("Set");
        panel.add(setButton, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel4, BorderLayout.CENTER);
        JComboBox<String> comboBox1 = new JComboBox<>();
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("Battleship");
        defaultComboBoxModel1.addElement("AirCraft");
        defaultComboBoxModel1.addElement("Cruiser");
        defaultComboBoxModel1.addElement("Submarine");
        defaultComboBoxModel1.addElement("Destroyer");
        comboBox1.setModel(defaultComboBoxModel1);
        panel4.add(comboBox1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        return panel;
    }

    private JPanel createPlayBoardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel emptyNorthPanel = new JPanel(new BorderLayout());
        emptyNorthPanel.setPreferredSize(new Dimension(50, 75));
        JPanel emptySouthPanel = new JPanel(new BorderLayout());
        emptySouthPanel.setPreferredSize(new Dimension(50, 75));
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));


        playerBoard = new GridBuilder(gameController, true);
        opponentBoard = new GridBuilder(gameController, false); // fix to enemiesGrid

        // I FUCKING HATE SWING
        JPanel p = (JPanel) opponentBoard.getComponent(0);
        Component[] ss = p.getComponents();
        ArrayList<JPanel> test = new ArrayList<>();
        for (Component s : ss) {
            test.add((JPanel) s);
        }
        for (JPanel tile : test) {
            for (MouseListener al : tile.getMouseListeners()) {
                tile.removeMouseListener(al);
            }
            tile.setEnabled(false);
            tile.setBackground(Color.red);
        }


        panel.add(emptyNorthPanel, BorderLayout.NORTH);
        panel.add(emptySouthPanel, BorderLayout.SOUTH);
        panel.add(playerBoard, BorderLayout.WEST);
        panel.add(opponentBoard, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Source code Pro", -1, 36));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setText("Battleship");
        panel.add(label1, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        chatListModel = new DefaultListModel<>();
        chatList = new JList<>(chatListModel);
        JScrollPane scrollPane = new JScrollPane(chatList);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel inputButtonPanel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
        inputButtonPanel.add(textField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener((ActionEvent evt) -> {
            Message send = new Message(this.username, textField.getText());
            sendMessage(send);
            textField.setText("");
        });
        inputButtonPanel.add(sendButton, BorderLayout.EAST);
        panel.add(inputButtonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void addClientMessage(Message message) {
        this.chatListModel.addElement(message);
    }

    // server stuff
    public void sendMessage(Message message) {
        try {
            this.outputStream.writeObject(message);
            addClientMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            this.addClientMessage(new Message(this.username, "Message could not send"));
        }
    }

    public void sendShip(Ship ship) {
        try {
            this.outputStream.writeObject(ship);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket(ip, port);
            this.addClientMessage(new Message(this.username, "Connected"));

            OutputStream outStream = socket.getOutputStream();
            outputStream = new ObjectOutputStream(outStream);
            InputStream inStream = socket.getInputStream();
            inputStream = new ObjectInputStream(inStream);

            ServerHandler serverHandler = new ServerHandler(this, socket, inputStream, outputStream);
            Thread thread = new Thread(serverHandler);

            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
            this.addClientMessage(new Message(this.username, "Unable to connect"));
        }
    }
}
