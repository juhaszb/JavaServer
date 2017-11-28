import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {
    static Client c;
    @BeforeClass
    public static void setup()
    {
         c = new Client(null,null);
    }

    @Test
    public void hashing() throws Exception {

        String hash =c.hashing("admin");
        assertEquals(hash,"44047210810420107506624974438055026627");
    }

    @Test(expected=NullPointerException.class)
    public void newmessage()
    {
        assertFalse(c.newMessage());
    }

}