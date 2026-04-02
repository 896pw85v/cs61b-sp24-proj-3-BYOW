package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

// i will try to avoid recursion
public class World {
    int length;
    int width;
    int area = 0;
    Random r;
    TETile[][] grid;
    OriginNet originNet = new OriginNet();

    public World(int length, int width) {
        this.length = length;
        this.width = width;
        r = new Random();
        grid = new TETile[length][width];
        for (int i  = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == Tileset.FLOOR) continue;
                grid[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void what() {
        while (area < length * width / 2) { // checking area. i guess this will cause problem
            int x = r.nextInt(length - 3);
            int y = r.nextInt(width - 3);
            int xp = r.nextInt(x, length - 1);
            int yp = r.nextInt(y, width - 1);
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
            originNet.addNode(origin); // heap or queue that's used to build walls at the end

            putTiles(x, y, xp, yp);
        }
    }



    // put rectangular room
    public void putTiles(int fromX, int fromY, int toX, int toY) {
        for (int i  = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                if (grid[i][j] == Tileset.FLOOR) continue;
                grid[i][j] = Tileset.FLOOR;
                area++;
            }
        }
    }

    public Origin findClosest(Origin node) {
        if (node == null) return null;
        int x= node.x(), y = node.y();
        // from the tree? from the grid? any existing node would be in both.
        //from tree: traverse tree;
        //from grid: traverse grid;
        //either is slow
        //either is fine
        // even though traversing the tree seem slow, but implementation is based on hashmap, super fast
        // and it
        double minDistance = Double.MAX_VALUE;
        Origin closest = originNet.origins().getFirst();
        for (Origin target : originNet.origins()) {
            if (distanceBetween(x, y, target.x(), target.y()) < minDistance) closest = target;
            // keep doing. i just realized on this step calculating straight distance is fine
            // the exact placement of blocks can be handled later
        }
        originNet.mapChild(closest, node);
        return closest;
    }

    public double distanceBetween(int a, int b, int c, int d) {
        if (b == d) return (double) c - a;
        if (a == c) return (double) d - b;
        return Math.sqrt(((double) c - (double) a) / ((double) d - (double) b));
    }

    public TETile[][] world() {
        return grid;
    }

}

/*
 is expand even necessary?
 it's to prevent randomness. if random is truly uniform, simply adding blocks by random
 would result in a map almost full, evenly filled like QR code.
 since expanding is necessary, how to do that?
 */
