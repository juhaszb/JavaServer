import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerTest {
    public static Server s;
    @BeforeClass
    public static void set()
    {
        s = new Server(1250,"valami.txt");
        s.setStart();
        s.start();
    }

    @Test
    public void startready() throws Exception {
        assertTrue(s.startready());
    }

    @Before
    public void setback()
    {
        s.changefactory("valami.txt");
    }

    @Test
    public void getuserfactorylocation()
    {
        assertEquals(s.getuserfactorylocation(),"valami.txt");
    }

    @Test
    public void changefactory() throws Exception {
        s.changefactory("uj.txt");
        assertEquals(s.getuserfactorylocation(),"uj.txt");
    }



}