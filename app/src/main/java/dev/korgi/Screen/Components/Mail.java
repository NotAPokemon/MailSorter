package dev.korgi.Screen.Components;

import javax.swing.*;

import dev.korgi.main.Main;

import java.awt.*;

public class Mail extends JPanel {
    private String bigText;
    private String smallText;

    public Mail(String bigText, String smallText) {
        this.bigText = bigText;
        this.smallText = smallText;
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 100, 100));
        setBackground(new Color(80, 80, 80));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int yOffset = Main.mailContainer.getMessages().size();
        
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0 + yOffset + 10, getWidth(), getHeight(), 30, 30);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString(bigText, 20, 40 + yOffset);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString(smallText, 20, 60 + yOffset);
    }
}
