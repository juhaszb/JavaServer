import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Userfactory {
    private String userlist;
    public Userfactory(String userlist)
    {
        this.userlist = userlist;
    }
    public void CreateUser(String email,String username,String password) throws IOException,NoSuchAlgorithmException
    {

        PrintWriter pw = new PrintWriter(new FileOutputStream(new File(userlist),true));
        System.out.println(password);
        MessageDigest m = MessageDigest.getInstance("MD5"); // MD5 hash generation
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt  = new BigInteger(1,digest);
        String hash = bigInt.toString();
        while(hash.length() < 32 ){
            hash = "0"+hash;
        }
        pw.println(username+"\t"+email+"\t"+hash);
        pw.close();

    }
    public void changelocation(String locationtochange)
    {
        userlist = locationtochange;
    }
    public String getlocation()
    {
        return userlist;
    }

}
