package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {

    //user registration
    public Account userRegistration(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account(username, password) VALUES(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                Account newAccount = new Account();
                int generatedAccountID = (int) rs.getLong(1);
                newAccount.setAccount_id(generatedAccountID);
                newAccount.setUsername(account.getUsername());
                newAccount.setPassword(account.getPassword());
                return newAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //login
    public Account login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND Password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Account newAccount = new Account();
                newAccount.setAccount_id(rs.getInt("account_id"));
                newAccount.setUsername(rs.getString("username"));
                newAccount.setPassword(rs.getString("password"));
                return newAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //check if username exist
    public Account usernameCheck(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Account newAccount = new Account();
                newAccount.setAccount_id(rs.getInt("account_id"));
                newAccount.setUsername(rs.getString("username"));
                newAccount.setPassword(rs.getString("password"));
                return newAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    //check if account id exist
    public Account accountIdCheck(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Account newAccount = new Account();
                newAccount.setAccount_id(rs.getInt("account_id"));
                newAccount.setUsername(rs.getString("username"));
                newAccount.setPassword(rs.getString("password"));
                return newAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
