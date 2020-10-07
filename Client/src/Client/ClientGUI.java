package Client;

import Board.GridBuilder;
import ProblemDomain.Coordinate;
import ProblemDomain.Message;
import ProblemDomain.Ship;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Matt Jones
 * @version 1
 *
 * Client Gui that bridges all interaction with the user
 * and the rest of the application. Client gui also starts
 * the connection to the server and hands it over to a
 * server handler to deal with.
 */
public class ClientGUI {
    private final JFrame frame;
    private final String username;
    private final String ip;
    private final int port;
    public DefaultListModel<Message> chatListModel;
    public ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;
    public Ship.ShipType currentShipType;
    JList<Message> chatList;
    MouseListener playModeListener;
    MouseListener setShipsListener;
    boolean isHorizontal;
    JPanel playerBoard;
    JPanel opponentBoard;
    GameController gameController;
    JComboBox<String> comboBox;

    //constructor
    public ClientGUI(String title, GameController gameController) {

        //networks stuff
        // fixme change this before submit
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
        this.frame.setResizable(false);

        //extra
        this.currentShipType = Ship.ShipType.Battleship;
        this.gameController = gameController;
        this.isHorizontal = true;
        buildGUI();
        connectToServer();
    }
    // gui stuff

    /**
     * Calls the rest of the panels to be built individually
     */
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
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

        panel.add(panel4, BorderLayout.CENTER);

        comboBox = new JComboBox<>();
        final DefaultComboBoxModel<String> defaultComboBoxModel1;
        defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("Battleship");
        defaultComboBoxModel1.addElement("AirCraft");
        defaultComboBoxModel1.addElement("Cruiser");
        defaultComboBoxModel1.addElement("Submarine");
        defaultComboBoxModel1.addElement("Destroyer");
        comboBox.setModel(defaultComboBoxModel1);
        panel4.add(comboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        // doesn't work on JComboBox
//        comboBox.addPropertyChangeListener(evt -> {
//            String str = String.valueOf(comboBox.getSelectedItem());
//            System.out.println(comboBox.getSelectedItem());
//            if (gameController.getPlayer().fleet.size() != 5) {
//                this.currentShipType = Ship.ShipType.valueOf(str);
//                System.out.println(this.currentShipType);
//            } else {
//                comboBox.setEnabled(false);
//                panel.setVisible(false);
//            }
//        });
        // literally the same thing
        comboBox.addActionListener(e -> {
            String str = String.valueOf(comboBox.getSelectedItem());
            System.out.println(comboBox.getItemCount());
            if (gameController.getPlayer().fleet.size() != 5) {
                this.currentShipType = Ship.ShipType.valueOf(str);
                System.out.println(this.currentShipType);
            } else {
                comboBox.setEnabled(false);
                panel.setVisible(false);
            }
        });

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

        playerBoard = new GridBuilder(gameController, true);
        opponentBoard = new GridBuilder(gameController, false);

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

    public void removeBoatFromComboBox(Ship.ShipType shipType) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(String.valueOf(shipType))) {
                System.out.println(comboBox.getItemAt(i) + " removed");
                comboBox.removeItem(comboBox.getItemAt(i));
            }
        }
    }

    public JPanel getPanelFromCord(int x, int y) {
        return (JPanel) this.playerBoard.getComponentAt(x, y);
    }

    // server stuff
    public void sendMessage(Message message) {
        try {
            this.outputStream.writeObject(message);
            this.outputStream.flush(); // <- added this
            addClientMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            this.addClientMessage(new Message(this.username, "Message could not send"));
        }
    }

    /**
     * Starts connection with server and lets server handler deal with it
     */
    public void connectToServer() {
        try {
            Socket socket = new Socket(ip, port);
            this.addClientMessage(new Message(this.username, "Connected"));

            OutputStream outStream = socket.getOutputStream();
            outputStream = new ObjectOutputStream(outStream);
            InputStream inStream = socket.getInputStream();
            inputStream = new ObjectInputStream(inStream);

            ServerHandler serverHandler = new ServerHandler(this, socket, inputStream);
            Thread thread = new Thread(serverHandler);

            thread.start();
            sendMessage(new Message("Admin", "is my turn?"));
        } catch (IOException e) {
            e.printStackTrace();
            this.addClientMessage(new Message(this.username, "Unable to connect"));
        }
    }

    public void sendShip(Ship ship) {
        try {
            this.outputStream.writeObject(ship);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCord(Coordinate cord) {
        try {
            this.outputStream.writeObject(cord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
