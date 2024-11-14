package dev.korgi.email;

import javax.mail.Message;
import javax.mail.MessagingException;

import dev.korgi.utils.Utils;

public class EmailContainer {
    private EmailHandler handler;
    public EmailContainer(String email, String Password) throws MessagingException{
        handler = new EmailHandler(email, Password);
    }

    public void start() throws MessagingException{
        handler.login();
        handler.openFolder();
        Message[] messages = handler.getMessages();
        for (int i = 0; i < messages.length; i++){
            handleEmail(messages[i]);
        }
    }

    public void handleEmail(Message message) throws MessagingException{
        String sender = message.getFrom()[0].toString();
        String subject = message.getSubject();
        if(sender.equals("Homestead Robotics <team670@homesteadrobotics.com>") || sender.equals("Competitions Team <comps@homesteadrobotics.com>")){
            Utils.print("moving");
            handler.MoveEmail(message, "Robotics");
        } else if (subject.contains("Weekly Bulletin") || subject.contains("Week")){
            handler.MoveEmail(message, "Weekly Bulletin");
        } else if (subject.toLowerCase().contains("fbla") || subject.toLowerCase().contains("pm #")){
            handler.MoveEmail(message, "FBLA");
        } else if (sender.contains("SDC")) {
            handler.MoveEmail(message, "Speech And Debate");
        } else if (sender.contains("Cloudflare")){
            handler.MoveEmail(message, "Cloudflare");
        } else if (sender.contains("Google Forms")){
            handler.MoveEmail(message, "Form Copys");
        } else if (subject.contains("HHS Hub")){
            handler.DeleteEmail(message);
        } else if (sender.contains("Schoology <")){
            handler.DeleteEmail(message);
        } else if (sender.contains("HHS Data Science <hhsdatascience@gmail.com>")){
            handler.MoveEmail(message, "Data Science");
        } else if (sender.contains("FUHSD")){
            handler.MoveEmail(message, "FUHSD");
        }
    }

    public EmailHandler getHandler(){
        return handler;
    }
}
