package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

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
//        1215520099    isolated

        World world = new World(70, 40);
        world.setSeed(seed);
//        world.setSeed(934267690);
        world.what();

        TERenderer renderer = new TERenderer();
        renderer.initialize(70, 42);

        int cx = 0;
        int cy = 0;
        TETile[][] w = world.world();
        for (int i = 0; i < 70; i++) {
            boolean out = false;
            for (int j = 0; j < 40; j++) {
                if (w[i][j] == Tileset.FLOOR) {
                    w[i][j] = Tileset.AVATAR;
                    cx = i;
                    cy = j;
                    out = true;
                    break;
                }
            }
            if (out) break;
        }
        String input = "aasdwasdwadwasdwwwwwwwdddddddsdsdsdsdsdwadasdwasdwad:q";
        int at = 0;
        while (true) {
            if (input.length() > at) {
                int onx = 0;
                int ony = 0;
                TETile tar;
                char c = input.charAt(at);
                at++;
                System.out.println(c);
                switch (c) {
                    case 'a':
                        onx = -1;
                        break;
                    case 's':
                        ony = -1;
                        break;
                    case 'd':
                        onx = 1;
                        break;
                    case 'w':
                        ony = 1;
                        break;
                    case ':':
                        if (at < input.length() && input.charAt(at) == 'q') System.exit(1);
                    default:
                        break;
                }
                tar = w[cx + onx][cy + ony];
                if (tar == Tileset.FLOOR) {
                    w[cx + onx][cy + ony] = Tileset.AVATAR;
                    w[cx][cy] = Tileset.FLOOR;
                    cx += onx;
                    cy += ony;
                }
            }
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, 41, "you are at (" + cx + ", " + cy + ")");
            StdDraw.show();
            int x = (int) StdDraw.mouseX();
            int y = (int)  StdDraw.mouseY();
            if (x < 70 && y < 40) {
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textRight(69, 41, w[x][y].description() + " at (" + x + ", " + y + ")");
                StdDraw.show();


            }
            StdDraw.clear(Color.black);
            renderer.drawTiles(w);

            StdDraw.pause(50);
        }
    }

}
