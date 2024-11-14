package dev.korgi.Screen.Components;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.*;

import dev.korgi.main.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MailContainer extends JPanel {
    private List<Message> messages;
    private JPanel messagePanel;
    private JScrollPane scrollPane;

    public MailContainer() {
        messages = new ArrayList<>();
        setLayout(new BorderLayout());

        setSize(new Dimension((int)Main.screenSize.getWidth(), 600));

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(new Color(40,40,40));
        
        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(40, 40, 40));
        scrollPane.getViewport().setBackground(new Color(40, 40, 40));
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        
        
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addMessage(Message message) throws MessagingException {
        messages.add(message);
        updateMessageDisplay();
    }

    public void clear(){
        messagePanel.removeAll();
    }

    private void updateMessageDisplay() throws MessagingException {
        messagePanel.removeAll();
        for (Message message : messages) {
            Mail newMessage = new Mail(message.getSubject() + " ","From: " + message.getFrom()[0] + " ");
            messagePanel.add(newMessage);
        }
        messagePanel.revalidate();
        messagePanel.repaint();
    }

    public List<Message> getMessages(){
        return messages;
    }
}