import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserfactoryTest {
    static Userfactory uf ;
    @BeforeClass
    public static void setup()
    {
        uf = new Userfactory("teszt.txt");
    }

    @Before
    public void retunrit()
    {
        uf.changelocation("teszt.txt");
    }
    @Test
    public void getlocation() throws Exception {
        assertEquals(uf.getlocation(),"teszt.txt");
    }

    @Test
    public void changelocation() throws Exception {
        uf.changelocation("ujj.txt");
        assertEquals(uf.getlocation(),"ujj.txt");
    }

}