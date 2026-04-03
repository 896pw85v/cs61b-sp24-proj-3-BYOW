package core;

import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    public static void main(String[] args) {



        World world = new World(60, 50);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(60, 50);
        renderer.renderFrame(world.world());

    }
}
