package dev.korgi.Screen;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.mail.MessagingException;
import javax.swing.JFrame;

import dev.korgi.Screen.Components.TitleBar;
import dev.korgi.email.EmailHandler;
import dev.korgi.main.Main;

public class Screen extends JFrame {
    private EmailHandler handler;
    private Point initialClick;
    private final int RESIZE_MARGIN = 50;
    private final int CORNER_RADIUS = 30;
    private boolean resizing;
    private String resizeDirection;

    public Screen(String Title, Dimension dimensions) throws IOException{
        setTitle(Title);   
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(new Dimension((int) dimensions.getWidth()- 50, (int) dimensions.getHeight() - 100));
        setBackground(new Color(40, 40, 40));
        TitleBar titleBar = new TitleBar(Title, this, e -> {
            try {
                handleCloseOperation();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            } finally {
                dispose(); // Close the frame
            }
        });


        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Calculate the new location of the frame
                int newX = thisX + e.getX() - initialClick.x;
                int newY = thisY + e.getY() - initialClick.y;

                setLocation(newX, newY);
            }
        });

        // Add mouse listeners for resizing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isInResizeArea(e.getPoint())) {
                    initialClick = e.getPoint();
                    resizing = true;
                    resizeDirection = getResizeDirection(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false; // Stop resizing
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {
                    resizeFrame(e.getPoint());
                }
            }
        });


        setLayout(new BorderLayout());
        add(titleBar, BorderLayout.NORTH);

        setVisible(true);
        
        handler = Main.eHandler.getHandler();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    handleCloseOperation();
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                } finally {
                    dispose();
                }
            }
        });
    }


    private boolean isInResizeArea(Point point) {
        return point.x <= RESIZE_MARGIN || point.x >= getWidth() - RESIZE_MARGIN ||
               point.y <= RESIZE_MARGIN || point.y >= getHeight() - RESIZE_MARGIN;
    }

    private String getResizeDirection(Point point) {
        boolean atLeft = point.x <= RESIZE_MARGIN;
        boolean atRight = point.x >= getWidth() - RESIZE_MARGIN;
        boolean atTop = point.y <= RESIZE_MARGIN;
        boolean atBottom = point.y >= getHeight() - RESIZE_MARGIN;

        if (atLeft && atTop) return "TOP_LEFT";
        if (atLeft && atBottom) return "BOTTOM_LEFT";
        if (atRight && atTop) return "TOP_RIGHT";
        if (atRight && atBottom) return "BOTTOM_RIGHT";
        if (atLeft) return "LEFT";
        if (atRight) return "RIGHT";
        if (atTop) return "TOP";
        if (atBottom) return "BOTTOM";

        return "NONE";
    }


    private void resizeFrame(Point currentMousePosition) {
        int newWidth = getWidth();
        int newHeight = getHeight();
        int deltaX = currentMousePosition.x - initialClick.x;
        int deltaY = currentMousePosition.y - initialClick.y;
    
        switch (resizeDirection) {
            case "TOP_LEFT":
                newWidth -= deltaX;
                newHeight -= deltaY;
                if (newWidth > RESIZE_MARGIN && newHeight > RESIZE_MARGIN) {
                    setSize(newWidth, newHeight);
                    setLocation(getX() + deltaX, getY() + deltaY);
                }
                break;
            case "TOP":
                newHeight -= deltaY;
                if (newHeight > RESIZE_MARGIN) {
                    setSize(newWidth, newHeight);
                    setLocation(getX(), getY() + deltaY);
                }
                break;
            case "TOP_RIGHT":
                newWidth += deltaX;
                newHeight -= deltaY;
                if (newWidth > RESIZE_MARGIN && newHeight > RESIZE_MARGIN) {
                    setSize(newWidth, newHeight);
                    setLocation(getX(), getY() + deltaY);
                }
                break;
            case "LEFT":
                newWidth -= deltaX;
                if (newWidth > RESIZE_MARGIN) {
                    setSize(newWidth, newHeight);
                    setLocation(getX() + deltaX, getY());
                }
                break;
            case "RIGHT":
                newWidth += deltaX;
                setSize(newWidth, newHeight);
                break;
            case "BOTTOM_LEFT":
                newWidth -= deltaX;
                newHeight += deltaY;
                if (newWidth > RESIZE_MARGIN) {
                    setSize(newWidth, newHeight);
                    setLocation(getX() + deltaX, getY());
                }
                break;
            case "BOTTOM":
                newHeight += deltaY;
                setSize(newWidth, newHeight);
                break;
            case "BOTTOM_RIGHT":
                newWidth += deltaX;
                newHeight += deltaY;
                setSize(newWidth, newHeight);
                break;
        }
    
        // Update the initial click point for continuous resizing
        initialClick = currentMousePosition;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Set the shape of the window
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 
                                                    CORNER_RADIUS, CORNER_RADIUS);
        setShape(shape);
    }

    private void handleCloseOperation() throws MessagingException {
        handler.closeFolder();
        handler.logout();
        dispose();
    }
}
