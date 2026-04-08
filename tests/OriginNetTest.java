import core.Origin;
import core.OriginNet;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;


public class OriginNetTest {
    OriginNet net;
    @BeforeEach
    void setUp() {
        net = new OriginNet();
    }

    @Test
    void testAddOrigin() {
        net.addNode(new Origin(1, 2));
        assertTrue(net.list.contains(new Origin(1, 2)));
    }

    @Test
    void testPutNode() {
        net.addNode(new Origin(1, 2));
        net.insert(new Origin(1, 2), new Origin(3, 4));
        assertTrue(net.net.get(new Origin(1, 2)).contains(new Origin(3, 4)));
    }

    @Test
    void testChangeMapping() {
        Origin point12 = new Origin(1, 2);
        Origin point34 = new Origin(3, 4);
        net.addNode(point12);
        net.insert(point12, point34);
        assertTrue(net.net.get(new Origin(1, 2)).contains(new Origin(3, 4)));
        net.net.get(point12).clear();
        assertEquals(new HashSet<>(), net.net.get(point12), "modifying value is impossible");
        assertNotSame(new HashSet<>(), net.net.get(point12));
    }
}
