import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.resources.Messages_es;

import static org.junit.Assert.*;

public class MessageContainerTest {
   static  MessageContainer mc;
    @BeforeClass
    public static void setup()
    {
        mc = new MessageContainer("Admin","user","It's time");
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals(mc.getMessage(),"It's time");
    }

    @Test
    public void getUsernameFrom() throws Exception {
        assertEquals(mc.getUsernameFrom(),"Admin");
    }

    @Test
    public void getUsernameTo() throws Exception {
        assertEquals(mc.getUsernameTo(),"user");
    }

}