import java.io.*;
import java.util.ArrayList;

public class MessageContainer implements Serializable{
    private String UsernameTo;
    private String UsernameFrom;
    private String message;

    public MessageContainer(String UF,String UTO,String msg)
    {
        UsernameFrom =UF;
        UsernameTo = UTO;
        message = msg;
    }
    public void Serialize()
    {
        File f = new File(UsernameTo);
        ArrayList<MessageContainer> messages = new ArrayList<>();
        if(f.exists()) {
            try {
                FileInputStream fi = new FileInputStream(UsernameTo);
                ObjectInputStream os = new ObjectInputStream(fi);


                messages = (ArrayList<MessageContainer>)os.readObject();
                messages.add(this);
                os.close();
                FileOutputStream fs = new FileOutputStream(UsernameTo);
                ObjectOutputStream oo = new ObjectOutputStream(fs);
                oo.writeObject(messages);
                oo.close();
            }
            catch(Exception e)
            {
                ;
            }
        }
        else{
            try{
                FileOutputStream fo = new FileOutputStream(UsernameTo);
                ObjectOutputStream oo = new ObjectOutputStream(fo);
                messages.add(this);
                oo.writeObject(messages);
                oo.close();

            }
            catch (Exception e)
            {
                ;
            }
        }

    }

    public String getMessage() {
        return message;
    }

    public String getUsernameFrom() {
        return UsernameFrom;
    }

    public String getUsernameTo() {
        return UsernameTo;
    }
}
