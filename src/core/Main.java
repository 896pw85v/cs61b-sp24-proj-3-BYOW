package core;

import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    public static void main(String[] args) {

        TETile[][] huh = new TETile[10][10];

        World world = new World(80, 60);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(80, 60);
        while (true) {
            renderer.renderFrame(world.world());
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
