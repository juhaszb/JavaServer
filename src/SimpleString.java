import java.util.Vector;

public class SimpleString extends Message {

    private String UsernameTo;
    private String UsernameFrom;
    private Server s;
    @Override
    public void decode() {
        Vector<Client> clients = s.getClients();
        for(Client c: clients)
        {
            if(c.getUsername().equals(UsernameTo))
            {
                c.send("msg\tstring\t"+UsernameFrom+"\t"+message+"\n");
            }
        }

    }
    public SimpleString(String message,String to, String from,Server s)
    {
        super(message);
        this.UsernameTo = to;
        this.UsernameFrom = from ;
        this.s = s;
    }
}
