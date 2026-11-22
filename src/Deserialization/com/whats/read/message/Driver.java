package Deserialization.com.whats.read.message;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Driver {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(
                "C:\\Users\\jasra\\OneDrive\\Desktop\\Java\\Serialization\\message.ser"
        );

        ObjectInputStream ois = new ObjectInputStream(fis);

        Serialization.com.whatsapp.message.Message message =
                (Serialization.com.whatsapp.message.Message) ois.readObject();

        System.out.println("De Ser: " + message.getMessage()+" || MessageID :"+message.getMessageId()+" || User Id: "+message.getUserId());

        ois.close();
        fis.close();
    }
}