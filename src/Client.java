import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;

public class Client extends Thread {
    protected SSLSocket socket;
    protected boolean loggedin = false;
    protected String message = null;
    protected String username;
    protected InputStream input = null;
    protected BufferedReader br = null;
    protected DataOutputStream dout = null;
    private volatile boolean stopsignal;
    public Client(SSLSocket clientsocket)
    {
        socket = clientsocket;
        stopsignal = false;
    }
    public void run()
    {

        try
        {
            input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            dout = new DataOutputStream(socket.getOutputStream());
            String line ;
            String[] split ;
            while(!stopsignal)
            {
                line = br.readLine();
                split = line.split("\t");
                if(!loggedin)
                {
                    if(split[0].equals("login")) {
                        loggedin = tryLogin(split[1], split[2]);
                        System.out.println(split[1] + " "+ split[2]);
                    }

                }
                else {
                    System.out.println(line);
                }

            }

        }
        catch( Exception e)
        {
            return;
        }

    }
    public boolean tryLogin(String username,String password)
    {
        try {
            FileReader fin = new FileReader(Server.getuserfactorylocation());
            BufferedReader br = new BufferedReader(fin);
            String line ;
            while((line = br.readLine())!= null)
            {
                String[] split = line.split("\t");
                MessageDigest m = MessageDigest.getInstance("MD5"); // MD5 hash generation
                m.reset();
                m.update(password.getBytes());
                byte[] digest = m.digest();
                BigInteger bigInt  = new BigInteger(1,digest);
                String hash = bigInt.toString();
                while(hash.length() < 32 ){
                    hash = "0"+hash;
                }
                System.out.println(split[1]+ " " +split[2]+ " "+ hash );
                if(split[1].equals(username) && split[2].equals(hash)) {
                    String ok = "login_ok\n";
                    System.out.println("login_k");
                    byte[] k = ok.getBytes("UTF-8");
                    dout.write(k);
                    this.username = username;
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
    public String getUsername()
    {
        return username;
    }
    public boolean newMessage()
    {
        if(message.equals(null))
            return false;
        return true;
    }
    public String getMessage()
    {
        return message;
    }
    protected void closeall()
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
    public void stopit()
    {
        stopsignal = true;
    }
    public boolean socketrunning()
    {
        return !socket.isClosed();
    }
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
}
