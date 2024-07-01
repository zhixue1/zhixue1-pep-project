package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //user registration
    public Account userRegistration(Account account){
        if (account.username != null && account.username != "" && account.password.length() >= 4 && accountDAO.usernameCheck(account.username) == null){
            return accountDAO.userRegistration(account);
        } else {
            return null;
        }
    }
    
    //login 
    public Account login(Account account){
        return accountDAO.login(account);
    }
}
