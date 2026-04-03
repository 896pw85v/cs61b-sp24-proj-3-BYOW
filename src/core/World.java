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
            int x = r.nextInt(1, length - 3);
            int y = r.nextInt(1, width - 3);
            int xp = r.nextInt(x + 1, length - 1);
            int yp = r.nextInt(y + 1, width - 1);
            if (xp - x > 10) xp = x + 10;
            if (yp - y > 10) yp = y + 10;
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
        buildPath();
        buildWall();
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





    public TETile[][] world() {
        return grid;
    }

    public void buildPath() {
        for (Origin parent : originNet.list) {
            if (!originNet.map.containsKey(parent)) continue;
            for (Origin child : originNet.map.get(parent)) {
                connectOrigins(parent, child);
            }
        }
    }

    public void connectOrigins(Origin parent, Origin child) {
        int a = parent.x(); int b = parent.y();
        int m = child.x(); int n = child.y();
        if (a >= m) {
            if (b >= n) pave(m, n, a, b);
            else pave(m, a, b, n);
        } else {
            if (b >= n) pave(a, n, m, b);
            else pave(a, b, m, n);
        }

    }

    public void pave(int fromX, int fromY, int toX, int toY) {
        if (toX - fromX >= toY - fromY) {
            for (int i = fromX; i <= toX; i++) {
                grid[i][fromY] = Tileset.FLOOR;
            }
            for (int i = fromY; i <= toY; i++) {
                grid[toX][i] = Tileset.FLOOR;
            }

        } else {
            for (int i = fromX; i <= toX; i++) {
                grid[i][fromY] = Tileset.FLOOR;
            }
            for (int i = fromY; i <= toY; i++) {
                grid[toX][i] = Tileset.FLOOR;
            }
        }
    }

    public void buildWall() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (detectFloor(i, j)) grid[i][j] = Tileset.WALL;
            }
        }
    }

    public boolean detectFloor(int x, int y) {
        if (grid[x][y] == Tileset.FLOOR) return false;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nr = x + dr[i];
            int nc = y + dc[i];

            // Skip if out of bounds
            if (nr < 0 || nr >= length || nc < 0 || nc >= width) {
                continue;
            }

            if (grid[nr][nc] == Tileset.FLOOR) {
                return true;
            }
        }

        return false;
    }
}

/*
 is expand even necessary?
 it's to prevent randomness. if random is truly uniform, simply adding blocks by random
 would result in a map almost full, evenly filled like QR code.
 since expanding is necessary, how to do that?
 */