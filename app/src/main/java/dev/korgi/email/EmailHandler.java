package dev.korgi.email;

import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dev.korgi.main.Main;
import dev.korgi.utils.Utils;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class EmailHandler {
    private String Email;
    private String Password;
    private String Host = "imap.gmail.com";
    private Store store;
    private Folder currentFolder;
    private final Properties properties = new Properties();

    public EmailHandler(String email, String password){
        Email = email;
        Password = password;
        setUpProps();
    }

    private void setUpProps(){
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        properties.put("mail.smtp.port", "587");
    }

    public EmailHandler(String email, String password, String host){
        Host = host;
        Email = email;
        Password = password;
        setUpProps();
    }

    public void login(){
        Session emailSession = Session.getDefaultInstance(properties);
        try {
            store = emailSession.getStore("imaps");
            store.connect(Host, Email, Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFolder(String name) throws MessagingException{
        currentFolder = store.getFolder(name);
        currentFolder.open(Folder.READ_WRITE);
    }


    public Folder[] getAllFolders() throws MessagingException{
        return store.getDefaultFolder().list("*");
    }

    public Message[] getMessages() throws MessagingException{
        return currentFolder.getMessages();
    }

    public void closeFolder() throws MessagingException{
        currentFolder.close(false);
    }

    public void logout() throws MessagingException{
        store.close();
    }

    public void openFolder() throws MessagingException{
        currentFolder = store.getFolder("INBOX");
        currentFolder.open(Folder.READ_WRITE);
    }

    public boolean isUptoDate() throws MessagingException{
        Message[] messages = currentFolder.getMessages();
        for (Message message : messages) {
            if (!message.isSet(Flags.Flag.SEEN)) {
                // This is a new email
                return false;
            }
        }
        return true;
    }

    public void checkForNewEmails() throws Exception {
        Message[] messages = currentFolder.getMessages();
        for (Message message : messages) {
            if (!message.isSet(Flags.Flag.SEEN)) {
                // This is a new email
                handleNewEmail(message);
            }
        }
    }

    private void handleNewEmail(Message message) throws Exception {
        Main.eHandler.handleEmail(message);
    }


    public void startChecking(long interval) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkForNewEmails();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, interval);
    }

    public void sendEmail(String recipient, String subject, String body) {

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Email, Password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void MoveEmail(Message message, String destination) throws MessagingException{

        Utils.print("Locating Folder");

        Folder target = store.getFolder(destination);

        Utils.print("Verifying Folder");

        if (!target.exists()) {

            Utils.print("Creating Folder");

            target.create(Folder.HOLDS_MESSAGES);
        }

        Utils.print("Copying Message");
        Message[] temp = new Message[1];
        temp[0] = message;
        Utils.print("Moving Message to \"" + destination + "\"");
        target.appendMessages(temp);

        Utils.print("Deleting Original Message");
        message.setFlag(Flags.Flag.DELETED, true);
        currentFolder.expunge();
        Utils.print("Done!");
    }


    public void DeleteEmail(Message message) throws MessagingException{
        Utils.print("deleting");
        message.setFlag(Flags.Flag.DELETED, true);
        currentFolder.expunge();
        Utils.print("done!");
    }

}
