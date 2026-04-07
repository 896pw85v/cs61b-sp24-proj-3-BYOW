package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;


public class World {
    int length;
    int width;
    int area = 0;
    Random r;
    TETile[][] grid;
    OriginNet originNet = new OriginNet();
    TERenderer ren = new TERenderer();

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
        ren.initialize(length, width);
    }

    public void what() {

        while (area < length * width / 5) { // checking area. I guess this will cause problem
            ren.renderFrame(grid);

            int x = r.nextInt(1, length - 3);
            int y = r.nextInt(1, width - 3);
//            if (grid[x][y] == Tileset.FLOOR) continue;

            int xp = r.nextInt(x + 1, length - 1);
            int yp = r.nextInt(y + 1, width - 1);


            if (xp - x > 5) xp = x + 5;
            if (yp - y > 5) yp = y + 5;
//            if (grid[xp][yp] == Tileset.FLOOR) continue;

            int ox;
            int oy;

                ox = r.nextInt(x, xp + 1);

                oy = r.nextInt(y, yp + 1);

//            if (grid[ox][oy] == Tileset.FLOOR) continue;

            assert x < ox && ox < xp;
            assert y < oy && oy < yp;

            Origin origin = new Origin(ox, oy);
            originNet.addNode(origin);

            buildRoom(x, y, xp, yp);
        }
        originNet.buildMapping();
        buildPath();
        buildWall();
    }



    // put rectangular room
    public void buildRoom(int fromX, int fromY, int toX, int toY) {
        for (int i  = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
//                ren.renderFrame(grid);
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
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

    // this one is completely wrong
    public void connectOrigins(Origin parent, Origin child) {
        System.out.println(parent + " to " + child);
        int a = parent.x(); int b = parent.y();
        int m = child.x(); int n = child.y();

        if (a < m) {
            if (b < n) {
                for (int i = a; i <= m; i++) {
                    grid[i][b] = Tileset.FLOOR;
                }
                for (int i = b; i <= n; i++) {
                    grid[m][i] = Tileset.FLOOR;
                }
            } else {
                for (int i = a; i <= m; i++) {
                    grid[i][b] = Tileset.FLOOR;
                }
                for (int i = n; i <= b; i++) {
                    grid[m][i] = Tileset.FLOOR;
                }
            }
        } else {
            if (b < n) {
                for (int i = m; i <= a; i++) {
                    grid[i][b] = Tileset.FLOOR;
                }
                for (int i = b; i <= n; i++) {
                    grid[m][i] = Tileset.FLOOR;
                }
            } else {
                for (int i = m; i <= a; i++) {
                    grid[i][b] = Tileset.FLOOR;
                }
                for (int i = n; i <= b; i++) {
                    grid[m][i] = Tileset.FLOOR;
                }
            }
        }

    }

    public void pavePath(int fromX, int fromY, int toX, int toY) {
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

    public void setSeed(int seed) {
        r = new Random(seed);
        // but this is very dangerous, cuz anyone can change seed anytime. should make it a constructor.
    }
}
