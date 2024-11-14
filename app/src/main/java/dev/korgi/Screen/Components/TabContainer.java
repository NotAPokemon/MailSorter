package dev.korgi.Screen.Components;

import javax.swing.*;

import dev.korgi.main.Main;
import dev.korgi.utils.Utils;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;


public class TabContainer extends JPanel{
    private String[] tabs;
    private int selectedIndex;

    public TabContainer(String[] argtabs){
        tabs = argtabs;
        selectedIndex = Utils.getIndex(argtabs, Main.config.read().get("Default"));
        setPreferredSize(new Dimension((int) Main.screenSize.getWidth(), 100));
        setBackground(new Color(40, 40, 40));
        setLayout(new FlowLayout());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }

        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(60, 60, 60));

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        int tabWidth = 200;
        int tabHeight = 50;
        int padding = 20;

        for (int i =0; i < tabs.length; i++){

            int x = 50 + 220 * i;
            int y = 0;
            

            g.setColor(new Color(60, 60, 60));

            if (i == selectedIndex){
                g.setColor(new Color(80, 80, 80));
            }

            g.fillRoundRect(50 + 220 * (i), 0, 200, 50,20,20);

            g.setColor(Color.WHITE);
            
            String[] lines = wrapText(g, tabs[i], tabWidth - padding * 2);

            int lineHeight = g.getFontMetrics().getHeight();
            int startY = y + (tabHeight - (lines.length * lineHeight)) / 2 + lineHeight;

            // Draw each line of text
            for (String line : lines) {
                g.drawString(line, x + padding, startY - 5);
                startY += lineHeight;
            }
        }
    }

    private String[] wrapText(Graphics g, String text, int maxWidth) {
        FontMetrics metrics = g.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder wrappedText = new StringBuilder();
        List<String> lines = new ArrayList<>();
    
        for (String word : words) {
            // Check if adding the word exceeds the max width
            if (metrics.stringWidth(wrappedText + word) > maxWidth) {
                lines.add(wrappedText.toString().trim());
                wrappedText.setLength(0); // Reset for the next line
            }
            wrappedText.append(word).append(" ");
        }
        lines.add(wrappedText.toString().trim()); // Add the last line
    
        return lines.toArray(new String[0]);
    }

    private void handleMouseClick(int x, int y) {
        for (int i = 0; i < tabs.length; i++) {
            int rectX = 10 + 220 * i; 
            int rectY = 0;             
            int rectWidth = 200;       
            int rectHeight = 50;

            if (x >= rectX && x <= rectX + rectWidth &&
                y >= rectY && y <= rectY + rectHeight) {
                selectedIndex = i;
                repaint();
                break;
            }
        }
    }
}
