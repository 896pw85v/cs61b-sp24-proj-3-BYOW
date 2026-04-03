import core.Origin;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WorldSomeTest {
    int length = 10;
    int width = 10;
    Random r = new Random();

    @Test
    void testGenerateRoom() {
        // [ ] [1] [ ] [ ] [ ] [ ] [ ] [7] [ ] [ ] 10
        int x = r.nextInt(1, length - 3);
        int y = r.nextInt(1, width - 3);

        int xp = r.nextInt(x + 1, length - 1);
        int yp = r.nextInt(y + 1, width - 1);

        if (xp - x > 15) xp = x + 15;
        if (yp - y > 15) yp = y + 15;
        int ox;
        int oy;
        if (x == xp) {
            ox = x;
        } else {
            ox = r.nextInt(x, xp);
        }
        if (y == yp) {
            oy = y;
        } else {
            oy = r.nextInt(y, yp);
        }

        Origin origin = new Origin(ox, oy);
        System.out.println(origin + " " + x + "-" + xp + ", " + y + "-" + yp);
        assertTrue(origin.x() <= xp, "smaller than right edge");
        assertTrue(origin.x() >= x, "smaller than right edge");
        assertTrue(origin.y() <= yp, "smaller than right edge");
        assertTrue(origin.y() >= y, "smaller than right edge");
    }
}
