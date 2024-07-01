package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import java.util.*;

public class MessageService {

    public MessageDAO messageDAO;
    public AccountDAO accountDAO = new AccountDAO();

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //create new message
    public Message createNewMessage(Message message){
        if(message.message_text != null && message.message_text != "" && message.message_text.length() < 255 && accountDAO.accountIdCheck(message.getPosted_by()) != null){
            return messageDAO.createNewMessage(message);
        } else {
            return null;
        }
    }

    //get all messages
    public List<Message> getAllMessage(){
        return messageDAO.getAllMessage();
    }

    //get one message given message id
    public Message getOneMessage(int messageId){
        return messageDAO.getOneMessage(messageId);
    }

    //delete a message given message id
    public Message deleteAMessage(int messageId){
        Message deletedMessage = messageDAO.getOneMessage(messageId);
        if(deletedMessage != null){
            messageDAO.deleteAMessage(messageId);
            return deletedMessage;
        } else {
            return null;
        }
    }

    //update message given message id
    public Message updateMessage(int messageId, String messageText){
        if(messageDAO.getOneMessage(messageId) != null && messageText != null && messageText != "" && messageText.length() <= 255){
            messageDAO.updateMessage(messageId, messageText);
            return messageDAO.getOneMessage(messageId);
        } else {
            return null;
        }
    }

    //get all messages from user given account id
    public List<Message> allUserMessages(int accountId){
        return messageDAO.allUserMessages(accountId);
    }

}
