import java.io.Serializable;

public abstract class Message implements Serializable{
    /**
     * Üzenet.
     */
    protected String message;

    /**
     * Dekódol egy üzenetet.
     */
    public abstract void decode();

    /**Konstruktor
     * @param message
     * üzenet.
     */
    public Message(String message)
    {
        this.message = message;
    }

}
