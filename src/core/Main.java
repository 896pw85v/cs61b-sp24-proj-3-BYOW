package core;

import tileengine.TERenderer;
import tileengine.TETile;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random r = new Random();
        int seed = r.nextInt();
        System.out.println(seed);
// 1505171948
//        706826667
//        -1083386485

        World world = new World(70, 40);
        world.setSeed(seed);
//        world.setSeed(706826667);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(70, 40);
        renderer.renderFrame(world.world());

    }
}
