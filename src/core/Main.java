package core;

import tileengine.TERenderer;
import tileengine.TETile;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random r = new Random();
        int seed = r.nextInt();
        System.out.println(seed);
//        1505171948    wrong closest
//        706826667     dead end
//        -1083386485   array out of bound
//        -1310781681   disconnected
//        655081307     index 70 out of 70
//        679430966     wrong closest (12, 12)
//        552674602     wrong closest, with image
//        934267690     wrong closest

        World world = new World(70, 40);
        world.setSeed(seed);
//        world.setSeed(934267690);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(70, 40);
        renderer.renderFrame(world.world());

    }
}
