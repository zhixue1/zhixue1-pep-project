package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    //create new Message
    public Message createNewMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                Message newMessage = new Message();
                int generatedAccountID = (int) rs.getLong(1);
                newMessage.setMessage_id(generatedAccountID);
                newMessage.setPosted_by(message.getPosted_by());
                newMessage.setMessage_text(message.getMessage_text());
                newMessage.setTime_posted_epoch(message.getTime_posted_epoch());
                return newMessage;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //get all messages
    public List<Message> getAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messageList.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messageList;
    }

    //get one message given message id
    public Message getOneMessage(int messageId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    //delete a message given message id
    public void deleteAMessage(int messageId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //update message given message id
    public void updateMessage(int message_id, String messageText){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //get all messages from user give account id
    public List<Message> allUserMessages(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messageList.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messageList;
    }
}
