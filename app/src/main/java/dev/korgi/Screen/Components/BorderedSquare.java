package dev.korgi.Screen.Components;

import javax.swing.*;
import java.awt.*;

class BorderedSquare extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set color for the outer square
        g.setColor(Color.BLUE);
        g.fillRect(50, 50, 100, 100); // Draw outer square

        // Set color for the inner square (hole)
        g.setColor(getBackground()); // Set to the panel's background color
        g.fillRect(70, 70, 60, 60); // Draw inner square (hole)
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(40, 40); // Set preferred size
    }
}
