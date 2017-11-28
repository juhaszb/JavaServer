import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Userfactory {
    /**
     * Merre található a felhasználó lista
     */
    private String userlist;

    /**Konstruktor
     * @param userlist
     * felhasználó lista
     */
    public Userfactory(String userlist)
    {
        this.userlist = userlist;
    }

    /**Új felhasználó létrehozása
     * @param email
     * Email cím
     * @param username
     * felhasználónév
     * @param password
     * Jelszó
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
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

    /**Megváltoztatja a fájl helyét, újat létrehozva, vagy meglévőre cserélve
     * @param locationtochange
     * fájl helye
     */
    public void changelocation(String locationtochange)
    {
        userlist = locationtochange;
    }

    /**getter
     * @return
     * Visszaadja a fájl helyét.
     */
    public String getlocation()
    {
        return userlist;
    }

}
