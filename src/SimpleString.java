import sun.java2d.pipe.SpanShapeRenderer;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class SimpleString extends Message {

    private   Server s;
    MessageContainer m ;
    private boolean found = false;
    @Override
    public void decode() {
        Vector<Client> clients = s.getClients();
        for(Client c: clients)
        {
            if(c.getUsername().equals(m.getUsernameTo()))
            {
                c.send("msg\tstring\t"+m.getUsernameFrom()+"\t"+message+"\n");
                found = true;
            }
        }
        if(!found)
        {
            m.Serialize();
        }


    }
    public SimpleString(String message,String to, String from,Server s)
    {
        super(message);
        m = new MessageContainer(from,to,message);
        this.s = s;

    }
    public SimpleString(MessageContainer m,Server s)
    {
        super(m.getMessage());
        this.m = m;
        this.s = s;

    }

}
