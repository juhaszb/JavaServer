import sun.java2d.pipe.SpanShapeRenderer;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class SimpleString extends Message {

    /**
     * szerver obj.
     */
    private  Server s;
    /**
     * MessageContainer obj.
     */
    private MessageContainer m ;
    /**
     * megtalálva?
     */
    private boolean found = false;

    /**
     * dekódolja az üzenetet.
     */
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

    /**Alapvető konstruktor
     * @param message
     * üzenet
     * @param to
     * kinek felhasználónév
     * @param from
     * kitől felhasználónév
     * @param s
     * szerver objektum
     */
    public SimpleString(String message,String to, String from,Server s)
    {
        super(message);
        m = new MessageContainer(from,to,message);
        this.s = s;

    }

    /**Konstruktor forradalma, második konstruktor
     * @param m
     * üzenetet tároló container
     * @param s
     * szerver objektum
     */
    public SimpleString(MessageContainer m,Server s)
    {
        super(m.getMessage());
        this.m = m;
        this.s = s;

    }

}
