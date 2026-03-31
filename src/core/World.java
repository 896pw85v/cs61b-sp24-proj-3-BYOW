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
    }

    public void what() {
        while (area < length * width / 2) { // checking area. i guess this will cause problem
            int x = r.nextInt(length);
            int y = r.nextInt(width);
            int l = r.nextInt(length - x);
            int w = r.nextInt(width - y);
            int [] origin = {r.nextInt(x, x + l), r.nextInt(y, y + w)};
            originNet.addNode(origin); // heap or queue that's used to build walls at the end
            for (int i = x; i < x + l; i++ ) {
                for (int j = y; j < y + w; j ++) {
                    grid[i][j] = Tileset.FLOOR;
                }
            } // this finishes a room, continue
            putTiles(x, y, x + l, y + w);
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

    public int[] findClosest(int[] node) {
        if (node == null || node.length != 2) return null;
        int x= node[0], y = node[1];
        // from the tree? from the grid? any existing node would be in both.
        //from tree: traverse tree;
        //from grid: traverse grid;
        //either is slow
        //either is fine
        double minDistance = Double.MAX_VALUE;
        int[] closest = originNet.origins().getFirst();
        for (int[] target : originNet.origins()) {
            if (distanceBetween(x, y, target[0], target[1]) < minDistance) closest = target;
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

}

/*
 is expand even necessary?
 it's to prevent randomness. if random is truly uniform, simply adding blocks by random
 would result in a map almost full, evenly filled like QR code.
 since expanding is necessary, how to do that?
 */
