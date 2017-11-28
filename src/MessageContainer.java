import java.io.*;
import java.util.ArrayList;

public class MessageContainer implements Serializable{
    /**
     * A címzett felhasználóneve
     */
    private String UsernameTo;
    /**
     * Küldő felhasználóneve
     */
    private String UsernameFrom;
    /**
     * Üzenet
     */
    private String message;

    /**Konstruktor
     * @param UF
     * A küldő felhasználóneve
     * @param UTO
     * A címzett felhasználóneve
     * @param msg
     * üzenet
     */
    public MessageContainer(String UF,String UTO,String msg)
    {
        UsernameFrom =UF;
        UsernameTo = UTO;
        message = msg;
    }

    /**
     * Szerializáció
     */
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

    /**Getter
     * @return
     * üzenet
     */
    public String getMessage() {
        return message;
    }

    /**Getter
     * @return
     * Küldő felhasználóneve
     */
    public String getUsernameFrom() {
        return UsernameFrom;
    }

    /**Getter
     * @return
     * Címzett felhasználóneve
     */
    public String getUsernameTo() {
        return UsernameTo;
    }
}
