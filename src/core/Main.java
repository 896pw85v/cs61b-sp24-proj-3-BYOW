package core;

import tileengine.TERenderer;
import tileengine.TETile;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random r = new Random();
        int seed = r.nextInt();
        System.out.println(seed);
//        1505171948    isolated room and dead end
//        706826667     dead end
//        -1083386485   array out of bound
//        -1310781681   disconnected

        World world = new World(70, 40);
        world.setSeed(seed);
//        world.setSeed(1505171948);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(70, 40);
        renderer.renderFrame(world.world());

    }
}
