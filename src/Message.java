public abstract class Message {
    protected String message;
    public abstract void decode();
    public Message(String message)
    {
        this.message = message;
    }
    public Message()
    {
        this.message = null;
    }
}
