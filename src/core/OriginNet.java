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
    ArrayList<int[]> list = new ArrayList<>();
    HashMap<int[], HashSet<int[]>> map = new HashMap<>();

    public OriginNet() {
        // do nothing
    }

    public void addNode(int[] node) {
        list.add(node);
    }

    public void mapChild(int[] node, int[] child) {
        if (map.containsKey(node)) {
            HashSet<int[]> set = map.get(node);
            set.add(child);
            // this is actually equivalent to the line below
        } else {
            map.put(node, new HashSet<>());
            map.get(node).add(child);
        }
    }

    public ArrayList<int[]> origins() {
        return list;
    }

}
