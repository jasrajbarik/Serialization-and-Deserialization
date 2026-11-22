package Serialization.com.whatsapp.message;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID =1L;
    private String messageId;
    private String message;
    private transient String userId;    //will be skipped during serialization

    public Message(String _messageId, String _message, String _userId) {
        super();
        this.messageId=_messageId;
        this.message=_message;
        this.userId=_userId;
    }

    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String _messageId) {
        this.messageId=_messageId;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String _message) {
        this.message=_message;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String _userId) {
        this.userId=_userId;
    }
}
