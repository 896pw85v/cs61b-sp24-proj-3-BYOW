package core;

import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    public static void main(String[] args) {



        World world = new World(70, 40);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(70, 40);
        renderer.renderFrame(world.world());

    }
}
