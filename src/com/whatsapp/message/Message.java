package com.whatsapp.message;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID =1L;
    private String messageId;
    private String message;

    public Message(String _messageId, String _message) {
        super();
        this.messageId=_messageId;
        this.message=_message;
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
}
