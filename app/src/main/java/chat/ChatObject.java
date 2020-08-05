package chat;

public class ChatObject {

    private String message;
    private Boolean currentuser;
    public ChatObject(String message, Boolean currentuser) {

    this.message=message;
    this.currentuser=currentuser;

    }

    public String getMessage() {
        return message;
    }

    public void getMessage(String message) {
        this.message = message;
    }

    public Boolean getCurrentUser() {
        return currentuser;
    }

    public void getCurrentUser(Boolean currentuser) {
        this.currentuser = currentuser;
    }
}

