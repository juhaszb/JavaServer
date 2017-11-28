import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Client extends Thread {
    /**
     * A kliens socket.
     */
    public SSLSocket socket;
    /**
     * Bejelentkezett -e a felhasználó
     */
    protected boolean loggedin = false;
    /**
     * Üzenet.
     */
    protected String message = null;
    /**
     * Felhasználónév
     */
    protected String username;
    /**
     * InputStream a socket számára
     */
    protected InputStream input = null;
    /**
     * BufferedReader, üzenetek fogadása
     */
    protected BufferedReader br = null;
    /**
     * A kimeneti Stream socketeknél.
     */
    protected DataOutputStream dout = null;
    /**
     * Szerver.
     */
    protected Server s;
    /**
     * Leállt már a szerver.
     */
    protected  boolean ended = false;
    /**
     * Thread leállításárs való.
     */
    private volatile boolean stopsignal;

    /**
     * Kliens sockete
     *  @param clientsocket
     * Szerver példány
     * @param s
     */
    public Client(SSLSocket clientsocket,Server s)
    {
        socket = clientsocket;
        stopsignal = false;
        this.s = s ;
    }

    /**
     * Threadet futtató függvény.
     */
    public void run()
    {

        try
        {
            input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input,"UTF-8"));
            dout = new DataOutputStream(socket.getOutputStream());
            String line ;
            String[] split ;
            while(!stopsignal)
            {
                line = br.readLine();
                split = line.split("\t");
                Message m;
                if(!loggedin)
                {
                    if(split[0].equals("login")) {
                        loggedin = tryLogin(split[1], split[2]);
                        System.out.println(split[1] + " "+ split[2]);
                    }

                }
                else if(split[0].equals("msg") && split[1].equals("string"))
                {
                    m = new SimpleString(split[3],split[2],username,s);
                    m.decode();
                    System.out.println(line);

                }
                else {
                    System.out.println(line);
                }
                if(socket.isConnected() == false)
                    ended = true;


            }

        }
        catch( Exception e)
        {
            return;
        }

    }

    /**
     * Felhasználónév
     * @param username
     * Jelszó
     * @param password
     * Sikeres volt-e a belépés
     * @return
     */
    public boolean tryLogin(String username,String password)
    {
        try {
            FileReader fin = new FileReader(Server.getuserfactorylocation());
            BufferedReader br = new BufferedReader(fin);
            String line ;
            while((line = br.readLine())!= null)
            {
                String[] split = line.split("\t");
                String hash = hashing(password);
                System.out.println(split[1]+ " " +split[2]+ " "+ hash );
                if(split[1].equals(username) && split[2].equals(hash)) {
                    String ok = "login_ok\n";
                    System.out.println("login_k");
                    byte[] k = ok.getBytes("UTF-8");
                    dout.write(k);
                    this.username = username;
                    File f = new File(username);
                    if(f.exists())
                    {
                        System.out.println("it exists");
                        try{
                            FileInputStream fi = new FileInputStream(username);
                            ObjectInputStream oi = new ObjectInputStream(fi);
                            ArrayList<MessageContainer> messages = new ArrayList<>();
                            messages =(ArrayList<MessageContainer>)oi.readObject();
                            System.out.println(messages.size());
                            for(MessageContainer mc : messages)
                            {
                                System.out.println("It should send");
                                SimpleString si = new SimpleString(mc,s);
                                si.decode();
                            }
                            oi.close();
                            f.delete();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Still exception");
                            System.out.println(e.getCause());
                        }
                    }
                    return true;
                }
                else if (split[1].equals(username) && !split[2].equals(hash)) {
                    System.out.println("Incorrect login");
                    String no = "login_no\n";
                    byte[] str = no.getBytes("UTF-8");
                    dout.write(str);
                    closeall();
                    return false;

                }
            }
            System.out.println("incorrect login");
            String no = "login_no\n";
            byte[] str = no.getBytes("UTF-8");
            dout.write(str);
            closeall();
            return false;

        }
        catch(Exception e)
        {
            ;
        }
        return false;
    }

    /**
     * felhasználónevet adja vissza
     * @return
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Volt-e új üzenet?
     * @return
     * @throws NullPointerException
     */
    public boolean newMessage() throws NullPointerException
    {
        if(message.equals(null))
            return false;
        return true;
    }

    /**
     * Bezárja a streameket és socketokat.
     */
  /*  public String getMessage()
    {
        return message;
    }*/
    public void closeall()
    {
        try {
            br.close();
            dout.close();
            socket.close();
        }
        catch(Exception e)
        {
            ;
        }
    }

    /**Elküldi az üzenetekt.
     * Küldenivaló üzenet
     * @param message
     */
    /*public void stopit()
    {
        stopsignal = true;
    }
    public boolean socketrunning()
    {
        return !socket.isClosed();
    }*/
    public void send(String message)
    {
        try {
            byte[] tosend = message.getBytes("UTF-8");
            dout.write(tosend);
        }
        catch (Exception e)
        {
            ;
        }
    }

    /**Le hasheli a jelszót.
     * Hashelendő jelszó
     * @param password
     * A jelszó hashelt verziója
     * @return
     */
    public String hashing(String password)
    {
        String hash = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5"); // MD5 hash generation
            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
             hash = bigInt.toString();
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
        }
        catch (Exception e)
        {
            ;
        }
        return hash;
    }

   /* public boolean isEnded() {
        return ended;
    }*/
}
