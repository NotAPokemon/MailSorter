package dev.korgi.main;
/*
 * This source file was generated by the Gradle 'init' task
 */

import javax.imageio.ImageIO;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;


import dev.korgi.Screen.Screen;
import dev.korgi.Screen.Components.MailContainer;
import dev.korgi.Screen.Components.ScreenContainer;
import dev.korgi.Screen.Components.TabContainer;
import dev.korgi.email.EmailContainer;
import dev.korgi.utils.JsonFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static EmailContainer eHandler;
    public static JsonFile config;
    public final static MailContainer mailContainer = new MailContainer();
    public static boolean showScreen = false;

    public static void main(String[] args) throws MessagingException, IOException {


        config = new JsonFile("config.json");
        String email = (String)config.read().get("email");
        String password = (String)config.read().get("password");

        eHandler = new EmailContainer(email, password);
        eHandler.getHandler().login();
        eHandler.getHandler().openFolder((String)config.read().get("Default"));
        eHandler.start();


        if (showScreen){
            Folder[] all_folders = eHandler.getHandler().getAllFolders();
            String[] tabs = new String[all_folders.length];
            for (int i = 0; i < all_folders.length; i++){
                tabs[i] = all_folders[i].getName();
            }

            
            List<String> ol = new ArrayList<>(Arrays.asList(tabs));
            ol.remove("[Gmail]");
            ol.remove("All Mail");
            ol.remove("Drafts");
            ol.remove("Sent Mail");
            ol.remove("Starred");

            tabs = ol.toArray(new String[0]);
            
            

            Screen screen = new Screen("Email", screenSize);
            ScreenContainer screencont = new ScreenContainer();


            screen.add(screencont);
            screencont.add(new TabContainer(tabs));
            screencont.add(mailContainer);
            Message[] x = eHandler.getHandler().getMessages();
            for (Message message : x) {
            mailContainer.addMessage(message);
            }

            screen.revalidate();
            screen.repaint();
            
            
            Image icon = ImageIO.read(new File("C:\\Users\\every\\OneDrive\\Desktop\\JavaProject\\app\\src\\main\\resources\\roundKorgi.png"));
            screen.setIconImage(icon);
        }
        


        

        
        

    }

    
}
