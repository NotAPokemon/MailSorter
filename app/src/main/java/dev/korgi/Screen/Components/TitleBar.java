package dev.korgi.Screen.Components;


import javax.swing.*;

import dev.korgi.Screen.Screen;
import dev.korgi.main.Main;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class TitleBar extends JPanel{
    public TitleBar(String title, Screen frame ,ActionListener closeAction) throws IOException {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setSize( (int) Main.screenSize.getWidth(), 10);
        
        
        // Close Button
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.BLACK);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(closeAction); // Set the close action
        closeButton.setPreferredSize(new Dimension(40,40));
        closeButton.setVerticalTextPosition(SwingConstants.CENTER);
        closeButton.setFont(new Font("DialogInput", Font.PLAIN, 30));
        closeButton.setFocusPainted(false);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.RED); // Change to red on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(Color.BLACK); // Change back to black
            }
        });
        add(closeButton, BorderLayout.EAST);
    }
}
