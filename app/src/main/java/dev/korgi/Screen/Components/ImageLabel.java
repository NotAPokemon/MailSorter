package dev.korgi.Screen.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageLabel extends JLabel {
    private Image image;

    public ImageLabel(String imagePath) {
        try {
            // Load the image
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw the image as the background
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g); // Call the superclass method
    }
}