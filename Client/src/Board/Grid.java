package Board;

import javax.swing.*;
import java.awt.*;

/**
 * @author Matt Jones
 * @version 1
 *
 * Abstract class that builds a grid, uses getTile from class
 * it's implemented in.
 */
public abstract class Grid extends JPanel {
    JPanel self;
    public boolean isPlayer;

    public Grid(boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        self = new JPanel();
        self.setLayout(new GridLayout(0, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel temp = getTile(this.isPlayer);
                self.add(temp);
            }
        }
        this.add(self);
    }

    //return the cell that selected at point p
    public JPanel getComponentAt(Point p) {
        Component comp = null;
        for (Component child : self.getComponents()) {
            if (child.getBounds().contains(p)) {
                comp = child;
            }
        }
        return (JPanel) comp;
    }

    protected abstract JPanel getTile(boolean isPlayer);
}