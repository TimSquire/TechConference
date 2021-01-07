import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
public class testfinal {
    @Test
    public void testFinal() {
        int a = 2;
        int b = 2;
        Integer w = new Integer(2);
        Integer x = new Integer(2);
        Integer y = x;
        Integer z = new Integer(w);
        assertTrue(x == y);
        assertTrue(a == x);
        assertTrue(a == b);
    }
}
