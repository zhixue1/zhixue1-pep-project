package Controller;

import java.util.*;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postCreateNewMessageHandler);
        app.get("/messages", this::getAllmessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageHandler);
        app.delete("/messages/{message_id}", this::deleteAMessageHandler);
        app.patch("/messages/{message_id}", this::patchUpdateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessages);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //user registration handler
    private void postRegisterHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account userRegistration = accountService.userRegistration(account);
        if(userRegistration != null){
            ctx.json(om.writeValueAsString(userRegistration));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    //login handler
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.login(account);
        if(loginAccount != null){
            ctx.json(om.writeValueAsString(loginAccount));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    //create new message handler
    private void postCreateNewMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createNewMessage(message);
        if(newMessage != null){
            ctx.json(om.writeValueAsString(newMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    //get all messages handler
    private void getAllmessagesHandler(Context ctx){
        List<Message> allMessages = messageService.getAllMessage();
        ctx.json(allMessages);
        ctx.status(200);
    }

    //get one message from message id handler
    private void getOneMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getOneMessage(messageId);
        ctx.json(message);
        ctx.status(200);
    }

    //delete a message given message id handler
    private void deleteAMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteAMessage(messageId);
        ctx.json(deletedMessage);
        ctx.status(200);
    }

    //update a message given message id handler
    private void patchUpdateMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        String messageText = ctx.body();
        Message updatedMessage = messageService.updateMessage(messageId, messageText);
        if(updatedMessage != null){
            ctx.json(updatedMessage);
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    //get all messages from user given account id
    private void getAllUserMessages(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> allUserMessages = messageService.allUserMessages(accountId);
        ctx.json(allUserMessages);
        ctx.status(200);
    }

}