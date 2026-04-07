package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * idea of this class come from Wordnet. assume this works.
 * This class has great flaw that the int[2] to indicate origin coordinates is never checked.
 * Passing in a weird int[] would be catastrophic
 */
public class OriginNet {
    public ArrayList<Origin> list = new ArrayList<>();
    public HashMap<Origin, HashSet<Origin>> map = new HashMap<>();

    public OriginNet() {
        // do nothing
    }

    public void addNode(Origin node) {


        list.add(node);
    }

    public void buildMapping() {
        for (Origin origin : list) {

            Origin parent = findClosest(origin);
            mapChild(parent, origin);
        }
    }

    public void mapChild(Origin node, Origin child) {
        if (map.containsKey(node)) {
            HashSet<Origin> set = map.get(node);
            set.add(child);
            // this is actually equivalent to the line below
        } else {
            map.put(node, new HashSet<>());
            map.get(node).add(child);
        }
    }

    public ArrayList<Origin> origins() {
        return list;
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
        Origin closest = new Origin(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Origin target : list) {
            if (target.equals(node)) continue;
            if (distanceBetween(x, y, target.x(), target.y()) < minDistance) closest = target;
            // keep doing. i just realized on this step calculating straight distance is fine
            // the exact placement of blocks can be handled later
        }
        mapChild(closest, node);
        return closest;
    }

    public double distanceBetween(int a, int b, int c, int d) {
//        if (b == d) return (double) c - a;
//        if (a == c) return (double) d - b;
//        return Math.sqrt(((double) c - (double) a) / ((double) d - (double) b));
        return Math.sqrt(Math.pow(a - c, 2) + Math.pow(b - d, 2));
    }

}
